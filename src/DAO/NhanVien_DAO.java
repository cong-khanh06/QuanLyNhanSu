
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import DTO.NhanVien_DTO;
import DTO.Phongban_DTO;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import DTO.ChucVu_DTO;

public class NhanVien_DAO extends Connection_DAO{
    Connection con=getCon();
    Statement stmt=getStmt();
    
    public NhanVien_DAO(){
        super();
    }
    
    
    public List<NhanVien_DTO> layDanhSachNV(){
        List<NhanVien_DTO> listResult=new ArrayList<>();
        
        try {
            String sqlNV="SELECT * FROM NhanVien ORDER BY CAST(SUBSTRING(ma_nv, 3, LEN(ma_nv)) AS INT) ASC";
            ResultSet rsNV=stmt.executeQuery(sqlNV);

            while(rsNV.next()){
                String maNV=rsNV.getString("ma_nv");
                String hoTen=rsNV.getString("ho_ten");
                String gioiTinh=rsNV.getString("gioi_tinh");
                Date sqlNgaySinh=rsNV.getDate("ngay_sinh");
                LocalDate ngaySinh=(sqlNgaySinh != null) ? sqlNgaySinh.toLocalDate():null;
                String diaChi=rsNV.getString("dia_chi");
                String cccd=rsNV.getString("cccd");
                String email=rsNV.getString("email");
                String sdt=rsNV.getString("sdt");
                Date sqlNgayVaoLam=rsNV.getDate("ngay_vao_lam");
                LocalDate ngayvaolam=(sqlNgayVaoLam !=null) ? sqlNgayVaoLam.toLocalDate():null;
                String maPB=rsNV.getString("ma_pb");
                String maCV=rsNV.getString("ma_cv");
                String trangThai=rsNV.getString("trang_thai");
                
                NhanVien_DTO.GioiTinh enumGioiTinh = null;
                NhanVien_DTO.TrangThaiNhanVien enumTrangThai = null;
                
                if(gioiTinh != null){
                    try {
                        enumGioiTinh = NhanVien_DTO.GioiTinh.valueOf(gioiTinh.trim());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Lỗi parse giới tính: " + e.getMessage());
                    }
                }

                if(trangThai != null){
                    try {
                        enumTrangThai = NhanVien_DTO.TrangThaiNhanVien.valueOf(trangThai.trim());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Lỗi parse trạng thái: " + e.getMessage());
                    }
                }
                
                   String avatar=rsNV.getString("avatar");
                   NhanVien_DTO rowData=new NhanVien_DTO(maNV,hoTen,ngaySinh,enumGioiTinh,diaChi,sdt,email,cccd,ngayvaolam,enumTrangThai,maPB,maCV,avatar);
                   listResult.add(rowData);
                }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listResult;
    }
    
    public String getCurrentMaNV(){
        String maxMaNV = null;
        try {
            String sqlMaNV="SELECT TOP 1 ma_nv FROM NhanVien ORDER BY ma_nv DESC";
            ResultSet rsNV=stmt.executeQuery(sqlMaNV);
            
            
            if(rsNV.next()){
                maxMaNV=rsNV.getString("ma_nv");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return maxMaNV;
    }
    
    public String getNewestMaNV(){
        String sql = "SELECT TOP 1 ma_nv FROM NhanVien ORDER BY CAST(SUBSTRING(ma_nv, 3, LEN(ma_nv)) AS INT) DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            if (rs.next()) {
                String lastID = rs.getString("ma_nv"); 
                String numberPart = lastID.substring(2);
                int number = Integer.parseInt(numberPart);
                number++; 
                
                return String.format("NV%03d", number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NV001";
    }
    
    public static NhanVien_DTO getNhanVienById(String maNV) {

            NhanVien_DTO nv = null;

            try {
                String sql = "SELECT * FROM NhanVien WHERE ma_nv = ?";
                Connection con = new Connection_DAO().getCon();

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, maNV);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    nv = new NhanVien_DTO();

                    nv.setMaNV(rs.getString("ma_nv"));
                    nv.setHoTen(rs.getString("ho_ten"));
                    if (rs.getString("gioi_tinh") != null) {
                        nv.setGioiTinh(NhanVien_DTO.GioiTinh.valueOf(rs.getString("gioi_tinh").trim()));
                    }

                    java.sql.Date ns = rs.getDate("ngay_sinh");
                    if (ns != null) nv.setNgaySinh(ns.toLocalDate());

                    nv.setDiaChi(rs.getString("dia_chi"));
                    nv.setSdt(rs.getString("sdt"));
                    nv.setEmail(rs.getString("email"));
                    nv.setCccd(rs.getString("cccd"));

                    java.sql.Date nvl = rs.getDate("ngay_vao_lam");
                    if (nvl != null) nv.setNgayVaoLam(nvl.toLocalDate());

                    nv.setMaPB(rs.getString("ma_pb"));
                    nv.setMaCV(rs.getString("ma_cv"));
                    if (rs.getString("trang_thai") != null) {
                        nv.setTrangThai(NhanVien_DTO.TrangThaiNhanVien.valueOf(rs.getString("trang_thai").trim()));
                    }
                    nv.setAvatar(rs.getString("avatar"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return nv;
        }
    
    public boolean DeleteNhanVien(String maNV){
        boolean result=true;
        try {
            String checkSql="SELECT " +
                    "(SELECT COUNT(*) FROM BangLuong WHERE ma_nv = ?) + " +
                    "(SELECT COUNT(*) FROM HopDong WHERE ma_nv = ?) + " +
                    "(SELECT COUNT(*) FROM BangChamCong WHERE ma_nv = ?) AS Total";
            
            PreparedStatement ps=con.prepareStatement(checkSql);
            ps.setString(1, maNV);
            ps.setString(2,maNV);
            ps.setString(3,maNV);
            ResultSet rs=ps.executeQuery();
            
            int totalConstraints = 0;
            if (rs.next()) {
                totalConstraints = rs.getInt("Total");
            }
            
            if(totalConstraints >0){
                String updateSql="UPDATE NhanVien SET trang_thai = 'NghiViec' WHERE ma_nv = ?";
                PreparedStatement psUpdate=con.prepareStatement(updateSql);
                psUpdate.setString(1,maNV);
                if(psUpdate.executeUpdate()>0){
                    return result;
                }
            }
            else{
                con.setAutoCommit(false);
                try {
                    String[] tablesToDelete = {"TaiKhoan", "BangCap", "ChiTietBaoHiem", "PhanCongDuAn"};
                    for (String table : tablesToDelete) {
                        String delSubSql = "DELETE FROM " + table + " WHERE ma_nv = ?";
                        PreparedStatement psDelSub = con.prepareStatement(delSubSql);
                        psDelSub.setString(1, maNV);
                        psDelSub.executeUpdate();
                    }
                    
                    String updatePhongBan = "UPDATE PhongBan SET ma_tp = NULL WHERE ma_tp = ?";
                    PreparedStatement psUpPB = con.prepareStatement(updatePhongBan);
                    psUpPB.setString(1, maNV);
                    psUpPB.executeUpdate();
                    
                    String delNVSql = "DELETE FROM NhanVien WHERE ma_nv = ?";
                    PreparedStatement psDelNV = con.prepareStatement(delNVSql);
                    psDelNV.setString(1, maNV);
                    psDelNV.executeUpdate();
                    
                    con.commit();
                    result=false;
                } catch (Exception e) {
                    con.rollback();
                    e.printStackTrace();
                }
                finally{
                    con.setAutoCommit(true);
                }
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean updateNhanVien(NhanVien_DTO nv) {
        String sql = "UPDATE NhanVien SET "
        + "ho_ten = ?, "
        + "gioi_tinh = ?, "
        + "dia_chi = ?, "
        + "sdt = ?, "
        + "email = ?, "
        + "cccd = ?, "
        + "ngay_sinh = ?, "
        + "ngay_vao_lam = ?, "
        + "ma_pb = ?, "
        + "ma_cv = ?, "
        + "trang_thai = ?, "
        + "avatar = ? "
        + "WHERE ma_nv = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getGioiTinh() != null ? nv.getGioiTinh().name() : null);
            ps.setString(3, nv.getDiaChi());
            ps.setString(4, nv.getSdt());
            ps.setString(5, nv.getEmail());
            ps.setString(6, nv.getCccd());

            ps.setDate(7, nv.getNgaySinh() != null ? java.sql.Date.valueOf(nv.getNgaySinh()) : null);
            ps.setDate(8, nv.getNgayVaoLam() != null ? java.sql.Date.valueOf(nv.getNgayVaoLam()) : null);

            ps.setString(9, nv.getMaPB());
            ps.setString(10, nv.getMaCV());
            ps.setString(11, nv.getTrangThai() != null ? nv.getTrangThai().name() : null);
            ps.setString(12, nv.getAvatar());
            ps.setString(13, nv.getMaNV());
            

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }
    public List<Phongban_DTO> layDanhSachPB() {
            List<Phongban_DTO> list = new ArrayList<>();
            String sql = "SELECT * FROM PhongBan";
            
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(new Phongban_DTO(
                        rs.getString("ma_pb"),
                        rs.getString("ten_pb"),
                        rs.getString("dia_chi"),
                        rs.getString("sdt_phong"), 
                        rs.getString("ma_bp"),
                        rs.getString("ma_tp")
                    ));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }
    
        public boolean insertNhanVien(NhanVien_DTO nv) {
        String sql = "INSERT INTO NhanVien (ma_nv, ho_ten, ngay_sinh, gioi_tinh,  dia_chi, sdt, email, cccd, ngay_vao_lam, ma_pb, ma_cv, trang_thai, avatar) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getHoTen());
            ps.setDate(3, nv.getNgaySinh() != null ? java.sql.Date.valueOf(nv.getNgaySinh()) : null);
            ps.setString(4, nv.getGioiTinh() != null ? nv.getGioiTinh().name() : null);
            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getSdt());
            ps.setString(7, nv.getEmail());
            ps.setString(8, nv.getCccd());
            ps.setDate(9, nv.getNgayVaoLam() != null ? java.sql.Date.valueOf(nv.getNgayVaoLam()) : null);
            ps.setString(10, nv.getMaPB());
            ps.setString(11, nv.getMaCV());
            ps.setString(12, nv.getTrangThai() != null ? nv.getTrangThai().name() : null);
            ps.setString(13, nv.getAvatar());

            return ps.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        
    public List<ChucVu_DTO> layDanhSachCV() {
        List<ChucVu_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ChucVu";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ChucVu_DTO(rs.getString("ma_cv"), rs.getString("ten_cv"),rs.getString("mo_ta")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    public List<NhanVien_DTO> timKiemNhanVien(String tuKhoa, String gioiTinh, String maPB, String maCV) {
        List<NhanVien_DTO> listResult = new ArrayList<>();

        
        StringBuilder sql = new StringBuilder(
            "SELECT * FROM NhanVien WHERE (ma_nv LIKE ? OR ho_ten LIKE ? OR sdt LIKE ?)"
        );

        
        if (gioiTinh != null && !gioiTinh.equalsIgnoreCase("Tat ca")) {
            sql.append(" AND gioi_tinh = ?");
        }

        
        if (maPB != null && !maPB.equalsIgnoreCase("Tat ca") && !maPB.isEmpty()) {
            sql.append(" AND ma_pb = ?");
        }
        
        if (maCV != null && !maCV.equalsIgnoreCase("Tat ca") && !maCV.isEmpty()) {
            sql.append(" AND ma_cv = ?");
        }
        
        sql.append(" ORDER BY CAST(SUBSTRING(ma_nv, 3, LEN(ma_nv)) AS INT) ASC");
        
        try (Connection conn = getCon();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            String searchPattern = "%" + tuKhoa + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            int paramIndex = 4;
            if (gioiTinh != null && !gioiTinh.equalsIgnoreCase("Tat ca")) {
                ps.setString(paramIndex++, gioiTinh);
            }
            if (maPB != null && !maPB.equalsIgnoreCase("Tat ca") && !maPB.isEmpty()) {
                ps.setString(paramIndex++, maPB);
            }
            if (maCV != null && !maCV.equalsIgnoreCase("Tat ca") && !maCV.isEmpty()) {
                ps.setString(paramIndex++, maCV);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVien_DTO nv = new NhanVien_DTO();
                nv.setMaNV(rs.getString("ma_nv"));
                nv.setHoTen(rs.getString("ho_ten"));
                if (rs.getString("gioi_tinh") != null) {
                    nv.setGioiTinh(NhanVien_DTO.GioiTinh.valueOf(rs.getString("gioi_tinh").trim()));
                }
                java.sql.Date ns = rs.getDate("ngay_sinh");
                if (ns != null) nv.setNgaySinh(ns.toLocalDate());

                nv.setDiaChi(rs.getString("dia_chi"));
                nv.setSdt(rs.getString("sdt"));
                nv.setEmail(rs.getString("email"));
                nv.setCccd(rs.getString("cccd"));

                java.sql.Date nvl = rs.getDate("ngay_vao_lam");
                if (nvl != null) nv.setNgayVaoLam(nvl.toLocalDate());

                nv.setMaPB(rs.getString("ma_pb"));
                nv.setMaCV(rs.getString("ma_cv"));
                if (rs.getString("trang_thai") != null) {
                    nv.setTrangThai(NhanVien_DTO.TrangThaiNhanVien.valueOf(rs.getString("trang_thai").trim()));
                }
                nv.setAvatar(rs.getString("avatar"));

                listResult.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listResult;
    }
    
    public int soLuongNhanVien(){
        String sql="SELECT COUNT(ma_nv) FROM NhanVien";
        int count=0;
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            
            if(rs.next()){
                count=rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    
}
