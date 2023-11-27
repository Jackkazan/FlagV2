package model.entity;

import model.collisioni.CollisionObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static view.GamePanel.tileSize;

public abstract class Entity {
    protected int x, y;
    protected int speed;
    private String entity;
    public BufferedImage
            up1, up2, up3, up4,
            down1, down2, down3, down4,
            left1, left2, left3, left4,
            right1, right2, right3, right4;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 3;

    // COLLISION
    private Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
    private int collisionDefaultX, collisionDefaultY;
    private boolean collisionOn = false;



    public abstract void setDefaultValues();

    public abstract void update();
    public abstract void getEntityImage();

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Rectangle getCollisionArea() {
        return collisionArea;
    }
    public abstract boolean collidesWithObjects(int nextX, int nextY);
    public abstract boolean checkCollision(int x, int y, CollisionObject collisionObject);
    public abstract void setCurrentCollisionMap(ArrayList<CollisionObject> collisionMap);
    public abstract boolean onTransitionPoint(int targetX, int targetY, int tolerance);
    public int getCollisionDefaultX() {
        return collisionDefaultX;
    }

    public int getCollisionDefaultY() {
        return collisionDefaultY;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }
}
