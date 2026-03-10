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

public class DuAn_GUI extends JPanel {
    JPanel pnSearchDA, pnHeader, pnToolBar;
    
    // Đã khai báo thêm tablePCDA và modelPCDA ở đây
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
        setLayout(new BorderLayout());
        

        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnHeader.setBackground(new Color(150, 214, 255));
        pnHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        lblTitle = new JLabel("Danh sách dự án");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnHeader.add(lblTitle);
        

        pnToolBar = new JPanel(new BorderLayout(10, 10));

        pnToolBar.setBackground(new Color(150, 214, 255));
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        
        JPanel pnSearch = new JPanel(new BorderLayout(5, 0));
        pnSearch.setBackground(new Color(150, 214, 255));
        
        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        btnSearch = new ButtonToolBar("Tìm kiếm");
        btnSearch.setPreferredSize(new Dimension(100, 36));
        
        pnSearch.add(txtSearch, BorderLayout.CENTER);
        pnSearch.add(btnSearch, BorderLayout.EAST);
        
        JPanel pnAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnAction.setBackground(new Color(150, 214, 255));
        
        btnAdd = new ButtonToolBar("Thêm");
        btnEdit = new ButtonToolBar("Sửa");     
        btnDelete = new ButtonToolBar("Xóa");
        btnDown = new ButtonToolBar("Tải Xuống");
        btnrefresh = new ButtonToolBar("Tải lại trang");
        btnPhanCong = new ButtonToolBar("Phân công nhân sự"); // Nút phân công
        
        btnDown.setPreferredSize(new Dimension(100, 36));
        btnEdit.setPreferredSize(new Dimension(80, 36));   
        btnDelete.setPreferredSize(new Dimension(80, 36));
        btnAdd.setPreferredSize(new Dimension(80, 36));
        btnPhanCong.setPreferredSize(new Dimension(140, 36));
        btnrefresh.setPreferredSize(new Dimension(100, 36));
        
        cbTrangThai = new JComboBox<>(new String[]{"Tất cả", "Chuẩn bị", "Đang thực hiện", "Hoàn thành"});
        cbTrangThai.setPreferredSize(new Dimension(130, 36));
        cbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pnAction.add(cbTrangThai); 
        pnAction.add(btnAdd);
        pnAction.add(btnEdit);   
        pnAction.add(btnDelete);
        pnAction.add(btnPhanCong);
        pnAction.add(btnDown);
        pnAction.add(btnrefresh);
        
        pnToolBar.add(pnSearch, BorderLayout.CENTER);
        pnToolBar.add(pnAction, BorderLayout.EAST);
        
        pnSearchDA = new JPanel(new BorderLayout());
        pnSearchDA.setBackground(Color.WHITE);
        pnSearchDA.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        pnSearchDA.add(pnHeader, BorderLayout.NORTH);
        pnSearchDA.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearchDA, BorderLayout.NORTH);


        
        String[] colsDA = {"Mã Dự Án", "Tên Dự Án", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Trạng Thái", "Người quản lý"};

        modelDA = new DefaultTableModel(colsDA, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableDA = new JTable(modelDA);
        tableDA.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableDA.setRowHeight(28);
        tableDA.setShowGrid(true);
        tableDA.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane scrollDA = new JScrollPane(tableDA);
        
        // ========================================================
        // --- 2. BẢNG CHI TIẾT NHÂN SỰ (BẢNG DƯỚI - DETAIL) ---
        // ========================================================
        JPanel pnChiTiet = new JPanel(new BorderLayout());
        pnChiTiet.setBackground(Color.WHITE);
        pnChiTiet.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Chi tiết nhân sự tham gia", 
            0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.BLUE
        ));

        String[] colsPCDA = {"Mã NV", "Tên Nhân Viên", "Vai Trò Đảm Nhận"};
        modelPCDA = new DefaultTableModel(colsPCDA, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablePCDA = new JTable(modelPCDA);
        tablePCDA.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablePCDA.setRowHeight(28);
        tablePCDA.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnChiTiet.add(new JScrollPane(tablePCDA), BorderLayout.CENTER);
        
        // --- 3. GHÉP 2 BẢNG VÀO SPLIT PANE ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollDA, pnChiTiet);
        splitPane.setResizeWeight(0.6); // Bảng trên 60%, bảng dưới 40%
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerSize(8);
        
        add(splitPane, BorderLayout.CENTER);
        
        // --- THỐNG KÊ ---
        JPanel pnThongKe = new JPanel(new GridLayout(1, 3, 20, 0)); 
        pnThongKe.setBackground(Color.WHITE);
        pnThongKe.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        lblSoChuanBi = new JLabel("0");
        lblSoDangThucHien = new JLabel("0");
        lblSoHoanThanh = new JLabel("0");
        
        pnThongKe.add(taoOThongKe("Chuẩn bị", new Color(255, 235, 153), lblSoChuanBi));        
        pnThongKe.add(taoOThongKe("Đang thực hiện", new Color(153, 204, 255), lblSoDangThucHien)); 
        pnThongKe.add(taoOThongKe("Hoàn thành", new Color(153, 255, 153), lblSoHoanThanh));       
        
        add(pnThongKe, BorderLayout.SOUTH);

        addEventRefresh();
        addEventAdd();
        addEventSearch();
        addEventEdit();
        addEventDelete();

        addEventPhanCong();
        addEventClickTableDA(); 
 
        taiDuLieuLenBang();
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

    

    
    
    public void setphanquyenManager(boolean kq) {
         btnAdd.setEnabled(kq);
         btnDelete.setEnabled(kq);
         btnEdit.setEnabled(kq);
         btnPhanCong.setEnabled(kq);
    }
}