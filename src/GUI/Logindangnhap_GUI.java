package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Logindangnhap_GUI extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField jtfuser;
    private JPasswordField passwordField;
    private JLabel jlchange;
    private JButton btlogin;

    public Logindangnhap_GUI() {
        
        setBackground(Color.WHITE);
        setLayout(new BorderLayout()); 
        setPreferredSize(new Dimension(400, 500));

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        wrapperPanel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridLayout(8, 1, 0, 0)); 
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(300, 400)); 
        JLabel lblTitle = new JLabel("SIGN IN TO YOUR ACCOUNT", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(lblTitle);


        JLabel jluser = new JLabel("Username");
        jluser.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(jluser);

        jtfuser = new JTextField();
        formPanel.add(jtfuser);

        JLabel jlpassword = new JLabel("Password");
        jlpassword.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(jlpassword);

        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JPanel pnlLink = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlLink.setBackground(Color.WHITE);
        jlchange = new JLabel("Change password");
        jlchange.setFont(new Font("Arial", Font.PLAIN, 12));
        pnlLink.add(jlchange);
        formPanel.add(pnlLink);

        btlogin = new JButton("LOGIN");
        btlogin.setBackground(new Color(0, 128, 255));
        btlogin.setForeground(Color.WHITE);
        btlogin.setFocusPainted(false);
        btlogin.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(btlogin);

        formPanel.add(new JLabel(""));


        wrapperPanel.add(formPanel);
        add(wrapperPanel, BorderLayout.CENTER);

        addEvent();
    }
    
    public JTextField getJtfuser()
    {
    	return jtfuser;
    }
    public JPasswordField getPasswordField()
    {
    	return passwordField;
    }
    public JButton getLogin()
    {
    	return btlogin;
    }
    
    public void addEvent() {
        jlchange.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Window window = SwingUtilities.getWindowAncestor(Logindangnhap_GUI.this);
                if (window instanceof Login_GUI) {
                    Login_GUI parent = (Login_GUI) window;
                    parent.showCard("formdmk");
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                jlchange.setForeground(Color.BLUE);
                jlchange.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jlchange.setForeground(Color.BLACK);
            }
        });

        btlogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btlogin.setBackground(new Color(0, 102, 204));
                btlogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btlogin.setBackground(new Color(0, 128, 255));
            }
        });
    }
    
}