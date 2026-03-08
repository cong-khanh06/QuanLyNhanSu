package GUI;

import BUS.TaiKhoan_BUS;
import DTO.TaiKhoan_DTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TaiKhoan_GUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private TaiKhoan_BUS bus = new TaiKhoan_BUS();

    private final Color PRIMARY_COLOR = new Color(63, 81, 181); // Xanh Indigo chủ đạo
    private final Color SECONDARY_COLOR = new Color(245, 245, 245);

    public TaiKhoan_GUI() {
        setLayout(new BorderLayout(0, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // --- HEADER ---
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(false);

        JLabel lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(PRIMARY_COLOR);
        panelHeader.add(lblTitle, BorderLayout.WEST);

        JPanel pHeaderActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pHeaderActions.setOpaque(false);

        txtSearch = new JTextField(18);
        txtSearch.setPreferredSize(new Dimension(200, 35));
        
        JButton btnSearch = createStyledButton("Tìm kiếm", PRIMARY_COLOR, Color.WHITE);
        JButton btnRefresh = createStyledButton("Làm mới", PRIMARY_COLOR, Color.WHITE);
        JButton btnAdd = createStyledButton("Thêm mới", PRIMARY_COLOR, Color.WHITE);
        JButton btnUpdate = createStyledButton("Chỉnh sửa", PRIMARY_COLOR, Color.WHITE);
        JButton btnDelete = createStyledButton("Xóa", PRIMARY_COLOR, Color.WHITE);

        pHeaderActions.add(new JLabel("Tìm kiếm:"));
        pHeaderActions.add(txtSearch);
        pHeaderActions.add(btnSearch);
        pHeaderActions.add(btnRefresh);
        pHeaderActions.add(btnAdd);
        pHeaderActions.add(btnUpdate);
        pHeaderActions.add(btnDelete);

        panelHeader.add(pHeaderActions, BorderLayout.EAST);
        add(panelHeader, BorderLayout.NORTH);

        setupTable();

        // --- EVENTS ---
        btnAdd.addActionListener(e -> showInputForm(null));
        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản!");
                return;
            }
            showInputForm(getDTOFromTable(row));
        });
        btnDelete.addActionListener(e -> deleteAction());
        btnRefresh.addActionListener(e -> loadData());
        btnSearch.addActionListener(e -> searchAction());

        loadData();
    }

    private void setupTable() {
        String[] headers = {"Mã TK", "Tên Đăng Nhập", "Mật Khẩu", "Quyền Truy Cập", "Mã NV"};
        model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(232, 234, 246)); 
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(224, 224, 224));
        table.setFocusable(false); 

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(92, 107, 192)); 
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadData() { renderTable(bus.getAll()); }
    
    private void searchAction() { renderTable(bus.search(txtSearch.getText().trim())); }

    private void renderTable(ArrayList<TaiKhoan_DTO> list) {
        model.setRowCount(0);
        for (TaiKhoan_DTO tk : list) {
            model.addRow(new Object[]{tk.getMataikhoan(), tk.getTendangnhap(), "******", tk.getQuyentruycap(), tk.getManv()});
        }
    }

    private TaiKhoan_DTO getDTOFromTable(int row) {
        return new TaiKhoan_DTO(
            getValueAt(row, 0), getValueAt(row, 1), "", getValueAt(row, 3), getValueAt(row, 4)
        );
    }

    private String getValueAt(int row, int col) {
        return model.getValueAt(row, col).toString();
    }

    private void deleteAction() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        String ma = getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Xóa tài khoản " + ma + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, bus.delete(ma));
            loadData();
        }
    }

    private void showInputForm(TaiKhoan_DTO data) {
        InputFormDialog dialog = new InputFormDialog((JFrame) SwingUtilities.getWindowAncestor(this), data);
        dialog.setVisible(true);
        if (dialog.isDataChanged()) loadData();
    }

    // --- DIALOG NHẬP LIỆU ---
    class InputFormDialog extends JDialog {
        private JTextField txtMaTK, txtUser, txtPass, txtMaNV;
        private JComboBox<String> cbQuyen;
        private boolean dataChanged = false;
        private boolean isEdit;

        public InputFormDialog(JFrame parent, TaiKhoan_DTO data) {
            super(parent, (data == null ? "THÊM TÀI KHOẢN MỚI" : "CẬP NHẬT TÀI KHOẢN"), true);
            this.isEdit = (data != null);
            setSize(450, 480); // Tăng chiều cao một chút để chứa dòng nhắc
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout());

            JPanel pMain = new JPanel(new GridBagLayout());
            pMain.setBorder(new EmptyBorder(20, 20, 20, 20));
            pMain.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            txtMaTK = new JTextField(15);
            txtUser = new JTextField(15);
            txtPass = new JPasswordField(15);
            txtMaNV = new JTextField(15);
            cbQuyen = new JComboBox<>(new String[]{"Admin", "User", "Manager"});

            if (isEdit) {
                txtMaTK.setText(data.getMataikhoan()); txtMaTK.setEditable(false);
                txtUser.setText(data.getTendangnhap()); txtUser.setEditable(false);
                txtMaNV.setText(data.getManv());
                cbQuyen.setSelectedItem(data.getQuyentruycap());
            }

            addComp(pMain, "Mã TK:", 0, 0, gbc); gbc.gridx = 1; pMain.add(txtMaTK, gbc);
            addComp(pMain, "Username:", 1, 0, gbc); gbc.gridx = 1; pMain.add(txtUser, gbc);
            addComp(pMain, "Mật khẩu:", 2, 0, gbc); gbc.gridx = 1; pMain.add(txtPass, gbc);

            if (isEdit) {
                gbc.gridy = 3; gbc.gridx = 1;
                JLabel lbNote = new JLabel("(*) Để trống nếu muốn giữ nguyên mật khẩu cũ");
                lbNote.setFont(new Font("Segoe UI", Font.ITALIC, 11));
                lbNote.setForeground(Color.RED);
                pMain.add(lbNote, gbc);
            }

            addComp(pMain, "Mã NV:", 4, 0, gbc); gbc.gridx = 1; pMain.add(txtMaNV, gbc);
            addComp(pMain, "Quyền:", 5, 0, gbc); gbc.gridx = 1; pMain.add(cbQuyen, gbc);

            add(pMain, BorderLayout.CENTER);

            JPanel pBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
            pBottom.setBackground(SECONDARY_COLOR);
            JButton btnSave = createStyledButton("LƯU DỮ LIỆU", PRIMARY_COLOR, Color.WHITE);
            JButton btnExit = createStyledButton("HỦY", Color.WHITE, Color.BLACK);
            btnExit.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            btnSave.addActionListener(e -> {
                String password = txtPass.getText().trim();
                
                if (!isEdit) { 
                    if (password.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu khởi tạo!");
                        txtPass.requestFocus();
                        return;
                    }
                    if (password.length() < 6) {
                        JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!");
                        txtPass.requestFocus();
                        return;
                    }
                } else { 
                    if (!password.isEmpty() && password.length() < 6) {
                        JOptionPane.showMessageDialog(this, "Mật khẩu mới phải có ít nhất 6 ký tự!");
                        txtPass.requestFocus();
                        return;
                    }
                }

                TaiKhoan_DTO dto = new TaiKhoan_DTO(
                    txtMaTK.getText(), 
                    txtUser.getText(), 
                    password, // Nếu password rỗng, BUS/DAO sẽ xử lý giữ nguyên pass cũ
                    cbQuyen.getSelectedItem().toString(), 
                    txtMaNV.getText()
                );
                
                String res = isEdit ? bus.update(dto) : bus.add(dto);
                JOptionPane.showMessageDialog(this, res);
                if (res.contains("thành công")) { dataChanged = true; dispose(); }
            });

            btnExit.addActionListener(e -> dispose());
            pBottom.add(btnExit); pBottom.add(btnSave);
            add(pBottom, BorderLayout.SOUTH);
        }

        private void addComp(JPanel p, String text, int row, int col, GridBagConstraints gbc) {
            gbc.gridx = col; gbc.gridy = row;
            JLabel lb = new JLabel(text);
            lb.setFont(new Font("Segoe UI", Font.BOLD, 12));
            p.add(lb, gbc);
        }
        public boolean isDataChanged() { return dataChanged; }
    }
}