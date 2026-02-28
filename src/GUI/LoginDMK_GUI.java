package GUI;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import BUS.TaiKhoan_BUS;

public class LoginDMK_GUI extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JTextField jtfuser;
	private JPasswordField jtfpass;
	private JPasswordField jtfpassnew;
	private JPasswordField jtfpassnew2;
	private JButton btluu;
	private JButton bthuy;

	/**
	 * Create the panel.
	 */
	public LoginDMK_GUI() {
		// Thiết lập thuộc tính cho JPanel
		setBackground(new Color(255, 255, 255));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		// Kích thước khớp với panelRight của bạn
		setPreferredSize(new Dimension(400, 500)); 
		
		Font f = new Font("Arial", Font.BOLD, 12);
		JLabel lblNewLabel = new JLabel("CHANGE PASSWORD");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(120, 30, 166, 29); // Điều chỉnh tọa độ Y cho cân đối
		add(lblNewLabel);
		
		JLabel jlusername = new JLabel("USERNAME");
		jlusername.setBounds(55, 80, 70, 20);
		jlusername.setFont(f);
		add(jlusername);
		
		jtfuser = new JTextField();
		jtfuser.setBounds(55, 105, 260, 30); // Tăng kích thước ô nhập cho dễ thao tác
		add(jtfuser);
		jtfuser.setColumns(10);
		
		JLabel jlpassword = new JLabel("Mật khẩu cũ");
		jlpassword.setBounds(55, 145, 80, 20);
		jlpassword.setFont(f);
		add(jlpassword);
		
		jtfpass = new JPasswordField();
		jtfpass.setBounds(55, 170, 260, 30);
		add(jtfpass);
		
		JLabel jlpasswordnew = new JLabel("Mật khẩu mới");
		jlpasswordnew.setBounds(55, 210, 80, 20);
		jlpasswordnew.setFont(f);
		add(jlpasswordnew);
		
		jtfpassnew = new JPasswordField();
		jtfpassnew.setBounds(55, 235, 260, 30);
		add(jtfpassnew);
		
		JLabel jlpasswordnew2 = new JLabel("Nhập lại mật khẩu mới");
		jlpasswordnew2.setBounds(55, 275, 150, 20);
		jlpasswordnew2.setFont(f);
		add(jlpasswordnew2);
		
		jtfpassnew2 = new JPasswordField();
		jtfpassnew2.setBounds(55, 300, 260, 30);
		add(jtfpassnew2);
		
		btluu = new JButton("Lưu");
		btluu.setForeground(new Color(255, 255, 255));
		btluu.setBorderPainted(false);
		btluu.setFocusPainted(false);
		btluu.setBounds(85, 360, 100, 35);
		btluu.setBackground(Color.decode("#4bcffa"));
		btluu.setFont(f);
		add(btluu);
		
		bthuy = new JButton("Hủy");
		bthuy.setForeground(new Color(255, 255, 255));
		bthuy.setFocusPainted(false);
		bthuy.setBorderPainted(false);
		bthuy.setBounds(195, 360, 100, 35);
		bthuy.setBackground(new Color(255, 0, 0));
		bthuy.setFont(f);
		add(bthuy);
		
		bthuy.addActionListener(this);
		btluu.addActionListener(this);
		addEvent();
	}
	
	// Getter để lấy dữ liệu phục vụ xử lý logic
	public JTextField getTextField() { return jtfuser; }
	public JPasswordField getPasswordField() { return jtfpass; }
	public JPasswordField getPasswordField_1() { return jtfpassnew; }
	public JPasswordField getPasswordField_2() { return jtfpassnew2; }
	public JButton getBtluu() { return btluu; }
	public JButton getBthuy() { return bthuy; }

	@Override
	public void actionPerformed(ActionEvent e) {
		String src=e.getActionCommand();
		if(src.equals("Hủy"))
		{
			xoatrang();
			Window window=SwingUtilities.getWindowAncestor(LoginDMK_GUI.this);
			if(window instanceof Login_GUI)
			{
				Login_GUI parent=(Login_GUI)window;
				parent.showCard("formdangnhap");
			}
			
			
		}
		else if(src.equals("Lưu"))
		{
			String taikhoan=jtfuser.getText();
			String matkhaucu=new String(jtfpass.getPassword());
			String matkhaumoi=new String(jtfpassnew.getPassword());
			String matkhauxacnhan=new String(jtfpassnew2.getPassword());
			if(taikhoan.isEmpty()||matkhaucu.isEmpty()||matkhaumoi.isEmpty()||matkhauxacnhan.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đur thông tin!","Thông báo",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(!matkhaumoi.equals(matkhauxacnhan))
			{
				JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu mới không khớp!");
				return;
			}
			TaiKhoan_BUS taikhoanbus=new TaiKhoan_BUS();
			String message=taikhoanbus.Checklogin(taikhoan, matkhaucu, matkhaumoi);
			JOptionPane.showMessageDialog(this, message,"Thông báo",JOptionPane.INFORMATION_MESSAGE);
			if(message.contains("thành công"))
			{
				xoatrang();
			}
			
		}
	}
	public void addEvent()
	{
		bthuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				bthuy.setBackground(new Color(255, 80, 80));
				bthuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				bthuy.setBackground(new Color(255, 0, 0));			}
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
	public void xoatrang()
	{
		jtfuser.setText("");
		jtfpass.setText("");
		jtfpassnew.setText("");
		jtfpassnew2.setText("");	
	}
	
}