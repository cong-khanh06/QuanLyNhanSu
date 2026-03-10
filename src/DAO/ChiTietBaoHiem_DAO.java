package DAO;

import DTO.ChiTietBaoHiem_DTO;
import DTO.LoaiBaoHiem_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChiTietBaoHiem_DAO extends Connection_DAO {
    Connection con = getCon();

    public List<LoaiBaoHiem_DTO> layTatCaLoaiBaoHiem() {
        List<LoaiBaoHiem_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiBaoHiem";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new LoaiBaoHiem_DTO(
                    rs.getString("ma_bh"), 
                    rs.getString("ten_bh"),
                    rs.getDouble("ty_le_nv_dong"), 
                    rs.getDouble("ty_le_cty_dong")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<ChiTietBaoHiem_DTO> layBaoHiemTheoNhanVien(String maNV) {
        List<ChiTietBaoHiem_DTO> list = new ArrayList<>();
        String sql = "SELECT ct.ma_ctbh, ct.ma_nv, ct.ma_bh, ct.so_the_bh, ct.ngay_cap, ct.noi_cap, " +
                     "lb.ten_bh, lb.ty_le_nv_dong, lb.ty_le_cty_dong " +
                     "FROM ChiTietBaoHiem ct " +
                     "INNER JOIN LoaiBaoHiem lb ON ct.ma_bh = lb.ma_bh " +
                     "WHERE ct.ma_nv = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNV);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    java.sql.Date sqlNgayCap = rs.getDate("ngay_cap");
                    LocalDate ngayCap = (sqlNgayCap != null) ? sqlNgayCap.toLocalDate() : null;

                    list.add(new ChiTietBaoHiem_DTO(
                        rs.getString("ma_ctbh"), rs.getString("so_the_bh"), rs.getString("noi_cap"),
                        rs.getString("ma_nv"), rs.getString("ma_bh"), ngayCap,
                        rs.getString("ten_bh"), rs.getDouble("ty_le_nv_dong"), rs.getDouble("ty_le_cty_dong")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean themChiTietBaoHiem(ChiTietBaoHiem_DTO dto) {
        String sql = "INSERT INTO ChiTietBaoHiem (ma_ctbh, ma_nv, ma_bh, so_the_bh, ngay_cap, noi_cap) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dto.getMaCTBH());
            ps.setString(2, dto.getMaNV());
            ps.setString(3, dto.getMaBH());
            ps.setString(4, dto.getSoTheBH());
            ps.setDate(5, dto.getNgayCap() != null ? java.sql.Date.valueOf(dto.getNgayCap()) : null);
            ps.setString(6, dto.getNoiCap());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean xoaChiTietBaoHiem(String maCTBH) {
        String sql = "DELETE FROM ChiTietBaoHiem WHERE ma_ctbh = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maCTBH);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public String taoMaMoi() {
        String sql = "SELECT TOP 1 ma_ctbh FROM ChiTietBaoHiem ORDER BY CAST(SUBSTRING(ma_ctbh, 5, LEN(ma_ctbh)) AS INT) DESC";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String lastID = rs.getString("ma_ctbh");
                int number = Integer.parseInt(lastID.substring(4)) + 1;
                return String.format("CTBH%02d", number);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return "CTBH01";
    }
    
    // Thay thế 2 hàm cũ bằng 2 hàm này trong ChiTietBaoHiem_DAO.java
    public List<ChiTietBaoHiem_DTO> layDanhSachNhanVienBaoHiem() {
        List<ChiTietBaoHiem_DTO> list = new ArrayList<>();
        String sql = "SELECT nv.ma_nv, nv.ho_ten, COUNT(ct.ma_bh) as so_luong " +
                     "FROM NhanVien nv " +
                     "LEFT JOIN ChiTietBaoHiem ct ON nv.ma_nv = ct.ma_nv " +
                     "GROUP BY nv.ma_nv, nv.ho_ten";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()) {
                list.add(new ChiTietBaoHiem_DTO(
                    rs.getString("ma_nv"),
                    rs.getString("ho_ten"),
                    rs.getInt("so_luong")
                ));
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<ChiTietBaoHiem_DTO> timKiemNhanVienBaoHiem(String tuKhoa) {
        List<ChiTietBaoHiem_DTO> list = new ArrayList<>();
        String sql = "SELECT nv.ma_nv, nv.ho_ten, COUNT(ct.ma_bh) as so_luong " +
                     "FROM NhanVien nv " +
                     "LEFT JOIN ChiTietBaoHiem ct ON nv.ma_nv = ct.ma_nv " +
                     "WHERE nv.ma_nv LIKE ? OR nv.ho_ten LIKE ? " +
                     "GROUP BY nv.ma_nv, nv.ho_ten";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String kw = "%" + tuKhoa + "%";
            ps.setString(1, kw); ps.setString(2, kw);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    list.add(new ChiTietBaoHiem_DTO(
                        rs.getString("ma_nv"),
                        rs.getString("ho_ten"),
                        rs.getInt("so_luong")
                    ));
                }
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }
    public List<ChiTietBaoHiem_DTO> layDanhSachNhanVienBaoHiemtumanv(String manv) {
        List<ChiTietBaoHiem_DTO> list = new ArrayList<>();
        String sql = "SELECT nv.ma_nv, nv.ho_ten, COUNT(ct.ma_bh) as so_luong " +
                     "FROM NhanVien nv " +
                     "LEFT JOIN ChiTietBaoHiem ct ON nv.ma_nv = ct.ma_nv "
                     + " Where nv.ma_nv=? " +
                     "GROUP BY nv.ma_nv, nv.ho_ten";
        try
        {
        	PreparedStatement ps=con.prepareStatement(sql);
        	ps.setString(1,manv);
        	ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                list.add(new ChiTietBaoHiem_DTO(
                    rs.getString("ma_nv"),
                    rs.getString("ho_ten"),
                    rs.getInt("so_luong")));
                }      
           }
        catch(Exception e) { 
        	e.printStackTrace();
        }
        return list;
    }
}