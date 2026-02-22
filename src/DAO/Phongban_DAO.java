package DAO;
import DTO.Phongban_DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DAO.Connection_DAO;
public class Phongban_DAO {
	public ArrayList<Phongban_DTO>getList()
	{
		ArrayList<Phongban_DTO> arr=new ArrayList<>();
		Connection_DAO conn=new Connection_DAO();
		Connection con;
		try
		{
			con=conn.getCon();
			Statement st=con.createStatement();
			String query="select * from PhongBan";
			ResultSet rs=st.executeQuery(query);
			while(rs.next())
			{
				String mapb=rs.getString("ma_pb");
				String tenpb=rs.getString("ten_pb");
				String sdt=rs.getString("sdt_phong");
				String diachi=rs.getString("dia_chi");
				String mabp=rs.getString("ma_bp");
				String matp="";
				if(rs.getString("ma_tp")==null)
				{
					 matp="Đang tuyển";
				}
				else
				{
					 matp=rs.getString("ma_tp");
				}
				arr.add(new Phongban_DTO(mapb,tenpb,diachi,sdt,mabp,matp));
				
			}
			conn.Closeconnection(con);
			return arr;
			
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public String gettentuma(String ma)
	{
		Connection_DAO conn=new Connection_DAO();
		Connection con;
		try
		{
			con=conn.getCon();
			
			String query="select * from NhanVien nv where nv.ma_nv=?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1,ma);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				return rs.getString("ho_ten");
			}
			conn.Closeconnection(con);
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
		
	}
	public int getsoluong(String maphongban) {
	    Connection_DAO conn = new Connection_DAO();
	    Connection con = null;
	    int count = 0;
	    try {
	        con = conn.getCon();
	        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM NhanVien WHERE ma_pb = ?");
	        ps.setString(1, maphongban);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) conn.Closeconnection(con);
	    }
	    return count;
	}
	public boolean insertPhongban(Phongban_DTO x) {
	    Connection_DAO conn = new Connection_DAO();
	    Connection con = null;
	    try {
	        con = conn.getCon();
	        // Sửa query: thêm đầy đủ 6 dấu ? và đóng ngoặc
	        String sql = "INSERT INTO PhongBan (ma_pb, ten_pb, dia_chi, sdt_phong, ma_bp, ma_tp) VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement ps = con.prepareStatement(sql);
	        
	        ps.setString(1, x.getMaphongban());
	        ps.setString(2, x.getTenphongban());
	        ps.setString(3, x.getDiachi());
	        ps.setString(4, x.getSdt());
	        ps.setString(5, x.getMabophan());
	        
	        
	        if (x.getMatruongphong() == null || x.getMatruongphong().equals("Đang tuyển") || x.getMatruongphong().isEmpty()) {
	            ps.setString(6, null);
	        } else {
	            ps.setString(6, x.getMatruongphong());
	        }

	        int result = ps.executeUpdate(); // THỰC THI LỆNH
	        return result > 0;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (con != null) conn.Closeconnection(con);
	    }
	}
	public boolean checkMaPhongBan(String mapb) {
	    Connection_DAO conn = new Connection_DAO();
	    Connection con = null;
	    try {
	        con = conn.getCon();
	        // Truy vấn đếm số lượng bản ghi có mã trùng với tham số truyền vào
	        String query = "SELECT COUNT(*) FROM PhongBan WHERE ma_pb = ?";
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1, mapb);
	        
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            // Nếu kết quả > 0 nghĩa là mã đã tồn tại
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) conn.Closeconnection(con);
	    }
	    return false;
	}
	public ArrayList<String> getListTenMaBoPhan() {
	    ArrayList<String> list = new ArrayList<>();
	    Connection_DAO conn = new Connection_DAO();
	    Connection con = null;
	    try {
	        con = conn.getCon();
	        String query = "SELECT ma_bp, ten_bp FROM BoPhan"; // Giả sử bảng là BoPhan và cột là ma_bp
	        Statement st = con.createStatement();
	        ResultSet rs = st.executeQuery(query);
	        while (rs.next()) {
	            list.add(rs.getString("ma_bp") + " - " + rs.getString("ten_bp"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) conn.Closeconnection(con);
	    }
	    return list;
	}
	public boolean updatePhongban(Phongban_DTO x)
	{
		Connection_DAO conn=new Connection_DAO();
		Connection con=null;
		try
		{
			con=conn.getCon();
			String query="UPDATE PhongBan SET ten_pb=?,dia_chi=?,sdt_phong=?,ma_bp=?,ma_tp=? WHERE ma_pb=?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, x.getTenphongban());
			ps.setString(2, x.getDiachi());
			ps.setString(3, x.getSdt());
			ps.setString(4, x.getMabophan());
			if(x.getMatruongphong().equals("Đang tuyển")||x.getMatruongphong()==null||x.getMatruongphong().isEmpty())
			{
				ps.setString(5,null	);
			}
			else
			{
				ps.setString(5, x.getMatruongphong());
			}
			ps.setString(6,x.getMaphongban());
			int result=ps.executeUpdate();
			return result>0;
		}catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		finally{
			if(con!=null)
			{
				conn.Closeconnection(con);
			}
			
		}
	}
}
