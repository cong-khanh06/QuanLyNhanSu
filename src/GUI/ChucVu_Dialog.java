package GUI;

import BUS.ChucVu_BUS;
import DTO.ChucVu_DTO;
import javax.swing.*;
import java.awt.*;

public class ChucVu_Dialog extends JDialog {
    private JTextField txtMaCV, txtTenCV;
    private JTextArea txtMoTa;
    private JButton btnLuu, btnHuy;

    private ChucVu_BUS bus = new ChucVu_BUS();
    private ChucVu_GUI parentGUI;
    private boolean isEditMode = false;

    public ChucVu_Dialog(ChucVu_GUI parent) {
        this.parentGUI = parent;
        khoiTaoGiaoDien();
        txtMaCV.setText(bus.taoMaMoi()); 
    }

    public ChucVu_Dialog(ChucVu_GUI parent, ChucVu_DTO cvEdit) {
        this.parentGUI = parent;
        this.isEditMode = true;
        khoiTaoGiaoDien();

        setTitle("Sửa Chức Vụ");
        btnLuu.setText("Cập Nhật");

        txtMaCV.setText(cvEdit.getMaCV());
        txtTenCV.setText(cvEdit.getTenCV());
        txtMoTa.setText(cvEdit.getMoTa());
    }

    private void khoiTaoGiaoDien() {
        getContentPane().setBackground(Color.WHITE);
        setTitle("Thêm Chức Vụ");
        setSize(400, 280);
        setLocationRelativeTo(parentGUI);
        setModal(true);
        setLayout(new BorderLayout());

        JPanel pnForm = new JPanel(new GridLayout(3, 2, 10, 15));
        pnForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pnForm.add(new JLabel("Mã Chức Vụ:"));
        txtMaCV = new JTextField();
        txtMaCV.setEditable(false);
        txtMaCV.setBackground(new Color(230, 230, 230));
        pnForm.add(txtMaCV);

        pnForm.add(new JLabel("Tên Chức Vụ:"));
        txtTenCV = new JTextField();
        pnForm.add(txtTenCV);

        pnForm.add(new JLabel("Mô Tả:"));
        txtMoTa = new JTextArea();
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        pnForm.add(scrollMoTa);

        add(pnForm, BorderLayout.CENTER);

        JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        
        btnLuu.putClientProperty("FlatLaf.styleClass", "primary");
        btnHuy.putClientProperty("FlatLaf.styleClass", "danger");
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pnButtons.add(btnLuu);
        pnButtons.add(btnHuy);
        add(pnButtons, BorderLayout.SOUTH);

        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> xuLyLuu());
    }

    private void xuLyLuu() {
        String maCV = txtMaCV.getText();
        String tenCV = txtTenCV.getText().trim();
        String moTa = txtMoTa.getText().trim();
        
        if (tenCV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên chức vụ không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ChucVu_DTO cv = new ChucVu_DTO(maCV, tenCV, moTa);
        boolean success = isEditMode ? bus.suaChucVu(cv) : bus.themChucVu(cv);

        if (success) {
            JOptionPane.showMessageDialog(this, isEditMode ? "Cập nhật thành công!" : "Thêm thành công!");
            parentGUI.taiDuLieuLenBangCV(); 
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thao tác thất bại!", "Lỗi DB", JOptionPane.ERROR_MESSAGE);
        }
    }
}