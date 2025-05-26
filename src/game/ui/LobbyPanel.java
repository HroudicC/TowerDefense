package game.ui;

import game.map.PanelType;

import javax.swing.*;

public class LobbyPanel extends JPanel {

    private MainWindow mainWindow;

    private Button gameButton;
    private Button tutorialButton;
    private Button exitButton;

    public LobbyPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        initialize();
        createButtons();

        setVisible(true);
    }

    private void initialize() {
        setSize(1600, 900);
        setLayout(null);

    }

    private void createButtons() {
         gameButton = new Button("GAME",300,350,400,200, e -> mainWindow.switchPanel(PanelType.GAME_PANEL));
        add(gameButton);

         tutorialButton = new Button("TUTORIAL",900,350,400,200, e -> mainWindow.switchPanel(PanelType.TUTORIAL_PANEL));
        add(tutorialButton);

         exitButton = new Button("EXIT",650,600,300,100, e -> System.exit(0));
        add(exitButton);

    }
}
