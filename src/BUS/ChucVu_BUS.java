package BUS;

import DAO.ChucVu_DAO;
import DTO.ChucVu_DTO;
import DTO.PhuCap_DTO; // Import thêm PhuCap_DTO
import java.util.List;

public class ChucVu_BUS {
    private ChucVu_DAO dao = new ChucVu_DAO();

    // 1. Lấy toàn bộ danh sách chức vụ
    public List<ChucVu_DTO> layDanhSachChucVu() {
        return dao.layDanhSachChucVu();
    }

    // 2. Tìm kiếm chức vụ
    public List<ChucVu_DTO> timKiemChucVu(String tuKhoa) {
        if (tuKhoa == null) tuKhoa = "";
        return dao.timKiemChucVu(tuKhoa.trim());
    }

    // 3. Thêm chức vụ
    public boolean themChucVu(ChucVu_DTO cv) {
        if (cv.getTenCV() == null || cv.getTenCV().trim().isEmpty()) {
            return false;
        }
        return dao.insertChucVu(cv);
    }

    // 4. Sửa chức vụ
    public boolean suaChucVu(ChucVu_DTO cv) {
        if (cv.getMaCV() == null || cv.getMaCV().trim().isEmpty() ||
            cv.getTenCV() == null || cv.getTenCV().trim().isEmpty()) {
            return false;
        }
        return dao.updateChucVu(cv);
    }

    // 5. Xóa chức vụ
    public boolean xoaChucVu(String maCV) {
        if (maCV == null || maCV.trim().isEmpty()) {
            return false;
        }
        return dao.deleteChucVu(maCV);
    }

    // 6. Tạo mã tự động
    public String taoMaMoi() {
        return dao.getNewestMaCV();
    }

    // =========================================================================
    // 7. LẤY DANH SÁCH PHỤ CẤP CỦA 1 CHỨC VỤ (PHỤC VỤ CHO BẢNG DƯỚI)
    // =========================================================================
    public List<PhuCap_DTO> layPhuCapTheoChucVu(String maCV) {
        if (maCV == null || maCV.trim().isEmpty()) {
            return null; // Trả về null nếu mã CV không hợp lệ
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
}