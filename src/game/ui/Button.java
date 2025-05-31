package game.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The Button class is for custom button with preset parameters.
 */
public class Button extends JButton {

    /**
     * Constructs a new Button with the given text, position, size, and action listener.
     *
     * @param name   the text to display on the button.
     * @param x      the x-coordinate of the button's position.
     * @param y      the y-coordinate of the button's position.
     * @param width  the width of the button.
     * @param height the height of the button.
     * @param action the ActionListener to be called when the button is pressed (can be null).
     */
    public Button(String name, int x, int y, int width, int height, ActionListener action) {
        setText(name);
        setBounds(x, y, width, height);
        setFocusable(false);
        setContentAreaFilled(false);
        setForeground(Color.BLACK);
        setFont(new Font("Arial", Font.BOLD, 30));
       if (action != null) {
           addActionListener(action);
       }
    }
}
