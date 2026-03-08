package BUS;

import DAO.DuAn_DAO;
import DTO.DuAn_DTO;
import java.util.List;

public class DuAn_BUS {
    private DuAn_DAO dao = new DuAn_DAO();

    public List<DuAn_DTO> layDanhSachDuAn() {
        return dao.layDanhSachDuAn();
    }

    public boolean themDuAn(DuAn_DTO da) {
        
        if(da.getTenDuAn() == null || da.getTenDuAn().trim().isEmpty()) {
            return false;
        }
        return dao.insertDuAn(da);
    }

    public boolean xoaDuAn(String maDA) {
        return dao.deleteDuAn(maDA);
    }

    public boolean suaDuAn(DuAn_DTO da) {
        return dao.updateDuAn(da);
    }

    public String taoMaMoi() {
        return dao.getNewestMaDA();
    }

    public List<DuAn_DTO> timKiemDuAn(String tuKhoa) {
        return dao.timKiemDuAn(tuKhoa);
    }
    
    public int soLuongDuAnDangThucHien(){
        return dao.soLuongDuAnDangThucHien();
    }
}