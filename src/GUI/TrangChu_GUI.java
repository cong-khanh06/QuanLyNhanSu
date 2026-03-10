package GUI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import BUS.NhanVien_BUS;
import BUS.Phongban_BUS;
import BUS.DuAn_BUS;
import BUS.HopDong_BUS;
import BUS.ThongBao_BUS; 
import DTO.ThongBao_DTO; 
import org.jfree.chart.*;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TrangChu_GUI extends JPanel {
    NhanVien_BUS busNv = new NhanVien_BUS();
    Phongban_BUS busPb = new Phongban_BUS();
    DuAn_BUS busDa = new DuAn_BUS();
    HopDong_BUS busHd = new HopDong_BUS();
    ThongBao_BUS busTb = new ThongBao_BUS(); 
    
    
    private DefaultTableModel modelAlert;
    private JTable tableAlert;
    
    public TrangChu_GUI() {
        setLayout(new BorderLayout(20, 20)); 
        setBackground(new Color(240, 242, 245)); 
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel pnCards = new JPanel(new GridLayout(1, 4, 20, 0)); 
        pnCards.setBackground(new Color(240, 242, 245));
        pnCards.setPreferredSize(new Dimension(0, 130)); 

        pnCards.add(taoTheThongKe("Tổng Nhân Viên", busNv.soLuongNhanVien(), new Color(74, 144, 226)));   
        pnCards.add(taoTheThongKe("Phòng Ban", busPb.soLuongPhongban(), new Color(80, 227, 194)));          
        pnCards.add(taoTheThongKe("Dự Án Đang Chạy",  busDa.soLuongDuAnDangThucHien(), new Color(245, 166, 35)));
        pnCards.add(taoTheThongKe("Hợp Đồng Sắp Hết", busHd.soLuongHopDongHetHan(), new Color(208, 2, 27)));    

        add(pnCards, BorderLayout.NORTH);

        JPanel pnContent = new JPanel(new GridLayout(1, 2, 20, 0)); 
        pnContent.setBackground(new Color(240, 242, 245));

        JPanel pnChart = new JPanel(new BorderLayout());
        pnChart.setBackground(Color.WHITE);
        pnChart.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(10, 10, 10, 10)
        ));
        
        JPanel pnBieuDo = new JPanel(new GridLayout(2, 2, 10, 10));
        pnBieuDo.setBackground(Color.WHITE);

        pnBieuDo.add(taoChartPanel(busPb.layThongKeGioiTinhToanCongTy(), "Tỷ lệ Giới tính"));
        pnBieuDo.add(taoChartPanel(busPb.layThongKeChucVuToanCongTy(), "Cơ cấu Chức vụ"));
        pnBieuDo.add(taoChartPanel(busPb.layThongKeDoTuoiToanCongTy(), "Độ tuổi"));

        pnChart.add(pnBieuDo, BorderLayout.CENTER);

        JPanel pnAlerts = new JPanel(new BorderLayout(0, 10));
        pnAlerts.setBackground(Color.WHITE);
        pnAlerts.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblAlertTitle = new JLabel("Thông báo & Nhắc nhở");
        lblAlertTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnAlerts.add(lblAlertTitle, BorderLayout.NORTH);

        
        String[] colsAlert = {"Ngày đăng", "Nội dung thông báo"};
        modelAlert = new DefaultTableModel(colsAlert, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } 
        };
        
        tableAlert = new JTable(modelAlert);
        tableAlert.setRowHeight(35); 

        tableAlert.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableAlert.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableAlert.setShowVerticalLines(false);
        
        tableAlert.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableAlert.getColumnModel().getColumn(0).setMaxWidth(120);

        
        capNhatDuLieuThongBao();
        
        
        tableAlert.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int row = tableAlert.getSelectedRow();
                    if (row >= 0) {
                        String ngay = modelAlert.getValueAt(row, 0).toString();
                        String noiDung = modelAlert.getValueAt(row, 1).toString();
                        
                        JTextArea jta = new JTextArea(noiDung);
                        jta.setEditable(false);
                        jta.setLineWrap(true);
                        jta.setWrapStyleWord(true);
                        jta.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                        jta.setMargin(new Insets(10, 10, 10, 10));
                        
                        JScrollPane scrollPane = new JScrollPane(jta);
                        scrollPane.setPreferredSize(new Dimension(400, 200));
                        
                        JOptionPane.showMessageDialog(pnAlerts, scrollPane, "Thông báo ngày " + ngay, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        pnAlerts.add(new JScrollPane(tableAlert), BorderLayout.CENTER);
        pnContent.add(pnChart);
        pnContent.add(pnAlerts);

        add(pnContent, BorderLayout.CENTER);
        
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                
                capNhatDuLieuThongBao();
            }
        });
    }

    
    public void capNhatDuLieuThongBao() {
        if (modelAlert != null) {
            modelAlert.setRowCount(0); 
            List<ThongBao_DTO> danhSachTB = busTb.layDanhSachThongBao();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            for (ThongBao_DTO tb : danhSachTB) {
                String strNgay = (tb.getNgayTao() != null) ? tb.getNgayTao().format(dtf) : "";
                modelAlert.addRow(new Object[]{strNgay, tb.getNoiDung()});
            }
        }
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
    
    private ChartPanel taoChartPanel(DefaultPieDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createRingChart(title, dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        org.jfree.chart.title.LegendTitle legend = chart.getLegend();
        if (legend != null) {
            Font legendFont = new Font("Arial", Font.BOLD, 14); 
            legend.setItemFont(legendFont);
            legend.setItemPaint(Color.DARK_GRAY); 
        }
        
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}")); 
        plot.setLabelFont(new Font("SansSerif", Font.BOLD, 12));
        plot.setLabelBackgroundPaint(new Color(255, 255, 255, 0)); 
        plot.setLabelOutlinePaint(null); 
        plot.setLabelShadowPaint(null); 
        
        plot.setBackgroundPaint(Color.WHITE);
        plot.setSectionOutlinesVisible(false);
        
        return new ChartPanel(chart);
    }
}