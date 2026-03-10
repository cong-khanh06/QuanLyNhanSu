package BUS;

import DAO.BangLuong_DAO;
import DTO.BangLuong_DTO;
import java.util.List;
import java.util.stream.Collectors;

public class BangLuong_BUS {
    private BangLuong_DAO dao = new BangLuong_DAO();

    public List<BangLuong_DTO> layDanhSachBangLuong() {
        return dao.layDanhSachBangLuong();
    }

    public boolean themBangLuong(BangLuong_DTO bl) {
        if (bl.getMaNV() == null || bl.getMaNV().trim().isEmpty()) return false;
        return dao.insertBangLuong(bl);
    }

    public boolean suaBangLuong(BangLuong_DTO bl) {
        return dao.updateBangLuong(bl);
    }

    public boolean xoaBangLuong(String maBL) {
        return dao.deleteBangLuong(maBL);
    }

    public String taoMaMoi() {
        return dao.getNewestMaBL();
    }

    
    public List<BangLuong_DTO> timKiemVaLoc(String tuKhoa, String thang, String nam, String trangThai) {
        List<BangLuong_DTO> all = dao.layDanhSachBangLuong();
        String keyword = tuKhoa.toLowerCase().trim();
        
        return all.stream().filter(bl -> {
            boolean matchTuKhoa = keyword.isEmpty() || 
                                  bl.getMaBL().toLowerCase().contains(keyword) ||
                                  bl.getMaNV().toLowerCase().contains(keyword) ||
                                  bl.getTenNV().toLowerCase().contains(keyword);
            
            boolean matchThang = thang.equals("Tất cả") || String.valueOf(bl.getThang()).equals(thang);
            boolean matchNam = nam.equals("Tất cả") || String.valueOf(bl.getNam()).equals(nam);
            boolean matchTrangThai = trangThai.equals("Tất cả") || bl.getTrangThai().equalsIgnoreCase(trangThai);
            
            return matchTuKhoa && matchThang && matchNam && matchTrangThai;
        }).collect(Collectors.toList());
    }
}