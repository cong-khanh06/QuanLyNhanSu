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

    // Hàm bổ trợ tạo mã chấm công thống nhất (Ví dụ: CC202603_NV001)
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

        // Tính toán các thông số tổng hợp
        int soNgayLam = soNgayThang - nghi.size();
        int soGioTre = tre.size() * gio;
        int soGioTangCa = tangca.size() * gio;
        int soNgayTre = tre.size();
        int soNgayTangCa = tangca.size();

        String maCC = taoMaCC(maNV, thang, nam);

        // Tạo đối tượng DTO bảng cha (BangChamCong)
        ChamCong_DTO cc = new ChamCong_DTO(maCC, maNV, thang, nam, soNgayLam, soGioTre, soGioTangCa);
        cc.setSoNgayTre(soNgayTre);
        cc.setSoNgayTangCa(soNgayTangCa);

        // 1. Lưu hoặc Cập nhật bảng tổng hợp trước
        if (!dao.insertOrUpdate(cc)) {
            return false;
        }

        // 2. XÓA dữ liệu chi tiết cũ của tháng này để tránh lỗi trùng khóa chính
        ctDao.deleteByMaChamCong(maCC);

        // 3. Chuẩn bị danh sách chi tiết mới với trạng thái có dấu
        ArrayList<ChamCongChiTiet_DTO> list = new ArrayList<>();
        for (int d : nghi)
            list.add(new ChamCongChiTiet_DTO(maCC, d, "Nghỉ", 0));
        for (int d : tre)
            list.add(new ChamCongChiTiet_DTO(maCC, d, "Trễ", gio));
        for (int d : tangca)
            list.add(new ChamCongChiTiet_DTO(maCC, d, "Tăng ca", gio));

        // 4. Lưu danh sách chi tiết vào Database
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
 // Trong class ChamCong_BUS
    public List<ChamCong_DTO> getDanhSachChamCongSVDangNhap(int thang, int nam, String manv) {
        return ctDao.getDanhSachChamCongSVDangNhap(thang, nam, manv);
    }
}