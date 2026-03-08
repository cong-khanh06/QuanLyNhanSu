package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import DTO.TaiKhoan_DTO;

public class TaiKhoan_DAO {
    
    // Lấy toàn bộ danh sách tài khoản
    public ArrayList<TaiKhoan_DTO> getListtaikhoan() {
        ArrayList<TaiKhoan_DTO> list = new ArrayList<>();
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query = "SELECT * FROM TaiKhoan";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                list.add(new TaiKhoan_DTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
        return list;
    }

    // Kiểm tra tên đăng nhập tồn tại chưa
    public boolean checktaikhoan(String taikhoan) {
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query = "SELECT * FROM TaiKhoan WHERE ten_dang_nhap=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, taikhoan);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
        return false;
    }

    // Kiểm tra mã tài khoản (Khóa chính) tồn tại chưa
    public boolean checkExistMaTK(String maTK) {
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query = "SELECT * FROM TaiKhoan WHERE ma_tk=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, maTK);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
        return false;
    }

    // Kiểm tra mật khẩu cũ
    public boolean checkmatkhau(String taikhoan, String matkhau) {
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query = "SELECT * FROM TaiKhoan WHERE ten_dang_nhap=? and mat_khau=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, taikhoan);
            ps.setString(2, matkhau);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
        return false;
    }

    // Đổi mật khẩu
    public boolean updatePassword(String user, String newPass) {
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query = "UPDATE TaiKhoan SET mat_khau=? WHERE ten_dang_nhap=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newPass);
            ps.setString(2, user);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
    }

    // Thêm tài khoản mới
    public boolean insert(TaiKhoan_DTO tk) {
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query = "INSERT INTO TaiKhoan (ma_tk, ten_dang_nhap, mat_khau, quyen_truy_cap, ma_nv) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, tk.getMataikhoan());  
            ps.setString(2, tk.getTendangnhap()); 
            ps.setString(3, tk.getMatkhau());      
            ps.setString(4, tk.getQuyentruycap()); 
            ps.setString(5, tk.getManv());         
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
        return false;
    }

// update
    public boolean update(TaiKhoan_DTO tk) {
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query;            boolean isChangePass = (tk.getMatkhau() != null && !tk.getMatkhau().trim().isEmpty());

            if (isChangePass) {
                query = "UPDATE TaiKhoan SET mat_khau=?, quyen_truy_cap=?, ma_nv=? WHERE ma_tk=?";
            } else {
                query = "UPDATE TaiKhoan SET quyen_truy_cap=?, ma_nv=? WHERE ma_tk=?";
            }

            PreparedStatement ps = con.prepareStatement(query);
            
            if (isChangePass) {
                ps.setString(1, tk.getMatkhau());
                ps.setString(2, tk.getQuyentruycap());
                ps.setString(3, tk.getManv());
                ps.setString(4, tk.getMataikhoan());
            } else {
                ps.setString(1, tk.getQuyentruycap());
                ps.setString(2, tk.getManv());
                ps.setString(3, tk.getMataikhoan());
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
        return false;
    }

    // Xóa tài khoản
    public boolean delete(String maTK) {
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query = "DELETE FROM TaiKhoan WHERE ma_tk=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, maTK);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
        return false;
    }

    // Tìm kiếm đa năng
    public ArrayList<TaiKhoan_DTO> search(String keyword) {
        ArrayList<TaiKhoan_DTO> list = new ArrayList<>();
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query = "SELECT * FROM TaiKhoan WHERE ten_dang_nhap LIKE ? OR ma_nv LIKE ? OR ma_tk LIKE ?";
            PreparedStatement ps = con.prepareStatement(query);
            String k = "%" + keyword + "%";
            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new TaiKhoan_DTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
        return list;
    }

    // Kiểm tra nhân viên này đã có tài khoản chưa (vì quan hệ 1-1)
    public boolean checkExistMaNV(String manv) {
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query = "SELECT * FROM TaiKhoan WHERE ma_nv=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, manv);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
        return false;
    }

    // Lấy tên chức vụ qua JOIN 3 bảng
    public String getTenchucvu(String user) {
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        String ketqua = null;
        try {
            con = conn.getCon();
            String query = "SELECT cv.ten_cv FROM TaiKhoan tk " +
                           "JOIN NhanVien nv ON tk.ma_nv = nv.ma_nv " +
                           "JOIN ChucVu cv ON nv.ma_cv = cv.ma_cv " +
                           "WHERE tk.ten_dang_nhap = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ketqua = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
        return ketqua;
    }
}