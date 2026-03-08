package GUI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import BUS.NhanVien_BUS;
import BUS.Phongban_BUS;
import BUS.DuAn_BUS;
import BUS.HopDong_BUS;

public class TrangChu_GUI extends JPanel {
    NhanVien_BUS busNv=new NhanVien_BUS();
    Phongban_BUS busPb=new Phongban_BUS();
    DuAn_BUS busDa=new DuAn_BUS();
    HopDong_BUS busHd=new HopDong_BUS();
    public TrangChu_GUI() {
        setLayout(new BorderLayout(20, 20)); 
        setBackground(new Color(240, 242, 245)); 
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel pnCards = new JPanel(new GridLayout(1, 4, 20, 0)); 
        pnCards.setBackground(new Color(240, 242, 245));
        pnCards.setPreferredSize(new Dimension(0, 130)); 

        // Thêm 4 thẻ với các màu sắc khác nhau
        pnCards.add(taoTheThongKe("Tổng Nhân Viên", busNv.soLuongNhanVien(), new Color(74, 144, 226)));   
        pnCards.add(taoTheThongKe("Phòng Ban", busPb.soLuongPhongban(), new Color(80, 227, 194)));          
        pnCards.add(taoTheThongKe("Dự Án Đang Chạy",  busDa.soLuongDuAnDangThucHien(), new Color(245, 166, 35)));
        pnCards.add(taoTheThongKe("Hợp Đồng Sắp Hết", busHd.soLuongHopDongHetHan(), new Color(208, 2, 27)));    

        add(pnCards, BorderLayout.NORTH);

        // ==========================================
        // 2. KHU VỰC NỘI DUNG CHÍNH (CENTER)
        // ==========================================
        JPanel pnContent = new JPanel(new GridLayout(1, 2, 20, 0)); // Chia làm 2 cột bằng nhau
        pnContent.setBackground(new Color(240, 242, 245));

        // --- Cột Trái: Chỗ để Biểu đồ ---
        JPanel pnChart = new JPanel(new BorderLayout());
        pnChart.setBackground(Color.WHITE);
        // Tạo viền nhẹ cho đẹp
        pnChart.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblChartTitle = new JLabel("Thống kê nhân sự theo phòng ban");
        lblChartTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnChart.add(lblChartTitle, BorderLayout.NORTH);
        
        JLabel lblChartPlaceholder = new JLabel("(Khu vực này để vẽ biểu đồ JFreeChart)", SwingConstants.CENTER);
        lblChartPlaceholder.setForeground(Color.GRAY);
        pnChart.add(lblChartPlaceholder, BorderLayout.CENTER);

        // --- Cột Phải: Bảng Nhắc nhở / Cảnh báo ---
        JPanel pnAlerts = new JPanel(new BorderLayout(0, 10));
        pnAlerts.setBackground(Color.WHITE);
        pnAlerts.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblAlertTitle = new JLabel("Thông báo & Nhắc nhở");
        lblAlertTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnAlerts.add(lblAlertTitle, BorderLayout.NORTH);

        // Tạo bảng thông báo giả lập
        String[] colsAlert = {"Loại", "Nội dung", "Thời hạn"};
        Object[][] dataAlert = {
            {"Sinh nhật", "Nguyễn Văn A (Phòng IT)", "Hôm nay"},
            {"Hợp đồng", "Trần Thị B sắp hết hạn", "Còn 5 ngày"},
            {"Tuyển dụng", "Phỏng vấn 3 ứng viên", "14:00 Chiều nay"},
            {"Dự án", "Họp tiến độ dự án Alpha", "Ngày mai"}
        };
        JTable tableAlert = new JTable(new DefaultTableModel(dataAlert, colsAlert));
        tableAlert.setRowHeight(35); // Dòng cao cho dễ nhìn
        tableAlert.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableAlert.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableAlert.setShowVerticalLines(false); // Bỏ đường kẻ dọc cho hiện đại
        
        pnAlerts.add(new JScrollPane(tableAlert), BorderLayout.CENTER);

        // Thêm 2 cột vào nội dung
        pnContent.add(pnChart);
        pnContent.add(pnAlerts);

        add(pnContent, BorderLayout.CENTER);
    }

    
    private JPanel taoTheThongKe(String tieuDe, int giaTri, Color mauNen) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(mauNen);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); 

        JLabel lblTitle = new JLabel(tieuDe);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String strGiaTri=String.valueOf(giaTri);
        JLabel lblValue = new JLabel(strGiaTri);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lblValue.setForeground(Color.WHITE);
        lblValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(10)); 
        panel.add(lblValue);

        return panel;
    }
}