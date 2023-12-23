package model.entity;

import controller.KeyHandler;
import model.gameState.GameStateManager;
import model.tile.TileManager;

import java.awt.*;

import static view.GamePanel.tileSize;

public abstract class Entity{
    protected String name;
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


    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setPosition(int x, int y) {
        this.x = x * tileSize;
        this.y = y * tileSize;
    }

    public void setCollisionArea(int larghezza, int altezza) {
        this.collisionArea = new Rectangle(this.x, this.y, larghezza, altezza);
    }
    public void setCollisionArea ( Rectangle collisionArea) {
        this.collisionArea = collisionArea;
    }


    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public int getScale() {
        return scale;
    }

    public GameStateManager getGsm() {
        return gsm;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGsm(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public void setKeyH(KeyHandler keyH) {
        this.keyH = keyH;
    }

    public void setTileManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }
}
