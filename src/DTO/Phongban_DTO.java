package DTO;

public class Phongban_DTO {
private String maphongban;
private String tenphongban;
private String sdt;
private String mabophan;
private String matruongphong;
private String diachi;
public Phongban_DTO()
{
}
public Phongban_DTO(String mapb,String ten,String diachi,String sdt,String mabp,String matp)
{
	this.maphongban=mapb;
	this.tenphongban=ten;
	this.sdt=sdt;
	this.mabophan=mabp;
	this.matruongphong=matp;
	this.diachi=diachi;
}
public String getMaphongban() {
	return maphongban;
}
public void setMaphongban(String maphongban) {
	this.maphongban = maphongban;
}
public String getTenphongban() {
	return tenphongban;
}
public void setTenphongban(String tenphongban) {
	this.tenphongban = tenphongban;
}
public String getSdt() {
	return sdt;
}
public void setSdt(String sdt) {
	this.sdt = sdt;
}
public String getMabophan() {
	return mabophan;
}
public void setMabophan(String mabophan) {
	this.mabophan = mabophan;
}
public String getMatruongphong() {
	return matruongphong;
}
public void setMatruongphong(String matruongphong) {
	this.matruongphong = matruongphong;
}
public String getDiachi() {
	return diachi;
}
public void setDiachi(String diachi) {
	this.diachi = diachi;
}

}
