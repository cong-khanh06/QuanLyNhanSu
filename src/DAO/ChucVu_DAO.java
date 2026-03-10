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

    // 2. Tìm kiếm chức vụ (Đã bỏ cột ma_pc)
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

    // 3. Thêm chức vụ mới (Chỉ Insert 3 cột: ma_cv, ten_cv, mo_ta)
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

    // 4. Cập nhật chức vụ (Chỉ Update ten_cv và mo_ta)
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

    // 5. Xóa chức vụ (Phải xóa khóa ngoại ở ChiTietChucVu trước)
    public boolean deleteChucVu(String maCV) {
        try {
            con.setAutoCommit(false); // Bắt đầu transaction (để nếu lỗi thì quay lại)
            
            // BƯỚC 1: Xóa các bản ghi liên quan trong bảng trung gian ChiTietChucVu
            String sqlChiTiet = "DELETE FROM ChiTietChucVu WHERE ma_cv = ?";
            try (PreparedStatement ps1 = con.prepareStatement(sqlChiTiet)) {
                ps1.setString(1, maCV);
                ps1.executeUpdate(); // Có thể trả về 0 nếu chưa có phụ cấp, không sao cả
            }

            // BƯỚC 2: Xóa chức vụ trong bảng ChucVu
            String sqlChucVu = "DELETE FROM ChucVu WHERE ma_cv = ?";
            try (PreparedStatement ps2 = con.prepareStatement(sqlChucVu)) {
                ps2.setString(1, maCV);
                int result = ps2.executeUpdate();
                
                con.commit(); // Hoàn tất Transaction
                return result > 0;
            }
        } catch (Exception e) {
            try {
                con.rollback(); // Rollback nếu có bất kỳ lỗi nào xảy ra
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true); // Trả DB về trạng thái mặc định
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 6. Tạo mã CV tự động (Giữ nguyên)
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

    // =========================================================================
    // 7. LẤY DANH SÁCH PHỤ CẤP CỦA 1 CHỨC VỤ (DÙNG ĐỂ HIỂN THỊ BẢNG Ở DƯỚI GUI)
    // =========================================================================
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
    
    // Lấy toàn bộ danh sách phụ cấp trong CSDL (Để hiển thị lên ComboBox khi muốn gán thêm)
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

    // Gán 1 phụ cấp cho 1 chức vụ (INSERT vào ChiTietChucVu)
    public boolean themPhuCapChoChucVu(String maCV, String maPC) {
        String sql = "INSERT INTO ChiTietChucVu (ma_cv, ma_pc) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maCV);
            ps.setString(2, maPC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            // Sẽ nhảy vào đây nếu bạn cố thêm 1 phụ cấp đã được gán rồi (Lỗi trùng Khóa chính)
            return false; 
        }
    }

    // Xóa 1 phụ cấp khỏi chức vụ (DELETE khỏi ChiTietChucVu)
    public boolean xoaPhuCapKhoiChucVu(String maCV, String maPC) {
        String sql = "DELETE FROM ChiTietChucVu WHERE ma_cv = ? AND ma_pc = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maCV);
            ps.setString(2, maPC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
 // Thêm vào ChucVu_DAO.java

 // Lấy chức vụ của 1 nhân viên cụ thể
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

 public List<PhuCap_DTO> layPhuCapCuaNhanVien(String maNV) {
     List<PhuCap_DTO> list = new ArrayList<>();
     String sql = "SELECT p.* FROM PhuCap p " +
                  "JOIN ChiTietChucVu ct ON p.ma_pc = ct.ma_pc " +
                  "JOIN NhanVien nv ON ct.ma_cv = nv.ma_cv " +
                  "WHERE nv.ma_nv = ?";
     try (PreparedStatement ps = con.prepareStatement(sql)) {
         ps.setString(1, maNV);
         ResultSet rs = ps.executeQuery();
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
 
 
}