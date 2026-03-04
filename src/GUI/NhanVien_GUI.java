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
        
        pnToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
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
        cbGioiTinh = new JComboBox<>(new String[]{"Tat ca", "Nam", "Nu"});
        cbPhongBan.setPreferredSize(new Dimension(150, 36));
        cbGioiTinh.setPreferredSize(new Dimension(120, 36));
        cbPhongBan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pnToolBar.add(txtSearch);
        pnToolBar.add(btnSearch);
        pnToolBar.add(cbPhongBan);
        pnToolBar.add(cbGioiTinh);
        pnToolBar.add(btnAdd);
        pnToolBar.add(btnDown);
        pnToolBar.add(btnrefresh);
        
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
                nv.getGioiTinh(),
                nv.getNgaySinh(),
                nv.getDiaChi(),
                nv.getSdt(),
                nv.getMaPB(),
                nv.getMaCV(),
                nv.getTrangThai()
            });
        }
    }
    
    public void addEventSearch() {
        btnSearch.addActionListener(e -> {
            // Lấy từ khóa
            String tuKhoa = txtSearch.getText().trim();

            // Lấy giới tính (Nếu chọn "Tất cả" thì gán bằng rỗng để DAO không lọc)
            String gioiTinh = cbGioiTinh.getSelectedItem().toString();
            if (gioiTinh.equals("Tất cả")) {
                gioiTinh = ""; 
            }

            // Lấy mã phòng ban từ Object cực nhàn
            Phongban_DTO pbSelected = (Phongban_DTO) cbPhongBan.getSelectedItem();
            String maPB = pbSelected != null ? pbSelected.getMaphongban() : "";

            // Gọi BUS để tìm kiếm
            NhanVien_BUS bus = new NhanVien_BUS();
            List<NhanVien_DTO> ketQua = bus.timKiemNhanVien(tuKhoa, gioiTinh, maPB);

            // Cập nhật lại JTable
            modelNV.setRowCount(0);
            for (NhanVien_DTO nv : ketQua) {
                modelNV.addRow(new Object[]{
                    nv.getMaNV(), nv.getHoTen(), nv.getGioiTinh(),
                    nv.getNgaySinh(), nv.getDiaChi(), nv.getSdt(),
                    nv.getMaPB(), nv.getMaCV(), nv.getTrangThai()
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
}
