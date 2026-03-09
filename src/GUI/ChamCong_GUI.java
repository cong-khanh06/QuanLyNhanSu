package GUI;

import BUS.ChamCong_BUS;
import DTO.ChamCongChiTiet_DTO;
import DTO.ChamCong_DTO;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.time.LocalDate;
import java.time.Year;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.List;

// JPanel Danh Sách Chấm Công
public class ChamCong_GUI extends JPanel {

    // bảng dữ liệu và các dữ liệu
    private JTable table;
    private DefaultTableModel model;

    // các nút lọc dữ liệu
    private JTextField txtSearch;
    private JComboBox<String> cboPhong;
    private JComboBox<String> cboGioiTinh;
    private JComboBox<Integer> cboThang;
    private JComboBox<Integer> cboNam;

    private JTable tableChiTiet;
    private DefaultTableModel modelChiTiet;

    // control chấm công chi tiết
    private JButton btnChamCong;

    private ChamCong_BUS bus = new ChamCong_BUS();
    private boolean isAdmin = true; // Mặc định là Admin
    private String currentMaNV = ""; // Lưu mã nhân viên nếu là User

    // Constructor dựng giao diện
    public ChamCong_GUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Tiêu đề
        JLabel lblTitle = new JLabel("Bảng chấm công nhân viên", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel các control
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Tìm kiếm
        txtSearch = new JTextField(18);
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(52, 152, 219));
        btnSearch.setForeground(Color.WHITE);
        // lọc theo phòng ban
        JLabel lblPhong = new JLabel("Phòng ban:");
        cboPhong = new JComboBox<>();
        cboPhong.addItem("Tất cả");
        List<String> dsPhong = bus.getDanhSachPhong();
        for (String p : dsPhong) {
            cboPhong.addItem(p);
        }

        // lọc theo giới tính
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        cboGioiTinh = new JComboBox<>();
        cboGioiTinh.addItem("Tất cả");
        cboGioiTinh.addItem("Nam");
        cboGioiTinh.addItem("Nữ");

