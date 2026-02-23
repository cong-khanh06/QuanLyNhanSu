package GUI;

import java.awt.Color;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import DTO.Phongban_DTO;
import DAO.Phongban_DAO;
import BUS.Phongban_BUS;
import DTO.NhanVien_DTO;
import javax.swing.SwingConstants;
public class Phongban1_GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTable table_1;
	private ArrayList<Phongban_DTO> arr;
	private Phongban_BUS pbb;
	private DefaultTableModel model;
	private JLabel jlnhanvien;
	public Phongban1_GUI() {
		init();
		this.setTitle("Quản lý phòng ban");
        this.setSize(1100, 700); // Kích thước cửa sổ
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng app khi nhấn X
        this.setLocationRelativeTo(null); // Hiển thị giữa màn hình
        this.setVisible(true); // Hiển thị lên
		
	}
	public void init()
	{
		setBackground(Color.white);
		getContentPane().setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(1080, 700));
		
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
		btthem.setBounds(617, 9, 96, 23);
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
		btxoa.setBounds(822, 9, 89, 23);
		panelTopTitle.add(btxoa);
		btxoa.setBorderPainted(false); 
		btxoa.setContentAreaFilled(true);
		btxoa.setFocusPainted(false);
		btxoa.setBackground(new Color(73, 209, 141));
		ImageIcon iconxoa=new ImageIcon(getClass().getResource("/deletepb.png"));
		Image imagexoa= iconxoa.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btxoa.setIcon(new ImageIcon(imagexoa));


		
		JButton btsearch = new JButton("Tìm kiếm");
		btsearch.setHorizontalAlignment(SwingConstants.LEFT);
		btsearch.setHorizontalTextPosition(SwingConstants.RIGHT);
		btsearch.setForeground(new Color(0, 0, 255));
		btsearch.setBounds(921, 9, 115, 23);
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
			btsua.setBounds(723, 9, 89, 23);
			panelTopTitle.add(btsua);
			btsua.setBorderPainted(false); 
			btsua.setContentAreaFilled(true);
			btsua.setFocusPainted(false);
			btsua.setBackground(new Color(73, 209, 141));
			ImageIcon iconsua=new ImageIcon(getClass().getResource("/fixpb.png"));
			Image imagesua=iconsua.getImage().getScaledInstance(20, 20,Image.SCALE_SMOOTH);
			btsua.setIcon(new ImageIcon(imagesua));
			
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
			panel.setBounds(10, 321, 680, 368);
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
					"STT", "M\u00E3 nh\u00E2n vi\u00EAn", "H\u1ECD v\u00E0 t\u00EAn", "Gi\u1EDBi t\u00EDnh", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i", "\u0110\u1ECBa ch\u1EC9"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, true
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table_1.setBounds(0, 0, 1, 1);
			JScrollPane scrollPane_1 = new JScrollPane(table_1);
			scrollPane_1.setBounds(0, 50, 680, 293);
			panel.add(scrollPane_1);
			
			
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_1.setBounds(700, 321, 380, 342);
			getContentPane().add(panel_1);
			
			btthem.addActionListener(this);
			btsua.addActionListener(this);
			renderTable();
			addEvent();
			if(table.getRowCount()>0)
			{
				String madautien=table.getValueAt(0, 1).toString();
				jlnhanvien.setText("Nhân Viên - "+table.getValueAt(0, 2).toString());

				LoaddataNhanVien(madautien);
				
			}
			
	}
	public void renderTable()
	{
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
	}
	
	
}
