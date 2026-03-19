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
        // 1. NỀN TỔNG THỂ XÁM NHẠT
        setBackground(new Color(226, 232, 240)); 
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        Font modernFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ================= PHẦN HEADER & TOOLBAR =================
        pnSearch = new JPanel(new BorderLayout());
        pnSearch.setBackground(Color.WHITE);
        // Viền Tím ở trên cùng
        pnSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(139, 92, 246)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnHeader.setBackground(Color.WHITE);
        lblTitle = new JLabel("Quản Lý Chức Vụ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 41, 59));
        pnHeader.add(lblTitle);

        pnToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnToolBar.setBackground(Color.WHITE);
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(250, 36));
        txtSearch.setFont(modernFont);
        
        btnSearch = createFlatButton("Tìm kiếm", "primary");
        btnAdd = createFlatButton("Thêm CV", "primary");
        btnEdit = createFlatButton("Sửa CV", "");
        btnDelete = createFlatButton("Xóa CV", "danger");
        btnQuanLyPhuCap = createFlatButton("Quản Lý Phụ Cấp", "success"); 
        btnRefresh = createFlatButton("Tải lại", "");

        pnToolBar.add(txtSearch); pnToolBar.add(btnSearch); pnToolBar.add(btnAdd);
        pnToolBar.add(btnEdit); pnToolBar.add(btnDelete); pnToolBar.add(btnQuanLyPhuCap); pnToolBar.add(btnRefresh);

        pnSearch.add(pnHeader, BorderLayout.NORTH);
        pnSearch.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearch, BorderLayout.NORTH);

        // ================= PHẦN BẢNG DỮ LIỆU =================
        String[] colsCV = {"Mã Chức Vụ", "Tên Chức Vụ", "Mô Tả"};
        modelCV = new DefaultTableModel(colsCV, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableCV = new JTable(modelCV);
        styleTable(tableCV);
        
        JScrollPane scrollCV = new JScrollPane(tableCV);
        scrollCV.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollCV.getViewport().setBackground(Color.WHITE);
        
        JPanel pnTableCard = new JPanel(new BorderLayout());
        pnTableCard.setBackground(Color.WHITE);
        pnTableCard.add(scrollCV, BorderLayout.CENTER);
        
        add(pnTableCard, BorderLayout.CENTER);

        taiDuLieuLenBangCV();
        addEvents();
    }

    // --- THÊM 2 HÀM NÀY VÀO TRONG CLASS ChucVu_GUI ---
    private JButton createFlatButton(String text, String style) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(130, 36)); // Rộng hơn một chút cho text dài
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (!style.isEmpty()) btn.putClientProperty("FlatLaf.styleClass", style);
        return btn;
    }

    private void styleTable(JTable tb) {
        tb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tb.setRowHeight(30);
        tb.setShowVerticalLines(false);
        tb.setShowHorizontalLines(true);
        tb.setGridColor(new Color(230, 230, 230));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tb.getTableHeader().setBackground(new Color(248, 250, 252));
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

        btnQuanLyPhuCap.addActionListener(e -> {
            int row = tableCV.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một chức vụ trên bảng trước!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maCV = tableCV.getValueAt(row, 0).toString();
            String tenCV = tableCV.getValueAt(row, 1).toString();
            
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
            taiDuLieuLenBangUser(maNV);
            
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