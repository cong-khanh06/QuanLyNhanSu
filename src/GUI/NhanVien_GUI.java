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
import java.util.ArrayList;

public class NhanVien_GUI extends JPanel{
    JPanel pnSearchNV,pnHeader,pnToolBar,pnSearchGroup;
    JTable tableNV;
    DefaultTableModel modelNV;
    JTextField txtSearch;
    JButton btnSearch,btnAdd,btnDown,btnXoa;
    JComboBox<String> cbPhongBan,cbGioiTinh;
    JLabel lblTitle;
    
    
    
    public NhanVien_GUI(){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1080,800));
        
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
        btnXoa=new ButtonToolBar("Xóa");
        btnDown=new ButtonToolBar("Tải Xuống");
        btnSearch.setPreferredSize(new Dimension(100, 36));
        btnDown.setPreferredSize(new Dimension(100, 36));
        btnAdd.setPreferredSize(new Dimension(100,36));
        btnXoa.setPreferredSize(new Dimension(100,36));

        
        cbPhongBan = new JComboBox<>(new String[]{"Tat ca","Trưởng phòng", "Kế toán", "Nhân viên","Kỹ thuật"});
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
        pnToolBar.add(btnXoa);
        pnToolBar.add(btnDown);
        
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
        taiDuLieuLenBang();
    }
    
    public void taiDuLieuLenBang(){
        modelNV.setRowCount(0);
        
        NhanVien_DAO dao=new NhanVien_DAO();
        List<Object[]> danhsach=(List<Object[]>) dao.layDanhSachNV();
        
        for(Object[] row: danhsach){
            modelNV.addRow(row);
        }
    }
}
