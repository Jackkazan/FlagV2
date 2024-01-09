package model.entities.characters.player;

import controller.KeyHandler;
import model.entities.EntityState;
import model.entities.characters.Characters;
import model.entities.characters.enemies.Enemy;
import model.entities.items.Item;
import model.entities.characters.npc.Npc;
import model.entities.states.AttackState;
import model.entities.states.HitState;
import model.entities.states.IdleState;
import model.entities.states.MovementState;
import model.gameState.GameStateManager;
import model.collisions.CollisionObject;
import model.tile.TileManager;
import view.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static view.GamePanel.tileSize;

public class Player extends Characters {
    private final int screenX;
    private final int screenY;

    private TileManager currentTile;

    private String enemyHitDirection;
    private int enemyHitDamage;

    public void setEnemyHitDamage(int damage) {
        enemyHitDamage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public enum swordStateAndArmor { IronSwordNoArmor, IronSwordAndArmor, GoldSwordAndArmor, RubySwordAndArmor }
    swordStateAndArmor currentSwordStateAndArmor;
    private Rectangle interactionArea;
    private Rectangle attackArea;
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

    public Player(int x, int y){
        super();
        this.name = "Player";
        screenX = GamePanel.screenWidth/2 - (tileSize/2);
        screenY = GamePanel.screenHeight/2 - (tileSize/2);
        setDefaultValues();
        getEntityImage();
        getHitImage();
        getAttackImages();
        setState(State.IDLE);
        super.setPosition(x, y);
    }

    public Player() {
        super();
        this.name = "Player";
        screenX = GamePanel.screenWidth/2 - (tileSize/2);
        screenY = GamePanel.screenHeight/2 - (tileSize/2);
        setDefaultValues();
        getEntityImage();
        getHitImage();
        getAttackImages();
        setState(State.IDLE);
    }

    public void setDefaultValues() {
        x = tileSize*3;  //3
        y = tileSize*4;  //4
        maxLife = 6;
        currentLife = 0;
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
        isAttackAnimationCompleted = true;
        isHitAnimationCompleted = true;
        attackArea = new Rectangle();
        collisionArea = new Rectangle(x-tileSize,y-tileSize,tileSize*2,tileSize*2);
        interactionArea = new Rectangle(x-16, y-16, tileSize*3, tileSize*3);
        hitCooldown = 2000;
        isDead = false;
        isDeadAnimationCompleted = true;
        damage=1;
    }


    public void setEnemyHitDirection(String direction) {
        enemyHitDirection = direction;
    }
    public String getEnemyHitDirection() {
        return enemyHitDirection;
    }
    public void setSwordStateAndArmor(swordStateAndArmor newSwordStateAndArmor) {
        this.currentSwordStateAndArmor = newSwordStateAndArmor;
    }

    public void takeDamage(){
        currentLife -= enemyHitDamage;

    }

    @Override
    public void update() {
        if(currentLife <= 0){
            setState(State.DEAD);
        }
        else{
            if(isHitted){
                setState(State.HIT);
            }else {
                if (isAttacking) {
                    this.setState(State.ATTACK);
                } else {
                    if (keyH.spacePressed && isAttackAnimationCompleted) {
                        resetAttack();
                        this.setState(State.ATTACK);
                    } else {
                        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                            this.setState(State.MOVEMENT);
                        } else {
                            this.setState(State.IDLE);
                        }
                    }
                }
            }
        }
        //System.out.println("Stato corrente "+ currentState);
        currentState.update(this);
    }

    @Override
    public void draw(Graphics2D graphics2D) {

        currentState.draw(graphics2D,this);

        /*
        // Disegna l'area di collisione per debug
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect((int) collisionArea.getX(), (int) collisionArea.getY(), (int) collisionArea.getWidth(), (int) collisionArea.getHeight());

        // Disegna l'area di interazione per debug
        graphics2D.setColor(Color.BLUE);
        graphics2D.drawRect((int) interactionArea.getX(), (int) interactionArea.getY(), (int) interactionArea.getWidth(), (int) interactionArea.getHeight());

        // Disegna l'area di attacco per debug
        graphics2D.setColor(Color.YELLOW);
        graphics2D.drawRect((int) attackArea.getX(), (int) attackArea.getY(), (int) attackArea.getWidth(), (int) attackArea.getHeight());


         */
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

    public void setCurrentLife(int h){
        this.currentLife = h;
    }
    public void resetAttack(){
        this.isAttacking = true;
        this.isAttackAnimationCompleted = false;
        this.spriteNum = 0;
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
    public void getHitImage() {
        try {
            switch (currentSwordStateAndArmor) {
                case IronSwordNoArmor -> {
                    //up
                    hit1= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_0.png")));
                    hit2= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_1.png")));
                    hit3= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_2.png")));
                    hit4= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_3.png")));
                    //down
                    hit5= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_4.png")));
                    hit6= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_5.png")));
                    hit7= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_6.png")));
                    hit8= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_7.png")));
                    //left
                    hit9= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_8.png")));
                    hit10= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_9.png")));
                    hit11= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_10.png")));
                    hit12= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_11.png")));
                    //right
                    hit13= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_12.png")));
                    hit14= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_13.png")));
                    hit15= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_14.png")));
                    hit16= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterHit_15.png")));
                }
                case IronSwordAndArmor -> {}
                case GoldSwordAndArmor -> {}
                case RubySwordAndArmor -> {}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public swordStateAndArmor getCurrentSwordStateAndArmor() {
        return currentSwordStateAndArmor;
    }

    public void setHitAnimationCompleted(boolean hitAnimationCompleted) {
        this.isHitAnimationCompleted = hitAnimationCompleted;
    }

    public Rectangle getAttackArea() {
        return attackArea;
    }

}
