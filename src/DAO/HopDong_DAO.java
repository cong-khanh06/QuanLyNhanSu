package DAO;

import DTO.HopDong_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HopDong_DAO {

    Connection_DAO connDAO = new Connection_DAO();

    public ArrayList<HopDong_DTO> getList() { 
        ArrayList<HopDong_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM HopDong";
        try (Connection con = connDAO.getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HopDong_DTO hd = new HopDong_DTO();
                hd.setMaHD(rs.getString("ma_hd"));
                hd.setLoaiHopDong(rs.getString("loai_hop_dong"));
                hd.setNgayBatDau(rs.getDate("ngay_bat_dau"));
                hd.setNgayKetThuc(rs.getDate("ngay_ket_thuc"));
                hd.setNgayKy(rs.getDate("ngay_ky"));
                hd.setMucLuongCoBan(rs.getLong("muc_luong_co_ban"));
                hd.setNoiDung(rs.getString("noi_dung"));
                hd.setTrangThai(rs.getString("trang_thai"));
                hd.setMaNV(rs.getString("ma_nv"));
                hd.setLanKy(rs.getInt("Lan_ky"));
                hd.setNgayLenLuongGanNhat(rs.getDate("ngay_len_luong_gan_nhat"));
                list.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertHopDong(HopDong_DTO hd) {
        String sql = "INSERT INTO HopDong (ma_hd, loai_hop_dong, ngay_bat_dau, ngay_ket_thuc, ngay_ky, " +
                     "muc_luong_co_ban, noi_dung, trang_thai, ma_nv, Lan_ky, ngay_len_luong_gan_nhat) " +
                     "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = connDAO.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, hd.getMaHD());
            ps.setString(2, hd.getLoaiHopDong());
            ps.setDate(3, hd.getNgayBatDau());
            ps.setDate(4, hd.getNgayKetThuc());
            ps.setDate(5, hd.getNgayKy());
            ps.setDouble(6, hd.getMucLuongCoBan());
            ps.setString(7, hd.getNoiDung());
            ps.setString(8, hd.getTrangThai());
            ps.setString(9, hd.getMaNV());
            ps.setInt(10, hd.getLanKy());
            ps.setDate(11, hd.getNgayLenLuongGanNhat());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateHopDong(HopDong_DTO hd) {
        String sql = "UPDATE HopDong SET loai_hop_dong=?, ngay_bat_dau=?, ngay_ket_thuc=?, ngay_ky=?, " +
                     "muc_luong_co_ban=?, noi_dung=?, trang_thai=?, ma_nv=?, Lan_ky=?, ngay_len_luong_gan_nhat=? " +
                     "WHERE ma_hd=?";
        try (Connection con = connDAO.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, hd.getLoaiHopDong());
            ps.setDate(2, hd.getNgayBatDau());
            ps.setDate(3, hd.getNgayKetThuc());
            ps.setDate(4, hd.getNgayKy());
            ps.setDouble(5, hd.getMucLuongCoBan());
            ps.setString(6, hd.getNoiDung());
            ps.setString(7, hd.getTrangThai());
            ps.setString(8, hd.getMaNV());
            ps.setInt(9, hd.getLanKy());
            ps.setDate(10, hd.getNgayLenLuongGanNhat());
            ps.setString(11, hd.getMaHD());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHopDong(String maHD) {
        String sql = "DELETE FROM HopDong WHERE ma_hd=?";
        try (Connection con = connDAO.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHD);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<HopDong_DTO> search(String keyword) {
        ArrayList<HopDong_DTO> list = new ArrayList<>();
        // Tìm kiếm theo mã hợp đồng, mã nhân viên hoặc loại hợp đồng
        String sql = "SELECT * FROM HopDong WHERE ma_hd LIKE ? OR ma_nv LIKE ? OR loai_hop_dong LIKE ?";
        
        try (Connection con = connDAO.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            String value = "%" + keyword + "%";
            ps.setString(1, value);
            ps.setString(2, value);
            ps.setString(3, value);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HopDong_DTO hd = new HopDong_DTO();
                    hd.setMaHD(rs.getString("ma_hd"));
                    hd.setLoaiHopDong(rs.getString("loai_hop_dong"));
                    hd.setNgayBatDau(rs.getDate("ngay_bat_dau"));
                    hd.setNgayKetThuc(rs.getDate("ngay_ket_thuc"));
                    hd.setNgayKy(rs.getDate("ngay_ky"));
                    hd.setMucLuongCoBan(rs.getLong("muc_luong_co_ban"));
                    hd.setNoiDung(rs.getString("noi_dung"));
                    hd.setTrangThai(rs.getString("trang_thai"));
                    hd.setMaNV(rs.getString("ma_nv"));
                    hd.setLanKy(rs.getInt("Lan_ky"));
                    hd.setNgayLenLuongGanNhat(rs.getDate("ngay_len_luong_gan_nhat"));
                    list.add(hd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean checkMaHopDongTonTai(String maHD) {
        String sql = "SELECT 1 FROM HopDong WHERE ma_hd=?";
        try (Connection con = connDAO.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHD);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkNhanVienTonTai(String maNV) {
        String sql = "SELECT 1 FROM NhanVien WHERE ma_nv=?";
        try (Connection con = connDAO.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNV);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}