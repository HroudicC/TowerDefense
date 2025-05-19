package game.ui;

import game.*;
import game.entities.Tower;
import game.entities.TowerType;
import game.entities.Wave;
import game.managers.WaveManager;
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

    private Button lobbyButton, startButton;
    private TowerType selectedTowerType = null;
    private Button tower1Button, tower2Button, tower3Button;
    private Label waveLabel, moneyLabel;
    private Timer timer;

    public GamePanel(MainWindow mainWindow, MapLoader mapLoader) {
        this.mainWindow = mainWindow;
        this.mapLoader = mapLoader;
        gameLogic = new GameLogic(mapLoader);

        setLayout(null);
        setSize(1600, 900);
        setBackground(Color.LIGHT_GRAY);
        setFocusable(true);
        setVisible(true);

        initializeTimer();
        initializeUI();
        initializeMouseListener();

    }

    public void initializeTimer(){
        timer = new Timer(16, e -> {
            gameLogic.update();
            repaint();
        });
        timer.start();
    }

    public void initializeUI(){
        startButton = new Button("START", 1200, 750, 200, 125, e -> {
            gameLogic.startNextWave();
            updateWaveLabel();
        });
        add(startButton);

        lobbyButton = new Button("LOBBY", 1400, 750, 200, 125, e -> mainWindow.switchPanel(PanelType.LOBBY_PANEL));
        add(lobbyButton);

        tower1Button = new Button("BASIC", 1200, 0, 200, 200, e -> selectedTowerType = TowerType.BASIC);
        add(tower1Button);

        tower2Button = new Button("SNIPER", 1400, 0, 200, 200, e -> selectedTowerType = TowerType.SNIPER);
        add(tower2Button);

        tower3Button = new Button("BOXER", 1200, 200, 200, 200, e -> selectedTowerType = TowerType.BOXER);
        add(tower3Button);


        waveLabel = new Label("WAVE: " + 0, 0, 750, 400,125);
        add(waveLabel);

        moneyLabel = new Label("MONEY: " + gameLogic.getMoneyManager().getMoney() + "$", 400, 750, 400,125);
        add(moneyLabel);
    }

    public void initializeMouseListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int tileSize = mapLoader.getTILE_SIZE();
                int gridX = e.getX() / tileSize;
                int gridY = e.getY() / tileSize;
                TileType tileType = mapLoader.getTileType(gridX, gridY);

                if (selectedTowerType == null) {
                    return;
                }
                if (tileType != TileType.GRASS) {
                    System.out.println("Nelze přidat na toto pole");
                    return;
                }
                if (gameLogic.getTowerManager().isTowerAt(gridX, gridY, tileSize)) {
                    System.out.println("Na tomto poli uz je vez");
                    return;
                }
                Tower newTower = gameLogic.getTowerManager().createTower(selectedTowerType, gridX, gridY, tileSize);
                boolean bought = gameLogic.getTowerManager().addTower(newTower);
                if (bought) {
                    System.out.println("Přidána věž: " + selectedTowerType);
                }
                updateMoneyLabel();
                selectedTowerType = null;
            }
        });
    }

    public void updateWaveLabel(){
        waveLabel.setText("WAVE: " + gameLogic.getWaveManager().getCurrentWaveNumber());
    }

    public void updateMoneyLabel(){
        moneyLabel.setText("MONEY: " + gameLogic.getMoneyManager().getMoney() + "$");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        gameLogic.draw(g);
    }
}
