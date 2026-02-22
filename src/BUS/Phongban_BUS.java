package BUS;
import java.util.ArrayList;

import DAO.Phongban_DAO;
import DTO.Phongban_DTO;

public class Phongban_BUS {
private ArrayList<Phongban_DTO>arr;
private Phongban_DAO pbdao;
public Phongban_BUS()
{
	pbdao=new Phongban_DAO();
	arr=new ArrayList<>();
}
public ArrayList<Phongban_DTO> getdatabase()
{
	this.arr=pbdao.getList();
	return arr;
}
public ArrayList<Phongban_DTO> getlist()
{
	return arr;
}
public Object[][] getObjectToRender() {
	getdatabase();
	Object[][] ob = new Object[arr.size()][];
	for(int i=0;i<arr.size();i++) {
		Phongban_DTO temp = arr.get(i);
		// lấy ên trưởng phòng
		String tenTruongPhong = "Đang tuyển";
		if(temp.getMatruongphong() != null && !temp.getMatruongphong().isEmpty() && !temp.getMatruongphong().equals("Đang tuyển")) {
		    tenTruongPhong = pbdao.gettentuma(temp.getMatruongphong());
		}
		ob[i]=new Object[] {i+1+"",temp.getMaphongban(),temp.getTenphongban(),tenTruongPhong,pbdao.getsoluong(temp.getMaphongban()),""+0};
		
		
	}
	return ob;
}
public String addPhongBan(Phongban_DTO pb) {
    // 1. Kiểm tra mã có trống không
    if (pb.getMaphongban().trim().isEmpty()) {
        return "Mã phòng ban không được để trống!";
    }
    if (!pb.getSdt().matches("^\\d{10}$")) {
        return "Số điện thoại không hợp lệ!";
    }

    // 2. Kiểm tra trùng mã thông qua DAO
    Phongban_DAO dao = new Phongban_DAO();
    if (dao.checkMaPhongBan(pb.getMaphongban())) {
        return "Mã phòng ban này đã tồn tại trong hệ thống!";
    }

    // 3. Nếu mọi thứ hợp lệ, tiến hành gọi DAO để chèn dữ liệu
    boolean success = dao.insertPhongban(pb);
    if (success) {
    	getdatabase();
        return "Thêm phòng ban thành công!";
        
    } else {
        return "Lỗi: Không thể lưu vào cơ sở dữ liệu.";
    }
}
public ArrayList<String>getTenMaBoPhan()
{	
	ArrayList<String> arrten=pbdao.getListTenMaBoPhan();
	return arrten;
}
public String updatePhongBan(Phongban_DTO pb) {
    // 1. Kiểm tra dữ liệu rỗng
    if (pb.getTenphongban().trim().isEmpty() || pb.getSdt().trim().isEmpty()) {
        return "Tên phòng và Số điện thoại không được để trống!";
    }

    // 2. Kiểm tra định dạng số điện thoại (10 số)
    if (!pb.getSdt().matches("^\\d{10}$")) {
        return "Số điện thoại phải đúng 10 chữ số!";
    }

    // 3. Gọi DAO thực hiện cập nhật
    if (pbdao.updatePhongban(pb)) {
        getdatabase(); // Làm mới danh sách trong bộ nhớ BUS
        return "Cập nhật phòng ban thành công!";
    } else {
        return "Lỗi: Không thể cập nhật dữ liệu vào hệ thống.";
    }
}

}
