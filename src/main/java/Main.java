import toolTMX.TMXReader;
import view.GamePanel;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        TMXReader loadSprite = new TMXReader("src/main/resources/Map/StanzaIntroduzione/Casetta_Iniziale.tmx");
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}