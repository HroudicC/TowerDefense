package game.ui;

import javax.swing.*;
import java.awt.*;

/**
 * The Label class is for custom label with preset parameters.
 */
public class Label extends JLabel {

    /**
     * Constructs a new Label with the given text, position, and size.
     *
     * @param name  the text to display on the label.
     * @param x     the x-coordinate of the label's position.
     * @param y     the y-coordinate of the label's position.
     * @param width the width of the label.
     * @param height the height of the label.
     */
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
