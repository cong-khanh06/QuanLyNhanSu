package DAO;

import DTO.BangLuong_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class BangLuong_DAO extends Connection_DAO {
    Connection con = getCon();
    Statement stmt = getStmt();
    
    
    private String dichSangDB(String ttUI) {
        if (ttUI == null) return "ChuaThanhToan";
        if (ttUI.trim().equalsIgnoreCase("Đã thanh toán")) return "DaThanhToan";
        return "ChuaThanhToan";
    }

    private String dichSangUI(String ttDB) {
        if (ttDB == null) return "Chưa thanh toán";
        if (ttDB.trim().equalsIgnoreCase("DaThanhToan")) return "Đã thanh toán";
        return "Chưa thanh toán";
    }
    
    public boolean insertBangLuong(BangLuong_DTO bl) {
        String sql = "INSERT INTO BangLuong (ma_bl, luong_co_ban, tong_phu_cap, tong_khau_tru, thuc_lanh, trang_thai, ma_nv, thang, nam) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bl.getMaBL().trim());
            ps.setBigDecimal(2, bl.getLuongCoBan());
            ps.setBigDecimal(3, bl.getTongPhuCap());
            ps.setBigDecimal(4, bl.getTongKhauTru());
            ps.setBigDecimal(5, bl.getThucLanh());
            ps.setString(6, dichSangDB(bl.getTrangThai()));
            ps.setString(7, bl.getMaNV().trim());
            ps.setInt(8, bl.getThang());
            ps.setInt(9, bl.getNam());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi Thêm Bảng Lương: " + e.getMessage());
        }
        return false;
    }
    
    public boolean updateBangLuong(BangLuong_DTO bl) {
        String sql = "UPDATE BangLuong SET luong_co_ban=?, tong_phu_cap=?, tong_khau_tru=?, thuc_lanh=?, trang_thai=?, ma_nv=?, thang=?, nam=? WHERE ma_bl=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBigDecimal(1, bl.getLuongCoBan());
            ps.setBigDecimal(2, bl.getTongPhuCap());
            ps.setBigDecimal(3, bl.getTongKhauTru());
            ps.setBigDecimal(4, bl.getThucLanh());
            ps.setString(5, dichSangDB(bl.getTrangThai()));
            ps.setString(6, bl.getMaNV().trim());
            ps.setInt(7, bl.getThang());
            ps.setInt(8, bl.getNam());
            ps.setString(9, bl.getMaBL().trim());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi Sửa Bảng Lương: " + e.getMessage());
        }
        return false;
    }
    
    public boolean deleteBangLuong(String maBL) {
        String sql = "DELETE FROM BangLuong WHERE ma_bl=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maBL.trim());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<BangLuong_DTO> layDanhSachBangLuong() {
        List<BangLuong_DTO> list = new ArrayList<>();
        
        String sql = "SELECT bl.*, nv.ho_ten FROM BangLuong bl LEFT JOIN NhanVien nv ON bl.ma_nv = nv.ma_nv ORDER BY bl.nam DESC, bl.thang DESC";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String maBL = rs.getString("ma_bl");
                BigDecimal luongCoBan = rs.getBigDecimal("luong_co_ban");
                BigDecimal tongPhuCap = rs.getBigDecimal("tong_phu_cap");
                BigDecimal tongKhauTru = rs.getBigDecimal("tong_khau_tru");
                BigDecimal thucLanh = rs.getBigDecimal("thuc_lanh");
                String trangThai = dichSangUI(rs.getString("trang_thai"));
                String maNV = rs.getString("ma_nv");
                int thang = rs.getInt("thang");
                int nam = rs.getInt("nam");
                String tenNV = rs.getString("ho_ten") != null ? rs.getString("ho_ten") : "N/A";
                
                list.add(new BangLuong_DTO(maBL, luongCoBan, tongPhuCap, tongKhauTru, thucLanh, trangThai, maNV, thang, nam, tenNV));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public String getNewestMaBL() {
        String sql = "SELECT TOP 1 ma_bl FROM BangLuong ORDER BY CAST(SUBSTRING(ma_bl, 3, LEN(ma_bl)) AS INT) DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String lastID = rs.getString("ma_bl"); 
                String numberPart = lastID.substring(2);
                int number = Integer.parseInt(numberPart) + 1;
                return String.format("BL%02d", number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "BL01";
    }
}