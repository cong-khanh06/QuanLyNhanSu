package GUI;

import BUS.ChiTietBaoHiem_BUS;
import DTO.ChiTietBaoHiem_DTO;
import DTO.LoaiBaoHiem_DTO;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChiTietBaoHiem_Dialog extends JDialog {
    private JTable tableBH;
    private DefaultTableModel modelBH;
    private JComboBox<LoaiBaoHiem_DTO> cbLoaiBH;
    private JTextField txtMaCTBH, txtSoThe, txtNoiCap;
    private DatePicker dpNgayCap;
    private JButton btnThem, btnXoa, btnDong;

    private ChiTietBaoHiem_BUS bus = new ChiTietBaoHiem_BUS();
    private String maNV, tenNV;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public ChiTietBaoHiem_Dialog(Window parent, String maNV, String tenNV) {
        super(parent, "Hồ Sơ Bảo Hiểm - " + tenNV + " (" + maNV + ")", Dialog.ModalityType.APPLICATION_MODAL);
        this.maNV = maNV;
        this.tenNV = tenNV;
        
        khoiTaoGiaoDien();
        taiDanhSachComboBox();
        taiDuLieuBaoHiem();
        txtMaCTBH.setText(bus.taoMaMoi()); 
    }

    private void khoiTaoGiaoDien() {
        setSize(800, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(0, 10));

        JPanel pnTop = new JPanel(new GridLayout(3, 1, 5, 5));
        pnTop.setBorder(BorderFactory.createTitledBorder("Cấp Bảo Hiểm Mới Cho Nhân Viên"));

        JPanel line1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        line1.add(new JLabel("Mã Chi Tiết:"));
        txtMaCTBH = new JTextField(10);
        txtMaCTBH.setEditable(false);
        txtMaCTBH.setBackground(new Color(230, 230, 230));
        line1.add(txtMaCTBH);
        line1.add(Box.createHorizontalStrut(15));
        line1.add(new JLabel("Loại BH:"));
        cbLoaiBH = new JComboBox<>();
        cbLoaiBH.setPreferredSize(new Dimension(180, 28));
        line1.add(cbLoaiBH);

        JPanel line2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        line2.add(new JLabel("Số thẻ BH:"));
        txtSoThe = new JTextField(15);
        line2.add(txtSoThe);
        line2.add(Box.createHorizontalStrut(15));
        line2.add(new JLabel("Ngày cấp:"));
        DatePickerSettings setDate = new DatePickerSettings();
        setDate.setFormatForDatesCommonEra("dd-MM-yyyy");
        dpNgayCap = new DatePicker(setDate);
        line2.add(dpNgayCap);
        line2.add(Box.createHorizontalStrut(15));
        line2.add(new JLabel("Nơi cấp:"));
        txtNoiCap = new JTextField(15);
        line2.add(txtNoiCap);

        JPanel line3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnThem = new JButton("Thêm Vào Hồ Sơ");
        btnThem.setBackground(new Color(153, 255, 153));
        line3.add(btnThem);

        pnTop.add(line1);
        pnTop.add(line2);
        pnTop.add(line3);
        add(pnTop, BorderLayout.NORTH);

        String[] cols = {"Mã CTBH", "Loại BH", "Số Thẻ", "Ngày Cấp", "Nơi Cấp", "NV Đóng(%)", "Cty Đóng(%)"};
        modelBH = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableBH = new JTable(modelBH);
        tableBH.setRowHeight(28);
        
        JScrollPane scroll = new JScrollPane(tableBH);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh Sách Bảo Hiểm Đang Tham Gia"));
        add(scroll, BorderLayout.CENTER);

        JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXoa = new JButton("Gỡ Bảo Hiểm (Đang Chọn)");
        btnDong = new JButton("Đóng");
        pnBottom.add(btnXoa);
        pnBottom.add(btnDong);
        add(pnBottom, BorderLayout.SOUTH);

  
        btnDong.addActionListener(e -> dispose());
        
        btnThem.addActionListener(e -> {
            if (cbLoaiBH.getSelectedItem() == null) return;
            LoaiBaoHiem_DTO loaiBH = (LoaiBaoHiem_DTO) cbLoaiBH.getSelectedItem();
            
            String maCTBH = txtMaCTBH.getText();
            String soThe = txtSoThe.getText().trim();
            LocalDate ngayCap = dpNgayCap.getDate();
            String noiCap = txtNoiCap.getText().trim();

            if (soThe.isEmpty() || ngayCap == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ Số Thẻ và Ngày Cấp!");
                return;
            }

            ChiTietBaoHiem_DTO newBH = new ChiTietBaoHiem_DTO(maCTBH, soThe, noiCap, maNV, loaiBH.getMaBH(), ngayCap);
            
            if (bus.themChiTietBaoHiem(newBH)) {
                JOptionPane.showMessageDialog(this, "Đã thêm bảo hiểm thành công!");
                taiDuLieuBaoHiem(); 
                txtSoThe.setText(""); txtNoiCap.setText(""); dpNgayCap.clear();
                txtMaCTBH.setText(bus.taoMaMoi()); 
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            int row = tableBH.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để xóa!");
                return;
            }
            String maCTBH = tableBH.getValueAt(row, 0).toString();
            
            int confirm = JOptionPane.showConfirmDialog(this, "Gỡ bản ghi bảo hiểm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.xoaChiTietBaoHiem(maCTBH)) {
                    JOptionPane.showMessageDialog(this, "Đã gỡ thành công!");
                    taiDuLieuBaoHiem();
                    txtMaCTBH.setText(bus.taoMaMoi()); 
                } else {
                    JOptionPane.showMessageDialog(this, "Gỡ thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void taiDuLieuBaoHiem() {
        modelBH.setRowCount(0);
        List<ChiTietBaoHiem_DTO> list = bus.layBaoHiemTheoNhanVien(maNV);
        for (ChiTietBaoHiem_DTO bh : list) {
            String strNgayCap = (bh.getNgayCap() != null) ? bh.getNgayCap().format(dtf) : "";
            modelBH.addRow(new Object[]{
                bh.getMaCTBH(), bh.getTenBH(), bh.getSoTheBH(), 
                strNgayCap, bh.getNoiCap(), 
                bh.getTyLeNV(), bh.getTyLeCT() 
            });
        }
    }

    private void taiDanhSachComboBox() {
        List<LoaiBaoHiem_DTO> listTatCa = bus.layTatCaLoaiBaoHiem();
        for (LoaiBaoHiem_DTO bh : listTatCa) {
            cbLoaiBH.addItem(bh);
        }
    }
    public void setphanquyenuser(boolean kq)
    {
    	btnThem.setVisible(kq);
    	btnXoa.setVisible(kq);
    	txtSoThe.setEditable(false);
        txtNoiCap.setEditable(false);
        cbLoaiBH.setEnabled(false);
        dpNgayCap.setEnabled(false);
    	
    }
    public void setphanquyenManager()
    {
    	btnXoa.setVisible(false);
    }
    
}