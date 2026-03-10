package DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class ChamCong_DTO {
    private String maChamCong;
    private String maNV;
    private String hoTen; // Lấy từ JOIN bảng NhanVien
    private LocalDate ngayTao;
    private LocalTime gioVao;
    private LocalTime gioRa;
    private float soGioTangCa;
    private String trangThai;

    public ChamCong_DTO() {}

    public ChamCong_DTO(String maChamCong, String maNV, String hoTen, LocalDate ngayTao, LocalTime gioVao, LocalTime gioRa, float soGioTangCa, String trangThai) {
        this.maChamCong = maChamCong;
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngayTao = ngayTao;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
        this.soGioTangCa = soGioTangCa;
        this.trangThai = trangThai;
    }

    public String getMaChamCong() { return maChamCong; }
    public void setMaChamCong(String maChamCong) { this.maChamCong = maChamCong; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public LocalDate getNgayTao() { return ngayTao; }
    public void setNgayTao(LocalDate ngayTao) { this.ngayTao = ngayTao; }

    public LocalTime getGioVao() { return gioVao; }
    public void setGioVao(LocalTime gioVao) { this.gioVao = gioVao; }

    public LocalTime getGioRa() { return gioRa; }
    public void setGioRa(LocalTime gioRa) { this.gioRa = gioRa; }

    public float getSoGioTangCa() { return soGioTangCa; }
    public void setSoGioTangCa(float soGioTangCa) { this.soGioTangCa = soGioTangCa; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}