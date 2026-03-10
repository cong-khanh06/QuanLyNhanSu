package GUI;

import BUS.UngLuong_BUS;
import DTO.UngLuong_DTO;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

public class UngLuong1_GUI extends JDialog {
    private JTextField txtMaUL, txtMaBL, txtSoTien, txtLyDo;
    private DatePicker dpNgayUng;
    private JComboBox<String> cbTrangThai;
    private JButton btnLuu, btnHuy;
    
    private UngLuong_BUS busUL = new UngLuong_BUS();
    private UngLuong_GUI parentGUI;
    private boolean isEditMode = false; 
    
    public UngLuong1_GUI(UngLuong_GUI parent) {
        this.parentGUI = parent;
        khoiTaoGiaoDien();
        txtMaUL.setText(busUL.taoMaMoi()); 
    }

    public UngLuong1_GUI(UngLuong_GUI parent, UngLuong_DTO ulEdit) {
        this.parentGUI = parent;
        this.isEditMode = true; 
        khoiTaoGiaoDien();
        
        setTitle("Sửa Thông Tin Ứng Lương");
        btnLuu.setText("Cập Nhật");
        
        txtMaUL.setText(ulEdit.getMaUL());
        txtMaBL.setText(ulEdit.getMaBL());
        txtSoTien.setText(ulEdit.getSoTien().toString());
        txtLyDo.setText(ulEdit.getLyDo());
        dpNgayUng.setDate(ulEdit.getNgayUng());
        cbTrangThai.setSelectedItem(ulEdit.getTrangThai());
    }
    
    private void khoiTaoGiaoDien() {
        setTitle("Thêm Phiếu Ứng Lương Mới");
        setSize(420, 350);
        setLocationRelativeTo(parentGUI);
        setModal(true);
        setLayout(new BorderLayout());

        JPanel pnForm = new JPanel(new GridLayout(6, 2, 10, 20));
        pnForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pnForm.add(new JLabel("Mã Ứng Lương:"));
        txtMaUL = new JTextField(); 
        txtMaUL.setEditable(false); 
        txtMaUL.setBackground(new Color(230, 230, 230));
        pnForm.add(txtMaUL);

        pnForm.add(new JLabel("Mã Bảng Lương:"));
        txtMaBL = new JTextField();
        pnForm.add(txtMaBL);

        pnForm.add(new JLabel("Ngày Ứng:"));
        DatePickerSettings setNgay = new DatePickerSettings();
        setNgay.setFormatForDatesCommonEra("dd-MM-yyyy");
        dpNgayUng = new DatePicker(setNgay);
        pnForm.add(dpNgayUng);

        pnForm.add(new JLabel("Số Tiền:"));
        txtSoTien = new JTextField();
        pnForm.add(txtSoTien);
        
        pnForm.add(new JLabel("Lý Do:"));
        txtLyDo = new JTextField();
        pnForm.add(txtLyDo);

        pnForm.add(new JLabel("Trạng Thái:"));
        cbTrangThai = new JComboBox<>(new String[]{"Chờ duyệt", "Đã duyệt", "Từ chối"});
        pnForm.add(cbTrangThai);

        add(pnForm, BorderLayout.CENTER);

        JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLuu = new JButton("Lưu");
        btnLuu.setBackground(new Color(150, 214, 255));
        btnHuy = new JButton("Hủy Bỏ");
        
        pnButtons.add(btnLuu);
        pnButtons.add(btnHuy);
        add(pnButtons, BorderLayout.SOUTH);

        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> xuLyLuuUngLuong());
    }

    private void xuLyLuuUngLuong() {
        try {
            String maUL = txtMaUL.getText();
            String maBL = txtMaBL.getText().trim();
            String lyDo = txtLyDo.getText().trim();
            LocalDate ngayUng = dpNgayUng.getDate();
            String trangThai = cbTrangThai.getSelectedItem().toString();
            
            if (maBL.isEmpty() || ngayUng == null || txtSoTien.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ Mã Bảng Lương, Ngày Ứng và Số Tiền!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            BigDecimal soTien = new BigDecimal(txtSoTien.getText().trim());

            UngLuong_DTO ulObj = new UngLuong_DTO(maUL, maBL, lyDo, trangThai, ngayUng, soTien);
            
            boolean thanhCong = isEditMode ? busUL.suaUngLuong(ulObj) : busUL.themUngLuong(ulObj);
            
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, isEditMode ? "Cập nhật thành công!" : "Thêm thành công!");
                parentGUI.taiDuLieuLenBang(); 
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thao tác thất bại!", "Lỗi DB", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ! Chỉ nhập số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}