package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalendarGrid extends JDialog {
    private JTextField target;
    private JPanel pDays;
    private JLabel lblMonthYear;
    private Calendar cal = Calendar.getInstance();
    // Đổi định dạng thành yyyy-MM-dd để dễ lưu thẳng vào Database
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    
    // Constructor nhận vào một JTextField để sau khi chọn ngày sẽ điền chữ vào đó
    public CalendarGrid(JTextField target) {
        this.target = target;
        setTitle("Chọn Ngày");
        setModal(true);
        setSize(350, 320);
        setLocationRelativeTo(target);
        setLayout(new BorderLayout());
        
        String existingDate = target.getText().trim();
        if (!existingDate.isEmpty()) {
            try {
                // Dùng sdf (đã khai báo yyyy-MM-dd) để ép kiểu chuỗi thành ngày
                java.util.Date parsedDate = sdf.parse(existingDate);
                // Cài đặt lịch lùi về đúng ngày vừa đọc được
                cal.setTime(parsedDate);
                target.setText(sdf.format(cal.getTime()));
            } catch (Exception ex) {
                // Nếu ô text rỗng hoặc sai định dạng thì bỏ qua, lịch tự lấy ngày hôm nay
            }
        }
        
        JPanel pHeader = new JPanel(new BorderLayout());
        JButton btnPrev = new JButton("<<");
        JButton btnNext = new JButton(">>");
        lblMonthYear = new JLabel("", JLabel.CENTER);

        btnPrev.addActionListener(e -> { cal.add(Calendar.MONTH, -1); updateCalendar(); });
        btnNext.addActionListener(e -> { cal.add(Calendar.MONTH, 1); updateCalendar(); });

        pHeader.add(btnPrev, BorderLayout.WEST);
        pHeader.add(lblMonthYear, BorderLayout.CENTER);
        pHeader.add(btnNext, BorderLayout.EAST);

        pDays = new JPanel(new GridLayout(0, 7));
        updateCalendar();

        add(pHeader, BorderLayout.NORTH);
        add(pDays, BorderLayout.CENTER);
    }

    private void updateCalendar() {
        pDays.removeAll();
        String[] header = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
        for (String h : header) {
            JLabel l = new JLabel(h, JLabel.CENTER);
            l.setForeground(Color.RED);
            pDays.add(l);
        }
        
        lblMonthYear.setText(new SimpleDateFormat("'Tháng' MM, yyyy", new Locale("vi")).format(cal.getTime()));
        Calendar temp = (Calendar) cal.clone();
        temp.set(Calendar.DAY_OF_MONTH, 1);
        int startDay = temp.get(Calendar.DAY_OF_WEEK) - 1;

        for (int i = 0; i < startDay; i++) {
            pDays.add(new JLabel(""));
        }

        int days = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= days; i++) {
            final int day = i;
            JButton btn = new JButton(String.valueOf(i));
            btn.addActionListener(e -> {
                cal.set(Calendar.DAY_OF_MONTH, day);
                // Ghi ngày vừa chọn vào ô JTextField
                target.setText(sdf.format(cal.getTime()));
                dispose(); // Đóng cửa sổ lịch
            });
            pDays.add(btn);
        }
        pDays.revalidate();
        pDays.repaint();
    }
}