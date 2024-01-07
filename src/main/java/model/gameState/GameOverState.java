package model.gameState;

import controller.KeyHandler;

import java.awt.*;

public class GameOverState implements GameState{ //BOZZA

    public GameOverState(){
    }
    @Override
    public void update() {
        if(keyH.enterPressed){
            gsm.reload();
        }
    }

    @Override
    public void draw(Graphics g) {
        if(gsm.getPlayState() !=null)
            gsm.getPlayState().draw(g);

    }
}
