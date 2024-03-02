package model.entities.characters;

import model.entities.Entity;
import model.entities.EntityState;
import model.entities.states.*;
import view.GamePanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static view.GamePanel.tileSize;

public abstract class Characters extends Entity {
    protected int speed;
    protected int speedChangeSprite;

    protected int spriteNum;

    protected int spriteCounter = 0;
    protected String direction;

    protected int totalSprite;
    protected BufferedImage
            up1, up2, up3, up4, up5, up6,
            down1, down2, down3, down4, down5,down6,
            left1, left2, left3, left4, left5,left6,
            right1, right2, right3, right4, right5, right6;
    protected BufferedImage attackUp1, attackUp2, attackUp3, attackUp4,
            attackDown1, attackDown2, attackDown3, attackDown4,
            attackLeft1, attackLeft2, attackLeft3, attackLeft4,
            attackRight1, attackRight2, attackRight3, attackRight4;

    protected BufferedImage dead1, dead2, dead3, dead4,
            dead5, dead6, dead7, dead8, dead9;

    protected BufferedImage idle1,idle2,idle3,idle4;

    protected BufferedImage hit1, hit2, hit3, hit4, hit5, hit6, hit7, hit8,
            hit9, hit10, hit11, hit12, hit13, hit14, hit15, hit16;

    public enum State{IDLE, MOVEMENT, HIT, ATTACK, DEAD, RESPAWN}
    protected EntityState currentState;
    protected int maxLife;
    protected int currentLife;
    protected int damage;

    protected boolean isAttacking;
    protected boolean isHitted;
    protected boolean isDead;
    protected boolean isAttackAnimationCompleted;
    protected boolean isDeadAnimationCompleted;
    protected boolean isHitAnimationCompleted;

    protected Rectangle attackArea;
    protected int respawnX; // Posizione di respawn X
    protected int respawnY; // Posizione di respawn Y
    protected long lastHitTime;  // Memorizza il tempo dell'ultima hit
    protected long hitCooldown;  // Cooldown in millisecondi (1 secondo)
    protected ArrayList<Rectangle2D.Double> currentCollisionMap;
    public Characters() {
        super();
    }
    public void takeDamage(int damage) {
        currentLife -= damage;
        //System.out.println("La vita del nemico e' : " + currentLife);
/*
        if(currentLife <= 0) {
            setState(State.DEAD);
        } else {
            setState(State.HIT);
        }
        */
    }
    public void setState(State entityState) {
        switch (entityState) {
            case IDLE -> currentState = new IdleState();
            case MOVEMENT -> currentState = new MovementState();
            case ATTACK -> currentState = new AttackState();
            case HIT -> currentState = new HitState();
            case DEAD -> currentState = new DeadState();
            case RESPAWN -> currentState = new RespawnState();
            default -> {}
        }
    }
    public void updateAttackArea(){
        switch(direction){
            case "up":
                attackArea = new Rectangle(x-tileSize,y-tileSize,tileSize*3,tileSize);
                break;
            case "down":
                attackArea = new Rectangle(x-tileSize,y,tileSize*3,tileSize);
                break;
            case "left":
                attackArea = new Rectangle(x-tileSize-24,y-tileSize,tileSize,tileSize*3);
                break;
            case "right":
                attackArea = new Rectangle(x+24,y-tileSize,tileSize,tileSize*3);
                break;
        }
    }
    public boolean collidesWithObjects(int nextX, int nextY) {
        // Verifica la collisione con gli oggetti di collisione della mappa corrente
        for (Rectangle2D.Double collisionObject : currentCollisionMap) {
            if (checkCollisionObject(nextX, nextY, collisionObject)) {
                return true; // Collisione rilevata
            }
        }
        return false; // Nessuna collisione rilevata
    }
    public boolean checkCollisionObject(int x, int y, Rectangle2D.Double collisionObject) {
        double objectX = collisionObject.getX() * GamePanel.scale;
        double objectY = collisionObject.getY() * GamePanel.scale;
        double objectWidth = collisionObject.getWidth() * GamePanel.scale;
        double objectHeight = collisionObject.getHeight() * GamePanel.scale;

        return x < objectX + objectWidth &&
                x + tileSize > objectX &&
                y < objectY + objectHeight &&
                y + tileSize > objectY;
    }

    public boolean checkCollisionRectangle(int x, int y, Rectangle collisionArea) {
        double objectX = collisionArea.getX();
        double objectY = collisionArea.getY();
        double objectWidth = collisionArea.getWidth();
        double objectHeight = collisionArea.getHeight();

        return x < objectX + objectWidth &&
                x + tileSize > objectX &&
                y < objectY + objectHeight &&
                y + tileSize > objectY;
    }

