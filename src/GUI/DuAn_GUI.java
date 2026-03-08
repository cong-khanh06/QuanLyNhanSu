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
import java.awt.GridLayout;
import java.util.List;
import DTO.DuAn_DTO;
import BUS.DuAn_BUS; 
import java.awt.Component;
import java.time.LocalDate;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;

public class DuAn_GUI extends JPanel {
    JPanel pnSearchDA, pnHeader, pnToolBar;
    JTable tableDA;
    DefaultTableModel modelDA;
    JTextField txtSearch;
    JButton btnSearch, btnAdd, btnEdit, btnDelete, btnDown, btnrefresh;
    JComboBox<String> cbTrangThai; // Đổi combobox lọc theo trạng thái dự án
    JLabel lblTitle;
    DuAn_BUS bus=new DuAn_BUS();
    JLabel lblSoChuanBi, lblSoDangThucHien, lblSoHoanThanh; 
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    public DuAn_GUI() {
        setLayout(new BorderLayout());
        
        // --- HEADER ---
        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnHeader.setBackground(new Color(150, 214, 255));
        pnHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        lblTitle = new JLabel("Danh sách dự án");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnHeader.add(lblTitle);
        
        // --- TOOLBAR ---
        pnToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnToolBar.setBackground(new Color(150, 214, 255));
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(220, 36));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        btnSearch = new ButtonToolBar("Tìm kiếm");
        btnAdd = new ButtonToolBar("Thêm");
        btnEdit = new ButtonToolBar("Sửa");     // Nút mới
        btnDelete = new ButtonToolBar("Xóa");
        btnDown = new ButtonToolBar("Tải Xuống");
        btnrefresh = new ButtonToolBar("Tải lại trang");
        
        btnSearch.setPreferredSize(new Dimension(100, 36));
        btnDown.setPreferredSize(new Dimension(100, 36));
        btnEdit.setPreferredSize(new Dimension(100, 36));   
        btnDelete.setPreferredSize(new Dimension(100, 36));
        btnAdd.setPreferredSize(new Dimension(100, 36));
        btnrefresh.setPreferredSize(new Dimension(100, 36));
        
        // Combobox lọc theo Trạng thái dự án
        cbTrangThai = new JComboBox<>(new String[]{"Tất cả", "Chuẩn bị", "Đang thực hiện", "Hoàn thành"});
        cbTrangThai.setPreferredSize(new Dimension(150, 36));
        cbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pnToolBar.add(txtSearch);
        pnToolBar.add(btnSearch);
        pnToolBar.add(cbTrangThai); 
        pnToolBar.add(btnAdd);
        pnToolBar.add(btnEdit);   
        pnToolBar.add(btnDelete);
        pnToolBar.add(btnDown);
        pnToolBar.add(btnrefresh);
        
