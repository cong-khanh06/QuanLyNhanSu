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
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private final Color HOVER_COLOR = new Color(92, 107, 192);
    private final Color TABLE_HEADER_COLOR = new Color(83, 109, 254);
    private final Color GRID_COLOR = new Color(230, 230, 230);

    public HopDong_GUI() {
        setLayout(new BorderLayout(0, 15));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- 1. HEADER & SEARCH ---
        JPanel pHeader = new JPanel(new BorderLayout());
        pHeader.setOpaque(false);

        JLabel lblTitle = new JLabel("QUẢN LÝ HỢP ĐỒNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(PRIMARY_COLOR);
        pHeader.add(lblTitle, BorderLayout.NORTH);

        JPanel pActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        pActions.setOpaque(false);
        
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 35));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1), new EmptyBorder(0, 10, 0, 10)));
        
        pActions.add(new JLabel("Mã :"));
        pActions.add(txtSearch);
        pActions.add(createStyledButton("Tìm kiếm", PRIMARY_COLOR));
        pActions.add(createStyledButton("Làm mới", PRIMARY_COLOR));
        pActions.add(createStyledButton("Thêm mới", PRIMARY_COLOR)); 
        pActions.add(createStyledButton("Chỉnh sửa", PRIMARY_COLOR)); 
        pActions.add(createStyledButton("Xóa", PRIMARY_COLOR)); 

        pHeader.add(pActions, BorderLayout.SOUTH);
        add(pHeader, BorderLayout.NORTH);

        // --- 2. CENTER: TABLE ---
        setupTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(GRID_COLOR));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. SOUTH: DETAIL DASHBOARD ---
        add(createDetailDashboard(), BorderLayout.SOUTH);

        setupEvents(pActions);
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

    private void setupEvents(JPanel pActions) {
        Component[] comps = pActions.getComponents();
        ((JButton)comps[2]).addActionListener(e -> searchAction());
        ((JButton)comps[3]).addActionListener(e -> { txtSearch.setText(""); loadData(); });
        ((JButton)comps[4]).addActionListener(e -> showInputForm(null));
        ((JButton)comps[5]).addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) showInputForm(getDTOFromRow(row));
            else JOptionPane.showMessageDialog(this, "Vui lòng chọn hợp đồng cần sửa!");
        });
        ((JButton)comps[6]).addActionListener(e -> deleteAction());

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
        valTrangThai.setText(hd.getTrangThai());
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
                String.format("%,.0f", hd.getMucLuongCoBan()), hd.getTrangThai()
            });
        }
    }

    private void showInputForm(HopDong_DTO data) {
        InputFormDialog dialog = new InputFormDialog((JFrame) SwingUtilities.getWindowAncestor(this), data);
        dialog.setVisible(true);
        if (dialog.isDataChanged()) loadData();
    }

    private Date parseDate(String s) {
        try { return new Date(sdf.parse(s).getTime()); } catch (Exception e) { return null; }
    }

    // --- DIALOG NHẬP LIỆU ---
    class InputFormDialog extends JDialog {
        private JTextField txtMaHD, txtLoaiHD, txtNgayBD, txtNgayKT, txtNgayKy, txtLuong, txtMaNV, txtLanKy, txtNgayLenLuong;
        private JComboBox<String> cbTrangThai;
        private JTextArea txtNoiDung;
        private boolean dataChanged = false;
        private boolean isEdit;

        public InputFormDialog(JFrame parent, HopDong_DTO data) {
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
            JButton btnSave = createStyledButton(isEdit ? "CẬP NHẬT" : "LƯU LẠI", PRIMARY_COLOR);
            JButton btnExit = new JButton("HỦY BỎ");
            btnExit.setPreferredSize(new Dimension(110, 35));

            btnSave.addActionListener(e -> {
                if (validateForm()) {
                    try {
                        HopDong_DTO dto = collectData();
                        String res = isEdit ? bus.update(dto) : bus.insert(dto);
                        JOptionPane.showMessageDialog(this, res);
                        if (res.toLowerCase().contains("thành công")) { dataChanged = true; dispose(); }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage());
                    }
                }
            });

            btnExit.addActionListener(e -> dispose());
            pBottom.add(btnExit); pBottom.add(btnSave);
            add(pBottom, BorderLayout.SOUTH);
        }

        private void initFields() {
            txtMaHD = new JTextField(); if(isEdit) txtMaHD.setEditable(false);
            txtLoaiHD = new JTextField(); txtNgayBD = new JTextField(); txtNgayKT = new JTextField();
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
            txtLanKy.setText(String.valueOf(d.getLanKy())); // Đã đảm bảo hiện thị lần ký khi Edit
            cbTrangThai.setSelectedItem(d.getTrangThai()); 
            txtNoiDung.setText(d.getNoiDung());
        }

        private boolean validateForm() {
            // 1. Kiểm tra để trống các trường bắt buộc
            if(txtMaHD.getText().trim().isEmpty() || txtMaNV.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "Mã Hợp đồng và Mã Nhân viên không được để trống!");
                return false;
            }
            
            // 2. Kiểm tra Mức lương (Phải là số > 0)
            try {
                if (Double.parseDouble(txtLuong.getText().trim()) <= 0) throw new Exception();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Mức lương phải là số lớn hơn 0!");
                return false;
            }

            // 3. Kiểm tra Lần ký (Phải là số nguyên >= 1) - CẬP NHẬT TẠI ĐÂY
            try {
                int lanky = Integer.parseInt(txtLanKy.getText().trim());
                if (lanky < 1) throw new Exception();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lần ký phải là số nguyên lớn hơn hoặc bằng 1!");
                return false;
            }

            return true;
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

        private HopDong_DTO collectData() {
            HopDong_DTO hd = new HopDong_DTO();
            hd.setMaHD(txtMaHD.getText().trim()); hd.setLoaiHopDong(txtLoaiHD.getText().trim());
            hd.setNgayBatDau(parseDate(txtNgayBD.getText())); hd.setNgayKetThuc(parseDate(txtNgayKT.getText()));
            hd.setNgayKy(parseDate(txtNgayKy.getText())); hd.setNgayLenLuongGanNhat(parseDate(txtNgayLenLuong.getText()));
            hd.setMucLuongCoBan(Double.parseDouble(txtLuong.getText().trim()));
            hd.setMaNV(txtMaNV.getText().trim()); hd.setLanKy(Integer.parseInt(txtLanKy.getText().trim()));
            hd.setTrangThai(cbTrangThai.getSelectedItem().toString()); hd.setNoiDung(txtNoiDung.getText().trim());
            return hd;
        }
        public boolean isDataChanged() { return dataChanged; }
    }

    // --- LỚP CALENDAR ---
    class CalendarGrid extends JDialog {
        private JTextField target; private JPanel pDays;
        private JComboBox<Integer> cbYear; private JComboBox<String> cbMonth;
        private Calendar cal = Calendar.getInstance();
        private boolean isUpdating = false;

        public CalendarGrid(JTextField target) {
            this.target = target; setModal(true); setSize(350, 300);
            setLocationRelativeTo(target); setLayout(new BorderLayout());
            JPanel pHeader = new JPanel(new BorderLayout());
            JButton btnPrev = new JButton("<");
            btnPrev.addActionListener(e -> { cal.add(Calendar.MONTH, -1); updateControls(); updateCalendar(); });
            JButton btnNext = new JButton(">");
            btnNext.addActionListener(e -> { cal.add(Calendar.MONTH, 1); updateControls(); updateCalendar(); });

            JPanel pCenterHeader = new JPanel(new FlowLayout());
            cbMonth = new JComboBox<>(new String[]{"Th1", "Th2", "Th3", "Th4", "Th5", "Th6", "Th7", "Th8", "Th9", "Th10", "Th11", "Th12"});
            Integer[] years = new Integer[151]; for (int i = 0; i <= 150; i++) years[i] = 1950 + i;
            cbYear = new JComboBox<>(years);
            pCenterHeader.add(cbMonth); pCenterHeader.add(cbYear);
            pHeader.add(btnPrev, BorderLayout.WEST); pHeader.add(pCenterHeader, BorderLayout.CENTER); pHeader.add(btnNext, BorderLayout.EAST);

            ActionListener cl = e -> { if (!isUpdating) { cal.set(Calendar.MONTH, cbMonth.getSelectedIndex()); cal.set(Calendar.YEAR, (Integer) cbYear.getSelectedItem()); updateCalendar(); } };
            cbMonth.addActionListener(cl); cbYear.addActionListener(cl);

            try { if(!target.getText().isEmpty()) cal.setTime(sdf.parse(target.getText())); } catch (Exception e) {}
            updateControls(); pDays = new JPanel(new GridLayout(0, 7)); updateCalendar();
            add(pHeader, BorderLayout.NORTH); add(pDays, BorderLayout.CENTER);
        }
        private void updateControls() { isUpdating = true; cbMonth.setSelectedIndex(cal.get(Calendar.MONTH)); cbYear.setSelectedItem(cal.get(Calendar.YEAR)); isUpdating = false; }
        private void updateCalendar() {
            pDays.removeAll();
            String[] heads = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
            for (String h : heads) { JLabel l = new JLabel(h, JLabel.CENTER); l.setForeground(Color.RED); pDays.add(l); }
            Calendar t = (Calendar) cal.clone(); t.set(Calendar.DAY_OF_MONTH, 1);
            int start = t.get(Calendar.DAY_OF_WEEK) - 1;
            for (int i = 0; i < start; i++) pDays.add(new JLabel(""));
            int days = t.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 1; i <= days; i++) {
                final int d = i; JButton btn = new JButton(String.valueOf(i)); btn.setBackground(Color.WHITE);
                if (i == cal.get(Calendar.DAY_OF_MONTH)) btn.setBackground(new Color(197, 202, 233));
                btn.addActionListener(e -> { cal.set(Calendar.DAY_OF_MONTH, d); target.setText(sdf.format(cal.getTime())); dispose(); });
                pDays.add(btn);
            }
            pDays.revalidate(); pDays.repaint();
        }
    }
}