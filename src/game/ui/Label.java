package game.ui;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {


    public Label(String name, int x, int y, int width, int height) {
        setText(name);
        setBounds(x, y, width, height);
        setOpaque(true);
        setFont(new Font("Arial", Font.BOLD, 35));
        setBackground(Color.LIGHT_GRAY);
        setForeground(Color.BLACK);
        setHorizontalAlignment(JLabel.CENTER);

    }
}
