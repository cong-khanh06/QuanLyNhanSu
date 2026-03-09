package DTO;

public class ChamCongChiTiet_DTO {

    private String maChamCong;
    private int ngay;
    private String trangThai;
    private int soGio;

    public ChamCongChiTiet_DTO(){}

    public ChamCongChiTiet_DTO(String maChamCong, int ngay, String trangThai, int soGio){
        this.maChamCong = maChamCong;
        this.ngay = ngay;
        this.trangThai = trangThai;
        this.soGio = soGio;
    }

    public String getMaChamCong() {
        return maChamCong;
    }

    public void setMaChamCong(String maChamCong) {
        this.maChamCong = maChamCong;
    }

    public int getNgay() {
        return ngay;
    }

    public void setNgay(int ngay) {
        this.ngay = ngay;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getSoGio() {
        return soGio;
    }

    public void setSoGio(int soGio) {
        this.soGio = soGio;
    }
}