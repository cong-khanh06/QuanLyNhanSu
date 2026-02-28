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
	public String Checklogin(String user,String pass,String passnew)
	{
		if(!taikhoandao.checktaikhoan(user))
		{
			return "Tài khoản không tồn tại!";
		}
		else if(!taikhoandao.checkmatkhau(user, pass))
		{
			return "Mật khẩu không chính xác";
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
	

}
