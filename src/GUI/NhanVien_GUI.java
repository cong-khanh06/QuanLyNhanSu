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
import DAO.NhanVien_DAO;
import java.util.List;
import DTO.NhanVien_DTO;
import BUS.NhanVien_BUS;
import DTO.ChucVu_DTO;
import java.awt.event.MouseAdapter;
import DTO.Phongban_DTO;
import BUS.Phongban_BUS;
import BUS.ChucVu_BUS;
import java.awt.event.MouseEvent;
import BUS.ExcelExporter;
import java.awt.Cursor;
import javax.swing.JSplitPane;

public class NhanVien_GUI extends JPanel{
    JPanel pnSearchNV,pnHeader,pnToolBar;
    JTable tableNV;
    DefaultTableModel modelNV;
    JTextField txtSearch;
    JButton btnSearch,btnAdd,btnDown,btnrefresh;
    JComboBox<String> cbGioiTinh;
    JComboBox<Phongban_DTO> cbPhongBan;
    JComboBox<ChucVu_DTO> cbChucVu;
    JLabel lblTitle;
    NhanVien_BUS busnv=new NhanVien_BUS();
    Phongban_BUS buspb=new Phongban_BUS();
    ChucVu_BUS buscv=new ChucVu_BUS();
    
    List<Phongban_DTO> dsPhongBan;
    List<ChucVu_DTO> dsChucVu;
	 NhanVien2_GUI pndetail;
        
