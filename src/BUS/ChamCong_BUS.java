package BUS;

import DAO.ChamCong_DAO;
import DAO.ChamCongChiTiet_DAO;
import DTO.ChamCongChiTiet_DTO;
import DTO.ChamCong_DTO;
import DTO.NhanVien_DTO;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChamCong_BUS {

    private ChamCong_DAO dao = new ChamCong_DAO();
    private ChamCongChiTiet_DAO ctDao = new ChamCongChiTiet_DAO();

    public List<ChamCong_DTO> getDanhSachChamCong(int thang, int nam, String phong, String gioiTinh, String keyword) {
        return dao.getDanhSachChamCong(thang, nam, phong, gioiTinh, keyword);
    }

    public ArrayList<NhanVien_DTO> getDanhSachNhanVien() {
        return dao.getDanhSachNhanVien();
    }

    public List<String> getDanhSachPhong() {
        return dao.getDanhSachPhong();
    }

    private String taoMaCC(String maNV, int thang, int nam) {
        return "CC" + nam + String.format("%02d", thang) + "_" + maNV;
    }

    public List<ChamCongChiTiet_DTO> getChiTietChamCong(String maNV, int thang, int nam) {
        String maCC = taoMaCC(maNV, thang, nam);
        return ctDao.getByMaChamCong(maCC);
    }

    public boolean luuChamCong(String maNV, int thang, int nam, int gio,
            Set<Integer> nghi, Set<Integer> tre, Set<Integer> tangca) {

        int soNgayThang = YearMonth.of(nam, thang).lengthOfMonth();

        int soNgayLam = soNgayThang - nghi.size();
        int soGioTre = tre.size() * gio;
        int soGioTangCa = tangca.size() * gio;
        int soNgayTre = tre.size();
        int soNgayTangCa = tangca.size();

        String maCC = taoMaCC(maNV, thang, nam);

        ChamCong_DTO cc = new ChamCong_DTO(maCC, maNV, thang, nam, soNgayLam, soGioTre, soGioTangCa);
        cc.setSoNgayTre(soNgayTre);
        cc.setSoNgayTangCa(soNgayTangCa);

        if (!dao.insertOrUpdate(cc)) {
            return false;
        }

        ctDao.deleteByMaChamCong(maCC);

        ArrayList<ChamCongChiTiet_DTO> list = new ArrayList<>();
        for (int d : nghi)
            list.add(new ChamCongChiTiet_DTO(maCC, d, "Nghỉ", 0));
        for (int d : tre)
            list.add(new ChamCongChiTiet_DTO(maCC, d, "Trễ", gio));
        for (int d : tangca)
            list.add(new ChamCongChiTiet_DTO(maCC, d, "Tăng ca", gio));

        return ctDao.insertList(list);
    }

    public int[] getThongKeChamCong(String maCC) {
        return ctDao.getThongKeChamCong(maCC);
    }
    public boolean xoaChamCong(String maNV, int thang, int nam) {
        String maCC = taoMaCC(maNV, thang, nam);
        
        ctDao.deleteByMaChamCong(maCC);
        
        
        return true; 
    }
    public List<ChamCong_DTO> getDanhSachChamCongSVDangNhap(int thang, int nam, String manv) {
        return ctDao.getDanhSachChamCongSVDangNhap(thang, nam, manv);
    }
}