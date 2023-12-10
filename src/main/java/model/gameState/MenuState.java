package model.gameState;

import controller.KeyHandler;
import model.view.GamePanel;

import java.awt.*;

public class MenuState implements GameState{

    private GamePanel gp;
    private GameStateManager gsm;
    private KeyHandler keyH;
    private Graphics2D g2;
    public MenuState(GamePanel gp, GameStateManager gsm) {
        this.gp = gp;
        this.gsm = gsm;
        this.keyH = gsm.getKeyH();


    }

    @Override
    public void update() {
        if(keyH.enterPressed == true){
            gsm.setState(GameStateManager.State.PLAY);
        }

    }

    @Override
    public void draw(Graphics g) {
        //this.g2 = (Graphics2D)g;
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());


        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String menuText = "Menu";
        int textWidth = g.getFontMetrics().stringWidth(menuText);
        int x = (gp.getScreenWidth()- textWidth) / 2;
        int y = gp.getScreenHeight()/ 2;
        g.drawString(menuText, x, y);


    }

}
