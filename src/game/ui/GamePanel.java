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

/**
 * The GamePanel class represents the main game view in the game.
 * It manages the game logic, timer updates, and the user interface for towers, labels, and buttons.
 * It also handles mouse events for placing towers on the map.
 */
public class GamePanel extends JPanel {

    private MainWindow mainWindow;
    private MapLoader mapLoader;
    private GameLogic gameLogic;

    private Button lobbyButton, startButton;
    private TowerType selectedTowerType = null;
    private Label waveLabel, moneyLabel, healthLabel, storeLabel;
    private JTextArea informationTextArea;
    private Timer timer;

    private HashMap<String, TowerInfo> towerConfigs;

    /**
     * Constructs a new GamePanel.
     * It initializes the game logic, loads tower configurations, sets up the layout,
     * and initializes timers, UI components, mouse listeners, and the information text area.
     *
     * @param mainWindow the main window to which this panel belongs.
     * @param mapLoader the MapLoader used to load and display the game map.
     */
    public GamePanel(MainWindow mainWindow, MapLoader mapLoader) {
        this.mainWindow = mainWindow;
        this.mapLoader = mapLoader;
        gameLogic = new GameLogic(mapLoader, this);
        towerConfigs = TowerConfigLoader.loadTowerConfigs("/game/config/TowerTypeInfo");

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

    /**
     * Initializes the game timer.
     * The timer calls the game logic update and repaints the panel every 16 milliseconds.
     */
    public void initializeTimer(){
        timer = new Timer(16, e -> {
            gameLogic.update();
            repaint();
        });
        timer.start();
    }

    /**
     * Initializes the information text area for information about towers.
     */
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

    /**
     * Creates a button to select a tower and adds it to the panel.
     *
     * @param name tower name, also used as a key to get information about the tower.
     * @param x horizontal position of the button in the panel.
     * @param y vertical position of the button in the panel.
     * @param type the type of tower that will be selected when the button is clicked.
     */
    private void createTowerButton(String name, int x, int y, TowerType type) {
        Button button = new Button(name, x, y, 200, 200, e -> selectedTowerType = type);
        button.addMouseListener(initializeTowerInfoListener(name));
        add(button);
    }


    /**
     * Initializes the user interface components.
     * This method sets all the buttons on the game panel, for instance to start the game, switch to the lobby and for all towers.
     * Every tower has a mouseListener for the information to the informationTextArea.
     */
    public void initializeUI(){
        startButton = new Button("START", 1200, 750, 200, 125, e -> {
            gameLogic.startNextWave();
            updateWaveLabel();
        });
        add(startButton);

        lobbyButton = new Button("LOBBY", 1400, 750, 200, 125, e -> mainWindow.switchPanel(PanelType.LOBBY_PANEL));
        add(lobbyButton);

        createTowerButton("BASIC", 1200, 150, TowerType.BASIC);
        createTowerButton("SNIPER", 1200, 350, TowerType.SNIPER);
        createTowerButton("CANNON", 1200, 550, TowerType.CANNON);
        createTowerButton("LASER", 1400, 150, TowerType.LASER);
        createTowerButton("ROCKET", 1400, 350, TowerType.ROCKET);
        createTowerButton("MINIGUN", 1400, 550, TowerType.MINIGUN);


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

    /**
     * Initializes the mouse listener for handling tower placement.
     * When the panel is clicked, it determines the grid position, checks if a tower can be placed, and if so, adds the tower.
     * It also updates the money label.
     * Source: ChatGPT helped me with this method.
     */
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
                    System.out.println("A tower cannot be placed on this tile.");
                    return;
                }
                if (gameLogic.getTowerManager().isTowerAt(gridX, gridY, tileSize)) {
                    System.out.println("A tower already stands on this tile.");
                    return;
                }
                Tower newTower = gameLogic.getTowerManager().createTower(selectedTowerType, gridX, gridY, tileSize);
                boolean bought = gameLogic.getTowerManager().addTower(newTower);
                if (bought) {
                    System.out.println(selectedTowerType + " tower has been added.");
                }
                updateMoneyLabel();
                selectedTowerType = null;
            }
        });
    }

    /**
     * Initializes a mouse adapter for tower information display.
     * When the mouse enters the tower button, the information text area is updated with details of the tower.
     * When the mouse exits, the text is cleared.
     *
     * @param towerType the type of the tower as a string.
     * @return a MouseAdapter that handles mouseEntered and mouseExited events.
     */
    private MouseAdapter initializeTowerInfoListener(String towerType){
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

    /**
     * Updates the wave label to show the current wave number.
     */
    public void updateWaveLabel(){
        waveLabel.setText("WAVE: " + gameLogic.getWaveManager().getCurrentWaveNumber());
    }

    /**
     * Updates the money label to show the current amount of money.
     */
    public void updateMoneyLabel(){
        moneyLabel.setText("MONEY: " + gameLogic.getMoneyManager().getMoney() + "$");
    }

    /**
     * Updates the health label to show the current number of lives.
     */
    public void updateHealthLabel(){
        healthLabel.setText("HEALTH: " + gameLogic.getLifeManager().getLives());
    }

    /**
     * Repaints the component, including rendering the game logic.
     *
     * @param g the Graphics context used for drawing the game components.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        gameLogic.draw(g);
    }
}
