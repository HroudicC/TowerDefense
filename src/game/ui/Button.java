package game.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends JButton {

    public Button(String name, int x, int y, int width, int height, ActionListener action) {
        setText(name);
        setBounds(x, y, width, height);
        setFocusable(false);
        setForeground(Color.BLACK);
        setFont(new Font("Arial", Font.BOLD, 30));
       if (action != null) {
           addActionListener(action);
       }
    }
}
