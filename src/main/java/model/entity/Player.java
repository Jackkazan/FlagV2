package model.entity;

import model.collisions.CollisionObject;
import model.items.KeyItems;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.Timer;

import static view.GamePanel.tileSize;

public class Player extends Entity{
    private int imageWidth = 32;
    private int imageHeight = 32;

    private boolean isAttacking = false;
    private boolean attackAnimationCompleted = true;
    private ArrayList<CollisionObject> currentCollisionMap;

    // Nuova area di interazione
    private Rectangle interactionArea = new Rectangle(0, 0, tileSize*2, tileSize*2);


    private BufferedImage
            up1, up2, up3, up4,
            down1, down2, down3, down4,
            left1, left2, left3, left4,
            right1, right2, right3, right4;
    private BufferedImage attackUp1, attackUp2, attackUp3, attackUp4, attackDown1, attackDown2, attackDown3, attackDown4, attackLeft1, attackLeft2, attackLeft3, attackLeft4, attackRight1, attackRight2, attackRight3, attackRight4;

    public Player() {
        super("Player");
        getEntityImage();
        getAttackImages();
    }

    public void setCurrentSwordStateAndArmor(swordStateAndArmor currentSwordStateAndArmor) {
        this.currentSwordStateAndArmor = currentSwordStateAndArmor;
    }

    @Override
    public void update() {
        if (this.getKeyHandler().attackVPressed && !isAttacking && attackAnimationCompleted) {
            isAttacking = true;
            attackAnimationCompleted = false;
            attack();
        }

        if (isAttacking) {
            updateAttackAnimation();
        } else {
            if (this.getKeyHandler().upPressed || this.getKeyHandler().downPressed || this.getKeyHandler().leftPressed || this.getKeyHandler().rightPressed) {
                int nextX = this.getX();
                int nextY = this.getY();

                if (this.getKeyHandler().rightPressed) {
                    this.setDirection("right");
                    nextX += this.getSpeed();
                }
                if (this.getKeyHandler().leftPressed) {
                    this.setDirection("left");
                    nextX -= this.getSpeed();
                }
                if (this.getKeyHandler().upPressed) {
                    this.setDirection("up");
                    nextY -= this.getSpeed();
                }
                if (this.getKeyHandler().downPressed) {
                    this.setDirection("down");
                    nextY += this.getSpeed();
                }

                // Aggiorna la collisionArea del giocatore
                collisionArea.setLocation(this.getX(), this.getY());

                // Aggiorna la collisionArea dell'area di interazione
                interactionArea.setLocation(this.getX() - 8, this.getY() - 8); // Esempio: l'area di interazione è leggermente più grande di quella del giocatore


                if (!collidesWithObjects(nextX, nextY) && !collidesWithEntities(nextX, nextY) && !collidesWithItems(nextX, nextY)) {
                    this.setX(nextX);
                    this.setY(nextY);
                }


                //alternatore di sprite
                this.setSpriteCounter(this.getSpriteCounter()+1);
                //velocità di cambio sprite 5-10
                if (this.getSpriteCounter() > 7) {
                    this.setSpriteNum((this.getSpriteNum() + 1) % 4);
                    this.setSpriteCounter(0);
                }
            }
            else{
                this.setSpriteNum(0);
            }

        }

    }
    private void attack() {
        switch (this.getDirection()) {
            case "left" -> this.setDirection("left&attack");
            case "right" -> this.setDirection("right&attack");
            case "down" -> this.setDirection("down&attack");
            case "up" -> this.setDirection("up&attack");
            default -> {}
        }

        this.setSpriteNum(1);

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
        this.setSpriteCounter(this.getSpriteCounter() + 1);
        //velocità di cambio sprite 5-10
        if (this.getSpriteCounter() > 7) {
            this.setSpriteNum((this.getSpriteNum() + 1) % 4);
            this.setSpriteCounter(0);
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        BufferedImage[] images = switch (this.getDirection()) {
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
        switch (this.getDirection()) {
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
            graphics2D.drawImage(images[this.getSpriteNum()], this.getScreenX()+offsetX, this.getScreenY()+offsetY, (imageWidth/2) *this.getScale(), (imageHeight/2)*this.getScale(), null);
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
        double objectX = collisionObject.getX() * this.getGamePanel().getScale();
        double objectY = collisionObject.getY() * this.getGamePanel().getScale();
        double objectWidth = collisionObject.getWidth() * this.getGamePanel().getScale();
        double objectHeight = collisionObject.getHeight() * this.getGamePanel().getScale();

        return x < objectX + objectWidth &&
                x + tileSize > objectX &&
                y < objectY + objectHeight &&
                y + tileSize > objectY;
    }


    // metodo per verificare la collisione con le entità
    public boolean collidesWithEntities(int nextX, int nextY) {
        // Verifica la collisione con le entità della lista npcList
        for (Entity npc : this.getGsm().getNpcList()) {
            if (npc.getTileManager().equals(this.getGsm().getMapManager().getCurrentMap()) && checkCollisionRectangle(nextX, nextY, npc.getCollisionArea())) {
                return true; // Collisione rilevata
            }
        }

        for (Entity enemy : this.getGsm().getEnemyList()) {
            if (enemy.getTileManager().equals(this.getGsm().getMapManager().getCurrentMap()) && checkCollisionRectangle(nextX, nextY, enemy.getCollisionArea())) {
                return true; // Collisione rilevata
            }
        }
        return false; // Nessuna collisione rilevata
    }
    public boolean collidesWithItems(int nextX, int nextY) {
        // Verifica la collisione con gli oggetti della lista keyItemsList
        for (KeyItems items : this.getGsm().getKeyItemsList()) {
            if (items.getTileManager().equals(this.getGsm().getMapManager().getCurrentMap()) && items.getCollisionArea()!=null && checkCollisionRectangle(nextX, nextY, items.getCollisionArea())) {
                items.interact();
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
        int playerTileX = this.getX() / tileSize;
        int playerTileY = this.getY() / tileSize;
        return Math.abs(playerTileX - targetX) <= tolerance && Math.abs(playerTileY - targetY) <= tolerance;
    }

    public void teleport(int targetX, int targetY) {
        this.setX(tileSize * targetX);
        this.setY(tileSize * targetY);
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

//    public String getDirection() {
//        return this.direction;
//    }
//
//    public int getMaxLife() {
//        return this.maxLife;
//    }
//
//    public int getCurrentLife() {
//        return this.currentLife;
//    }

    // COLLISION
    private Rectangle collisionArea = new Rectangle(0, 0, 48, 48);

    public Rectangle getCollisionArea() {
        return this.collisionArea;
    }

    public Rectangle getInteractionArea() {
        return interactionArea;
    }

//    public int getScale() {
//        return scale;
//    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
}
