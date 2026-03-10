package BUS;

import DAO.ChamCong_DAO;
import DTO.ChamCong_DTO;
import java.util.List;

public class ChamCong_BUS {
    private ChamCong_DAO dao = new ChamCong_DAO();

    public List<ChamCong_DTO> getDanhSachChamCong(int thang, int nam, String phong, String keyword) {
        return dao.getDanhSachChamCong(thang, nam, phong, keyword);
    }
    
    public List<ChamCong_DTO> getDanhSachChamCongSVDangNhap(int thang, int nam, String manv) {
        return dao.getDanhSachChamCong(thang, nam, "", manv);
    }

    public boolean themChamCong(ChamCong_DTO cc) { return dao.insertChamCong(cc); }
    public boolean suaChamCong(ChamCong_DTO cc) { return dao.updateChamCong(cc); }
    public boolean xoaChamCong(String maCC) { return dao.deleteChamCong(maCC); }
    
    public String taoMaMoi() { return dao.getNewestMaCC(); }
    public List<String> getDanhSachPhong() { return dao.getDanhSachPhong(); }
    public List<String> getDanhSachNhanVien() { return dao.getDanhSachNhanVienCombobox(); }
}