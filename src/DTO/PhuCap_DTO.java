/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author khanh
 */
public class PhuCap_DTO {
    private String maPC,tenPC;
    private double soTien;

    public PhuCap_DTO(String maPC, String tenPC, double soTien) {
        this.maPC = maPC;
        this.tenPC = tenPC;
        this.soTien = soTien;
    }

    

    public String getMaPC() {
        return maPC;
    }

    public void setMaPC(String maPC) {
        this.maPC = maPC;
    }

    public String getTenPC() {
        return tenPC;
    }

    public void setTenPC(String tenPC) {
        this.tenPC = tenPC;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }
    
    @Override
    public String toString(){
        return this.tenPC;
    }
}
