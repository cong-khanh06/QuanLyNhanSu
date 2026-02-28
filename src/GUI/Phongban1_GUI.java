package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import DTO.Phongban_DTO;
import DAO.Phongban_DAO;
import BUS.Phongban_BUS;
import DTO.NhanVien_DTO;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
public class Phongban1_GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTable table_1;
	private ArrayList<Phongban_DTO> arr;
	private Phongban_BUS pbb;
	private DefaultTableModel model;
	private JLabel jlnhanvien;
	private JTextField jtfSearch;
	private JLabel jlthongtin;
	private JLabel jlmaso;
	private JLabel jlvaluengayvaolam;
	private JLabel jlvaluephongban;
	private JLabel jlvaluedc;
	private JLabel jlvaluesdt;
	private JLabel jlvaluegioitinh;
	private JLabel jlvaluehoten;
	private JLabel jlvaluemaso;
	private JLabel jlngayvaolam;
	private JComponent jlphongban;
	private JComponent jldiachi;
	private JLabel jlhoten;
	private JComponent jlgioitinh;
	private JComponent jlsdt;
	private JButton btsearch;
	public Phongban1_GUI() {
		init();
		this.setTitle("Quản lý phòng ban");
        this.setSize(1080, 700); // Kích thước cửa sổ
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng app khi nhấn X
        this.setLocationRelativeTo(null); // Hiển thị giữa màn hình
        this.setVisible(true); // Hiển thị lên
		
	}
	public void init()
	{
		setBackground(Color.white);
		getContentPane().setLayout(null);		
		pbb=new Phongban_BUS();
		arr=new ArrayList<>();
		arr=pbb.getdatabase();
		JPanel panelTop = new JPanel();
		panelTop.setBackground(Color.white);
		panelTop.setBounds(10,10,1070,300);
		panelTop.setBorder(new LineBorder(new Color(128, 128, 128)));
		getContentPane().add(panelTop);
		panelTop.setLayout(null);
		
		JPanel panelTopTitle = new JPanel();
		panelTopTitle.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelTopTitle.setBounds(0,1,1070,40);
		panelTopTitle.setBackground(new Color(0, 255, 255));
		panelTop.add(panelTopTitle);
		panelTopTitle.setLayout(null);
		
		JButton btthem = new JButton("Thêm");
		btthem.setHorizontalAlignment(SwingConstants.LEFT);
		btthem.setHorizontalTextPosition(SwingConstants.RIGHT);
		btthem.setIconTextGap(5);
		btthem.setForeground(new Color(0, 0, 255));
		btthem.setBackground(new Color(73, 209, 141));
		btthem.setBounds(766, 9, 96, 23);
		panelTopTitle.add(btthem);
		btthem.setBorderPainted(false); 
		btthem.setContentAreaFilled(true); 
		btthem.setFocusPainted(false);
		ImageIcon imageicon=new ImageIcon(getClass().getResource("/addpb.png"));
		Image image=imageicon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btthem.setIcon(new ImageIcon(image));
		
		JButton btxoa = new JButton("Xóa");
		btxoa.setHorizontalTextPosition(SwingConstants.RIGHT);
		btxoa.setHorizontalAlignment(SwingConstants.LEFT);
		btxoa.setIconTextGap(5);
		btxoa.setForeground(new Color(0, 0, 255));
		btxoa.setBounds(971, 9, 89, 23);
		panelTopTitle.add(btxoa);
		btxoa.setBorderPainted(false); 
		btxoa.setContentAreaFilled(true);
		btxoa.setFocusPainted(false);
		btxoa.setBackground(new Color(73, 209, 141));
		ImageIcon iconxoa=new ImageIcon(getClass().getResource("/deletepb.png"));
		Image imagexoa= iconxoa.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btxoa.setIcon(new ImageIcon(imagexoa));


		
		btsearch = new JButton("Tìm kiếm");
		btsearch.setHorizontalAlignment(SwingConstants.LEFT);
		btsearch.setHorizontalTextPosition(SwingConstants.RIGHT);
		btsearch.setForeground(new Color(0, 0, 255));
		btsearch.setBounds(544, 9, 101, 23);
		panelTopTitle.add(btsearch);
		btsearch.setBorderPainted(false); 
		btsearch.setContentAreaFilled(true);
		btsearch.setFocusPainted(false);
		btsearch.setBackground(new Color(73, 209, 141));
		ImageIcon iconsearch =new ImageIcon(getClass().getResource("/searchpb.png"));
		Image imagesearch=iconsearch.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btsearch.setIcon(new ImageIcon(imagesearch));
		


		
			JLabel lbtt = new JLabel("Quản lý phòng ban");
			lbtt.setBounds(10, 4, 280, 30);
			panelTopTitle.add(lbtt);
			lbtt.setFont(new Font("Arial",1,14));
			lbtt.setForeground(new Color(0, 0, 0));
			
			JButton btsua = new JButton("Sửa");
			btsua.setIconTextGap(5);
			btsua.setHorizontalTextPosition(SwingConstants.RIGHT);
			btsua.setHorizontalAlignment(SwingConstants.LEFT);
			btsua.setForeground(new Color(0, 0, 255));
			btsua.setBackground(new Color(73, 209, 141));
			btsua.setBounds(872, 9, 89, 23);
			panelTopTitle.add(btsua);
			btsua.setBorderPainted(false); 
			btsua.setContentAreaFilled(true);
			btsua.setFocusPainted(false);
			btsua.setBackground(new Color(73, 209, 141));
			ImageIcon iconsua=new ImageIcon(getClass().getResource("/fixpb.png"));
			Image imagesua=iconsua.getImage().getScaledInstance(20, 20,Image.SCALE_SMOOTH);
			btsua.setIcon(new ImageIcon(imagesua));
			
			jtfSearch = new JTextField();
			jtfSearch.setBounds(395, 9, 149, 23);
			panelTopTitle.add(jtfSearch);
			jtfSearch.setColumns(10);
			
			JButton btrefresh = new JButton("Refresh");
			btrefresh.setFont(new Font("Arial", Font.BOLD, 11));
			btrefresh.setForeground(new Color(0, 0, 255));
			btrefresh.setBounds(655, 9, 101, 23);
			btrefresh.setFocusPainted(false);
			btrefresh.setContentAreaFilled(true);
			btrefresh.setBorderPainted(false);
			ImageIcon iconrefresh=new ImageIcon(getClass().getResource("/Refresh.png"));
			Image imagerefresh=iconrefresh.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			btrefresh.setIcon(new ImageIcon(imagerefresh));
			btrefresh.setBackground(new Color(73, 209, 141));

			panelTopTitle.add(btrefresh);
			
			table = new JTable();
			table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"STT", "M\u00E3 ph\u00F2ng ban", "T\u00EAn ph\u00F2ng ban", "Tr\u01B0\u1EDFng ph\u00F2ng", "S\u1ED1 l\u01B0\u1EE3ng", "L\u01B0\u01A1ng trung  b\u00ECnh"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table.getColumnModel().getColumn(1).setPreferredWidth(86);
			table.getColumnModel().getColumn(2).setPreferredWidth(86);
			table.getColumnModel().getColumn(3).setPreferredWidth(86);
			table.getColumnModel().getColumn(4).setPreferredWidth(69);
			table.getColumnModel().getColumn(5).setPreferredWidth(102);
			table.setBounds(0, 0, 1, 1);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(0, 40, 1070, 260);
			panelTop.add(scrollPane);
			
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel.setBounds(10, 321, 680, 342);
			getContentPane().add(panel);
			panel.setLayout(null);
			
			jlnhanvien = new JLabel("Nhân Viên");
			jlnhanvien.setFont(new Font("Tahoma", Font.BOLD, 14));
			jlnhanvien.setBounds(10, 4, 359, 30);
			panel.add(jlnhanvien);
			
			table_1 = new JTable();
			table_1.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"STT", "M\u00E3 nh\u00E2n vi\u00EAn", "H\u1ECD v\u00E0 t\u00EAn"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table_1.setBounds(0, 0, 1, 1);
			JScrollPane scrollPane_1 = new JScrollPane(table_1);
			scrollPane_1.setBounds(0, 52, 680, 291);
			panel.add(scrollPane_1);
			
			
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_1.setBounds(700, 321, 380, 342);
			getContentPane().add(panel_1);
			panel_1.setLayout(null);
			
			jlthongtin = new JLabel("Thông tin nhân viên");
			jlthongtin.setFont(new Font("Arial", Font.BOLD, 14));
			jlthongtin.setForeground(new Color(0, 0, 0));
			jlthongtin.setBounds(10, 11, 188, 31);
			panel_1.add(jlthongtin);
			
			jlmaso = new JLabel("Mã số:");
			jlmaso.setFont(new Font("Tahoma", Font.BOLD, 13));
			jlmaso.setBounds(10, 70, 49, 22);
			panel_1.add(jlmaso);
			
			jlhoten = new JLabel("Họ tên:");
			jlhoten.setFont(new Font("Arial", Font.BOLD, 13));
			jlhoten.setBounds(10, 110, 49, 22);
			panel_1.add(jlhoten);
			
			jlgioitinh = new JLabel("Giới tính:");
			jlgioitinh.setFont(new Font("Arial", Font.BOLD, 13));
			jlgioitinh.setBounds(10, 150, 69, 22);
			panel_1.add(jlgioitinh);
			
			jlsdt = new JLabel("Số điện thoại:");
			jlsdt.setFont(new Font("Arial", Font.BOLD, 13));
			jlsdt.setBounds(10, 190, 96, 22);
			panel_1.add(jlsdt);
			
			jldiachi = new JLabel("Địa chỉ:");
			jldiachi.setFont(new Font("Arial", Font.BOLD, 13));
			jldiachi.setBounds(10, 230, 69, 22);
			panel_1.add(jldiachi);
			
			jlphongban = new JLabel("Phòng ban:");
			jlphongban.setFont(new Font("Arial", Font.BOLD, 13));
			jlphongban.setBounds(10, 270, 79, 22);
			panel_1.add(jlphongban);
			
			jlngayvaolam = new JLabel("Ngày vào làm:");
			jlngayvaolam.setFont(new Font("Arial", Font.BOLD, 13));
			jlngayvaolam.setBounds(10, 310, 96, 22);
			panel_1.add(jlngayvaolam);
			
			jlvaluemaso = new JLabel("");
			jlvaluemaso.setFont(new Font("Arial", Font.PLAIN, 13));
			jlvaluemaso.setForeground(new Color(0, 0, 255));
			jlvaluemaso.setBounds(69, 70, 152, 22);
			panel_1.add(jlvaluemaso);
			
			jlvaluehoten = new JLabel("");
			jlvaluehoten.setFont(new Font("Arial", Font.PLAIN, 13));
			jlvaluehoten.setForeground(new Color(0, 0, 255));
			jlvaluehoten.setBounds(69, 110, 152, 22);
			panel_1.add(jlvaluehoten);
			
			jlvaluegioitinh = new JLabel("");
			jlvaluegioitinh.setForeground(new Color(0, 0, 255));
			jlvaluegioitinh.setFont(new Font("Arial", Font.PLAIN, 13));
			jlvaluegioitinh.setBounds(90, 150, 199, 22);
			panel_1.add(jlvaluegioitinh);
			
			jlvaluesdt = new JLabel("");
			jlvaluesdt.setForeground(new Color(0, 0, 255));
			jlvaluesdt.setFont(new Font("Arial", Font.PLAIN, 13));
			jlvaluesdt.setBounds(116, 190, 152, 22);
			panel_1.add(jlvaluesdt);
			
			jlvaluedc = new JLabel("");
			jlvaluedc.setForeground(new Color(0, 0, 255));
			jlvaluedc.setFont(new Font("Arial", Font.PLAIN, 13));
			jlvaluedc.setBounds(89, 230, 179, 22);
			panel_1.add(jlvaluedc);
			
			jlvaluephongban = new JLabel("");
			jlvaluephongban.setForeground(new Color(0, 0, 255));
			jlvaluephongban.setFont(new Font("Arial", Font.PLAIN, 13));
			jlvaluephongban.setBounds(99, 270, 160, 22);
			panel_1.add(jlvaluephongban);
			
			jlvaluengayvaolam = new JLabel("");
			jlvaluengayvaolam.setForeground(new Color(0, 0, 255));
			jlvaluengayvaolam.setFont(new Font("Arial", Font.PLAIN, 13));
			jlvaluengayvaolam.setBounds(116, 310, 140, 22);
			panel_1.add(jlvaluengayvaolam);
			
			btthem.addActionListener(this);
			btsua.addActionListener(this);
			btxoa.addActionListener(this);
			btsearch.addActionListener(this);
			btrefresh.addActionListener(this);
			
			setMouse(btthem);
			setMouse(btsua);
			setMouse(btxoa);
			setMouse(btsearch);
			setMouse(btrefresh);
			
			renderTable();
			addEvent();
			if(table.getRowCount()>0)
			{
				table.setRowSelectionInterval(0, 0);
				String madautien=table.getValueAt(0, 1).toString();
				jlnhanvien.setText("Nhân Viên - "+table.getValueAt(0, 2).toString());

				LoaddataNhanVien(madautien);
				
			}
			
	}
	public void filterTable(String text)
	{
		model=(DefaultTableModel)table.getModel();
		model.setRowCount(0);
		int stt=1;
		String searchText=text.toLowerCase().trim();
		if (searchText.isEmpty()) {
	        renderTable(); 
	        return;	
	    }
		for(Phongban_DTO pb : arr)
		{
			String tenPB = pb.getTenphongban().toLowerCase();
	        String maPB = pb.getMaphongban().toLowerCase();
	        
	        if (tenPB.contains(searchText) || maPB.contains(searchText)) {
	            model.addRow(pbb.buildRowObject(pb, stt++));
	        }
			
		}
		if (table.getRowCount() > 0) {
	        table.setRowSelectionInterval(0, 0);
	        String mapb = table.getValueAt(0, 1).toString();
	        jlnhanvien.setText("Nhân Viên - " + table.getValueAt(0, 2).toString());
	        LoaddataNhanVien(mapb);
	    } else {
	        xoatrangthongtin();
	    }
	}
	public void renderTable()
	{
		if(jtfSearch!=null)
		{
			jtfSearch.setText("");
		}
		arr=pbb.getdatabase();
		model=(DefaultTableModel)table.getModel();
		model.setRowCount(0);
		Object[][] oj=pbb.getObjectToRender();
		for(Object[] row:oj)
		{
			model.addRow(row);
		}
		table.setModel(model);
	}
	public static void main(String[] args) {
        try {
            // Chạy giao diện
            new Phongban1_GUI();
        } catch (Exception e) {
        	
            e.printStackTrace();
        }
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		String src=e.getActionCommand();
		Object source = e.getSource();
		if(src.equals("Thêm"))
		{
			Phongban2_GUI framethem=new Phongban2_GUI();
			framethem.setParent(this);
			framethem.setupAddMode();
			framethem.setVisible(true);
			framethem.setLocationRelativeTo(null);
		}
		else if(src.equals("Sửa"))
		{
			
			int i=table.getSelectedRow();
			if(i!=-1)
			{
				String ma=table.getValueAt(i, 1).toString();
				String tetruongphong=table.getValueAt(i, 3).toString();
				Phongban_DTO pbchon=null;
				for(Phongban_DTO tmp:arr)
				{
					if(tmp.getMaphongban().equals(ma))
					{
						pbchon=tmp;
						break;
					}
				}
				if(pbchon!=null)
				{
					Phongban2_GUI framesua=new Phongban2_GUI();
					framesua.setParent(this);
					String matruongphong=pbchon.getMatruongphong();
					String ten=table.getValueAt(i, 2).toString();
					framesua.setEditMode(ma,ten,pbchon.getDiachi(),pbchon.getSdt());
					framesua.LoaddataComboboxTP(pbchon.getMaphongban(),matruongphong);
					framesua.setVisible(true);
					framesua.setLocationRelativeTo(null);
				}

			}
			else {
	            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 hàng để sửa!");
	        }
		}
			else if (src.equals("Tìm kiếm")) {
		        String input = JOptionPane.showInputDialog(this, 
		                "Nhập tên hoặc mã phòng ban cần tìm:", 
		                "Tìm kiếm nhanh", 
		                JOptionPane.QUESTION_MESSAGE);
		        
		        if (input != null && !input.trim().isEmpty()) {
		            jtfSearch.setText(input.trim());
		            filterTable(input.trim());
		        }
		    }
		
		else if(src.equals("Xóa"))
		{
			int row=table.getSelectedRow();
			if(row!=-1)
			{
				String ma=table.getValueAt(row,1).toString();
				String ten=table.getValueAt(row, 2).toString();
				int i=JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa "+ten+" (Mã: "+ma+") không?","Xác nhận xóa",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
				if(i==JOptionPane.YES_OPTION)
				{
					String message=pbb.DeletePB(ma);
					JOptionPane.showMessageDialog(this, message);
					if(message.contains("thành công"))
					{
						renderTable();
					}
				}
				
				
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phòng ban để xóa!");
			}
			
		}
		else if(src.equals("Refresh"))
		{
			renderTable();
			if(table.getRowCount()>0)
			{
				table.setRowSelectionInterval(0, 0);
				String madautien=table.getValueAt(0, 1).toString();
				jlnhanvien.setText("Nhân Viên - "+table.getValueAt(0, 2).toString());

				LoaddataNhanVien(madautien);
				
			}
		}
		
		
	}
	public void LoaddataNhanVien(String mapb)
	{
		DefaultTableModel model1=(DefaultTableModel)table_1.getModel();
		model1.setRowCount(0);
		Object[][] data=pbb.getNhanVienbyPB(mapb);
		for(Object []row:data)
		{
			model1.addRow(row);
		}
		if(table_1.getRowCount()>0)
		{
			HienthithongtinNV(0,mapb);
		}
		else
		{
			xoatrangthongtin();
		}
	}
	public void addEvent()
	{
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();
				if(row!=-1)
				{					
					jlnhanvien.setText("Nhân Viên - "+table.getValueAt(row, 2).toString());
					String mapb=table.getValueAt(row, 1).toString();
					LoaddataNhanVien(mapb);
				}
				
			}
			
		});
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=table_1.getSelectedRow();
				int rowpb=table.getSelectedRow();
				if(row!=-1 && rowpb!=-1)
				{
					String mapb = table.getValueAt(rowpb, 1).toString();
		            HienthithongtinNV(row, mapb);
				}
				
			}
		});
		jtfSearch.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				filterTable(jtfSearch.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filterTable(jtfSearch.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filterTable(jtfSearch.getText());
			}
			
		});
	}
	public void HienthithongtinNV(int row,String mapb)
	{
		ArrayList<NhanVien_DTO> list=pbb.getlistNhanVien(mapb);
		if(row!=-1 &&row<table_1.getRowCount())
		{
			NhanVien_DTO nv=list.get(row);
			jlvaluemaso.setText(nv.getManv());
			jlvaluehoten.setText(nv.getHoTen());
			jlvaluegioitinh.setText(nv.getGioiTinh());
			jlvaluesdt.setText(nv.getSdt());
			jlvaluedc.setText(nv.getDiaChi());
			jlvaluengayvaolam.setText(pbb.Dinhdangngay(nv.getNgayVaoLam()));
	        String pbText = jlnhanvien.getText().replace("Nhân Viên - ", "");
	        jlvaluephongban.setText(pbText);
			
		}
		
	}
	public void xoatrangthongtin() {
	    jlvaluemaso.setText("");
	    jlvaluehoten.setText("");
	    jlvaluegioitinh.setText("");
	    jlvaluesdt.setText("");
	    jlvaluedc.setText("");
	    jlvaluengayvaolam.setText("");
	    jlvaluephongban.setText("");
	}
	public void setMouse(JButton bt)
	{
		Color color=bt.getBackground();
		Color hoverColor = new Color(46, 204, 113);
		bt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				bt.setBackground(hoverColor);
				bt.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				bt.setBackground(color);
			}
		});
		
		
	}
}
