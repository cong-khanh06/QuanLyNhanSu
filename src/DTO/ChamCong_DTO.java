package DTO;

public class ChamCong_DTO {
    private String maChamCong;
    private String maNV;
    private int thang, nam;
    private int soNgayLam, soGioTre, soGioTangCa;
    private String hoTen;

    private int soNgayTre;
    private int soNgayTangCa;

    public ChamCong_DTO(String maChamCong, String maNV, int thang, int nam, int soNgayLam, int soGioTre,
            int soGioTangCa, String hoTen, int soNgayTre, int soNgayTangCa) {
        this.maChamCong = maChamCong;
        this.maNV = maNV;
        this.thang = thang;
        this.nam = nam;
        this.soNgayLam = soNgayLam;
        this.soGioTre = soGioTre;
        this.soGioTangCa = soGioTangCa;
        this.hoTen = hoTen;
        this.soNgayTre = soNgayTre;
        this.soNgayTangCa = soNgayTangCa;
    }
    public ChamCong_DTO(String maChamCong,
                    String maNV,
                    int thang,
                    int nam,
                    int soNgayLam,
                    int soGioTre,
                    int soGioTangCa) {

    this.maChamCong = maChamCong;
    this.maNV = maNV;
    this.thang = thang;
    this.nam = nam;
    this.soNgayLam = soNgayLam;
    this.soGioTre = soGioTre;
    this.soGioTangCa = soGioTangCa;
}
    public ChamCong_DTO(){}

    public String getMaChamCong() {
        return maChamCong;
    }

    public void setMaChamCong(String maChamCong) {
        this.maChamCong = maChamCong;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public int getSoNgayLam() {
        return soNgayLam;
    }

    public void setSoNgayLam(int soNgayLam) {
        this.soNgayLam = soNgayLam;
    }

    public int getSoGioTre() {
        return soGioTre;
    }

    public void setSoGioTre(int soGioTre) {
        this.soGioTre = soGioTre;
    }

    public int getSoGioTangCa() {
        return soGioTangCa;
    }

    public void setSoGioTangCa(int soGioTangCa) {
        this.soGioTangCa = soGioTangCa;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getSoNgayTre() {
        return soNgayTre;
    }

    public void setSoNgayTre(int soNgayTre) {
        this.soNgayTre = soNgayTre;
    }

    public int getSoNgayTangCa() {
        return soNgayTangCa;
    }

    public void setSoNgayTangCa(int soNgayTangCa) {
        this.soNgayTangCa = soNgayTangCa;
    }
    

}