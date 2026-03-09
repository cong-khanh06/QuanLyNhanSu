/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author khanh
 */
import java.sql.Connection;
import DTO.DuAn_DTO;
import DTO.NhanVien_DTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.SQLException;
public class DuAn_DAO extends Connection_DAO{
    Connection con=getCon();
    Statement stmt=getStmt();
    
    public boolean insertDuAn(DuAn_DTO da){
        String sql="INSERT INTO DuAn (ma_da,ten_du_an,ngay_bat_dau,ngay_ket_thuc,trang_thai)"
                + "VALUES (?,?,?,?,?)";
        try(PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1, da.getMaDa());
            ps.setString(2, da.getTenDuAn());
            ps.setDate(3, da.getNgayBatDau() != null ? java.sql.Date.valueOf(da.getNgayBatDau()) : null);
            ps.setDate(4, da.getNgayKetThuc() != null ? java.sql.Date.valueOf(da.getNgayKetThuc()) : null);
            ps.setString(5, da.getTrangThai());
            
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteDuAn(String maDA){
        String sql="DELETE FROM DuAn WHERE ma_da=?";
        try (PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1, maDA);
            
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateDuAn(DuAn_DTO da){
        String sql="UPDATE DuAn SET "
                +"ten_du_an = ?, "
                +"ngay_bat_dau = ?, "
                +"ngay_ket_thuc = ?, "
                +"trang_thai = ? "
                +"WHERE ma_da = ? ";
        
        try (PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1, da.getTenDuAn());
            ps.setDate(2, java.sql.Date.valueOf(da.getNgayBatDau()));
            ps.setDate(3, java.sql.Date.valueOf(da.getNgayKetThuc()));
            ps.setString(4, da.getTrangThai());
            ps.setString(5, da.getMaDa());
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<DuAn_DTO> layDanhSachDuAn(){
        List<DuAn_DTO> list=new ArrayList<>();
        try {
            String sql="SELECT * FROM DuAn";
            ResultSet rs=stmt.executeQuery(sql);
            
            while(rs.next()){
                String maDA=rs.getString("ma_da");
                String tenDA=rs.getString("ten_du_an");
                Date ngaybatdau=rs.getDate("ngay_bat_dau");
                LocalDate ngayBD=(ngaybatdau!=null) ? ngaybatdau.toLocalDate():null;
                Date ngayketthuc=rs.getDate("ngay_ket_thuc");
                LocalDate ngayKT=(ngayketthuc!=null) ? ngayketthuc.toLocalDate():null;
                String trangThai=rs.getString("trang_thai");
                String nguoiQuanLy=rs.getString("nguoi_quan_ly");
                
                DuAn_DTO rowdata=new DuAn_DTO(maDA, tenDA, trangThai, nguoiQuanLy, ngayBD, ngayKT);
                list.add(rowdata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public String getNewestMaDA(){
        String sql="SELECT TOP 1 ma_da FROM DuAn ORDER BY CAST(SUBSTRING(ma_da, 3, LEN(ma_da)) AS INT) DESC ";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            if (rs.next()) {
                String lastID = rs.getString("ma_da"); 
                String numberPart = lastID.substring(2);
                int number = Integer.parseInt(numberPart);
                number++; 
                
                
                return String.format("DA%02d", number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "DA01";
    }
    
    public List<DuAn_DTO> timKiemDuAn(String tuKhoa) {
        List<DuAn_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM DuAn WHERE ma_da LIKE ? OR ten_du_an LIKE ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String keyword = "%" + tuKhoa + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maDA = rs.getString("ma_da");
                    String tenDA = rs.getString("ten_du_an");
                    String nguoiQuanLy=rs.getString("nguoi_quan_ly");
                    
                    Date ngaybatdau = rs.getDate("ngay_bat_dau");
                    LocalDate ngayBD = (ngaybatdau != null) ? ngaybatdau.toLocalDate() : null;
                    
                    Date ngayketthuc = rs.getDate("ngay_ket_thuc");
                    LocalDate ngayKT = (ngayketthuc != null) ? ngayketthuc.toLocalDate() : null;
                    
                    String trangThai = rs.getString("trang_thai");
                    
                    DuAn_DTO rowdata = new DuAn_DTO(maDA, tenDA, trangThai, nguoiQuanLy, ngayBD, ngayKT);
                    list.add(rowdata);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int soLuongDuAnDangThucHien(){
        String sql="SELECT COUNT(*) FROM DuAn WHERE trang_thai= N'Đang thực hiện' ";
        int count=0;
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rsDa=ps.executeQuery();
            if(rsDa.next()){
                count=rsDa.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
