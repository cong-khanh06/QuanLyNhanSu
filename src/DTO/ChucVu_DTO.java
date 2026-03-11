package DTO;

public class ChucVu_DTO {
    private String maCV, tenCV, moTa;
    
    public ChucVu_DTO() {}

    public ChucVu_DTO(String maCV, String tenCV, String moTa) {
        this.maCV = maCV;
        this.tenCV = tenCV;
        this.moTa = moTa;
    }

    public String getMaCV() { return maCV; }
    public void setMaCV(String maCV) { this.maCV = maCV; }
    public String getTenCV() { return tenCV; }
    public void setTenCV(String tenCV) { this.tenCV = tenCV; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    
    @Override
    public String toString(){
        return this.tenCV;
    }
}