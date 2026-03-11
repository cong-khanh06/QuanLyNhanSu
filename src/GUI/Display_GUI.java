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
import javax.swing.JOptionPane;

public class Display_GUI extends JFrame{
    JPanel pnLeft,pnRight,pnRAN,pnCard;
    JLabel name,role,nameValue,roleValue;

    ButtonSidebar btnHome,btnNV,btnHD,btnPB,btnL,btnCC,btnTK,btnDA,btnLogout,btnBC,btnCV,btnCTBH,btnUL,btnTB;

    CardLayout cardlayout=new CardLayout();
    Phongban1_GUI phongban;
    NhanVien_GUI nhanvien;
    HopDong_GUI hopdong;
    TaiKhoan_GUI taikhoan;
    DuAn_GUI duan;
    TrangChu_GUI trangchu;
    UngLuong_GUI ungluong;
    ChamCong_GUI chamcong;
    BangLuong_GUI bangluong;
    BangCap_GUI bangcap;
    ChucVu_GUI chucvu;
    ChiTietBaoHiem_GUI chitietbaohiem;
    ThongBao_GUI thongbao;

    public Display_GUI(){
        setTitle("Quản Lý Nhân Sự");
        setSize(1400,800);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        phongban= new Phongban1_GUI();
        nhanvien=new NhanVien_GUI();
        hopdong=new HopDong_GUI();
        taikhoan=new TaiKhoan_GUI();
        duan=new DuAn_GUI();
        trangchu=new TrangChu_GUI();
        bangluong=new BangLuong_GUI();
        chamcong=new ChamCong_GUI();
        ungluong=new UngLuong_GUI();
        bangcap=new BangCap_GUI();
        chucvu=new ChucVu_GUI();
        chitietbaohiem=new ChiTietBaoHiem_GUI();
        thongbao=new ThongBao_GUI();

        
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
        btnPB=new ButtonSidebar("Phòng ban", loadIcon("/GUI/icon/phongban.png"));
        pnCard.add(btnPB);
        btnL=new ButtonSidebar("Lương", loadIcon("/GUI/icon/salary.png"));        
        pnCard.add(btnL);
        btnCC=new ButtonSidebar("Chấm công", loadIcon("/GUI/icon/timesheets.png"));        
        pnCard.add(btnCC);
        btnTK=new ButtonSidebar("Tài khoản", loadIcon("/GUI/icon/account.png"));        
        pnCard.add(btnTK);
        btnDA=new ButtonSidebar("Dự án", loadIcon("/GUI/icon/duan.png"));
        pnCard.add(btnDA);
        btnBC=new ButtonSidebar("Bằng cấp", loadIcon("/GUI/icon/bangcap.png"));
        pnCard.add(btnBC);
        btnCV=new ButtonSidebar("Chức vụ", loadIcon("/GUI/icon/chucvu.png"));
        pnCard.add(btnCV);
        btnCTBH=new ButtonSidebar("Bảo hiểm", loadIcon("/GUI/icon/baohiem.png"));
        pnCard.add(btnCTBH);
        btnUL=new ButtonSidebar("Ứng lương", loadIcon("/GUI/icon/ungluong.png"));
        pnCard.add(btnUL);
        btnTB=new ButtonSidebar("Thông báo", loadIcon("/GUI/icon/thongbao.png"));
        pnCard.add(btnTB);
        btnLogout = new ButtonSidebar("Đăng xuất", loadIcon("/GUI/icon/logout.png")); 
        pnCard.add(btnLogout);
        
        
        btnHome.setHorizontalAlignment(SwingConstants.LEFT);
        btnNV.setHorizontalAlignment(SwingConstants.LEFT);
        btnPB.setHorizontalAlignment(SwingConstants.LEFT);
        btnHD.setHorizontalAlignment(SwingConstants.LEFT);
        btnL.setHorizontalAlignment(SwingConstants.LEFT);
        btnCC.setHorizontalAlignment(SwingConstants.LEFT);
        btnTK.setHorizontalAlignment(SwingConstants.LEFT);
        btnDA.setHorizontalAlignment(SwingConstants.LEFT);
        btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
        btnBC.setHorizontalAlignment(SwingConstants.LEFT);
        btnCV.setHorizontalAlignment(SwingConstants.LEFT);
        btnCTBH.setHorizontalAlignment(SwingConstants.LEFT);
        btnUL.setHorizontalAlignment(SwingConstants.LEFT);
        btnTB.setHorizontalAlignment(SwingConstants.LEFT);

        pnLeft.add(pnCard);

        pnRight=new JPanel(cardlayout);
        
        pnRight.add(trangchu,"panelTC");
        pnRight.add(nhanvien,"panelNV");
        pnRight.add(phongban,"panelPB");
        pnRight.add(hopdong,"panelHD");
        pnRight.add(duan,"panelDA");
        pnRight.add(taikhoan, "panelTK");
        pnRight.add(bangluong, "panelBL");
        pnRight.add(chamcong,"panelCC");

        pnRight.add(bangcap, "panelBC");
        pnRight.add(chucvu, "panelCV");
        pnRight.add(chitietbaohiem,"panelCTHD");
        pnRight.add(ungluong, "panelUL");
        pnRight.add(thongbao,"panelTB");

        cardlayout.show(pnRight,"panelTC");
        
        btnHome.addActionListener(e->{
            cardlayout.show(pnRight,"panelTC");
        });
        
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
        
        btnDA.addActionListener(e->{
            cardlayout.show(pnRight, "panelDA");
        });
        
        btnTK.addActionListener(e->{
            cardlayout.show(pnRight, "panelTK");
        });
        btnCC.addActionListener(e -> {
        	cardlayout.show(pnRight, "panelCC");
        });
        
        btnBC.addActionListener(e->{
            cardlayout.show(pnRight,"panelBC");
        });
        
        btnCV.addActionListener(e->{
            cardlayout.show(pnRight,"panelCV");
        });
        
        btnCTBH.addActionListener(e->{
            cardlayout.show(pnRight, "panelCTHD");
        });
        
        btnUL.addActionListener(e->{
            cardlayout.show(pnRight, "panelUL");
        });
        
        btnL.addActionListener(e->{
            cardlayout.show(pnRight, "panelBL");
        });
        
        btnTB.addActionListener(e->{
            cardlayout.show(pnRight,"panelTB");
        });
        
        add(pnLeft,BorderLayout.WEST);
        add(pnRight,BorderLayout.CENTER);
        
        DangXuat();
        
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
    		btnDA.setVisible(false);
    		btnTB.setVisible(false);
    		pnCard.revalidate();
    		nhanvien.setphanquyenuser(false,manv);
    		nhanvien.setphanquyennut(false,quyen);
    		hopdong.setphanquyenUser(false, manv);
    		chamcong.setphanquyenUser(false,manv);
    		chitietbaohiem.setphanquyenUser(false, manv);
    		bangluong.setphanquyenUser(false, manv);
    		ungluong.setphanquyenUser(false, manv);
    		chucvu.setPhanQuyen(quyen, manv);
    		
    		pnCard.revalidate();
    		pnCard.repaint();
    	}
    	else if(quyen.equals("Manager"))
    	{
    		nhanvien.setphanquyennut(false,quyen);
    		hopdong.setphanquyenManager(false);
    		phongban.setphanquyenManager(false);
    		duan.setphanquyenManager(false);
    		chitietbaohiem.setphanquyenManager();
    		chucvu.setPhanQuyen(quyen, manv);
    		bangluong.setphanquyenManager(false);
    		ungluong.setphanquyenManager(false);
    		btnTK.setVisible(false);	
    		pnCard.revalidate();
    		pnCard.repaint();
    	}
    	
    }
    
    public void DangXuat(){
        btnLogout.addActionListener(e->{
            int confirm=JOptionPane.showConfirmDialog(
                    this, "Bạn có chắc chắn muốn đăng xuất không?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            
            if(confirm ==JOptionPane.YES_OPTION){
                this.dispose();
                new Login_GUI().setVisible(true);
            }
        });
    }
    
    public static void main(String []args)
    {
    	Display_GUI ds=new Display_GUI();
    }
    
}
