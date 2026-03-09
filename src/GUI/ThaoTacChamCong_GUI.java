package GUI;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;

import BUS.ChamCong_BUS;
import DTO.NhanVien_DTO;

public class ThaoTacChamCong_GUI extends JPanel {

    private ChamCong_BUS bus = new ChamCong_BUS();

    private JTable tableNV;
    private DefaultTableModel modelNV;

    private JPanel calendarPanel;
    private JButton btnNghi, btnTre, btnTangCa, btnLuu, btnXoa;
    private JComboBox<Integer> cboThang, cboNam;

    private int startDayOffset = 0;

    private JToggleButton[] gioButtons;
    private ButtonGroup gioGroup;
    private int soGioChon = 0;

    private String mode = ""; // lưu control đang được chọn

    private Set<Integer> nghi = new HashSet<>();
    private Set<Integer> tre = new HashSet<>();
    private Set<Integer> tangca = new HashSet<>();

    private String maNVSelected = null;
    private String tenNVSelected = null;

    public ThaoTacChamCong_GUI() {
        initUI();
        loadNhanVien();
    }

    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    private void styleComboBox(JComboBox<?> cbo) {
        cbo.setBackground(Color.WHITE);
        cbo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        cbo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    private void styleActionButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setPreferredSize(new Dimension(90, 30));
    }

    private void resetModeButtonColor() {
        btnNghi.setBackground(Color.WHITE);
        btnTre.setBackground(Color.WHITE);
        btnTangCa.setBackground(Color.WHITE);
    }

    // khởi tạo giao diện
    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        String[] cols = { "STT", "Nhân viên", "Trạng thái", };
        modelNV = new DefaultTableModel(cols, 0);
        tableNV = new JTable(modelNV);
        tableNV.setRowHeight(25);

        JScrollPane sp = new JScrollPane(tableNV);
        sp.setPreferredSize(new Dimension(280, 0));
        add(sp, BorderLayout.WEST);

        tableNV.getSelectionModel().addListSelectionListener(e -> {
            int row = tableNV.getSelectedRow();
            if (row >= 0) {
                String[] arr = modelNV.getValueAt(row, 1).toString().split(" - ");
                maNVSelected = arr[0];
                tenNVSelected = arr[1];
                resetCalendar();
                loadChamCongCu();
                JOptionPane.showMessageDialog(this, "Đang chấm công cho: " + tenNVSelected);
            }
        });

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        btnNghi = new JButton("Nghỉ");
        btnTre = new JButton("Trễ");
        btnTangCa = new JButton("Tăng ca");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu chấm công");

        JPanel gioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        gioButtons = new JToggleButton[5];
        gioGroup = new ButtonGroup();

        for (int i = 1; i <= 5; i++) {

            JToggleButton btn = new JToggleButton(String.valueOf(i));

            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(35, 35));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

            btn.setBackground(Color.WHITE);

            int gio = i;

            btn.addActionListener(e -> {
                soGioChon = gio;

                for (JToggleButton b : gioButtons) {
                    b.setBackground(new Color(0, 150, 255));
                }
            });

