package model.gameState;

import controller.KeyHandler;
import model.entity.Entity;
import model.entity.NPCCreator;
import model.entity.Player;
import model.items.KeyItems;
import model.items.ObjectsCreator;
import model.tile.MapManager;
import model.tile.TileManager;
import model.view.GamePanel;

import java.awt.*;

public class DialogueState implements GameState {

    private GamePanel gp;
    private GameStateManager gsm;
    private KeyHandler keyH;

    public DialogueState(GamePanel gp, GameStateManager gsm, KeyHandler keyH) {
            this.gp = gp;
            this.gsm = gsm;
            this.keyH = keyH;
    }

    @Override
    public void update() {
        gsm.getPlayState().update();
        // logica dialoghi
    }

    @Override
    public void draw(Graphics g) {
        gsm.getPlayState().draw(g);

    }
}
