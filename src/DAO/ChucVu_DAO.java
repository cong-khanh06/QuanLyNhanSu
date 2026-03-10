package DAO;

import DTO.ChucVu_DTO;
import DTO.PhuCap_DTO; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChucVu_DAO extends Connection_DAO {
    Connection con = getCon();
    Statement stmt = getStmt();

    public List<ChucVu_DTO> layDanhSachChucVu() {
        List<ChucVu_DTO> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ChucVu";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new ChucVu_DTO(
                    rs.getString("ma_cv"),
                    rs.getString("ten_cv"),
                    rs.getString("mo_ta")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ChucVu_DTO> timKiemChucVu(String tuKhoa) {
        List<ChucVu_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ChucVu WHERE ma_cv LIKE ? OR ten_cv LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String keyword = "%" + tuKhoa + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ChucVu_DTO(
                        rs.getString("ma_cv"),
                        rs.getString("ten_cv"),
                        rs.getString("mo_ta")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertChucVu(ChucVu_DTO cv) {
        String sql = "INSERT INTO ChucVu (ma_cv, ten_cv, mo_ta) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cv.getMaCV());
            ps.setString(2, cv.getTenCV());
            ps.setString(3, cv.getMoTa());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean updateChucVu(ChucVu_DTO cv) {
        String sql = "UPDATE ChucVu SET ten_cv = ?, mo_ta = ? WHERE ma_cv = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cv.getTenCV());
            ps.setString(2, cv.getMoTa());
            ps.setString(3, cv.getMaCV());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean deleteChucVu(String maCV) {
        try {
            con.setAutoCommit(false); 
            
            
            String sqlChiTiet = "DELETE FROM ChiTietChucVu WHERE ma_cv = ?";
            try (PreparedStatement ps1 = con.prepareStatement(sqlChiTiet)) {
                ps1.setString(1, maCV);
                ps1.executeUpdate(); 
            }

            
            String sqlChucVu = "DELETE FROM ChucVu WHERE ma_cv = ?";
            try (PreparedStatement ps2 = con.prepareStatement(sqlChucVu)) {
                ps2.setString(1, maCV);
                int result = ps2.executeUpdate();
                
                con.commit(); 
                return result > 0;
            }
        } catch (Exception e) {
            try {
                con.rollback(); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    
    public String getNewestMaCV() {
        String sql = "SELECT TOP 1 ma_cv FROM ChucVu ORDER BY CAST(SUBSTRING(ma_cv, 3, LEN(ma_cv)) AS INT) DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String lastID = rs.getString("ma_cv"); 
                String numberPart = lastID.substring(2);
                int number = Integer.parseInt(numberPart);
                number++; 
                return String.format("CV%02d", number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "CV01";
    }

    
    public List<PhuCap_DTO> layPhuCapTheoChucVu(String maCV) {
        List<PhuCap_DTO> list = new ArrayList<>();
        String sql = "SELECT p.ma_pc, p.ten_pc, p.so_tien " +
                     "FROM PhuCap p " +
                     "INNER JOIN ChiTietChucVu ct ON p.ma_pc = ct.ma_pc " +
                     "WHERE ct.ma_cv = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maCV);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new PhuCap_DTO(
                        rs.getString("ma_pc"),
                        rs.getString("ten_pc"),
                        rs.getDouble("so_tien")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<PhuCap_DTO> layDanhSachTatCaPhuCap() {
        List<PhuCap_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM PhuCap";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new PhuCap_DTO(
                    rs.getString("ma_pc"),
                    rs.getString("ten_pc"),
                    rs.getDouble("so_tien")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    
    public boolean themPhuCapChoChucVu(String maCV, String maPC) {
        String sql = "INSERT INTO ChiTietChucVu (ma_cv, ma_pc) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maCV);
            ps.setString(2, maPC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            
            return false; 
        }
    }

    
    public boolean xoaPhuCapKhoiChucVu(String maCV, String maPC) {
        String sql = "DELETE FROM ChiTietChucVu WHERE ma_cv = ? AND ma_pc = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maCV);
            ps.setString(2, maPC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }


 
 public List<ChucVu_DTO> layChucVuTheoNV(String maNV) {
     List<ChucVu_DTO> list = new ArrayList<>();
     String sql = "SELECT cv.* FROM ChucVu cv " +
                  "JOIN NhanVien nv ON cv.ma_cv = nv.ma_cv " +
                  "WHERE nv.ma_nv = ?";
     try (PreparedStatement ps = con.prepareStatement(sql)) {
         ps.setString(1, maNV);
         ResultSet rs = ps.executeQuery();
         while (rs.next()) {
             list.add(new ChucVu_DTO(
                 rs.getString("ma_cv"),
                 rs.getString("ten_cv"),
                 rs.getString("mo_ta")
             ));
         }
     } catch (Exception e) { e.printStackTrace(); }
     return list;
 }

public double layTongPhuCapCuaNhanVien(String maNV) {
        double tongPhuCap = 0.0;
        
        
        String sql = "SELECT SUM(p.so_tien) AS tong_tien " +
                     "FROM PhuCap p " +
                     "JOIN ChiTietChucVu ct ON p.ma_pc = ct.ma_pc " +
                     "JOIN NhanVien nv ON ct.ma_cv = nv.ma_cv " +
                     "WHERE nv.ma_nv = ?";
                     
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                tongPhuCap = rs.getDouble("tong_tien");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return tongPhuCap;
    }
 
 
}