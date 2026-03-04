package GUI;

import BUS.HopDong_BUS;
import DTO.HopDong_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HopDong_GUI extends JPanel {

    private JTextField txtMaHD, txtLoaiHD, txtNgayBD, txtNgayKT,
            txtNgayKy, txtLuong, txtMaNV,
            txtLanKy, txtNgayLenLuong, txtSearch;
    

    private JComboBox<String> cbTrangThai;
    private JTextArea txtNoiDung;
    private JTable table;
    private DefaultTableModel model;
    private HopDong_BUS bus = new HopDong_BUS();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public HopDong_GUI() {
        setLayout(new BorderLayout());

        JPanel panelInput = new JPanel(new GridBagLayout());
        panelInput.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "THÔNG TIN CHI TIẾT HỢP ĐỒNG", 0, 0, 
                new Font("Arial", Font.BOLD, 14), Color.BLUE));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        initInputFields(); 
        setupInputGrid(panelInput, gbc);

        JScrollPane scrollInput = new JScrollPane(panelInput);
        scrollInput.setPreferredSize(new Dimension(0, 350)); 
        add(scrollInput, BorderLayout.NORTH);
        
        setupTable();
        setupActionButtons();
        loadData();
    }

    private void initInputFields() {
        txtMaHD = new JTextField(10);
        txtLoaiHD = new JTextField(10);
        txtNgayBD = new JTextField(10);
        txtNgayKT = new JTextField(10);
        txtNgayKy = new JTextField(10);
        txtNgayLenLuong = new JTextField(10);
        txtLuong = new JTextField(10);
        
        String[] rules = {"HieuLuc", "HetHan", "Huy"};
        cbTrangThai = new JComboBox<>(rules);
        cbTrangThai.setBackground(Color.WHITE);

        txtMaNV = new JTextField(10);
        txtLanKy = new JTextField(10);
        txtNoiDung = new JTextArea(3, 10);
        txtNoiDung.setLineWrap(true);
        txtSearch = new JTextField(12);
    }

    private void setupInputGrid(JPanel p, GridBagConstraints gbc) {
        addComponent(p, "Mã Hợp Đồng:", txtMaHD, 0, 0, gbc);
        addComponent(p, "Loại Hợp Đồng:", txtLoaiHD, 0, 1, gbc);
        addDateComponent(p, "Ngày Bắt Đầu:", txtNgayBD, 1, 0, gbc);
        addDateComponent(p, "Ngày Kết Thúc:", txtNgayKT, 1, 1, gbc);
        addDateComponent(p, "Ngày Ký:", txtNgayKy, 2, 0, gbc);
        addDateComponent(p, "Ngày Lên Lương:", txtNgayLenLuong, 2, 1, gbc);
        addComponent(p, "Mức Lương:", txtLuong, 3, 0, gbc);
        
        gbc.gridx = 2; gbc.gridy = 3; gbc.weightx = 0.1;
        p.add(new JLabel("Trạng Thái:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.4;
        p.add(cbTrangThai, gbc);

        addComponent(p, "Mã Nhân Viên:", txtMaNV, 4, 0, gbc);
        addComponent(p, "Lần Ký:", txtLanKy, 4, 1, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        p.add(new JLabel("Nội Dung:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        p.add(new JScrollPane(txtNoiDung), gbc);
    }

    private void setupTable() {
        String[] headers = {"Mã HD", "Loại HD", "Bắt đầu", "Kết thúc", "Ngày ký", "Lương", "Trạng thái", "Mã NV", "Lần ký", "Nội dung"};
        model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { fillForm(table.getSelectedRow()); }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void setupActionButtons() {
        JPanel panelAction = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdd = new JButton("Thêm Mới");
        JButton btnUpdate = new JButton("Cập Nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm Mới");
        JButton btnSearch = new JButton("Tìm Kiếm");

        btnAdd.addActionListener(e -> insert());
        btnUpdate.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        btnClear.addActionListener(e -> { clearForm(); loadData(); });
        btnSearch.addActionListener(e -> search());

        panelAction.add(btnAdd); panelAction.add(btnUpdate); panelAction.add(btnDelete); 
        panelAction.add(btnClear); panelAction.add(new JLabel("  Tìm mã HD:")); 
        panelAction.add(txtSearch); panelAction.add(btnSearch);
        add(panelAction, BorderLayout.SOUTH);
    }

    private void autoUpdateStatus() {
        try {
            if (txtNgayKT.getText().trim().isEmpty()) return;

            java.util.Date ngayKT = sdf.parse(txtNgayKT.getText().trim());
            java.util.Date homNay = new java.util.Date();

            // Nếu ngày hôm nay sau ngày kết thúc thì tự động chọn HetHan
            if (homNay.after(ngayKT)) {
                cbTrangThai.setSelectedItem("HetHan");
            } else {
                cbTrangThai.setSelectedItem("HieuLuc");
            }
        } catch (Exception e) {
            cbTrangThai.setSelectedItem("HieuLuc");
        }
    }

    private void addComponent(JPanel p, String label, Component comp, int row, int col, GridBagConstraints gbc) {
        gbc.gridx = col * 2; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0.1;
        p.add(new JLabel(label), gbc);
        gbc.gridx = col * 2 + 1; gbc.weightx = 0.4;
        p.add(comp, gbc);
    }

    private void addDateComponent(JPanel p, String label, JTextField txt, int row, int col, GridBagConstraints gbc) {
        gbc.gridx = col * 2; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0.1;
        p.add(new JLabel(label), gbc);
        JPanel pDate = new JPanel(new BorderLayout());
        pDate.add(txt, BorderLayout.CENTER);
        JButton btn = new JButton("📅");
        btn.setMargin(new Insets(2, 2, 2, 2));
        btn.addActionListener(e -> {
            new CalendarGrid(txt).setVisible(true);
            autoUpdateStatus(); 
        });
        pDate.add(btn, BorderLayout.EAST);
        gbc.gridx = col * 2 + 1; gbc.weightx = 0.4;
        p.add(pDate, gbc);
    }


    class CalendarGrid extends JDialog {
        private JTextField target;
        private JPanel pDays;
        private JLabel lblMonthYear;
        private Calendar cal = Calendar.getInstance();

        public CalendarGrid(JTextField target) {
            this.target = target;
            setTitle("Chọn Ngày"); setModal(true); setSize(350, 320);
            setLocationRelativeTo(target); setLayout(new BorderLayout());
            JPanel pHeader = new JPanel(new BorderLayout());
            JButton btnPrev = new JButton("<<"); JButton btnNext = new JButton(">>");
            lblMonthYear = new JLabel("", JLabel.CENTER);
            btnPrev.addActionListener(e -> { cal.add(Calendar.MONTH, -1); updateCalendar(); });
            btnNext.addActionListener(e -> { cal.add(Calendar.MONTH, 1); updateCalendar(); });
            pHeader.add(btnPrev, BorderLayout.WEST); pHeader.add(lblMonthYear, BorderLayout.CENTER); pHeader.add(btnNext, BorderLayout.EAST);
            pDays = new JPanel(new GridLayout(0, 7));
            updateCalendar();
            add(pHeader, BorderLayout.NORTH); add(pDays, BorderLayout.CENTER);
        }

        private void updateCalendar() {
            pDays.removeAll();
            String[] header = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
            for (String h : header) { JLabel l = new JLabel(h, JLabel.CENTER); l.setForeground(Color.RED); pDays.add(l); }
            lblMonthYear.setText(new SimpleDateFormat("'Tháng' MM, yyyy", new Locale("vi")).format(cal.getTime()));
            Calendar temp = (Calendar) cal.clone(); temp.set(Calendar.DAY_OF_MONTH, 1);
            int startDay = temp.get(Calendar.DAY_OF_WEEK) - 1;
            for (int i = 0; i < startDay; i++) pDays.add(new JLabel(""));
            int days = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 1; i <= days; i++) {
                final int day = i;
                JButton btn = new JButton(String.valueOf(i));
                btn.addActionListener(e -> {
                    cal.set(Calendar.DAY_OF_MONTH, day);
                    target.setText(sdf.format(cal.getTime()));
                    dispose();
                });
                pDays.add(btn);
            }
            pDays.revalidate(); pDays.repaint();
        }
    }

    void loadData() { renderTable(bus.getAll()); }

    void renderTable(ArrayList<HopDong_DTO> list) {
        model.setRowCount(0);
        for (HopDong_DTO hd : list) {
            model.addRow(new Object[]{
                hd.getMaHD(), hd.getLoaiHopDong(),
                hd.getNgayBatDau() != null ? sdf.format(hd.getNgayBatDau()) : "",
                hd.getNgayKetThuc() != null ? sdf.format(hd.getNgayKetThuc()) : "",
                hd.getNgayKy() != null ? sdf.format(hd.getNgayKy()) : "",
                hd.getMucLuongCoBan(),
                hd.getTrangThai(), hd.getMaNV(), hd.getLanKy(), hd.getNoiDung()
            });
        }
    }

    void fillForm(int i) {
        if (i < 0) return;
        txtMaHD.setText(getValueAt(i, 0));
        txtLoaiHD.setText(getValueAt(i, 1));
        txtNgayBD.setText(getValueAt(i, 2));
        txtNgayKT.setText(getValueAt(i, 3));
        txtNgayKy.setText(getValueAt(i, 4));
        txtLuong.setText(getValueAt(i, 5));
        cbTrangThai.setSelectedItem(getValueAt(i, 6));
        txtMaNV.setText(getValueAt(i, 7));
        txtLanKy.setText(getValueAt(i, 8));
        txtNoiDung.setText(getValueAt(i, 9));
    }

    private String getValueAt(int row, int col) {
        Object val = model.getValueAt(row, col);
        return val == null ? "" : val.toString();
    }

    HopDong_DTO getForm() {
        HopDong_DTO hd = new HopDong_DTO();
        hd.setMaHD(txtMaHD.getText().trim());
        hd.setLoaiHopDong(txtLoaiHD.getText().trim());
        hd.setNgayBatDau(parseDate(txtNgayBD.getText()));
        hd.setNgayKetThuc(parseDate(txtNgayKT.getText()));
        hd.setNgayKy(parseDate(txtNgayKy.getText()));
        hd.setNgayLenLuongGanNhat(parseDate(txtNgayLenLuong.getText()));
        
        try {
            String luongTxt = txtLuong.getText().trim().replaceAll("[^\\d.]", "");
            hd.setMucLuongCoBan(luongTxt.isEmpty() ? 0 : Double.parseDouble(luongTxt));
            String lanKyTxt = txtLanKy.getText().trim().replaceAll("[^\\d]", "");
            hd.setLanKy(lanKyTxt.isEmpty() ? 1 : Integer.parseInt(lanKyTxt));
        } catch(Exception e) {
            hd.setMucLuongCoBan(0); hd.setLanKy(1);
        }
        hd.setNoiDung(txtNoiDung.getText().trim());
        hd.setTrangThai(cbTrangThai.getSelectedItem().toString()); 
        hd.setMaNV(txtMaNV.getText().trim());
        return hd;
    }

    private Date parseDate(String s) {
        try { return new Date(sdf.parse(s).getTime()); } catch (Exception e) { return null; }
    }

    void insert() { 
        String res = bus.insert(getForm());
        JOptionPane.showMessageDialog(this, res);
        if(res.contains("thành công")) { loadData(); clearForm(); }
    }
    
    void update() { 
        String res = bus.update(getForm());
        JOptionPane.showMessageDialog(this, res);
        if(res.contains("thành công")) { loadData(); }
    }
    
    void delete() { 
        String ma = txtMaHD.getText().trim();
        if(ma.isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng chọn hợp đồng!"); return; }
        if(JOptionPane.showConfirmDialog(this, "Xác nhận xóa hợp đồng này?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String res = bus.delete(ma);
            JOptionPane.showMessageDialog(this, res);
            if(res.contains("thành công")) { loadData(); clearForm(); }
        }
    }

    void search() { renderTable(bus.search(txtSearch.getText().trim())); }
    
    void clearForm() {
        JTextField[] fields = {txtMaHD, txtLoaiHD, txtNgayBD, txtNgayKT, txtNgayKy, txtLuong, txtMaNV, txtLanKy, txtNgayLenLuong, txtSearch};
        for(JTextField t : fields) t.setText("");
        cbTrangThai.setSelectedIndex(0);
        txtNoiDung.setText("");
        table.clearSelection();
    }
//test
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        JFrame frame = new JFrame("QUẢN LÝ HỢP ĐỒNG ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 800);
        frame.add(new HopDong_GUI());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}