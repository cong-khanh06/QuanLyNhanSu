package BUS;

import DAO.HopDong_DAO;
import DTO.HopDong_DTO;
import java.util.ArrayList;

public class HopDong_BUS {
    private HopDong_DAO dao = new HopDong_DAO();

    public ArrayList<HopDong_DTO> getAll() {
        return dao.getList();
    }

    public ArrayList<HopDong_DTO> search(String keyword) {
        return dao.search(keyword == null ? "" : keyword.trim());
    }

    private String validate(HopDong_DTO hd) {
        if (hd.getMaHD() == null || hd.getMaHD().trim().isEmpty()) return "Mã hợp đồng không được để trống!";
        if (hd.getMaNV() == null || hd.getMaNV().trim().isEmpty()) return "Mã nhân viên không được để trống!";
        if (hd.getLoaiHopDong() == null || hd.getLoaiHopDong().trim().isEmpty()) return "Loại hợp đồng không được để trống!";
        
        if (hd.getMucLuongCoBan() <= 0) return "Mức lương phải lớn hơn 0!";
        if (hd.getLanKy() <= 0) return "Số lần ký phải lớn hơn hoặc bằng 1!";

        if (hd.getNgayBatDau() == null || hd.getNgayKy() == null) {
            return "Vui lòng chọn đầy đủ Ngày bắt đầu và Ngày ký!";
        }
        
        if (hd.getNgayKetThuc() != null && hd.getNgayBatDau().after(hd.getNgayKetThuc())) {
            return "Ngày bắt đầu phải trước ngày kết thúc!";
        }
        
        if (hd.getNgayKy().after(hd.getNgayBatDau())) {
            return "Ngày ký phải trước hoặc bằng ngày bắt đầu!";
        }

        return null; 
    }

    public String insert(HopDong_DTO hd) {
        String error = validate(hd);
        if (error != null) return error;

        if (dao.checkMaHopDongTonTai(hd.getMaHD())) {
            return "Mã hợp đồng này đã tồn tại !";
        }
        if (!dao.checkNhanVienTonTai(hd.getMaNV())) {
            return "Nhân viên (mã " + hd.getMaNV() + ") không tồn tại!";
        }

        return dao.insertHopDong(hd) ? "Thêm hợp đồng thành công!" : "Lỗi: Không thể lưu.";
    }

    public String update(HopDong_DTO hd) {
        String error = validate(hd);
        if (error != null) return error;

        return dao.updateHopDong(hd) ? "Cập nhật hợp đồng thành công!" : "Lỗi: Cập nhật thất bại.";
    }

    public String delete(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) return "Vui lòng chọn mã hợp đồng để xóa!";
        return dao.deleteHopDong(maHD) ? "Xóa hợp đồng thành công!" : "Lỗi: Không thể xóa dữ liệu.";
    }
}