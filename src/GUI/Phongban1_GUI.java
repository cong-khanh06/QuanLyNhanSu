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
    private JLabel jlnhanvien, jlthongtin,jlAvatar;
    private JTextField jtfSearch;
    private JLabel jlvaluemaso, jlvaluehoten, jlvaluegioitinh, jlvaluesdt, jlvaluedc, jlvaluephongban, jlvaluengayvaolam;
    private JButton btthem, btxoa, btsua, btsearch, btrefresh,btthongke;
	
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
        // 1. ĐỔI MÀU NỀN TỔNG THỂ SANG XÁM XANH NHẠT (Tạo độ sâu)
        setBackground(new Color(226, 232, 240)); 
        this.setLayout(new BorderLayout(15, 15)); 
        this.setBorder(new EmptyBorder(15, 15, 15, 15)); 
        
        Font modernFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 14);

        // ================= PHẦN TRÊN: PHÒNG BAN =================
        JPanel panelTop = new JPanel(new BorderLayout(0, 10));
        panelTop.setBackground(Color.WHITE); // Khối Card màu trắng
        
        // 2. TẠO ĐIỂM NHẤN: Đường viền xanh dương đậm ở trên cùng của Card
        panelTop.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(59, 130, 246)), // Accent Top
            BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 220)), 
                new EmptyBorder(10, 15, 10, 15)
            )
        ));

        JPanel panelTopToolbar = new JPanel(new BorderLayout());
        panelTopToolbar.setBackground(Color.WHITE);

        JLabel lbtt = new JLabel("Danh Sách Phòng Ban");
        lbtt.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbtt.setForeground(new Color(30, 41, 59));
        panelTopToolbar.add(lbtt, BorderLayout.WEST);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlButtons.setBackground(Color.WHITE);

        jtfSearch = new JTextField(15);
        jtfSearch.setPreferredSize(new Dimension(200, 35));
        jtfSearch.setFont(modernFont);
        
        btsearch = createStyledButton("Tìm kiếm", "/img/searchpb.png", "primary");
        btrefresh = createStyledButton("Làm mới", "/img/Refresh.png", "");
        btthem = createStyledButton("Thêm", "/img/addpb.png", "primary");
        btsua = createStyledButton("Sửa", "/img/fixpb.png", "");
        btxoa = createStyledButton("Xóa", "/img/deletepb.png", "danger");
        btthongke = createStyledButton("Thống kê", "/img/thongke.png", "success");

        pnlButtons.add(jtfSearch); pnlButtons.add(btsearch); pnlButtons.add(btrefresh);
        pnlButtons.add(btthem); pnlButtons.add(btsua); pnlButtons.add(btxoa); pnlButtons.add(btthongke);

        panelTopToolbar.add(pnlButtons, BorderLayout.EAST);
        panelTop.add(panelTopToolbar, BorderLayout.NORTH);

        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"STT", "Mã phòng ban", "Tên phòng ban", "Trưởng phòng", "Số lượng", "Lương trung bình"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        styleTable(table); 
        
        JScrollPane scrollPaneTop = new JScrollPane(table);
        scrollPaneTop.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneTop.getViewport().setBackground(Color.WHITE);
        panelTop.add(scrollPaneTop, BorderLayout.CENTER);
        
        // ================= PHẦN DƯỚI: CHI TIẾT NHÂN VIÊN =================
        // KHU VỰC TRÁI (BẢNG NHÂN VIÊN)
        JPanel panelLeft = new JPanel(new BorderLayout(0, 10));
        panelLeft.setBackground(Color.WHITE);
        
        // Tạo điểm nhấn viền trên màu Xanh Ngọc cho khối Nhân viên
        panelLeft.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(16, 185, 129)), // Accent Top Xanh ngọc
            BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 220)), 
                new EmptyBorder(10, 15, 10, 15)
            )
        ));
        
        jlnhanvien = new JLabel("Nhân Viên", SwingConstants.LEFT);
        jlnhanvien.setFont(boldFont);
        jlnhanvien.setForeground(new Color(16, 185, 129)); // Chữ đồng màu viền
        panelLeft.add(jlnhanvien, BorderLayout.NORTH);

        table_1 = new JTable();
        table_1.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"STT", "Mã nhân viên", "Họ và tên"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        styleTable(table_1);
        
        JScrollPane scrollPanelLeft = new JScrollPane(table_1);
        scrollPanelLeft.setBorder(BorderFactory.createEmptyBorder());
        scrollPanelLeft.getViewport().setBackground(Color.WHITE);
        panelLeft.add(scrollPanelLeft, BorderLayout.CENTER);

        // KHU VỰC PHẢI (THÔNG TIN CHI TIẾT)
        JPanel panelRight = new JPanel(new BorderLayout());
        panelRight.setBackground(Color.WHITE);
        
        // Tạo điểm nhấn viền trên màu Tím cho khối Chi tiết
        panelRight.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(139, 92, 246)), // Accent Top Tím
            BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 220)), 
                new EmptyBorder(10, 20, 10, 20)
            )
        ));

        JLabel lblDetailTitle = new JLabel("Thông Tin Chi Tiết");
        lblDetailTitle.setFont(boldFont);
        lblDetailTitle.setForeground(new Color(139, 92, 246)); // Chữ đồng màu viền
        panelRight.add(lblDetailTitle, BorderLayout.NORTH);

        JPanel pnlContent = new JPanel(new BorderLayout(20, 0)); 
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(20, 0, 0, 0));

        JPanel pnlLabels = new JPanel(new GridLayout(7, 1, 0, 15)); 
        pnlLabels.setBackground(Color.WHITE);
        
        String[] lbText = {"Mã số:", "Họ tên:", "Giới tính:", "Số điện thoại:", "Địa chỉ:", "Phòng ban:", "Ngày vào làm:"};
        for (String text : lbText) {
            JLabel lbl = new JLabel(text);
            lbl.setFont(modernFont);
            lbl.setForeground(new Color(100, 116, 139)); // Label xám dịu
            pnlLabels.add(lbl);
        }

        JPanel pnlValues = new JPanel(new GridLayout(7, 1, 0, 15));
        pnlValues.setBackground(Color.WHITE);

        jlvaluemaso = createValueLabel(); jlvaluehoten = createValueLabel();
        jlvaluegioitinh = createValueLabel(); jlvaluesdt = createValueLabel();
        jlvaluedc = createValueLabel(); jlvaluephongban = createValueLabel();
        jlvaluengayvaolam = createValueLabel();

        pnlValues.add(jlvaluemaso); pnlValues.add(jlvaluehoten);
        pnlValues.add(jlvaluegioitinh); pnlValues.add(jlvaluesdt);
        pnlValues.add(jlvaluedc); pnlValues.add(jlvaluephongban);
        pnlValues.add(jlvaluengayvaolam);
        
        jlAvatar=new JLabel("Chưa có ảnh",JLabel.CENTER);
        jlAvatar.setPreferredSize(new Dimension(140,180));
        jlAvatar.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        pnlContent.add(pnlLabels, BorderLayout.WEST);   
        pnlContent.add(pnlValues, BorderLayout.CENTER); 
        pnlContent.add(jlAvatar,BorderLayout.EAST);
        
        JPanel pnlNorthWrapper = new JPanel(new BorderLayout());
        pnlNorthWrapper.setBackground(Color.WHITE);
        pnlNorthWrapper.add(pnlContent, BorderLayout.NORTH);
        panelRight.add(pnlNorthWrapper, BorderLayout.CENTER);

        JSplitPane splitPaneBottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLeft, panelRight);
        splitPaneBottom.setResizeWeight(0.45);
        splitPaneBottom.setDividerSize(10);
        splitPaneBottom.setBorder(null);
        splitPaneBottom.setOpaque(false); // Xóa nền thanh kéo

        JSplitPane splitPaneMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelTop, splitPaneBottom);
        splitPaneMain.setResizeWeight(0.6); 
        splitPaneMain.setDividerSize(12);
        splitPaneMain.setBorder(null);
        splitPaneMain.setOpaque(false);

        this.add(splitPaneMain, BorderLayout.CENTER);

        btthem.addActionListener(this); btsua.addActionListener(this);
        btxoa.addActionListener(this); btsearch.addActionListener(this);
        btrefresh.addActionListener(this); btthongke.addActionListener(this);
    }
    
    // Hàm tối ưu giao diện bảng
    private void styleTable(JTable tb) {
        tb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tb.setRowHeight(30);
        tb.setShowVerticalLines(false);
        tb.setShowHorizontalLines(true);
        tb.setGridColor(new Color(230, 230, 230));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Nền Header bảng xám rất nhẹ để phân cách
        tb.getTableHeader().setBackground(new Color(241, 245, 249)); 
    }

    // Hàm tạo nút dùng sức mạnh của FlatLaf
    private JButton createStyledButton(String text, String iconPath, String styleClass) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (!styleClass.isEmpty()) {
            btn.putClientProperty("FlatLaf.styleClass", styleClass);
        }
        
        java.net.URL url = getClass().getResource(iconPath);
        if (url != null) {
            ImageIcon icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));
            btn.setIcon(icon);
        }
        return btn;
    }

    private JLabel createValueLabel() {
        JLabel label = new JLabel("");
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
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
            String avtPath=nv.getAvatar();
            if(avtPath!=null && !avtPath.trim().isEmpty())
            {
            	try
            	{
            		ImageIcon icon=new ImageIcon(avtPath);
            		Image image=icon.getImage().getScaledInstance(jlAvatar.getWidth(),jlAvatar.getHeight(), Image.SCALE_SMOOTH);
            		jlAvatar.setIcon(new ImageIcon(image));
            		jlAvatar.setText("");
            	}
            	catch(Exception e)
            	{
            		jlAvatar.setIcon(null);
            		jlAvatar.setText("Error");
            	}
            	
            }
            else
            {
            	jlAvatar.setIcon(null);
            	jlAvatar.setText("Đang cập nhật");
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
        jlAvatar.setIcon(null);
        jlAvatar.setText("Đang cập nhật");
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
		else if(src.equals("Thống kê"))
		{
			int row = table.getSelectedRow();
		    if (row != -1) {
		        String mapb = table.getValueAt(row, 1).toString();
		        String tenpb = table.getValueAt(row, 2).toString();
		        
		        PhongbanThongKe_GUI gdThongKe = new PhongbanThongKe_GUI(mapb, tenpb);
		        gdThongKe.setVisible(true);
		    } else {
		        JOptionPane.showMessageDialog(this, "Vui lòng chọn một phòng ban trên bảng!");
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
    public void setphanquyenManager(boolean kq)
    {
    	
    	btxoa.setEnabled(kq);
    	
    }
    
}