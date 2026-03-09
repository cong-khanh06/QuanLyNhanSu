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
public class BangCap_DTO {
    private String maBC,tenBC,noiCap,maNV;
    private LocalDate ngayCap;

    public BangCap_DTO(String maBC, String tenBC, String noiCap, String maNV, LocalDate ngayCap) {
        this.maBC = maBC;
        this.tenBC = tenBC;
        this.noiCap = noiCap;
        this.maNV = maNV;
        this.ngayCap = ngayCap;
    }

    public String getMaBC() {
        return maBC;
    }

    public void setMaBC(String maBC) {
        this.maBC = maBC;
    }

    public String getTenBC() {
        return tenBC;
    }

    public void setTenBC(String tenBC) {
        this.tenBC = tenBC;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public LocalDate getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(LocalDate ngayCap) {
        this.ngayCap = ngayCap;
    }
    
    
}
