package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.TaiKhoan_DTO;

public class TaiKhoan_DAO {
    
    public ArrayList<TaiKhoan_DTO> getListtaikhoan() {
        ArrayList<TaiKhoan_DTO> list = new ArrayList<>();
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            Statement st = con.createStatement();
            String query = "SELECT * FROM TaiKhoan";
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

    public boolean update(TaiKhoan_DTO tk) {
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query = "UPDATE TaiKhoan SET mat_khau=?, quyen_truy_cap=? WHERE ma_tk=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, tk.getMatkhau());
            ps.setString(2, tk.getQuyentruycap());
            ps.setString(3, tk.getMataikhoan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) conn.Closeconnection(con);
        }
        return false;
    }

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

    public ArrayList<TaiKhoan_DTO> search(String keyword) {
        ArrayList<TaiKhoan_DTO> list = new ArrayList<>();
        Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        try {
            con = conn.getCon();
            String query = "SELECT * FROM TaiKhoan WHERE ten_dang_nhap LIKE ? OR ma_nv LIKE ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
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
    public String getTenchucvu(String user)
    {
    	Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        String ketqua=null;
        try
        {
        	con=conn.getCon();
        	String query="Select ten_cv from TaiKhoan tk join NhanVien nv on tk.ma_nv=nv.ma_nv join ChucVu cv"
        					+ " on nv.ma_cv=cv.ma_cv   Where tk.ten_dang_nhap=?";
        	PreparedStatement ps=con.prepareStatement(query);
        	ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            
        	if(rs.next())
        	{
        		ketqua=rs.getString(1);
        	}
        	
        	
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        finally {
        	if(con!=null)
        	{
        		conn.Closeconnection(con);
        	}
        }
        return ketqua;
    }
    public String getQuyen(String user)
    {
    	Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        String quyen=null;
        try
        {
        	con=conn.getCon();
        	String query="Select quyen_truy_cap from TaiKhoan WHERE ten_dang_nhap=?";
        	PreparedStatement ps=con.prepareStatement(query);
        	ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
            	quyen=rs.getString("quyen_truy_cap");
            }
            return quyen;
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        finally {
        	if(con!=null)
        	{
        		conn.Closeconnection(con);
        	}
        }
        return quyen;
    }
    public TaiKhoan_DTO getTaiKhoantheouser(String user)
    {

    	Connection_DAO conn = new Connection_DAO();
        Connection con = null;
        TaiKhoan_DTO tk=null;
        try
        {
        	con=conn.getCon();
        	String query="Select * from TaiKhoan WHERE ten_dang_nhap=?";
        	PreparedStatement ps=con.prepareStatement(query);
        	ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
            	String matk=rs.getString("ma_tk");
            	String tendn=rs.getString("ten_dang_nhap");
            	String pass=rs.getString("mat_khau");
            	String quyen=rs.getString("quyen_truy_cap");
            	String manv=rs.getString("ma_nv");
            	tk=new TaiKhoan_DTO(matk,tendn,pass,quyen,manv);
            }
            return tk;
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        finally {
        	if(con!=null)
        	{
        		conn.Closeconnection(con);
        	}
        }
        return tk;
    	
    }
    
}