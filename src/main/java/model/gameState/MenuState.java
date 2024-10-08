package model.gameState;

import controller.KeyHandler;
import view.GamePanel;

import javax.swing.*;
import java.awt.*;

public class MenuState implements GameState{

   private int screenWidth = GamePanel.screenWidth;
    private int screenHeight = GamePanel.screenHeight;
    private boolean start = false;
    public MenuState() {
    }

    @Override
    public void update() {
        if (keyH.startGame){ start = true; keyH.startGame = false;}
        if (!gsm.isInitializing() && !gsm.isInitialized()){
            gsm.init();
        }
        if(start && gsm.isInitialized()){
            gsm.setState(GameStateManager.State.PLAY);
            gsm.setState(GameStateManager.State.PAUSE);
        }

    }

    @Override
    public void draw(Graphics g) {
        // Carica l'immagine di sfondo
        Image backgroundImage = new ImageIcon("src/main/resources/images/backgroundMenu.jpg").getImage();

        //Carica l'immagine del titolo
        Image titleImage = new ImageIcon("src/main/resources/images/TitleFataDraconis2.png").getImage();
        // Disegna l'immagine di sfondo
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);

        // Calcola le coordinate per centrare l'immagine del titolo in alto
        int titleX = screenWidth/2 - titleImage.getWidth(null)/2;
        int titleY = screenHeight /8;
        //Disegna l'immagine del titolo
        g.drawImage(titleImage, titleX,titleY,null);


        // Setta altri elementi sopra l'immagine di sfondo
        g.setColor(new Color(255, 255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String menuText = "Press Enter to continue";


        int textWidth = g.getFontMetrics().stringWidth(menuText);
        int x = (screenWidth - textWidth) / 2;
        int y = screenHeight *2/3;


        g.drawString(menuText, x, y);

    }

}
