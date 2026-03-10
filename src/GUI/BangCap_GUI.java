package GUI;

import BUS.BangCap_BUS;
import DTO.BangCap_DTO;
import GUI.button.ButtonToolBar; 

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BangCap_GUI extends JPanel {
    JPanel pnSearch, pnHeader, pnToolBar;
    JTable tableBC;
    DefaultTableModel modelBC;
    JTextField txtSearch;
    JButton btnSearch, btnAdd, btnEdit, btnDelete, btnRefresh;
    JLabel lblTitle;
    
    BangCap_BUS bus = new BangCap_BUS();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BangCap_GUI() {
        setLayout(new BorderLayout());

        pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnHeader.setBackground(new Color(150, 214, 255));
        pnHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        lblTitle = new JLabel("Quản Lý Bằng Cấp Nhân Viên");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnHeader.add(lblTitle);

        pnToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnToolBar.setBackground(new Color(150, 214, 255));
        pnToolBar.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(220, 36));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnSearch = new JButton("Tìm kiếm");
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnRefresh = new JButton("Tải lại");

        Dimension btnSize = new Dimension(100, 36);
        btnSearch.setPreferredSize(btnSize);
        btnAdd.setPreferredSize(btnSize);
        btnEdit.setPreferredSize(btnSize);
        btnDelete.setPreferredSize(btnSize);
        btnRefresh.setPreferredSize(btnSize);

        pnToolBar.add(txtSearch);
        pnToolBar.add(btnSearch);
        pnToolBar.add(btnAdd);
        pnToolBar.add(btnEdit);
        pnToolBar.add(btnDelete);
        pnToolBar.add(btnRefresh);

        pnSearch = new JPanel(new BorderLayout());
        pnSearch.add(pnHeader, BorderLayout.NORTH);
        pnSearch.add(pnToolBar, BorderLayout.CENTER);
        add(pnSearch, BorderLayout.NORTH);

        String[] cols = {"Mã Bằng Cấp", "Tên Bằng Cấp", "Nơi Cấp", "Ngày Cấp", "Mã Nhân Viên"};
        modelBC = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableBC = new JTable(modelBC);
        tableBC.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableBC.setRowHeight(28);
        tableBC.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        add(new JScrollPane(tableBC), BorderLayout.CENTER);

        btnSearch.addActionListener(e -> {
            String tuKhoa = txtSearch.getText().trim();
            
            List<BangCap_DTO> ketQua = bus.timkiem(tuKhoa);

            modelBC.setRowCount(0);

            for (BangCap_DTO bc : ketQua) {
                String strNgayCap = (bc.getNgayCap() != null) ? bc.getNgayCap().format(dtf) : "";
                
                modelBC.addRow(new Object[]{
                    bc.getMaBC(), 
                    bc.getTenBC(), 
                    bc.getNoiCap(), 
                    strNgayCap, 
                    bc.getMaNV()
                });
            }
            
            if (ketQua.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy bằng cấp nào khớp với từ khóa: " + tuKhoa, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        taiDuLieuLenBang();
        addEvents();
    }

    public void taiDuLieuLenBang() {
        modelBC.setRowCount(0);
        List<BangCap_DTO> list = bus.layDanhSachBangCap();
        for (BangCap_DTO bc : list) {
            String strNgayCap = (bc.getNgayCap() != null) ? bc.getNgayCap().format(dtf) : "";
            modelBC.addRow(new Object[]{
                bc.getMaBC(), bc.getTenBC(), bc.getNoiCap(), strNgayCap, bc.getMaNV()
            });
        }
    }

    private void addEvents() {
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            taiDuLieuLenBang();
        });

        btnAdd.addActionListener(e -> {
            BangCap_Dialog dialog = new BangCap_Dialog(this);
            dialog.setVisible(true);
        });

        btnDelete.addActionListener(e -> {
            int row = tableBC.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bằng cấp để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maBC = tableBC.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa bằng cấp " + maBC + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.xoaBangCap(maBC)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    taiDuLieuLenBang();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEdit.addActionListener(e -> {
            int row = tableBC.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bằng cấp để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String maBC = tableBC.getValueAt(row, 0).toString();
                String tenBC = tableBC.getValueAt(row, 1) != null ? tableBC.getValueAt(row, 1).toString() : "";
                String noiCap = tableBC.getValueAt(row, 2) != null ? tableBC.getValueAt(row, 2).toString() : "";
                String strNgayCap = tableBC.getValueAt(row, 3) != null ? tableBC.getValueAt(row, 3).toString() : "";
                String maNV = tableBC.getValueAt(row, 4) != null ? tableBC.getValueAt(row, 4).toString() : "";

                LocalDate ngayCap = strNgayCap.isEmpty() ? null : LocalDate.parse(strNgayCap, dtf);

                BangCap_DTO bcEdit = new BangCap_DTO(maBC, tenBC, noiCap, maNV, ngayCap);
                BangCap_Dialog dialog = new BangCap_Dialog(this, bcEdit);
                dialog.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi đọc dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    
}