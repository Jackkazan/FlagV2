package model.gameState;

import controller.KeyHandler;

import java.awt.*;

public class GameOverState implements GameState{ //BOZZA

    public GameOverState(){}

    @Override
    public void update() {
        if(keyH.enterPressed){
            gsm.reload();
        }
    }

    @Override
    public void draw(Graphics g) {
        gsm.getPlayState().draw(g);
        g.setColor(new Color(0, 0, 0, 255));
        g.fillRect(0, 0, screenWidth, screenHeight);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        int text = g.getFontMetrics().stringWidth("GAME OVER");
        int x = (screenWidth - text) / 2;
        int y = screenHeight/ 2;
        g.drawString("GAME OVER", x, y);
        g.drawString("Press enter to retry", x - 40, y + 70);
    }
}
