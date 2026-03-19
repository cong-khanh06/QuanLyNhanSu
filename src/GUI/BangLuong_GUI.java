package GUI;

import BUS.BangLuong_BUS;
import BUS.HopDong_BUS;
import BUS.ChucVu_BUS;
import BUS.ChiTietBaoHiem_BUS;
import DTO.BangLuong_DTO;
import GUI.button.ButtonToolBar;
import BUS.ExcelExporter;
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
    JButton btnSearch, btnAdd, btnEdit, btnDelete, btnRefresh,btnDown;
    JComboBox<String> cbThang, cbNam, cbTrangThai; 
    JLabel lblTitle;
    
    BangLuong_BUS bus = new BangLuong_BUS();
    HopDong_BUS bushd=new HopDong_BUS();
    ChucVu_BUS buscv=new ChucVu_BUS();
    ChiTietBaoHiem_BUS busctbh=new ChiTietBaoHiem_BUS();
    JLabel lblTongQuyLuong, lblTongDaThanhToan, lblTongChuaThanhToan; 
    DecimalFormat df = new DecimalFormat("#,### VNĐ");
    private boolean check=true;
    private String manhanvien="";
    public BangLuong_GUI() {
        // NỀN TỔNG THỂ XÁM NHẠT
        setBackground(new Color(226, 232, 240)); 
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        Font modernFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ================= PHẦN HEADER & TOOLBAR (Card Trắng) =================
        pnSearchBL = new JPanel(new BorderLayout());
        pnSearchBL.setBackground(Color.WHITE);
        // Viền Xanh Đậm (Indigo) ở trên cùng
        pnSearchBL.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(79, 70, 229)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnHeader.setBackground(Color.WHITE);
        lblTitle = new JLabel("Quản Lý Bảng Lương");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 41, 59));
        pnHeader.add(lblTitle);
        
        pnToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); 
        pnToolBar.setBackground(Color.WHITE);
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(180, 36));
        txtSearch.setFont(modernFont);
        txtSearch.setToolTipText("Tìm mã NV, tên NV, mã BL...");
        
        btnSearch = createFlatButton("Tìm", "primary");
        btnSearch.setPreferredSize(new Dimension(80, 36));

        cbThang = new JComboBox<>(new String[]{"Tất cả", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
        cbThang.setPreferredSize(new Dimension(90, 36)); cbThang.setFont(modernFont);
        
        cbNam = new JComboBox<>(new String[]{"Tất cả", "2024", "2025", "2026", "2027"});
        cbNam.setPreferredSize(new Dimension(90, 36)); cbNam.setFont(modernFont);

        cbTrangThai = new JComboBox<>(new String[]{"Tất cả", "Chưa thanh toán", "Đã thanh toán"});
        cbTrangThai.setPreferredSize(new Dimension(140, 36)); cbTrangThai.setFont(modernFont);

        btnAdd = createFlatButton("Thêm Mới", "primary");
        btnEdit = createFlatButton("Sửa", "");     
        btnDelete = createFlatButton("Xóa", "danger");
        btnRefresh = createFlatButton("Tải Lại", "");
        btnDown = createFlatButton("Tải xuống", "success");

        pnToolBar.add(txtSearch); pnToolBar.add(btnSearch);
        pnToolBar.add(new JLabel("Tháng:")); pnToolBar.add(cbThang);
        pnToolBar.add(new JLabel("Năm:")); pnToolBar.add(cbNam);
        pnToolBar.add(cbTrangThai); 
        
        pnToolBar.add(btnAdd); pnToolBar.add(btnEdit);   
        pnToolBar.add(btnDelete); pnToolBar.add(btnRefresh); pnToolBar.add(btnDown);
        
        pnSearchBL.add(pnHeader, BorderLayout.NORTH);
        pnSearchBL.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearchBL, BorderLayout.NORTH);
        
        // ================= PHẦN BẢNG DỮ LIỆU =================
        String[] colsBL = {
            "Mã BL", "Mã NV", "Tên Nhân Viên", "Kỳ Lương", "Lương CB", "Phụ Cấp", "Khấu Trừ", "THỰC LÃNH", "Trạng Thái"
        };
        modelBL = new DefaultTableModel(colsBL, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableBL = new JTable(modelBL);
        styleTable(tableBL);
        
        JScrollPane scrollPane = new JScrollPane(tableBL);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        JPanel pnTableCard = new JPanel(new BorderLayout());
        pnTableCard.setBackground(Color.WHITE);
        pnTableCard.add(scrollPane, BorderLayout.CENTER);
        add(pnTableCard, BorderLayout.CENTER);
        
        // ================= PHẦN THỐNG KÊ (Card Đẹp) =================
        JPanel pnThongKe = new JPanel(new GridLayout(1, 3, 20, 0)); 
        pnThongKe.setBackground(new Color(226, 232, 240)); 
        pnThongKe.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        lblTongQuyLuong = new JLabel("0 VNĐ"); lblTongDaThanhToan = new JLabel("0 VNĐ"); lblTongChuaThanhToan = new JLabel("0 VNĐ");
        
        pnThongKe.add(taoOThongKe("Tổng Quỹ Lương (Kết quả lọc)", new Color(245, 158, 11), lblTongQuyLuong));        
        pnThongKe.add(taoOThongKe("Đã Thanh Toán", new Color(16, 185, 129), lblTongDaThanhToan)); 
        pnThongKe.add(taoOThongKe("Còn Nợ (Chưa thanh toán)", new Color(239, 68, 68), lblTongChuaThanhToan));       
        
        add(pnThongKe, BorderLayout.SOUTH);

        btnDown.addActionListener(e -> ExcelExporter.exportJTableToExcel(tableBL));
        addEventRefresh(); addEventSearch(); addEventAdd(); addEventEdit(); addEventDelete();
        
        taiDuLieuLenBang();
    }

    // --- CÁC HÀM UI ---
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

    private JPanel taoOThongKe(String tieuDe, Color accentColor, JLabel lblSoTien) {
        JPanel pnBox = new JPanel(new BorderLayout());
        pnBox.setBackground(Color.WHITE);
        pnBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accentColor),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel lblTitle = new JLabel(tieuDe);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(new Color(100, 116, 139)); 
        
        lblSoTien.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblSoTien.setForeground(new Color(30, 41, 59));
        
        pnBox.add(lblTitle, BorderLayout.NORTH);
        pnBox.add(lblSoTien, BorderLayout.SOUTH);
        return pnBox;
    }
    
    public void taiDuLieuLenBang() {
    	if (!check) { 
            hienThiDanhSach(bus.getdanhsachuser(manhanvien));
        } else {
            hienThiDanhSach(bus.layDanhSachBangLuong());
        }
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
        	String tuKhoa;
            if (!check) {
                tuKhoa = manhanvien; 
            } else {
                tuKhoa = txtSearch.getText().trim();
            }            
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
    public void setphanquyenUser(boolean kq,String manv)
    {
    	this.check=kq;
    	this.manhanvien=manv;
    	btnAdd.setVisible(kq);
    	btnDelete.setVisible(kq);
    	btnEdit.setVisible(kq);
    	
    	taiDuLieuLenBang();
    	
    }
    public void setphanquyenManager(boolean kq)
    {
    	btnDelete.setVisible(kq);
    	taiDuLieuLenBang();
    }
}