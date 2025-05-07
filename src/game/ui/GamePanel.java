package game.ui;

import game.*;
import game.entities.Tower;
import game.entities.TowerType;
import game.managers.TowerManager;
import game.map.MapLoader;
import game.map.PanelType;
import game.map.TileType;

import java.awt.event.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class GamePanel extends JPanel {

    private MainWindow mainWindow;
    private MapLoader mapLoader;
    private GameLogic gameLogic;
    private TowerManager towerManager;

    private Button lobbyButton, startButton;


    private TowerType selectedTowerType = null;
    private Button tower1Button, tower2Button;
    private Timer timer;

    public GamePanel(MainWindow mainWindow, MapLoader mapLoader) {
        this.mainWindow = mainWindow;
        this.mapLoader = mapLoader;
        towerManager = new TowerManager();

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

        tower1Button = new Button("TOWER1", 1200, 0 , 200, 200, e -> selectedTowerType = TowerType.BASIC);
        add(tower1Button);
        tower2Button = new Button("TOWER2", 1400, 0 , 200, 200, e -> selectedTowerType = TowerType.SNIPER);
        add(tower2Button);



        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int tileSize = mapLoader.getTILE_SIZE();
                int gridX = e.getX() / tileSize;
                int gridY = e.getY() / tileSize;
                TileType tileType = mapLoader.getTileType(gridX, gridY);

                if (tileType == TileType.GRASS) {
                    Tower newTower = towerManager.createTower(selectedTowerType, gridX, gridY, tileSize);
                    towerManager.addTower(newTower);
                    selectedTowerType = null;
                    System.out.println("Pridana vezicka");
                }
                System.out.println("Nelze pridat");
            }
        });

    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        mapLoader.paintComponent(g);
        gameLogic.draw(g);
        towerManager.draw(g);
    }
}
