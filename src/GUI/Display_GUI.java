/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author khanh
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
    ButtonSidebar btnHome,btnNV,btnHD,btnPB;
    CardLayout cardlayout=new CardLayout();
    public Display_GUI(){
        setTitle("Quản Lý Nhân Sự");
        setSize(1400,800);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        
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
        btnNV=new ButtonSidebar("Nhân Viên", loadIcon("/GUI/icon/nhanvien.png"));
        pnCard.add(btnNV);
        btnHD=new ButtonSidebar("Hợp Đồng", loadIcon("/GUI/icon/hopdong.png"));
        pnCard.add(btnHD);
        btnPB=new ButtonSidebar("Phòng ban", loadIcon("/GUI/icon/phongban.png"));
        
        pnCard.add(btnPB);
        
        pnLeft.add(pnCard);
        
        pnRight=new JPanel(cardlayout);
        pnRight.setBackground(Color.white);
        
        pnRight.add(new NhanVien_GUI(),"panelNV");
        pnRight.add(new Phongban1_GUI(),"panelPB");
        
        btnNV.addActionListener(e->{
            cardlayout.show(pnRight,"panelNV");
        });
        
        btnPB.addActionListener(e->{
            cardlayout.show(pnRight,"panelPB");
            pnRight.revalidate();
            pnRight.repaint();
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
}
