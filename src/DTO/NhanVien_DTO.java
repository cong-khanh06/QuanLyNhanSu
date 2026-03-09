package DTO;

import java.time.LocalDate;

public class NhanVien_DTO {
    public enum GioiTinh {
        Nam("Nam"), Nu("Nữ");
        private String tenHienThi;
        GioiTinh(String tenHienThi) { this.tenHienThi = tenHienThi; }
        public String getTenHienThi() { return tenHienThi; }
        @Override public String toString() { return tenHienThi; } // Giúp JComboBox tự hiện tiếng Việt
    }
    
    public enum TrangThaiNhanVien {
        DangLam("Đang Làm"), TamNghi("Tạm Nghỉ"), NghiViec("Nghỉ Việc");
        private String tenHienThi;
        TrangThaiNhanVien(String tenHienThi) { this.tenHienThi = tenHienThi; }
        public String getTenHienThi() { return tenHienThi; }
        @Override public String toString() { return tenHienThi; } // Giúp JComboBox tự hiện tiếng Việt
    }
    private String maNV;
    private String hoTen;
    private LocalDate ngaySinh;
    private GioiTinh gioiTinh;
    private String diaChi;
    private String sdt;
    private String email;
    private String cccd;
    private LocalDate ngayVaoLam;
    private TrangThaiNhanVien trangThai;
    private String maPB;
    private String maCV;
    private String avatar;

    public NhanVien_DTO() {}
    public NhanVien_DTO(String manv, String hoTen){
        this.maNV = manv;
        this.hoTen = hoTen;
    }

    public NhanVien_DTO(String maNV, String hoTen, LocalDate ngaySinh, GioiTinh gioiTinh, String diaChi, String sdt, String email, String cccd, LocalDate ngayVaoLam, TrangThaiNhanVien trangThai, String maPB, String maCV, String avatar) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.cccd = cccd;
        this.ngayVaoLam = ngayVaoLam;
        this.trangThai = trangThai;
        this.maPB = maPB;
        this.maCV = maCV;
        this.avatar = avatar;
    }
    
    public String getMaNV(){
        return maNV;
    }
    
    public void setMaNV(String maNV){
        this.maNV=maNV;
    }
    
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public GioiTinh getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(GioiTinh gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public TrangThaiNhanVien getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiNhanVien trangThai) {
        this.trangThai = trangThai;
    }

    

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    

    public String getMaPB() {
        return maPB;
    }

    public void setMaPB(String maPB) {
        this.maPB = maPB;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    
}
