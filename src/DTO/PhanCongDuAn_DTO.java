package DTO;

public class PhanCongDuAn_DTO {
    private String maDA;
    private String maNV;
    private String tenNV;
    private String vaiTro;

    public PhanCongDuAn_DTO() {
    }

    public PhanCongDuAn_DTO(String maDA, String maNV, String tenNV, String vaiTro) {
        this.maDA = maDA;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.vaiTro = vaiTro;
    }

    public String getMaDA() { return maDA; }
    public void setMaDA(String maDA) { this.maDA = maDA; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
}