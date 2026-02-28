package GUI;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;


public class Logindangnhap_GUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel jlchange;
	private JButton btlogin;

	/**
	 * Create the panel.
	 */
	public Logindangnhap_GUI() {
		setBackground(new Color(255, 255, 255));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		setPreferredSize(new Dimension(400, 500)); 
		
		JLabel lblNewLabel = new JLabel("SIGN IN TO YOUR ACCOUNT");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(100, 30, 235, 25); 
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setBounds(55, 90, 75, 17);
		add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(55, 115, 260, 30);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(55, 160, 75, 17);
		add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(55, 185, 260, 30);
		add(passwordField);
		
		btlogin = new JButton("LOGIN");
		btlogin.setFocusPainted(false);
		btlogin.setBorderPainted(false);
		btlogin.setBackground(new Color(0, 128, 255));
		btlogin.setForeground(new Color(255, 255, 255));
		btlogin.setFont(new Font("Arial", Font.BOLD, 13));
		btlogin.setBounds(55, 280, 260, 35);
		add(btlogin);
		
		jlchange = new JLabel("Change password");
		jlchange.setFont(new Font("Arial", Font.PLAIN, 12));
		jlchange.setBounds(205, 226, 110, 30);
		add(jlchange);
		addEvent();
	}

	public JTextField getTextField() {
		return textField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}
	public void addEvent()
	{
		jlchange.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Window window=SwingUtilities.getWindowAncestor(Logindangnhap_GUI.this);
				if(window instanceof Login_GUI)
				{
					Login_GUI parent =(Login_GUI)window;
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