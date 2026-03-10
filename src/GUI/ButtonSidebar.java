
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
        Color normal = new Color(120, 190, 240);
        Color hover  = new Color(90, 170, 230);

        setText(text);
        setIcon(icon);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBackground(normal); 
        setOpaque(true);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setForeground(Color.black);
        
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(normal);
            }
        });
    }
    
}
