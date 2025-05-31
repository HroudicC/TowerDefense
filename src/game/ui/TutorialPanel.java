package game.ui;

import game.assets.AssetLoader;
import game.map.PanelType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The TutorialPanel class represents a panel that displays tutorial content.
 * It shows a tutorial image and lobby button that allows the user to return to the lobby.
 */
public class TutorialPanel extends JPanel {

    private MainWindow mainWindow;

    private Button lobbyButton;
    private BufferedImage tutorialImage;
    private JLabel tutorialLabel;

    /**
     * Constructs a new TutorialPanel.
     * It loads a tutorial image from static class and initializes the panel components.
     *
     * @param mainWindow the main window used to switch between panels
     */
    public TutorialPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        tutorialImage = AssetLoader.loadImage("/game/assets/Tutorial.png");

        initialize();
        initializeTutorialLabel();
    }

    /**
     * Initializes the panel layout and the lobby button.
     */
    private void initialize(){
        setLayout(null);
        setSize(1600,900);
        setBackground(Color.WHITE);
        setVisible(true);

        lobbyButton = new Button("LOBBY", 700,700,200,100, e ->mainWindow.switchPanel(PanelType.LOBBY_PANEL));
        lobbyButton.setContentAreaFilled(false);
        add(lobbyButton);
    }

    /**
     * Initializes the tutorial label with the tutorial image.
     */
    private void initializeTutorialLabel(){
        tutorialLabel = new JLabel(new ImageIcon(tutorialImage));
        tutorialLabel.setBounds(100,0,tutorialImage.getWidth(),tutorialImage.getHeight());
        add(tutorialLabel);
    }
}
