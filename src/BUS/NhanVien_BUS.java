package BUS;
import DTO.NhanVien_DTO;
import java.util.ArrayList;

public class NhanVien_BUS {
    ArrayList<NhanVien_DTO> dsNhanVien;
    public static boolean isValidPhone(String sdt) {
        if (sdt == null) return false;
        return sdt.matches("^0(3|5|7|8|9)[0-9]{8}$");
    }

    public static boolean isValidCCCD(String cccd) {
        if (cccd == null) return false;
        return cccd.matches("^[0-9]{12}$");
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
    public enum GioiTinh {
        Nam("Nam"),
        Nu("Nữ");

        private String gioiTinhHienThi;

        GioiTinh(String gioiTinhHienThi){
            this.gioiTinhHienThi=gioiTinhHienThi;
        }

        public String getGioiTinhHienThi() {
            return gioiTinhHienThi;
        }
    }
    public enum TrangThaiNhanVien {
        DangLam("Đang Làm"),
        TamNghi("Tạm Nghỉ"),
        NghiViec("Nghỉ Việc");

        private String trangThaiHienThi;


        TrangThaiNhanVien(String trangThaiHienThi) {
            this.trangThaiHienThi = trangThaiHienThi;
        }


        public String getTrangThaiHienThi() {
            return trangThaiHienThi;
        }
    }
}
