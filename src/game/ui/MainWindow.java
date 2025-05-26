package game.ui;

import game.map.MapLoader;
import game.map.PanelType;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private CardLayout cardLayout;
    private JPanel container;

    private LobbyPanel lobbyPanel;
    private TutorialPanel tutorialPanel;
    private GamePanel gamePanel;
    private MapLoader mapLoader;

    public MainWindow(){
        setTitle("Tower Defense");
        setSize(1600, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        createUIComponents();
        add(container);

        cardLayout.show(container, String.valueOf(PanelType.LOBBY_PANEL));

        setVisible(true);
    }

    private void createUIComponents() {
        mapLoader = new MapLoader();
        lobbyPanel = new LobbyPanel(this);
        tutorialPanel = new TutorialPanel(this);
        gamePanel = new GamePanel(this, mapLoader);

        container.add(lobbyPanel, String.valueOf(PanelType.LOBBY_PANEL));
        container.add(gamePanel, String.valueOf(PanelType.GAME_PANEL));
        container.add(tutorialPanel, String.valueOf(PanelType.TUTORIAL_PANEL));
    }

    public void switchPanel(PanelType panel) {
        cardLayout.show(container, String.valueOf(panel));
    }
}
