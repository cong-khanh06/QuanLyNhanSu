/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import BUS.NhanVien_BUS;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import DTO.NhanVien_DTO;
import java.time.LocalDate;
import java.time.ZoneId;
import DTO.Phongban_DTO;
import DAO.NhanVien_DAO;
import DTO.ChucVu_DTO;
import java.util.List;
/**
 *
 * @author khanh
 */
public class NhanVien1_GUI extends JDialog{
    JTextField txtTen, txtDiaChi, txtSDT,txtEmail,txtCCCD,txtMaNV;
    JComboBox<String> cbGioiTinh, cbTrangThai;
    JComboBox<Phongban_DTO> cbPhongBan;
    JComboBox<ChucVu_DTO> cbChucVu;
    JButton  btnLuu,btnHuy;
    JSpinner spNgaySinh,spNgayVaoLam;
    NhanVien_BUS bus = new NhanVien_BUS();
    NhanVien_DAO dao=new NhanVien_DAO();
    public NhanVien1_GUI(){
        setTitle("Thêm nhân viên");
        setSize(500,400);
        setModal(true);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); 
        
        
        JPanel pnForm=new JPanel(new GridLayout(12,2,10,10));
        pnForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        txtTen = new JTextField();
        txtDiaChi = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();
        txtCCCD = new JTextField();
        txtMaNV=new JTextField();
        txtMaNV.setForeground(Color.black);
        txtMaNV.setEditable(false);
        txtMaNV.setBackground(Color.LIGHT_GRAY);
        
        spNgaySinh=new JSpinner(new SpinnerDateModel());
        spNgaySinh.setEditor(new JSpinner.DateEditor(spNgaySinh,"dd/MM/yyyy"));
        spNgayVaoLam=new JSpinner(new SpinnerDateModel());
        spNgayVaoLam.setEditor(new JSpinner.DateEditor(spNgayVaoLam,"dd/MM/yyyy"));

        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cbPhongBan = new JComboBox<>();
        loadComboPhongBan();
        cbChucVu = new JComboBox<>();
        loadComboChucVu();
        cbTrangThai = new JComboBox<>(new String[]{"Đang Làm", "Nghỉ Việc","Tạm Nghỉ"});
        
        pnForm.add(new JLabel("Mã nhân viên"));
        pnForm.add(txtMaNV);
        
        pnForm.add(new JLabel("Họ tên"));
        pnForm.add(txtTen);
        
        pnForm.add(new JLabel("Ngày sinh"));
        pnForm.add(spNgaySinh);

        pnForm.add(new JLabel("Ngày vào làm"));
        pnForm.add(spNgayVaoLam);
        
        pnForm.add(new JLabel("Giới tính"));
        pnForm.add(cbGioiTinh);

        pnForm.add(new JLabel("Địa chỉ"));
        pnForm.add(txtDiaChi);

        pnForm.add(new JLabel("SĐT"));
        pnForm.add(txtSDT);
        
        pnForm.add(new JLabel("Email"));
        pnForm.add(txtEmail);

        pnForm.add(new JLabel("CCCD"));
        pnForm.add(txtCCCD);
        
        pnForm.add(new JLabel("Phòng ban"));
        pnForm.add(cbPhongBan);

        pnForm.add(new JLabel("Chức vụ"));
        pnForm.add(cbChucVu);

        pnForm.add(new JLabel("Trạng thái"));
        pnForm.add(cbTrangThai);

        btnLuu = new JButton("Lưu");
        btnHuy=new JButton("Hủy");

        JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnBottom.add(btnLuu);
        pnBottom.add(btnHuy);

        add(pnForm, BorderLayout.CENTER);
        add(pnBottom, BorderLayout.SOUTH);
        
        loadComboPhongBan(); 
        txtMaNV.setText(bus.taoMaMoiNhat());
        
        addEventClose();
        addEventLuu();
    }
    
    public void addEventClose(){
        btnHuy.addActionListener(e ->{
            this.setVisible(false);
        });
    }
    
    public void setMaNV(String manv){
        txtMaNV.setText(manv);
    }
    
    private boolean validateInput() {
        String ten = txtTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String email = txtEmail.getText().trim();
        String cccd = txtCCCD.getText().trim();

        // Họ tên
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống");
            txtTen.requestFocus();
            return false;
        }

        // SĐT
        if (!NhanVien_BUS.isValidPhone(sdt)) {
            JOptionPane.showMessageDialog(this, "SĐT không hợp lệ");
            txtSDT.requestFocus();
            return false;
        }

        // Email
        if (!email.isEmpty() && !NhanVien_BUS.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ");
            txtEmail.requestFocus();
            return false;
        }

        // CCCD
        if (!NhanVien_BUS.isValidCCCD(cccd)) {
            JOptionPane.showMessageDialog(this, "CCCD phải gồm 12 chữ số");
            txtCCCD.requestFocus();
            return false;
        }

        return true;
    }
    
    public void addEventLuu(){
        btnLuu.addActionListener(e ->{
            if (!validateInput()) return;
            try {
                String maNV = txtMaNV.getText();
                String ten = txtTen.getText();
                String gioiTinh = cbGioiTinh.getSelectedItem().toString();
                String diaChi = txtDiaChi.getText();
                String sdt = txtSDT.getText();
                String email = txtEmail.getText();
                String cccd = txtCCCD.getText();
                Phongban_DTO pbSelected = (Phongban_DTO) cbPhongBan.getSelectedItem();
                String maPB = pbSelected != null ? pbSelected.getMaphongban() : "";
                ChucVu_DTO cvSelected = (ChucVu_DTO) cbChucVu.getSelectedItem();
                String maCV = cvSelected != null ? cvSelected.getMaCV(): "";
                String trangThaiHienThi = cbTrangThai.getSelectedItem().toString();
                String trangThai = "";

                if (trangThaiHienThi.equals("Đang Làm")) {
                    trangThai = "DangLam";
                } else if (trangThaiHienThi.equals("Tạm Nghỉ")) {
                    trangThai = "TamNghi";
                } else if (trangThaiHienThi.equals("Nghỉ Việc")) {
                    trangThai = "NghiViec";
                }

                // Lấy ngày từ JSpinner
                Date d1 = (Date) spNgaySinh.getValue();
                Date d2 = (Date) spNgayVaoLam.getValue();

                LocalDate ngaySinh = d1.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                LocalDate ngayVaoLam = d2.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                NhanVien_DTO nv = new NhanVien_DTO(
                        maNV, ten, gioiTinh, ngaySinh,
                        diaChi, sdt, email, cccd,
                        ngayVaoLam, maPB, maCV, trangThai
                );

                if (bus.themNhanVien(nv)) {
                    JOptionPane.showMessageDialog(this,
                            "Thêm nhân viên thành công!");
                    dispose(); // đóng form
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Dữ liệu không hợp lệ!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi: " + ex.getMessage());
            }
        });
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
//