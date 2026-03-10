package GUI;

import BUS.BangLuong_BUS;
import BUS.NhanVien_BUS;
import DTO.BangLuong_DTO;
import DTO.NhanVien_DTO;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BangLuong1_GUI extends JDialog {
    private JTextField txtMaBL, txtTenNV, txtLuongCB, txtPhuCap, txtKhauTru, txtThucLanh,txtMaNV;
    private JComboBox<String> cbNhanVien, cbThang, cbNam, cbTrangThai;
    private JButton btnLuu, btnHuy;
    
    private BangLuong_BUS busBL = new BangLuong_BUS();
    private NhanVien_BUS busNV = new NhanVien_BUS(); 
    private BangLuong_GUI parentGUI;
    private boolean isEditMode = false; 
    
    public BangLuong1_GUI(BangLuong_GUI parent) {
        this.parentGUI = parent;
        khoiTaoGiaoDien();
        txtMaBL.setText(busBL.taoMaMoi()); 
        

        LocalDate now = LocalDate.now();
        cbThang.setSelectedItem(String.valueOf(now.getMonthValue()));
        cbNam.setSelectedItem(String.valueOf(now.getYear()));
        
        
        if (cbNhanVien.getItemCount() > 0) {
            capNhatTenNV();
        }
    }

    public BangLuong1_GUI(BangLuong_GUI parent, BangLuong_DTO blEdit) {
        this.parentGUI = parent;
        this.isEditMode = true; 
        khoiTaoGiaoDien();
        setTitle("Sửa Bảng Lương");
        btnLuu.setText("Cập Nhật");
        
        txtMaNV.setEditable(false);
        
        cbNhanVien.setEnabled(false); 
        cbThang.setEnabled(false);
        cbNam.setEnabled(false);
        
        txtMaBL.setText(blEdit.getMaBL());
        
        
        String maNVEdit = blEdit.getMaNV();
        for (int i = 0; i < cbNhanVien.getItemCount(); i++) {
            if (cbNhanVien.getItemAt(i).startsWith(maNVEdit)) {
                cbNhanVien.setSelectedIndex(i);
                break;
            }
        }
        
        txtTenNV.setText(blEdit.getTenNV());
        cbThang.setSelectedItem(String.valueOf(blEdit.getThang()));
        cbNam.setSelectedItem(String.valueOf(blEdit.getNam()));
        
        txtLuongCB.setText(blEdit.getLuongCoBan().toString());
        txtPhuCap.setText(blEdit.getTongPhuCap().toString());
        txtKhauTru.setText(blEdit.getTongKhauTru().toString());
        txtThucLanh.setText(blEdit.getThucLanh().toString());
        cbTrangThai.setSelectedItem(blEdit.getTrangThai());
    }
    
    private void khoiTaoGiaoDien() {
        setTitle("Quản Lý Bảng Lương");
        setSize(500, 500);
        setLocationRelativeTo(parentGUI);
        setModal(true);
        setLayout(new BorderLayout());

        JPanel pnForm = new JPanel(new GridLayout(10, 2, 10, 15));
        pnForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        pnForm.add(new JLabel("Mã Bảng Lương:"));
        txtMaBL = new JTextField(); 
        txtMaBL.setEditable(false); 
        txtMaBL.setBackground(new Color(230, 230, 230));
        pnForm.add(txtMaBL);

        
        pnForm.add(new JLabel("Chọn Nhân Viên:"));
        cbNhanVien = new JComboBox<>();
        List<NhanVien_DTO> dsNV = busNV.layDanhSachNhanVien();
        for (NhanVien_DTO nv : dsNV) {
            
            if (nv.getTrangThai() != null && nv.getTrangThai().name().equals("DangLam")) {
                cbNhanVien.addItem(nv.getMaNV() + " - " + nv.getHoTen());
            }
        }
        pnForm.add(cbNhanVien);
        
        // Sự kiện: Khi chọn 1 nhân viên khác, tự động điền Tên NV xuống Textfield dưới
        cbNhanVien.addActionListener(e -> capNhatTenNV());
        
        pnForm.add(new JLabel("Tên Nhân Viên:"));
        txtTenNV = new JTextField(); 
        txtTenNV.setEditable(false); 
        txtTenNV.setBackground(new Color(230, 230, 230));
        pnForm.add(txtTenNV);

        pnForm.add(new JLabel("Tháng:"));
        cbThang = new JComboBox<>(new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"});
        pnForm.add(cbThang);
        
        pnForm.add(new JLabel("Năm:"));
        cbNam = new JComboBox<>(new String[]{"2024","2025","2026","2027"});
        pnForm.add(cbNam);

        pnForm.add(new JLabel("Lương Cơ Bản (VNĐ):"));
        txtLuongCB = new JTextField("0");
        pnForm.add(txtLuongCB);
        
        pnForm.add(new JLabel("Tổng Phụ Cấp (VNĐ):"));
        txtPhuCap = new JTextField("0");
        pnForm.add(txtPhuCap);
        
        pnForm.add(new JLabel("Tổng Khấu Trừ (VNĐ):"));
        txtKhauTru = new JTextField("0");
        pnForm.add(txtKhauTru);
        
        pnForm.add(new JLabel("THỰC LÃNH (VNĐ):"));
        txtThucLanh = new JTextField("0"); 
        txtThucLanh.setEditable(false); 
        txtThucLanh.setBackground(new Color(200, 255, 200)); 
        txtThucLanh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnForm.add(txtThucLanh);

        pnForm.add(new JLabel("Trạng Thái:"));
        cbTrangThai = new JComboBox<>(new String[]{"Chưa thanh toán", "Đã thanh toán"});
        pnForm.add(cbTrangThai);

        add(pnForm, BorderLayout.CENTER);

        JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLuu = new JButton("Lưu Bảng Lương");
        btnLuu.setBackground(new Color(150, 214, 255));
        btnHuy = new JButton("Hủy Bỏ");
        
        pnButtons.add(btnLuu);
        pnButtons.add(btnHuy);
        add(pnButtons, BorderLayout.SOUTH);

        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> xuLyLuu());
        
        kichHoatTuDongTinhTien();
    }
    

    private void capNhatTenNV() {
        if (cbNhanVien.getSelectedItem() != null) {
            String selectedItem = cbNhanVien.getSelectedItem().toString();
            String[] parts = selectedItem.split(" - ");
            if (parts.length > 1) {
                txtTenNV.setText(parts[1].trim());
            }
        }
    }
    
    private void kichHoatTuDongTinhTien() {
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { tinhThucLanh(); }
            public void removeUpdate(DocumentEvent e) { tinhThucLanh(); }
            public void changedUpdate(DocumentEvent e) { tinhThucLanh(); }
        };
        txtLuongCB.getDocument().addDocumentListener(listener);
        txtPhuCap.getDocument().addDocumentListener(listener);
        txtKhauTru.getDocument().addDocumentListener(listener);
    }
    
    private void tinhThucLanh() {
        try {
            BigDecimal lcb = txtLuongCB.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(txtLuongCB.getText().trim());
            BigDecimal pc = txtPhuCap.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(txtPhuCap.getText().trim());
            BigDecimal kt = txtKhauTru.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(txtKhauTru.getText().trim());
            
            BigDecimal thucLanh = lcb.add(pc).subtract(kt);
            txtThucLanh.setText(thucLanh.toString());
        } catch (Exception ex) {

        }
    }

    private void xuLyLuu() {
        try {
            String maBL = txtMaBL.getText();
            
            
            String maNV = "";
            if (cbNhanVien.getSelectedItem() != null) {
                maNV = cbNhanVien.getSelectedItem().toString().split(" - ")[0].trim();
            }
            
            int thang = Integer.parseInt(cbThang.getSelectedItem().toString());
            int nam = Integer.parseInt(cbNam.getSelectedItem().toString());
            String trangThai = cbTrangThai.getSelectedItem().toString();
            
            if (maNV.isEmpty() || txtThucLanh.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin và kiểm tra lại số tiền!");
                return;
            }

            BigDecimal luongCB = new BigDecimal(txtLuongCB.getText().trim());
            BigDecimal phuCap = new BigDecimal(txtPhuCap.getText().trim());
            BigDecimal khauTru = new BigDecimal(txtKhauTru.getText().trim());
            BigDecimal thucLanh = new BigDecimal(txtThucLanh.getText().trim());

            BangLuong_DTO blObj = new BangLuong_DTO(maBL, luongCB, phuCap, khauTru, thucLanh, trangThai, maNV, thang, nam, "");
            
            boolean thanhCong = isEditMode ? busBL.suaBangLuong(blObj) : busBL.themBangLuong(blObj);
            
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, "Lưu thành công!");
                parentGUI.taiDuLieuLenBang(); 
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lưu thất bại! (Bảng lương cho nhân viên này trong tháng " + thang + " có thể đã tồn tại)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}