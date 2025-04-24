import javax.swing.*;

public class TutorialPanel extends JPanel {

    MainWindow mainWindow;

    Button lobbyButton;

    public TutorialPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        initialize();

    }

    private void initialize(){
        setLayout(null);
        setSize(1600,900);
        setVisible(true);

        lobbyButton = new Button("LOBBY", 400,400,200,200,e ->mainWindow.switchPanel(PanelType.LOBBY_PANEL));
        add(lobbyButton);
    }

}
