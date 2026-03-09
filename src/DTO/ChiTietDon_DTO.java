/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;

/**
 *
 * @author khanh
 */
public class ChiTietDon_DTO {
    private String maDon,noiDung,trangThai,nguoiDuyet,maNV,maLoaiDon;
    private LocalDate ngayTao;

    public ChiTietDon_DTO(String maDon, String noiDung, String trangThai, String nguoiDuyet, String maNV, String maLoaiDon, LocalDate ngayTao) {
        this.maDon = maDon;
        this.noiDung = noiDung;
        this.trangThai = trangThai;
        this.nguoiDuyet = nguoiDuyet;
        this.maNV = maNV;
        this.maLoaiDon = maLoaiDon;
        this.ngayTao = ngayTao;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }
    

    public String getMaDon() {
        return maDon;
    }

    public void setMaDon(String maDon) {
        this.maDon = maDon;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getNguoiDuyet() {
        return nguoiDuyet;
    }

    public void setNguoiDuyet(String nguoiDuyet) {
        this.nguoiDuyet = nguoiDuyet;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaLoaiDon() {
        return maLoaiDon;
    }

    public void setMaLoaiDon(String maLoaiDon) {
        this.maLoaiDon = maLoaiDon;
    }
    
    
}
