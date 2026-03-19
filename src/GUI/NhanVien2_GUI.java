
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
import java.awt.Dimension;
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


public class NhanVien2_GUI extends JPanel {
    private java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
    JTextField txtMa, txtTen, txtDiaChi, txtSDT, txtEmail, txtCCCD;
    DatePicker dpNgaySinh,dpNgayVaoLam;

    JComboBox<NhanVien_DTO.GioiTinh> cbGioiTinh;
    JComboBox<NhanVien_DTO.TrangThaiNhanVien> cbTrangThai;
    JComboBox<Phongban_DTO> cbPhongBan;
    JComboBox<ChucVu_DTO> cbChucVu;
    JButton btnSua, btnXoa, btnHuy, btnLuu,btnChonAnh;
    
    JLabel lblAvatar;
    File selectedFile=null;
    String currentAvatarPath=null;
    
    NhanVien_DAO dao = new NhanVien_DAO();
    private NhanVien_GUI parentGUI;

    private boolean isDeleteAllowed = true; 

    public NhanVien2_GUI() {
        initComponents();
        setEditable(false);
    }
    
    public void setParentGUI(NhanVien_GUI parentGUI) {
        this.parentGUI = parentGUI;
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        // Điểm nhấn viền Xanh Ngọc
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(16, 185, 129)), 
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Chi Tiết Nhân Viên", 
                0, 0, new Font("Segoe UI", Font.BOLD, 16), new Color(30, 41, 59))
        ));

        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        txtMa = new JTextField(); txtMa.setEditable(false); txtMa.setBackground(new Color(241, 245, 249));
        txtTen = new JTextField(); txtTen.setFont(inputFont);
        txtDiaChi = new JTextField(); txtDiaChi.setFont(inputFont);
        txtSDT = new JTextField(); txtSDT.setFont(inputFont);
        txtEmail = new JTextField(); txtEmail.setFont(inputFont);
        txtCCCD = new JTextField(); txtCCCD.setFont(inputFont);
        
        DatePickerSettings settingsNS = new DatePickerSettings(); settingsNS.setFormatForDatesCommonEra("dd-MM-yyyy");
        dpNgaySinh = new DatePicker(settingsNS); dpNgaySinh.setFont(inputFont);

        DatePickerSettings settingsNVL = new DatePickerSettings(); settingsNVL.setFormatForDatesCommonEra("dd-MM-yyyy");
        dpNgayVaoLam = new DatePicker(settingsNVL); dpNgayVaoLam.setFont(inputFont);

        cbGioiTinh = new JComboBox<>(NhanVien_DTO.GioiTinh.values()); cbGioiTinh.setFont(inputFont);
        cbPhongBan = new JComboBox<>(); loadComboPhongBan(); cbPhongBan.setFont(inputFont);
        cbChucVu = new JComboBox<>(); loadComboChucVu(); cbChucVu.setFont(inputFont);
        cbTrangThai = new JComboBox<>(NhanVien_DTO.TrangThaiNhanVien.values()); cbTrangThai.setFont(inputFont);

        btnSua = new JButton("Sửa"); btnSua.putClientProperty("FlatLaf.styleClass", ""); 
        btnXoa = new JButton("Xóa"); btnXoa.putClientProperty("FlatLaf.styleClass", "danger"); 
        btnLuu = new JButton("Lưu"); btnLuu.putClientProperty("FlatLaf.styleClass", "primary"); 
        btnHuy = new JButton("Hủy"); btnHuy.putClientProperty("FlatLaf.styleClass", "danger");
        
        btnSua.setCursor(new Cursor(Cursor.HAND_CURSOR)); btnXoa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR)); btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLuu.setEnabled(false); btnHuy.setEnabled(false);

        JPanel panelForm = new JPanel(new GridLayout(0, 4, 15, 10)); 
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        JLabel[] labels = {
            new JLabel("Mã NV:"), new JLabel("Họ tên:"), new JLabel("Giới tính:"), new JLabel("Ngày sinh:"),
            new JLabel("Địa chỉ:"), new JLabel("SĐT:"), new JLabel("Email:"), new JLabel("CCCD:"),
            new JLabel("Ngày vào làm:"), new JLabel("Phòng ban:"), new JLabel("Chức vụ:"), new JLabel("Trạng thái:")
        };
        for(JLabel l : labels) l.setFont(labelFont);

        panelForm.add(labels[0]); panelForm.add(txtMa);
        panelForm.add(labels[1]); panelForm.add(txtTen);
        panelForm.add(labels[2]); panelForm.add(cbGioiTinh);
        panelForm.add(labels[3]); panelForm.add(dpNgaySinh);
        panelForm.add(labels[4]); panelForm.add(txtDiaChi);
        panelForm.add(labels[5]); panelForm.add(txtSDT);
        panelForm.add(labels[6]); panelForm.add(txtEmail);
        panelForm.add(labels[7]); panelForm.add(txtCCCD);
        panelForm.add(labels[8]); panelForm.add(dpNgayVaoLam);
        panelForm.add(labels[9]); panelForm.add(cbPhongBan);
        panelForm.add(labels[10]); panelForm.add(cbChucVu);
        panelForm.add(labels[11]); panelForm.add(cbTrangThai);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelButtons.setBackground(Color.WHITE);
        panelButtons.add(btnSua); panelButtons.add(btnLuu); panelButtons.add(btnHuy); panelButtons.add(btnXoa);

        JPanel panelAvatar = new JPanel(new BorderLayout(5, 5));
        panelAvatar.setBackground(Color.WHITE);
        panelAvatar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
        
        lblAvatar = new JLabel("Không có ảnh", JLabel.CENTER);
        lblAvatar.setPreferredSize(new Dimension(150, 200));
        lblAvatar.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 3, 3, 2, false));
        
        btnChonAnh = new JButton("Đổi ảnh");
        btnChonAnh.putClientProperty("FlatLaf.styleClass", "primary");
        btnChonAnh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChonAnh.setEnabled(false); 
        
        panelAvatar.add(lblAvatar, BorderLayout.CENTER);
        panelAvatar.add(btnChonAnh, BorderLayout.SOUTH);
        
        add(panelAvatar, BorderLayout.WEST);
        add(panelForm, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        btnXoa.addActionListener(e -> {
            String maNV = txtMa.getText();
            if(maNV.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa!");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn xóa nhân viên " + txtTen.getText() + "?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean status = dao.DeleteNhanVien(maNV);
                
                if (status == true) {
                    JOptionPane.showMessageDialog(this, "Nhân viên này có dữ liệu Lương/Hợp đồng liên quan.\nHệ thống đã tự động chuyển trạng thái thành 'Nghỉ Việc' để bảo toàn lịch sử.");
                    if (parentGUI != null) parentGUI.taiDuLieuLenBang(); 
                } else if (status == false) {
                    JOptionPane.showMessageDialog(this, "Đã xóa hoàn toàn nhân viên khỏi hệ thống.");
                    clearForm(); 
                    if (parentGUI != null) parentGUI.taiDuLieuLenBang(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại. Vui lòng kiểm tra Console!");
                }
            }
        });
        
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
                
                if(parentGUI!=null){
                    parentGUI.taiDuLieuLenBang();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Vui lòng kiểm tra Console!");
            }
        });
        
        btnHuy.addActionListener(e -> {
            NhanVien_DTO nvCu = dao.getNhanVienById(txtMa.getText());
            hienThiNhanVien(nvCu);
            
            setEditable(false);
            btnSua.setEnabled(true);
            btnXoa.setEnabled(true);
            btnLuu.setEnabled(false);
            btnHuy.setEnabled(false);
        });
        
        btnChonAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
                lblAvatar.setIcon(new ImageIcon(img));
                lblAvatar.setText("");
            }
        });
    }

    public void hienThiNhanVien(NhanVien_DTO nv) {
        if (nv == null) return;
        
        txtMa.setText(nv.getMaNV());
        txtTen.setText(nv.getHoTen());
        txtDiaChi.setText(nv.getDiaChi());
        txtSDT.setText(nv.getSdt());
        txtEmail.setText(nv.getEmail());
        txtCCCD.setText(nv.getCccd());
        dpNgaySinh.setDate(nv.getNgaySinh());
        dpNgayVaoLam.setDate(nv.getNgayVaoLam());

        cbGioiTinh.setSelectedItem(nv.getGioiTinh());
        
        for (int i = 0; i < cbPhongBan.getItemCount(); i++) {
            if (cbPhongBan.getItemAt(i).getMaphongban().equals(nv.getMaPB())) {
                cbPhongBan.setSelectedIndex(i);
                break;
            }
        }
        
        for (int i = 0; i < cbChucVu.getItemCount(); i++) {
            if (cbChucVu.getItemAt(i).getMaCV().equals(nv.getMaCV())) {
                cbChucVu.setSelectedIndex(i);
                break;
            }
        }
        
        cbTrangThai.setSelectedItem(nv.getTrangThai());
        
        currentAvatarPath = nv.getAvatar();
        selectedFile = null; 

        if (currentAvatarPath != null && !currentAvatarPath.isEmpty()) {
            File imgFile = new File(currentAvatarPath);
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
                lblAvatar.setIcon(new ImageIcon(img));
                lblAvatar.setText("");
            } else {
                lblAvatar.setIcon(null);
                lblAvatar.setText("Ảnh bị lỗi/xóa");
            }
        } else {
            lblAvatar.setIcon(null);
            lblAvatar.setText("Không có ảnh");
        }
        
        setEditable(false);
        btnSua.setEnabled(isDeleteAllowed);
        btnXoa.setEnabled(isDeleteAllowed);
        btnLuu.setEnabled(false);
        btnHuy.setEnabled(false);
    }
    
    private void clearForm() {
        txtMa.setText(""); txtTen.setText(""); txtDiaChi.setText("");
        txtSDT.setText(""); txtEmail.setText(""); txtCCCD.setText("");
        dpNgaySinh.clear(); dpNgayVaoLam.clear(); 
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

        nv.setGioiTinh((NhanVien_DTO.GioiTinh) cbGioiTinh.getSelectedItem());

        nv.setTrangThai((NhanVien_DTO.TrangThaiNhanVien) cbTrangThai.getSelectedItem());

        Phongban_DTO pbSelected = (Phongban_DTO) cbPhongBan.getSelectedItem();
        nv.setMaPB(pbSelected != null ? pbSelected.getMaphongban() : "");

        ChucVu_DTO cvSelected = (ChucVu_DTO) cbChucVu.getSelectedItem();
        String maCV = (cvSelected != null) ? cvSelected.getMaCV() : "";
        nv.setMaCV(maCV);
        
        nv.setNgaySinh(dpNgaySinh.getDate());
        nv.setNgayVaoLam(dpNgayVaoLam.getDate());
        
        String finalAvatarPath = currentAvatarPath;

        if (selectedFile != null) { 
            try {
                String newFileName = nv.getMaNV() + "_" + selectedFile.getName();
                Path targetPath = Paths.get("avatars", newFileName);
                Files.createDirectories(targetPath.getParent());
                Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                finalAvatarPath = targetPath.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        nv.setAvatar(finalAvatarPath);
        return nv;
    }
    
    private void setEditable(boolean enable) {
        txtTen.setEditable(enable);
        txtDiaChi.setEditable(enable);
        txtSDT.setEditable(enable);
        txtEmail.setEditable(enable);
        txtCCCD.setEditable(enable);
        dpNgaySinh.setEnabled(enable);
        dpNgayVaoLam.setEnabled(enable);

        cbGioiTinh.setEnabled(enable);
        cbPhongBan.setEnabled(enable);
        cbChucVu.setEnabled(enable);
        cbTrangThai.setEnabled(enable);
        btnChonAnh.setEnabled(enable);
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

    public void setphanquyennut(boolean kq,String quyen)
    {
    	this.isDeleteAllowed = kq;
    	btnXoa.setEnabled(kq);
    	if(quyen.equals("User"))
    	btnSua.setEnabled(kq);
    }
}