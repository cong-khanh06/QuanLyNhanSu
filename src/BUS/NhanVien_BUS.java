package BUS;

import DAO.NhanVien_DAO;
import DTO.NhanVien_DTO;
import java.util.List;

public class NhanVien_BUS {
    NhanVien_DAO dao = new NhanVien_DAO();
    public static boolean isValidPhone(String sdt) {
        if (sdt == null) return false;
        return sdt.matches("^0(3|5|7|8|9)[0-9]{8}$");
    }

    public static boolean isValidCCCD(String cccd) {
        if (cccd == null) return false;
        return cccd.matches("^[0-9]{12}$");
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$");
    }
    
    
    public String taoMaMoiNhat(){
        return dao.getNewestMaNV();
    }
    
    public boolean themNhanVien(NhanVien_DTO nv) {
        if (!isValidPhone(nv.getSdt())) return false;
        if (!isValidEmail(nv.getEmail())) return false;
        if (!isValidCCCD(nv.getCccd())) return false;

        return dao.insertNhanVien(nv);
    }
    
    public List<NhanVien_DTO> timKiemNhanVien(String tuKhoa, String gioiTinh, String maPB, String maCV) {
        if (tuKhoa == null) {
            tuKhoa = "";
        } else {
            tuKhoa = tuKhoa.trim();
        }

        return dao.timKiemNhanVien(tuKhoa, gioiTinh, maPB, maCV);
    }
    
    public int soLuongNhanVien(){
        return dao.soLuongNhanVien();
    }
    
    public List<NhanVien_DTO> layDanhSachNhanVien(){
        return dao.layDanhSachNV();
    }
}
