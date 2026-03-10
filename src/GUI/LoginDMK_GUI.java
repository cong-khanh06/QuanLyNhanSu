package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import BUS.TaiKhoan_BUS;

public class LoginDMK_GUI extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextField jtfuser;
    private JPasswordField jtfpass;
    private JPasswordField jtfpassnew;
    private JPasswordField jtfpassnew2;
    private JButton btluu;
    private JButton bthuy;

    public LoginDMK_GUI() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 550));

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        wrapperPanel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridLayout(10, 1, 0, 0));
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(300, 480));
        Font fBold = new Font("Arial", Font.BOLD, 12);

        JLabel lblTitle = new JLabel("CHANGE PASSWORD", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(lblTitle);

        JLabel jlusername = new JLabel("USERNAME");
        jlusername.setFont(fBold);
        formPanel.add(jlusername);
        jtfuser = new JTextField();
        formPanel.add(jtfuser);

        JLabel jlpassword = new JLabel("Mật khẩu cũ");
        jlpassword.setFont(fBold);
        formPanel.add(jlpassword);
        jtfpass = new JPasswordField();
        formPanel.add(jtfpass);

        JLabel jlpasswordnew = new JLabel("Mật khẩu mới");
        jlpasswordnew.setFont(fBold);
        formPanel.add(jlpasswordnew);
        jtfpassnew = new JPasswordField();
        formPanel.add(jtfpassnew);

        JLabel jlpasswordnew2 = new JLabel("Nhập lại mật khẩu mới");
        jlpasswordnew2.setFont(fBold);
        formPanel.add(jlpasswordnew2);
        jtfpassnew2 = new JPasswordField();
        formPanel.add(jtfpassnew2);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlButtons.setBackground(Color.WHITE);

        btluu = new JButton("Lưu");
        btluu.setPreferredSize(new Dimension(100, 40));
        btluu.setForeground(Color.WHITE);
        btluu.setBackground(Color.decode("#4bcffa"));
        btluu.setBorderPainted(false);
        btluu.setFocusPainted(false);
        btluu.setFont(fBold);

        bthuy = new JButton("Hủy");
        bthuy.setPreferredSize(new Dimension(100, 40));
        bthuy.setForeground(Color.WHITE);
        bthuy.setBackground(new Color(255, 0, 0));
        bthuy.setBorderPainted(false);
        bthuy.setFocusPainted(false);
        bthuy.setFont(fBold);

        pnlButtons.add(btluu);
        pnlButtons.add(bthuy);
        formPanel.add(pnlButtons);

        wrapperPanel.add(formPanel);
        add(wrapperPanel, BorderLayout.CENTER);
        bthuy.addActionListener(this);
        btluu.addActionListener(this);
        addEvent();
    }


    public JTextField getTextField() { return jtfuser; }

	public JPasswordField getPasswordField() { return jtfpass; }

	public JPasswordField getPasswordField_1() { return jtfpassnew; }

	public JPasswordField getPasswordField_2() { return jtfpassnew2; }

	public JButton getBtluu() { return btluu; }

	public JButton getBthuy() { return bthuy; }
	
    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        if (src.equals("Hủy")) {
            xoatrang();
            Window window = SwingUtilities.getWindowAncestor(LoginDMK_GUI.this);
            if (window instanceof Login_GUI) {
                Login_GUI parent = (Login_GUI) window;
                parent.showCard("formdangnhap");
            }
        } else if (src.equals("Lưu")) {
            String taikhoan = jtfuser.getText();
            String matkhaucu = new String(jtfpass.getPassword());
            String matkhaumoi = new String(jtfpassnew.getPassword());
            String matkhauxacnhan = new String(jtfpassnew2.getPassword());
            
            if (taikhoan.isEmpty() || matkhaucu.isEmpty() || matkhaumoi.isEmpty() || matkhauxacnhan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (!matkhaumoi.equals(matkhauxacnhan)) {
                JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu mới không khớp!");
                return;
            }
            TaiKhoan_BUS taikhoanbus = new TaiKhoan_BUS();
            String message = taikhoanbus.CheckDMK(taikhoan, matkhaucu, matkhaumoi);
            JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            if (message.contains("thành công")) {
                xoatrang();
            }
        }
    }

    public void addEvent() {
        bthuy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bthuy.setBackground(new Color(255, 80, 80));
                bthuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                bthuy.setBackground(new Color(255, 0, 0));
            }
        });
        btluu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btluu.setBackground(new Color(52, 152, 219));
                btluu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btluu.setBackground(Color.decode("#4bcffa"));
            }
        });
    }

    public void xoatrang() {
        jtfuser.setText("");
        jtfpass.setText("");
        jtfpassnew.setText("");
        jtfpassnew2.setText("");
    }
}