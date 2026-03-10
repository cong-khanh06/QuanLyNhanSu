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
        setLayout(new BorderLayout());


        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnHeader.setBackground(new Color(150, 214, 255));
        pnHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        lblTitle = new JLabel("Quản Lý Chi Tiết Bảo Hiểm Theo Nhân Viên");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnHeader.add(lblTitle);


        pnToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnToolBar.setBackground(new Color(150, 214, 255));
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(250, 36));
        btnSearch = new JButton("Tìm kiếm NV");
        btnChiTiet = new JButton("Chi Tiết / Chỉnh Sửa"); 
        btnRefresh = new JButton("Tải lại");

        pnToolBar.add(txtSearch); 
        pnToolBar.add(btnSearch);
        pnToolBar.add(btnChiTiet); 
        pnToolBar.add(btnRefresh);

        pnSearch = new JPanel(new BorderLayout());
        pnSearch.add(pnHeader, BorderLayout.NORTH);
        pnSearch.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearch, BorderLayout.NORTH);

        String[] cols = {"Mã Nhân Viên", "Tên Nhân Viên", "Số Lượng BH Tham Gia"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh Sách Nhân Viên"));
        add(scroll, BorderLayout.CENTER);

        if(!check)
        {
        	taiDuLieuLenBanguser(manvuser);
        }
        else
        {
        	taiDuLieuLenBang();
        }
        addEvents();
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