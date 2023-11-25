package model.entity;

import controller.KeyHandler;
import model.collisioni.CollisionObject;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static view.GamePanel.tileSize;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    private ArrayList<CollisionObject> currentCollisionMap;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (tileSize/2);
        screenY = gp.screenHeight/2 - (tileSize/2);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = tileSize*3;
        worldY = tileSize*4;
        speed = 4;
        direction = "down";
    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            int nextX = worldX;
            int nextY = worldY;

            if (keyH.upPressed) {
                direction = "up";
                nextY -= speed;
            }
            if (keyH.downPressed) {
                direction = "down";
                nextY += speed;
            }
            if (keyH.leftPressed) {
                direction = "left";
                nextX -= speed;
            }
            if (keyH.rightPressed) {
                direction = "right";
                nextX += speed;
            }

            if (!collidesWithObjects(nextX, nextY)) {
                worldX = nextX;
                worldY = nextY;
            }

            //alternatore di sprite
            spriteCounter++;
            //velocità di cambio sprite 5-10
            if (spriteCounter > 7) {
                spriteNum = (spriteNum + 1) % 4;
                spriteCounter = 0;
            }

        }

        //COLLISIONI
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveUpCharacter0.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveUpCharacter1.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveUpCharacter2.png")));
            up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveUpCharacter3.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveDownCharacter_0.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveDownCharacter_1.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveDownCharacter_2.png")));
            down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveDownCharacter_3.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveLeftCharacter0.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveLeftCharacter1.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveLeftCharacter2.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveLeftCharacter3.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveRightCharacter0.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveRightCharacter1.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveRightCharacter2.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/moveRightCharacter3.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage draw(Graphics2D g2) {
        BufferedImage[] images = switch (direction) {
            case "up" -> new BufferedImage[]{up1, up2, up3, up4};
            case "down" -> new BufferedImage[]{down1, down2, down3, down4};
            case "left" -> new BufferedImage[]{left1, left2, left3, left4};
            case "right" -> new BufferedImage[]{right1, right2, right3, right4};
            default -> null;
        };

        if (images != null && !collidesWithObjects(worldX, worldY)) {
            g2.drawImage(images[spriteNum], screenX, screenY, tileSize+16, tileSize+16, null);
        }
        return null;
    }

    public void setPosition (int x, int y){
        this.worldX = tileSize * x;
        this.worldY = tileSize * y;
    }

    private boolean collidesWithObjects(int nextX, int nextY) {
        // Verifica la collisione con gli oggetti di collisione della mappa corrente
        for (CollisionObject collisionObject : gp.tileManagerCasettaIniziale.getCollisionMap()) {
            if (checkCollision(nextX, nextY, collisionObject)) {
                return true; // Collisione rilevata
            }
        }
        return false; // Nessuna collisione rilevata
    }

    private boolean checkCollision(int x, int y, CollisionObject collisionObject) {
        double objectX = collisionObject.getX() * gp.scale;
        double objectY = collisionObject.getY() * gp.scale;
        double objectWidth = collisionObject.getWidth() * gp.scale;
        double objectHeight = collisionObject.getHeight() * gp.scale;

        return x < objectX + objectWidth &&
                x + tileSize > objectX &&
                y < objectY + objectHeight &&
                y + tileSize > objectY;
    }

    public void setCurrentCollisionMap(ArrayList<CollisionObject> collisionMap) {
        this.currentCollisionMap = collisionMap;
    }
}
