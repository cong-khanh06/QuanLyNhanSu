package GUI;

import BUS.ChiTietBaoHiem_BUS;
import DTO.ChiTietBaoHiem_DTO; 

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChiTietBaoHiem_GUI extends JPanel {
    JPanel pnSearch, pnHeader, pnToolBar;
    JTable table;
    DefaultTableModel model;
    JTextField txtSearch;
    JButton btnSearch, btnChiTiet, btnRefresh;
    JLabel lblTitle;
    private String currentRole = "Admin";

    ChiTietBaoHiem_BUS bus = new ChiTietBaoHiem_BUS();
    boolean check=true;
    String manvuser="";

    public ChiTietBaoHiem_GUI() {
        setBackground(new Color(226, 232, 240)); 
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font modernFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ================= PHẦN HEADER & TOOLBAR =================
        pnSearch = new JPanel(new BorderLayout());
        pnSearch.setBackground(Color.WHITE);
        // Viền Xanh Ngọc ở trên cùng
        pnSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(16, 185, 129)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnHeader.setBackground(Color.WHITE);
        lblTitle = new JLabel("Quản Lý Chi Tiết Bảo Hiểm Theo Nhân Viên");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 41, 59));
        pnHeader.add(lblTitle);

        pnToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnToolBar.setBackground(Color.WHITE);
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(250, 36));
        txtSearch.setFont(modernFont);
        
        btnSearch = createFlatButton("Tìm kiếm NV", "primary");
        btnChiTiet = createFlatButton("Chi Tiết / Chỉnh Sửa", "success"); 
        btnRefresh = createFlatButton("Tải lại", "");

        pnToolBar.add(txtSearch); pnToolBar.add(btnSearch);
        pnToolBar.add(btnChiTiet); pnToolBar.add(btnRefresh);

        pnSearch.add(pnHeader, BorderLayout.NORTH);
        pnSearch.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearch, BorderLayout.NORTH);

        // ================= PHẦN BẢNG DỮ LIỆU =================
        String[] cols = {"Mã Nhân Viên", "Tên Nhân Viên", "Số Lượng BH Tham Gia"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scroll.getViewport().setBackground(Color.WHITE);
        
        JPanel pnTableCard = new JPanel(new BorderLayout());
        pnTableCard.setBackground(Color.WHITE);
        pnTableCard.add(scroll, BorderLayout.CENTER);
        
        add(pnTableCard, BorderLayout.CENTER);

        if(!check) taiDuLieuLenBanguser(manvuser);
        else taiDuLieuLenBang();
        addEvents();
    }

    // --- THÊM 2 HÀM NÀY VÀO TRONG CLASS ChiTietBaoHiem_GUI ---
    private JButton createFlatButton(String text, String style) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(160, 36)); // Nút ở đây chữ dài hơn
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (!style.isEmpty()) btn.putClientProperty("FlatLaf.styleClass", style);
        return btn;
    }

    private void styleTable(JTable tb) {
        tb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tb.setRowHeight(30);
        tb.setShowVerticalLines(false);
        tb.setShowHorizontalLines(true);
        tb.setGridColor(new Color(230, 230, 230));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tb.getTableHeader().setBackground(new Color(248, 250, 252));
    }
    private void taiDuLieuLenBang() {
        model.setRowCount(0);
        List<ChiTietBaoHiem_DTO> list = bus.layDanhSachNhanVienBaoHiem();
        for (ChiTietBaoHiem_DTO nv : list) {
            model.addRow(new Object[]{ nv.getMaNV(), nv.getTenNV(), nv.getSoLuongBH() });
        }
    }
    public void taiDuLieuLenBanguser(String manv)
    {
    	model.setRowCount(0);
        List<ChiTietBaoHiem_DTO> list = bus.laydanhsachNhanVienBHuser(manv);
        for (ChiTietBaoHiem_DTO nv : list) {
            model.addRow(new Object[]{ nv.getMaNV(), nv.getTenNV(), nv.getSoLuongBH() });
        }
    }

    private void addEvents() {
        btnRefresh.addActionListener(e -> { 
            txtSearch.setText(""); 
            taiDuLieuLenBang(); 
        });

        btnSearch.addActionListener(e -> {
            String tuKhoa = txtSearch.getText().trim();
            List<ChiTietBaoHiem_DTO> kq = bus.timKiemNhanVienBaoHiem(tuKhoa);
            model.setRowCount(0);
            for (ChiTietBaoHiem_DTO nv : kq) {
                model.addRow(new Object[]{ nv.getMaNV(), nv.getTenNV(), nv.getSoLuongBH() });
            }
            if(kq.isEmpty()) JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
        });

        btnChiTiet.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 nhân viên trên bảng để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maNV = table.getValueAt(row, 0).toString();
            String tenNV = table.getValueAt(row, 1).toString();

            Window parentWindow = SwingUtilities.getWindowAncestor(this);
             ChiTietBaoHiem_Dialog dialogBH = new ChiTietBaoHiem_Dialog(parentWindow, maNV, tenNV);
            
            if (currentRole.equals("User")) {
                dialogBH.setphanquyenuser(false); 
            } else if (currentRole.equals("Manager")) {
                dialogBH.setphanquyenManager(); 
            }

            dialogBH.setVisible(true);

            if (currentRole.equals("User")) taiDuLieuLenBanguser(manvuser);
            else taiDuLieuLenBang();
        });
    }
    public void setphanquyenUser(boolean kq, String manv) {
        this.check = kq; 
        this.manvuser = manv;
        this.currentRole = "User";
        txtSearch.setVisible(false);
        btnSearch.setVisible(false);
        btnRefresh.setVisible(false);
        taiDuLieuLenBanguser(manv);
    }
    public void setphanquyenManager() {
        this.currentRole = "Manager";
        this.check = true; 
        taiDuLieuLenBang();
    }
}