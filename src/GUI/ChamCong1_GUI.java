package GUI;

import BUS.ChamCong_BUS;
import DTO.ChamCong_DTO;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ChamCong1_GUI extends JDialog {
    private JTextField txtMaCC, txtTangCa;
    private JComboBox<String> cbNhanVien, cbTrangThai;
    private DatePicker dpNgay;
    private TimePicker tpGioVao, tpGioRa;
    private JButton btnLuu, btnHuy;

    private ChamCong_BUS bus = new ChamCong_BUS();
    private ChamCong_GUI parent;
    private boolean isEditMode = false;

    public ChamCong1_GUI(ChamCong_GUI parent, ChamCong_DTO editData) {
        this.parent = parent;
        this.isEditMode = (editData != null);
        
        setTitle(isEditMode ? "Sửa thông tin chấm công" : "Thêm chấm công mới");
        setSize(450, 450);
        setLocationRelativeTo(parent);
        setModal(true);
        setLayout(new BorderLayout());

        getContentPane().setBackground(Color.WHITE);
        
        JPanel pnForm = new JPanel(new GridLayout(7, 2, 10, 15));
        pnForm.setBackground(Color.WHITE); // Nền trắng
        pnForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        pnForm.add(new JLabel("Mã Chấm Công:"));
        txtMaCC = new JTextField();
        txtMaCC.setEditable(false);
        txtMaCC.setBackground(new Color(230, 230, 230));
        pnForm.add(txtMaCC);

        
        pnForm.add(new JLabel("Nhân viên:"));
        cbNhanVien = new JComboBox<>();
        for (String nv : bus.getDanhSachNhanVien()) cbNhanVien.addItem(nv);
        pnForm.add(cbNhanVien);

        
        pnForm.add(new JLabel("Ngày chấm:"));
        dpNgay = new DatePicker();
        pnForm.add(dpNgay);

        
        pnForm.add(new JLabel("Giờ vào ca:"));
        tpGioVao = new TimePicker();
        pnForm.add(tpGioVao);

        
        pnForm.add(new JLabel("Giờ ra ca:"));
        tpGioRa = new TimePicker();
        pnForm.add(tpGioRa);

        
        pnForm.add(new JLabel("Số giờ tăng ca:"));
        txtTangCa = new JTextField("0");
        pnForm.add(txtTangCa);

        
        pnForm.add(new JLabel("Trạng thái:"));
        cbTrangThai = new JComboBox<>(new String[]{"Đúng giờ", "Trễ", "Tăng ca", "Nghỉ", "Nửa ngày"});
        pnForm.add(cbTrangThai);

        add(pnForm, BorderLayout.CENTER);

        JPanel pnBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnBtn.setBackground(Color.WHITE); // Nền trắng
        
        btnLuu = new JButton("Lưu Dữ Liệu");
        btnLuu.putClientProperty("FlatLaf.styleClass", "primary"); // Style xịn
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnHuy = new JButton("Hủy Bỏ");
        btnHuy.putClientProperty("FlatLaf.styleClass", "danger"); // Style xịn
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pnBtn.add(btnLuu);
        pnBtn.add(btnHuy);
        add(pnBtn, BorderLayout.SOUTH);

       
        if (isEditMode) {
            txtMaCC.setText(editData.getMaChamCong());
            for (int i = 0; i < cbNhanVien.getItemCount(); i++) {
                if (cbNhanVien.getItemAt(i).startsWith(editData.getMaNV())) {
                    cbNhanVien.setSelectedIndex(i); break;
                }
            }
            dpNgay.setDate(editData.getNgayTao());
            tpGioVao.setTime(editData.getGioVao());
            tpGioRa.setTime(editData.getGioRa());
            txtTangCa.setText(String.valueOf(editData.getSoGioTangCa()));
            cbTrangThai.setSelectedItem(editData.getTrangThai());
        } else {
            txtMaCC.setText(bus.taoMaMoi());
            dpNgay.setDate(LocalDate.now());
        }

        // Sự kiện
        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> xuLyLuu());
    }

    private void xuLyLuu() {
        try {
            String macc = txtMaCC.getText();
            String manv = cbNhanVien.getSelectedItem().toString().split(" - ")[0];
            LocalDate ngay = dpNgay.getDate();
            LocalTime vao = tpGioVao.getTime();
            LocalTime ra = tpGioRa.getTime();
            float tangCa = Float.parseFloat(txtTangCa.getText().trim());
            String trangThai = cbTrangThai.getSelectedItem().toString();

            if (ngay == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!"); return;
            }

            ChamCong_DTO cc = new ChamCong_DTO(macc, manv, "", ngay, vao, ra, tangCa, trangThai);
            boolean kq = isEditMode ? bus.suaChamCong(cc) : bus.themChamCong(cc);

            if (kq) {
                JOptionPane.showMessageDialog(this, "Lưu dữ liệu thành công!");
                parent.refreshData();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lưu thất bại! Hãy kiểm tra lại thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số giờ tăng ca phải là một số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}