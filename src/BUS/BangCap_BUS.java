package BUS;

import DAO.BangCap_DAO;
import DTO.BangCap_DTO;
import java.util.List;

public class BangCap_BUS {
    private BangCap_DAO dao = new BangCap_DAO();

    public List<BangCap_DTO> layDanhSachBangCap() {
        return dao.layDanhSachBangCap();
    }

    public List<BangCap_DTO> layDanhSachBangCapTheoNV(String maNVien) {
        if (maNVien == null || maNVien.trim().isEmpty()) {
            return null;
        }
        return dao.layDanhSachBangCapTheoNV(maNVien);
    }

    public boolean themBangCap(BangCap_DTO bc) {
        if (bc.getTenBC() == null || bc.getTenBC().trim().isEmpty()) {
            return false; 
        }
        return dao.insertBangCap(bc);
    }

    public boolean suaBangCap(BangCap_DTO bc) {
        if (bc.getMaBC() == null || bc.getMaBC().trim().isEmpty() || 
            bc.getTenBC() == null || bc.getTenBC().trim().isEmpty()) {
            return false;
        }
        return dao.updateBangCap(bc);
    }

    public boolean xoaBangCap(String maBC) {
        if (maBC == null || maBC.trim().isEmpty()) {
            return false;
        }
        return dao.deleteBangCap(maBC);
    }

    public String taoMaMoi() {
        return dao.getNewestMaBC();
    }
    
    public List<BangCap_DTO> timkiem(String tukhoa){
        return dao.timKiemBangCap(tukhoa);
    }
}