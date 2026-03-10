package BUS;

import DAO.PhanCongDuAn_DAO;
import DTO.PhanCongDuAn_DTO;
import java.util.List;

public class PhanCongDuAn_BUS {
    private PhanCongDuAn_DAO dao = new PhanCongDuAn_DAO();

    public List<PhanCongDuAn_DTO> layDanhSachPhanCong(String maDA) {
        return dao.layDanhSachPhanCong(maDA);
    }

    public boolean themPhanCong(PhanCongDuAn_DTO pc) {
        if (pc.getMaNV() == null || pc.getVaiTro().trim().isEmpty()) return false;
        return dao.insertPhanCong(pc);
    }

    public boolean suaPhanCong(PhanCongDuAn_DTO pc) {
        return dao.updatePhanCong(pc);
    }

    public boolean xoaPhanCong(String maDA, String maNV) {
        return dao.deletePhanCong(maDA, maNV);
    }

    public List<String> layDanhSachNhanVienCombobox() {
        return dao.layDanhSachNhanVienCombobox();
    }
}