            gioGroup.add(btn);
            gioButtons[i - 1] = btn;
            gioPanel.add(btn);
        }
        cboThang = new JComboBox<>();
        cboNam = new JComboBox<>();

        for (int i = 1; i <= 12; i++)
            cboThang.addItem(i);
        int namHienTai = LocalDate.now().getYear();
        for (int i = namHienTai - 10; i <= namHienTai; i++)
            cboNam.addItem(i);

        cboThang.setSelectedItem(LocalDate.now().getMonthValue());
        cboNam.setSelectedItem(LocalDate.now().getYear());

        styleActionButton(btnNghi);
        styleActionButton(btnTre);
        styleActionButton(btnTangCa);
        styleActionButton(btnXoa);

        btnXoa.setBackground(new Color(180, 0, 0));
        btnXoa.setForeground(Color.WHITE);

        styleButton(btnLuu);

        styleComboBox(cboThang);
        styleComboBox(cboNam);

        top.add(new JLabel("Tháng:"));
        top.add(cboThang);
        top.add(new JLabel("Năm:"));
        top.add(cboNam);

        top.add(btnNghi);
        top.add(btnTre);
        top.add(btnTangCa);
        top.add(new JLabel("Số giờ:"));
        top.add(gioPanel);
        top.add(btnXoa);

        add(top, BorderLayout.NORTH);

        calendarPanel = new JPanel();
        add(calendarPanel, BorderLayout.CENTER);
        veLich(31);

        cboThang.addActionListener(e -> doiThang());
        cboNam.addActionListener(e -> doiThang());

        btnLuu.setBackground(new Color(0, 150, 255));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu, BorderLayout.SOUTH);

        btnNghi.addActionListener(e -> {
            mode = "NGHI";
            resetModeButtonColor();
            btnNghi.setBackground(Color.RED);
        });

        btnTre.addActionListener(e -> {
            mode = "TRE";
            resetModeButtonColor();
            btnTre.setBackground(Color.YELLOW);
        });

        btnTangCa.addActionListener(e -> {
            mode = "TANGCA";
            resetModeButtonColor();
            btnTangCa.setBackground(Color.GREEN);
        });

        btnXoa.addActionListener(e -> {
            if (maNVSelected == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
                return;
            }

            int check = JOptionPane.showConfirmDialog(this, 
                    "Bạn có chắc muốn xóa sạch chấm công tháng này của " + tenNVSelected + "?", 
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (check == JOptionPane.YES_OPTION) {
                int thang = (int) cboThang.getSelectedItem();
                int nam = (int) cboNam.getSelectedItem();
                
                boolean success = bus.xoaChamCong(maNVSelected, thang, nam);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công dữ liệu trên hệ thống!");
                    resetCalendar();
                    resetModeButtonColor();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi: Không thể xóa dữ liệu!");
                }
            }
        });

        btnLuu.addActionListener(e -> luu());
    }

    private void loadNhanVien() {
        modelNV.setRowCount(0);
        int stt = 1;

        for (NhanVien_DTO nv : bus.getDanhSachNhanVien()) {

            String nhanVien = nv.getMaNV() + " - " + nv.getHoTen();

            modelNV.addRow(new Object[] { stt++, nhanVien, "" });
        }
    }

    private void resetCalendar() {
        nghi.clear();
        tre.clear();
        tangca.clear();
        gioGroup.clearSelection();
        soGioChon = 0;
        mode = "";
        doiThang();
    }

    private void veLich(int soNgay) {

        calendarPanel.removeAll();
        calendarPanel.setLayout(new GridLayout(0, 7, 5, 5));

        String[] thu = { "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật" };

        for (String t : thu) {
            JLabel lbl = new JLabel(t, JLabel.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            calendarPanel.add(lbl);
        }

        int thang = (int) cboThang.getSelectedItem();
        int nam = (int) cboNam.getSelectedItem();

        LocalDate firstDay = LocalDate.of(nam, thang, 1);
        startDayOffset = firstDay.getDayOfWeek().getValue() - 1;

        for (int i = 0; i < startDayOffset; i++) {
            calendarPanel.add(new JLabel(""));
        }

        for (int i = 1; i <= soNgay; i++) {

            JButton day = new JButton(String.valueOf(i));

            day.setBackground(Color.WHITE);
            day.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            int d = i;

            day.addActionListener(e -> chonNgay(day, d));

            calendarPanel.add(day);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void doiThang() {
        int thang = (int) cboThang.getSelectedItem();
        int nam = (int) cboNam.getSelectedItem();
        int soNgay = YearMonth.of(nam, thang).lengthOfMonth();
        veLich(soNgay);
        nghi.clear();
        tre.clear();
        tangca.clear();
        loadChamCongCu();
    }

    private void chonNgay(JButton btn, int d) {
        if (maNVSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên trước!");
            return;
        }

        if (mode.equals("NGHI")) {
            btn.setBackground(Color.RED);
            nghi.add(d);
        }
        if (mode.equals("TRE")) {
            btn.setBackground(Color.YELLOW);
            tre.add(d);
        }
        if (mode.equals("TANGCA")) {
            btn.setBackground(Color.GREEN);
            tangca.add(d);
        }
    }

    private void luu() {
        if (maNVSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên!");
            return;
        }
        if (soGioChon == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn số giờ!");
            return;
        }

        int thang = (int) cboThang.getSelectedItem();
        int nam = (int) cboNam.getSelectedItem();
        int gio = soGioChon;

        boolean kq = bus.luuChamCong(maNVSelected, thang, nam, gio, nghi, tre, tangca);

        if (kq) {
            JOptionPane.showMessageDialog(this, "Đã lưu chấm công cho: " + tenNVSelected);
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu chấm công!");
        }

    }

    private void loadChamCongCu() {

        if (maNVSelected == null)
            return;

        int thang = (int) cboThang.getSelectedItem();
        int nam = (int) cboNam.getSelectedItem();

        var list = bus.getChiTietChamCong(maNVSelected, thang, nam);

        for (var ct : list) {

            int ngay = ct.getNgay();
            String tt = ct.getTrangThai();

            int index = ngay + startDayOffset + 7;
            if (index < calendarPanel.getComponentCount()) {
                var comp = calendarPanel.getComponent(index);

                if (comp instanceof JButton btn) {
                	if (tt.equals("Nghỉ")) {
                	    btn.setBackground(Color.RED);
                	    nghi.add(ngay);
                	}

                	if (tt.equals("Trễ")) {
                	    btn.setBackground(Color.YELLOW);
                	    tre.add(ngay);
                	}

                	if (tt.equals("Tăng ca")) {
                	    btn.setBackground(Color.GREEN);
                	    tangca.add(ngay);
                	}

                }

            }

        }
    }
}
