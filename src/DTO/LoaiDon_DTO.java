
package DTO;

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
