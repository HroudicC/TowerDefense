import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    CardLayout cardLayout;
    JPanel container;

    public MainWindow(){
        initialize();
    }

    private void initialize() {
        setTitle("Tower Defense");
        setSize(1600, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void createUIComponents() {
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        add(container);
    }
}
