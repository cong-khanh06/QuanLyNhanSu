package GUI;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import BUS.Phongban_BUS;

public class PhongbanThongKe_GUI extends JDialog {
    private Phongban_BUS pbb = new Phongban_BUS();

    public PhongbanThongKe_GUI(String mapb, String tenpb) {
        setTitle("Thống kê nhân sự");
        setSize(1100, 500);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());
        JPanel pnNorth = new JPanel(new BorderLayout());
        pnNorth.setBackground(new Color(0, 153, 153)); 

        JLabel lblHeader = new JLabel("THỐNG KÊ: " + tenpb.toUpperCase(), SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        pnNorth.add(lblHeader, BorderLayout.CENTER);
        add(pnNorth, BorderLayout.NORTH);

        JPanel pnlCharts = new JPanel(new GridLayout(1, 3, 20, 0));
        pnlCharts.setBackground(Color.WHITE);

        
        pnlCharts.add(taoChartPanel(pbb.layThongKeGioiTinh(mapb), "Tỷ lệ Giới tính"));
        
        pnlCharts.add(taoChartPanel(pbb.layThongKeChucVu(mapb), "Cơ cấu Chức vụ"));
        
        pnlCharts.add(taoChartPanel(pbb.layThongKeDoTuoi(mapb), "Độ tuổi"));


        add(pnlCharts, BorderLayout.CENTER);
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