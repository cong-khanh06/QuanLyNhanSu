package GUI;

import BUS.BangCap_BUS;
import BUS.NhanVien_BUS; 
import DTO.BangCap_DTO;
import DTO.NhanVien_DTO;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class BangCap_Dialog extends JDialog {
    private JTextField txtMaBC, txtTenBC, txtNoiCap;
    private JComboBox<String> cbMaNV; 
    private DatePicker dpNgayCap;
    private JButton btnLuu, btnHuy;

    private BangCap_BUS bus = new BangCap_BUS();
    private BangCap_GUI parentGUI;
    private boolean isEditMode = false;

    public BangCap_Dialog(BangCap_GUI parent) {
        this.parentGUI = parent;
        khoiTaoGiaoDien();
        txtMaBC.setText(bus.taoMaMoi()); 
    }

    public BangCap_Dialog(BangCap_GUI parent, BangCap_DTO bcEdit) {
        this.parentGUI = parent;
        this.isEditMode = true;
        khoiTaoGiaoDien();

        setTitle("Sửa Bằng Cấp");
        btnLuu.setText("Cập Nhật");

        txtMaBC.setText(bcEdit.getMaBC());
        txtTenBC.setText(bcEdit.getTenBC());
        txtNoiCap.setText(bcEdit.getNoiCap());
        dpNgayCap.setDate(bcEdit.getNgayCap());
        
        String maNVCanTim = bcEdit.getMaNV();
        for (int i = 0; i < cbMaNV.getItemCount(); i++) {
            if (cbMaNV.getItemAt(i).startsWith(maNVCanTim + " -")) {
                cbMaNV.setSelectedIndex(i);
                break;
            }
        }
    }

    private void khoiTaoGiaoDien() {
        setTitle("Thêm Bằng Cấp");
        setSize(420, 350);
        setLocationRelativeTo(parentGUI);
        setModal(true);
        setLayout(new BorderLayout());

        JPanel pnForm = new JPanel(new GridLayout(5, 2, 10, 15));
        pnForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pnForm.add(new JLabel("Mã Bằng Cấp:"));
        txtMaBC = new JTextField();
        txtMaBC.setEditable(false);
        txtMaBC.setBackground(new Color(230, 230, 230));
        pnForm.add(txtMaBC);

        pnForm.add(new JLabel("Tên Bằng Cấp:"));
        txtTenBC = new JTextField();
        pnForm.add(txtTenBC);

        pnForm.add(new JLabel("Nơi Cấp:"));
        txtNoiCap = new JTextField();
        pnForm.add(txtNoiCap);

        pnForm.add(new JLabel("Ngày Cấp:"));
        DatePickerSettings settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("dd-MM-yyyy");
        dpNgayCap = new DatePicker(settings);
        pnForm.add(dpNgayCap);

        pnForm.add(new JLabel("Nhân Viên:"));
        cbMaNV = new JComboBox<>();
        loadDanhSachNhanVienVaoCB(); 
        pnForm.add(cbMaNV);

        add(pnForm, BorderLayout.CENTER);

        JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");

        pnButtons.add(btnLuu);
        pnButtons.add(btnHuy);
        add(pnButtons, BorderLayout.SOUTH);

        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> xuLyLuu());
    }

    private void loadDanhSachNhanVienVaoCB() {
        NhanVien_BUS nvBUS = new NhanVien_BUS(); 
        List<NhanVien_DTO> dsNhanVien = nvBUS.layDanhSachNhanVien(); 
        
        for (NhanVien_DTO nv : dsNhanVien) {
            if (nv.getTrangThai() == NhanVien_DTO.TrangThaiNhanVien.DangLam) {
                cbMaNV.addItem(nv.getMaNV() + " - " + nv.getHoTen());
            }
        }
    }

    private void xuLyLuu() {
        String maBC = txtMaBC.getText();
        String tenBC = txtTenBC.getText().trim();
        String noiCap = txtNoiCap.getText().trim();
        LocalDate ngayCap = dpNgayCap.getDate();

        if (tenBC.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên bằng cấp không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cbMaNV.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chưa có nhân viên nào để chọn!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedItem = cbMaNV.getSelectedItem().toString();
        String maNV = selectedItem.split(" - ")[0];

        BangCap_DTO bc = new BangCap_DTO(maBC, tenBC, noiCap, maNV, ngayCap);
        boolean success;

        if (isEditMode) {
            success = bus.suaBangCap(bc);
        } else {
            success = bus.themBangCap(bc);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, isEditMode ? "Cập nhật thành công!" : "Thêm thành công!");
            parentGUI.taiDuLieuLenBang(); 
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thao tác thất bại!", "Lỗi DB", JOptionPane.ERROR_MESSAGE);
        }
    }
}