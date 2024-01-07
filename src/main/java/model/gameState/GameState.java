package model.gameState;

import controller.KeyHandler;
import view.GamePanel;

import java.awt.*;

public interface GameState {
    GameStateManager gsm = GameStateManager.getInstance();
    KeyHandler keyH = KeyHandler.getInstance();
    int screenWidth = GamePanel.screenWidth;
    int screenHeight = GamePanel.screenHeight;
    void update();

    void draw(Graphics g);
}
