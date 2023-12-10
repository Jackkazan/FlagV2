package model.gameState;

import controller.KeyHandler;
import model.view.GamePanel;

import java.awt.*;

public class PauseState implements GameState{

    GamePanel gp;
    GameStateManager gsm;
    KeyHandler keyH;



    public PauseState(GamePanel gp, GameStateManager gsm, KeyHandler keyH) {
        this.gp = gp;
        this.gsm = gsm;
        this.keyH = keyH;
    }

    @Override
    public void update() {
        if (!keyH.isPaused()){
            gsm.setState(GameStateManager.State.PLAY);
        }

    }

    @Override
    public void draw(Graphics g) {;
        gsm.getPlayState().draw(g);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());


        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String pauseText = "E BECCATI STO TRAPEZIO";
        int textWidth = g.getFontMetrics().stringWidth(pauseText);
        int x = (gp.getScreenWidth()- textWidth) / 2;
        int y = gp.getScreenHeight()/ 2;
        g.drawString(pauseText, x, y);

    }
}
