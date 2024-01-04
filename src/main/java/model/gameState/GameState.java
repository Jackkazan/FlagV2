package model.gameState;

import view.GamePanel;

import java.awt.*;

public interface GameState {
    int screenWidth = GamePanel.screenWidth;
    int screenHeight = GamePanel.screenHeight;
    void update();

    void draw(Graphics g);
}
