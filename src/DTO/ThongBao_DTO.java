package DTO;


import java.time.LocalDate;
public class ThongBao_DTO {
    private String maTB,maTK,noiDung;
    private LocalDate ngayTao;

    public ThongBao_DTO(String maTB, String maTK, String noiDung, LocalDate ngayTao) {
        this.maTB = maTB;
        this.maTK = maTK;
        this.noiDung = noiDung;
        this.ngayTao = ngayTao;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }
    
    
}
