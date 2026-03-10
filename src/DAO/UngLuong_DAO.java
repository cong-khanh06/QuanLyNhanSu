package DAO;

import DTO.UngLuong_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class UngLuong_DAO extends Connection_DAO {
    Connection con = getCon();
    Statement stmt = getStmt();
    
    private String dichSangDB(String ttUI) {
        if (ttUI == null) return "ChoDuyet";
        String tt = ttUI.trim().toLowerCase();
        if (tt.equals("đã duyệt")) return "DaDuyet";
        if (tt.equals("từ chối")) return "TuChoi";
        return "ChoDuyet";
    }

    
    private String dichSangUI(String ttDB) {
        if (ttDB == null) return "Chờ duyệt";
        String tt = ttDB.trim();
        if (tt.equalsIgnoreCase("DaDuyet")) return "Đã duyệt";
        if (tt.equalsIgnoreCase("TuChoi")) return "Từ chối";
        return "Chờ duyệt";
    }
    
    public boolean insertUngLuong(UngLuong_DTO ul) {
        String sql = "INSERT INTO UngLuong (ma_ul, ma_bl, ly_do, trang_thai, ngay_ung, so_tien) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ul.getMaUL().trim());
            ps.setString(2, ul.getMaBL().trim());
            ps.setNString(3, ul.getLyDo());
            
            
            ps.setString(4, dichSangDB(ul.getTrangThai())); 
            
            ps.setDate(5, ul.getNgayUng() != null ? Date.valueOf(ul.getNgayUng()) : null);
            ps.setBigDecimal(6, ul.getSoTien());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi Thêm Ứng Lương: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteUngLuong(String maUL) {
        String sql = "DELETE FROM UngLuong WHERE ma_ul=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maUL.trim());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateUngLuong(UngLuong_DTO ul) {
        String sql = "UPDATE UngLuong SET ma_bl=?, ly_do=?, trang_thai=?, ngay_ung=?, so_tien=? WHERE ma_ul=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ul.getMaBL().trim());
            ps.setNString(2, ul.getLyDo());
            
            // Ép sang chuẩn DB
            ps.setString(3, dichSangDB(ul.getTrangThai())); 
            
            ps.setDate(4, Date.valueOf(ul.getNgayUng()));
            ps.setBigDecimal(5, ul.getSoTien());
            ps.setString(6, ul.getMaUL().trim());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi Sửa Ứng Lương: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public List<UngLuong_DTO> layDanhSachUngLuong() {
        List<UngLuong_DTO> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM UngLuong";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String maUL = rs.getString("ma_ul");
                String maBL = rs.getString("ma_bl");
                String lyDo = rs.getString("ly_do");
                
                // Dịch ngược từ DB (ChoDuyet) lên UI (Chờ duyệt)
                String trangThai = dichSangUI(rs.getString("trang_thai"));
                
                Date dbNgayUng = rs.getDate("ngay_ung");
                LocalDate ngayUng = (dbNgayUng != null) ? dbNgayUng.toLocalDate() : null;
                BigDecimal soTien = rs.getBigDecimal("so_tien");
                
                UngLuong_DTO rowdata = new UngLuong_DTO(maUL, maBL, lyDo, trangThai, ngayUng, soTien);
                list.add(rowdata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public String getNewestMaUL() {
        String sql = "SELECT TOP 1 ma_ul FROM UngLuong ORDER BY CAST(SUBSTRING(ma_ul, 3, LEN(ma_ul)) AS INT) DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            if (rs.next()) {
                String lastID = rs.getString("ma_ul"); 
                String numberPart = lastID.substring(2);
                int number = Integer.parseInt(numberPart);
                number++; 
                return String.format("UL%02d", number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "UL01";
    }
    
    public List<UngLuong_DTO> timKiemUngLuong(String tuKhoa) {
        List<UngLuong_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM UngLuong WHERE ma_ul LIKE ? OR ma_bl LIKE ? OR ly_do LIKE ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String keyword = "%" + tuKhoa + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maUL = rs.getString("ma_ul");
                    String maBL = rs.getString("ma_bl");
                    String lyDo = rs.getString("ly_do");
                    
                    
                    String trangThai = dichSangUI(rs.getString("trang_thai"));
                    
                    Date dbNgayUng = rs.getDate("ngay_ung");
                    LocalDate ngayUng = (dbNgayUng != null) ? dbNgayUng.toLocalDate() : null;
                    BigDecimal soTien = rs.getBigDecimal("so_tien");
                    
                    UngLuong_DTO rowdata = new UngLuong_DTO(maUL, maBL, lyDo, trangThai, ngayUng, soTien);
                    list.add(rowdata);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int soLuongUngLuongTheoTrangThai(String trangThaiUI) {
        String sql = "SELECT COUNT(*) FROM UngLuong WHERE trang_thai = ?";
        int count = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dichSangDB(trangThaiUI)); 
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}