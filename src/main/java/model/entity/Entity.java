package model.entity;

import controller.KeyHandler;
import model.gameState.GameStateManager;
import model.tile.TileManager;

import java.awt.*;

public abstract class Entity{
    protected int x;
    protected int y;
    protected int scale;

    protected Rectangle collisionArea;
    protected GameStateManager gsm;
    protected KeyHandler keyH;
    protected TileManager tileManager;

    protected boolean isInteractable;
    protected Interactable interactionAction;

    public abstract void draw(Graphics2D graphics2D);

    public abstract void update();


    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public boolean getIsInteractable() {
        return isInteractable;
    }

    public void setInteractable(boolean interactable) {
        this.isInteractable = interactable;
    }

    public Interactable getInteractionAction() {
        return interactionAction;
    }

    public void setInteractionAction(Interactable interactionAction) {
        this.interactionAction = interactionAction;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCollisionArea(int larghezza, int altezza) {
        this.collisionArea = new Rectangle(this.x, this.y, larghezza, altezza);
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
