package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        this.setSize(750, 320);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true); 
        this.setLayout(new BorderLayout());

        // Tiêu đề màu xanh dịu
        JPanel pTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pTitle.setBackground(new Color(41, 128, 185)); // Xanh dương đậm hiện đại
        lblTitle = new JLabel("Thêm phòng ban");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(Color.WHITE);
        pTitle.add(lblTitle);
        this.add(pTitle, BorderLayout.NORTH);

        // Body chứa form (Dùng GridLayout cho đều và an toàn)
        JPanel pBody = new JPanel(new BorderLayout());
        pBody.setBackground(Color.WHITE);
        pBody.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(41, 128, 185), 2), // Viền xanh tổng thể
            new EmptyBorder(20, 30, 10, 30) // Padding trong
        ));

        JPanel pForm = new JPanel(new GridLayout(3, 4, 20, 20)); // 3 dòng, 4 cột (Nhãn-Input-Nhãn-Input)
        pForm.setBackground(Color.WHITE);
        
        Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 14);

        // Dòng 1
        pForm.add(createLabel("Mã phòng:", fontLabel));
        jtfMaPhong = new JTextField(); jtfMaPhong.setFont(fontInput);
        pForm.add(jtfMaPhong);

        pForm.add(createLabel("Tên phòng:", fontLabel));
        jtfTenPhong = new JTextField(); jtfTenPhong.setFont(fontInput);
        pForm.add(jtfTenPhong);

        // Dòng 2
        pForm.add(createLabel("Địa chỉ:", fontLabel));
        jtfdiachi = new JTextField(); jtfdiachi.setFont(fontInput);
        pForm.add(jtfdiachi);

        pForm.add(createLabel("Số điện thoại:", fontLabel));
        jtfsdt = new JTextField(); jtfsdt.setFont(fontInput);
        pForm.add(jtfsdt);

        // Dòng 3
        pForm.add(createLabel("Mã bộ phận:", fontLabel));
        comboBox = new JComboBox(); comboBox.setFont(fontInput);
        pForm.add(comboBox);

        pForm.add(createLabel("Trưởng phòng:", fontLabel));
        comboBox_tp = new JComboBox(); comboBox_tp.setFont(fontInput);
        comboBox_tp.setEnabled(false);
        pForm.add(comboBox_tp);

        pBody.add(pForm, BorderLayout.CENTER);

        // Phần Nút bấm
        JPanel pButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pButtons.setBackground(Color.WHITE);

        bthuy = new JButton("Hủy");
        bthuy.setPreferredSize(new Dimension(100, 35));
        bthuy.setFont(fontLabel);
        bthuy.putClientProperty("FlatLaf.styleClass", "danger"); // Nút đỏ
        bthuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bthuy.addActionListener(this);
        
        btluu = new JButton("Lưu");
        btluu.setPreferredSize(new Dimension(100, 35));
        btluu.setFont(fontLabel);
        btluu.putClientProperty("FlatLaf.styleClass", "primary"); // Nút xanh lục/xanh dương
        btluu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btluu.addActionListener(this);

        pButtons.add(bthuy);
        pButtons.add(btluu);
        pBody.add(pButtons, BorderLayout.SOUTH);

        this.add(pBody, BorderLayout.CENTER);

        LoaddataComboboxbophan();
    }

    private JLabel createLabel(String text, Font f) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(f);
        return lbl;
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
    	comboBox_tp.removeAllItems();
    	comboBox_tp.addItem("Đang tuyển");
    	comboBox_tp.setSelectedIndex(0);
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
    		JOptionPane.showMessageDialog(this,"Vui lòng điền đầy đủ thông tin!","Thông báo",JOptionPane.ERROR_MESSAGE);
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
	    	JOptionPane.showMessageDialog(this, message,"Thông báo",JOptionPane.INFORMATION_MESSAGE);
	
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

                if (item.startsWith(matp + " -")) {
                    comboBox_tp.setSelectedIndex(i);
                    break; 
                }
            }
        }
    }
}