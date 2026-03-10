package GUI;

import BUS.ChucVu_BUS;
import DTO.ChucVu_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChucVu_GUI extends JPanel {
    JPanel pnSearch, pnHeader, pnToolBar;
    JTable tableCV; 
    DefaultTableModel modelCV; 
    JTextField txtSearch;
    JButton btnSearch, btnAdd, btnEdit, btnDelete, btnRefresh, btnQuanLyPhuCap;
    JLabel lblTitle;
    private String currentRole = "Admin";
    private String currentMaNV = "";
    
    ChucVu_BUS bus = new ChucVu_BUS();

    public ChucVu_GUI() {
        setLayout(new BorderLayout());

        // --- KHU VỰC TÌM KIẾM VÀ TOOLBAR ---
        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnHeader.setBackground(new Color(150, 214, 255));
        pnHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        lblTitle = new JLabel("Quản Lý Chức Vụ"); // Đổi lại tiêu đề
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnHeader.add(lblTitle);

        pnToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnToolBar.setBackground(new Color(150, 214, 255));
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(220, 36));
        
        btnSearch = new JButton("Tìm kiếm");
        btnAdd = new JButton("Thêm CV");
        btnEdit = new JButton("Sửa CV");
        btnDelete = new JButton("Xóa CV");
        btnRefresh = new JButton("Tải lại");
        btnQuanLyPhuCap = new JButton("Quản Lý Phụ Cấp"); 

        pnToolBar.add(txtSearch);
        pnToolBar.add(btnSearch);
        pnToolBar.add(btnAdd);
        pnToolBar.add(btnEdit);
        pnToolBar.add(btnDelete);
        pnToolBar.add(btnQuanLyPhuCap);
        pnToolBar.add(btnRefresh);

        pnSearch = new JPanel(new BorderLayout());
        pnSearch.add(pnHeader, BorderLayout.NORTH);
        pnSearch.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearch, BorderLayout.NORTH);

        // --- KHU VỰC BẢNG (CHỈ CÒN DUY NHẤT BẢNG CHỨC VỤ) ---
        String[] colsCV = {"Mã Chức Vụ", "Tên Chức Vụ", "Mô Tả"};
        modelCV = new DefaultTableModel(colsCV, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableCV = new JTable(modelCV);
        tableCV.setRowHeight(28);
        tableCV.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JScrollPane scrollCV = new JScrollPane(tableCV);
        scrollCV.setBorder(BorderFactory.createTitledBorder("Danh sách Chức Vụ"));

        // Add thẳng bảng vào giữa (Không dùng JSplitPane nữa)
        add(scrollCV, BorderLayout.CENTER);

        // --- SỰ KIỆN ---
        taiDuLieuLenBangCV();
        addEvents();
    }

    public void taiDuLieuLenBangCV() {
        modelCV.setRowCount(0);
        List<ChucVu_DTO> list = bus.layDanhSachChucVu();
        for (ChucVu_DTO cv : list) {
            modelCV.addRow(new Object[]{ cv.getMaCV(), cv.getTenCV(), cv.getMoTa() });
        }
    }

    private void addEvents() {
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            taiDuLieuLenBangCV();
        });

        btnSearch.addActionListener(e -> {
            String tuKhoa = txtSearch.getText().trim();
            List<ChucVu_DTO> ketQua = bus.timKiemChucVu(tuKhoa);
            modelCV.setRowCount(0);
            for (ChucVu_DTO cv : ketQua) {
                modelCV.addRow(new Object[]{
                    cv.getMaCV(), cv.getTenCV(), cv.getMoTa()
                });
            }
            if (ketQua.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả!");
            }
        });

        btnAdd.addActionListener(e -> {
            ChucVu_Dialog dialog = new ChucVu_Dialog(this);
            dialog.setVisible(true);
        });

        btnDelete.addActionListener(e -> {
            int row = tableCV.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maCV = tableCV.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa chức vụ " + maCV + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.xoaChucVu(maCV)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    taiDuLieuLenBangCV();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại! Có thể chức vụ này đang được gán cho nhân viên.", "Lỗi DB", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEdit.addActionListener(e -> {
            int row = tableCV.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maCV = tableCV.getValueAt(row, 0).toString();
            String tenCV = tableCV.getValueAt(row, 1) != null ? tableCV.getValueAt(row, 1).toString() : "";
            String moTa = tableCV.getValueAt(row, 2) != null ? tableCV.getValueAt(row, 2).toString() : "";

            ChucVu_DTO cvEdit = new ChucVu_DTO(maCV, tenCV, moTa);
            ChucVu_Dialog dialog = new ChucVu_Dialog(this, cvEdit);
            dialog.setVisible(true);
        });

        // SỰ KIỆN MỞ FORM QUẢN LÝ CHI TIẾT PHỤ CẤP
        btnQuanLyPhuCap.addActionListener(e -> {
            int row = tableCV.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một chức vụ trên bảng trước!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maCV = tableCV.getValueAt(row, 0).toString();
            String tenCV = tableCV.getValueAt(row, 1).toString();
            
            // Gọi màn hình ChiTietChucVu_Dialog lên
            Window parentWindow = SwingUtilities.getWindowAncestor(this);
            ChiTietChucVu_Dialog dialog = new ChiTietChucVu_Dialog(parentWindow, maCV, tenCV);
            dialog.setPhanQuyen(currentRole);
            dialog.setVisible(true);
        });
    }

    public void setPhanQuyen(String quyen, String maNV) {
        this.currentRole = quyen;
        this.currentMaNV = maNV;

        if (quyen.equalsIgnoreCase("User")) {
            // 1. Chỉ load chức vụ của chính nhân viên đó
            taiDuLieuLenBangUser(maNV);
            
            // 2. Ẩn toàn bộ các nút tác động dữ liệu
            btnAdd.setVisible(false);
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);            
            txtSearch.setVisible(false);
            btnSearch.setVisible(false);
            btnRefresh.setVisible(false);
            
        } 
        else if (quyen.equalsIgnoreCase("Manager")) {
            taiDuLieuLenBangCV(); 
            
            btnDelete.setVisible(false); 
            
        }
        
        pnToolBar.revalidate();
        pnToolBar.repaint();
    }

    private void taiDuLieuLenBangUser(String maNV) {
        modelCV.setRowCount(0); 
        List<ChucVu_DTO> list = bus.layChucVuTheoNV(maNV);
        if (list != null) {
            for (ChucVu_DTO cv : list) {
                modelCV.addRow(new Object[]{ cv.getMaCV(), cv.getTenCV(), cv.getMoTa() });
            }
        }
        
        if (tableCV.getRowCount() > 0) {
            tableCV.setRowSelectionInterval(0, 0);
        }
    }
}