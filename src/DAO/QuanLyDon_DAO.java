/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author khanh
 */

import DTO.LoaiDon_DTO;
import DTO.ChiTietDon_DTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.time.LocalDate;
public class QuanLyDon_DAO extends Connection_DAO{
    Connection con=getCon();
    Statement stmt=getStmt();
    
    public List<LoaiDon_DTO> getDanhSachLoaiDon(){
        List<LoaiDon_DTO> list=new ArrayList<>();
        String sql="SELECT * FROM LoaiDon";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                String maLoaiDon=rs.getString("ma_loai_don");
                String tenLoaiDon=rs.getString("ten_loai_don");
                
                LoaiDon_DTO ld=new LoaiDon_DTO(maLoaiDon, tenLoaiDon);
                list.add(ld);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<ChiTietDon_DTO> getDanhSachChiTietDon(){
        List<ChiTietDon_DTO> list = new ArrayList<>();
        String sql="SELECT * FROM ChiTietDon";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                String maDon=rs.getString("ma_don");
                Date sqlNgayTao=rs.getDate("ngay_tao");
                LocalDate ngayTao=(sqlNgayTao!=null) ? sqlNgayTao.toLocalDate():null;
                String noiDung=rs.getString("noi_dung");
                String trangThai=rs.getString("trang_thai");
                String nguoiDuyet=rs.getString("nguoi_duyet");
                String maNv=rs.getString("ma_nv");
                String maLoaiDon=rs.getString("ma_loai_don");
                
                ChiTietDon_DTO ctd=new ChiTietDon_DTO(maDon, noiDung, trangThai, nguoiDuyet, maNv, maLoaiDon, ngayTao);
                list.add(ctd);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    public String getNewestMaLoaiDon(){
        String sql = "SELECT TOP 1 ma_loai_don FROM LoaiDon ORDER BY CAST(SUBSTRING(ma_loai_don, 3, LEN(ma_loai_don)) AS INT) DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            if (rs.next()) {
                String lastID = rs.getString("ma_loai_don"); 
                String numberPart = lastID.substring(2);
                int number = Integer.parseInt(numberPart);
                number++; 
                
                return String.format("LD%02d", number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "LD01";
    }
    
    public String getNewestMaCTDon(){
        String sql = "SELECT TOP 1 ma_don FROM ChiTietDon ORDER BY CAST(SUBSTRING(ma_don, 4, LEN(ma_don)) AS INT) DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            if (rs.next()) {
                String lastID = rs.getString("ma_don"); 
                String numberPart = lastID.substring(2);
                int number = Integer.parseInt(numberPart);
                number++; 
                
                return String.format("DON%02d", number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "DON01";
    }
    
    public ChiTietDon_DTO getCtdonById(){
        String sql="SELECT * FROM ChiTietDon WHERE ma_don = ? ";
        ChiTietDon_DTO ctd=null;
        try {
            PreparedStatement ps=con.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ctd;
    }
}
