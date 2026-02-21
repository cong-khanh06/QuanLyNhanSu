package DAO;
import java.sql.*;
public class Connection_DAO {
    protected Statement stmt;
    protected Connection con;
    public Connection_DAO(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl="jdbc:sqlserver://localhost:1433;DatabaseName=quanlynhansu;encrypt=true;trustServerCertificate=true;";
            String username="sa";
            String password="YourStrong@123";
            con=DriverManager.getConnection(dbUrl,username,password);
            stmt=con.createStatement();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
