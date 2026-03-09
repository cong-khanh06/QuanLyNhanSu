/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author khanh
 */
public class LoaiDon_DTO {
    private String maLoaiDon, tenLoaiDon;

    public LoaiDon_DTO(String maLoaiDon, String tenLoaiDon) {
        this.maLoaiDon = maLoaiDon;
        this.tenLoaiDon = tenLoaiDon;
    }

    public String getMaLoaiDon() {
        return maLoaiDon;
    }

    public void setMaLoaiDon(String maLoaiDon) {
        this.maLoaiDon = maLoaiDon;
    }

    public String getTenLoaiDon() {
        return tenLoaiDon;
    }

    public void setTenLoaiDon(String tenLoaiDon) {
        this.tenLoaiDon = tenLoaiDon;
    }
    
}
