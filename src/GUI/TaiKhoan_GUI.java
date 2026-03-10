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

    private final Color PRIMARY_COLOR = new Color(63, 81, 181);

    public TaiKhoan_GUI() {
        setLayout(new BorderLayout(0, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 15, 15, 15));

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
        TaiKhoan1_GUI dialog = new TaiKhoan1_GUI((JFrame) SwingUtilities.getWindowAncestor(this), data);
        dialog.setVisible(true);
        if (dialog.isDataChanged()) loadData();
    }
}