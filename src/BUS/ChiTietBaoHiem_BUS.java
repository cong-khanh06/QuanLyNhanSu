package BUS;

import DAO.ChiTietBaoHiem_DAO;
import DTO.ChiTietBaoHiem_DTO;
import DTO.LoaiBaoHiem_DTO;
import java.util.List;

public class ChiTietBaoHiem_BUS {
    private ChiTietBaoHiem_DAO dao = new ChiTietBaoHiem_DAO();

    // Các hàm dành cho Dialog (Chi tiết bảo hiểm của 1 người)
    public List<LoaiBaoHiem_DTO> layTatCaLoaiBaoHiem() {
        return dao.layTatCaLoaiBaoHiem();
    }

    public List<ChiTietBaoHiem_DTO> layBaoHiemTheoNhanVien(String maNV) {
        return dao.layBaoHiemTheoNhanVien(maNV);
    }

    public boolean themChiTietBaoHiem(ChiTietBaoHiem_DTO dto) {
        return dao.themChiTietBaoHiem(dto);
    }

    public boolean xoaChiTietBaoHiem(String maCTBH) {
        return dao.xoaChiTietBaoHiem(maCTBH);
    }
    
    public String taoMaMoi() {
        return dao.taoMaMoi();
    }

    // Các hàm dành cho GUI Chính (Danh sách Nhân viên và Số lượng BH)
    public List<ChiTietBaoHiem_DTO> layDanhSachNhanVienBaoHiem() {
        return dao.layDanhSachNhanVienBaoHiem();
    }

    public List<ChiTietBaoHiem_DTO> timKiemNhanVienBaoHiem(String tuKhoa) {
        if (tuKhoa == null) tuKhoa = "";
        return dao.timKiemNhanVienBaoHiem(tuKhoa.trim());
    }
}