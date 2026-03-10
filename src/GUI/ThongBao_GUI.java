package GUI;

import BUS.ThongBao_BUS;
import DTO.ThongBao_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ThongBao_GUI extends JPanel {
    private JTable tableTB;
    private DefaultTableModel modelTB;
    private JTextField txtMaTB, txtMaTK, txtNgayTao;
    private JTextArea txtNoiDung;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;

    private ThongBao_BUS bus = new ThongBao_BUS();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public ThongBao_GUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        
        JPanel pnLeft = new JPanel(new BorderLayout());
        pnLeft.setBackground(Color.WHITE);
        pnLeft.setBorder(BorderFactory.createTitledBorder("Danh Sách Thông Báo"));

        String[] cols = {"Mã Thông Báo", "Mã Tài Khoản", "Nội Dung", "Ngày Tạo"};
        modelTB = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableTB = new JTable(modelTB);
        tableTB.setRowHeight(28);
        tableTB.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableTB.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        pnLeft.add(new JScrollPane(tableTB), BorderLayout.CENTER);
        add(pnLeft, BorderLayout.CENTER);

        
        JPanel pnRight = new JPanel(new BorderLayout(0, 15));
        pnRight.setPreferredSize(new Dimension(350, 0));
        pnRight.setBackground(Color.WHITE);
        pnRight.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));

        JPanel pnForm = new JPanel();
        pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
        pnForm.setBackground(Color.WHITE);
        pnForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        pnForm.add(new JLabel("Mã Thông Báo (Tự động):"));
        txtMaTB = new JTextField();
        txtMaTB.setEditable(false);
        txtMaTB.setBackground(new Color(230, 230, 230));
        pnForm.add(txtMaTB);
        pnForm.add(Box.createVerticalStrut(10));

        
        pnForm.add(new JLabel("Người tạo (mã tài khoản):"));
        String maTK_DangNhap = "";
        if (DTO.taiKhoanDangDangNhap.getTkHienTai() != null) {
            maTK_DangNhap = DTO.taiKhoanDangDangNhap.getTkHienTai().getMataikhoan();
        }
        txtMaTK = new JTextField(maTK_DangNhap);
        txtMaTK.setEditable(false);
        pnForm.add(txtMaTK);
        pnForm.add(Box.createVerticalStrut(10));

        
        pnForm.add(new JLabel("Ngày Tạo:"));
        txtNgayTao = new JTextField();
        txtNgayTao.setEditable(false); 
        txtNgayTao.setBackground(new Color(230, 230, 230));
        pnForm.add(txtNgayTao);
        pnForm.add(Box.createVerticalStrut(10));

        
        pnForm.add(new JLabel("Nội Dung:"));
        txtNoiDung = new JTextArea(5, 20);
        txtNoiDung.setLineWrap(true);
        txtNoiDung.setWrapStyleWord(true);
        JScrollPane scrollNoiDung = new JScrollPane(txtNoiDung);
        pnForm.add(scrollNoiDung);

        pnRight.add(pnForm, BorderLayout.CENTER);

        JPanel pnButtons = new JPanel(new GridLayout(2, 2, 10, 10));
        pnButtons.setBackground(Color.WHITE);
        pnButtons.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        btnThem = new JButton("Thêm Mới");
        btnThem.setBackground(new Color(153, 255, 153));
        
        btnSua = new JButton("Cập Nhật");
        btnSua.setBackground(new Color(255, 235, 153));
        
        btnXoa = new JButton("Xóa");
        btnXoa.setBackground(new Color(255, 153, 153));
        
        btnLamMoi = new JButton("Làm Mới Form");

        pnButtons.add(btnThem);
        pnButtons.add(btnSua);
        pnButtons.add(btnXoa);
        pnButtons.add(btnLamMoi);

        pnRight.add(pnButtons, BorderLayout.SOUTH);

        add(pnRight, BorderLayout.EAST);

        
        taiDuLieuLenBang();
        resetForm();

        
        tableTB.getSelectionModel().addListSelectionListener(e -> {
            int row = tableTB.getSelectedRow();
            if (row >= 0) {
                txtMaTB.setText(modelTB.getValueAt(row, 0).toString());
                txtMaTK.setText(modelTB.getValueAt(row, 1).toString());
                txtNoiDung.setText(modelTB.getValueAt(row, 2).toString());
                txtNgayTao.setText(modelTB.getValueAt(row, 3).toString());
            }
        });

        
        btnLamMoi.addActionListener(e -> resetForm());

        
        btnThem.addActionListener(e -> {
            String maTB = txtMaTB.getText();
            String maTK = txtMaTK.getText().trim();
            String noiDung = txtNoiDung.getText().trim();
            
            
            LocalDate ngayTao = LocalDate.now();

            if (maTK.isEmpty() || noiDung.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã tài khoản và Nội dung thông báo!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ThongBao_DTO tb = new ThongBao_DTO(maTB, maTK, noiDung, ngayTao);
            if (bus.themThongBao(tb)) {
                JOptionPane.showMessageDialog(this, "Thêm thông báo thành công!");
                taiDuLieuLenBang();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại! Kiểm tra lại mã Tài khoản có tồn tại không.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        btnSua.addActionListener(e -> {
            if (tableTB.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 thông báo để sửa!");
                return;
            }
            
            String maTB = txtMaTB.getText();
            String maTK = txtMaTK.getText().trim();
            String noiDung = txtNoiDung.getText().trim();
            
            ThongBao_DTO tb = new ThongBao_DTO(maTB, maTK, noiDung, null); // Không update ngày tạo
            
            if (bus.suaThongBao(tb)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                taiDuLieuLenBang();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        btnXoa.addActionListener(e -> {
            if (tableTB.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 thông báo để xóa!");
                return;
            }
            
            String maTB = txtMaTB.getText();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa thông báo " + maTB + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.xoaThongBao(maTB)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    taiDuLieuLenBang();
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void taiDuLieuLenBang() {
        modelTB.setRowCount(0);
        List<ThongBao_DTO> danhSach = bus.layDanhSachThongBao();
        for (ThongBao_DTO tb : danhSach) {
            String strNgay = (tb.getNgayTao() != null) ? tb.getNgayTao().format(dtf) : "";
            modelTB.addRow(new Object[]{
                tb.getMaTB(), tb.getMaTK(), tb.getNoiDung(), strNgay
            });
        }
    }

    
    private void resetForm() {
        tableTB.clearSelection();
        txtMaTB.setText(bus.taoMaMoi()); 
        
        
        if (DTO.taiKhoanDangDangNhap.getTkHienTai() != null) {
            txtMaTK.setText(DTO.taiKhoanDangDangNhap.getTkHienTai().getMataikhoan());
        } else {
            txtMaTK.setText("");
        }
        
        txtNoiDung.setText("");
        txtNgayTao.setText(LocalDate.now().format(dtf)); 
    }
}