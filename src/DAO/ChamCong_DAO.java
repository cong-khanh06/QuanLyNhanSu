package DAO;

import DTO.ChamCong_DTO;
import DTO.NhanVien_DTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChamCong_DAO extends Connection_DAO {
    Connection_DAO connDAO = new Connection_DAO();

    public List<ChamCong_DTO> getDanhSachChamCong(
            Integer thang, Integer nam,
            String phong, String gioiTinh,
            String keyword) {

        List<ChamCong_DTO> list = new ArrayList<>();

        String sql = """
                    SELECT cc.ma_cc, nv.ma_nv, nv.ho_ten,
                           cc.thang, cc.nam,
                           cc.so_ngay_lam, cc.so_gio_tre, cc.so_gio_tang_ca,
                           cc.so_ngay_tre, cc.so_ngay_tang_ca
                    FROM BangChamCong cc
                    JOIN NhanVien nv ON cc.ma_nv = nv.ma_nv
                    WHERE cc.thang = ?
                      AND cc.nam = ?
                      AND (? = '' OR nv.ma_pb = ?)
                      AND (? = '' OR nv.gioi_tinh = ?)
                      AND (? = '' OR nv.ma_nv LIKE ? OR nv.ho_ten LIKE ?)
                """;

        try (
                Connection con = connDAO.getCon();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ps.setString(3, phong);
            ps.setString(4, phong);
            ps.setString(5, gioiTinh);
            ps.setString(6, gioiTinh);
            ps.setString(7, keyword);
            ps.setString(8, "%" + keyword + "%");
            ps.setString(9, "%" + keyword + "%");

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<NhanVien_DTO> getDanhSachNhanVien() {
        ArrayList<NhanVien_DTO> list = new ArrayList<>();
        String sql = "SELECT ma_nv, ho_ten FROM NhanVien";
        try (
                Connection con = connDAO.getCon();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhanVien_DTO nv = new NhanVien_DTO();
                nv.setMaNV(rs.getString("ma_nv"));
                nv.setHoTen(rs.getString("ho_ten"));
                list.add(nv);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<String> getDanhSachPhong() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT ma_pb FROM PhongBan";
        try (
                Connection con = connDAO.getCon();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("ma_pb"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertOrUpdate(ChamCong_DTO dto) {
        String sql = """
                    MERGE BangChamCong AS target
                    USING (SELECT ? AS ma_nv, ? AS thang, ? AS nam) AS src
                    ON target.ma_nv = src.ma_nv AND target.thang = src.thang AND target.nam = src.nam
                    WHEN MATCHED THEN
                        UPDATE SET so_ngay_lam = ?, so_gio_tre = ?, so_gio_tang_ca = ?, 
                                   so_ngay_tre = ?, so_ngay_tang_ca = ?
                    WHEN NOT MATCHED THEN
                        INSERT (ma_cc, ma_nv, thang, nam, so_ngay_lam, so_gio_tre, so_gio_tang_ca, so_ngay_tre, so_ngay_tang_ca)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                    """;

        try (
            Connection con = connDAO.getCon();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, dto.getMaNV());
            ps.setInt(2, dto.getThang());
            ps.setInt(3, dto.getNam());

            ps.setInt(4, dto.getSoNgayLam());
            ps.setInt(5, dto.getSoGioTre());
            ps.setInt(6, dto.getSoGioTangCa());
            ps.setInt(7, dto.getSoNgayTre());
            ps.setInt(8, dto.getSoNgayTangCa());

            ps.setString(9, dto.getMaChamCong());
            ps.setString(10, dto.getMaNV());
            ps.setInt(11, dto.getThang());
            ps.setInt(12, dto.getNam());
            ps.setInt(13, dto.getSoNgayLam());
            ps.setInt(14, dto.getSoGioTre());
            ps.setInt(15, dto.getSoGioTangCa());
            ps.setInt(16, dto.getSoNgayTre());
            ps.setInt(17, dto.getSoNgayTangCa());

            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}