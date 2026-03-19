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
import java.util.ArrayList;
import java.util.List;
import DTO.UngLuong_DTO;
import BUS.UngLuong_BUS; 
import java.awt.Component;
import java.awt.Cursor;
import java.time.LocalDate;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;

public class UngLuong_GUI extends JPanel {
    JPanel pnSearchUL, pnHeader, pnToolBar;
    JTable tableUL;
    DefaultTableModel modelUL;
    JTextField txtSearch;
    JButton btnSearch, btnAdd, btnEdit, btnDelete, btnDown, btnrefresh;
    JComboBox<String> cbTrangThai; 
    JLabel lblTitle;
    UngLuong_BUS bus = new UngLuong_BUS();
    JLabel lblSoChoDuyet, lblSoDaDuyet, lblSoTuChoi; 
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private boolean ischeck=true;
    private String manhanvien="";
    
    public UngLuong_GUI() {
        // 1. NỀN TỔNG THỂ XÁM NHẠT
        setBackground(new Color(226, 232, 240)); 
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        Font modernFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 14);

        // ================= PHẦN HEADER & TOOLBAR (Card Trắng) =================
        pnSearchUL = new JPanel(new BorderLayout());
        pnSearchUL.setBackground(Color.WHITE);
        // Viền xanh dương ở trên cùng tạo điểm nhấn
        pnSearchUL.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(59, 130, 246)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnHeader.setBackground(Color.WHITE);
        lblTitle = new JLabel("Danh sách Ứng Lương");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 41, 59));
        pnHeader.add(lblTitle);
        
        pnToolBar = new JPanel(new BorderLayout(10, 10)); 
        pnToolBar.setBackground(Color.WHITE);
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        JPanel pnSearch = new JPanel(new BorderLayout(5, 0));
        pnSearch.setBackground(Color.WHITE);
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(250, 36));
        txtSearch.setFont(modernFont);
        
        // Chuyển sang dùng JButton thường kết hợp FlatLaf thay vì ButtonToolBar để giao diện mượt nhất
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setPreferredSize(new Dimension(100, 36));
        btnSearch.putClientProperty("FlatLaf.styleClass", "primary");
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pnSearch.add(txtSearch, BorderLayout.CENTER);
        pnSearch.add(btnSearch, BorderLayout.EAST);
        
        JPanel pnAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnAction.setBackground(Color.WHITE);
        
        cbTrangThai = new JComboBox<>(new String[]{"Tất cả", "Chờ duyệt", "Đã duyệt", "Từ chối"});
        cbTrangThai.setPreferredSize(new Dimension(150, 36));
        cbTrangThai.setFont(modernFont);

        btnAdd = createFlatButton("Thêm", "primary");
        btnEdit = createFlatButton("Sửa", "");     
        btnDelete = createFlatButton("Xóa", "danger");
        btnDown = createFlatButton("Tải Xuống", "success");
        btnrefresh = createFlatButton("Tải lại", "");
        
        pnAction.add(cbTrangThai); pnAction.add(btnAdd); pnAction.add(btnEdit);   
        pnAction.add(btnDelete); pnAction.add(btnDown); pnAction.add(btnrefresh);
        
        pnToolBar.add(pnSearch, BorderLayout.CENTER);
        pnToolBar.add(pnAction, BorderLayout.EAST);
        
        pnSearchUL.add(pnHeader, BorderLayout.NORTH);
        pnSearchUL.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearchUL, BorderLayout.NORTH);
        
        // ================= PHẦN BẢNG DỮ LIỆU =================
        String[] colsUL = {"Mã Ứng Lương", "Mã Bảng Lương", "Ngày Ứng", "Số Tiền", "Lý Do", "Trạng Thái"};
        modelUL = new DefaultTableModel(colsUL, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableUL = new JTable(modelUL);
        styleTable(tableUL); // Gọi hàm style bảng
        
        JScrollPane scrollPane = new JScrollPane(tableUL);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        JPanel pnTableCard = new JPanel(new BorderLayout());
        pnTableCard.setBackground(Color.WHITE);
        pnTableCard.add(scrollPane, BorderLayout.CENTER);
        add(pnTableCard, BorderLayout.CENTER);
        
        // ================= PHẦN THỐNG KÊ (Card Đẹp) =================
        JPanel pnThongKe = new JPanel(new GridLayout(1, 3, 20, 0)); 
        pnThongKe.setBackground(new Color(226, 232, 240)); // Nền xám hòa vào nền tổng thể
        pnThongKe.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        lblSoChoDuyet = new JLabel("0"); lblSoDaDuyet = new JLabel("0"); lblSoTuChoi = new JLabel("0");
        
        // Phối màu hiện đại: Cam cho Chờ, Xanh cho Đã duyệt, Đỏ cho Từ chối
        pnThongKe.add(taoOThongKe("Chờ duyệt", new Color(245, 158, 11), lblSoChoDuyet));        
        pnThongKe.add(taoOThongKe("Đã duyệt", new Color(16, 185, 129), lblSoDaDuyet)); 
        pnThongKe.add(taoOThongKe("Từ chối", new Color(239, 68, 68), lblSoTuChoi));       
        
        add(pnThongKe, BorderLayout.SOUTH);

        addEventRefresh(); addEventAdd(); addEventSearch();
        addEventEdit(); addEventDelete();
        
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

    // Hàm trang trí bảng chuẩn Web
    private void styleTable(JTable tb) {
        tb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tb.setRowHeight(30);
        tb.setShowVerticalLines(false);
        tb.setShowHorizontalLines(true);
        tb.setGridColor(new Color(230, 230, 230));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tb.getTableHeader().setBackground(new Color(248, 250, 252));
    }

    // Thiết kế lại ô thống kê
    private JPanel taoOThongKe(String tieuDe, Color accentColor, JLabel lblSoLuong) {
        JPanel pnBox = new JPanel(new BorderLayout());
        pnBox.setBackground(Color.WHITE);
        // Viền màu sắc ở bên trái tạo điểm nhấn
        pnBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accentColor),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel lblTitle = new JLabel(tieuDe);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(new Color(100, 116, 139)); // Chữ xám dịu
        
        lblSoLuong.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblSoLuong.setForeground(new Color(30, 41, 59));
        
        pnBox.add(lblTitle, BorderLayout.NORTH);
        pnBox.add(lblSoLuong, BorderLayout.SOUTH);
        return pnBox;
    }
    
    public void taiDuLieuLenBang() {
        modelUL.setRowCount(0); 
        List<UngLuong_DTO> danhsach=new ArrayList<>();
        if (!ischeck) {
            danhsach = bus.getdanhsachUngLuonguser(manhanvien);
        } else {
            danhsach = bus.layDanhSachUngLuong();
        }
        
        for (UngLuong_DTO ul : danhsach) {
            String strNgayUng = (ul.getNgayUng() != null) ? ul.getNgayUng().format(dtf) : "";
            modelUL.addRow(new Object[]{
                ul.getMaUL(), ul.getMaBL(), strNgayUng, ul.getSoTien().toString(), ul.getLyDo(), ul.getTrangThai()
            });
        }
        capNhatThongKe(danhsach);
    }
    
    public void addEventSearch() {
        btnSearch.addActionListener(e -> {
            String tuKhoa = txtSearch.getText().trim();
            String trangThai = cbTrangThai.getSelectedItem().toString();
            if (trangThai.equals("Tất cả")) trangThai = ""; 

            List<UngLuong_DTO> ketQua;
            if (!ischeck) {
                ketQua = bus.getdanhsachUngLuonguser(manhanvien);
            } else {
                String key = txtSearch.getText().trim();
                ketQua = bus.timKiemUngLuong(key);
            }
            modelUL.setRowCount(0);
            for (UngLuong_DTO ul : ketQua) {
                
                if(trangThai.isEmpty() || ul.getTrangThai().trim().equalsIgnoreCase(trangThai)) {
                    String strNgayUng = (ul.getNgayUng() != null) ? ul.getNgayUng().format(dtf) : "";
                    modelUL.addRow(new Object[]{
                        ul.getMaUL(), ul.getMaBL(), strNgayUng, ul.getSoTien().toString(), ul.getLyDo(), ul.getTrangThai()
                    });
                }
            }
            capNhatThongKe(ketQua);
        });
        
        cbTrangThai.addActionListener(e -> {
            btnSearch.doClick();
        });
    }

    private void capNhatThongKe(List<UngLuong_DTO> danhSach) {
        int choDuyet = 0, daDuyet = 0, tuChoi = 0;
        for (UngLuong_DTO ul : danhSach) {
            String tt = ul.getTrangThai().trim();
            if (tt.equalsIgnoreCase("Chờ duyệt")) choDuyet++;
            else if (tt.equalsIgnoreCase("Đã duyệt")) daDuyet++;
            else if (tt.equalsIgnoreCase("Từ chối")) tuChoi++;
        }
        lblSoChoDuyet.setText(String.valueOf(choDuyet));
        lblSoDaDuyet.setText(String.valueOf(daDuyet));
        lblSoTuChoi.setText(String.valueOf(tuChoi));
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
            UngLuong1_GUI formThem = new UngLuong1_GUI(this); 
            formThem.setVisible(true);
        });
    }
    
    public void addEventEdit() {
        btnEdit.addActionListener(e -> {
            int row = tableUL.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu ứng lương để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                String maUL = tableUL.getValueAt(row, 0).toString();
                String maBL = tableUL.getValueAt(row, 1).toString();
                String strNgayUng = tableUL.getValueAt(row, 2) != null ? tableUL.getValueAt(row, 2).toString() : "";
                LocalDate ngayUng = strNgayUng.isEmpty() ? null : LocalDate.parse(strNgayUng, dtf);
                BigDecimal soTien = new BigDecimal(tableUL.getValueAt(row, 3).toString());
                String lyDo = tableUL.getValueAt(row, 4).toString();
                String trangThai = tableUL.getValueAt(row, 5).toString();
                
                UngLuong_DTO ulEdit = new UngLuong_DTO(maUL, maBL, lyDo, trangThai, ngayUng, soTien);
                UngLuong1_GUI formSua = new UngLuong1_GUI(this, ulEdit,this.ischeck);
                formSua.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi đọc dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    public void addEventDelete() {
        btnDelete.addActionListener(e -> {
            int row = tableUL.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu ứng lương để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maUL = tableUL.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Bạn có chắc muốn xóa phiếu ứng lương: " + maUL + "?", 
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.xoaUngLuong(maUL)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                    taiDuLieuLenBang(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi DB", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    public void setphanquyenUser(boolean kq, String manv) {
        this.ischeck=kq;
        this.manhanvien=manv;

            btnDelete.setVisible(false);
            txtSearch.setVisible(false); 
            taiDuLieuLenBang();
        
    }
    public void setphanquyenManager(boolean kq)
    {
    
        btnDelete.setVisible(kq);
        taiDuLieuLenBang();
    }
    
}