package BUS;

import java.util.ArrayList;
import DAO.TaiKhoan_DAO;
import DTO.TaiKhoan_DTO;

public class TaiKhoan_BUS {
    private ArrayList<TaiKhoan_DTO> list;
    private TaiKhoan_DAO taikhoandao;

    public TaiKhoan_BUS() {
        list = new ArrayList<>();
        taikhoandao = new TaiKhoan_DAO();
    }

    public String Checklogin(String user, String pass, String passnew) {
        if (!taikhoandao.checktaikhoan(user)) {
            return "Tài khoản không tồn tại!";
        } else if (!taikhoandao.checkmatkhau(user, pass)) {
            return "Mật khẩu không chính xác";
        } else if (passnew.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự!";
        } else if (taikhoandao.checkmatkhau(user, passnew)) {
            return "Mật khẩu mới không được trùng với mật khẩu cũ!";
        } else {
            if (taikhoandao.updatePassword(user, passnew))
                return "Đã thay đổi mật khẩu thành công";
            else
                return "Lỗi hệ thống: Không thể cập nhật mật khẩu!";
        }
    }

    public ArrayList<TaiKhoan_DTO> getAll() {
        return taikhoandao.getListtaikhoan();
    }

    // Thêm 
    public String addTaiKhoan(TaiKhoan_DTO tk) {
        if (taikhoandao.checktaikhoan(tk.getTendangnhap())) {
            return "Tên đăng nhập đã tồn tại!";
        }
        if (taikhoandao.checkExistMaNV(tk.getManv())) {
            return "Nhân viên này đã có tài khoản rồi!";
        }
        
        if (taikhoandao.insert(tk)) {
            return "Thêm tài khoản thành công!";
        }
        return "Lỗi khi thêm tài khoản!";
    }


    public String updateTaiKhoan(TaiKhoan_DTO tk) {
        if (taikhoandao.update(tk)) {
            return "Cập nhật tài khoản thành công!";
        }
        return "Cập nhật thất bại!";
    }


    public String deleteTaiKhoan(String maTK) {
        if (maTK == null || maTK.isEmpty()) {
            return "Vui lòng chọn tài khoản cần xóa!";
        }
        if (taikhoandao.delete(maTK)) {
            return "Xóa thành công!";
        }
        return "Xóa thất bại!";
    }


    public ArrayList<TaiKhoan_DTO> search(String keyword) {
        return taikhoandao.search(keyword);
    }
}