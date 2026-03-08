package GUI;

import BUS.TaiKhoan_BUS;
import DTO.TaiKhoan_DTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TaiKhoan1_GUI extends JDialog {
    private JTextField txtMaTK, txtUser, txtPass, txtMaNV;
    private JComboBox<String> cbQuyen;
    private boolean dataChanged = false;
    private boolean isEdit;
    private TaiKhoan_BUS bus = new TaiKhoan_BUS();
    private final Color PRIMARY_COLOR = new Color(63, 81, 181);

    public TaiKhoan1_GUI(JFrame parent, TaiKhoan_DTO data) {
        super(parent, (data == null ? "THÊM TÀI KHOẢN MỚI" : "CẬP NHẬT TÀI KHOẢN"), true);
        this.isEdit = (data != null);
        setSize(450, 480);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel pMain = new JPanel(new GridBagLayout());
        pMain.setBorder(new EmptyBorder(20, 20, 20, 20));
        pMain.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaTK = new JTextField(15);
        txtUser = new JTextField(15);
        txtPass = new JPasswordField(15);
        txtMaNV = new JTextField(15);
        cbQuyen = new JComboBox<>(new String[]{"Admin", "User", "Manager"});

        if (isEdit) {
            txtMaTK.setText(data.getMataikhoan()); txtMaTK.setEditable(false);
            txtUser.setText(data.getTendangnhap()); txtUser.setEditable(false);
            txtMaNV.setText(data.getManv());
            cbQuyen.setSelectedItem(data.getQuyentruycap());
        }

        addComp(pMain, "Mã TK:", 0, 0, gbc); gbc.gridx = 1; pMain.add(txtMaTK, gbc);
        addComp(pMain, "Username:", 1, 0, gbc); gbc.gridx = 1; pMain.add(txtUser, gbc);
        addComp(pMain, "Mật khẩu:", 2, 0, gbc); gbc.gridx = 1; pMain.add(txtPass, gbc);

        if (isEdit) {
            gbc.gridy = 3; gbc.gridx = 1;
            JLabel lbNote = new JLabel("(*) Để trống nếu muốn giữ nguyên mật khẩu cũ");
            lbNote.setFont(new Font("Segoe UI", Font.ITALIC, 11));
            lbNote.setForeground(Color.RED);
            pMain.add(lbNote, gbc);
        }

        addComp(pMain, "Mã NV:", 4, 0, gbc); gbc.gridx = 1; pMain.add(txtMaNV, gbc);
        addComp(pMain, "Quyền:", 5, 0, gbc); gbc.gridx = 1; pMain.add(cbQuyen, gbc);

        add(pMain, BorderLayout.CENTER);

        JPanel pBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        JButton btnSave = new JButton("LƯU DỮ LIỆU");
        btnSave.setBackground(PRIMARY_COLOR);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JButton btnExit = new JButton("HỦY");

        btnSave.addActionListener(e -> {
            String password = txtPass.getText().trim();
            
            if (!isEdit && password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu khởi tạo!");
                return;
            }
            if (!password.isEmpty() && password.length() < 6) {
                JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!");
                return;
            }

            TaiKhoan_DTO dto = new TaiKhoan_DTO(
                txtMaTK.getText().trim(), 
                txtUser.getText().trim(), 
                password, 
                cbQuyen.getSelectedItem().toString(), 
                txtMaNV.getText().trim()
            );
            
            String res = isEdit ? bus.update(dto) : bus.add(dto);
            JOptionPane.showMessageDialog(this, res);
            if (res.contains("thành công")) { dataChanged = true; dispose(); }
        });

        btnExit.addActionListener(e -> dispose());
        pBottom.add(btnExit); pBottom.add(btnSave);
        add(pBottom, BorderLayout.SOUTH);
    }

    private void addComp(JPanel p, String text, int row, int col, GridBagConstraints gbc) {
        gbc.gridx = col; gbc.gridy = row;
        JLabel lb = new JLabel(text);
        lb.setFont(new Font("Segoe UI", Font.BOLD, 12));
        p.add(lb, gbc);
    }

    public boolean isDataChanged() { return dataChanged; }
}