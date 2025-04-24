import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    MainWindow mainWindow;

    Button lobbyButton;

    public GamePanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        initialize();
    }

    private void initialize() {
        setLayout(null);
        setSize(1600, 900);
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);

        lobbyButton = new Button("LOBBY", 1400,700,200,200,e -> mainWindow.switchPanel(PanelType.LOBBY_PANEL));
        add(lobbyButton);
    }
}
