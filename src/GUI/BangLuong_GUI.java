package GUI;

import BUS.BangLuong_BUS;
import DTO.BangLuong_DTO;
import GUI.button.ButtonToolBar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class BangLuong_GUI extends JPanel {
    JPanel pnSearchBL, pnHeader, pnToolBar;
    JTable tableBL;
    DefaultTableModel modelBL;
    JTextField txtSearch;
    JButton btnSearch, btnAdd, btnEdit, btnDelete, btnRefresh;
    JComboBox<String> cbThang, cbNam, cbTrangThai; 
    JLabel lblTitle;
    
    BangLuong_BUS bus = new BangLuong_BUS();
    JLabel lblTongQuyLuong, lblTongDaThanhToan, lblTongChuaThanhToan; 
    DecimalFormat df = new DecimalFormat("#,### VNĐ");
    
    public BangLuong_GUI() {
        setLayout(new BorderLayout());
        
        
        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnHeader.setBackground(new Color(150, 214, 255));
        pnHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        lblTitle = new JLabel("Quản Lý Bảng Lương");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnHeader.add(lblTitle);
        
        
        pnToolBar = new JPanel(new BorderLayout(10, 10)); 
        pnToolBar.setBackground(new Color(150, 214, 255));
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        
        
        JPanel pnSearch = new JPanel(new BorderLayout(5, 0));
        pnSearch.setBackground(new Color(150, 214, 255));
        
        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setToolTipText("Tìm mã NV, tên NV, mã BL...");
        
        btnSearch = new ButtonToolBar("Tìm");
        btnSearch.setPreferredSize(new Dimension(80, 36));
        
        pnSearch.add(txtSearch, BorderLayout.CENTER);
        pnSearch.add(btnSearch, BorderLayout.EAST);
        
        // 2. Nhóm Chức năng & Lọc (Cố định đẩy về bên phải)
        JPanel pnAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnAction.setBackground(new Color(150, 214, 255));
        
        btnAdd = new ButtonToolBar("Thêm Mới");
        btnEdit = new ButtonToolBar("Sửa");     
        btnDelete = new ButtonToolBar("Xóa");
        btnRefresh = new ButtonToolBar("Tải Lại");
        
        btnEdit.setPreferredSize(new Dimension(80, 36));   
        btnDelete.setPreferredSize(new Dimension(80, 36));
        btnAdd.setPreferredSize(new Dimension(100, 36));
        btnRefresh.setPreferredSize(new Dimension(100, 36));
        
        cbThang = new JComboBox<>(new String[]{"Tất cả", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
        cbThang.setPreferredSize(new Dimension(90, 36));
        cbThang.setToolTipText("Chọn Tháng");
        
        cbNam = new JComboBox<>(new String[]{"Tất cả", "2024", "2025", "2026", "2027"});
        cbNam.setPreferredSize(new Dimension(90, 36));
        cbNam.setToolTipText("Chọn Năm");

        cbTrangThai = new JComboBox<>(new String[]{"Tất cả", "Chưa thanh toán", "Đã thanh toán"});
        cbTrangThai.setPreferredSize(new Dimension(140, 36));

        pnAction.add(new JLabel("Tháng:")); pnAction.add(cbThang);
        pnAction.add(new JLabel("Năm:")); pnAction.add(cbNam);
        pnAction.add(cbTrangThai); 
        pnAction.add(btnAdd);
        pnAction.add(btnEdit);   
        pnAction.add(btnDelete);
        pnAction.add(btnRefresh);

        pnToolBar.add(pnSearch, BorderLayout.CENTER);
        pnToolBar.add(pnAction, BorderLayout.EAST);
        
        pnSearchBL = new JPanel(new BorderLayout());
        pnSearchBL.setBackground(Color.WHITE);
        pnSearchBL.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        pnSearchBL.add(pnHeader, BorderLayout.NORTH);
        pnSearchBL.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearchBL, BorderLayout.NORTH);
        
        
        String[] colsBL = {
            "Mã BL", "Mã NV", "Tên Nhân Viên", "Kỳ Lương", "Lương CB", "Phụ Cấp", "Khấu Trừ", "THỰC LÃNH", "Trạng Thái"
        };
        modelBL = new DefaultTableModel(colsBL, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableBL = new JTable(modelBL);
        tableBL.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableBL.setRowHeight(28);
        tableBL.setShowGrid(true);
        tableBL.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        add(new JScrollPane(tableBL), BorderLayout.CENTER);
        
        JPanel pnThongKe = new JPanel(new GridLayout(1, 3, 20, 0)); 
        pnThongKe.setBackground(Color.WHITE);
        pnThongKe.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        lblTongQuyLuong = new JLabel();
        lblTongDaThanhToan = new JLabel();
        lblTongChuaThanhToan = new JLabel();
        
        pnThongKe.add(taoOThongKe("Tổng Quỹ Lương (Kết quả lọc)", new Color(255, 235, 153), lblTongQuyLuong));        
        pnThongKe.add(taoOThongKe("Đã Thanh Toán", new Color(153, 255, 153), lblTongDaThanhToan)); 
        pnThongKe.add(taoOThongKe("Còn Nợ (Chưa thanh toán)", new Color(255, 153, 153), lblTongChuaThanhToan));       
        
        add(pnThongKe, BorderLayout.SOUTH);

        addEventRefresh();
        addEventSearch();
        addEventAdd();
        addEventEdit();
        addEventDelete();
        
        taiDuLieuLenBang();
    }
    
    public void taiDuLieuLenBang() {
        hienThiDanhSach(bus.layDanhSachBangLuong());
    }
    
    private void hienThiDanhSach(List<BangLuong_DTO> danhSach) {
        modelBL.setRowCount(0); 
        for (BangLuong_DTO bl : danhSach) {
            String kyLuong = bl.getThang() + "/" + bl.getNam();
            modelBL.addRow(new Object[]{
                bl.getMaBL(), 
                bl.getMaNV(), 
                bl.getTenNV(), 
                kyLuong, 
                df.format(bl.getLuongCoBan()), 
                df.format(bl.getTongPhuCap()), 
                df.format(bl.getTongKhauTru()), 
                df.format(bl.getThucLanh()), 
                bl.getTrangThai()
            });
        }
        capNhatThongKe(danhSach);
    }
    
    public void addEventSearch() {
        btnSearch.addActionListener(e -> {
            String tuKhoa = txtSearch.getText().trim();
            String thang = cbThang.getSelectedItem().toString();
            String nam = cbNam.getSelectedItem().toString();
            String trangThai = cbTrangThai.getSelectedItem().toString();

            List<BangLuong_DTO> ketQua = bus.timKiemVaLoc(tuKhoa, thang, nam, trangThai); 
            hienThiDanhSach(ketQua);
        });
        
        cbThang.addActionListener(e -> btnSearch.doClick());
        cbNam.addActionListener(e -> btnSearch.doClick());
        cbTrangThai.addActionListener(e -> btnSearch.doClick());
    }
    
    public void addEventRefresh() {
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            cbThang.setSelectedIndex(0);
            cbNam.setSelectedIndex(0);
            cbTrangThai.setSelectedIndex(0);
            taiDuLieuLenBang();
        });
    }
    
    public void addEventAdd() {
        btnAdd.addActionListener(e -> {
            BangLuong1_GUI formThem = new BangLuong1_GUI(this); 
            formThem.setVisible(true);
        });
    }
    
    public void addEventEdit() {
        btnEdit.addActionListener(e -> {
            int row = tableBL.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một Bảng lương trong danh sách để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String maBL = tableBL.getValueAt(row, 0).toString();

                List<BangLuong_DTO> listAll = bus.layDanhSachBangLuong();
                BangLuong_DTO blEdit = listAll.stream()
                                              .filter(bl -> bl.getMaBL().equals(maBL))
                                              .findFirst()
                                              .orElse(null);
                
                if (blEdit != null) {
                    BangLuong1_GUI formSua = new BangLuong1_GUI(this, blEdit);
                    formSua.setVisible(true);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi đọc dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    public void addEventDelete() {
        btnDelete.addActionListener(e -> {
            int row = tableBL.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn Bảng lương để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maBL = tableBL.getValueAt(row, 0).toString();
            String tenNV = tableBL.getValueAt(row, 2).toString();
            String kyLuong = tableBL.getValueAt(row, 3).toString();
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Bạn có chắc muốn xóa bảng lương kỳ " + kyLuong + " của nhân viên: " + tenNV + "?", 
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.xoaBangLuong(maBL)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                    btnSearch.doClick(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi DB", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private JPanel taoOThongKe(String tieuDe, Color mauNen, JLabel lblSoTien) {
        JPanel pnBox = new JPanel();
        pnBox.setLayout(new BoxLayout(pnBox, BoxLayout.Y_AXIS)); 
        pnBox.setBackground(mauNen);
        pnBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        
        JLabel lblTitle = new JLabel(tieuDe);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblSoTien.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblSoTien.setForeground(new Color(50, 50, 50));
        lblSoTien.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pnBox.add(lblTitle);
        pnBox.add(Box.createVerticalStrut(10)); 
        pnBox.add(lblSoTien);
        return pnBox;
    }
    
    private void capNhatThongKe(List<BangLuong_DTO> danhSach) {
        BigDecimal tongQuy = BigDecimal.ZERO;
        BigDecimal daThanhToan = BigDecimal.ZERO;
        BigDecimal chuaThanhToan = BigDecimal.ZERO;
        
        for (BangLuong_DTO bl : danhSach) {
            tongQuy = tongQuy.add(bl.getThucLanh());
            
            if (bl.getTrangThai().trim().equalsIgnoreCase("Đã thanh toán")) {
                daThanhToan = daThanhToan.add(bl.getThucLanh());
            } else {
                chuaThanhToan = chuaThanhToan.add(bl.getThucLanh());
            }
        }
        
        lblTongQuyLuong.setText(df.format(tongQuy));
        lblTongDaThanhToan.setText(df.format(daThanhToan));
        lblTongChuaThanhToan.setText(df.format(chuaThanhToan));
    }
}