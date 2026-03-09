package DAO;
import DTO.Phongban_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import DTO.NhanVien_DTO;
import DAO.Connection_DAO;
import org.jfree.data.general.DefaultPieDataset;
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
		Connection con=null;
                String hoTen=null;
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
			
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
                finally{
                    if (con != null) conn.Closeconnection(con);
                }
		return hoTen;
		
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
	public double gettongluong(String mapb)
	{
		Connection_DAO conn=new Connection_DAO();
		Connection con=null;
		double tongluong=0;
		try
		{
			con=conn.getCon();
			String query="SELECT SUM(bl.thuc_lanh) FROM NhanVien nv JOIN BangLuong bl on nv.ma_nv=bl.ma_nv"
					+ " WHERE nv.ma_pb=?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, mapb);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				tongluong=rs.getDouble(1);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			conn.Closeconnection(con);
		}
		return tongluong;
	}
	public boolean insertPhongban(Phongban_DTO x) {
	    Connection_DAO conn = new Connection_DAO();
	    Connection con = null;
	    try {
	        con = conn.getCon();
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
	public ArrayList<NhanVien_DTO> getListNhanVien(String mapb) {
	    Connection_DAO conn = new Connection_DAO();
	    Connection con = null;
	    ArrayList<NhanVien_DTO> listnhanvien = new ArrayList<>();
	    try {
	        con = conn.getCon();
	        String query = "SELECT ma_nv, ho_ten, gioi_tinh, sdt, dia_chi, ngay_vao_lam,avatar from NhanVien WHERE ma_pb=?";
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1, mapb);
	        ResultSet rs = ps.executeQuery();
	        
	        while(rs.next()) {
	            NhanVien_DTO nv = new NhanVien_DTO();
	            nv.setMaNV(rs.getString("ma_nv"));
	            nv.setHoTen(rs.getString("ho_ten"));
	            nv.setSdt(rs.getString("sdt"));
	            nv.setDiaChi(rs.getString("dia_chi"));
	            nv.setAvatar(rs.getString("avatar"));
	            if(rs.getDate("ngay_vao_lam") != null) {
	                nv.setNgayVaoLam(rs.getDate("ngay_vao_lam").toLocalDate());
	            }
                    String strGioiTinh = rs.getString("gioi_tinh");
                    if (strGioiTinh != null) {
                        try {
                            nv.setGioiTinh(NhanVien_DTO.GioiTinh.valueOf(strGioiTinh.trim()));
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                    }
	            listnhanvien.add(nv);
	        }
	        return listnhanvien;
	    } catch(SQLException e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        if(con != null) conn.Closeconnection(con);
	    }
	}
	public boolean DeletePhongban(String mapb)
	{
		Connection_DAO conn=new Connection_DAO();
		Connection con=null;
		try
		{
			con=conn.getCon();
			String query="DELETE From PhongBan WHERE ma_pb=?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, mapb);
			int result =ps.executeUpdate();
			return result>0;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		finally {
			if(con!=null)
			{
				conn.Closeconnection(con);
			}
		}
	}
	public  int getSoLuongNhanVien(String maPhongBan) {
		Connection_DAO conn=new Connection_DAO();
		Connection con=null;
		try {
			con=conn.getCon();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select COUNT(maNhanVien) from NHANVIEN where NHANVIEN.trangThai=1 and maPhong = '"+maPhongBan+"'");
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(con!=null)
			{
				conn.Closeconnection(con);
			}
		}
		
		return 0;
	}


	public DefaultPieDataset getThongKeGioiTinh(String mapb) {
	    DefaultPieDataset dataset = new DefaultPieDataset();
	    Connection_DAO conn = new Connection_DAO();
	    Connection con = null;
	    try {
	        con = conn.getCon();
	        String sql = "SELECT gioi_tinh, COUNT(*) FROM NhanVien WHERE ma_pb = ? GROUP BY gioi_tinh";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, mapb);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            String label = rs.getString(1);
	            if (label == null) label = "Chưa xác định";
	            dataset.setValue(label, rs.getInt(2));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) conn.Closeconnection(con);
	    }
	    return dataset;
	}

	
	public DefaultPieDataset getThongKeChucVu(String mapb) {
	    DefaultPieDataset dataset = new DefaultPieDataset();
	    Connection_DAO conn = new Connection_DAO();
	    Connection con = null;
	    try {
	        con = conn.getCon();
	        String sql = "SELECT cv.ten_cv, COUNT(*) FROM NhanVien nv "
	                   + "JOIN ChucVu cv ON nv.ma_cv = cv.ma_cv Where nv.ma_pb=?"
	                   + " GROUP BY cv.ten_cv";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, mapb);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            dataset.setValue(rs.getString(1), rs.getInt(2));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) conn.Closeconnection(con);
	    }
	    return dataset;
	}

	
	public DefaultPieDataset getThongKeDoTuoi(String mapb) {
	    DefaultPieDataset dataset = new DefaultPieDataset();
	    Connection_DAO conn = new Connection_DAO();
	    Connection con = null;
	    try {
	        con = conn.getCon();
	        // Sử dụng chuỗi SQL rõ ràng, tránh để khoảng trắng lỗi giữa CASE và GROUP BY
	        String sql = "SELECT " +
	                     "  CASE " +
	                     "    WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) <= 25 THEN '16-25' " +
	                     "    WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) BETWEEN 26 AND 40 THEN '26-40' " +
	                     "    WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) BETWEEN 41 AND 55 THEN '41-55' " +
	                     "    ELSE '56-65' " +
	                     "  END AS NhomTuoi, COUNT(*) " +
	                     "FROM NhanVien " +
	                     "WHERE ma_pb = ? " +
	                     "GROUP BY " +
	                     "  CASE " +
	                     "    WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) <= 25 THEN '16-25' " +
	                     "    WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) BETWEEN 26 AND 40 THEN '26-40' " +
	                     "    WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) BETWEEN 41 AND 55 THEN '41-55' " +
	                     "    ELSE '56-65' " +
	                     "  END";
	        
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, mapb);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            dataset.setValue(rs.getString(1), rs.getInt(2));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) conn.Closeconnection(con);
	    }
	    return dataset;
	}
	
	public DefaultPieDataset getThongKeGioiTinhToanCongTy() {
	    DefaultPieDataset dataset = new DefaultPieDataset();
	    Connection_DAO conn = new Connection_DAO();
	    try (Connection con = conn.getCon()) {
	        String sql = "SELECT gioi_tinh, COUNT(*) FROM NhanVien GROUP BY gioi_tinh";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            String label = rs.getString(1) == null ? "Chưa xác định" : rs.getString(1);
	            dataset.setValue(label, rs.getInt(2));
	        }
	    } catch (SQLException e) { e.printStackTrace(); }
	    return dataset;
	}
	public DefaultPieDataset getThongKeChucVuToanCongTy() {
	    DefaultPieDataset dataset = new DefaultPieDataset();
	    Connection_DAO conn = new Connection_DAO();
	    Connection con = null;
	    try {
	        con = conn.getCon();
	        String sql = "SELECT cv.ten_cv, COUNT(*) FROM NhanVien nv "
	                   + "JOIN ChucVu cv ON nv.ma_cv = cv.ma_cv "
	                   + " GROUP BY cv.ten_cv";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            dataset.setValue(rs.getString(1), rs.getInt(2));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) conn.Closeconnection(con);
	    }
	    return dataset;
	}

	
	public DefaultPieDataset getThongKeDoTuoiToanCongTy() {
	    DefaultPieDataset dataset = new DefaultPieDataset();
	    Connection_DAO conn = new Connection_DAO();
	    Connection con = null;
	    try {
	        con = conn.getCon();
	        String sql = "SELECT CASE " +
	                     "WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) <= 25 THEN '16-25' " +
	                     "WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) BETWEEN 26 AND 40 THEN '26-40' " +
	                     "WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) BETWEEN 41 AND 55 THEN '41-55' " +
	                     "ELSE '56-65' END AS NhomTuoi, COUNT(*) " +
	                     "FROM NhanVien nv  " +
	                     "GROUP BY CASE " +
	                     "WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) <= 25 THEN '16-25' " +
	                     "WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) BETWEEN 26 AND 40 THEN '26-40' " +
	                     "WHEN DATEDIFF(YEAR, ngay_sinh, GETDATE()) BETWEEN 41 AND 55 THEN '41-55' " +
	                     "ELSE '56-65' END";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            dataset.setValue(rs.getString(1), rs.getInt(2));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) conn.Closeconnection(con);
	    }
	    return dataset;

	}


        public int soLuongPhongBan(){
            String sql="SELECT COUNT(ma_pb) FROM PhongBan";
            int count=0;
            try {
                Connection_DAO conn=new Connection_DAO();
                Connection con=conn.getCon();
                PreparedStatement ps=con.prepareStatement(sql);
                ResultSet rsPb=ps.executeQuery();
                
                if(rsPb.next()){
                    count=rsPb.getInt(1);
                }
                return count;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return count;
        }
}
