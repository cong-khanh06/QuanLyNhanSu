package BUS;

import DAO.ChucVu_DAO;
import DTO.ChucVu_DTO;
import DTO.PhuCap_DTO; 
import java.util.List;

public class ChucVu_BUS {
    private ChucVu_DAO dao = new ChucVu_DAO();

    public List<ChucVu_DTO> layDanhSachChucVu() {
        return dao.layDanhSachChucVu();
    }

    public List<ChucVu_DTO> timKiemChucVu(String tuKhoa) {
        if (tuKhoa == null) tuKhoa = "";
        return dao.timKiemChucVu(tuKhoa.trim());
    }

    public boolean themChucVu(ChucVu_DTO cv) {
        if (cv.getTenCV() == null || cv.getTenCV().trim().isEmpty()) {
            return false;
        }
        return dao.insertChucVu(cv);
    }

    public boolean suaChucVu(ChucVu_DTO cv) {
        if (cv.getMaCV() == null || cv.getMaCV().trim().isEmpty() ||
            cv.getTenCV() == null || cv.getTenCV().trim().isEmpty()) {
            return false;
        }
        return dao.updateChucVu(cv);
    }

    public boolean xoaChucVu(String maCV) {
        if (maCV == null || maCV.trim().isEmpty()) {
            return false;
        }
        return dao.deleteChucVu(maCV);
    }

    public String taoMaMoi() {
        return dao.getNewestMaCV();
    }

    public List<PhuCap_DTO> layPhuCapTheoChucVu(String maCV) {
        if (maCV == null || maCV.trim().isEmpty()) {
            return null;
        }
        return dao.layPhuCapTheoChucVu(maCV);
    }
    
    public List<PhuCap_DTO> layDanhSachTatCaPhuCap() {
        return dao.layDanhSachTatCaPhuCap();
    }

    public boolean themPhuCapChoChucVu(String maCV, String maPC) {
        return dao.themPhuCapChoChucVu(maCV, maPC);
    }

    public boolean xoaPhuCapKhoiChucVu(String maCV, String maPC) {
        return dao.xoaPhuCapKhoiChucVu(maCV, maPC);
    }
    public List<ChucVu_DTO> layChucVuTheoNV(String maNV) {
        return dao.layChucVuTheoNV(maNV);
    }

    public double getTongPhuCapCuaNhanVien(String manv){
        return dao.layTongPhuCapCuaNhanVien(manv);
    }
}