        // --- KHU VỰC PHÍA BẮC (HEADER + TOOLBAR) ---
        pnSearchDA = new JPanel(new BorderLayout());
        pnSearchDA.setBackground(Color.WHITE);
        pnSearchDA.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            )
        );
        pnSearchDA.add(pnHeader, BorderLayout.NORTH);
        pnSearchDA.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearchDA, BorderLayout.NORTH);
        
        // --- BẢNG DỮ LIỆU (TABLE) ---
        String[] colsDA = {
            "Mã Dự Án", "Tên Dự Án", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Trạng Thái"
        };
        modelDA = new DefaultTableModel(colsDA, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép edit trực tiếp trên bảng
            }
        };
        tableDA = new JTable(modelDA);
        tableDA.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableDA.setRowHeight(28);
        tableDA.setShowGrid(true);
        tableDA.setIntercellSpacing(new Dimension(0, 0));
        tableDA.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        add(new JScrollPane(tableDA), BorderLayout.CENTER);
        
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

        // --- GÁN SỰ KIỆN ---
        addEventRefresh();
        addEventAdd();
        addEventSearch();
        addEventEdit();
        addEventDelete();
        // Tải dữ liệu ban đầu
        taiDuLieuLenBang();
    }
    
    public void taiDuLieuLenBang() {
        modelDA.setRowCount(0); // Xóa trắng bảng
        
        List<DuAn_DTO> danhsach = bus.layDanhSachDuAn();
        
        for (DuAn_DTO da : danhsach) {
            modelDA.addRow(new Object[]{
                da.getMaDa(),
                da.getTenDuAn(),
                da.getNgayBatDau().format(dtf), 
                da.getNgayKetThuc().format(dtf),
                da.getTrangThai()
            });
        }
        capNhatThongKe(danhsach);
    }
    
    public void addEventSearch() {
        btnSearch.addActionListener(e -> {
            String tuKhoa = txtSearch.getText().trim();
            String trangThai = cbTrangThai.getSelectedItem().toString();
            if (trangThai.equals("Tất cả")) {
                trangThai = ""; 
            }

            List<DuAn_DTO> ketQua = bus.timKiemDuAn(tuKhoa); 
            
            // Cập nhật lại bảng
            modelDA.setRowCount(0);
            for (DuAn_DTO da : ketQua) {
                // Nếu có bộ lọc trạng thái thì kiểm tra thêm
                if(trangThai.isEmpty() || da.getTrangThai().equals(trangThai)) {
                    modelDA.addRow(new Object[]{
                        da.getMaDa(),
                        da.getTenDuAn(),
                        da.getNgayBatDau(),
                        da.getNgayKetThuc(),
                        da.getTrangThai()
                    });
                }
            }
            capNhatThongKe(ketQua);
        });
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
            // Truyền 'this' (chính là panel DuAn_GUI hiện tại) vào form thêm 
            // để form thêm có thể gọi hàm taiDuLieuLenBang() sau khi lưu thành công
            DuAn1_GUI formThem = new DuAn1_GUI(this); 
            formThem.setVisible(true);
        });
    }
    
    private JPanel taoOThongKe(String tieuDe, Color mauNen, JLabel lblSoLuong) {
        JPanel pnBox = new JPanel();
        pnBox.setLayout(new BoxLayout(pnBox, BoxLayout.Y_AXIS)); // Sắp xếp dọc
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
        pnBox.add(Box.createVerticalStrut(10)); // Khoảng cách giữa chữ và số
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
    
    public void addEventDelete() {
        btnDelete.addActionListener(e -> {
            // Lấy dòng đang được chọn trên JTable
            int row = tableDA.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dự án trong bảng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Lấy Mã dự án từ cột số 0 của dòng được chọn
            String maDA = tableDA.getValueAt(row, 0).toString();
            String tenDA = tableDA.getValueAt(row, 1).toString();
            
            // Hiển thị hộp thoại xác nhận
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Bạn có chắc chắn muốn xóa dự án: " + tenDA + " (" + maDA + ") không?", 
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.xoaDuAn(maDA)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa dự án thành công!");
                    taiDuLieuLenBang(); // Tải lại bảng và thống kê
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại! Có thể dự án đang có nhân viên tham gia.", "Lỗi DB", JOptionPane.ERROR_MESSAGE);
                }
            }
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
                // Lấy thông tin từ các cột của dòng đang chọn
                String maDA = tableDA.getValueAt(row, 0).toString();
                String tenDA = tableDA.getValueAt(row, 1).toString();
                LocalDate ngayBD = LocalDate.parse(tableDA.getValueAt(row, 2).toString(), dtf);
                LocalDate ngayKT = LocalDate.parse(tableDA.getValueAt(row, 3).toString(), dtf);
                String trangThai = tableDA.getValueAt(row, 4).toString();
                
                
                DuAn_DTO daEdit = new DuAn_DTO(maDA, tenDA, ngayBD, ngayKT, trangThai);
                
                
                DuAn1_GUI formSua = new DuAn1_GUI(this, daEdit);
                formSua.setVisible(true);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi đọc dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    public void setphanquyenManager(boolean kq)
    {
    	 btnAdd.setEnabled(kq);
    	 btnDelete.setEnabled(kq);
    	 btnEdit.setEnabled(kq);
    }
}