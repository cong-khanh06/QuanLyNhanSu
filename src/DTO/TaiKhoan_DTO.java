package DTO;

public class TaiKhoan_DTO {
	private String mataikhoan;
	private String tendangnhap;
	private String matkhau;
	private String quyentruycap;
	private String manv;
	public TaiKhoan_DTO()
	{
		
	}
	public TaiKhoan_DTO(String ma,String ten,String matkhau,String quyentruycap,String manv)
	{
		this.mataikhoan=ma;
		this.tendangnhap=ten;
		this.matkhau=matkhau;
		this.manv=manv;
		this.quyentruycap=quyentruycap;
	}
	public String getMataikhoan() {
		return mataikhoan;
	}
	public void setMataikhoan(String mataikhoan) {
		this.mataikhoan = mataikhoan;
	}
	public String getTendangnhap() {
		return tendangnhap;
	}
	public void setTendangnhap(String tendangnhap) {
		this.tendangnhap = tendangnhap;
	}
	public String getMatkhau() {
		return matkhau;
	}
	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}
	public String getManv() {
		return manv;
	}
	public void setManv(String manv) {
		this.manv = manv;
	}
	public String getQuyentruycap() {
		return quyentruycap;
	}
	public void setQuyentruycap(String quyentruycap) {
		this.quyentruycap = quyentruycap;
	}
	
	
	
}
