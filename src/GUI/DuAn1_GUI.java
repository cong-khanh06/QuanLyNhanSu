package GUI;

import BUS.DuAn_BUS;
import DTO.DuAn_DTO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DuAn1_GUI extends JDialog {
    private JTextField txtMaDA, txtTenDA, txtNgayBD, txtNgayKT;
    private JComboBox<String> cbTrangThai;
    private JButton btnLuu, btnHuy;
    
    private DuAn_BUS busDA = new DuAn_BUS();
    private DuAn_GUI parentGUI;
    private boolean isEditMode = false; // Cờ đánh dấu form đang ở chế độ Sửa hay Thêm
    private DateTimeFormatter dtfGUI = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    public DuAn1_GUI(DuAn_GUI parent) {
        this.parentGUI = parent;
        khoiTaoGiaoDien();
        txtMaDA.setText(busDA.taoMaMoi()); 
    }

    
    public DuAn1_GUI(DuAn_GUI parent, DuAn_DTO daEdit) {
        this.parentGUI = parent;
        this.isEditMode = true; // Bật cờ chế độ Sửa
        khoiTaoGiaoDien();
        
        
        setTitle("Sửa Thông Tin Dự Án");
        btnLuu.setText("Cập Nhật");
        
        
        txtMaDA.setText(daEdit.getMaDa());
        txtTenDA.setText(daEdit.getTenDuAn());
        txtNgayBD.setText(daEdit.getNgayBatDau().format(dtfGUI));
        txtNgayKT.setText(daEdit.getNgayKetThuc().format(dtfGUI));
        cbTrangThai.setSelectedItem(daEdit.getTrangThai());
        }

    
    private void khoiTaoGiaoDien() {
        setTitle("Thêm Dự Án Mới");
        setSize(420, 350);
        setLocationRelativeTo(parentGUI);
        setModal(true);
        setLayout(new BorderLayout());

        JPanel pnForm = new JPanel(new GridLayout(5, 2, 10, 20));
        pnForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pnForm.add(new JLabel("Mã Dự Án:"));
        txtMaDA = new JTextField(); 
        txtMaDA.setEditable(false); 
        txtMaDA.setBackground(new Color(230, 230, 230));
        pnForm.add(txtMaDA);

        pnForm.add(new JLabel("Tên Dự Án:"));
        txtTenDA = new JTextField();
        pnForm.add(txtTenDA);

        pnForm.add(new JLabel("Ngày Bắt Đầu:"));
        JPanel pnNgayBD = new JPanel(new BorderLayout());
        txtNgayBD = new JTextField();
        txtNgayBD.setEditable(false);
        txtNgayBD.setBackground(Color.WHITE);
        JButton btnChonNgayBD = new JButton("...");
        btnChonNgayBD.setPreferredSize(new Dimension(40, 0));
        btnChonNgayBD.addActionListener(e -> new CalendarGrid(txtNgayBD).setVisible(true));
        pnNgayBD.add(txtNgayBD, BorderLayout.CENTER);
        pnNgayBD.add(btnChonNgayBD, BorderLayout.EAST);
        pnForm.add(pnNgayBD);

        pnForm.add(new JLabel("Ngày Kết Thúc:"));
        JPanel pnNgayKT = new JPanel(new BorderLayout());
        txtNgayKT = new JTextField();
        txtNgayKT.setEditable(false);
        txtNgayKT.setBackground(Color.WHITE);
        JButton btnChonNgayKT = new JButton("...");
        btnChonNgayKT.setPreferredSize(new Dimension(40, 0));
        btnChonNgayKT.addActionListener(e -> new CalendarGrid(txtNgayKT).setVisible(true));
        pnNgayKT.add(txtNgayKT, BorderLayout.CENTER);
        pnNgayKT.add(btnChonNgayKT, BorderLayout.EAST);
        pnForm.add(pnNgayKT);

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
            String ngayBDStr = txtNgayBD.getText().trim();
            String ngayKTStr = txtNgayKT.getText().trim();
            String trangThai = cbTrangThai.getSelectedItem().toString();
            
            if (ten.isEmpty() || ngayBDStr.isEmpty() || ngayKTStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate ngayBD = LocalDate.parse(ngayBDStr, formatter);
            LocalDate ngayKT = LocalDate.parse(ngayKTStr, formatter);

            if (ngayKT.isBefore(ngayBD)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu!", "Lỗi logic", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DuAn_DTO daObj = new DuAn_DTO(ma, ten, ngayBD, ngayKT, trangThai);
            
            
            boolean thanhCong = false;
            if (isEditMode) {
                thanhCong = busDA.suaDuAn(daObj);
            } else {
                thanhCong = busDA.themDuAn(daObj);
            }
            
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, isEditMode ? "Cập nhật thành công!" : "Thêm thành công!");
                parentGUI.taiDuLieuLenBang(); // Refresh lại danh sách và thống kê
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