
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
import java.awt.Cursor;
import java.awt.Font;

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
    public NhanVien1_GUI() {
        setTitle("Thêm nhân viên mới");
        setSize(850, 520);
        setModal(true);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); 
        getContentPane().setBackground(Color.WHITE); // Ép nền trắng
        
        JPanel pnForm = new JPanel(new GridLayout(0, 4, 15, 15));
        pnForm.setBackground(Color.WHITE);
        pnForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);

        txtTen = new JTextField(); txtTen.setFont(inputFont);
        txtDiaChi = new JTextField(); txtDiaChi.setFont(inputFont);
        txtSDT = new JTextField(); txtSDT.setFont(inputFont);
        txtEmail = new JTextField(); txtEmail.setFont(inputFont);
        txtCCCD = new JTextField(); txtCCCD.setFont(inputFont);
        
        txtMaNV = new JTextField();
        txtMaNV.setEditable(false);
        txtMaNV.setBackground(new Color(241, 245, 249)); // Xám cực nhạt
        txtMaNV.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        DatePickerSettings settingsNS = new DatePickerSettings();
        settingsNS.setFormatForDatesCommonEra("dd-MM-yyyy");
        dpNgaySinh = new DatePicker(settingsNS); dpNgaySinh.setFont(inputFont);

        DatePickerSettings settingsNVL = new DatePickerSettings();
        settingsNVL.setFormatForDatesCommonEra("dd-MM-yyyy");
        dpNgayVaoLam = new DatePicker(settingsNVL); dpNgayVaoLam.setFont(inputFont);
        
        cbGioiTinh = new JComboBox<>(NhanVien_DTO.GioiTinh.values()); cbGioiTinh.setFont(inputFont);
        cbPhongBan = new JComboBox<>(); loadComboPhongBan(); cbPhongBan.setFont(inputFont);
        cbChucVu = new JComboBox<>(); loadComboChucVu(); cbChucVu.setFont(inputFont);
        cbTrangThai = new JComboBox<>(NhanVien_DTO.TrangThaiNhanVien.values()); cbTrangThai.setFont(inputFont);
        
        JLabel[] labels = {
            new JLabel("Mã nhân viên:"), new JLabel("Họ tên:"), new JLabel("Ngày sinh:"), new JLabel("Ngày vào làm:"),
            new JLabel("Giới tính:"), new JLabel("Địa chỉ:"), new JLabel("SĐT:"), new JLabel("Email:"),
            new JLabel("CCCD:"), new JLabel("Phòng ban:"), new JLabel("Chức vụ:"), new JLabel("Trạng thái:")
        };
        for(JLabel l : labels) l.setFont(labelFont);

        pnForm.add(labels[0]); pnForm.add(txtMaNV);
        pnForm.add(labels[1]); pnForm.add(txtTen);
        pnForm.add(labels[2]); pnForm.add(dpNgaySinh);
        pnForm.add(labels[3]); pnForm.add(dpNgayVaoLam);
        pnForm.add(labels[4]); pnForm.add(cbGioiTinh);
        pnForm.add(labels[5]); pnForm.add(txtDiaChi);
        pnForm.add(labels[6]); pnForm.add(txtSDT);
        pnForm.add(labels[7]); pnForm.add(txtEmail);
        pnForm.add(labels[8]); pnForm.add(txtCCCD);
        pnForm.add(labels[9]); pnForm.add(cbPhongBan);
        pnForm.add(labels[10]); pnForm.add(cbChucVu);
        pnForm.add(labels[11]); pnForm.add(cbTrangThai);

        btnLuu = new JButton("Lưu"); btnLuu.putClientProperty("FlatLaf.styleClass", "primary");
        btnHuy = new JButton("Hủy"); btnHuy.putClientProperty("FlatLaf.styleClass", "danger");
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR)); btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnBottom.setBackground(Color.WHITE);
        pnBottom.add(btnLuu); pnBottom.add(btnHuy);
        
        JPanel pnAvatar = new JPanel(new BorderLayout(5, 5));
        pnAvatar.setBackground(Color.WHITE);
        pnAvatar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0)); 
        
        lblAvatar = new JLabel("Chưa có ảnh", JLabel.CENTER);
        lblAvatar.setPreferredSize(new Dimension(150, 200)); 
        lblAvatar.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 3, 3, 2, false));
        
        btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.putClientProperty("FlatLaf.styleClass", "");
        btnChonAnh.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnAvatar.add(lblAvatar, BorderLayout.CENTER);
        pnAvatar.add(btnChonAnh, BorderLayout.SOUTH);
        
        add(pnAvatar, BorderLayout.WEST);
        add(pnForm, BorderLayout.CENTER);
        add(pnBottom, BorderLayout.SOUTH);
        
        loadComboPhongBan(); 
        txtMaNV.setText(bus.taoMaMoiNhat());
        
        addEventChonAnh(); addEventClose(); addEventLuu();
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
