
package GUI.button;

import javax.swing.JButton;
import java.awt.Color;

public class ButtonToolBar extends JButton{
    public ButtonToolBar(String text){
        setText(text);
        setFocusPainted(false);
        setBorderPainted(false);
        
        Color normal = new Color(33,150,243);
        Color hover = new Color(21,101,192);
        
        setBackground(normal);
        setForeground(Color.WHITE);

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
