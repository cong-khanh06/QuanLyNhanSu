package GUI;

import BUS.BangCap_BUS;
import DTO.BangCap_DTO;
import GUI.button.ButtonToolBar; 

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BangCap_GUI extends JPanel {
    JPanel pnSearch, pnHeader, pnToolBar;
    JTable tableBC;
    DefaultTableModel modelBC;
    JTextField txtSearch;
    JButton btnSearch, btnAdd, btnEdit, btnDelete, btnRefresh;
    JLabel lblTitle;
    
    BangCap_BUS bus = new BangCap_BUS();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BangCap_GUI() {
        setBackground(new Color(226, 232, 240)); 
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        pnSearch = new JPanel(new BorderLayout());
        pnSearch.setBackground(Color.WHITE);
        // Điểm nhấn viền Xanh Ngọc
        pnSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(16, 185, 129)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnHeader.setBackground(Color.WHITE);
        lblTitle = new JLabel("Quản Lý Bằng Cấp Nhân Viên");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 41, 59));
        pnHeader.add(lblTitle);

        pnToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnToolBar.setBackground(Color.WHITE);
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(250, 36));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Áp dụng FlatLaf styles
        btnSearch = createFlatButton("Tìm kiếm", "primary");
        btnAdd = createFlatButton("Thêm", "primary");
        btnEdit = createFlatButton("Sửa", "");
        btnDelete = createFlatButton("Xóa", "danger");
        btnRefresh = createFlatButton("Tải lại", "");

        pnToolBar.add(txtSearch); pnToolBar.add(btnSearch);
        pnToolBar.add(btnAdd); pnToolBar.add(btnEdit);
        pnToolBar.add(btnDelete); pnToolBar.add(btnRefresh);

        pnSearch.add(pnHeader, BorderLayout.NORTH);
        pnSearch.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearch, BorderLayout.NORTH);

        String[] cols = {"Mã Bằng Cấp", "Tên Bằng Cấp", "Nơi Cấp", "Ngày Cấp", "Mã Nhân Viên"};
        modelBC = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableBC = new JTable(modelBC);
        styleTable(tableBC); // Nhớ copy hàm styleTable từ phần Ứng Lương ở trên xuống nhé

        JScrollPane scrollPane = new JScrollPane(tableBC);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        JPanel pnTableCard = new JPanel(new BorderLayout());
        pnTableCard.setBackground(Color.WHITE);
        pnTableCard.add(scrollPane, BorderLayout.CENTER);
        add(pnTableCard, BorderLayout.CENTER);
        taiDuLieuLenBang();
        addEvents();
    }
    
    private void styleTable(JTable tb) {
        tb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tb.setRowHeight(30);
        tb.setShowVerticalLines(false);
        tb.setShowHorizontalLines(true);
        tb.setGridColor(new Color(230, 230, 230));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tb.getTableHeader().setBackground(new Color(248, 250, 252));
    }
    
    private JButton createFlatButton(String text, String style) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(100, 36));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (!style.isEmpty()) btn.putClientProperty("FlatLaf.styleClass", style);
        return btn;
    }
    
    public void taiDuLieuLenBang() {
        modelBC.setRowCount(0);
        List<BangCap_DTO> list = bus.layDanhSachBangCap();
        for (BangCap_DTO bc : list) {
            String strNgayCap = (bc.getNgayCap() != null) ? bc.getNgayCap().format(dtf) : "";
            modelBC.addRow(new Object[]{
                bc.getMaBC(), bc.getTenBC(), bc.getNoiCap(), strNgayCap, bc.getMaNV()
            });
        }
    }

    private void addEvents() {
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            taiDuLieuLenBang();
        });

        btnAdd.addActionListener(e -> {
            BangCap_Dialog dialog = new BangCap_Dialog(this);
            dialog.setVisible(true);
        });

        btnDelete.addActionListener(e -> {
            int row = tableBC.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bằng cấp để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maBC = tableBC.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa bằng cấp " + maBC + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.xoaBangCap(maBC)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    taiDuLieuLenBang();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEdit.addActionListener(e -> {
            int row = tableBC.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bằng cấp để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String maBC = tableBC.getValueAt(row, 0).toString();
                String tenBC = tableBC.getValueAt(row, 1) != null ? tableBC.getValueAt(row, 1).toString() : "";
                String noiCap = tableBC.getValueAt(row, 2) != null ? tableBC.getValueAt(row, 2).toString() : "";
                String strNgayCap = tableBC.getValueAt(row, 3) != null ? tableBC.getValueAt(row, 3).toString() : "";
                String maNV = tableBC.getValueAt(row, 4) != null ? tableBC.getValueAt(row, 4).toString() : "";

                LocalDate ngayCap = strNgayCap.isEmpty() ? null : LocalDate.parse(strNgayCap, dtf);

                BangCap_DTO bcEdit = new BangCap_DTO(maBC, tenBC, noiCap, maNV, ngayCap);
                BangCap_Dialog dialog = new BangCap_Dialog(this, bcEdit);
                dialog.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi đọc dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    
}