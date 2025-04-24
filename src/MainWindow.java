import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    CardLayout cardLayout;
    JPanel container;

    LobbyPanel lobbyPanel;
    TutorialPanel tutorialPanel;
    GamePanel gamePanel;
    MapLoader mapLoader;

    public MainWindow(){
        initialize();
        createUIComponents();
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

        mapLoader = new MapLoader();
        lobbyPanel = new LobbyPanel(this);
        tutorialPanel = new TutorialPanel(this);
        gamePanel = new GamePanel(this, mapLoader);

        add(container);
        cardLayout.show(container, String.valueOf(PanelType.LOBBY_PANEL));
        container.add(lobbyPanel, String.valueOf(PanelType.LOBBY_PANEL));
        container.add(gamePanel, String.valueOf(PanelType.GAME_PANEL));
        container.add(tutorialPanel, String.valueOf(PanelType.TUTORIAL_PANEL));


    }

    public void switchPanel(PanelType panel) {
        cardLayout.show(container, String.valueOf(panel));
    }
}
