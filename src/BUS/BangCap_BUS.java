package BUS;

import DAO.BangCap_DAO;
import DTO.BangCap_DTO;
import java.util.List;

public class BangCap_BUS {
    private BangCap_DAO dao = new BangCap_DAO();

    // Lấy toàn bộ danh sách bằng cấp
    public List<BangCap_DTO> layDanhSachBangCap() {
        return dao.layDanhSachBangCap();
    }

    // Lấy danh sách bằng cấp của 1 nhân viên cụ thể
    public List<BangCap_DTO> layDanhSachBangCapTheoNV(String maNVien) {
        if (maNVien == null || maNVien.trim().isEmpty()) {
            return null; // Trả về null hoặc list rỗng nếu mã NV không hợp lệ
        }
        return dao.layDanhSachBangCapTheoNV(maNVien);
    }

    // Thêm bằng cấp mới
    public boolean themBangCap(BangCap_DTO bc) {
        // Kiểm tra dữ liệu đầu vào (Validation)
        if (bc.getTenBC() == null || bc.getTenBC().trim().isEmpty()) {
            return false; // Không cho phép thêm nếu tên bằng cấp trống
        }
        return dao.insertBangCap(bc);
    }

    // Sửa thông tin bằng cấp
    public boolean suaBangCap(BangCap_DTO bc) {
        // Kiểm tra dữ liệu đầu vào
        if (bc.getMaBC() == null || bc.getMaBC().trim().isEmpty() || 
            bc.getTenBC() == null || bc.getTenBC().trim().isEmpty()) {
            return false;
        }
        return dao.updateBangCap(bc);
    }

    // Xóa bằng cấp
    public boolean xoaBangCap(String maBC) {
        if (maBC == null || maBC.trim().isEmpty()) {
            return false;
        }
        return dao.deleteBangCap(maBC);
    }

    // Tạo mã bằng cấp tự động
    public String taoMaMoi() {
        return dao.getNewestMaBC();
    }
    
    public List<BangCap_DTO> timkiem(String tukhoa){
        return dao.timKiemBangCap(tukhoa);
    }
}