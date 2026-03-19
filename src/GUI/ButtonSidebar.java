package GUI;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.BorderFactory;
import java.awt.Font;

public class ButtonSidebar extends JButton {

    public ButtonSidebar(String text, ImageIcon icon) {
        
        Color normal = new Color(228, 233, 242); 
        Color hover  = new Color(200, 210, 228); 
        
        Color textColor = new Color(30, 41, 59);

        setText(text);
        setIcon(icon);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        
        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
        
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setHorizontalAlignment(SwingConstants.LEFT); 
        
        
        setBackground(normal); 
        setForeground(textColor); 
        
        
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               setBackground(hover); // Đổi nền khi chuột vào
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(normal); // Trả lại nền cũ khi chuột ra
            }
        });
    }
}