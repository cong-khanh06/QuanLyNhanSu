package DAO;

import DTO.ChamCong_DTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ChamCong_DAO extends Connection_DAO {
    
    
    public List<ChamCong_DTO> getDanhSachChamCong(int thang, int nam, String phong, String keyword) {
        List<ChamCong_DTO> list = new ArrayList<>();
        
        
        StringBuilder sql = new StringBuilder(
            "SELECT cc.*, nv.ho_ten " +
            "FROM BangChamCong cc " +
            "LEFT JOIN NhanVien nv ON cc.ma_nv = nv.ma_nv " +
            "WHERE 1=1 "
        );

        
        if (thang > 0) {
            sql.append(" AND MONTH(cc.ngay_tao) = ?");
        }
        if (nam > 0) {
            sql.append(" AND YEAR(cc.ngay_tao) = ?");
        }
        if (phong != null && !phong.isEmpty()) {
            sql.append(" AND nv.ma_pb = ?");
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (cc.ma_nv LIKE ? OR nv.ho_ten LIKE ?)");
        }
        sql.append(" ORDER BY cc.ngay_tao DESC");

        try (Connection con = getCon();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
             
            int index = 1;
            if (thang > 0) ps.setInt(index++, thang);
            if (nam > 0) ps.setInt(index++, nam);
            
            if (phong != null && !phong.isEmpty()) {
                ps.setString(index++, phong);
            }
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(index++, "%" + keyword + "%");
                ps.setString(index++, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChamCong_DTO dto = new ChamCong_DTO();
                dto.setMaChamCong(rs.getString("ma_cc"));
                dto.setMaNV(rs.getString("ma_nv"));
                
                String hoTen = rs.getString("ho_ten");
                dto.setHoTen(hoTen != null ? hoTen : "Nhân viên đã xóa"); // Tránh lỗi null nếu NV bị xóa
                
                Date ngayTaoDB = rs.getDate("ngay_tao");
                dto.setNgayTao(ngayTaoDB != null ? ngayTaoDB.toLocalDate() : null);
                
                Time gioVaoDB = rs.getTime("gio_vao");
                dto.setGioVao(gioVaoDB != null ? gioVaoDB.toLocalTime() : null);
                
                Time gioRaDB = rs.getTime("gio_ra");
                dto.setGioRa(gioRaDB != null ? gioRaDB.toLocalTime() : null);
                
                dto.setSoGioTangCa(rs.getFloat("so_gio_tang_ca"));
                dto.setTrangThai(rs.getString("trang_thai"));
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    
    public boolean insertChamCong(ChamCong_DTO cc) {
        String sql = "INSERT INTO BangChamCong (ma_cc, ngay_tao, gio_vao, gio_ra, so_gio_tang_ca, trang_thai, ma_nv) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = getCon(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cc.getMaChamCong());
            ps.setDate(2, cc.getNgayTao() != null ? Date.valueOf(cc.getNgayTao()) : Date.valueOf(LocalDate.now()));
            
            if (cc.getGioVao() != null) ps.setTime(3, Time.valueOf(cc.getGioVao()));
            else ps.setNull(3, Types.TIME);
            
            if (cc.getGioRa() != null) ps.setTime(4, Time.valueOf(cc.getGioRa()));
            else ps.setNull(4, Types.TIME);
            
            ps.setFloat(5, cc.getSoGioTangCa());
            ps.setNString(6, cc.getTrangThai());
            ps.setString(7, cc.getMaNV());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    
    public boolean updateChamCong(ChamCong_DTO cc) {
        String sql = "UPDATE BangChamCong SET ngay_tao=?, gio_vao=?, gio_ra=?, so_gio_tang_ca=?, trang_thai=?, ma_nv=? WHERE ma_cc=?";
        try (Connection con = getCon(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, cc.getNgayTao() != null ? Date.valueOf(cc.getNgayTao()) : null);
            
            if (cc.getGioVao() != null) ps.setTime(2, Time.valueOf(cc.getGioVao()));
            else ps.setNull(2, Types.TIME);
            
            if (cc.getGioRa() != null) ps.setTime(3, Time.valueOf(cc.getGioRa()));
            else ps.setNull(3, Types.TIME);
            
            ps.setFloat(4, cc.getSoGioTangCa());
            ps.setNString(5, cc.getTrangThai());
            ps.setString(6, cc.getMaNV());
            ps.setString(7, cc.getMaChamCong());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    
    public boolean deleteChamCong(String maCC) {
        String sql = "DELETE FROM BangChamCong WHERE ma_cc=?";
        try (Connection con = getCon(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maCC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    
    public String getNewestMaCC() {
        String sql = "SELECT TOP 1 ma_cc FROM BangChamCong ORDER BY CAST(SUBSTRING(ma_cc, 3, LEN(ma_cc)) AS INT) DESC";
        try (Connection con = getCon(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String lastID = rs.getString(1);
                int number = Integer.parseInt(lastID.substring(2)) + 1;
                return String.format("CC%02d", number);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return "CC01";
    }

    public List<String> getDanhSachPhong() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT ma_pb FROM PhongBan";
        try (Connection con = getCon(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { list.add(rs.getString("ma_pb")); }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<String> getDanhSachNhanVienCombobox() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT ma_nv, ho_ten FROM NhanVien WHERE trang_thai = N'DangLam'";
        try (Connection con = getCon(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { list.add(rs.getString("ma_nv") + " - " + rs.getString("ho_ten")); }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}