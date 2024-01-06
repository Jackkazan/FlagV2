package model.entities;

import controller.KeyHandler;
import model.gameState.GameStateManager;
import model.tile.TileManager;

import java.awt.*;

import static view.GamePanel.tileSize;

public abstract class Entity{
    public static final int MAX_ATTACK_ANIMATION_FRAMES= 60;
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

    protected int imageWidth;
    protected int imageHeight;

    public Entity(){
        this.gsm = GameStateManager.getInstance();
        this.keyH = KeyHandler.getInstance();
    }
    public abstract void draw(Graphics2D graphics2D);

    public abstract void update();


    public static class EntityBuilder<T extends Entity, B extends EntityBuilder<T, B>> {
        protected T entity;

        public EntityBuilder() {
            this.entity = createEntity();
        }

        protected T createEntity() {
            return null;
        }

        public B setName(String name) {
            this.entity.name = name;
            return (B) this;
        }

        public B setX(int x) {
            this.entity.x = x;
            return (B) this;
        }

        public B setY(int y) {
            this.entity.y = y;
            return (B) this;
        }

        // ... (aggiungi altri setter comuni)
        public B setCollisionArea(int larghezza, int altezza) {
            this.entity.collisionArea = new Rectangle(this.entity.x, this.entity.y , larghezza, altezza);
            return (B) this;
        }
        public B setCollisionArea(int x, int y, int larghezza, int altezza) {
            this.entity.collisionArea = new Rectangle(x, y , larghezza, altezza);
            return (B) this;
        }

        public B setContainedMap(TileManager tileManager) {
            this.entity.tileManager = tileManager;
            return (B) this;
        }
        public B setInteractable(boolean interactable) {
            this.entity.isInteractable = interactable;
            return (B) this;
        }
        public B setImageDimension(int imageWidth, int imageHeigth) {
            this.entity.imageWidth = imageWidth;
            this.entity.imageHeight = imageHeigth;
            return (B) this;
        }
        public B setInteractionAction(Interactable interactionActionItems){
            this.entity.interactionAction = interactionActionItems;
            return (B) this;
        }
        public B setScale(int scale) {
            this.entity.scale = scale;
            return (B) this;
        }
        public T build() {
            return this.entity;
        }
    }

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

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setTileManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }
}