        // lọc theo tháng
        JLabel lblThang = new JLabel("Tháng:");
        cboThang = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            cboThang.addItem(i);
        }
        // lọc theo năm
        JLabel lblNam = new JLabel("Năm:");
        cboNam = new JComboBox<>();
        int namHienTai = Year.now().getValue();
        for (int i = 2023; i <= namHienTai; i++) {
            cboNam.addItem(i);
        }
        // mặc định là tháng năm hiện tại
        cboThang.setSelectedItem(LocalDate.now().getMonthValue());
        cboNam.setSelectedItem(LocalDate.now().getYear());

        // control chấm công chi tiết
        btnChamCong = new JButton("Chấm công");
        btnChamCong.setBackground(new Color(46, 204, 113));
        btnChamCong.setForeground(Color.WHITE);

        // Thêm tìm kiếm và các control vào một Panel
        filterPanel.add(txtSearch);
        filterPanel.add(btnSearch);
        filterPanel.add(lblPhong);
        filterPanel.add(cboPhong);
        filterPanel.add(lblGioiTinh);
        filterPanel.add(cboGioiTinh);
        filterPanel.add(lblThang);
        filterPanel.add(cboThang);
        filterPanel.add(lblNam);
        filterPanel.add(cboNam);
        filterPanel.add(btnChamCong);

        // set layout
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(lblTitle, BorderLayout.NORTH);
        northPanel.add(filterPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        // Bảng dữ liệu
        String[] cols = { 
        		"STT", 
        		"Mã chấm công", 
        		"Nhân viên", 
        		"Ngày làm", 
        		"Nghỉ", 
        		"Trễ", 
        		"Tăng ca",
        		"Trạng thái"
        		};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel centerPanel = new JPanel(new BorderLayout());

        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // bảng chi tiết
        String[] colsCT = { "Thời gian", "Trạng thái", "Số giờ" };

        modelChiTiet = new DefaultTableModel(colsCT, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tableChiTiet = new JTable(modelChiTiet);
        tableChiTiet.setRowHeight(26);

        JScrollPane spCT = new JScrollPane(tableChiTiet);
        spCT.setPreferredSize(new java.awt.Dimension(0, 150));

        centerPanel.add(spCT, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // Thêm sự kiện
        btnChamCong.addActionListener(e -> moChamCong());
        btnSearch.addActionListener(e -> loadTable());

        table.getSelectionModel().addListSelectionListener(e -> loadChiTiet());

        cboPhong.addActionListener(e -> loadTable());
        cboGioiTinh.addActionListener(e -> loadTable());
        cboThang.addActionListener(e -> refreshData());
        cboNam.addActionListener(e -> refreshData());

        refreshData();
    }
    private void refreshData() {
        if (isAdmin) {
            loadTable();
        } else {
            loadTableuser(currentMaNV);
        }
    }

    // mở Frame chấm công chi tiết
    private void moChamCong() {

        JFrame f = new JFrame("Chấm công chi tiết");
        f.setSize(950, 600);
        f.setLocationRelativeTo(null);

        ThaoTacChamCong_GUI panel = new ThaoTacChamCong_GUI();

        f.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                loadTable();
            }
        });

        f.add(panel);

        f.setVisible(true);
    }

    private void loadTable() {
        model.setRowCount(0);

        int thang = (int) cboThang.getSelectedItem();
        int nam = (int) cboNam.getSelectedItem();

        String phong = cboPhong.getSelectedItem().toString();
        String gioiTinh = cboGioiTinh.getSelectedItem().toString();
        String keyword = txtSearch.getText().trim();

        if (phong.equals("Tất cả")) {
            phong = "";
        }
        if ("Tất cả".equals(gioiTinh)) {
            gioiTinh = "";
        }

        // gọi BUS để lấy danh sách từ cơ sở dữ liệu
        List<ChamCong_DTO> list = bus.getDanhSachChamCong(thang, nam, phong, gioiTinh, keyword);
        if (list == null)
            return;
        // đổ dữ liệu vào bảng
        int stt = 1;
        for (ChamCong_DTO c : list) {

            String nhanVien = c.getMaNV() + " - " + c.getHoTen();

            int[] tk = bus.getThongKeChamCong(c.getMaChamCong());

            int soNgayNghi = tk[0];
            int soNgayTre = tk[1];
            int soNgayTangCa = tk[2];

            String trangThai = (tk[3] == 1) ? "Đã chấm công" : "Chưa chấm công";
            
            model.addRow(new Object[] {
                    stt++,
                    c.getMaChamCong(),
                    nhanVien,
                    c.getSoNgayLam(),
                    soNgayNghi,
                    soNgayTre,
                    soNgayTangCa,
                    trangThai
            });
        }
    }

    private void loadChiTiet() {

        int row = table.getSelectedRow();
        if (row < 0)
            return;

        modelChiTiet.setRowCount(0);

        String maNV = model.getValueAt(row, 2).toString().split(" - ")[0];

        int thang = (int) cboThang.getSelectedItem();
        int nam = (int) cboNam.getSelectedItem();

        List<ChamCongChiTiet_DTO> list = bus.getChiTietChamCong(maNV, thang, nam);

        int tongNghi = 0;
        int tongTre = 0;
        int tongTangCa = 0;

        for (ChamCongChiTiet_DTO c : list) {

            modelChiTiet.addRow(new Object[] {
                    c.getNgay(),
                    c.getTrangThai(),
                    c.getSoGio()
            });
            if ("Nghỉ".equals(c.getTrangThai()))
                tongNghi++;
            if ("Trễ".equals(c.getTrangThai()))
                tongTre++;
            if ("Tăng ca".equals(c.getTrangThai()))
                tongTangCa++;
        }

        modelChiTiet.addRow(new Object[] { "", "", "" });
        modelChiTiet.addRow(new Object[] { "Tổng nghỉ", tongNghi, "" });
        modelChiTiet.addRow(new Object[] { "Tổng trễ", tongTre, "" });
        modelChiTiet.addRow(new Object[] { "Tổng tăng ca", tongTangCa, "" });
    }
    public void setphanquyenUser(boolean kq,String manv) {
    	this.isAdmin = kq;
        this.currentMaNV = manv;
        txtSearch.setEnabled(kq);
        cboPhong.setEnabled(kq);
        cboGioiTinh.setEnabled(kq);
        btnChamCong.setEnabled(kq); 
        loadTableuser(manv);
    }
    public void loadTableuser(String manv) {
        model.setRowCount(0);
        int thang = (int) cboThang.getSelectedItem();
        int nam = (int) cboNam.getSelectedItem();

        // Gọi hàm lọc dành riêng cho cá nhân
        List<ChamCong_DTO> list = bus.getDanhSachChamCongSVDangNhap(thang, nam,manv);
        
        int stt = 1;
        for (ChamCong_DTO c : list) {
            String nhanVien = c.getMaNV() + " - " + c.getHoTen();

            // Lấy thống kê từ BUS dựa trên mã chấm công
            int[] tk = bus.getThongKeChamCong(c.getMaChamCong());
            int soNgayNghi = tk[0];
            int soNgayTre = tk[1];
            int soNgayTangCa = tk[2];

            String trangThai = (tk[3] == 1) ? "Đã chấm công" : "Chưa chấm công";
            
            model.addRow(new Object[] {
                    stt++,
                    c.getMaChamCong(),
                    nhanVien,
                    c.getSoNgayLam(),
                    soNgayNghi,     // Thay cho c.getSoNgayNghi()
                    soNgayTre,      // Thay cho c.getSoNgayTre()
                    soNgayTangCa,   // Thay cho c.getSoNgayTangCa()
                    trangThai
            });
        }

        // Tự động chọn dòng để hiện chi tiết bên dưới
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }
    }
}
