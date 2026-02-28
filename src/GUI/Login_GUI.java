package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;

public class Login_GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private final JPanel panelLeft = new JPanel();
    private Logindangnhap_GUI logindangnhap;
    private LoginDMK_GUI logindmk;
	private JPanel panelRight;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login_GUI frame = new Login_GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login_GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("HR Management System - Login");
        logindangnhap=new Logindangnhap_GUI();
        logindmk=new LoginDMK_GUI();
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setPreferredSize(new Dimension(800, 500)); // Kích thước tổng thể mới
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // --- PANEL BÊN TRÁI (MÀU XANH) ---
        panelLeft.setBounds(0, 0, 400, 500);
        panelLeft.setBackground(new Color(25, 118, 210));
        panelLeft.setLayout(null);
        contentPane.add(panelLeft);
        
        // Logo - Căn giữa panel trái (400/2 - 170/2 = 115)
        JLabel jlimage = new JLabel("");
        jlimage.setBounds(115, 50, 170, 90);
        panelLeft.add(jlimage);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/quanlynhansu.png"));
            Image image = icon.getImage().getScaledInstance(170, 90, Image.SCALE_SMOOTH);
            jlimage.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            System.out.println("Lưu ý: Chưa tìm thấy file ảnh quanlynhansu.png");
        }

        // Câu chào nhỏ
        JLabel jlWelcome = new JLabel("Welcome to");
        jlWelcome.setForeground(Color.WHITE);
        jlWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jlWelcome.setBounds(50, 180, 300, 30);
        panelLeft.add(jlWelcome);

        // Tên phần mềm (Đã điều chỉnh size font xuống 36 để vừa chiều rộng 400)
        JLabel jlTitle = new JLabel("HR PRO SYSTEM");
        jlTitle.setForeground(Color.WHITE);
        jlTitle.setFont(new Font("Segoe UI", Font.BOLD, 36)); 
        jlTitle.setBounds(50, 210, 350, 50);
        panelLeft.add(jlTitle);

        // Slogan sử dụng HTML để tự động xuống dòng và căn lề
     // Slogan sử dụng HTML để tự động xuống dòng và căn lề
        JLabel jlSlogan=new JLabel("<html><bode style='width: 250px;'>Connecting people with purpose to build a stronger"
        		+ " and more productive organization.</body></html>");
        jlSlogan.setForeground(new Color(255, 255, 255));
        jlSlogan.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        jlSlogan.setBounds(50, 270, 328, 80); 
        panelLeft.add(jlSlogan);
        
        panelRight = new JPanel();
        panelRight.setBackground(Color.WHITE);
        panelRight.setBounds(400, 0, 400, 500);
        contentPane.add(panelRight);
        panelRight.setLayout(new CardLayout(0, 0));
        panelRight.add(logindangnhap,"formdangnhap");
        panelRight.add(logindmk,"formdmk");
        
        showCard("formdangnhap");
        this.pack();
        this.setLocationRelativeTo(null);
        
    }
    public void showCard(String name)
    {
    	CardLayout card=(CardLayout)panelRight.getLayout();
    	card.show(panelRight, name);
    }
    
}