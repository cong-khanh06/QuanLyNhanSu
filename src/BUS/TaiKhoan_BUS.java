package BUS;
import java.util.ArrayList;

import DAO.TaiKhoan_DAO;
import DTO.TaiKhoan_DTO;
public class TaiKhoan_BUS {
private ArrayList<TaiKhoan_DTO> list;
private TaiKhoan_DAO taikhoandao;
	public TaiKhoan_BUS()
	{
		list=new ArrayList<>();
		taikhoandao=new TaiKhoan_DAO();
	}
	public String CheckDMK(String user,String pass,String passnew)
	{
		if(!taikhoandao.checktaikhoan(user))
		{
			return "Tài khoản không tồn tại!";
		}
		else if(!taikhoandao.checkmatkhau(user, pass))
		{
			return "Mật khẩu cũ không chính xác";
		}
		else if(passnew.length()<6)
		{
			return "Mật khẩu phải có ít nhất 6 ký tự!";
		}
		else if(taikhoandao.checkmatkhau(user, passnew))
		{
			return "Mật khẩu mới không được trùng với mật khẩu cũ!";
		}
		else
		{
			if(taikhoandao.updatePassword(user, passnew))
				return "Đã thay đổi mật khẩu thành công";
			else
				return "Lỗi hệ thống: Không thể cập nhật mật khẩu!";				
		}
	}
	public String CheckLogin(String user,String pass)
	{
		if(!taikhoandao.checktaikhoan(user))
		{
			return "Tài khoản không tồn tại!";
		}
		else if(!taikhoandao.checkmatkhau(user, pass))
		{
			return "Tài khoản hoặc mật khẩu không chính xác";
		}
		else
		{
			return "Đăng nhập thành công!";
		}
	}
	public String getTenchucvu(String user) {
        String chucvu = taikhoandao.getTenchucvu(user);
        
        // Nếu kết quả từ DAO là null hoặc chuỗi rỗng, trả về "Đang cập nhật"
        if (chucvu == null || chucvu.trim().isEmpty()) {
            return "Đang cập nhật";
        }
        
        return chucvu;
    }
	

}
