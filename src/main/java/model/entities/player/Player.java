package model.entities.player;

import controller.KeyHandler;
import model.entities.enemies.Enemy;
import model.entities.items.Item;
import model.entities.npc.Npc;
import model.gameState.GameStateManager;
import model.collisions.CollisionObject;
import view.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.Timer;

import static view.GamePanel.tileSize;

public class Player extends Enemy {
    private final GamePanel gamePanel;

    private boolean isAttacking;
    private boolean attackAnimationCompleted;

    private final int screenX;
    private final int screenY;

    public enum swordStateAndArmor { IronSwordNoArmor, IronSwordAndArmor, GoldSwordAndArmor, RubySwordAndArmor }

    swordStateAndArmor currentSwordStateAndArmor;

    private ArrayList<CollisionObject> currentCollisionMap;

    // Nuova area di interazione
    private Rectangle interactionArea;

    private BufferedImage attackUp1, attackUp2, attackUp3, attackUp4, attackDown1, attackDown2, attackDown3, attackDown4, attackLeft1, attackLeft2, attackLeft3, attackLeft4, attackRight1, attackRight2, attackRight3, attackRight4;


    public Player(GamePanel gamePanel, GameStateManager gsm, KeyHandler keyH) {
        this.gamePanel = gamePanel;
        this.gsm = gsm;
        this.keyH = keyH;

        screenX = gamePanel.getScreenWidth()/2 - (tileSize/2);
        screenY = gamePanel.getScreenHeight()/2 - (tileSize/2);
        setDefaultValues();
        getEntityImage();
        getAttackImages();

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
        attackAnimationCompleted = true;
        interactionArea = new Rectangle(0, 0, tileSize*2+16, tileSize*2+16);
        collisionArea = new Rectangle(0, 0, tileSize, tileSize);

    }

    public void setSwordStateAndArmor(swordStateAndArmor newSwordStateAndArmor) {
        this.currentSwordStateAndArmor = newSwordStateAndArmor;
    }

    @Override
    public void update() {
        if (keyH.attackVPressed && !isAttacking && attackAnimationCompleted) {
            isAttacking = true;
            attackAnimationCompleted = false;
            attack();
        }

        if (isAttacking) {
            updateAttackAnimation();
        } else {
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                int nextX = x;
                int nextY = y;

                if (keyH.rightPressed) {
                    direction = "right";
                    nextX += speed;
                }
                if (keyH.leftPressed) {
                    direction = "left";
                    nextX -= speed;
                }
                if (keyH.upPressed) {
                    direction = "up";
                    nextY -= speed;
                }
                if (keyH.downPressed) {
                    direction = "down";
                    nextY += speed;
                }

                // Aggiorna la collisionArea del giocatore
                collisionArea.setLocation(x, y);

                // Aggiorna la collisionArea dell'area di interazione
                interactionArea.setLocation(x - 24, y - 16); // Esempio: l'area di interazione è leggermente più grande di quella del giocatore


                if (!collidesWithObjects(nextX, nextY) && !collidesWithEntities(nextX, nextY) && !collidesWithItems(nextX, nextY)) {
                    x = nextX;
                    y = nextY;
                }

                //alternatore di sprite
                spriteCounter++;
                //velocità di cambio sprite 5-10
                if (spriteCounter > 7) {
                    spriteNum = (spriteNum + 1) % 4;
                    spriteCounter = 0;
                }
            }
            else{
                spriteNum = 0;
            }
        }
    }

    private void attack() {
        switch (direction) {
            case "left" -> direction = "left&attack";
            case "right" -> direction = "right&attack";
            case "down" -> direction = "down&attack";
            case "up" -> direction = "up&attack";
            default -> {}
        }

        spriteNum=1;

        // Imposta un timer per la durata dell'animazione dell'attacco
        Timer timer = new Timer(385, e -> {
            isAttacking = false;
            attackAnimationCompleted = true;
            ((Timer) e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();

    }
    private void updateAttackAnimation() {
        //alternatore di sprite
        spriteCounter++;
        //velocità di cambio sprite 5-10
        if (spriteCounter > 7) {
            spriteNum = (spriteNum + 1) % 4;
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        BufferedImage[] images = switch (direction) {
            case "up" -> new BufferedImage[]{up1, up2, up3, up4};
            case "down" -> new BufferedImage[]{down1, down2, down3, down4};
            case "left" -> new BufferedImage[]{left1, left2, left3, left4};
            case "right" -> new BufferedImage[]{right1, right2, right3, right4};
            case "up&attack" -> new BufferedImage[]{attackUp1,attackUp2 , attackUp3, attackUp4};
            case "down&attack" -> new BufferedImage[]{attackDown1, attackDown2, attackDown3, attackDown4};
            case "left&attack" -> new BufferedImage[]{attackLeft1, attackLeft2, attackLeft3, attackLeft4};
            case "right&attack" -> new BufferedImage[]{attackRight1, attackRight2, attackRight3, attackRight4};
            default -> null;
        };
        int offsetX, offsetY, imageWidth, imageHeight;
        switch (direction) {
            case "down&attack" -> {
                offsetX = -16;
                offsetY = -32;
                imageWidth = 32;
                imageHeight = 48;
            }
            case "left&attack" -> {
                offsetX = -58;
                offsetY = -32;
                imageWidth = 48;
                imageHeight = 32;
            }
            case "right&attack" -> {
                offsetX = -14;
                offsetY = -32;
                imageWidth = 48;
                imageHeight = 32;
            }
            case "up&attack" -> {
                offsetX = -16;
                offsetY = -72;
                imageWidth = 32;
                imageHeight = 48;
            }
            default -> {
                offsetX = -16;
                offsetY = -32;
                imageWidth = 32;
                imageHeight = 32;
            }
        }

        if (images != null) {
            graphics2D.drawImage(images[spriteNum], screenX+offsetX, screenY+offsetY, (imageWidth/2) *scale, (imageHeight/2)*scale, null);
        }
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
        double objectX = collisionObject.getX() * gamePanel.getScale();
        double objectY = collisionObject.getY() * gamePanel.getScale();
        double objectWidth = collisionObject.getWidth() * gamePanel.getScale();
        double objectHeight = collisionObject.getHeight() * gamePanel.getScale();

        return x < objectX + objectWidth &&
                x + tileSize > objectX &&
                y < objectY + objectHeight &&
                y + tileSize > objectY;
    }

    // metodo per verificare la collisione con le entità
    public boolean collidesWithEntities(int nextX, int nextY) {
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

    public boolean isAttacking() {
        return isAttacking;
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
}
