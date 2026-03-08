package BUS;

import java.util.ArrayList;
import DAO.TaiKhoan_DAO;
import DTO.TaiKhoan_DTO;

public class TaiKhoan_BUS {
    private ArrayList<TaiKhoan_DTO> list;
    private TaiKhoan_DAO taikhoandao;

    public TaiKhoan_BUS() {
        taikhoandao = new TaiKhoan_DAO();
    }

    public String CheckDMK(String user, String pass, String passnew) {
        if (!taikhoandao.checktaikhoan(user)) {
            return "Tài khoản không tồn tại!";
        } else if (!taikhoandao.checkmatkhau(user, pass)) {
            return "Mật khẩu cũ không chính xác";
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

    public String CheckLogin(String user, String pass) {
        if (!taikhoandao.checktaikhoan(user)) {
            return "Tài khoản không tồn tại!";
        } else if (!taikhoandao.checkmatkhau(user, pass)) {
            return "Tài khoản hoặc mật khẩu không chính xác";
        } else {
            return "Đăng nhập thành công!";
        }
    }

    public String getTenchucvu(String user) {
        String chucvu = taikhoandao.getTenchucvu(user);
        if (chucvu == null || chucvu.trim().isEmpty()) {
            return "Đang cập nhật";
        }
        return chucvu;
    }

    public ArrayList<TaiKhoan_DTO> getAll() {
        return taikhoandao.getListtaikhoan();
    }

    public String add(TaiKhoan_DTO tk) {
        if (tk.getMataikhoan().trim().isEmpty() || tk.getTendangnhap().trim().isEmpty()) {
            return "Mã tài khoản và Tên đăng nhập không được để trống!";
        }
        
        // --- THÊM KIỂM TRA MẬT KHẨU TẠI ĐÂY ---
        if (tk.getMatkhau() == null || tk.getMatkhau().length() < 6) {
            return "Mật khẩu khởi tạo phải có ít nhất 6 ký tự!";
        }
        // --------------------------------------

        if (taikhoandao.checkExistMaTK(tk.getMataikhoan())) {
            return "Mã tài khoản này đã tồn tại!";
        }
        if (taikhoandao.checktaikhoan(tk.getTendangnhap())) {
            return "Tên đăng nhập đã được sử dụng!";
        }
        if (taikhoandao.checkExistMaNV(tk.getManv())) {
            return "Nhân viên này đã có tài khoản rồi!";
        }
        
        if (taikhoandao.insert(tk)) {
            return "Thêm tài khoản thành công!";
        }
        return "Thêm thất bại!";
    }

    public String update(TaiKhoan_DTO tk) {
        if (tk.getMataikhoan().trim().isEmpty()) {
            return "Không xác định được mã tài khoản cần sửa!";
        }
        
        // --- THÊM KIỂM TRA MẬT KHẨU KHI CẬP NHẬT (Nếu có nhập pass mới) ---
        if (tk.getMatkhau() != null && !tk.getMatkhau().isEmpty() && tk.getMatkhau().length() < 6) {
            return "Mật khẩu mới phải có ít nhất 6 ký tự!";
        }
        // ---------------------------------------------------------------

        if (taikhoandao.update(tk)) {
            return "Cập nhật thành công!";
        }
        return "Cập nhật thất bại!";
    }

    public String delete(String maTK) {
        if (maTK == null || maTK.trim().isEmpty()) {
            return "Vui lòng chọn tài khoản cần xóa!";
        }
        if (taikhoandao.delete(maTK)) {
            return "Xóa tài khoản thành công!";
        }
        return "Xóa thất bại!";
    }

    public ArrayList<TaiKhoan_DTO> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        return taikhoandao.search(keyword.trim());
    }
}