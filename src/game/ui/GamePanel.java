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

        timer = new Timer(16, e -> {
            gameLogic.update();
            repaint();
        });
        timer.start();

        startButton = new Button("START", 1200, 750, 200,125, e -> gameLogic.startNextWave());
        add(startButton);

        lobbyButton = new Button("LOBBY", 1400, 750, 200, 125, e -> mainWindow.switchPanel(PanelType.LOBBY_PANEL));
        add(lobbyButton);

    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        mapLoader.paintComponent(g);
        gameLogic.draw(g);
    }
}
