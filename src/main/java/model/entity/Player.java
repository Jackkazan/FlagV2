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

public class Player {
    private GamePanel gamePanel;
    private KeyHandler keyHandler;

    private int x;
    private int y;
    private int speed;

    private final int screenX;
    private final int screenY;
    private ArrayList<CollisionObject> currentCollisionMap;

    private BufferedImage
            up1, up2, up3, up4,
            down1, down2, down3, down4,
            left1, left2, left3, left4,
            right1, right2, right3, right4;
    private String direction;

    private int spriteCounter = 0;
    private int spriteNum = 3;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.getScreenWidth()/2 - (tileSize/2);
        screenY = gamePanel.getScreenHeight()/2 - (tileSize/2);
        setDefaultValues();
        getEntityImage();
    }

    public void setDefaultValues() {
        x = tileSize*17;
        y = tileSize*38;
        speed = 4;
        direction = "down";
    }

    public void update() {

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

            if (!collidesWithObjects(nextX, nextY)) {
                x = nextX;
                y = nextY;
            }
            double length = Math.sqrt(nextX * nextX + nextY * nextY);

            // Normalizza il vettore
            nextX /= length;
            nextY /= length;

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

    public void getEntityImage() {
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


    public BufferedImage draw(Graphics2D graphics2D) {
        BufferedImage[] images = switch (direction) {
            case "up" -> new BufferedImage[]{up1, up2, up3, up4};
            case "down" -> new BufferedImage[]{down1, down2, down3, down4};
            case "left" -> new BufferedImage[]{left1, left2, left3, left4};
            case "right" -> new BufferedImage[]{right1, right2, right3, right4};
            default -> null;
        };

        if (images != null && !collidesWithObjects(x, y)) {
            graphics2D.drawImage(images[spriteNum], screenX, screenY, tileSize+16, tileSize+16, null);
        }
        return null;
    }

    public boolean collidesWithObjects(int nextX, int nextY) {
        // Verifica la collisione con gli oggetti di collisione della mappa corrente
        for (CollisionObject collisionObject : currentCollisionMap) {
            if (checkCollision(nextX, nextY, collisionObject)) {
                return true; // Collisione rilevata
            }
        }
        return false; // Nessuna collisione rilevata
    }

    public boolean checkCollision(int x, int y, CollisionObject collisionObject) {
        double objectX = collisionObject.getX() * gamePanel.getScale();
        double objectY = collisionObject.getY() * gamePanel.getScale();
        double objectWidth = collisionObject.getWidth() * gamePanel.getScale();
        double objectHeight = collisionObject.getHeight() * gamePanel.getScale();

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


    // COLLISION
    private Rectangle collisionArea = new Rectangle(0, 0, 48, 48);

    public Rectangle getCollisionArea() {
        return collisionArea;
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
}
