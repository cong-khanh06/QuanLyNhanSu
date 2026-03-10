package GUI;

import BUS.DuAn_BUS;
import DTO.DuAn_DTO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

public class DuAn1_GUI extends JDialog {
    private JTextField txtMaDA, txtTenDA, txtNguoiQuanLy;
    private DatePicker dpNgayBD, dpNgayKT;
    private JComboBox<String> cbTrangThai;
    private JButton btnLuu, btnHuy;
    
    private DuAn_BUS busDA = new DuAn_BUS();
    private DuAn_GUI parentGUI;
    private boolean isEditMode = false; 
    private DateTimeFormatter dtfGUI = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    public DuAn1_GUI(DuAn_GUI parent) {
        this.parentGUI = parent;
        khoiTaoGiaoDien();
        txtMaDA.setText(busDA.taoMaMoi()); 
    }

    
    public DuAn1_GUI(DuAn_GUI parent, DuAn_DTO daEdit) {
        this.parentGUI = parent;
        this.isEditMode = true; 
        khoiTaoGiaoDien();
        
        
        setTitle("Sửa Thông Tin Dự Án");
        btnLuu.setText("Cập Nhật");
        
        
        txtMaDA.setText(daEdit.getMaDa());
        txtTenDA.setText(daEdit.getTenDuAn());
        txtNguoiQuanLy.setText(daEdit.getNguoiQuanLy());
        dpNgayBD.setDate(daEdit.getNgayBatDau());
        dpNgayKT.setDate(daEdit.getNgayKetThuc());
        cbTrangThai.setSelectedItem(daEdit.getTrangThai());
        }

    
    private void khoiTaoGiaoDien() {
        setTitle("Thêm Dự Án Mới");
        setSize(420, 350);
        setLocationRelativeTo(parentGUI);
        setModal(true);
        setLayout(new BorderLayout());

        JPanel pnForm = new JPanel(new GridLayout(6, 2, 10, 20));
        pnForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pnForm.add(new JLabel("Mã Dự Án:"));
        txtMaDA = new JTextField(); 
        txtMaDA.setEditable(false); 
        txtMaDA.setBackground(new Color(230, 230, 230));
        pnForm.add(txtMaDA);

        pnForm.add(new JLabel("Tên Dự Án:"));
        txtTenDA = new JTextField();
        pnForm.add(txtTenDA);

        pnForm.add(new JLabel("Người quản lý:"));
        txtNguoiQuanLy=new JTextField();
        pnForm.add(txtNguoiQuanLy);        
        pnForm.add(new JLabel("Ngày Bắt Đầu:"));
        DatePickerSettings setBD = new DatePickerSettings();
        setBD.setFormatForDatesCommonEra("dd-MM-yyyy");
        dpNgayBD = new DatePicker(setBD);
        pnForm.add(dpNgayBD);

        pnForm.add(new JLabel("Ngày Kết Thúc:"));
        DatePickerSettings setKT = new DatePickerSettings();
        setKT.setFormatForDatesCommonEra("dd-MM-yyyy");
        dpNgayKT = new DatePicker(setKT);
        pnForm.add(dpNgayKT);

        pnForm.add(new JLabel("Trạng Thái:"));
        cbTrangThai = new JComboBox<>(new String[]{"Chuẩn bị", "Đang thực hiện", "Hoàn thành"});
        pnForm.add(cbTrangThai);

        add(pnForm, BorderLayout.CENTER);

        JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLuu = new JButton("Lưu Dự Án");
        btnLuu.setBackground(new Color(150, 214, 255));
        btnHuy = new JButton("Hủy Bỏ");
        
        pnButtons.add(btnLuu);
        pnButtons.add(btnHuy);
        add(pnButtons, BorderLayout.SOUTH);

        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> xuLyLuuDuAn());
    }

    private void xuLyLuuDuAn() {
        try {
            String ma = txtMaDA.getText();
            String ten = txtTenDA.getText().trim();
            String nguoiQuanLy=txtNguoiQuanLy.getText().trim();
            LocalDate ngayBD = dpNgayBD.getDate();
            LocalDate ngayKT = dpNgayKT.getDate();
            String trangThai = cbTrangThai.getSelectedItem().toString();
            
            if (ten.isEmpty() || ngayBD == null || ngayKT == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin và chọn ngày!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (ngayKT.isBefore(ngayBD)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu!", "Lỗi logic", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DuAn_DTO daObj = new DuAn_DTO(ma, ten, trangThai, nguoiQuanLy, ngayBD, ngayKT);
            
            
            boolean thanhCong = false;
            if (isEditMode) {
                thanhCong = busDA.suaDuAn(daObj);
            } else {
                thanhCong = busDA.themDuAn(daObj);
            }
            
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, isEditMode ? "Cập nhật thành công!" : "Thêm thành công!");
                parentGUI.taiDuLieuLenBang(); 
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thao tác thất bại!", "Lỗi DB", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}