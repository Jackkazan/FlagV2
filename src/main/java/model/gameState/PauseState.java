package model.gameState;

import controller.KeyHandler;
import view.GamePanel;

import java.awt.*;

public class PauseState implements GameState{

    GamePanel gp;
    GameStateManager gsm;
    KeyHandler keyH;



    public PauseState(GamePanel gp, GameStateManager gsm, KeyHandler keyH) {
        System.out.println("costruttore pausestate");
        this.gp = gp;
        this.gsm = gsm;
        this.keyH = keyH;
        gsm.setAlreadyPaused(true);
    }

    @Override
    public void update() {
        //gsm.getPlayState().update();
        if (!keyH.isPaused()){
            if (gsm.isInDialogue()){
                gsm.setState(GameStateManager.State.PREVIOUS); // Se si è in dialogo uscendo dalla pausa bisogna ritornare allo stato dialogo salvato in previous state
            }
            else{
            gsm.setState(GameStateManager.State.PLAY);
            }
            gsm.setAlreadyPaused(false);
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
