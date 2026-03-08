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


public class NhanVien2_GUI extends JPanel {
    private java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
    JTextField txtMa, txtTen, txtDiaChi, txtSDT, txtEmail, txtCCCD;
    JTextField txtNgaySinh, txtNgayVaoLam;

    JComboBox<NhanVien_DTO.GioiTinh> cbGioiTinh;
    JComboBox<NhanVien_DTO.TrangThaiNhanVien> cbTrangThai;
    JComboBox<Phongban_DTO> cbPhongBan;
    JComboBox<ChucVu_DTO> cbChucVu;
    JButton btnSua, btnXoa, btnHuy, btnLuu,btnChonAnh,btnChonNgaySinh,btnChonNgayVaoLam;
    
    JLabel lblAvatar;
    File selectedFile=null;
    String currentAvatarPath=null;
    
    NhanVien_DAO dao = new NhanVien_DAO();
    private NhanVien_GUI parentGUI;

    private boolean isDeleteAllowed = true; 

    // Sửa Constructor thành Không tham số
    public NhanVien2_GUI() {
        initComponents();
        setEditable(false);
    }
    
    public void setParentGUI(NhanVien_GUI parentGUI) {
        this.parentGUI = parentGUI;
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
        txtNgaySinh.setToolTipText("Nhập theo định dạng: dd-MM-yyyy (VD: 30-12-2000)");
        txtNgayVaoLam.setToolTipText("Nhập theo định dạng: dd-MM-yyyy (VD: 15-01-2024)"); 
        
        txtMa.setEditable(false);

        // ===== ComboBox =====
        cbGioiTinh = new JComboBox<>(NhanVien_DTO.GioiTinh.values());
        cbPhongBan = new JComboBox<>();
        loadComboPhongBan();
        
        cbChucVu = new JComboBox<>();
        loadComboChucVu();
        
        cbTrangThai = new JComboBox<>(NhanVien_DTO.TrangThaiNhanVien.values());

        // ===== Buttons =====
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        btnChonNgaySinh = new JButton("📅");
        btnChonNgaySinh.addActionListener(e -> new CalendarGrid(txtNgaySinh).setVisible(true));
        
        btnChonNgayVaoLam = new JButton("📅");
        btnChonNgayVaoLam.addActionListener(e -> new CalendarGrid(txtNgayVaoLam).setVisible(true));
        
        btnLuu.setEnabled(false);
        btnHuy.setEnabled(false);

        // ===== Bố cục Form (Center) =====
        JPanel panelForm = new JPanel(new GridLayout(0, 4, 15, 10)); 
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelForm.add(new JLabel("Mã NV:")); panelForm.add(txtMa);
        panelForm.add(new JLabel("Họ tên:")); panelForm.add(txtTen);
        panelForm.add(new JLabel("Giới tính:")); panelForm.add(cbGioiTinh);
        
        JPanel pnNgaySinh = new JPanel(new BorderLayout());
        pnNgaySinh.add(txtNgaySinh, BorderLayout.CENTER);
        pnNgaySinh.add(btnChonNgaySinh, BorderLayout.EAST);
        panelForm.add(new JLabel("Ngày sinh:")); 
        panelForm.add(pnNgaySinh);
            
        panelForm.add(new JLabel("Địa chỉ:")); panelForm.add(txtDiaChi);
        panelForm.add(new JLabel("SĐT:")); panelForm.add(txtSDT);
        panelForm.add(new JLabel("Email:")); panelForm.add(txtEmail);
        panelForm.add(new JLabel("CCCD:")); panelForm.add(txtCCCD);
        
        JPanel pnNgayVaoLam = new JPanel(new BorderLayout());
        pnNgayVaoLam.add(txtNgayVaoLam, BorderLayout.CENTER);
        pnNgayVaoLam.add(btnChonNgayVaoLam, BorderLayout.EAST);
        panelForm.add(new JLabel("Ngày vào làm:")); 
        panelForm.add(pnNgayVaoLam);
        
        panelForm.add(new JLabel("Phòng ban:")); panelForm.add(cbPhongBan);
        panelForm.add(new JLabel("Chức vụ:")); panelForm.add(cbChucVu);
        panelForm.add(new JLabel("Trạng thái:")); panelForm.add(cbTrangThai);

        // ===== Bố cục Buttons (South) =====
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelButtons.add(btnSua);
        panelButtons.add(btnLuu);
        panelButtons.add(btnHuy);
        panelButtons.add(btnXoa);

        JPanel panelAvatar = new JPanel(new BorderLayout(5, 5));
        panelAvatar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
        lblAvatar = new JLabel("Không có ảnh", JLabel.CENTER);
        lblAvatar.setPreferredSize(new Dimension(150, 200));
        lblAvatar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btnChonAnh = new JButton("Đổi ảnh");
        btnChonAnh.setEnabled(false); // Khóa lúc đầu
        panelAvatar.add(lblAvatar, BorderLayout.CENTER);
        panelAvatar.add(btnChonAnh, BorderLayout.SOUTH);
        
        add(panelAvatar, BorderLayout.WEST);
        add(panelForm, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // ===== Gán Sự Kiện =====
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
                    if (parentGUI != null) parentGUI.taiDuLieuLenBang(); // Load lại bảng
                } else if (status == false) {
                    JOptionPane.showMessageDialog(this, "Đã xóa hoàn toàn nhân viên khỏi hệ thống.");
                    clearForm(); // Làm trống form
                    if (parentGUI != null) parentGUI.taiDuLieuLenBang(); // Load lại bảng
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
            // Khi hủy thì nạp lại thông tin gốc từ DB
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

    // ===== HÀM ĐỂ NHẬN DỮ LIỆU TỪ BẢNG Ở TRÊN =====
    public void hienThiNhanVien(NhanVien_DTO nv) {
        if (nv == null) return;
        
        txtMa.setText(nv.getMaNV());
        txtTen.setText(nv.getHoTen());
        txtDiaChi.setText(nv.getDiaChi());
        txtSDT.setText(nv.getSdt());
        txtEmail.setText(nv.getEmail());
        txtCCCD.setText(nv.getCccd());
        txtNgaySinh.setText(nv.getNgaySinh() != null ? nv.getNgaySinh().format(dtf) : "");
        txtNgayVaoLam.setText(nv.getNgayVaoLam() != null ? nv.getNgayVaoLam().format(dtf) : "");

        cbGioiTinh.setSelectedItem(nv.getGioiTinh());
        
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
        cbTrangThai.setSelectedItem(nv.getTrangThai());
        
        currentAvatarPath = nv.getAvatar();
        selectedFile = null; // Reset file được chọn

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
        
        // Đảm bảo Form đang bị khóa khi mới hiện
        setEditable(false);
        btnSua.setEnabled(isDeleteAllowed);
        btnXoa.setEnabled(isDeleteAllowed);
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
        nv.setGioiTinh((NhanVien_DTO.GioiTinh) cbGioiTinh.getSelectedItem());

        // 2. XỬ LÝ TRẠNG THÁI 
        nv.setTrangThai((NhanVien_DTO.TrangThaiNhanVien) cbTrangThai.getSelectedItem());

        // 3. XỬ LÝ PHÒNG BAN 
        Phongban_DTO pbSelected = (Phongban_DTO) cbPhongBan.getSelectedItem();
        nv.setMaPB(pbSelected != null ? pbSelected.getMaphongban() : "");

        // 4. XỬ LÝ CHỨC VỤ 
        ChucVu_DTO cvSelected = (ChucVu_DTO) cbChucVu.getSelectedItem();
        String maCV = (cvSelected != null) ? cvSelected.getMaCV() : "";
        nv.setMaCV(maCV);
        
        // 5. XỬ LÝ NGÀY THÁNG 
            try {
            String ngaySinhStr = txtNgaySinh.getText().trim();
            String ngayVaoLamStr = txtNgayVaoLam.getText().trim();

            if (!ngaySinhStr.isEmpty()) {
                nv.setNgaySinh(LocalDate.parse(ngaySinhStr, dtf)); // Thêm dtf vào đây
            }
            if (!ngayVaoLamStr.isEmpty()) {
                nv.setNgayVaoLam(LocalDate.parse(ngayVaoLamStr, dtf)); // Thêm dtf vào đây
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi ngày tháng! Vui lòng nhập đúng định dạng: Ngày-Tháng-Năm (VD: 30-12-2000)");
            return null; 
        }
        
        String finalAvatarPath = currentAvatarPath;

        if (selectedFile != null) { // Nếu có chọn ảnh mới
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
        txtNgaySinh.setEditable(enable);
        txtNgayVaoLam.setEditable(enable);
        btnChonNgaySinh.setEnabled(enable);
        btnChonNgayVaoLam.setEnabled(enable);

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

    private JPanel createDateField(JTextField txt) {
        JPanel pDate = new JPanel(new BorderLayout());
        pDate.add(txt, BorderLayout.CENTER);
        JButton btn = new JButton("📅");
        btn.setMargin(new java.awt.Insets(2, 2, 2, 2));
        
        // GỌI CLASS CALENDAR GRID RIÊNG Ở ĐÂY
        btn.addActionListener(e -> {
            new CalendarGrid(txt).setVisible(true);
        });
        
        pDate.add(btn, BorderLayout.EAST);
        return pDate;
    }

    public void setphanquyennut(boolean kq,String quyen)
    {
    	this.isDeleteAllowed = kq;
    	btnXoa.setEnabled(kq);
    	if(quyen.equals("User"))
    	btnSua.setEnabled(kq);
    }
}