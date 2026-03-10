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
        setLayout(new BorderLayout());
        
        
        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnHeader.setBackground(new Color(150, 214, 255));
        pnHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        lblTitle = new JLabel("Danh sách Ứng Lương");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnHeader.add(lblTitle);
        
        // --- TOOLBAR CO GIÃN TỰ ĐỘNG ---
        pnToolBar = new JPanel(new BorderLayout(10, 10)); // Dùng BorderLayout
        pnToolBar.setBackground(new Color(150, 214, 255));
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        
        // 1. Nhóm Tìm kiếm (Đặt ở CENTER để co giãn)
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
        
        btnDown.setPreferredSize(new Dimension(100, 36));
        btnEdit.setPreferredSize(new Dimension(100, 36));   
        btnDelete.setPreferredSize(new Dimension(100, 36));
        btnAdd.setPreferredSize(new Dimension(100, 36));
        btnrefresh.setPreferredSize(new Dimension(100, 36));
        
        cbTrangThai = new JComboBox<>(new String[]{"Tất cả", "Chờ duyệt", "Đã duyệt", "Từ chối"});
        cbTrangThai.setPreferredSize(new Dimension(150, 36));
        cbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pnAction.add(cbTrangThai); 
        pnAction.add(btnAdd);
        pnAction.add(btnEdit);   
        pnAction.add(btnDelete);
        pnAction.add(btnDown);
        pnAction.add(btnrefresh);
        
        
        pnToolBar.add(pnSearch, BorderLayout.CENTER);
        pnToolBar.add(pnAction, BorderLayout.EAST);
        
        pnSearchUL = new JPanel(new BorderLayout());
        pnSearchUL.setBackground(Color.WHITE);
        pnSearchUL.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        pnSearchUL.add(pnHeader, BorderLayout.NORTH);
        pnSearchUL.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearchUL, BorderLayout.NORTH);
        
        
        String[] colsUL = {
            "Mã Ứng Lương", "Mã Bảng Lương", "Ngày Ứng", "Số Tiền", "Lý Do", "Trạng Thái"
        };
        modelUL = new DefaultTableModel(colsUL, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tableUL = new JTable(modelUL);
        tableUL.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableUL.setRowHeight(28);
        tableUL.setShowGrid(true);
        tableUL.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        add(new JScrollPane(tableUL), BorderLayout.CENTER);
        
        
        JPanel pnThongKe = new JPanel(new GridLayout(1, 3, 20, 0)); 
        pnThongKe.setBackground(Color.WHITE);
        pnThongKe.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        lblSoChoDuyet = new JLabel();
        lblSoDaDuyet = new JLabel();
        lblSoTuChoi = new JLabel();
        
        pnThongKe.add(taoOThongKe("Chờ duyệt", new Color(255, 235, 153), lblSoChoDuyet));        
        pnThongKe.add(taoOThongKe("Đã duyệt", new Color(153, 204, 255), lblSoDaDuyet)); 
        pnThongKe.add(taoOThongKe("Từ chối", new Color(255, 153, 153), lblSoTuChoi));       
        
        add(pnThongKe, BorderLayout.SOUTH);

        addEventRefresh();
        addEventAdd();
        addEventSearch();
        addEventEdit();
        addEventDelete();
        
        taiDuLieuLenBang();
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