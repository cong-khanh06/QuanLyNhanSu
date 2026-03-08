package DTO;

import java.sql.Date;

public class HopDong_DTO {

    private String maHD;
    private String loaiHopDong;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Date ngayKy;
    private long mucLuongCoBan;
    private String noiDung;
    private String trangThai;
    private String maNV;
    private int lanKy;
    private Date ngayLenLuongGanNhat;

    public HopDong_DTO() {
    }

    public HopDong_DTO(String maHD, String loaiHopDong, Date ngayBatDau,
                       Date ngayKetThuc, Date ngayKy, long mucLuongCoBan,
                       String noiDung, String trangThai, String maNV,
                       int lanKy, Date ngayLenLuongGanNhat) {

        this.maHD = maHD;
        this.loaiHopDong = loaiHopDong;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.ngayKy = ngayKy;
        this.mucLuongCoBan = mucLuongCoBan;
        this.noiDung = noiDung;
        this.trangThai = trangThai;
        this.maNV = maNV;
        this.lanKy = lanKy;
        this.ngayLenLuongGanNhat = ngayLenLuongGanNhat;
    }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getLoaiHopDong() { return loaiHopDong; }
    public void setLoaiHopDong(String loaiHopDong) { this.loaiHopDong = loaiHopDong; }

    public Date getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(Date ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    public Date getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(Date ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }

    public Date getNgayKy() { return ngayKy; }
    public void setNgayKy(Date ngayKy) { this.ngayKy = ngayKy; }

    public long getMucLuongCoBan() { return mucLuongCoBan; }
    public void setMucLuongCoBan(long mucLuongCoBan) { this.mucLuongCoBan = mucLuongCoBan; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public int getLanKy() { return lanKy; }
    public void setLanKy(int lanKy) { this.lanKy = lanKy; }

    public Date getNgayLenLuongGanNhat() { return ngayLenLuongGanNhat; }
    public void setNgayLenLuongGanNhat(Date ngayLenLuongGanNhat) {
        this.ngayLenLuongGanNhat = ngayLenLuongGanNhat;
    }
}