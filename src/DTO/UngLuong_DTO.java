/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author khanh
 */
public class UngLuong_DTO {
    private String maUL,maBL,lyDo,trangThai;
    private LocalDate ngayUng;
    private BigDecimal soTien;

    public UngLuong_DTO(String maUL, String maBL, String lyDo, String trangThai, LocalDate ngayUng, BigDecimal soTien) {
        this.maUL = maUL;
        this.maBL = maBL;
        this.lyDo = lyDo;
        this.trangThai = trangThai;
        this.ngayUng = ngayUng;
        this.soTien = soTien;
    }

    public String getMaUL() {
        return maUL;
    }

    public void setMaUL(String maUL) {
        this.maUL = maUL;
    }

    public String getMaBL() {
        return maBL;
    }

    public void setMaBL(String maBL) {
        this.maBL = maBL;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDate getNgayUng() {
        return ngayUng;
    }

    public void setNgayUng(LocalDate ngayUng) {
        this.ngayUng = ngayUng;
    }

    public BigDecimal getSoTien() {
        return soTien;
    }

    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }
    
    
}
