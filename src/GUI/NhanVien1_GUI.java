
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.awt.Dimension;
import java.util.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

/**
 *
 * @author khanh
 */
public class NhanVien1_GUI extends JDialog{
    private java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
    JTextField txtTen, txtDiaChi, txtSDT,txtEmail,txtCCCD,txtMaNV;
    DatePicker dpNgaySinh, dpNgayVaoLam;
    JComboBox<NhanVien_DTO.GioiTinh> cbGioiTinh;
    JComboBox<NhanVien_DTO.TrangThaiNhanVien>  cbTrangThai;
    JComboBox<Phongban_DTO> cbPhongBan;
    JComboBox<ChucVu_DTO> cbChucVu;
    JButton  btnLuu,btnHuy,btnChonAnh;
    JSpinner spNgaySinh,spNgayVaoLam;
    JLabel lblAvatar;
    File selectedFile=null;
    NhanVien_BUS bus = new NhanVien_BUS();
    NhanVien_DAO dao=new NhanVien_DAO();
    public NhanVien1_GUI(){
        setTitle("Thêm nhân viên");
        setSize(850,500);
        setModal(true);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); 
        
        
        JPanel pnForm = new JPanel(new GridLayout(0, 4, 15, 15));
        pnForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtTen = new JTextField();
        txtDiaChi = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();
        txtCCCD = new JTextField();
        txtMaNV=new JTextField();
        txtMaNV.setForeground(Color.black);
        txtMaNV.setEditable(false);
        txtMaNV.setBackground(Color.LIGHT_GRAY);
        
        DatePickerSettings settingsNS = new DatePickerSettings();
        settingsNS.setFormatForDatesCommonEra("dd-MM-yyyy");
        dpNgaySinh = new DatePicker(settingsNS);

        DatePickerSettings settingsNVL = new DatePickerSettings();
        settingsNVL.setFormatForDatesCommonEra("dd-MM-yyyy");
        dpNgayVaoLam = new DatePicker(settingsNVL);
        
        cbGioiTinh = new JComboBox<>(NhanVien_DTO.GioiTinh.values());
        cbPhongBan = new JComboBox<>();
        loadComboPhongBan();
        cbChucVu = new JComboBox<>();
        loadComboChucVu();
        cbTrangThai = new JComboBox<>(NhanVien_DTO.TrangThaiNhanVien.values());
        
        pnForm.add(new JLabel("Mã nhân viên"));
        pnForm.add(txtMaNV);
        
        pnForm.add(new JLabel("Họ tên"));
        pnForm.add(txtTen);
        
        pnForm.add(new JLabel("Ngày sinh"));
        pnForm.add(dpNgaySinh);

        pnForm.add(new JLabel("Ngày vào làm"));
        pnForm.add(dpNgayVaoLam);
        
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
        
        JPanel pnAvatar = new JPanel(new BorderLayout(5, 5));
        pnAvatar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0)); 
        lblAvatar = new JLabel("Chưa có ảnh", JLabel.CENTER);
        lblAvatar.setPreferredSize(new Dimension(180, 240)); 
        lblAvatar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btnChonAnh = new JButton("Chọn ảnh");
        pnAvatar.add(lblAvatar, BorderLayout.CENTER);
        pnAvatar.add(btnChonAnh, BorderLayout.SOUTH);
        
        add(pnAvatar, BorderLayout.WEST);
        add(pnForm, BorderLayout.CENTER);
        add(pnBottom, BorderLayout.SOUTH);
        
        loadComboPhongBan(); 
        txtMaNV.setText(bus.taoMaMoiNhat());
        
        addEventChonAnh();
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

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống");
            txtTen.requestFocus();
            return false;
        }
        if (!NhanVien_BUS.isValidPhone(sdt)) {
            JOptionPane.showMessageDialog(this, "SĐT không hợp lệ");
            txtSDT.requestFocus();
            return false;
        }
        if (!email.isEmpty() && !NhanVien_BUS.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ");
            txtEmail.requestFocus();
            return false;
        }
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
                NhanVien_DTO.GioiTinh gioiTinh = (NhanVien_DTO.GioiTinh) cbGioiTinh.getSelectedItem();
                String diaChi = txtDiaChi.getText();
                String sdt = txtSDT.getText();
                String email = txtEmail.getText();
                String cccd = txtCCCD.getText();
                Phongban_DTO pbSelected = (Phongban_DTO) cbPhongBan.getSelectedItem();
                String maPB = pbSelected != null ? pbSelected.getMaphongban() : "";
                ChucVu_DTO cvSelected = (ChucVu_DTO) cbChucVu.getSelectedItem();
                String maCV = cvSelected != null ? cvSelected.getMaCV(): "";
                NhanVien_DTO.TrangThaiNhanVien trangThai = (NhanVien_DTO.TrangThaiNhanVien) cbTrangThai.getSelectedItem();
                
                String avatarPath = null;
                if (selectedFile != null) {
                    try {
                        String newFileName = maNV + "_" + selectedFile.getName();
                        Path targetPath = Paths.get("avatars", newFileName);
                        Files.createDirectories(targetPath.getParent());
                        Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                        avatarPath = targetPath.toString();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi khi lưu file ảnh!");
                    }
                }
                
                LocalDate ngaySinh = dpNgaySinh.getDate();
                LocalDate ngayVaoLam = dpNgayVaoLam.getDate();

                NhanVien_DTO nv = new NhanVien_DTO(
                        maNV, ten, ngaySinh, gioiTinh,
                        diaChi, sdt, email, cccd,
                        ngayVaoLam,trangThai, maPB,maCV,  avatarPath
                );

                if (bus.themNhanVien(nv)) {
                    JOptionPane.showMessageDialog(this,
                            "Thêm nhân viên thành công!");
                    dispose(); 
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
    
    private void addEventChonAnh() {
        btnChonAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh (JPG, PNG)", "jpg", "png", "jpeg"));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
                lblAvatar.setIcon(new ImageIcon(img));
                lblAvatar.setText("");
            }
        });
    }
}
