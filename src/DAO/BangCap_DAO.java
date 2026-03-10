package DAO;

import DTO.BangCap_DTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BangCap_DAO extends Connection_DAO {
    Connection con = getCon();
    Statement stmt = getStmt();

    public List<BangCap_DTO> layDanhSachBangCap() {
        List<BangCap_DTO> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM BangCap";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String maBC = rs.getString("ma_bc");
                String tenBC = rs.getString("ten_bc");
                String noiCap = rs.getString("noi_cap");
                
                Date ngay_cap_sql = rs.getDate("ngay_cap");
                LocalDate ngayCap = (ngay_cap_sql != null) ? ngay_cap_sql.toLocalDate() : null;
                
                String maNV = rs.getString("ma_nv");

                BangCap_DTO rowdata = new BangCap_DTO(maBC, tenBC, noiCap, maNV, ngayCap);
                list.add(rowdata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<BangCap_DTO> layDanhSachBangCapTheoNV(String maNVien) {
        List<BangCap_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM BangCap WHERE ma_nv = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNVien);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maBC = rs.getString("ma_bc");
                String tenBC = rs.getString("ten_bc");
                String noiCap = rs.getString("noi_cap");
                
                Date ngay_cap_sql = rs.getDate("ngay_cap");
                LocalDate ngayCap = (ngay_cap_sql != null) ? ngay_cap_sql.toLocalDate() : null;
                
                String maNV = rs.getString("ma_nv");

                BangCap_DTO rowdata = new BangCap_DTO(maBC, tenBC, noiCap, maNV, ngayCap);
                list.add(rowdata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertBangCap(BangCap_DTO bc) {
        String sql = "INSERT INTO BangCap (ma_bc, ten_bc, noi_cap, ngay_cap, ma_nv) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bc.getMaBC());
            ps.setString(2, bc.getTenBC());
            ps.setString(3, bc.getNoiCap());
            
            ps.setDate(4, bc.getNgayCap() != null ? java.sql.Date.valueOf(bc.getNgayCap()) : null);
            
            if (bc.getMaNV() == null || bc.getMaNV().isEmpty()) {
                ps.setNull(5, java.sql.Types.VARCHAR);
            } else {
                ps.setString(5, bc.getMaNV());
            }

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBangCap(BangCap_DTO bc) {
        String sql = "UPDATE BangCap SET ten_bc = ?, noi_cap = ?, ngay_cap = ?, ma_nv = ? WHERE ma_bc = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bc.getTenBC());
            ps.setString(2, bc.getNoiCap());
            ps.setDate(3, bc.getNgayCap() != null ? java.sql.Date.valueOf(bc.getNgayCap()) : null);
            
            if (bc.getMaNV() == null || bc.getMaNV().isEmpty()) {
                ps.setNull(4, java.sql.Types.VARCHAR);
            } else {
                ps.setString(4, bc.getMaNV());
            }
            
            ps.setString(5, bc.getMaBC());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBangCap(String maBC) {
        String sql = "DELETE FROM BangCap WHERE ma_bc = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maBC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getNewestMaBC() {
        String sql = "SELECT TOP 1 ma_bc FROM BangCap ORDER BY CAST(SUBSTRING(ma_bc, 3, LEN(ma_bc)) AS INT) DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            if (rs.next()) {
                String lastID = rs.getString("ma_bc"); 
                String numberPart = lastID.substring(2);
                int number = Integer.parseInt(numberPart);
                number++; 
                return String.format("BC%02d", number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "BC01"; 
    }
    
    public List<BangCap_DTO> timKiemBangCap(String tuKhoa) {
        List<BangCap_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM BangCap WHERE ma_bc LIKE ? OR ten_bc LIKE ? OR noi_cap LIKE ? OR ma_nv LIKE ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String keyword = "%" + tuKhoa + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            ps.setString(4, keyword);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maBC = rs.getString("ma_bc");
                    String tenBC = rs.getString("ten_bc");
                    String noiCap = rs.getString("noi_cap");
                    
                    java.sql.Date ngay_cap_sql = rs.getDate("ngay_cap");
                    LocalDate ngayCap = (ngay_cap_sql != null) ? ngay_cap_sql.toLocalDate() : null;
                    
                    String maNV = rs.getString("ma_nv");

                    BangCap_DTO rowdata = new BangCap_DTO(maBC, tenBC, noiCap, maNV, ngayCap);
                    list.add(rowdata);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}