    private java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public NhanVien_GUI() {
        // NỀN XÁM TỔNG THỂ
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(226, 232, 240));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font modernFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ================= HEADER & TOOLBAR (Card Trắng) =================
        pnSearchNV = new JPanel(new BorderLayout());
        pnSearchNV.setBackground(Color.WHITE);
        // Viền Xanh dương
        pnSearchNV.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(59, 130, 246)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnHeader.setBackground(Color.WHITE);
        lblTitle = new JLabel("Danh Sách Nhân Viên");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 41, 59));
        pnHeader.add(lblTitle);

        // Chuyển sang FlowLayout cho gọn gàng, tự động xuống dòng nếu màn hình nhỏ
        pnToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnToolBar.setBackground(Color.WHITE);
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 36));
        txtSearch.setFont(modernFont);
        
        btnSearch = createFlatButton("Tìm kiếm", "primary");
        btnAdd = createFlatButton("Thêm", "primary");
        btnDown = createFlatButton("Tải Xuống", "success");
        btnrefresh = createFlatButton("Tải lại trang", "");

        cbGioiTinh = new JComboBox<>(new String[]{"Tất cả", "Nam", "Nữ"});
        cbPhongBan = new JComboBox<>(); loadComboPhongBanSearch();
        cbChucVu = new JComboBox<>(); loadComboChucVuSearch();
        
        dsPhongBan = buspb.layDanhSachPB();
        dsChucVu = buscv.layDanhSachChucVu();

        cbPhongBan.setPreferredSize(new Dimension(150, 36)); cbPhongBan.setFont(modernFont);
        cbChucVu.setPreferredSize(new Dimension(150, 36)); cbChucVu.setFont(modernFont);
        cbGioiTinh.setPreferredSize(new Dimension(100, 36)); cbGioiTinh.setFont(modernFont);

        pnToolBar.add(txtSearch); pnToolBar.add(btnSearch);
        pnToolBar.add(cbPhongBan); pnToolBar.add(cbChucVu); pnToolBar.add(cbGioiTinh);
        pnToolBar.add(btnAdd); pnToolBar.add(btnDown); pnToolBar.add(btnrefresh);
        
        pnSearchNV.add(pnHeader, BorderLayout.NORTH);
        pnSearchNV.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearchNV, BorderLayout.NORTH);

        // ================= BẢNG DỮ LIỆU =================
        String[] colsNV = {
            "Mã Nhân Viên","Nhân viên","Giới tính",
            "Ngày sinh","Địa chỉ","Liên hệ",
            "Phòng ban","Chức vụ","Trạng thái"
        };
        modelNV = new DefaultTableModel(colsNV, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableNV = new JTable(modelNV);
        styleTable(tableNV);
        
        pndetail = new NhanVien2_GUI();
        pndetail.setParentGUI(this);
        tableNV.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableNV.getSelectedRow();
                if (row != -1) {
                    String maNV = tableNV.getValueAt(row, 0).toString();
                    NhanVien_DTO nv = busnv.getNhanVienByID(maNV);
                    pndetail.hienThiNhanVien(nv); 
                }
            }
        });
        
        JScrollPane scrollTable = new JScrollPane(tableNV);
        scrollTable.setBorder(BorderFactory.createEmptyBorder());
        scrollTable.getViewport().setBackground(Color.WHITE);
        
        JPanel pnTableContainer = new JPanel(new BorderLayout());
        pnTableContainer.setBackground(Color.WHITE);
        pnTableContainer.add(scrollTable, BorderLayout.CENTER);
        
        // ================= SPLIT PANE =================
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnTableContainer, pndetail);
        splitPane.setResizeWeight(0.55); 
        splitPane.setContinuousLayout(true); 
        splitPane.setOneTouchExpandable(true); 
        splitPane.setDividerSize(10); 
        splitPane.setBorder(null); 
        splitPane.setOpaque(false); // Xóa nền của thanh kéo

        add(splitPane, BorderLayout.CENTER);
        
        addEventRefresh(); addEventAdd(); addEventSearch();
        taiDuLieu(); taiDuLieuLenBang();
    }

    // --- COPY 2 HÀM NÀY XUỐNG DƯỚI CLASS ---
    private JButton createFlatButton(String text, String style) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(110, 36));
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
    
    public void taiDuLieuLenBang(){
        modelNV.setRowCount(0);
        
        List<NhanVien_DTO> danhsach= busnv.layDanhSachNhanVien();
        
        for(NhanVien_DTO nv: danhsach){
            modelNV.addRow(new Object[]{
                nv.getMaNV(),
                nv.getHoTen(),
                nv.getGioiTinh() != null ? nv.getGioiTinh().getTenHienThi() : "",
                nv.getNgaySinh() != null ? nv.getNgaySinh().format(dtf) : "",
                nv.getDiaChi(),
                nv.getSdt(),
                getTenPhongBan(nv.getMaPB()),
                getTenChucVu(nv.getMaCV()),
                nv.getTrangThai() != null ? nv.getTrangThai().getTenHienThi() : ""
            });
        }
    }
    
    public void addEventSearch() {
        btnSearch.addActionListener(e -> {
            String tuKhoa = txtSearch.getText().trim();

            String gioiTinh = cbGioiTinh.getSelectedItem().toString();
            if (gioiTinh.equals("Tất cả")) {
                gioiTinh = "Tat ca";
            } else if (gioiTinh.equals("Nữ")) {
                gioiTinh = "Nu";
            }

            Phongban_DTO pbSelected = (Phongban_DTO) cbPhongBan.getSelectedItem();
            String maPB = (pbSelected != null && !pbSelected.getTenphongban().equals("Tất cả")) 
                      ? pbSelected.getMaphongban() : "Tat ca";
            ChucVu_DTO cvSelected =(ChucVu_DTO) cbChucVu.getSelectedItem();
            String maCV=(cvSelected != null && !cvSelected.getTenCV().equals("Tất cả"))?cvSelected.getMaCV():"Tat ca";
            NhanVien_BUS bus = new NhanVien_BUS();
            List<NhanVien_DTO> ketQua = bus.timKiemNhanVien(tuKhoa, gioiTinh, maPB, maCV);

            modelNV.setRowCount(0);
            for (NhanVien_DTO nv : ketQua) {
                modelNV.addRow(new Object[]{
                    nv.getMaNV(), nv.getHoTen(), nv.getGioiTinh() != null ? nv.getGioiTinh().getTenHienThi() : "",
                    nv.getNgaySinh() != null ? nv.getNgaySinh().format(dtf) : "", nv.getDiaChi(), nv.getSdt(),
                    getTenPhongBan(nv.getMaPB()), getTenChucVu(nv.getMaCV()), nv.getTrangThai() != null ? nv.getTrangThai().getTenHienThi() : ""
                });
            }
            
        });
    }
    
    public void addEventRefresh(){
        btnrefresh.addActionListener(e->{
            taiDuLieuLenBang();
        });
    }
    
    public void addEventAdd(){
        btnAdd.addActionListener(e -> {
            NhanVien1_GUI nv1=new NhanVien1_GUI();
            NhanVien_BUS busNv=new NhanVien_BUS();
            String maMoi=busNv.taoMaMoiNhat();
            nv1.setMaNV(maMoi);
            
            nv1.setVisible(true);
            taiDuLieuLenBang();
        });
    }
    
    
    
    private void loadComboPhongBanSearch() {
        List<Phongban_DTO> list = buspb.layDanhSachPB();

        cbPhongBan.removeAllItems();

        Phongban_DTO tatCa = new Phongban_DTO();
        tatCa.setMaphongban(""); 
        tatCa.setTenphongban("Tất cả"); 

        cbPhongBan.addItem(tatCa);

        for (Phongban_DTO pb : list) {
            cbPhongBan.addItem(pb);
        }
    }

    
    private void loadComboChucVuSearch(){
        List<ChucVu_DTO> list=buscv.layDanhSachChucVu();
        
        cbChucVu.removeAllItems();
        ChucVu_DTO tatCa=new ChucVu_DTO();
        tatCa.setMaCV("");
        tatCa.setTenCV("Tất cả");
        
        cbChucVu.addItem(tatCa);
        for(ChucVu_DTO cv:list){
            cbChucVu.addItem(cv);
        }
    }
    
    private String getTenPhongBan(String maPB) {
        if (maPB == null || maPB.isEmpty()) return "";
        for (Phongban_DTO pb : dsPhongBan) {
            if (pb.getMaphongban().equals(maPB)) {
                return pb.getTenphongban();
            }
        }
        return maPB; 
    }

    private String getTenChucVu(String maCV) {
        if (maCV == null || maCV.isEmpty()) return "";
        for (ChucVu_DTO cv : dsChucVu) {
            if (cv.getMaCV().equals(maCV)) {
                return cv.getTenCV();
            }
        }
        return maCV;     }
    public void Loaddatatheoma(String manv){
        modelNV.setRowCount(0);
        
        NhanVien_DTO nv= busnv.getNhanVienByID(manv);
        
       
            modelNV.addRow(new Object[]{
                nv.getMaNV(),
                nv.getHoTen(),
                nv.getGioiTinh(),
                nv.getNgaySinh() != null ? nv.getNgaySinh().format(dtf) : "",
                nv.getDiaChi(),
                nv.getSdt(),
                nv.getMaPB(),
                nv.getMaCV(),
                nv.getTrangThai()
            });
    }
    
    public void taiDuLieu(){
        btnDown.addActionListener(e->{
            ExcelExporter.exportJTableToExcel(tableNV);
        });
    }
    
    public void setphanquyenuser(boolean kiemtra,String manv)
    {
    	btnAdd.setEnabled(kiemtra);
    	btnDown.setEnabled(kiemtra);
    	btnSearch.setEnabled(kiemtra);
    	cbChucVu.setEnabled(kiemtra);
    	cbGioiTinh.setEnabled(kiemtra);
    	cbPhongBan.setEnabled(kiemtra);
    	btnrefresh.setEnabled(kiemtra);
    	Loaddatatheoma(manv);
    }
    public void setphanquyennut(boolean kq,String quyen)
    {
    	pndetail.setphanquyennut(kq,quyen);

    }
}
