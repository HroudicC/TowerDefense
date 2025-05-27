package game.ui;

import game.assets.AssetLoader;
import game.map.PanelType;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class LobbyPanel extends JPanel {

    private MainWindow mainWindow;

    private Button gameButton;
    private Button tutorialButton;
    private Button exitButton;
    private JLabel coverLabel;
    private BufferedImage lobbyImage, gameImage, tutorialImage, exitImage;

    public LobbyPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        lobbyImage = AssetLoader.loadImage("src/game/assets/Cover.png");
        gameImage = AssetLoader.loadImage("src/game/assets/GameImage.png");
        tutorialImage = AssetLoader.loadImage("src/game/assets/TutorialImage.png");
        exitImage = AssetLoader.loadImage("src/game/assets/ExitImage.png");
        initialize();
        createButtons();
        initializeLobbyImage();

        setVisible(true);
    }

    private void initialize() {
        setSize(1600, 900);
        setLayout(null);

    }

    private void createButtons() {
         gameButton = new Button("GAME",300,450,400,200, e -> mainWindow.switchPanel(PanelType.GAME_PANEL));
         gameButton.setIcon(new ImageIcon(gameImage));
         gameButton.setBorder(null);
        add(gameButton);

         tutorialButton = new Button("TUTORIAL",900,450,400,200, e -> mainWindow.switchPanel(PanelType.TUTORIAL_PANEL));
         tutorialButton.setIcon(new ImageIcon(tutorialImage));
         tutorialButton.setBorder(null);
        add(tutorialButton);

         exitButton = new Button("EXIT",650,700,300,100, e -> System.exit(0));
         exitButton.setIcon(new ImageIcon(exitImage));
         exitButton.setBorder(null);
        add(exitButton);

    }

    private void initializeLobbyImage(){
        coverLabel = new JLabel(new ImageIcon(lobbyImage));
        coverLabel.setSize(lobbyImage.getWidth(), lobbyImage.getHeight());
        add(coverLabel);
    }
}
