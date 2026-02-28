	package BUS;
	import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
	
	import DAO.Phongban_DAO;
	import DTO.Phongban_DTO;
	import DTO.NhanVien_DTO;
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
		Object[][] ob = new Object[arr.size()][5];
		for(int i=0;i<arr.size();i++) {
			Phongban_DTO temp = arr.get(i);
			// lấy ên trưởng phòng
			String tenTruongPhong = "Đang tuyển";
			if(temp.getMatruongphong() != null && !temp.getMatruongphong().isEmpty() && !temp.getMatruongphong().equals("Đang tuyển")) {
			    tenTruongPhong = pbdao.gettentuma(temp.getMatruongphong());
			}
			int soluong=pbdao.getsoluong(temp.getMaphongban());
			double tongluong=pbdao.gettongluong(temp.getMaphongban());
			double luongtb=(tongluong>0)?(tongluong/soluong):0;
			ob[i]=new Object[] {i+1+"",temp.getMaphongban(),temp.getTenphongban(),tenTruongPhong,pbdao.getsoluong(temp.getMaphongban()),String.format("%.2f", luongtb)};	
		}
		return ob;
	}
	public Object[][] getNhanVienbyPB(String mapb)
	{
		ArrayList<NhanVien_DTO> listnv=pbdao.getListNhanVien(mapb);
		Object [][]data=new Object[listnv.size()][6];
		for(int i=0;i<listnv.size();i++)
		{
			NhanVien_DTO nv=listnv.get(i);
			data[i][0]=i+1;
			data[i][1]=nv.getManv();
			data[i][2]=nv.getHoTen();
		}
		return data;
	}
	public ArrayList<NhanVien_DTO> getlistNhanVien(String mapb)
	{
		return pbdao.getListNhanVien(mapb);
	}
	public ArrayList<String> getdataComboBoxTP(String mapb)
	{
		ArrayList<NhanVien_DTO> listdata=pbdao.getListNhanVien(mapb);
		ArrayList<String> listds=new ArrayList<>();
		for(NhanVien_DTO x: listdata)
		{
			listds.add(x.getManv()+" - "+x.getHoTen());
		}
		return listds;
		
	}
	public String addPhongBan(Phongban_DTO pb) {
	    // 1. Kiểm tra mã có trống không
	    if (pb.getMaphongban().trim().isEmpty()) {
	        return "Mã phòng ban không được để trống!";
	    }
	    String sdt=pb.getSdt().trim();
	    if (!sdt.matches("^[0-9]+$")) {
	        return "Số điện thoại không được chứa ký tự chữ và ký tự đặc biệt!";
	    }
	    if(sdt.length()!=10)
	    {
	    	return "Số điện thoại phải có đúng 10 chữ số!";
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
	    if (pb.getTenphongban().trim().isEmpty() || pb.getSdt().trim().isEmpty()) {
	        return "Tên phòng và Số điện thoại không được để trống!";
	    }
	    String sdt=pb.getSdt();
	    if(!sdt.matches("^[0-9]+$"))
	    {
	        return "Số điện thoại không được chứa ký tự chữ và ký tự đặc biệt!";
	    }
	    if (sdt.length()!=10) {
	        return "Số điện thoại phải đúng 10 chữ số!";
	    }
	
	    if (pbdao.updatePhongban(pb)) {
	        getdatabase(); 
	        return "Cập nhật phòng ban thành công!";
	    } else {
	        return "Lỗi: Không thể cập nhật dữ liệu vào hệ thống.";
	    }
	}
	public String DeletePB(String mapb)
	{
		int soluong=pbdao.getsoluong(mapb);
		if(soluong>0)
		{
			return "Lỗi: Không thể xóa hiện tại đang có "+soluong+" nhân viên!";
		}
		if(pbdao.DeletePhongban(mapb))
		{
			return "Xóa phòng ban thành công!";
		}
		else
		{
			return "Lỗi: Không thể xóa dữ liệu khỏi hệ thống";
		}
		
		
	}
	public Object[] buildRowObject(Phongban_DTO temp, int stt) {
	    String tenTruongPhong = "Đang tuyển";
	    if(temp.getMatruongphong() != null && !temp.getMatruongphong().isEmpty() && !temp.getMatruongphong().equals("Đang tuyển")) {
	        tenTruongPhong = pbdao.gettentuma(temp.getMatruongphong());
	    }
	    int soluong = pbdao.getsoluong(temp.getMaphongban());
	    double tongluong = pbdao.gettongluong(temp.getMaphongban());
	    double luongtb = (soluong > 0) ? (tongluong / soluong) : 0;
	    
	    return new Object[] {stt + "", temp.getMaphongban(), temp.getTenphongban(), tenTruongPhong, soluong, String.format("%.2f", luongtb)};
	}
	public String Dinhdangngay(LocalDate ngay)
	{
		if(ngay==null)
		{
			return "Chưa cập nhật";
		}
		else
		{
			DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return ngay.format(dtf);
		}
	}
}
