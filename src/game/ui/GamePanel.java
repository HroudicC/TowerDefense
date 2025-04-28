package game.ui;

import game.*;
import game.map.MapLoader;
import game.map.PanelType;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private MainWindow mainWindow;
    private MapLoader mapLoader;
    private GameLogic gameLogic;

    private Button lobbyButton, startButton;
    private Timer timer;

    public GamePanel(MainWindow mainWindow, MapLoader mapLoader) {
        this.mainWindow = mainWindow;
        this.mapLoader = mapLoader;

        setLayout(null);
        setSize(1600, 900);
        setBackground(Color.LIGHT_GRAY);

        setFocusable(true);
        setVisible(true);

        gameLogic = new GameLogic(mapLoader);

        startButton = new Button("START", 1200, 775, 200,100, e -> start());
        add(startButton);

        lobbyButton = new Button("LOBBY", 1400, 775, 200, 100, e -> mainWindow.switchPanel(PanelType.LOBBY_PANEL));
        add(lobbyButton);

    }

    private void start() {
        timer = new Timer(16, e -> {
            gameLogic.update();
            repaint();
        });
        timer.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        mapLoader.paintComponent(g);
        gameLogic.draw(g);
    }
}
