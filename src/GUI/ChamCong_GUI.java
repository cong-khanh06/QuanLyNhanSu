package GUI;

import BUS.ChamCong_BUS;
import DTO.ChamCong_DTO;
import GUI.button.ButtonToolBar;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChamCong_GUI extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private JComboBox<String> cboPhong, cboThang, cboNam; 
    private JButton btnSearch, btnAdd, btnEdit, btnDelete;

    private ChamCong_BUS bus = new ChamCong_BUS();
    private boolean isAdmin = true; 
    private String currentMaNV = ""; 
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ChamCong_GUI() {
        // NỀN XÁM TỔNG THỂ
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(226, 232, 240));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ================= HEADER & TOOLBAR (Card Trắng) =================
        JPanel pnTopCard = new JPanel(new BorderLayout());
        pnTopCard.setBackground(Color.WHITE);
        pnTopCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(245, 158, 11)), // Viền Cam
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel lblTitle = new JLabel("QUẢN LÝ CHẤM CÔNG HÀNG NGÀY");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 41, 59));
        pnTopCard.add(lblTitle, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        txtSearch = new JTextField(15);
        txtSearch.setPreferredSize(new Dimension(150, 36));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnSearch = createFlatButton("Tìm kiếm", "primary");

        cboPhong = new JComboBox<>(); cboPhong.addItem("Tất cả");
        for (String p : bus.getDanhSachPhong()) cboPhong.addItem(p);

        cboThang = new JComboBox<>(); cboThang.addItem("Tất cả");
        for (int i = 1; i <= 12; i++) cboThang.addItem(String.valueOf(i));
        
        cboNam = new JComboBox<>(); cboNam.addItem("Tất cả");
        int namHienTai = Year.now().getValue();
        for (int i = 2020; i <= namHienTai; i++) cboNam.addItem(String.valueOf(i)); 
        
        cboThang.setSelectedItem("Tất cả");
        cboNam.setSelectedItem("Tất cả");

        btnAdd = createFlatButton("Thêm Mới", "primary");
        btnEdit = createFlatButton("Sửa", "");
        btnDelete = createFlatButton("Xóa", "danger");

        filterPanel.add(txtSearch); filterPanel.add(btnSearch);
        filterPanel.add(new JLabel("Phòng:")); filterPanel.add(cboPhong);
        filterPanel.add(new JLabel("Tháng:")); filterPanel.add(cboThang);
        filterPanel.add(new JLabel("Năm:")); filterPanel.add(cboNam);
        filterPanel.add(btnAdd); filterPanel.add(btnEdit); filterPanel.add(btnDelete);

        pnTopCard.add(filterPanel, BorderLayout.CENTER);
        add(pnTopCard, BorderLayout.NORTH);
        
        // ================= BẢNG DỮ LIỆU =================
        String[] cols = { "Mã CC", "Nhân viên", "Ngày Chấm", "Giờ Vào", "Giờ Ra", "Tăng ca (Giờ)", "Trạng thái" };
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        JPanel pnTableCard = new JPanel(new BorderLayout());
        pnTableCard.setBackground(Color.WHITE);
        pnTableCard.add(scrollPane, BorderLayout.CENTER);
        
        add(pnTableCard, BorderLayout.CENTER);

        
        btnSearch.addActionListener(e -> refreshData());
        cboPhong.addActionListener(e -> refreshData());
        cboThang.addActionListener(e -> refreshData());
        cboNam.addActionListener(e -> refreshData());

        btnAdd.addActionListener(e -> {
            new ChamCong1_GUI(this, null).setVisible(true);
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa!"); return;
            }
            try {
                String macc = table.getValueAt(row, 0).toString();
                String manv = table.getValueAt(row, 1).toString().split(" - ")[0]; 
                
                String strNgay = table.getValueAt(row, 2).toString();
                LocalDate ngay = strNgay.isEmpty() ? null : LocalDate.parse(strNgay, dateFormatter);
                
                String strVao = table.getValueAt(row, 3).toString();
                LocalTime vao = strVao.equals("--:--") ? null : LocalTime.parse(strVao);
                
                String strRa = table.getValueAt(row, 4).toString();
                LocalTime ra = strRa.equals("--:--") ? null : LocalTime.parse(strRa);
                
                float tangCa = Float.parseFloat(table.getValueAt(row, 5).toString());
                String trangThai = table.getValueAt(row, 6).toString();
                
                ChamCong_DTO ccEdit = new ChamCong_DTO(macc, manv, "", ngay, vao, ra, tangCa, trangThai);
                new ChamCong1_GUI(this, ccEdit).setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa!"); return;
            }
            String macc = table.getValueAt(row, 0).toString();
            if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (bus.xoaChamCong(macc)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa!");
                    refreshData();
                }
            }
        });

        refreshData();
    }
    
    private JButton createFlatButton(String text, String style) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(100, 36));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (!style.isEmpty()) btn.putClientProperty("FlatLaf.styleClass", style);
        return btn;
    }

    private void styleTable(JTable tb) {
        tb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tb.setRowHeight(30);
        tb.setShowVerticalLines(false); tb.setShowHorizontalLines(true);
        tb.setGridColor(new Color(230, 230, 230));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tb.getTableHeader().setBackground(new Color(248, 250, 252));
    }
    
    public void refreshData() {
        if (isAdmin) loadTable();
        else loadTableuser(currentMaNV);
    }

    private void loadTable() {
        model.setRowCount(0);
        int thang = cboThang.getSelectedItem().equals("Tất cả") ? 0 : Integer.parseInt(cboThang.getSelectedItem().toString());
        int nam = cboNam.getSelectedItem().equals("Tất cả") ? 0 : Integer.parseInt(cboNam.getSelectedItem().toString());
        
        String phong = cboPhong.getSelectedItem().toString();
        if (phong.equals("Tất cả")) phong = "";
        String keyword = txtSearch.getText().trim();

        fillTable(bus.getDanhSachChamCong(thang, nam, phong, keyword));
    }

    public void setphanquyenUser(boolean isAdmin, String manv) {
        this.isAdmin = isAdmin;
        this.currentMaNV = manv;
        txtSearch.setEnabled(isAdmin);
        cboPhong.setEnabled(isAdmin);
        btnAdd.setEnabled(isAdmin); 
        btnEdit.setEnabled(isAdmin); 
        btnDelete.setEnabled(isAdmin); 
        refreshData();
    }

    public void loadTableuser(String manv) {
        model.setRowCount(0);
        int thang = cboThang.getSelectedItem().equals("Tất cả") ? 0 : Integer.parseInt(cboThang.getSelectedItem().toString());
        int nam = cboNam.getSelectedItem().equals("Tất cả") ? 0 : Integer.parseInt(cboNam.getSelectedItem().toString());
        
        fillTable(bus.getDanhSachChamCongSVDangNhap(thang, nam, manv));
    }
    
    private void fillTable(List<ChamCong_DTO> list) {
        model.setRowCount(0);
        for (ChamCong_DTO c : list) {
            String nhanVien = c.getMaNV() + " - " + c.getHoTen();
            String strNgay = c.getNgayTao() != null ? c.getNgayTao().format(dateFormatter) : "";
            String strVao = c.getGioVao() != null ? c.getGioVao().format(timeFormatter) : "--:--";
            String strRa = c.getGioRa() != null ? c.getGioRa().format(timeFormatter) : "--:--";
            
            model.addRow(new Object[] {
                c.getMaChamCong(), nhanVien, strNgay, strVao, strRa, c.getSoGioTangCa(), c.getTrangThai()
            });
        }
    }
}