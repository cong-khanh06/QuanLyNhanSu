package DAO;

import DTO.PhanCongDuAn_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhanCongDuAn_DAO extends Connection_DAO {
    Connection con = getCon();

    // 1. Lấy danh sách phân công theo Mã Dự Án
    public List<PhanCongDuAn_DTO> layDanhSachPhanCong(String maDA) {
        List<PhanCongDuAn_DTO> list = new ArrayList<>();
        String sql = "SELECT pc.ma_da, pc.ma_nv, nv.ho_ten, pc.vai_tro " +
                     "FROM PhanCongDuAn pc " +
                     "JOIN NhanVien nv ON pc.ma_nv = nv.ma_nv " +
                     "WHERE pc.ma_da = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDA);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new PhanCongDuAn_DTO(
                    rs.getString("ma_da"), rs.getString("ma_nv"), 
                    rs.getString("ho_ten"), rs.getString("vai_tro")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm phân công
    public boolean insertPhanCong(PhanCongDuAn_DTO pc) {
        String sql = "INSERT INTO PhanCongDuAn (ma_da, ma_nv, vai_tro) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pc.getMaDA());
            ps.setString(2, pc.getMaNV());
            ps.setNString(3, pc.getVaiTro());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi Thêm Phân Công (Có thể do trùng lặp hoặc sai mã NV): " + e.getMessage());
        }
        return false;
    }

    // 3. Xóa phân công
    public boolean deletePhanCong(String maDA, String maNV) {
        String sql = "DELETE FROM PhanCongDuAn WHERE ma_da=? AND ma_nv=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDA);
            ps.setString(2, maNV);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 4. Cập nhật vai trò
    public boolean updatePhanCong(PhanCongDuAn_DTO pc) {
        String sql = "UPDATE PhanCongDuAn SET vai_tro=? WHERE ma_da=? AND ma_nv=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setNString(1, pc.getVaiTro());
            ps.setString(2, pc.getMaDA());
            ps.setString(3, pc.getMaNV());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 5. Lấy danh sách nhân viên đang làm việc để hiển thị lên ComboBox
    public List<String> layDanhSachNhanVienCombobox() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT ma_nv, ho_ten FROM NhanVien WHERE trang_thai = N'DangLam'";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("ma_nv") + " - " + rs.getString("ho_ten"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}