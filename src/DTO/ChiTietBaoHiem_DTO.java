package DTO;

import java.time.LocalDate;

public class ChiTietBaoHiem_DTO {
    private String maCTBH, soTheBH, noiCap, maNV, maBH;
    private LocalDate ngayCap;
    
    // Dùng cho Dialog (Hiển thị chi tiết bảo hiểm)
    private String tenBH;
    private double tyLeNV, tyLeCT;

    // Dùng cho GUI Chính (Hiển thị danh sách nhân viên)
    private String tenNV;
    private int soLuongBH;

    public ChiTietBaoHiem_DTO() {}

    // 1. Constructor dùng khi THÊM mới (từ GUI xuống DB)
    public ChiTietBaoHiem_DTO(String maCTBH, String soTheBH, String noiCap, String maNV, String maBH, LocalDate ngayCap) {
        this.maCTBH = maCTBH;
        this.soTheBH = soTheBH;
        this.noiCap = noiCap;
        this.maNV = maNV;
        this.maBH = maBH;
        this.ngayCap = ngayCap;
    }

    // 2. Constructor dùng khi LOAD dữ liệu lên Dialog
    public ChiTietBaoHiem_DTO(String maCTBH, String soTheBH, String noiCap, String maNV, String maBH, LocalDate ngayCap, String tenBH, double tyLeNV, double tyLeCT) {
        this.maCTBH = maCTBH;
        this.soTheBH = soTheBH;
        this.noiCap = noiCap;
        this.maNV = maNV;
        this.maBH = maBH;
        this.ngayCap = ngayCap;
        this.tenBH = tenBH;
        this.tyLeNV = tyLeNV;
        this.tyLeCT = tyLeCT;
    }

    // 3. Constructor MỚI dùng khi LOAD dữ liệu lên GUI Chính (Thống kê nhân viên)
    public ChiTietBaoHiem_DTO(String maNV, String tenNV, int soLuongBH) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.soLuongBH = soLuongBH;
    }

    // --- GETTERS & SETTERS ---
    public String getMaCTBH() { return maCTBH; }
    public void setMaCTBH(String maCTBH) { this.maCTBH = maCTBH; }
    public String getSoTheBH() { return soTheBH; }
    public void setSoTheBH(String soTheBH) { this.soTheBH = soTheBH; }
    public String getNoiCap() { return noiCap; }
    public void setNoiCap(String noiCap) { this.noiCap = noiCap; }
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }
    public String getMaBH() { return maBH; }
    public void setMaBH(String maBH) { this.maBH = maBH; }
    public LocalDate getNgayCap() { return ngayCap; }
    public void setNgayCap(LocalDate ngayCap) { this.ngayCap = ngayCap; }

    public String getTenBH() { return tenBH; }
    public double getTyLeNV() { return tyLeNV; }
    public double getTyLeCT() { return tyLeCT; }
    
    public String getTenNV() { return tenNV; }
    public int getSoLuongBH() { return soLuongBH; }
}