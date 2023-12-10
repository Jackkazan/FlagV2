package model.entity;

import controller.KeyHandler;
import model.collisioni.CollisionObject;
import model.items.KeyItems;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.Timer;

import static view.GamePanel.tileSize;

public class Player {
    private GamePanel gamePanel;
    private KeyHandler keyHandler;

    private int x;
    private int y;
    private int speed;

    private final int screenX;
    private final int screenY;
    private int maxLife = 10;
    private int currentLife = 9;
    private ArrayList<CollisionObject> currentCollisionMap;

    // Nuova area di interazione
    private Rectangle interactionArea = new Rectangle(0, 0, tileSize*2, tileSize*2);

    private boolean isAttacking = false;
    private boolean attackAnimationCompleted = true;

    private BufferedImage
            up1, up2, up3, up4,
            down1, down2, down3, down4,
            left1, left2, left3, left4,
            right1, right2, right3, right4;
    private BufferedImage attackUp1, attackUp2, attackUp3, attackUp4, attackDown1, attackDown2, attackDown3, attackDown4, attackLeft1, attackLeft2, attackLeft3, attackLeft4, attackRight1, attackRight2, attackRight3, attackRight4;
    private String direction;
    private int spriteCounter = 0;
    private int spriteNum = 3;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.getScreenWidth()/2 - (tileSize/2);
        screenY = gamePanel.getScreenHeight()/2 - (tileSize/2);
        setDefaultValues();
        getAttackImages();
        getEntityImage();
    }

    public void setDefaultValues() {
        x = tileSize*4;
        y = tileSize*4;
        maxLife = 6;
        speed = 4;
        direction = "down";
    }

    public void update() {
        if (keyHandler.attackPressed && !isAttacking && attackAnimationCompleted) {
            isAttacking = true;
            attackAnimationCompleted = false;
            attack();
        }

        if (isAttacking) {
            updateAttackAnimation();
        } else {
            if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
                int nextX = x;
                int nextY = y;

                if (keyHandler.rightPressed) {
                    direction = "right";
                    nextX += speed;
                }
                if (keyHandler.leftPressed) {
                    direction = "left";
                    nextX -= speed;
                }
                if (keyHandler.upPressed) {
                    direction = "up";
                    nextY -= speed;
                }
                if (keyHandler.downPressed) {
                    direction = "down";
                    nextY += speed;
                }
                
                // Aggiorna la collisionArea del giocatore
                collisionArea.setLocation(x, y);

                // Aggiorna la collisionArea dell'area di interazione
                interactionArea.setLocation(x - 8, y - 8); // Esempio: l'area di interazione è leggermente più grande di quella del giocatore


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
        Timer timer = new Timer(385, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAttacking = false;
                attackAnimationCompleted = true;
                ((Timer) e.getSource()).stop();
            }
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


    public void getAttackImages() {
        try {
            attackUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackUp_0.png")));
            attackUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackUp_1.png")));
            attackUp3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackUp_2.png")));
            attackUp4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackUp_3.png")));

            attackDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackDown_0.png")));
            attackDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackDown_1.png")));
            attackDown3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackDown_2.png")));
            attackDown4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackDown_3.png")));

            attackLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackLeft_0.png")));
            attackLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackLeft_1.png")));
            attackLeft3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackLeft_2.png")));
            attackLeft4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackLeft_3.png")));

            attackRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackRight_0.png")));
            attackRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackRight_1.png")));
            attackRight3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackRight_2.png")));
            attackRight4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterAttackRight_3.png")));


        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void getEntityImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterUp_0.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterUp_1.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterUp_2.png")));
            up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterUp_3.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterDown_0.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterDown_1.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterDown_2.png")));
            down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterDown_3.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterLeft_0.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterLeft_1.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterLeft_2.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterLeft_3.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterRight_0.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterRight_1.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterRight_2.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/MainCharacterRight_3.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage draw(Graphics2D graphics2D) {
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
        int offsetX, offsetY, offsetWidth, offsetHeight;
        switch (direction) {
            case "down&attack" -> {
                offsetX = -16;
                offsetY = -32;
                offsetWidth = 48;
                offsetHeight = 96;
            }
            case "left&attack" -> {
                offsetX = -64;
                offsetY = -32;
                offsetWidth = 96;
                offsetHeight = 48;
            }
            case "right&attack" -> {
                offsetX = -16;
                offsetY = -32;
                offsetWidth = 96;
                offsetHeight = 48;
            }
            case "up&attack" -> {
                offsetX = -16;
                offsetY = -80;
                offsetWidth = 48;
                offsetHeight = 96;
            }
            default -> {
                offsetX = -16;
                offsetY = -32;
                offsetWidth = 48;
                offsetHeight = 48;
            }
        }

        if (images != null) {
            //standard è tileSize+16 ---------------------------
            graphics2D.drawImage(images[spriteNum], screenX+offsetX, screenY+offsetY, tileSize+offsetWidth, tileSize+offsetHeight, null);
        }
        return null;
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

    //------------------------------------------------------
    // metodo per verificare la collisione con le entità
    public boolean collidesWithEntities(int nextX, int nextY) {
        // Verifica la collisione con le entità della lista npcList
        for (Entity npc : gamePanel.getNpcList()) {
            if (checkCollisionRectangle(nextX, nextY, npc.getCollisionArea())) {
                return true; // Collisione rilevata
            }
        }
        return false; // Nessuna collisione rilevata
    }
    public boolean collidesWithItems(int nextX, int nextY) {
        // Verifica la collisione con gli oggetti della lista keyItemsList
        for (KeyItems items : gamePanel.getKeyItemsList()) {
            if (items.getCollisionArea()!=null && checkCollisionRectangle(nextX, nextY, items.getCollisionArea())) {
                items.interact();
                return true; // Collisione rilevata
            }
        }
        return false; // Nessuna collisione rilevata
    }
    // Aggiungi questo nuovo metodo per verificare la collisione con i CollisionObject
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

    public String getDirection() {
        return this.direction;
    }

    public int getMaxLife() {
        return this.maxLife;
    }

    public int getCurrentLife() {
        return this.currentLife;
    }

    // COLLISION
    private Rectangle collisionArea = new Rectangle(0, 0, 48, 48);

    public Rectangle getCollisionArea() {
        return this.collisionArea;
    }

    public int getScreenX() {
        return this.screenX;
    }

    public int getScreenY() {
        return this.screenY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Rectangle getInteractionArea() {
        return interactionArea;
    }
}
