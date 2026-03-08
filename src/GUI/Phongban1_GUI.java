package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import DTO.Phongban_DTO;
import BUS.Phongban_BUS;
import DTO.NhanVien_DTO;

public class Phongban1_GUI extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTable table, table_1;
    private ArrayList<Phongban_DTO> arr;
    private Phongban_BUS pbb;
    private DefaultTableModel model;
    private JLabel jlnhanvien, jlthongtin;
    private JTextField jtfSearch;
    private JLabel jlvaluemaso, jlvaluehoten, jlvaluegioitinh, jlvaluesdt, jlvaluedc, jlvaluephongban, jlvaluengayvaolam;
    private JButton btthem, btxoa, btsua, btsearch, btrefresh;

    public Phongban1_GUI() {
        pbb = new Phongban_BUS();
        init();
        renderTable();
        addEvent();
        
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
            String madautien = table.getValueAt(0, 1).toString();
            jlnhanvien.setText("Nhân Viên - " + table.getValueAt(0, 2).toString());
            LoaddataNhanVien(madautien);
        }
    }

    public void init() {
        setBackground(Color.white);
        this.setLayout(new BorderLayout(0, 0));
        this.setBorder(null);      
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setPreferredSize(new Dimension(1000, 300));
        panelTop.setBorder(new LineBorder(Color.GRAY));
        panelTop.setBackground(Color.white);

        JPanel panelTopToolbar = new JPanel(new BorderLayout());
        panelTopToolbar.setBackground(new Color(0, 255, 255));
        panelTopToolbar.setPreferredSize(new Dimension(0, 45));

        JLabel lbtt = new JLabel("  Quản lý phòng ban");
        lbtt.setFont(new Font("Arial", Font.BOLD, 16));
        panelTopToolbar.add(lbtt, BorderLayout.WEST);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 10));
        pnlButtons.setOpaque(false);

        jtfSearch = new JTextField(15);
        btsearch = createStyledButton("Tìm kiếm", "/img/searchpb.png");
        btrefresh = createStyledButton("Refresh", "/img/Refresh.png");
        btthem = createStyledButton("Thêm", "/img/addpb.png");
        btsua = createStyledButton("Sửa", "/img/fixpb.png");
        btxoa = createStyledButton("Xóa", "/img/deletepb.png");

        pnlButtons.add(jtfSearch);
        pnlButtons.add(btsearch);
        pnlButtons.add(btrefresh);
        pnlButtons.add(btthem);
        pnlButtons.add(btsua);
        pnlButtons.add(btxoa);

        panelTopToolbar.add(pnlButtons, BorderLayout.EAST);
        panelTop.add(panelTopToolbar, BorderLayout.NORTH);

        // Bảng Phòng Ban
        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"STT", "Mã phòng ban", "Tên phòng ban", "Trưởng phòng", "Số lượng", "Lương trung bình"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        JScrollPane scrollPaneTop=new JScrollPane(table);
        scrollPaneTop.getViewport().setBackground(Color.white);
        
        panelTop.add(scrollPaneTop,BorderLayout.CENTER);
        this.add(panelTop, BorderLayout.NORTH);
        
        JPanel panelBottom = new JPanel(new GridLayout(1, 2, 0, 0));
        panelBottom.setOpaque(false);

        JPanel panelLeft = new JPanel(new BorderLayout());
        panelLeft.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Danh sách nhân viên"));
        panelLeft.setBackground(Color.white);
        
        jlnhanvien = new JLabel("Nhân Viên", SwingConstants.CENTER);
        jlnhanvien.setFont(new Font("Arial", Font.BOLD, 14));
        panelLeft.add(jlnhanvien, BorderLayout.NORTH);

        table_1 = new JTable();
        table_1.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"STT", "Mã nhân viên", "Họ và tên"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        JScrollPane scrollPanelLeft = new JScrollPane(table_1);
        scrollPanelLeft.getViewport().setBackground(Color.WHITE);
        panelLeft.add(scrollPanelLeft, BorderLayout.CENTER);

        JPanel panelRight = new JPanel(new BorderLayout());
        panelRight.setBackground(Color.WHITE);
        panelRight.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Thông tin chi tiết"));

        JPanel pnlContent = new JPanel(new BorderLayout(20, 0)); 
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(50, 15, 10, 10));

        
        JPanel pnlLabels = new JPanel(new GridLayout(7, 1, 0, 20)); 
        pnlLabels.setBackground(Color.WHITE);
        
        String[] lbText = {"Mã số:", "Họ tên:", "Giới tính:", "Số điện thoại:", "Địa chỉ:", "Phòng ban:", "Ngày vào làm:"};
        for (String text : lbText) {
            JLabel lbl = new JLabel(text);
            lbl.setFont(new Font("Arial", Font.BOLD, 13));
            pnlLabels.add(lbl);
        }

        JPanel pnlValues = new JPanel(new GridLayout(7, 1, 0, 15));
        pnlValues.setBackground(Color.WHITE);

        jlvaluemaso = createValueLabel();
        jlvaluehoten = createValueLabel();
        jlvaluegioitinh = createValueLabel();
        jlvaluesdt = createValueLabel();
        jlvaluedc = createValueLabel();
        jlvaluephongban = createValueLabel();
        jlvaluengayvaolam = createValueLabel();

        pnlValues.add(jlvaluemaso);
        pnlValues.add(jlvaluehoten);
        pnlValues.add(jlvaluegioitinh);
        pnlValues.add(jlvaluesdt);
        pnlValues.add(jlvaluedc);
        pnlValues.add(jlvaluephongban);
        pnlValues.add(jlvaluengayvaolam);

        pnlContent.add(pnlLabels, BorderLayout.WEST);   
        pnlContent.add(pnlValues, BorderLayout.CENTER); 
        
        JPanel pnlNorthWrapper = new JPanel(new BorderLayout());
        pnlNorthWrapper.setBackground(Color.WHITE);
        pnlNorthWrapper.add(pnlContent, BorderLayout.NORTH);

        panelRight.add(pnlNorthWrapper, BorderLayout.CENTER);

        panelBottom.add(panelLeft);
        panelBottom.add(panelRight);
        this.add(panelBottom, BorderLayout.CENTER);
        panelBottom.add(panelLeft);
        panelBottom.add(panelRight);
        this.add(panelBottom, BorderLayout.CENTER);

        // Gán sự kiện
        btthem.addActionListener(this);
        btsua.addActionListener(this);
        btxoa.addActionListener(this);
        btsearch.addActionListener(this);
        btrefresh.addActionListener(this);
    }

    // Hàm bổ trợ tạo nút bấm có style đồng nhất
    private JButton createStyledButton(String text, String iconPath) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(73, 209, 141));
        btn.setForeground(Color.BLUE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        java.net.URL url = getClass().getResource(iconPath);
        if (url != null) {
            ImageIcon icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));
            btn.setIcon(icon);
        }
        
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(46, 204, 113)); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(new Color(73, 209, 141)); }
        });
        return btn;
    }

    private JLabel createValueLabel() {
        JLabel label = new JLabel("");
        label.setForeground(Color.BLUE);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        return label;
    }

    
    
    public void filterTable(String text) {
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        int stt = 1;
        String searchText = text.toLowerCase().trim();
        if (searchText.isEmpty()) { renderTable(); return; }
        for (Phongban_DTO pb : arr) {
            if (pb.getTenphongban().toLowerCase().contains(searchText) || pb.getMaphongban().toLowerCase().contains(searchText)) {
                model.addRow(pbb.buildRowObject(pb, stt++));
            }
        }
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
            LoaddataNhanVien(table.getValueAt(0, 1).toString());
        } else { xoatrangthongtin(); }
    }

    public void renderTable() {
        if (jtfSearch != null) jtfSearch.setText("");
        arr = pbb.getdatabase();
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        Object[][] oj = pbb.getObjectToRender();
        for (Object[] row : oj) model.addRow(row);
    }

    public void LoaddataNhanVien(String mapb) {
        DefaultTableModel model1 = (DefaultTableModel) table_1.getModel();
        model1.setRowCount(0);
        Object[][] data = pbb.getNhanVienbyPB(mapb);
        for (Object[] row : data) model1.addRow(row);
        if (table_1.getRowCount() > 0) HienthithongtinNV(0, mapb);
        else xoatrangthongtin();
    }

    public void HienthithongtinNV(int row, String mapb) {
        ArrayList<NhanVien_DTO> list = pbb.getlistNhanVien(mapb);
        if (row != -1 && row < list.size()) {
            NhanVien_DTO nv = list.get(row);
            jlvaluemaso.setText(nv.getMaNV());
            jlvaluehoten.setText(nv.getHoTen());
            if (nv.getGioiTinh() != null) {
                jlvaluegioitinh.setText(nv.getGioiTinh().getTenHienThi());
            } else {
                jlvaluegioitinh.setText(""); 
            }
            jlvaluesdt.setText(nv.getSdt());
            jlvaluedc.setText(nv.getDiaChi());
            if (nv.getNgayVaoLam() != null) {
                jlvaluengayvaolam.setText(pbb.Dinhdangngay(nv.getNgayVaoLam()));
            } else {
                jlvaluengayvaolam.setText("");
            }
            jlvaluephongban.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
        }
    }

    public void xoatrangthongtin() {
        jlvaluemaso.setText(""); jlvaluehoten.setText(""); jlvaluegioitinh.setText("");
        jlvaluesdt.setText(""); jlvaluedc.setText(""); jlvaluengayvaolam.setText("");
        jlvaluephongban.setText("");
    }

    public void addEvent() {
        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    jlnhanvien.setText("Nhân Viên - " + table.getValueAt(row, 2).toString());
                    LoaddataNhanVien(table.getValueAt(row, 1).toString());
                }
            }
        });
        table_1.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int row = table_1.getSelectedRow();
                int rowpb = table.getSelectedRow();
                if (row != -1 && rowpb != -1) HienthithongtinNV(row, table.getValueAt(rowpb, 1).toString());
            }
        });
        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterTable(jtfSearch.getText()); }
            public void removeUpdate(DocumentEvent e) { filterTable(jtfSearch.getText()); }
            public void changedUpdate(DocumentEvent e) { filterTable(jtfSearch.getText()); }
        });
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
				int i=JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phòng "+ten+" (Mã: "+ma+") không?","Xác nhận xóa",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);

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