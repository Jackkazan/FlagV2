package model.entities.player;

import controller.KeyHandler;
import model.entities.Entity;
import model.entities.EntityState;
import model.entities.enemies.Enemy;
import model.entities.items.Item;
import model.entities.npc.Npc;
import model.entities.states.AttackState;
import model.entities.states.HitState;
import model.entities.states.IdleState;
import model.entities.states.MovementState;
import model.gameState.GameStateManager;
import model.collisions.CollisionObject;
import view.GamePanel;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static view.GamePanel.tileSize;

public class Player extends Entity{
    private final GamePanel gamePanel;
    protected int speed;
    protected int speedChangeSprite;

    protected int spriteNum;
    protected BufferedImage
            up1, up2, up3, up4,
            down1, down2, down3, down4,
            left1, left2, left3, left4,
            right1, right2, right3, right4;

    protected String direction;
    protected int spriteCounter = 0;
    protected int totalSprite;

    private boolean hitAnimationCompleted;
    private final int screenX;
    private final int screenY;

    protected int maxLife;
    protected int currentLife;
    protected EntityState currentState;

    protected boolean isAttacking =false;
    protected boolean isHitted = false;
    protected boolean attackAnimationCompleted = true;
    protected BufferedImage attackUp1, attackUp2, attackUp3, attackUp4, attackDown1, attackDown2, attackDown3, attackDown4, attackLeft1, attackLeft2, attackLeft3, attackLeft4, attackRight1, attackRight2, attackRight3, attackRight4;
    protected ArrayList<CollisionObject> currentCollisionMap;

    private boolean isHitAnimationCompleted = true;
    private boolean deadAnimationCompleted= true;
    public void updateInteractionArea() {
        interactionArea.setLocation(x -tileSize , y-tileSize);
    }

    public void updateCollisionArea() {
        collisionArea.setLocation(x-tileSize, y-tileSize);
    }

    public void updateAttackArea() {
        switch(direction){
            case "up":
                attackArea = new Rectangle(x-tileSize,y-tileSize*2,tileSize*3,tileSize*2);
                break;
            case "down":
                attackArea = new Rectangle(x-tileSize,y,tileSize*3,tileSize*2);
                break;
            case "left":
                attackArea = new Rectangle(x-tileSize-24,y-tileSize,tileSize*2,tileSize*3);
                break;
            case "right":
                attackArea = new Rectangle(x+24,y-tileSize,tileSize*2,tileSize*3);
                break;
        }

    }


    public enum swordStateAndArmor { IronSwordNoArmor, IronSwordAndArmor, GoldSwordAndArmor, RubySwordAndArmor }


    swordStateAndArmor currentSwordStateAndArmor;


    // Nuova area di interazione
    private Rectangle interactionArea;

    private Rectangle attackArea;


    public enum State{IDLE, MOVEMENT,HIT,ATTACK, RESPAWN, DEAD}


    public Player(GamePanel gamePanel, GameStateManager gsm, KeyHandler keyH) {
        this.gamePanel = gamePanel;
        this.gsm = gsm;
        this.keyH = keyH;

        screenX = gamePanel.getScreenWidth()/2 - (tileSize/2);
        screenY = gamePanel.getScreenHeight()/2 - (tileSize/2);
        setDefaultValues();
        getEntityImage();
        getAttackImages();
        setState(State.IDLE);

    }

    public void setDefaultValues() {
        x = tileSize*3;  //3
        y = tileSize*4;  //4
        maxLife = 6;
        currentLife = 6;
        speed = 4;
        scale = 5;
        spriteCounter = 0;
        spriteNum = 3;
        direction = "down";
        currentSwordStateAndArmor= swordStateAndArmor.IronSwordNoArmor;
        imageWidth = tileSize;
        imageHeight = tileSize;
        isAttacking=false;
        isHitted= false;
        attackAnimationCompleted = true;
        hitAnimationCompleted = true;
        attackArea = new Rectangle();
        collisionArea = new Rectangle(x-tileSize,y-tileSize,tileSize*2,tileSize*2);
        interactionArea = new Rectangle(x-16, y-16, tileSize*3, tileSize*3);

    }

    public void setState(State playerState) {
        switch (playerState) {
            case IDLE -> currentState = new IdleState();
            case MOVEMENT -> currentState = new MovementState();
            case ATTACK -> currentState = new AttackState();
            case HIT -> currentState = new HitState();
            default -> {}
        }
    }

