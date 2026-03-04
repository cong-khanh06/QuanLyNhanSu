/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import DAO.NhanVien_DAO;
import DTO.NhanVien_DTO;
import javax.swing.JComboBox;
import java.time.LocalDate;
import DTO.Phongban_DTO;
import DTO.ChucVu_DTO;
import java.util.List;

public class NhanVien2_GUI extends JPanel {
    JTextField txtMa, txtTen, txtDiaChi, txtSDT, txtEmail, txtCCCD;
    JTextField txtNgaySinh, txtNgayVaoLam;

    JComboBox<String> cbGioiTinh, cbTrangThai;
    JComboBox<Phongban_DTO> cbPhongBan;
    JComboBox<ChucVu_DTO> cbChucVu;
    JButton btnSua, btnXoa, btnHuy, btnLuu;
    NhanVien_DAO dao = new NhanVien_DAO();
    // Sửa Constructor thành Không tham số
    public NhanVien2_GUI() {
        initComponents();
        setEditable(false);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), 
                "Chi tiết nhân viên"
        ));

        // ===== TextField =====
        txtMa = new JTextField();
        txtTen = new JTextField();
        txtDiaChi = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();
        txtCCCD = new JTextField();
        txtNgaySinh = new JTextField();
        txtNgayVaoLam = new JTextField();

        txtMa.setEditable(false);

        // ===== ComboBox =====
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cbPhongBan = new JComboBox<>();
        loadComboPhongBan();
        
        cbChucVu = new JComboBox<>();
        loadComboChucVu();
        
        cbTrangThai = new JComboBox<>(new String[]{"Đang Làm", "Tạm Nghỉ", "Nghỉ Việc"});

        // ===== Buttons =====
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        
        btnLuu.setEnabled(false);
        btnHuy.setEnabled(false);

        // ===== Bố cục Form (Center) =====
        JPanel panelForm = new JPanel(new GridLayout(0, 4, 15, 10)); // 4 cột cho gọn, đỡ bị dài ngoẵng
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelForm.add(new JLabel("Mã NV:")); panelForm.add(txtMa);
        panelForm.add(new JLabel("Họ tên:")); panelForm.add(txtTen);
        panelForm.add(new JLabel("Giới tính:")); panelForm.add(cbGioiTinh);
        panelForm.add(new JLabel("Ngày sinh:")); panelForm.add(txtNgaySinh);
        panelForm.add(new JLabel("Địa chỉ:")); panelForm.add(txtDiaChi);
        panelForm.add(new JLabel("SĐT:")); panelForm.add(txtSDT);
        panelForm.add(new JLabel("Email:")); panelForm.add(txtEmail);
        panelForm.add(new JLabel("CCCD:")); panelForm.add(txtCCCD);
        panelForm.add(new JLabel("Ngày vào làm:")); panelForm.add(txtNgayVaoLam);
        panelForm.add(new JLabel("Phòng ban:")); panelForm.add(cbPhongBan);
        panelForm.add(new JLabel("Chức vụ:")); panelForm.add(cbChucVu);
        panelForm.add(new JLabel("Trạng thái:")); panelForm.add(cbTrangThai);

        // ===== Bố cục Buttons (South) =====
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelButtons.add(btnSua);
        panelButtons.add(btnLuu);
        panelButtons.add(btnHuy);
        panelButtons.add(btnXoa);

        add(panelForm, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // ===== Gán Sự Kiện =====
//        btnXoa.addActionListener(e -> {
//            if(txtMa.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa!");
//                return;
//            }
//            int confirm = JOptionPane.showConfirmDialog(
//                    this,
//                    "Bạn có chắc muốn xóa nhân viên " + txtTen.getText() + "?",
//                    "Xác nhận",
//                    JOptionPane.YES_NO_OPTION
//            );
//
//            if (confirm == JOptionPane.YES_OPTION) {
//                if (dao.deleteNhanVien(txtMa.getText())) {
//                    JOptionPane.showMessageDialog(this, "Xóa thành công");
//                    // Xóa xong thì xóa trắng form
//                    clearForm();
//                    
//                } else {
//                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
//                }
//            }
//        });
        
        btnSua.addActionListener(e -> {
            if(txtMa.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để sửa!");
                return;
            }
            setEditable(true);
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
            btnLuu.setEnabled(true);
            btnHuy.setEnabled(true);
        });
        
        btnLuu.addActionListener(e -> {
            NhanVien_DTO nvNew = getDataFromForm();

            if (nvNew == null) {
                return; 
            }

            if (dao.updateNhanVien(nvNew)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                setEditable(false);
                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);
                btnLuu.setEnabled(false);
                btnHuy.setEnabled(false);
                // Lưu ý: Cần gọi NhanVien_GUI cập nhật lại bảng ở đây
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Vui lòng kiểm tra Console!");
            }
        });
        
        btnHuy.addActionListener(e -> {
            // Khi hủy thì nạp lại thông tin gốc từ DB
            NhanVien_DTO nvCu = dao.getNhanVienById(txtMa.getText());
            hienThiNhanVien(nvCu);
            
            setEditable(false);
            btnSua.setEnabled(true);
            btnXoa.setEnabled(true);
            btnLuu.setEnabled(false);
            btnHuy.setEnabled(false);
        });
    }

    // ===== HÀM ĐỂ NHẬN DỮ LIỆU TỪ BẢNG Ở TRÊN =====
    public void hienThiNhanVien(NhanVien_DTO nv) {
        if (nv == null) return;
        
        txtMa.setText(nv.getMaNV());
        txtTen.setText(nv.getHoTen());
        txtDiaChi.setText(nv.getDiaChi());
        txtSDT.setText(nv.getSdt());
        txtEmail.setText(nv.getEmail());
        txtCCCD.setText(nv.getCccd());
        txtNgaySinh.setText(nv.getNgaySinh() != null ? nv.getNgaySinh().toString() : "");
        txtNgayVaoLam.setText(nv.getNgayVaoLam() != null ? nv.getNgayVaoLam().toString() : "");

        cbGioiTinh.setSelectedItem(nv.getGioiTinh().equals("Nu") ? "Nữ" : "Nam");
        
        // Chọn ComboBox Phòng Ban
        for (int i = 0; i < cbPhongBan.getItemCount(); i++) {
            if (cbPhongBan.getItemAt(i).getMaphongban().equals(nv.getMaPB())) {
                cbPhongBan.setSelectedIndex(i);
                break;
            }
        }
        
        // Chọn ComboBox Chức Vụ
        for (int i = 0; i < cbChucVu.getItemCount(); i++) {
            if (cbChucVu.getItemAt(i).getMaCV().equals(nv.getMaCV())) {
                cbChucVu.setSelectedIndex(i);
                break;
            }
        }
        
        // Chọn ComboBox Trạng Thái
        if(nv.getTrangThai() != null) {
            switch(nv.getTrangThai()) {
                case "DangLam": cbTrangThai.setSelectedItem("Đang Làm"); break;
                case "TamNghi": cbTrangThai.setSelectedItem("Tạm Nghỉ"); break;
                case "NghiViec": cbTrangThai.setSelectedItem("Nghỉ Việc"); break;
            }
        }
        
        // Đảm bảo Form đang bị khóa khi mới hiện
        setEditable(false);
        btnSua.setEnabled(true);
        btnXoa.setEnabled(true);
        btnLuu.setEnabled(false);
        btnHuy.setEnabled(false);
    }
    
    private void clearForm() {
        txtMa.setText(""); txtTen.setText(""); txtDiaChi.setText("");
        txtSDT.setText(""); txtEmail.setText(""); txtCCCD.setText("");
        txtNgaySinh.setText(""); txtNgayVaoLam.setText("");
        cbGioiTinh.setSelectedIndex(0); cbTrangThai.setSelectedIndex(0);
        if(cbPhongBan.getItemCount() > 0) cbPhongBan.setSelectedIndex(0);
        if(cbChucVu.getItemCount() > 0) cbChucVu.setSelectedIndex(0);
    }
    
    private NhanVien_DTO getDataFromForm() {
        NhanVien_DTO nv = new NhanVien_DTO();

        nv.setMaNV(txtMa.getText());
        nv.setHoTen(txtTen.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setSdt(txtSDT.getText());
        nv.setEmail(txtEmail.getText());
        nv.setCccd(txtCCCD.getText());

        // 1. XỬ LÝ GIỚI TÍNH 
        String gioiTinhHienThi = cbGioiTinh.getSelectedItem().toString();
        if (gioiTinhHienThi.equals("Nữ")) {
            nv.setGioiTinh("Nu"); 
        } else {
            nv.setGioiTinh("Nam");
        }

        // 2. XỬ LÝ TRẠNG THÁI 
        String trangThaiHienThi = cbTrangThai.getSelectedItem().toString();
        if (trangThaiHienThi.equals("Đang Làm")) nv.setTrangThai("DangLam");
        else if (trangThaiHienThi.equals("Tạm Nghỉ")) nv.setTrangThai("TamNghi");
        else if (trangThaiHienThi.equals("Nghỉ Việc")) nv.setTrangThai("NghiViec");

        // 3. XỬ LÝ PHÒNG BAN 
        Phongban_DTO pbSelected = (Phongban_DTO) cbPhongBan.getSelectedItem();
        nv.setMaPB(pbSelected != null ? pbSelected.getMaphongban() : "");

        // 4. XỬ LÝ CHỨC VỤ 
        ChucVu_DTO cvSelected = (ChucVu_DTO) cbChucVu.getSelectedItem();
        String maCV = (cvSelected != null) ? cvSelected.getMaCV() : "";
        nv.setMaCV(maCV);
        
        // 5. XỬ LÝ NGÀY THÁNG 
        try {
            nv.setNgaySinh(LocalDate.parse(txtNgaySinh.getText().trim()));
            nv.setNgayVaoLam(LocalDate.parse(txtNgayVaoLam.getText().trim()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi ngày tháng! Vui lòng nhập đúng định dạng: Năm-Tháng-Ngày (VD: 2000-12-30)");
            return null; 
        }

        return nv;
    }
    
    private void setEditable(boolean enable) {
        txtTen.setEditable(enable);
        txtDiaChi.setEditable(enable);
        txtSDT.setEditable(enable);
        txtEmail.setEditable(enable);
        txtCCCD.setEditable(enable);
        txtNgaySinh.setEditable(enable);
        txtNgayVaoLam.setEditable(enable);

        cbGioiTinh.setEnabled(enable);
        cbPhongBan.setEnabled(enable);
        cbChucVu.setEnabled(enable);
        cbTrangThai.setEnabled(enable);
    }
    
    private void loadComboPhongBan() {
        List<Phongban_DTO> list = dao.layDanhSachPB();
        cbPhongBan.removeAllItems();
        for (Phongban_DTO pb : list) {
            cbPhongBan.addItem(pb);
        }
    }
    
    private void loadComboChucVu(){
        List<ChucVu_DTO> list=dao.layDanhSachCV();
        cbChucVu.removeAllItems();
        for (ChucVu_DTO cv:list){
            cbChucVu.addItem(cv);
        }
    }
}