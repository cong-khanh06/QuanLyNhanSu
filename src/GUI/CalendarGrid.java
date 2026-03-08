package GUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

public class CalendarGrid extends JDialog {
    private JTextField target;
    private JPanel pDays;
    private JComboBox<Integer> cbYear;
    private JComboBox<String> cbMonth;
    private Calendar cal = Calendar.getInstance();
    private boolean isUpdating = false;
    
    // ĐỔI THÀNH ĐỊNH DẠNG DATABASE: Năm-Tháng-Ngày
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public CalendarGrid(JTextField target) {
        this.target = target;
        setModal(true);
        setTitle("Chọn Ngày (yyyy-MM-dd)");
        setSize(400, 350);
        setLocationRelativeTo(target);
        setLayout(new BorderLayout());

        // --- Header: Điều hướng và ComboBox ---
        JPanel pHeader = new JPanel(new BorderLayout());
        pHeader.setBackground(new Color(240, 240, 240));
        pHeader.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton btnPrev = new JButton("<");
        JButton btnNext = new JButton(">");
        
        JPanel pCenterHeader = new JPanel(new FlowLayout());
        pCenterHeader.setOpaque(false);
        
        cbMonth = new JComboBox<>(new String[]{
            "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", 
            "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        });
        
        // Tạo danh sách năm từ 1950 đến 2050
        Integer[] years = new Integer[101];
        for (int i = 0; i <= 100; i++) years[i] = 1950 + i;
        cbYear = new JComboBox<>(years);

        pCenterHeader.add(cbMonth);
        pCenterHeader.add(cbYear);

        pHeader.add(btnPrev, BorderLayout.WEST);
        pHeader.add(pCenterHeader, BorderLayout.CENTER);
        pHeader.add(btnNext, BorderLayout.EAST);

        // Sự kiện nút bấm
        btnPrev.addActionListener(e -> { cal.add(Calendar.MONTH, -1); syncControls(); updateCalendar(); });
        btnNext.addActionListener(e -> { cal.add(Calendar.MONTH, 1); syncControls(); updateCalendar(); });

        ActionListener comboListener = e -> {
            if (!isUpdating) {
                cal.set(Calendar.MONTH, cbMonth.getSelectedIndex());
                cal.set(Calendar.YEAR, (Integer) cbYear.getSelectedItem());
                updateCalendar();
            }
        };
        cbMonth.addActionListener(comboListener);
        cbYear.addActionListener(comboListener);

        // --- Đọc ngày từ TextField (Định dạng yyyy-MM-dd) ---
        try {
            String text = target.getText().trim();
            if (!text.isEmpty()) {
                cal.setTime(sdf.parse(text));
            }
        } catch (Exception e) {
            // Nếu lỗi định dạng hoặc rỗng, mặc định lấy ngày hiện tại
            cal = Calendar.getInstance(); 
        }

        syncControls();
        pDays = new JPanel(new GridLayout(0, 7, 2, 2));
        pDays.setBackground(Color.WHITE);
        updateCalendar();

        add(pHeader, BorderLayout.NORTH);
        add(pDays, BorderLayout.CENTER);
    }

    private void syncControls() {
        isUpdating = true;
        cbMonth.setSelectedIndex(cal.get(Calendar.MONTH));
        cbYear.setSelectedItem(cal.get(Calendar.YEAR));
        isUpdating = false;
    }

    private void updateCalendar() {
        pDays.removeAll();
        
        // Vẽ tiêu đề thứ
        String[] heads = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
        for (String h : heads) {
            JLabel l = new JLabel(h, JLabel.CENTER);
            l.setFont(new Font("Segoe UI", Font.BOLD, 12));
            l.setForeground(h.equals("CN") ? Color.RED : Color.DARK_GRAY);
            pDays.add(l);
        }

        Calendar t = (Calendar) cal.clone();
        t.set(Calendar.DAY_OF_MONTH, 1);
        int startOffset = t.get(Calendar.DAY_OF_WEEK) - 1;
        int maxDays = t.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Ô trống đầu tháng
        for (int i = 0; i < startOffset; i++) {
            pDays.add(new JLabel(""));
        }

        // Các nút ngày
        for (int i = 1; i <= maxDays; i++) {
            final int day = i;
            JButton btn = new JButton(String.valueOf(i));
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setMargin(new Insets(2, 2, 2, 2));
            
            // Highlight ngày đang chọn
            if (i == cal.get(Calendar.DAY_OF_MONTH)) {
                btn.setBackground(new Color(63, 81, 181));
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            }

            btn.addActionListener(e -> {
                cal.set(Calendar.DAY_OF_MONTH, day);
                target.setText(sdf.format(cal.getTime())); // Xuất ra yyyy-MM-dd
                dispose();
            });
            pDays.add(btn);
        }

        pDays.revalidate();
        pDays.repaint();
    }
}