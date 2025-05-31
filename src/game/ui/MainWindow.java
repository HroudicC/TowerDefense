package game.ui;

import game.map.MapLoader;
import game.map.PanelType;

import javax.swing.*;
import java.awt.*;

/**
 * MainWindow is the main frame of the whole game.
 * It uses a card layout to switch between different panels like the lobby, game, and tutorial.
 */
public class MainWindow extends JFrame {

    private CardLayout cardLayout;
    private JPanel container;

    private LobbyPanel lobbyPanel;
    private TutorialPanel tutorialPanel;
    private GamePanel gamePanel;
    private MapLoader mapLoader;

    /**
     * Constructs a new MainWindow.
     * It sets up the basic properties, creates the UI components, and displays the lobby panel by default.
     */
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

    /**
     * Creates and initializes the user interface components.
     * It creates instances of MapLoader, LobbyPanel, TutorialPanel, and GamePanel
     * and then adds these panels to the container using a card layout.
     */
    private void createUIComponents() {
        mapLoader = new MapLoader();
        lobbyPanel = new LobbyPanel(this);
        tutorialPanel = new TutorialPanel(this);
        gamePanel = new GamePanel(this, mapLoader);

        container.add(lobbyPanel, String.valueOf(PanelType.LOBBY_PANEL));
        container.add(gamePanel, String.valueOf(PanelType.GAME_PANEL));
        container.add(tutorialPanel, String.valueOf(PanelType.TUTORIAL_PANEL));
    }

    /**
     * Switches the displayed panel.
     * The panel to display is selected based on the given PanelType.
     *
     * @param panel the panel type to switch to.
     */
    public void switchPanel(PanelType panel) {
        cardLayout.show(container, String.valueOf(panel));
    }
}