    public void setHitted(boolean hitted) {
        isHitted = hitted;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSpeedChangeSprite(int speedChangeSprite) {
        this.speedChangeSprite = speedChangeSprite;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public void setTotalSprite(int totalSprite) {
        this.totalSprite = totalSprite;
    }
    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
    public void setAttackAnimationCompleted(boolean attackAnimationCompleted) {
        this.isAttackAnimationCompleted = attackAnimationCompleted;
    }
    public void incrementSpriteCounter() {
        this.spriteCounter = spriteCounter +1;
    }

    public boolean isHitted() {
        return this.isHitted;
    }
    public int getSpeed() {
        return this.speed;
    }

    public String getDirection() {
        return this.direction;
    }

    public int getSpeedChangeSprite() {
        return speedChangeSprite;
    }

    public int getSpriteNum() {
        return spriteNum;
    }
    public int getMaxLife() {
        return maxLife;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public EntityState getCurrentState() {
        return currentState;
    }

    public boolean getAttackAnimationCompleted() {
        return this.isAttackAnimationCompleted;
    }
    public long getLastHitTime() {
        return lastHitTime;
    }

    public void setLastHitTime(long time) {
        lastHitTime = time;
    }

    public long getHitCooldown() {
        return hitCooldown;
    }

    public boolean isAttackAnimationCompleted() {
        return isAttackAnimationCompleted;
    }

    public ArrayList<Rectangle2D.Double> getCurrentCollisionMap() {
        return this.currentCollisionMap;
    }

    public boolean getDeadAnimationCompleted() {
        return isDeadAnimationCompleted;
    }

    public boolean getHitAnimationCompleted() {
        return isHitAnimationCompleted;
    }
    public BufferedImage getUp1() {
        return up1;
    }

    public BufferedImage getUp2() {
        return up2;
    }

    public BufferedImage getUp3() {
        return up3;
    }

    public BufferedImage getUp4() {
        return up4;
    }

    public BufferedImage getDown1() {
        return down1;
    }

    public BufferedImage getDown2() {
        return down2;
    }

    public BufferedImage getDown3() {
        return down3;
    }

    public BufferedImage getDown4() {
        return down4;
    }

    public BufferedImage getLeft1() {
        return left1;
    }

    public BufferedImage getLeft2() {
        return left2;
    }

    public BufferedImage getLeft3() {
        return left3;
    }

    public BufferedImage getLeft4() {
        return left4;
    }

    public BufferedImage getRight1() {
        return right1;
    }

    public BufferedImage getRight2() {
        return right2;
    }

    public BufferedImage getRight3() {
        return right3;
    }

    public BufferedImage getRight4() {
        return right4;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public int getTotalSprite() {
        return totalSprite;
    }
    public BufferedImage getAttackUp1() {
        return this.attackUp1;
    }

    public BufferedImage getAttackUp2() {
        return this.attackUp2;
    }

    public BufferedImage getAttackUp3() {
        return this.attackUp3;
    }

    public BufferedImage getAttackUp4() {
        return this.attackUp4;
    }

    public BufferedImage getAttackDown1() {
        return this.attackDown1;
    }

    public BufferedImage getAttackDown2() {
        return this.attackDown2;
    }

    public BufferedImage getAttackDown3() {
        return this.attackDown3;
    }

    public BufferedImage getAttackDown4() {
        return this.attackDown4;
    }

    public BufferedImage getAttackLeft1() {
        return attackLeft1;
    }

    public BufferedImage getAttackLeft2() {
        return this.attackLeft2;
    }

    public BufferedImage getAttackLeft3() {
        return this.attackLeft3;
    }

    public BufferedImage getAttackLeft4() {
        return this.attackLeft4;
    }

    public BufferedImage getAttackRight1() {
        return this.attackRight1;
    }

    public BufferedImage getAttackRight2() {
        return this.attackRight2;
    }

    public BufferedImage getAttackRight3() {
        return this.attackRight3;
    }

    public BufferedImage getAttackRight4() {
        return this.attackRight4;
    }
    public BufferedImage getHit1() {
        return hit1;
    }

    public BufferedImage getHit2() {
        return hit2;
    }

    public BufferedImage getHit3() {
        return hit3;
    }

    public BufferedImage getHit4() {
        return hit4;
    }

    public BufferedImage getHit5() {
        return hit5;
    }

    public BufferedImage getHit6() {
        return hit6;
    }

    public BufferedImage getHit7() {
        return hit7;
    }

    public BufferedImage getHit8() {
        return hit8;
    }

    public BufferedImage getHit9() {
        return hit9;
    }

    public BufferedImage getHit10() {
        return hit10;
    }

    public BufferedImage getHit11() {
        return hit11;
    }

    public BufferedImage getHit12() {
        return hit12;
    }

    public BufferedImage getHit13() {
        return hit13;
    }

    public BufferedImage getHit14() {
        return hit14;
    }

    public BufferedImage getHit15() {
        return hit15;
    }

    public BufferedImage getHit16() {
        return hit16;
    }
    public BufferedImage getIdle1() {
        return this.idle1;
    }

    public BufferedImage getIdle2() {
        return this.idle2;
    }

    public BufferedImage getIdle3() {
        return this.idle3;
    }

    public BufferedImage getIdle4() {
        return this.idle4;
    }

    public BufferedImage getDead1() {
        return dead1;
    }

    public BufferedImage getDead2() {
        return dead2;
    }

    public BufferedImage getDead3() {
        return dead3;
    }

    public BufferedImage getDead4() {
        return dead4;
    }

    public BufferedImage getDead5() {
        return dead5;
    }

    public BufferedImage getDead6() {
        return dead6;
    }

    public BufferedImage getDead7() {
        return dead7;
    }

    public BufferedImage getDead8() {
        return dead8;
    }

    public BufferedImage getDead9() {
        return dead9;
    }



}
