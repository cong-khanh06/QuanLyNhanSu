package DTO;

public class LoaiBaoHiem_DTO {
    private String maBH, tenBH;
    private double tyLeNV, tyLeCT;

    public LoaiBaoHiem_DTO() {}

    public LoaiBaoHiem_DTO(String maBH, String tenBH, double tyLeNV, double tyLeCT) {
        this.maBH = maBH;
        this.tenBH = tenBH;
        this.tyLeNV = tyLeNV;
        this.tyLeCT = tyLeCT;
    }

    public String getMaBH() { return maBH; }
    public void setMaBH(String maBH) { this.maBH = maBH; }
    public String getTenBH() { return tenBH; }
    public void setTenBH(String tenBH) { this.tenBH = tenBH; }
    public double getTyLeNV() { return tyLeNV; }
    public void setTyLeNV(double tyLeNV) { this.tyLeNV = tyLeNV; }
    public double getTyLeCT() { return tyLeCT; }
    public void setTyLeCT(double tyLeCT) { this.tyLeCT = tyLeCT; }
    
    @Override
    public String toString() {
        return maBH + " - " + tenBH;
    }
}