    public void setSwordStateAndArmor(swordStateAndArmor newSwordStateAndArmor) {
        this.currentSwordStateAndArmor = newSwordStateAndArmor;
    }

    @Override
    public void update() {
        if (isAttacking) {
            hitAnEnemy();
            this.setState(State.ATTACK);
        } else {
            if (keyH.spacePressed && attackAnimationCompleted) {
                this.setState(State.ATTACK);
            } else {
                if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                    this.setState(State.MOVEMENT);
                } else {
                    this.setState(State.IDLE);
                }
            }
        }
        //System.out.println("Stato corrente "+ currentState);
        currentState.update(this);
    }


    @Override
    public void draw(Graphics2D graphics2D) {

        currentState.draw(graphics2D,this);
        // Disegna l'area di collisione per debug
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect((int) collisionArea.getX(), (int) collisionArea.getY(), (int) collisionArea.getWidth(), (int) collisionArea.getHeight());

        // Disegna l'area di interazione per debug
        graphics2D.setColor(Color.BLUE);
        graphics2D.drawRect((int) interactionArea.getX(), (int) interactionArea.getY(), (int) interactionArea.getWidth(), (int) interactionArea.getHeight());

        // Disegna l'area di attacco per debug
        graphics2D.setColor(Color.YELLOW);
        graphics2D.drawRect((int) attackArea.getX(), (int) attackArea.getY(), (int) attackArea.getWidth(), (int) attackArea.getHeight());

    }

    public void hitAnEnemy(){
        for(Enemy enemy: gsm.getEnemyList()) {
            if (attackArea.intersects(enemy.getCollisionArea())) {
                //System.out.println(enemy.getName() + " è stato hittato");
                if(!enemy.isHitted()) {
                    enemy.setSpriteNum(0);
                    enemy.setHitAnimationCompleted(false);
                }
                enemy.setHitted(true);
            }
        }
    }

    public boolean collidesWithEnemies(int nextX, int nextY) {
        // Verifica la collisione con le entità della lista npcList
        for (Enemy enemy : gsm.getEnemyList()) {
            if (enemy.getTileManager().equals(gsm.getMapManager().getCurrentMap()) && checkCollisionRectangle(nextX, nextY, enemy.getCollisionArea())) {
                System.out.println("Sei stato hittato da "+ enemy.getName());

                return true; // Collisione rilevata
            }
        }
        return false; // Nessuna collisione rilevata
    }

    // metodo per verificare la collisione con le entità
    public boolean collidesWithNpcs(int nextX, int nextY) {
        // Verifica la collisione con le entità della lista npcList
        for (Npc npc : gsm.getNpcList()) {
            if (npc.getTileManager().equals(gsm.getMapManager().getCurrentMap()) && checkCollisionRectangle(nextX, nextY, npc.getCollisionArea())) {
                return true; // Collisione rilevata
            }
        }
        return false; // Nessuna collisione rilevata
    }
    public boolean collidesWithItems(int nextX, int nextY) {
        // Verifica la collisione con gli oggetti della lista keyItemsList
        for (Item item : gsm.getKeyItemsList()) {
            if (item.getTileManager().equals(gsm.getMapManager().getCurrentMap()) && item.getCollisionArea()!=null && checkCollisionRectangle(nextX, nextY, item.getCollisionArea())) {
                item.interact();
                return true; // Collisione rilevata
            }
        }
        return false; // Nessuna collisione rilevata
    }
    //metodo per verificare la collisione con i Rectangle degli Items
    private boolean checkCollisionRectangle(int x, int y, Rectangle collisionArea) {
        double objectX = collisionArea.getX();
        double objectY = collisionArea.getY();
        double objectWidth = collisionArea.getWidth();
        double objectHeight = collisionArea.getHeight();

        return x < objectX + objectWidth &&
                x + tileSize > objectX &&
                y < objectY + objectHeight &&
                y + tileSize > objectY;
    }
    public boolean collidesWithObjects(int nextX, int nextY) {
        // Verifica la collisione con gli oggetti di collisione della mappa corrente
        for (CollisionObject collisionObject : currentCollisionMap) {
            if (checkCollisionObject(nextX, nextY, collisionObject)) {
                return true; // Collisione rilevata
            }
        }
        return false; // Nessuna collisione rilevata
    }

    public boolean checkCollisionObject(int x, int y, CollisionObject collisionObject) {
        double objectX = collisionObject.getX() * gsm.getGamePanel().getScale();
        double objectY = collisionObject.getY() * gsm.getGamePanel().getScale();
        double objectWidth = collisionObject.getWidth() * gsm.getGamePanel().getScale();
        double objectHeight = collisionObject.getHeight() * gsm.getGamePanel().getScale();

        return x < objectX + objectWidth &&
                x + tileSize > objectX &&
                y < objectY + objectHeight &&
                y + tileSize > objectY;
    }
    public boolean isAttacking() {
        return this.isAttacking;
    }
    public void setCurrentCollisionMap(ArrayList<CollisionObject> collisionMap) {
        this.currentCollisionMap = collisionMap;
    }

    public boolean onTransitionPoint(int targetX, int targetY, int tolerance) {
        int playerTileX = x / tileSize;
        int playerTileY = y / tileSize;
        return Math.abs(playerTileX - targetX) <= tolerance && Math.abs(playerTileY - targetY) <= tolerance;
    }

    public void teleport(int targetX, int targetY) {
        x = tileSize * targetX;
        y = tileSize * targetY;
    }
    public void getEntityImage() {
        try {
            switch (currentSwordStateAndArmor) {
                case IronSwordNoArmor -> {
                    up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterUp_0.png")));
                    up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterUp_1.png")));
                    up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterUp_2.png")));
                    up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterUp_3.png")));
                    down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterDown_0.png")));
                    down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterDown_1.png")));
                    down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterDown_2.png")));
                    down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterDown_3.png")));
                    left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterLeft_1.png")));
                    left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterLeft_2.png")));
                    left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterLeft_3.png")));
                    left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterLeft_0.png")));
                    right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterRight_0.png")));
                    right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterRight_1.png")));
                    right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterRight_2.png")));
                    right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterRight_3.png")));
                }
                case IronSwordAndArmor -> {}
                case GoldSwordAndArmor -> {}
                case RubySwordAndArmor -> {}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getAttackImages() {
        try {
            switch (currentSwordStateAndArmor) {
                case IronSwordNoArmor -> {
                    attackUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackUp_0.png")));
                    attackUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackUp_1.png")));
                    attackUp3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackUp_2.png")));
                    attackUp4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackUp_3.png")));
                    attackDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackDown_0.png")));
                    attackDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackDown_1.png")));
                    attackDown3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackDown_2.png")));
                    attackDown4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackDown_3.png")));
                    attackLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackLeft_0.png")));
                    attackLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackLeft_1.png")));
                    attackLeft3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackLeft_2.png")));
                    attackLeft4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackLeft_3.png")));
                    attackRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackRight_0.png")));
                    attackRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackRight_1.png")));
                    attackRight3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackRight_2.png")));
                    attackRight4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterIronAttackRight_3.png")));
                }
                case IronSwordAndArmor -> {}
                case GoldSwordAndArmor -> {}
                case RubySwordAndArmor -> {}
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public int getMaxLife() {
        return maxLife;
    }
    public int getCurrentLife() {
        return currentLife;
    }

    public int getScreenX() {
        return this.screenX;
    }

    public int getScreenY() {
        return this.screenY;
    }

    public Rectangle getInteractionArea() {
        return interactionArea;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }



    public boolean isAttackAnimationCompleted() {
        return attackAnimationCompleted;
    }

    public swordStateAndArmor getCurrentSwordStateAndArmor() {
        return currentSwordStateAndArmor;
    }

    public ArrayList<CollisionObject> getCurrentCollisionMap() {
        return currentCollisionMap;
    }



    public void setHitAnimationCompleted(boolean hitAnimationCompleted) {
        this.hitAnimationCompleted = hitAnimationCompleted;
    }


    public void setHitted(boolean hitted) {
        isHitted = hitted;
    }

    public boolean isHitted() {
        return isHitted;
    }

    public Rectangle getAttackArea() {
        return attackArea;
    }

    public boolean isHitAnimationCompleted() {
        return hitAnimationCompleted;
    }

    public EntityState getCurrentState() {
        return currentState;
    }

    public void incrementSpriteCounter() {
        this.spriteCounter = spriteCounter +1;
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


    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
    public void setAttackAnimationCompleted(boolean attackAnimationCompleted) {
        this.attackAnimationCompleted = attackAnimationCompleted;
    }


    public boolean getAttackAnimationCompleted() {
        return this.attackAnimationCompleted;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
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


}
