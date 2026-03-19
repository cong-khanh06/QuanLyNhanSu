package GUI;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;
import GUI.button.ButtonToolBar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSplitPane; // Khai báo JSplitPane
import java.awt.GridLayout;
import java.util.List;
import DTO.DuAn_DTO;
import BUS.DuAn_BUS; 
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;
import DTO.PhanCongDuAn_DTO;
import BUS.PhanCongDuAn_BUS;
import BUS.ExcelExporter;
import java.awt.Cursor;

public class DuAn_GUI extends JPanel {
    JPanel pnSearchDA, pnHeader, pnToolBar;
    
    
    JTable tableDA, tablePCDA; 
    DefaultTableModel modelDA, modelPCDA; 
    
    JTextField txtSearch;

    JButton btnSearch, btnAdd, btnEdit, btnDelete, btnDown, btnrefresh, btnPhanCong;

    JComboBox<String> cbTrangThai; 
    JLabel lblTitle;
    
    DuAn_BUS bus = new DuAn_BUS();
    PhanCongDuAn_BUS busPcda = new PhanCongDuAn_BUS();
    
    JLabel lblSoChuanBi, lblSoDangThucHien, lblSoHoanThanh; 
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    public DuAn_GUI() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(226, 232, 240)); 
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ================= HEADER & TOOLBAR (Card Trắng) =================
        pnSearchDA = new JPanel(new BorderLayout());
        pnSearchDA.setBackground(Color.WHITE);
        pnSearchDA.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(14, 165, 233)), // Viền Xanh Sky
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnHeader.setBackground(Color.WHITE);
        lblTitle = new JLabel("Danh sách dự án");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 41, 59));
        pnHeader.add(lblTitle);
        
        pnToolBar = new JPanel(new BorderLayout(10, 10));
        pnToolBar.setBackground(Color.WHITE);
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        JPanel pnSearch = new JPanel(new BorderLayout(5, 0));
        pnSearch.setBackground(Color.WHITE);
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 36));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        btnSearch = createFlatButton("Tìm kiếm", "primary");
        pnSearch.add(txtSearch, BorderLayout.CENTER);
        pnSearch.add(btnSearch, BorderLayout.EAST);
        
        JPanel pnAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnAction.setBackground(Color.WHITE);
        
        btnAdd = createFlatButton("Thêm", "primary");
        btnEdit = createFlatButton("Sửa", "");     
        btnDelete = createFlatButton("Xóa", "danger");
        btnDown = createFlatButton("Tải Xuống", "success");
        btnrefresh = createFlatButton("Tải lại", "");
        btnPhanCong = createFlatButton("Phân công NS", "primary"); 
        btnPhanCong.setPreferredSize(new Dimension(140, 36));
        
        cbTrangThai = new JComboBox<>(new String[]{"Tất cả", "Chuẩn bị", "Đang thực hiện", "Hoàn thành"});
        cbTrangThai.setPreferredSize(new Dimension(130, 36));
        cbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pnAction.add(cbTrangThai); pnAction.add(btnAdd); pnAction.add(btnEdit);   
        pnAction.add(btnDelete); pnAction.add(btnPhanCong); pnAction.add(btnDown); pnAction.add(btnrefresh);
        
        pnToolBar.add(pnSearch, BorderLayout.CENTER);
        pnToolBar.add(pnAction, BorderLayout.EAST);
        
        pnSearchDA.add(pnHeader, BorderLayout.NORTH);
        pnSearchDA.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearchDA, BorderLayout.NORTH);

        // ================= 2 BẢNG DỮ LIỆU =================
        String[] colsDA = {"Mã Dự Án", "Tên Dự Án", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Trạng Thái", "Người quản lý"};
        modelDA = new DefaultTableModel(colsDA, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableDA = new JTable(modelDA);
        styleTable(tableDA); // Gọi hàm style
        
        JScrollPane scrollDA = new JScrollPane(tableDA);
        scrollDA.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollDA.getViewport().setBackground(Color.WHITE);
        
        JPanel pnChiTiet = new JPanel(new BorderLayout());
        pnChiTiet.setBackground(Color.WHITE);
        pnChiTiet.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(59, 130, 246)),
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Chi Tiết Nhân Sự Tham Gia", 
                0, 0, new Font("Segoe UI", Font.BOLD, 15), new Color(30, 41, 59))
        ));

        String[] colsPCDA = {"Mã NV", "Tên Nhân Viên", "Vai Trò Đảm Nhận"};
        modelPCDA = new DefaultTableModel(colsPCDA, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablePCDA = new JTable(modelPCDA);
        styleTable(tablePCDA);
        
        JScrollPane scrollPCDA = new JScrollPane(tablePCDA);
        scrollPCDA.setBorder(BorderFactory.createEmptyBorder());
        scrollPCDA.getViewport().setBackground(Color.WHITE);
        pnChiTiet.add(scrollPCDA, BorderLayout.CENTER);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollDA, pnChiTiet);
        splitPane.setResizeWeight(0.6); 
        splitPane.setDividerSize(10);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);
        
        add(splitPane, BorderLayout.CENTER);
        
        // ================= THỐNG KÊ (Card Đẹp) =================
        JPanel pnThongKe = new JPanel(new GridLayout(1, 3, 20, 0)); 
        pnThongKe.setBackground(new Color(226, 232, 240));
        pnThongKe.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        lblSoChuanBi = new JLabel("0"); lblSoDangThucHien = new JLabel("0"); lblSoHoanThanh = new JLabel("0");
        
        pnThongKe.add(taoOThongKe("Chuẩn bị", new Color(245, 158, 11), lblSoChuanBi));        
        pnThongKe.add(taoOThongKe("Đang thực hiện", new Color(59, 130, 246), lblSoDangThucHien)); 
        pnThongKe.add(taoOThongKe("Hoàn thành", new Color(16, 185, 129), lblSoHoanThanh));       
        
        add(pnThongKe, BorderLayout.SOUTH);

        addEventRefresh();
        addEventAdd();
        addEventSearch();
        addEventEdit();
        addEventDelete();

        addEventPhanCong();
        addEventClickTableDA(); 
        taiDuLieu();
        taiDuLieuLenBang();
    }
    
    private JButton createFlatButton(String text, String style) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(100, 36));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (!style.isEmpty()) btn.putClientProperty("FlatLaf.styleClass", style);
        return btn;
    }

    private void styleTable(JTable tb) {
        tb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tb.setRowHeight(30);
        tb.setShowVerticalLines(false); tb.setShowHorizontalLines(true);
        tb.setGridColor(new Color(230, 230, 230));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tb.getTableHeader().setBackground(new Color(248, 250, 252));
    }
    
    // SỰ KIỆN CLICK BẢNG TRÊN
    private void addEventClickTableDA() {
        tableDA.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableDA.getSelectedRow();
                if (row >= 0) {
                    String maDA = tableDA.getValueAt(row, 0).toString();
                    taiDuLieuChiTietLenBang(maDA);
                }
            }
        });
    }

    // TẢI DỮ LIỆU BẢNG TRÊN (DỰ ÁN)
    public void taiDuLieuLenBang() {
        modelDA.setRowCount(0); 
        modelPCDA.setRowCount(0); // Xóa luôn bảng dưới
        
        List<DuAn_DTO> danhsach = bus.layDanhSachDuAn();
        for (DuAn_DTO da : danhsach) {
            String strNgayBD = (da.getNgayBatDau() != null) ? da.getNgayBatDau().format(dtf) : "";
            String strNgayKT = (da.getNgayKetThuc() != null) ? da.getNgayKetThuc().format(dtf) : "";
            modelDA.addRow(new Object[]{
                da.getMaDa(), da.getTenDuAn(), strNgayBD, strNgayKT, da.getTrangThai(), da.getNguoiQuanLy()
            });
        }
        capNhatThongKe(danhsach);
    }
    
    // TẢI DỮ LIỆU BẢNG DƯỚI (CHI TIẾT PHÂN CÔNG)
    public void taiDuLieuChiTietLenBang(String maDA) {
        modelPCDA.setRowCount(0); 
        List<PhanCongDuAn_DTO> dsPhanCong = busPcda.layDanhSachPhanCong(maDA);
        
        for (PhanCongDuAn_DTO pc : dsPhanCong) {
            modelPCDA.addRow(new Object[]{
                pc.getMaNV(), pc.getTenNV(), pc.getVaiTro()
            });
        }
    }
    
    public void addEventSearch() {
        btnSearch.addActionListener(e -> {
            String tuKhoa = txtSearch.getText().trim();
            String trangThai = cbTrangThai.getSelectedItem().toString();
            if (trangThai.equals("Tất cả")) trangThai = ""; 

            List<DuAn_DTO> ketQua = bus.timKiemDuAn(tuKhoa); 
            modelDA.setRowCount(0);
            modelPCDA.setRowCount(0); // Xóa bảng dưới
            
            for (DuAn_DTO da : ketQua) {
                if(trangThai.isEmpty() || da.getTrangThai().equals(trangThai)) {
                    String strNgayBD = (da.getNgayBatDau() != null) ? da.getNgayBatDau().format(dtf) : "";
                    String strNgayKT = (da.getNgayKetThuc() != null) ? da.getNgayKetThuc().format(dtf) : "";
                    modelDA.addRow(new Object[]{
                        da.getMaDa(), da.getTenDuAn(), strNgayBD, strNgayKT, da.getTrangThai(), da.getNguoiQuanLy()
                    });
                }
            }
            capNhatThongKe(ketQua);
        });
        cbTrangThai.addActionListener(e -> btnSearch.doClick());
    }
    
    public void addEventRefresh() {
        btnrefresh.addActionListener(e -> {
            txtSearch.setText("");
            cbTrangThai.setSelectedIndex(0);
            taiDuLieuLenBang();
        });
    }
    
    public void addEventAdd() {
        btnAdd.addActionListener(e -> {
            DuAn1_GUI formThem = new DuAn1_GUI(this); 
            formThem.setVisible(true);
        });
    }
    
    public void addEventEdit() {
        btnEdit.addActionListener(e -> {
            int row = tableDA.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dự án trong bảng để sửa thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String maDA = tableDA.getValueAt(row, 0).toString();
                String tenDA = tableDA.getValueAt(row, 1).toString();
                String strNgayBD = tableDA.getValueAt(row, 2) != null ? tableDA.getValueAt(row, 2).toString() : "";
                String strNgayKT = tableDA.getValueAt(row, 3) != null ? tableDA.getValueAt(row, 3).toString() : "";
                LocalDate ngayBD = strNgayBD.isEmpty() ? null : LocalDate.parse(strNgayBD, dtf);
                LocalDate ngayKT = strNgayKT.isEmpty() ? null : LocalDate.parse(strNgayKT, dtf);
                String trangThai = tableDA.getValueAt(row, 4).toString();
                String nguoiQuanLy = tableDA.getValueAt(row, 5) != null ? tableDA.getValueAt(row, 5).toString() : "";
                
                DuAn_DTO daEdit = new DuAn_DTO(maDA, tenDA, trangThai, nguoiQuanLy, ngayBD, ngayKT);
                DuAn1_GUI formSua = new DuAn1_GUI(this, daEdit);
                formSua.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi đọc dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    public void addEventDelete() {
        btnDelete.addActionListener(e -> {
            int row = tableDA.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dự án trong bảng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maDA = tableDA.getValueAt(row, 0).toString();
            String tenDA = tableDA.getValueAt(row, 1).toString();
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Bạn có chắc chắn muốn xóa dự án: " + tenDA + " (" + maDA + ") không?", 
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.xoaDuAn(maDA)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa dự án thành công!");
                    taiDuLieuLenBang(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại! Có thể dự án đang có nhân viên tham gia.", "Lỗi DB", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    public void addEventPhanCong() {
        btnPhanCong.addActionListener(e -> {
            int row = tableDA.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 Dự Án trước khi phân công nhân sự!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maDA = tableDA.getValueAt(row, 0).toString();
            String tenDA = tableDA.getValueAt(row, 1).toString();
            
            PhanCongDuAn_GUI formPC = new PhanCongDuAn_GUI(this, maDA, tenDA);
            formPC.setVisible(true);
        });
    }

    private JPanel taoOThongKe(String tieuDe, Color mauNen, JLabel lblSoLuong) {
        JPanel pnBox = new JPanel();
        pnBox.setLayout(new BoxLayout(pnBox, BoxLayout.Y_AXIS)); 
        pnBox.setBackground(mauNen);
        pnBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        
        JLabel lblTitle = new JLabel(tieuDe);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblSoLuong.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblSoLuong.setForeground(Color.DARK_GRAY);
        lblSoLuong.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pnBox.add(lblTitle);
        pnBox.add(Box.createVerticalStrut(10)); 
        pnBox.add(lblSoLuong);
        return pnBox;
    }
    
    private void capNhatThongKe(List<DuAn_DTO> danhSach) {
        int chuanBi = 0, dangThucHien = 0, hoanThanh = 0;
        for (DuAn_DTO da : danhSach) {
            if (da.getTrangThai().equalsIgnoreCase("Chuẩn bị")) chuanBi++;
            else if (da.getTrangThai().equalsIgnoreCase("Đang thực hiện")) dangThucHien++;
            else if (da.getTrangThai().equalsIgnoreCase("Hoàn thành")) hoanThanh++;
        }
        lblSoChuanBi.setText(String.valueOf(chuanBi));
        lblSoDangThucHien.setText(String.valueOf(dangThucHien));
        lblSoHoanThanh.setText(String.valueOf(hoanThanh));
    }

    private void taiDuLieu(){
        btnDown.addActionListener(e -> {
            
            ExcelExporter.exportJTableToExcel(tableDA); 
        });
    }

    
    
    public void setphanquyenManager(boolean kq) {
         btnAdd.setEnabled(kq);
         btnDelete.setEnabled(kq);
         btnEdit.setEnabled(kq);
         btnPhanCong.setEnabled(kq);
    }
}