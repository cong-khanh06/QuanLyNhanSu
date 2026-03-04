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

//Thêm
    public String insert(HopDong_DTO hd) {
        if (hd.getMaHD() == null || hd.getMaHD().trim().isEmpty()) return "Mã hợp đồng không được để trống!";
        if (hd.getMaNV() == null || hd.getMaNV().trim().isEmpty()) return "Mã nhân viên không được để trống!";
        if (hd.getLoaiHopDong() == null || hd.getLoaiHopDong().trim().isEmpty()) return "Loại hợp đồng không được để trống!";


        if (hd.getMucLuongCoBan() <= 0) return "Mức lương phải lớn hơn 0!";

        if (hd.getNgayBatDau() == null || hd.getNgayKetThuc() == null || hd.getNgayKy() == null) {
            return "Vui lòng chọn đầy đủ các loại ngày!";
        }
        if (hd.getNgayBatDau().after(hd.getNgayKetThuc())) {
            return "Ngày bắt đầu phải trước ngày kết thúc!";
        }
        if (hd.getNgayKy().after(hd.getNgayBatDau())) {
            return "Ngày ký phải trước hoặc bằng ngày bắt đầu!";
        }

        if (dao.checkMaHopDongTonTai(hd.getMaHD())) {
            return "Mã hợp đồng này đã tồn tại trong hệ thống!";
        }
        if (!dao.checkNhanVienTonTai(hd.getMaNV())) {
            return "Nhân viên (mã " + hd.getMaNV() + ") không tồn tại!";
        }

        if (dao.insertHopDong(hd)) {
            return "Thêm hợp đồng thành công!";
        } else {
            return "Lỗi: Không thể lưu vào cơ sở dữ liệu.";
        }
    }

//Sửa
    public String update(HopDong_DTO hd) {
        if (hd.getMaHD().trim().isEmpty()) return "Mã hợp đồng không được để trống!";
        if (hd.getMucLuongCoBan() <= 0) return "Lương phải lớn hơn 0!";

        if (dao.updateHopDong(hd)) {
            return "Cập nhật hợp đồng thành công!";
        } else {
            return "Lỗi: Cập nhật thất bại.";
        }
    }

// xoá 
    public String delete(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) return "Vui lòng chọn mã hợp đồng để xóa!";
        
        if (dao.deleteHopDong(maHD)) {
            return "Xóa hợp đồng thành công!";
        } else {
            return "Lỗi: Không thể xóa dữ liệu.";
        }
    }
}