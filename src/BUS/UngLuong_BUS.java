package BUS;

import DAO.UngLuong_DAO;
import DTO.UngLuong_DTO;
import java.math.BigDecimal;
import java.util.List;

public class UngLuong_BUS {
    private UngLuong_DAO dao = new UngLuong_DAO();

    public List<UngLuong_DTO> layDanhSachUngLuong() {
        return dao.layDanhSachUngLuong();
    }

    public boolean themUngLuong(UngLuong_DTO ul) {
        if (ul.getMaBL() == null || ul.getMaBL().trim().isEmpty()) {
            return false;
        }
        if (ul.getSoTien() == null || ul.getSoTien().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        return dao.insertUngLuong(ul);
    }

    public boolean xoaUngLuong(String maUL) {
        return dao.deleteUngLuong(maUL);
    }

    public boolean suaUngLuong(UngLuong_DTO ul) {
        return dao.updateUngLuong(ul);
    }

    public String taoMaMoi() {
        return dao.getNewestMaUL();
    }

    public List<UngLuong_DTO> timKiemUngLuong(String tuKhoa) {
        return dao.timKiemUngLuong(tuKhoa);
    }
    
    public int soLuongUngLuongTheoTrangThai(String trangThai) {
        return dao.soLuongUngLuongTheoTrangThai(trangThai);
    }
}