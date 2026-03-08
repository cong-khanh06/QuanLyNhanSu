package GUI;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

public class Display_GUI extends JFrame{
    JPanel pnLeft,pnRight,pnRAN,pnCard;
    JLabel name,role,nameValue,roleValue;
<<<<<<< Updated upstream
    ButtonSidebar btnHome,btnNV,btnHD,btnPB,btnDA;
=======
    ButtonSidebar btnHome,btnNV,btnHD,btnPB,btnL,btnCC,btnTK;
>>>>>>> Stashed changes
    CardLayout cardlayout=new CardLayout();
    Phongban1_GUI phongban;
    NhanVien_GUI nhanvien;
    public Display_GUI(){
        setTitle("Quản Lý Nhân Sự");
        setSize(1400,800);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        phongban= new Phongban1_GUI();
        nhanvien=new NhanVien_GUI();
        
        
        pnLeft=new JPanel();
        pnLeft.setPreferredSize(new Dimension(250,800));
        pnLeft.setBackground(new Color(150,214,255));
        
        name=new JLabel("Tên Đăng nhập: ");
        role=new JLabel("Chức vụ: ");
        
        nameValue = new JLabel("");
        roleValue = new JLabel("");
        
        pnRAN=new JPanel();
        pnRAN.setPreferredSize(new Dimension(250,100));
        pnRAN.setLayout(new GridLayout(2,2,5,5));
        pnRAN.setBackground(new Color(200, 230, 255)); 
        
        pnRAN.add(name);
        pnRAN.add(nameValue);
        pnRAN.add(role);
        pnRAN.add(roleValue);
        
        pnLeft.add(pnRAN);
        
        pnCard=new JPanel();
        pnCard.setPreferredSize(new Dimension(250,700));
        pnCard.setLayout(new BoxLayout(pnCard, BoxLayout.Y_AXIS));
        pnCard.setBackground(new Color(120, 190, 240));
        btnHome = new ButtonSidebar("Trang chủ", loadIcon("/GUI/icon/home.png"));
        pnCard.add(btnHome);
        btnNV=new ButtonSidebar("Nhân viên", loadIcon("/GUI/icon/nhanvien.png"));
        pnCard.add(btnNV);
        btnHD=new ButtonSidebar("Hợp đồng", loadIcon("/GUI/icon/hopdong.png"));
        pnCard.add(btnHD);
<<<<<<< Updated upstream
        btnPB=new ButtonSidebar("Phòng ban", loadIcon("/GUI/icon/phongban.png"));
        pnCard.add(btnPB);
        btnDA=new ButtonSidebar("Dự án", loadIcon("/GUI/icon/duan.png"));
        pnCard.add(btnDA);
=======
        btnPB=new ButtonSidebar("Phòng ban", loadIcon("/GUI/icon/phongban.png"));        
        pnCard.add(btnPB);
        btnL=new ButtonSidebar("Lương", loadIcon("/GUI/icon/salary.png"));        
        pnCard.add(btnL);
        btnCC=new ButtonSidebar("Chấm công", loadIcon("/GUI/icon/timesheets.png"));        
        pnCard.add(btnCC);
        btnTK=new ButtonSidebar("Tài khoản", loadIcon("/GUI/icon/account.png"));        
        pnCard.add(btnTK);
        

        btnHome.setHorizontalAlignment(SwingConstants.LEFT);
        btnNV.setHorizontalAlignment(SwingConstants.LEFT);
        btnPB.setHorizontalAlignment(SwingConstants.LEFT);
        btnHD.setHorizontalAlignment(SwingConstants.LEFT);
        btnL.setHorizontalAlignment(SwingConstants.LEFT);
        btnCC.setHorizontalAlignment(SwingConstants.LEFT);
        btnTK.setHorizontalAlignment(SwingConstants.LEFT);
>>>>>>> Stashed changes
        
        pnLeft.add(pnCard);

        pnRight=new JPanel(cardlayout);
        pnRight.setBackground(Color.white);
        
<<<<<<< Updated upstream
        pnRight.add(new NhanVien_GUI(),"panelNV");
        pnRight.add(new Phongban1_GUI(),"panelPB");
        pnRight.add(new HopDong_GUI(), "panelHD");
        pnRight.add(new DuAn_GUI(),"panelDA");
=======
        pnRight.add(nhanvien,"panelNV");
        pnRight.add(phongban,"panelPB");
        pnRight.add(new HopDong_GUI(),"panelHD");
>>>>>>> Stashed changes
        
        btnNV.addActionListener(e->{
            cardlayout.show(pnRight,"panelNV");
        });
        
        btnPB.addActionListener(e->{
            cardlayout.show(pnRight,"panelPB");
            pnRight.revalidate();
            pnRight.repaint();
        });
        btnHD.addActionListener(e->{
            cardlayout.show(pnRight,"panelHD");
        });
        
        
        btnHD.addActionListener(e->{
            cardlayout.show(pnRight,"panelHD");
        });
        
        btnDA.addActionListener(e->{
            cardlayout.show(pnRight, "panelDA");
        });
        
        add(pnLeft,BorderLayout.WEST);
        add(pnRight,BorderLayout.CENTER);
        
        
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    private ImageIcon loadIcon(String path) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) return new ImageIcon(imgURL);
        } catch (Exception e) {
            System.err.println("Không tìm thấy icon: " + path);
        }
        return null; 
    }
    public JLabel getnameValue()
    {
    	return nameValue;
    }
    public JLabel getroleValue()
    {
    	return roleValue;
    }
    public Phongban1_GUI getPhongban()
    {
    	return phongban;
    }
    public void PhanQuyen(String quyen,String manv)
    {
    	if(quyen.equals("User"))
    	{
    		btnPB.setVisible(false);
    		btnTK.setVisible(false);
    		pnCard.revalidate();
    		nhanvien.setphanquyenuser(false);
    		nhanvien.Loaddatatheoma(manv);
    		pnCard.revalidate();
    		pnCard.repaint();
    	}
    	else if(quyen.equals("Manager"))
    	{
    		nhanvien.setphanquyenManager(false);
    		btnTK.setVisible(false);
    		pnCard.revalidate();
    		pnCard.repaint();
    		
    	}
    	
    }
    public static void main(String []args)
    {
    	Display_GUI ds=new Display_GUI();
    }
}
