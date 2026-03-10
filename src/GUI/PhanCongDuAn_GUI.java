package GUI;

import BUS.PhanCongDuAn_BUS;
import DTO.PhanCongDuAn_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PhanCongDuAn_GUI extends JDialog {
    private String maDA;
    private JTable tablePCDA;
    private DefaultTableModel modelPCDA;
    private JComboBox<String> cbNhanVien;
    private JTextField txtVaiTro;
    private JButton btnThem, btnSua, btnXoa;
    
    private PhanCongDuAn_BUS bus = new PhanCongDuAn_BUS();
    private DuAn_GUI parent;

    
    
    public PhanCongDuAn_GUI(DuAn_GUI parent, String maDA, String tenDA) {
        this.parent = parent;
        this.maDA = maDA;
        
        setTitle( tenDA );
        setSize(750, 450);
        setLocationRelativeTo(parent);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        
        String[] cols = {"Mã NV", "Tên Nhân Viên", "Vai Trò Đảm Nhận"};
        modelPCDA = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablePCDA = new JTable(modelPCDA);
        tablePCDA.setRowHeight(28);
        tablePCDA.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(tablePCDA);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách nhân sự hiện tại"));
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel pnRight = new JPanel(new BorderLayout());
        pnRight.setPreferredSize(new Dimension(280, 0));
        pnRight.setBorder(BorderFactory.createTitledBorder("Thông tin phân công"));
        
        JPanel pnForm = new JPanel(new GridLayout(4, 1, 5, 5));
        pnForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        pnForm.add(new JLabel("Chọn Nhân Viên:"));
        cbNhanVien = new JComboBox<>();
        for (String nv : bus.layDanhSachNhanVienCombobox()) {
            cbNhanVien.addItem(nv);
        }
        pnForm.add(cbNhanVien);
        
        pnForm.add(new JLabel("Vai Trò:"));
        txtVaiTro = new JTextField();
        pnForm.add(txtVaiTro);
        
        pnRight.add(pnForm, BorderLayout.NORTH);

        
        JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnThem = new JButton("Thêm Mới");
        btnSua = new JButton("Cập Nhật Vai Trò");
        btnXoa = new JButton("Xóa Khỏi DA");
        
        pnButtons.add(btnThem);
        pnButtons.add(btnSua);
        pnButtons.add(btnXoa);
        pnRight.add(pnButtons, BorderLayout.CENTER);

        add(pnRight, BorderLayout.EAST);

        
        taiDuLieu();
        addEvents();
    }

    private void taiDuLieu() {
        modelPCDA.setRowCount(0);
        List<PhanCongDuAn_DTO> ds = bus.layDanhSachPhanCong(maDA);
        for (PhanCongDuAn_DTO pc : ds) {
            modelPCDA.addRow(new Object[]{ pc.getMaNV(), pc.getTenNV(), pc.getVaiTro() });
        }
        
        parent.taiDuLieuChiTietLenBang(maDA); 
    }

    private void addEvents() {
        
        tablePCDA.getSelectionModel().addListSelectionListener(e -> {
            int row = tablePCDA.getSelectedRow();
            if (row >= 0) {
                String maNV = tablePCDA.getValueAt(row, 0).toString();
                String vaiTro = tablePCDA.getValueAt(row, 2).toString();
                txtVaiTro.setText(vaiTro);
                
                for (int i = 0; i < cbNhanVien.getItemCount(); i++) {
                    if (cbNhanVien.getItemAt(i).startsWith(maNV)) {
                        cbNhanVien.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

        
        btnThem.addActionListener(e -> {
            if (txtVaiTro.getText().trim().isEmpty() || cbNhanVien.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên và nhập vai trò!"); return;
            }
            String maNV = cbNhanVien.getSelectedItem().toString().split(" - ")[0];
            PhanCongDuAn_DTO pc = new PhanCongDuAn_DTO(maDA, maNV, "", txtVaiTro.getText().trim());
            
            if (bus.themPhanCong(pc)) {
                JOptionPane.showMessageDialog(this, "Đã thêm thành công!");
                taiDuLieu();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại! Nhân viên này có thể đã tham gia dự án.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        btnSua.addActionListener(e -> {
            int row = tablePCDA.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chọn 1 dòng để sửa!"); return;
            }
            String maNV = tablePCDA.getValueAt(row, 0).toString(); // Mã NV cũ
            PhanCongDuAn_DTO pc = new PhanCongDuAn_DTO(maDA, maNV, "", txtVaiTro.getText().trim());
            
            if (bus.suaPhanCong(pc)) {
                JOptionPane.showMessageDialog(this, "Đã cập nhật vai trò!");
                taiDuLieu();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        btnXoa.addActionListener(e -> {
            int row = tablePCDA.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chọn 1 dòng để xóa!"); return;
            }
            String maNV = tablePCDA.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa nhân viên " + maNV + " khỏi dự án này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.xoaPhanCong(maDA, maNV)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    taiDuLieu();
                }
            }
        });
    }
}