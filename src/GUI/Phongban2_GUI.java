package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import BUS.Phongban_BUS;
import DTO.Phongban_DTO;

public class Phongban2_GUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextField jtfMaPhong, jtfTenPhong, jtfdiachi, jtfsdt;
    private JButton bthuy, btluu;
    private Phongban1_GUI parent;
    private JComboBox comboBox, comboBox_tp;
    private JLabel lblTitle;

    public Phongban2_GUI() {
        this.setSize(780, 280);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true); 
        this.getContentPane().setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBounds(0, 0, 780, 280);
        p1.setBackground(Color.decode("#3498db")); 
        p1.setLayout(null);
        this.getContentPane().add(p1);

        lblTitle = new JLabel("THÊM PHÒNG BAN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setForeground(Color.white);
        lblTitle.setBounds(10, 0, 300, 35); 
        p1.add(lblTitle);

        JPanel p2 = new JPanel();
        p2.setBounds(5, 35, 770, 240); 
        p2.setBackground(Color.white);
        p2.setLayout(null);
        p1.add(p2);

        JLabel jlmaphong = new JLabel("Mã phòng: ");
        jlmaphong.setBounds(20, 25, 100, 28);
        jlmaphong.setFont(new Font("Arial", Font.BOLD, 13));
        p2.add(jlmaphong);

        jtfMaPhong = new JTextField();
        jtfMaPhong.setBounds(130, 25, 220, 28);
        p2.add(jtfMaPhong);

        JLabel jlten = new JLabel("Tên phòng: ");
        jlten.setBounds(400, 25, 100, 28);
        jlten.setFont(new Font("Arial", Font.BOLD, 13));
        p2.add(jlten);

        jtfTenPhong = new JTextField();
        jtfTenPhong.setBounds(520, 25, 220, 28);
        p2.add(jtfTenPhong);

        JLabel jldiachi = new JLabel("Địa chỉ:");
        jldiachi.setBounds(20, 75, 100, 28);
        jldiachi.setFont(new Font("Arial", Font.BOLD, 13));
        p2.add(jldiachi);

        jtfdiachi = new JTextField();
        jtfdiachi.setBounds(130, 75, 220, 28);
        p2.add(jtfdiachi);

        JLabel jlsdt = new JLabel("Số điện thoại: ");
        jlsdt.setBounds(400, 75, 100, 28);
        jlsdt.setFont(new Font("Arial", Font.BOLD, 13));
        p2.add(jlsdt);

        jtfsdt = new JTextField();
        jtfsdt.setBounds(520, 75, 220, 28);
        p2.add(jtfsdt);

        JLabel jlbophan = new JLabel("Mã bộ phận:");
        jlbophan.setBounds(20, 125, 100, 28);
        jlbophan.setFont(new Font("Arial", Font.BOLD, 13));
        p2.add(jlbophan);

        comboBox = new JComboBox();
        comboBox.setBounds(130, 128, 220, 22);
        p2.add(comboBox);

        JLabel jltp = new JLabel("Trưởng phòng:");
        jltp.setBounds(400, 125, 110, 28);
        jltp.setFont(new Font("Arial", Font.BOLD, 13));
        p2.add(jltp);

        comboBox_tp = new JComboBox();
        comboBox_tp.setEnabled(false);
        comboBox_tp.setBounds(520, 128, 220, 22);
        
        p2.add(comboBox_tp);

        bthuy = new JButton("Hủy");
        bthuy.setForeground(new Color(255, 255, 255));
        bthuy.setBounds(265, 190, 85, 30);
        bthuy.setBackground(new Color(255, 0, 0));
        bthuy.setFont(new Font("Arial", Font.BOLD, 12));
        bthuy.setBorderPainted(false);
        bthuy.addActionListener(this);
        p2.add(bthuy);

        btluu = new JButton("Lưu");
        btluu.setBounds(400, 190, 85, 30);
        btluu.setBackground(Color.decode("#4cd137"));
        btluu.setForeground(Color.WHITE);
        btluu.setFont(new Font("Arial", Font.BOLD, 12));
        btluu.setBorderPainted(false);
        btluu.addActionListener(this);
        p2.add(btluu);

        LoaddataComboboxbophan();
    }

    public void setEditMode(String ma, String ten, String diachi, String sdt) {
        lblTitle.setText("Sửa phòng ban");
        jtfMaPhong.setText(ma);
        jtfMaPhong.setEditable(false); 
        jtfTenPhong.setText(ten);
        jtfdiachi.setText(diachi);
        jtfsdt.setText(sdt);
        comboBox_tp.setEnabled(true);
    }
    public void setupAddMode()
    {
    	lblTitle.setText("Thêm phòng ban");
    	jtfMaPhong.setText("");
    	jtfTenPhong.setText("");
    	jtfdiachi.setText("");
    	jtfsdt.setText("");
    	comboBox_tp.setEnabled(false);
    }
    public void setParent(Phongban1_GUI parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        if (src.equals("Lưu")) {
            handleSave();
        } else if (src.equals("Hủy")) {
            this.dispose();
        }
    }

    private void handleSave() {
    	if(jtfMaPhong.getText().equals("")||jtfTenPhong.getText().equals("")
    	|| jtfdiachi.getText().equals("")||jtfsdt.getText().equals("")){
    		JOptionPane.showMessageDialog(this,"Vui lòng điền đầy đủ thông tin!");
    		return;
    	}
    	else
    	{

	    	String ma = jtfMaPhong.getText().trim();
	    	String ten = jtfTenPhong.getText().trim();
	    	String diachi = jtfdiachi.getText().trim();
	    	String sdt = jtfsdt.getText().trim();
	    	String mabp = comboBox.getSelectedItem().toString();
	    	String arr[]=mabp.split("-");
	    	mabp=arr[0];
	    	String matp = "";
	    	if(comboBox_tp.isEnabled() && comboBox_tp.getSelectedItem()!=null)
	    	{
	    		String selected= comboBox_tp.getSelectedItem().toString();
	    		if(selected.equals("Đang tuyển"))
	    		{
	    			matp="";
	    		}
	    		else
	    		{
	    			String []tmp=selected.split("-");
	    			matp=tmp[0].trim();
	    		}
	    	}
	
	    	Phongban_DTO pb = new Phongban_DTO(ma, ten, diachi, sdt, mabp, matp);
	    	Phongban_BUS pbb=new Phongban_BUS();
	    	String message="";
	    	if(lblTitle.getText().equals("Thêm phòng ban"))
	    	{
	    		message=pbb.addPhongBan(pb);
	    		comboBox_tp.removeAllItems();
	    		comboBox_tp.addItem("Đang tuyển");
	    	}
	    	else
	    	{
	    		message=pbb.updatePhongBan(pb);
	    	}
	    	JOptionPane.showMessageDialog(this, message);
	
	    	if (message.contains("thành công")) {
	
	    		if (parent != null) parent.renderTable();
	
	    		this.dispose();
	
	    	}
    	}
    }

    public void LoaddataComboboxbophan() {
        Phongban_BUS pbb = new Phongban_BUS();
        ArrayList<String> list = pbb.getTenMaBoPhan();
        comboBox.removeAllItems();
        for (String tmp : list) {
            comboBox.addItem(tmp);
        }
    }
    public void LoaddataComboboxTP(String mapb,String matp)
    {
    	Phongban_BUS pbb= new Phongban_BUS();
    	ArrayList<String>list=pbb.getdataComboBoxTP(mapb);
    	comboBox_tp.removeAllItems();
    	comboBox_tp.addItem("Đang tuyển");
    	for(String x :list)
    	{
    		comboBox_tp.addItem(x);
    	}
        if (matp == null || matp.equals("Đang tuyển") || matp.isEmpty()) {
            comboBox_tp.setSelectedItem("Đang tuyển");
        } else {
            for (int i = 0; i < comboBox_tp.getItemCount(); i++) {
                String item = comboBox_tp.getItemAt(i).toString();
                
                // So sánh: Nếu mục bắt đầu bằng "Mã -", chắc chắn là người đó
                if (item.startsWith(matp + " -")) {
                    comboBox_tp.setSelectedIndex(i);
                    break; 
                }
            }
        }
    }
}