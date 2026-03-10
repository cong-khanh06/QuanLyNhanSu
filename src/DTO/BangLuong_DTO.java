package DTO;

import java.math.BigDecimal;

public class BangLuong_DTO {
    private String maBL;
    private BigDecimal luongCoBan;
    private BigDecimal tongPhuCap;
    private BigDecimal tongKhauTru;
    private BigDecimal thucLanh;
    private String trangThai;
    private String maNV;
    private int thang;
    private int nam;
    
    // Thuộc tính phụ để hiển thị lên bảng (không lưu trực tiếp vào CSDL bảng BangLuong)
    private String tenNV;

    public BangLuong_DTO() {}

    public BangLuong_DTO(String maBL, BigDecimal luongCoBan, BigDecimal tongPhuCap, BigDecimal tongKhauTru, 
                         BigDecimal thucLanh, String trangThai, String maNV, int thang, int nam, String tenNV) {
        this.maBL = maBL;
        this.luongCoBan = luongCoBan;
        this.tongPhuCap = tongPhuCap;
        this.tongKhauTru = tongKhauTru;
        this.thucLanh = thucLanh;
        this.trangThai = trangThai;
        this.maNV = maNV;
        this.thang = thang;
        this.nam = nam;
        this.tenNV = tenNV;
    }

    // --- GETTER & SETTER ---
    public String getMaBL() { return maBL; }
    public void setMaBL(String maBL) { this.maBL = maBL; }

    public BigDecimal getLuongCoBan() { return luongCoBan; }
    public void setLuongCoBan(BigDecimal luongCoBan) { this.luongCoBan = luongCoBan; }

    public BigDecimal getTongPhuCap() { return tongPhuCap; }
    public void setTongPhuCap(BigDecimal tongPhuCap) { this.tongPhuCap = tongPhuCap; }

    public BigDecimal getTongKhauTru() { return tongKhauTru; }
    public void setTongKhauTru(BigDecimal tongKhauTru) { this.tongKhauTru = tongKhauTru; }

    public BigDecimal getThucLanh() { return thucLanh; }
    public void setThucLanh(BigDecimal thucLanh) { this.thucLanh = thucLanh; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public int getThang() { return thang; }
    public void setThang(int thang) { this.thang = thang; }

    public int getNam() { return nam; }
    public void setNam(int nam) { this.nam = nam; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }
}