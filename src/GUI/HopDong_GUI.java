package GUI;

import BUS.HopDong_BUS;
import DTO.HopDong_DTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.*;

public class HopDong_GUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private HopDong_BUS bus = new HopDong_BUS();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private JLabel valMaHD, valLoaiHD, valNgayKy, valTuNgay, valDenNgay, valLuong, valNhanVien, valTrangThai, valLanKy, valNgayLenLuong;
    private JTextArea txtNoiDungChiTiet;

    private final Color PRIMARY_COLOR = new Color(63, 81, 181);
    private final Color TABLE_HEADER_COLOR = new Color(83, 109, 254);
    private final Color GRID_COLOR = new Color(230, 230, 230);
    
    private JButton bttiemkiem, btlammoi, btthem, btsua, btxoa;

    public HopDong_GUI() {
        setLayout(new BorderLayout(0, 15));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pHeader = new JPanel(new BorderLayout(20, 0)); 
        pHeader.setOpaque(false);
        pHeader.setBorder(new EmptyBorder(0, 0, 15, 0)); 

        JPanel pLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        pLeft.setOpaque(false);
        JLabel lblTitle = new JLabel("QUẢN LÝ HỢP ĐỒNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24)); 
        lblTitle.setForeground(PRIMARY_COLOR);
        pLeft.add(lblTitle);

        JPanel pRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10)); 
        pRight.setOpaque(false);
        
        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        lblTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(180, 32)); 
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1), new EmptyBorder(0, 10, 0, 10)));
        
        bttiemkiem = createStyledButton("Tìm kiếm", PRIMARY_COLOR);
        btlammoi = createStyledButton("Làm mới", PRIMARY_COLOR);
        btthem = createStyledButton("Thêm mới", PRIMARY_COLOR);
        btsua = createStyledButton("Chỉnh sửa", PRIMARY_COLOR);
        btxoa = createStyledButton("Xóa", PRIMARY_COLOR);
        
        pRight.add(lblTimKiem);
        pRight.add(txtSearch);
        pRight.add(bttiemkiem);
        pRight.add(btlammoi);
        pRight.add(btthem); 
        pRight.add(btsua); 
        pRight.add(btxoa); 

        pHeader.add(pLeft, BorderLayout.WEST);
        pHeader.add(pRight, BorderLayout.CENTER); 
        add(pHeader, BorderLayout.NORTH);

        setupTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(GRID_COLOR));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        add(createDetailDashboard(), BorderLayout.SOUTH);

        setupEvents();
        loadData();
    }

    private void setupTable() {
        String[] headers = {"Mã HD", "Mã NV", "Loại HD", "Lương", "Trạng thái"};
        model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(232, 234, 246));
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(true);
        table.setGridColor(GRID_COLOR);
        table.setIntercellSpacing(new Dimension(1, 1)); 
        table.setFocusable(false);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setBorder(new MatteBorder(0, 0, 1, 1, GRID_COLOR));
                return this;
            }
        };
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(TABLE_HEADER_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.getTableHeader().setReorderingAllowed(false);
    }

    private JPanel createDetailDashboard() {
        JPanel pMainDetail = new JPanel(new BorderLayout(15, 0));
        pMainDetail.setOpaque(false);
        pMainDetail.setPreferredSize(new Dimension(0, 230));
        pMainDetail.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(PRIMARY_COLOR, 1), " THÔNG TIN CHI TIẾT ", 
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), PRIMARY_COLOR));

        JPanel pInfoGrid = new JPanel(new GridLayout(4, 3, 20, 8));
        pInfoGrid.setOpaque(false);
        pInfoGrid.setBorder(new EmptyBorder(10, 20, 10, 20));

        valMaHD = new JLabel("---"); valLoaiHD = new JLabel("---"); valNhanVien = new JLabel("---");
        valNgayKy = new JLabel("---"); valTuNgay = new JLabel("---"); valDenNgay = new JLabel("---");
        valLuong = new JLabel("---"); valTrangThai = new JLabel("---"); valLanKy = new JLabel("---");
        valNgayLenLuong = new JLabel("---");

        pInfoGrid.add(createLabelGroup("Mã HD:", valMaHD));
        pInfoGrid.add(createLabelGroup("Nhân viên:", valNhanVien));
        pInfoGrid.add(createLabelGroup("Loại HD:", valLoaiHD));
        pInfoGrid.add(createLabelGroup("Ngày ký:", valNgayKy));
        pInfoGrid.add(createLabelGroup("Bắt đầu:", valTuNgay));
        pInfoGrid.add(createLabelGroup("Kết thúc:", valDenNgay));
        pInfoGrid.add(createLabelGroup("Lương:", valLuong));
        pInfoGrid.add(createLabelGroup("Trạng thái:", valTrangThai));
        pInfoGrid.add(createLabelGroup("Lần ký:", valLanKy));
        pInfoGrid.add(createLabelGroup("Nâng lương:", valNgayLenLuong));

        txtNoiDungChiTiet = new JTextArea();
        txtNoiDungChiTiet.setEditable(false);
        txtNoiDungChiTiet.setLineWrap(true);
        txtNoiDungChiTiet.setWrapStyleWord(true);
        txtNoiDungChiTiet.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        txtNoiDungChiTiet.setBackground(new Color(250, 250, 250));
        
        JScrollPane spNoiDung = new JScrollPane(txtNoiDungChiTiet);
        spNoiDung.setBorder(BorderFactory.createTitledBorder("Nội dung ghi chú"));
        spNoiDung.setPreferredSize(new Dimension(350, 0));

        pMainDetail.add(pInfoGrid, BorderLayout.CENTER);
        pMainDetail.add(spNoiDung, BorderLayout.EAST);
        return pMainDetail;
    }

    private JPanel createLabelGroup(String title, JLabel valueLabel) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.setOpaque(false);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        valueLabel.setForeground(new Color(50, 50, 50));
        p.add(lblTitle); p.add(valueLabel);
        return p;
    }

    private void setupEvents() {
        bttiemkiem.addActionListener(e -> searchAction());
        btlammoi.addActionListener(e -> { txtSearch.setText(""); loadData(); });
        btthem.addActionListener(e -> showInputForm(null));
        btsua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) showInputForm(getDTOFromRow(row));
            else JOptionPane.showMessageDialog(this, "Vui lòng chọn hợp đồng cần sửa!");
        });
        btxoa.addActionListener(e -> deleteAction());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) updateDetailPanel(getDTOFromRow(row));
            }
        });
    }

    private HopDong_DTO getDTOFromRow(int row) {
        if (row < 0) return null;
        String maHD = table.getValueAt(row, 0).toString();
        for (HopDong_DTO hd : bus.getAll()) {
            if (hd.getMaHD().equals(maHD)) return hd;
        }
        return null;
    }

    private void updateDetailPanel(HopDong_DTO hd) {
        if (hd == null) return;
        valMaHD.setText(hd.getMaHD());
        valNhanVien.setText(hd.getMaNV());
        valLoaiHD.setText(hd.getLoaiHopDong());
        valNgayKy.setText(hd.getNgayKy() != null ? sdf.format(hd.getNgayKy()) : "---");
        valTuNgay.setText(hd.getNgayBatDau() != null ? sdf.format(hd.getNgayBatDau()) : "---");
        valDenNgay.setText(hd.getNgayKetThuc() != null ? sdf.format(hd.getNgayKetThuc()) : "Vô thời hạn");
        valLuong.setText(String.format("%,.0f VNĐ", hd.getMucLuongCoBan()));
        valTrangThai.setText(formatTrangThai(hd.getTrangThai()));
        valLanKy.setText(String.valueOf(hd.getLanKy()));
        valNgayLenLuong.setText(hd.getNgayLenLuongGanNhat() != null ? sdf.format(hd.getNgayLenLuongGanNhat()) : "---");
        txtNoiDungChiTiet.setText(hd.getNoiDung());
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(105, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(bg.brighter()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(bg); }
        });
        return btn;
    }

    public void loadData() { renderTable(bus.getAll()); }
    private void searchAction() { renderTable(bus.search(txtSearch.getText().trim())); }
    
    private void deleteAction() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hợp đồng muốn xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String ma = table.getValueAt(row, 0).toString();
        if (JOptionPane.showConfirmDialog(this, "Xác nhận xóa hợp đồng: " + ma + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == 0) {
            JOptionPane.showMessageDialog(this, bus.delete(ma));
            loadData();
        }
    }

    public void renderTable(ArrayList<HopDong_DTO> list) {
        model.setRowCount(0);
        for (HopDong_DTO hd : list) {
            model.addRow(new Object[]{
                hd.getMaHD(), hd.getMaNV(), hd.getLoaiHopDong(),
                String.format("%,.0f", hd.getMucLuongCoBan()), formatTrangThai(hd.getTrangThai())
            });
        }
    }

    private void showInputForm(HopDong_DTO data) {
        HopDong1_GUI dialog = new HopDong1_GUI((JFrame) SwingUtilities.getWindowAncestor(this), data);
        dialog.setVisible(true);
        if (dialog.isDataChanged()) loadData();
    }

    public void renderTableuser(String manv) {
        ArrayList<HopDong_DTO> list = bus.getHopDongTheoMaNV(manv);
        renderTable(list);
    }
    
    private String formatTrangThai(String trangThai){
        if(trangThai==null) return "---";
        String strtt=trangThai.trim().toLowerCase();
        switch(strtt){
            case "hethan":
                return "Hết hạn";
            case "hieuluc":
                return "Hiệu lực";
            default:
                return trangThai;
        }
    }
    
    public void setphanquyenUser(boolean kq, String manv) {
        btthem.setEnabled(kq);
        btsua.setEnabled(kq);
        btxoa.setEnabled(kq);
        bttiemkiem.setEnabled(kq);
        btlammoi.setEnabled(kq);
        renderTableuser(manv);
    }

    public void setphanquyenManager(boolean kq) {
        btxoa.setEnabled(kq);
    }
}