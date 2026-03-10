package GUI;

import BUS.ChucVu_BUS;
import DTO.PhuCap_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChiTietChucVu_Dialog extends JDialog {
    private JTable tablePC;
    private DefaultTableModel modelPC;
    private JComboBox<String> cbPhuCap;
    private JButton btnThem, btnXoa, btnDong;

    private ChucVu_BUS bus = new ChucVu_BUS();
    private String maCV, tenCV;

    public ChiTietChucVu_Dialog(Window parent, String maCV, String tenCV) {
        super(parent, "Quản Lý Phụ Cấp - " + tenCV, Dialog.ModalityType.APPLICATION_MODAL);
        this.maCV = maCV;
        this.tenCV = tenCV;
        
        khoiTaoGiaoDien();
        taiDuLieuPhuCapCuaChucVu();
        taiDanhSachComboBox();
    }

    private void khoiTaoGiaoDien() {
        setSize(550, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel pnTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnTop.setBorder(BorderFactory.createTitledBorder("Gán Phụ Cấp Mới"));
        
        cbPhuCap = new JComboBox<>();
        cbPhuCap.setPreferredSize(new Dimension(250, 30));
        
        btnThem = new JButton("Thêm Phụ Cấp");
        btnXoa = new JButton("Xóa Phụ Cấp (Đang chọn)");
        
        pnTop.add(new JLabel("Chọn Phụ Cấp:"));
        pnTop.add(cbPhuCap);
        pnTop.add(btnThem);
        add(pnTop, BorderLayout.NORTH);

        String[] cols = {"Mã PC", "Tên Phụ Cấp", "Số Tiền (VNĐ)"};
        modelPC = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablePC = new JTable(modelPC);
        tablePC.setRowHeight(28);
        
        JScrollPane scroll = new JScrollPane(tablePC);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh Sách Phụ Cấp Hiện Tại Của: " + tenCV));
        add(scroll, BorderLayout.CENTER);

        JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnDong = new JButton("Đóng");
        pnBottom.add(btnXoa);
        pnBottom.add(btnDong);
        add(pnBottom, BorderLayout.SOUTH);


        btnDong.addActionListener(e -> dispose());
        
        btnThem.addActionListener(e -> {
            if (cbPhuCap.getSelectedItem() == null) return;
            String selectedItem = cbPhuCap.getSelectedItem().toString();
            String maPC = selectedItem.split(" - ")[0];
            
            if (bus.themPhuCapChoChucVu(maCV, maPC)) {
                JOptionPane.showMessageDialog(this, "Đã gán phụ cấp thành công!");
                taiDuLieuPhuCapCuaChucVu(); 
            } else {
                JOptionPane.showMessageDialog(this, "Thất bại! Phụ cấp này có thể đã được gán rồi.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            int row = tablePC.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phụ cấp trong bảng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maPC = tablePC.getValueAt(row, 0).toString();
            
            int confirm = JOptionPane.showConfirmDialog(this, "Gỡ phụ cấp này khỏi chức vụ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.xoaPhuCapKhoiChucVu(maCV, maPC)) {
                    JOptionPane.showMessageDialog(this, "Đã gỡ thành công!");
                    taiDuLieuPhuCapCuaChucVu();
                } else {
                    JOptionPane.showMessageDialog(this, "Gỡ thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void taiDuLieuPhuCapCuaChucVu() {
        modelPC.setRowCount(0);
        List<PhuCap_DTO> list = bus.layPhuCapTheoChucVu(maCV);
        for (PhuCap_DTO pc : list) {
            modelPC.addRow(new Object[]{pc.getMaPC(), pc.getTenPC(), pc.getSoTien()});
        }
    }

    private void taiDanhSachComboBox() {
        List<PhuCap_DTO> listTatCa = bus.layDanhSachTatCaPhuCap();
        for (PhuCap_DTO pc : listTatCa) {
            cbPhuCap.addItem(pc.getMaPC() + " - " + pc.getTenPC());
        }
    }
    public void setPhanQuyen(String quyen) {
        if (quyen.equalsIgnoreCase("User")) {
            btnThem.setVisible(false);
            btnXoa.setVisible(false);            
            cbPhuCap.setEnabled(false);
            
        } 
        else if (quyen.equalsIgnoreCase("Manager")) {
            btnThem.setVisible(true);
            btnThem.setEnabled(true);
            
            btnXoa.setVisible(false); 
            btnXoa.setEnabled(false);
            
        }
    }
}