package game.ui;

import game.assets.AssetLoader;
import game.map.PanelType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TutorialPanel extends JPanel {

    private MainWindow mainWindow;

    private Button lobbyButton;
    private BufferedImage tutorialImage;
    private JLabel tutorialLabel;

    public TutorialPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        tutorialImage = AssetLoader.loadImage("src/game/assets/Tutorial.png");

        initialize();
        initializeTutorialLabel();
    }

    private void initialize(){
        setLayout(null);
        setSize(1600,900);
        setBackground(Color.WHITE);
        setVisible(true);

        lobbyButton = new Button("LOBBY", 700,700,200,100, e ->mainWindow.switchPanel(PanelType.LOBBY_PANEL));
        add(lobbyButton);
    }

    private void initializeTutorialLabel(){
        tutorialLabel = new JLabel(new ImageIcon(tutorialImage));
        tutorialLabel.setBounds(100,0,tutorialImage.getWidth(),tutorialImage.getHeight());
        add(tutorialLabel);
    }
}
