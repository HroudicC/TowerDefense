package game.ui;

import game.assets.AssetLoader;
import game.map.PanelType;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * The LobbyPanel class represents the lobby screen of the game.
 * It displays a cover image and contains buttons to switch between the GamePanel, TutorialPanel and Exit.
 */
public class LobbyPanel extends JPanel {

    private MainWindow mainWindow;

    private Button gameButton, tutorialButton, exitButton;
    private JLabel coverLabel;
    private BufferedImage lobbyImage, gameImage, tutorialImage, exitImage;

    /**
     * Constructs a new LobbyPanel.
     * It loads the necessary images, initializes the panel, creates the buttons, and displays the lobby cover image.
     *
     * @param mainWindow the MainWindow instance used for switching panels.
     */
    public LobbyPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        lobbyImage = AssetLoader.loadImage("/game/assets/Cover.png");
        gameImage = AssetLoader.loadImage("/game/assets/GameImage.png");
        tutorialImage = AssetLoader.loadImage("/game/assets/TutorialImage.png");
        exitImage = AssetLoader.loadImage("/game/assets/ExitImage.png");
        initialize();
        createButtons();
        initializeLobbyImage();
        setVisible(true);
    }

    /**
     * Initializes the panel.
     */
    private void initialize() {
        setSize(1600, 900);
        setLayout(null);

    }

    /**
     * Creates and configures the buttons for game, tutorial, and exit.
     * Each button is enhanced with an icon set and removed border.
     */
    private void createButtons() {
         gameButton = new Button("GAME",300,450,400,200, e -> mainWindow.switchPanel(PanelType.GAME_PANEL));
         gameButton.setIcon(new ImageIcon(gameImage));
         gameButton.setBorder(null);
         gameButton.setFocusPainted(false);
         gameButton.setOpaque(false);
        add(gameButton);

         tutorialButton = new Button("TUTORIAL",900,450,400,200, e -> mainWindow.switchPanel(PanelType.TUTORIAL_PANEL));
         tutorialButton.setIcon(new ImageIcon(tutorialImage));
         tutorialButton.setBorder(null);
         tutorialButton.setFocusPainted(false);
         tutorialButton.setOpaque(false);
        add(tutorialButton);

         exitButton = new Button("EXIT",650,700,300,100, e -> System.exit(0));
         exitButton.setIcon(new ImageIcon(exitImage));
         exitButton.setBorder(null);
         exitButton.setFocusPainted(false);
         exitButton.setOpaque(false);
        add(exitButton);

    }

    /**
     * Initializes the cover image label using the loaded lobby image.
     */
    private void initializeLobbyImage(){
        coverLabel = new JLabel(new ImageIcon(lobbyImage));
        coverLabel.setSize(lobbyImage.getWidth(), lobbyImage.getHeight());
        add(coverLabel);
    }
}
