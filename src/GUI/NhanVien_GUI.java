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
    public NhanVien_GUI(){
        setLayout(new BorderLayout());
        
        
        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnHeader.setBackground(new Color(150,214,255));
        pnHeader.setBorder(
            BorderFactory.createEmptyBorder(10, 15, 5, 15)
        );

        lblTitle = new JLabel("Danh sách nhân viên");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));

        pnHeader.add(lblTitle);
        
        pnToolBar = new JPanel(new java.awt.GridBagLayout());
        pnToolBar.setBackground(new Color(150,214,255));
        pnToolBar.setBorder(
            BorderFactory.createEmptyBorder(5, 15, 10, 15)
        );
        
        txtSearch=new JTextField();
        txtSearch.setPreferredSize(new Dimension(220, 36));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnSearch=new ButtonToolBar("Tìm kiếm");
        btnAdd=new ButtonToolBar("Thêm");
        btnDown=new ButtonToolBar("Tải Xuống");
        btnrefresh=new ButtonToolBar("Tải lại trang");
        btnSearch.setPreferredSize(new Dimension(100, 36));
        btnDown.setPreferredSize(new Dimension(100, 36));
        btnAdd.setPreferredSize(new Dimension(100,36));
        btnrefresh.setPreferredSize(new Dimension(100,36));
        
        cbPhongBan = new JComboBox<>();
        loadComboPhongBanSearch();
        cbChucVu=new JComboBox<>();   
        loadComboChucVuSearch();
        dsPhongBan = buspb.layDanhSachPB();
        dsChucVu = buscv.layDanhSachChucVu();
        cbGioiTinh = new JComboBox<>(new String[]{"Tất cả", "Nam", "Nữ"});
        cbPhongBan.setPreferredSize(new Dimension(150, 36));
        cbGioiTinh.setPreferredSize(new Dimension(120, 36));
        cbPhongBan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL; 
        gbc.insets = new java.awt.Insets(0, 0, 0, 10); 
        gbc.gridy = 0; 

        gbc.weightx = 1.0;
        pnToolBar.add(txtSearch, gbc);
        gbc.weightx = 0;
        pnToolBar.add(btnSearch, gbc);
        pnToolBar.add(cbPhongBan, gbc);
        pnToolBar.add(cbChucVu, gbc);
        pnToolBar.add(cbGioiTinh, gbc);
        pnToolBar.add(btnAdd, gbc);
        pnToolBar.add(btnDown, gbc);
        pnToolBar.add(btnrefresh, gbc);
        
        pnSearchNV = new JPanel(new BorderLayout());
        pnSearchNV.setBackground(Color.WHITE);
        pnSearchNV.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            )
        );
        pnSearchNV.setBackground(Color.blue);
        pnSearchNV.add(pnHeader,BorderLayout.NORTH);
        pnSearchNV.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearchNV,BorderLayout.NORTH);
        
        String[] colsNV={
                "Mã Nhân Viên","Nhân viên","Giới tính",
                "Ngày sinh","Địa chỉ","Liên hệ",
                "Phòng ban","Chức vụ","Trạng thái"
        };
        modelNV=new DefaultTableModel(colsNV,0){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        tableNV=new JTable(modelNV);
        tableNV.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableNV.setRowHeight(28);
        tableNV.setShowGrid(true);
        tableNV.setIntercellSpacing(new Dimension(0, 0));
        tableNV.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableNV.setPreferredSize(new Dimension(1080,690));
        
        add(new JScrollPane(tableNV),BorderLayout.CENTER);
     
        pndetail=new NhanVien2_GUI();
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
        add(pndetail,BorderLayout.SOUTH);
        addEventRefresh();
        addEventAdd();
        addEventSearch();
        taiDuLieu();
        taiDuLieuLenBang();
        
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
