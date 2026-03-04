/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.ResultSet;
import BUS.NhanVien_BUS;
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
/**
 *
 * @author khanh
 */
public class NhanVien_DAO extends Connection_DAO{
    Connection con=getCon();
    Statement stmt=getStmt();
    
    public NhanVien_DAO(){
        super();
    }
    
    @SuppressWarnings("empty-statement")
    public List<NhanVien_DTO> layDanhSachNV(){
        List<NhanVien_DTO> listResult=new ArrayList<>();
        
        try {
            String sqlNV="SELECT ma_nv, ho_ten, gioi_tinh, "
                    + "ngay_sinh, dia_chi, sdt, ma_pb, "
                    + "ma_cv, trang_thai FROM NhanVien nv ";
            ResultSet rsNV=stmt.executeQuery(sqlNV);

            while(rsNV.next()){
                String maNV=rsNV.getString("ma_nv");
                String hoTen=rsNV.getString("ho_ten");
                String gioiTinh=rsNV.getString("gioi_tinh");
                Date sqlNgaySinh=rsNV.getDate("ngay_sinh");
                LocalDate ngaySinh=(sqlNgaySinh != null) ? sqlNgaySinh.toLocalDate():null;
                String diaChi=rsNV.getString("dia_chi");
                String sdt=rsNV.getString("sdt");
                String maPB=rsNV.getString("ma_pb");
                String maCV=rsNV.getString("ma_cv");
                String trangThai=rsNV.getString("trang_thai");

                if(gioiTinh != null){
                    try {
                        NhanVien_BUS.GioiTinh gt= NhanVien_BUS.GioiTinh.valueOf(gioiTinh.trim());
                        gioiTinh= gt.getGioiTinhHienThi();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e);
                    }
                }

                if(trangThai != null){
                    try {
                        NhanVien_BUS.TrangThaiNhanVien ttnv=NhanVien_BUS.TrangThaiNhanVien.valueOf(trangThai.trim());
                        trangThai=ttnv.getTrangThaiHienThi();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e);
                    }
                }

                   NhanVien_DTO rowData=new NhanVien_DTO(maNV,hoTen,ngaySinh,gioiTinh,diaChi,sdt,maPB,maCV,trangThai);
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
                String lastID = rs.getString("ma_nv"); // Ví dụ: "HD005"
                // Cắt bỏ 2 chữ cái đầu ("HD"), lấy phần số ("005")
                String numberPart = lastID.substring(2);
                int number = Integer.parseInt(numberPart);
                number++; // Tăng lên 1 thành 6
                
                // Ghép lại với định dạng 3 chữ số (HD006)
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
                    nv.setGioiTinh(rs.getString("gioi_tinh"));

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
                    nv.setTrangThai(rs.getString("trang_thai"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return nv;
        }
//    public boolean deleteNhanVien(String maNV) {
//        String sql = "UPDATE NhanVien SET trang_thai = 'NghiViec' WHERE ma_nv = ?";
//    
//        try {
//            // ... Code chạy câu lệnh PreparedStatement của bạn ...
//            // Nếu executeUpdate() > 0 thì return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
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
        + "trang_thai = ? "
        + "WHERE ma_nv = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getGioiTinh());
            ps.setString(3, nv.getDiaChi());
            ps.setString(4, nv.getSdt());
            ps.setString(5, nv.getEmail());
            ps.setString(6, nv.getCccd());

            ps.setDate(7, java.sql.Date.valueOf(nv.getNgaySinh()));
            ps.setDate(8, java.sql.Date.valueOf(nv.getNgayVaoLam()));

            ps.setString(9, nv.getMaPB());
            ps.setString(10, nv.getMaCV());
            ps.setString(11, nv.getTrangThai());

            ps.setString(12, nv.getMaNV());

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
        String sql = "INSERT INTO NhanVien (ma_nv, ho_ten, gioi_tinh, ngay_sinh, dia_chi, sdt, email, cccd, ngay_vao_lam, ma_pb, ma_cv, trang_thai) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getGioiTinh());

            // Chuyển đổi từ LocalDate sang java.sql.Date
            ps.setDate(4, nv.getNgaySinh() != null ? java.sql.Date.valueOf(nv.getNgaySinh()) : null);

            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getSdt());
            ps.setString(7, nv.getEmail());
            ps.setString(8, nv.getCccd());

            // Chuyển đổi từ LocalDate sang java.sql.Date
            ps.setDate(9, nv.getNgayVaoLam() != null ? java.sql.Date.valueOf(nv.getNgayVaoLam()) : null);

            ps.setString(10, nv.getMaPB());
            ps.setString(11, nv.getMaCV());
            ps.setString(12, nv.getTrangThai());

            return ps.executeUpdate() > 0; // Trả về true nếu chèn thành công ít nhất 1 dòng
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
                list.add(new ChucVu_DTO(rs.getString("ma_cv"), rs.getString("ten_cv"),rs.getString("mo_ta"),rs.getString("ma_pc")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
        public List<NhanVien_DTO> timKiemNhanVien(String tuKhoa, String gioiTinh, String maPB) {
        List<NhanVien_DTO> listResult = new ArrayList<>();

        
        StringBuilder sql = new StringBuilder(
            "SELECT * FROM NhanVien WHERE (ma_nv LIKE ? OR ho_ten LIKE ? OR sdt LIKE ?)"
        );

        // Nếu có lọc theo giới tính (Khác "Tat ca")
        if (gioiTinh != null && !gioiTinh.equalsIgnoreCase("Tat ca")) {
            sql.append(" AND gioi_tinh = ?");
        }

        // Nếu có lọc theo phòng ban (Khác "Tat ca")
        if (maPB != null && !maPB.equalsIgnoreCase("Tat ca") && !maPB.isEmpty()) {
            sql.append(" AND ma_pb = ?");
        }

        try (Connection conn = getCon();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Thêm ký tự % để tìm kiếm gần đúng (chứa từ khóa)
            String searchPattern = "%" + tuKhoa + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            int paramIndex = 4;

            // Gắn giá trị tham số cho Giới tính nếu có
            if (gioiTinh != null && !gioiTinh.equalsIgnoreCase("Tat ca")) {
                ps.setString(paramIndex++, gioiTinh);
            }

            // Gắn giá trị tham số cho Phòng ban nếu có
            if (maPB != null && !maPB.equalsIgnoreCase("Tat ca") && !maPB.isEmpty()) {
                ps.setString(paramIndex++, maPB);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVien_DTO nv = new NhanVien_DTO();
                nv.setMaNV(rs.getString("ma_nv"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setGioiTinh(rs.getString("gioi_tinh"));

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
                nv.setTrangThai(rs.getString("trang_thai"));

                listResult.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listResult;
    }
}
