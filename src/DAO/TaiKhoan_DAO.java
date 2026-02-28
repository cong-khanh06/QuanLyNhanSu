package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.TaiKhoan_DTO;
public class TaiKhoan_DAO {
	public ArrayList<TaiKhoan_DTO>getListtaikhoan()
	{
		ArrayList<TaiKhoan_DTO> list=new ArrayList<>();
		Connection_DAO conn=new Connection_DAO();
		Connection con=null;
		try
		{
			con=conn.getCon();
			Statement st=con.createStatement();
			String query="SELECT * FROM TaiKhoan";
			ResultSet rs=st.executeQuery(query);
				while(rs.next())
				{
					String mataikhoan=rs.getString(1);
					String user=rs.getString(2);
					String pass=rs.getString(3);
					String quyen=rs.getString(4);
					String manv=rs.getString(5);
					
					list.add(new TaiKhoan_DTO(mataikhoan,user,pass,quyen,manv));
				}
				
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			if(con!=null)
			{
				conn.Closeconnection(con);
			}
		}
		return list;
	}
	public boolean checktaikhoan(String taikhoan)
	{
		Connection_DAO conn=new Connection_DAO();
		Connection con=null;
		try
		{
			con=conn.getCon();
			String query="SELECT * FROM TaiKhoan WHERE ten_dang_nhap=?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, taikhoan);
			ResultSet rs=ps.executeQuery();
			return rs.next();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(con!=null)
			{
				conn.Closeconnection(con);
			}
		}
		return false;
	}
	public boolean checkmatkhau(String taikhoan,String matkhau)
	{
		Connection_DAO conn=new Connection_DAO();
		Connection con=null;
		try
		{
			con=conn.getCon();
			String query="SELECT * FROM TaiKhoan WHERE ten_dang_nhap=? and mat_khau=?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1,taikhoan);
			ps.setString(2,matkhau);
			ResultSet rs=ps.executeQuery();
			return rs.next();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(con!=null)
			{
				conn.Closeconnection(con);
			}
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
	        return ps.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        conn.Closeconnection(con);
	    }
	}
}
