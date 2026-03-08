package GUI;

import BUS.HopDong_BUS;
import DTO.HopDong_DTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.awt.event.*;

public class HopDong1_GUI extends JDialog {
    private JTextField txtMaHD, txtLoaiHD, txtNgayBD, txtNgayKT, txtNgayKy, txtLuong, txtMaNV, txtLanKy, txtNgayLenLuong;
    private JComboBox<String> cbTrangThai;
    private JTextArea txtNoiDung;
    private boolean dataChanged = false;
    private boolean isEdit;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private HopDong_BUS bus = new HopDong_BUS();
    private final Color PRIMARY_COLOR = new Color(63, 81, 181);

    public HopDong1_GUI(JFrame parent, HopDong_DTO data) {
        super(parent, (data == null ? "THÊM HỢP ĐỒNG MỚI" : "CẬP NHẬT HỢP ĐỒNG"), true);
        this.isEdit = (data != null);
        setSize(750, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel pMain = new JPanel(new GridBagLayout());
        pMain.setBorder(new EmptyBorder(20, 20, 20, 20));
        pMain.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        initFields();
        if (isEdit) fillForm(data);

        addLabel(pMain, "Mã HD:", 0, 0, gbc);
        gbc.gridx = 1; pMain.add(txtMaHD, gbc);
        addLabel(pMain, "Loại HD:", 0, 2, gbc);
        gbc.gridx = 3; pMain.add(txtLoaiHD, gbc);

        addLabel(pMain, "Bắt đầu:", 1, 0, gbc);
        gbc.gridx = 1; pMain.add(createDatePanel(txtNgayBD), gbc);
        addLabel(pMain, "Kết thúc:", 1, 2, gbc);
        gbc.gridx = 3; pMain.add(createDatePanel(txtNgayKT), gbc);

        addLabel(pMain, "Ngày ký:", 2, 0, gbc);
        gbc.gridx = 1; pMain.add(createDatePanel(txtNgayKy), gbc);
        addLabel(pMain, "Lên lương:", 2, 2, gbc);
        gbc.gridx = 3; pMain.add(createDatePanel(txtNgayLenLuong), gbc);

        addLabel(pMain, "Mức lương:", 3, 0, gbc);
        gbc.gridx = 1; pMain.add(txtLuong, gbc);
        addLabel(pMain, "Trạng thái:", 3, 2, gbc);
        gbc.gridx = 3; pMain.add(cbTrangThai, gbc);

        addLabel(pMain, "Mã NV:", 4, 0, gbc);
        gbc.gridx = 1; pMain.add(txtMaNV, gbc);
        addLabel(pMain, "Lần ký:", 4, 2, gbc);
        gbc.gridx = 3; pMain.add(txtLanKy, gbc);

        addLabel(pMain, "Nội dung:", 5, 0, gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        pMain.add(new JScrollPane(txtNoiDung), gbc);

        add(pMain, BorderLayout.CENTER);

        JPanel pBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        JButton btnSave = createSaveButton(isEdit ? "CẬP NHẬT" : "LƯU LẠI");
        JButton btnExit = new JButton("HỦY BỎ");
        btnExit.setPreferredSize(new Dimension(110, 35));

        btnSave.addActionListener(e -> {
            try {
                HopDong_DTO dto = collectData(); 
                String res = isEdit ? bus.update(dto) : bus.insert(dto);
                
                if (res.toLowerCase().contains("thành công")) { 
                    JOptionPane.showMessageDialog(this, res, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dataChanged = true; 
                    dispose(); 
                } else {
                    // Hiển thị lỗi từ BUS (lỗi validate hoặc lỗi trùng mã)
                    JOptionPane.showMessageDialog(this, res, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Lương và Lần ký phải là con số!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi xử lý: " + ex.getMessage());
            }
        });

        btnExit.addActionListener(e -> dispose());
        pBottom.add(btnSave); pBottom.add(btnExit);
        add(pBottom, BorderLayout.SOUTH);
    }

    private void initFields() {
        txtMaHD = new JTextField(); if(isEdit) txtMaHD.setEditable(false);
        txtLoaiHD = new JTextField(); 
        txtNgayBD = new JTextField(); txtNgayKT = new JTextField();
        txtNgayKy = new JTextField(); txtNgayLenLuong = new JTextField();
        txtLuong = new JTextField(); txtMaNV = new JTextField(); txtLanKy = new JTextField();
        cbTrangThai = new JComboBox<>(new String[]{"HieuLuc", "HetHan", "Huy"});
        txtNoiDung = new JTextArea(4, 15); txtNoiDung.setLineWrap(true);
    }

    private void fillForm(HopDong_DTO d) {
        txtMaHD.setText(d.getMaHD()); 
        txtLoaiHD.setText(d.getLoaiHopDong());
        txtNgayBD.setText(d.getNgayBatDau() != null ? sdf.format(d.getNgayBatDau()) : "");
        txtNgayKT.setText(d.getNgayKetThuc() != null ? sdf.format(d.getNgayKetThuc()) : "");
        txtNgayKy.setText(d.getNgayKy() != null ? sdf.format(d.getNgayKy()) : "");
        txtNgayLenLuong.setText(d.getNgayLenLuongGanNhat() != null ? sdf.format(d.getNgayLenLuongGanNhat()) : "");
        DecimalFormat df = new DecimalFormat("#");
        txtLuong.setText(df.format(d.getMucLuongCoBan())); 
        txtMaNV.setText(d.getMaNV()); 
        txtLanKy.setText(String.valueOf(d.getLanKy())); 
        cbTrangThai.setSelectedItem(d.getTrangThai()); 
        txtNoiDung.setText(d.getNoiDung());
    }

    private void addLabel(JPanel p, String text, int row, int col, GridBagConstraints gbc) {
        gbc.gridx = col; gbc.gridy = row; gbc.gridwidth = 1;
        JLabel lb = new JLabel(text); lb.setFont(new Font("Segoe UI", Font.BOLD, 12));
        p.add(lb, gbc);
    }

    private JPanel createDatePanel(JTextField txt) {
        JPanel p = new JPanel(new BorderLayout()); p.setOpaque(false);
        txt.setPreferredSize(new Dimension(100, 30)); p.add(txt, BorderLayout.CENTER);
        JButton btn = new JButton("📅");
        btn.addActionListener(e -> new CalendarGrid(txt).setVisible(true));
        p.add(btn, BorderLayout.EAST);
        return p;
    }

    private JButton createSaveButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(110, 35));
        return btn;
    }

    private HopDong_DTO collectData() {
        HopDong_DTO hd = new HopDong_DTO();
        hd.setMaHD(txtMaHD.getText().trim()); 
        hd.setLoaiHopDong(txtLoaiHD.getText().trim());
        hd.setNgayBatDau(parseDate(txtNgayBD.getText())); 
        hd.setNgayKetThuc(parseDate(txtNgayKT.getText()));
        hd.setNgayKy(parseDate(txtNgayKy.getText())); 
        hd.setNgayLenLuongGanNhat(parseDate(txtNgayLenLuong.getText()));
        
        String luongStr = txtLuong.getText().trim();
        hd.setMucLuongCoBan(luongStr.isEmpty() ? 0 : Double.parseDouble(luongStr));
        
        hd.setMaNV(txtMaNV.getText().trim()); 
        String lanKyStr = txtLanKy.getText().trim();
        hd.setLanKy(lanKyStr.isEmpty() ? 0 : Integer.parseInt(lanKyStr));
        
        hd.setTrangThai(cbTrangThai.getSelectedItem().toString()); 
        hd.setNoiDung(txtNoiDung.getText().trim());
        return hd;
    }

    private Date parseDate(String s) {
        try { 
            if (s == null || s.trim().isEmpty()) return null;
            return new Date(sdf.parse(s.trim()).getTime()); 
        } catch (Exception e) { 
            return null; 
        }
    }

    public boolean isDataChanged() { return dataChanged; }
}