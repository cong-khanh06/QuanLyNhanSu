package BUS;

import DAO.ThongBao_DAO;
import DTO.ThongBao_DTO;
import java.util.List;

public class ThongBao_BUS {
    private ThongBao_DAO dao = new ThongBao_DAO();

    public List<ThongBao_DTO> layDanhSachThongBao() {
        return dao.layDanhSachThongBao();
    }

    public boolean themThongBao(ThongBao_DTO tb) {
        if (tb.getMaTK() == null || tb.getMaTK().trim().isEmpty() || tb.getNoiDung().trim().isEmpty()) {
            return false;
        }
        return dao.insertThongBao(tb);
    }

    public boolean suaThongBao(ThongBao_DTO tb) {
        return dao.updateThongBao(tb);
    }

    public boolean xoaThongBao(String maTB) {
        return dao.deleteThongBao(maTB);
    }

    public String taoMaMoi() {
        return dao.getNewestMaTB();
    }
}