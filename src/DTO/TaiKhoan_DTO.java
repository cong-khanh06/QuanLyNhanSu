package DTO;

import java.util.Objects;

public class TaiKhoan_DTO {
    private String mataikhoan;
    private String tendangnhap;
    private String matkhau;
    private String quyentruycap;
    private String manv;

    public TaiKhoan_DTO() {
    }

    public TaiKhoan_DTO(String ma, String ten, String matkhau, String quyentruycap, String manv) {
        this.mataikhoan = ma;
        this.tendangnhap = ten;
        this.matkhau = matkhau;
        this.manv = manv;
        this.quyentruycap = quyentruycap;
    }


    public String getMataikhoan() {
        return mataikhoan;
    }

    public void setMataikhoan(String mataikhoan) {
        this.mataikhoan = mataikhoan;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getQuyentruycap() {
        return quyentruycap;
    }

    public void setQuyentruycap(String quyentruycap) {
        this.quyentruycap = quyentruycap;
    }


    @Override
    public String toString() {
        return "TaiKhoan_DTO{" +
                "maTK='" + mataikhoan + '\'' +
                ", user='" + tendangnhap + '\'' +
                ", quyen='" + quyentruycap + '\'' +
                ", maNV='" + manv + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaiKhoan_DTO that = (TaiKhoan_DTO) o;
        return Objects.equals(mataikhoan, that.mataikhoan) || 
               Objects.equals(tendangnhap, that.tendangnhap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mataikhoan, tendangnhap);
    }
}