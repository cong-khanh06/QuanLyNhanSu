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
import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;

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
    NhanVien_DAO dao=new NhanVien_DAO();
    List<Phongban_DTO> dsPhongBan;
    List<ChucVu_DTO> dsChucVu;
    
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
        dsPhongBan = dao.layDanhSachPB();
        dsChucVu = dao.layDanhSachCV();
        cbGioiTinh = new JComboBox<>(new String[]{"Tất cả", "Nam", "Nữ"});
        cbPhongBan.setPreferredSize(new Dimension(150, 36));
        cbGioiTinh.setPreferredSize(new Dimension(120, 36));
        cbPhongBan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL; // Cho phép co giãn theo chiều ngang
        gbc.insets = new java.awt.Insets(0, 0, 0, 10); // Tạo khoảng cách (margin) 10px bên phải mỗi component
        gbc.gridy = 0; // Đặt tất cả trên cùng 1 hàng ngang (row 0)

        // 3. Cấu hình riêng cho Ô tìm kiếm (txtSearch) giãn ra chiếm toàn bộ khoảng trống
        gbc.weightx = 1.0; // Trọng số 1.0 nghĩa là nó sẽ hút hết không gian thừa
        pnToolBar.add(txtSearch, gbc);

        // 4. Các nút và ComboBox còn lại giữ nguyên kích thước (weightx = 0)
        gbc.weightx = 0;
        pnToolBar.add(btnSearch, gbc);
        pnToolBar.add(cbPhongBan, gbc);
        pnToolBar.add(cbChucVu, gbc);
        pnToolBar.add(cbGioiTinh, gbc);
        pnToolBar.add(btnAdd, gbc);
        pnToolBar.add(btnDown, gbc);
        
        // Nút cuối cùng không cần khoảng cách bên phải nữa
        gbc.insets = new java.awt.Insets(0, 0, 0, 0); 
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
        
        NhanVien2_GUI pndetail=new NhanVien2_GUI();
        pndetail.setParentGUI(this);
        
        tableNV.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableNV.getSelectedRow();
                if (row != -1) {
                    String maNV = tableNV.getValueAt(row, 0).toString();
                    NhanVien_DTO nv = dao.getNhanVienById(maNV);
                    pndetail.hienThiNhanVien(nv); // Chuyền banh xuống panel dưới!
                }
            }
        });
        add(pndetail,BorderLayout.SOUTH);
        addEventRefresh();
        addEventAdd();
        addEventSearch();
        taiDuLieuLenBang();
        
    }
    
    public void taiDuLieuLenBang(){
        modelNV.setRowCount(0);
        
        List<NhanVien_DTO> danhsach= dao.layDanhSachNV();
        
        for(NhanVien_DTO nv: danhsach){
            modelNV.addRow(new Object[]{
                nv.getMaNV(),
                nv.getHoTen(),
                nv.getGioiTinh() != null ? nv.getGioiTinh().getTenHienThi() : "",
                nv.getNgaySinh(),
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

            // Lấy giới tính (Nếu chọn "Tất cả" thì gán bằng rỗng để DAO không lọc)
            String gioiTinh = cbGioiTinh.getSelectedItem().toString();
            if (gioiTinh.equals("Tất cả")) {
                gioiTinh = "Tat ca";
            } else if (gioiTinh.equals("Nữ")) {
                gioiTinh = "Nu";
            }

            // Lấy mã phòng ban từ Object cực nhàn
            Phongban_DTO pbSelected = (Phongban_DTO) cbPhongBan.getSelectedItem();
            String maPB = (pbSelected != null && !pbSelected.getTenphongban().equals("Tất cả")) 
                      ? pbSelected.getMaphongban() : "Tat ca";
            ChucVu_DTO cvSelected =(ChucVu_DTO) cbChucVu.getSelectedItem();
            String maCV=(cvSelected != null && !cvSelected.getTenCV().equals("Tất cả"))?cvSelected.getMaCV():"Tat ca";
            // Gọi BUS để tìm kiếm
            NhanVien_BUS bus = new NhanVien_BUS();
            List<NhanVien_DTO> ketQua = bus.timKiemNhanVien(tuKhoa, gioiTinh, maPB, maCV);

            // Cập nhật lại JTable
            modelNV.setRowCount(0);
            for (NhanVien_DTO nv : ketQua) {
                modelNV.addRow(new Object[]{
                    nv.getMaNV(), nv.getHoTen(), nv.getGioiTinh() != null ? nv.getGioiTinh().getTenHienThi() : "",
                    nv.getNgaySinh(), nv.getDiaChi(), nv.getSdt(),
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
        List<Phongban_DTO> list = dao.layDanhSachPB();

        cbPhongBan.removeAllItems();

        // Tạo một DTO "giả" làm tùy chọn Tất cả
        Phongban_DTO tatCa = new Phongban_DTO();
        tatCa.setMaphongban(""); // Mã rỗng để ngầm hiểu là không lọc
        tatCa.setTenphongban("Tất cả"); // Tên hiển thị nhờ hàm toString()

        cbPhongBan.addItem(tatCa); // Nhét tùy chọn "Tất cả" lên đầu tiên

        // Đổ danh sách phòng ban thật từ Database vào
        for (Phongban_DTO pb : list) {
            cbPhongBan.addItem(pb);
        }
    }
    
    private void loadComboChucVuSearch(){
        List<ChucVu_DTO> list=dao.layDanhSachCV();
        
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
        return maPB; // Nếu không tìm thấy thì hiện mã luôn
    }

    private String getTenChucVu(String maCV) {
        if (maCV == null || maCV.isEmpty()) return "";
        for (ChucVu_DTO cv : dsChucVu) {
            if (cv.getMaCV().equals(maCV)) {
                return cv.getTenCV();
            }
        }
        return maCV; // Nếu không tìm thấy thì hiện mã luôn
    }
}
