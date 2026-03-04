/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author khanh
 */
public class ChucVu_DTO {
    private String maCV,tenCV,moTa,maPC;
    
    public ChucVu_DTO(){}

    public ChucVu_DTO(String maCV, String tenCV, String moTa, String maPC) {
        this.maCV = maCV;
        this.tenCV = tenCV;
        this.moTa = moTa;
        this.maPC = maPC;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaPC() {
        return maPC;
    }

    public void setMaPC(String maPC) {
        this.maPC = maPC;
    }
    
    
    
    
    @Override
    public String toString(){
        return this.tenCV;
    }
}
