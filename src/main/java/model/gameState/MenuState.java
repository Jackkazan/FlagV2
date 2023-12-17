package model.gameState;

import controller.KeyHandler;
import view.GamePanel;

import javax.swing.*;
import java.awt.*;

public class MenuState implements GameState{

    private GamePanel gp;
    private GameStateManager gsm;
    private KeyHandler keyH;
    private Graphics2D g2;
    public MenuState(GamePanel gp, GameStateManager gsm, KeyHandler keyH) {
        this.gp = gp;
        this.gsm = gsm;
        this.keyH = keyH;


    }

    @Override
    public void update() {
        if(keyH.enterPressed){
            gsm.setState(GameStateManager.State.PLAY);
        }

    }

    @Override
    public void draw(Graphics g) {
        /*
        //this.g2 = (Graphics2D)g;
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());


        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String menuText = "Press Enter to continue";
        int textWidth = g.getFontMetrics().stringWidth(menuText);
        int x = (gp.getScreenWidth()- textWidth) / 2;
        int y = gp.getScreenHeight()/ 2;
        g.drawString(menuText, x, y);
        */

        // Carica l'immagine di sfondo
        Image backgroundImage = new ImageIcon("src/main/resources/images/backgroundMenu.jpg").getImage();

        //Carica l'immagine del titolo
        Image titleImage = new ImageIcon("src/main/resources/images/TitleFataDraconis2.png").getImage();
        // Disegna l'immagine di sfondo
        g.drawImage(backgroundImage, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);

        // Calcola le coordinate per centrare l'immagine del titolo in alto
        int titleX = gp.getScreenWidth()/2 - titleImage.getWidth(null)/2;
        int titleY = gp.getScreenHeight() /8;
        //Disegna l'immagine del titolo
        g.drawImage(titleImage, titleX,titleY,null);


        // Setta altri elementi sopra l'immagine di sfondo
        g.setColor(new Color(255, 255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String menuText = "Press Enter to continue";


        int textWidth = g.getFontMetrics().stringWidth(menuText);
        int x = (gp.getScreenWidth() - textWidth) / 2;
        int y = gp.getScreenHeight() *2/3;


        g.drawString(menuText, x, y);

    }

}
