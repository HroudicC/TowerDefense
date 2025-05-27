package game.ui;

import game.*;
import game.config.TowerConfigLoader;
import game.config.TowerInfo;
import game.entities.Tower;
import game.entities.TowerType;
import game.map.MapLoader;
import game.map.PanelType;
import game.map.TileType;

import java.awt.event.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.HashMap;

public class GamePanel extends JPanel {

    private MainWindow mainWindow;
    private MapLoader mapLoader;
    private GameLogic gameLogic;

    private Button lobbyButton, startButton;
    private TowerType selectedTowerType = null;
    private Button tower1Button, tower2Button, tower3Button, tower4Button, tower5Button, tower6Button;
    private Label waveLabel, moneyLabel, healthLabel, storeLabel;
    private JTextArea informationTextArea;
    private Timer timer;

    private HashMap<String, TowerInfo> towerConfigs;

    public GamePanel(MainWindow mainWindow, MapLoader mapLoader) {
        this.mainWindow = mainWindow;
        this.mapLoader = mapLoader;
        gameLogic = new GameLogic(mapLoader, this);
        towerConfigs = TowerConfigLoader.loadTowerConfigs("src/game/config/TowerTypeInfo");

        setLayout(null);
        setSize(1600, 900);
        setBackground(Color.LIGHT_GRAY);
        setFocusable(true);
        setVisible(true);

        initializeTimer();
        initializeUI();
        initializeMouseListener();
        initializeInformationTextArea();
    }

    public void initializeTimer(){
        timer = new Timer(16, e -> {
            gameLogic.update();
            repaint();
        });
        timer.start();
    }

    public void initializeInformationTextArea(){
        informationTextArea = new JTextArea();
        informationTextArea.setForeground(Color.BLACK);
        informationTextArea.setBackground(Color.LIGHT_GRAY);
        informationTextArea.setFont(new Font("Arial", Font.BOLD, 18));
        informationTextArea.setBounds(900,750,300,125);
        informationTextArea.setEditable(false);
        informationTextArea.setLineWrap(true);
        informationTextArea.setWrapStyleWord(true);
        add(informationTextArea);
    }

    public void initializeUI(){
        startButton = new Button("START", 1200, 750, 200, 125, e -> {
            gameLogic.startNextWave();
            updateWaveLabel();
        });
        add(startButton);

        lobbyButton = new Button("LOBBY", 1400, 750, 200, 125, e -> mainWindow.switchPanel(PanelType.LOBBY_PANEL));
        add(lobbyButton);

        tower1Button = new Button("BASIC", 1200, 150, 200, 200, e -> selectedTowerType = TowerType.BASIC);
        tower1Button.addMouseListener(createTowerInfoListener("BASIC"));
        add(tower1Button);

        tower2Button = new Button("SNIPER", 1200, 350, 200, 200, e -> selectedTowerType = TowerType.SNIPER);
        tower2Button.addMouseListener(createTowerInfoListener("SNIPER"));
        add(tower2Button);

        tower3Button = new Button("CANNON", 1200, 550, 200, 200, e -> selectedTowerType = TowerType.CANNON);
        tower3Button.addMouseListener(createTowerInfoListener("CANNON"));
        add(tower3Button);

        tower4Button = new Button("LASER", 1400, 150, 200, 200, e -> selectedTowerType = TowerType.LASER);
        tower4Button.addMouseListener(createTowerInfoListener("LASER"));
        add(tower4Button);

        tower5Button = new Button("ROCKET", 1400, 350, 200, 200, e -> selectedTowerType = TowerType.ROCKET);
        tower5Button.addMouseListener(createTowerInfoListener("ROCKET"));
        add(tower5Button);

        tower6Button = new Button("MINIGUN", 1400, 550, 200, 200, e -> selectedTowerType = TowerType.MINIGUN);
        tower6Button.addMouseListener(createTowerInfoListener("MINIGUN"));
        add(tower6Button);


        waveLabel = new Label("WAVE: " + 0, 0, 750, 300,125);
        add(waveLabel);

        moneyLabel = new Label("MONEY: " + gameLogic.getMoneyManager().getMoney() + "$", 300, 750, 300,125);
        add(moneyLabel);

        healthLabel = new Label("HEALTH: " + gameLogic.getLifeManager().getLives(), 600, 750, 300,125);
        add(healthLabel);


        storeLabel = new Label("STORE", 1200,0,400,150);
        storeLabel.setFont(new Font("Arial", Font.BOLD, 50));
        add(storeLabel);
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

    private MouseAdapter createTowerInfoListener(String towerType){
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                TowerInfo info = towerConfigs.get(towerType);
                if (info != null) {
                    informationTextArea.setText(info.getInfo());
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                informationTextArea.setText("");
            }
        };
    }

    public void updateWaveLabel(){
        waveLabel.setText("WAVE: " + gameLogic.getWaveManager().getCurrentWaveNumber());
    }

    public void updateMoneyLabel(){
        moneyLabel.setText("MONEY: " + gameLogic.getMoneyManager().getMoney() + "$");
    }

    public void updateHealthLabel(){
        healthLabel.setText("HEALTH: " + gameLogic.getLifeManager().getLives());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        gameLogic.draw(g);
    }
}
