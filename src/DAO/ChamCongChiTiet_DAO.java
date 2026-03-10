package DAO;

import DTO.ChamCongChiTiet_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import DTO.ChamCong_DTO;

public class ChamCongChiTiet_DAO extends Connection_DAO {
    Connection_DAO connDAO = new Connection_DAO();

    public boolean deleteByMaChamCong(String maChamCong) {
        String sql = "DELETE FROM ChamCongChiTiet WHERE ma_cc=?";
        try (
                Connection con = getCon();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, maChamCong);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ChamCongChiTiet_DTO> getByMaChamCong(String maChamCong) {
        List<ChamCongChiTiet_DTO> list = new ArrayList<>();
        String sql = "SELECT ma_cc, ngay, trang_thai, so_gio FROM ChamCongChiTiet WHERE ma_cc=?";

        try (
                Connection con = getCon();
                PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setString(1, maChamCong);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ChamCongChiTiet_DTO(
                        rs.getString("ma_cc"),
                        rs.getInt("ngay"),
                        rs.getString("trang_thai"),
                        rs.getInt("so_gio")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertList(List<ChamCongChiTiet_DTO> list) {
        String sql = "INSERT INTO ChamCongChiTiet (ma_cc, ngay, trang_thai, so_gio) VALUES(?,?,?,?)";

        try (
                Connection con = getCon();
                PreparedStatement ps = con.prepareStatement(sql);) {

            for (ChamCongChiTiet_DTO ct : list) {
                ps.setString(1, ct.getMaChamCong());
                ps.setInt(2, ct.getNgay());
                ps.setString(3, ct.getTrangThai());
                ps.setInt(4, ct.getSoGio());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int[] getThongKeChamCong(String maChamCong) {
        int[] kq = new int[4];
        try {
            Connection con = connDAO.getCon();
            String sql = """
                        SELECT
                            COALESCE(SUM(CASE WHEN trang_thai = N'Nghỉ' THEN 1 END), 0),
                            COALESCE(SUM(CASE WHEN trang_thai = N'Trễ' THEN 1 END), 0),
                            COALESCE(SUM(CASE WHEN trang_thai = N'Tăng ca' THEN 1 END), 0),
                            COUNT(*)
                        FROM ChamCongChiTiet
                        WHERE ma_cc = ?
                    """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maChamCong);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                kq[0] = rs.getInt(1); 
                kq[1] = rs.getInt(2); 
                kq[2] = rs.getInt(3); 

                int tong = rs.getInt(4);
                kq[3] = (tong > 0) ? 1 : 0; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }
    public List<ChamCong_DTO> getDanhSachChamCongSVDangNhap(int thang, int nam, String manv) {
        List<ChamCong_DTO> list = new ArrayList<>();
        String sql = """
                    SELECT cc.ma_cc, nv.ma_nv, nv.ho_ten,
                           cc.thang, cc.nam,
                           cc.so_ngay_lam, cc.so_gio_tre, cc.so_gio_tang_ca,
                           cc.so_ngay_tre, cc.so_ngay_tang_ca
                    FROM BangChamCong cc
                    JOIN NhanVien nv ON cc.ma_nv = nv.ma_nv
                    WHERE cc.thang = ? AND cc.nam = ? AND nv.ma_nv = ?
                """;

        try (Connection con = connDAO.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ps.setString(3, manv);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChamCong_DTO dto = new ChamCong_DTO();
                dto.setMaChamCong(rs.getString("ma_cc"));
                dto.setMaNV(rs.getString("ma_nv"));
                dto.setHoTen(rs.getString("ho_ten"));
                dto.setThang(rs.getInt("thang"));
                dto.setNam(rs.getInt("nam"));
                dto.setSoNgayLam(rs.getInt("so_ngay_lam"));
                dto.setSoGioTre(rs.getInt("so_gio_tre"));
                dto.setSoGioTangCa(rs.getInt("so_gio_tang_ca"));
                dto.setSoNgayTre(rs.getInt("so_ngay_tre"));
                dto.setSoNgayTangCa(rs.getInt("so_ngay_tang_ca"));
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}