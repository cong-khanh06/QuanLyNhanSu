package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import BUS.TaiKhoan_BUS;

public class Login_GUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Logindangnhap_GUI logindangnhap;
    private LoginDMK_GUI logindmk;
    private JPanel panelRight;
    private TaiKhoan_BUS taikhoanbus;

    public Login_GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("HR Management System - Login");
        taikhoanbus = new TaiKhoan_BUS();
        
        logindangnhap = new Logindangnhap_GUI();
        logindmk = new LoginDMK_GUI();

        contentPane = new JPanel(new GridLayout(1, 2));
        contentPane.setPreferredSize(new Dimension(800, 500));
        setContentPane(contentPane);

        JPanel panelLeft = new JPanel(new GridBagLayout());
        panelLeft.setBackground(new Color(25, 118, 210));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 40, 10, 40); 

        
        JLabel jlimage = new JLabel("", SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/quanlynhansu.png"));
            Image image = icon.getImage().getScaledInstance(170, 90, Image.SCALE_SMOOTH);
            jlimage.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            System.out.println("Lưu ý: Chưa tìm thấy file ảnh quanlynhansu.png");
        }
        gbc.gridy = 0;
        panelLeft.add(jlimage, gbc);

        JLabel jlWelcome = new JLabel("Welcome to");
        jlWelcome.setForeground(Color.WHITE);
        jlWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridy = 1;
        panelLeft.add(jlWelcome, gbc);

        JLabel jlTitle = new JLabel("HR PRO SYSTEM");
        jlTitle.setForeground(Color.WHITE);
        jlTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        gbc.gridy = 2;
        panelLeft.add(jlTitle, gbc);

        JLabel jlSlogan = new JLabel("<html>Connecting people with purpose to build a stronger "
                + "and more productive organization.</html>");
        jlSlogan.setForeground(Color.WHITE);
        jlSlogan.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        gbc.gridy = 3;
        panelLeft.add(jlSlogan, gbc);

        panelRight = new JPanel(new CardLayout());
        panelRight.setBackground(Color.WHITE);
        panelRight.add(logindangnhap, "formdangnhap");
        panelRight.add(logindmk, "formdmk");

        contentPane.add(panelLeft);
        contentPane.add(panelRight);

        logindangnhap.getLogin().addActionListener(this);

        showCard("formdangnhap");
        
        this.pack(); 
        this.setLocationRelativeTo(null); 
    }

    public void showCard(String name) {
        CardLayout card = (CardLayout) panelRight.getLayout();
        card.show(panelRight, name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        if (src.equals("LOGIN")) {
            String user = logindangnhap.getJtfuser().getText();
            String password = new String(logindangnhap.getPasswordField().getPassword());
            
            if (user.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được để trống tài khoản hoặc mật khẩu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String message = taikhoanbus.CheckLogin(user, password);
            if (message.contains("thành công")) {
                this.dispose();
                Display_GUI ds=new Display_GUI();
                ds.getnameValue().setText(user);
                ds.getroleValue().setText(taikhoanbus.getTenchucvu(user));
            } else {
                JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login_GUI().setVisible(true);
        });
    }
}