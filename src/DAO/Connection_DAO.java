package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
public class Connection_DAO {

	public Connection getCon()
	{
		Connection con=null;
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			try
			{
				String url="jdbc:sqlserver://localhost:1433;DatabaseName=quanlynhansu;encrypt=true;trustServerCertificate=true";
				String user="sa";
				String pass="123";
				con=DriverManager.getConnection(url,user,pass);
				
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
			return con;
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public void Closeconnection(Connection con)
	{
		try
		{
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
