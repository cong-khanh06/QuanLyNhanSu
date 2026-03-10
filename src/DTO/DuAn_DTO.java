
package DTO;

import java.time.LocalDate;

public class DuAn_DTO {
    private String maDa,tenDuAn,trangThai,nguoiQuanLy;
    private LocalDate ngayBatDau,ngayKetThuc;

    public DuAn_DTO(String maDa, String tenDuAn, String trangThai, String nguoiQuanLy, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.maDa = maDa;
        this.tenDuAn = tenDuAn;
        this.trangThai = trangThai;
        this.nguoiQuanLy = nguoiQuanLy;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getNguoiQuanLy() {
        return nguoiQuanLy;
    }

    public void setNguoiQuanLy(String nguoiQuanLy) {
        this.nguoiQuanLy = nguoiQuanLy;
    }

    

    public String getMaDa() {
        return maDa;
    }

    public void setMaDa(String maDa) {
        this.maDa = maDa;
    }

    public String getTenDuAn() {
        return tenDuAn;
    }

    public void setTenDuAn(String tenDuAn) {
        this.tenDuAn = tenDuAn;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    
    

}
