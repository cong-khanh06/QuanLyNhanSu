package DAO;

import DTO.ThongBao_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThongBao_DAO extends Connection_DAO {
    Connection con = getCon();
    Statement stmt = getStmt();

    public List<ThongBao_DTO> layDanhSachThongBao() {
        List<ThongBao_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ThongBao ORDER BY ngay_tao DESC";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String maTB = rs.getString("ma_tb");
                String maTK = rs.getString("ma_tk");
                String noiDung = rs.getString("noi_dung");
                Date dbNgayTao = rs.getDate("ngay_tao");
                LocalDate ngayTao = (dbNgayTao != null) ? dbNgayTao.toLocalDate() : null;
                
                list.add(new ThongBao_DTO(maTB, maTK, noiDung, ngayTao));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertThongBao(ThongBao_DTO tb) {
        String sql = "INSERT INTO ThongBao (ma_tb, ma_tk, noi_dung, ngay_tao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tb.getMaTB());
            ps.setString(2, tb.getMaTK());
            ps.setNString(3, tb.getNoiDung()); 
            ps.setDate(4, tb.getNgayTao() != null ? Date.valueOf(tb.getNgayTao()) : Date.valueOf(LocalDate.now()));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi Thêm Thông Báo: " + e.getMessage());
        }
        return false;
    }

    public boolean updateThongBao(ThongBao_DTO tb) {
        String sql = "UPDATE ThongBao SET ma_tk=?, noi_dung=? WHERE ma_tb=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tb.getMaTK());
            ps.setNString(2, tb.getNoiDung());
            
            ps.setString(3, tb.getMaTB());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteThongBao(String maTB) {
        String sql = "DELETE FROM ThongBao WHERE ma_tb=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maTB);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getNewestMaTB() {
        String sql = "SELECT TOP 1 ma_tb FROM ThongBao ORDER BY CAST(SUBSTRING(ma_tb, 3, LEN(ma_tb)) AS INT) DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String lastID = rs.getString("ma_tb");
                String numberPart = lastID.substring(2);
                int number = Integer.parseInt(numberPart) + 1;
                return String.format("TB%02d", number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "TB01";
    }
}