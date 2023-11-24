package model.entity;

import controller.KeyHandler;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.GamePanel.tileSize;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (tileSize/2);
        screenY = gp.screenHeight/2 - (tileSize/2);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = tileSize*11;
        worldY = tileSize*38;
        speed = 4;
        direction = "down";
    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            if (keyH.upPressed) {
                direction = "up";
                worldY -= speed;
            }
            if (keyH.downPressed) {
                direction = "down";
                worldY += speed;
            }
            if (keyH.leftPressed) {
                direction = "left";
                worldX -= speed;
            }
            if (keyH.rightPressed) {
                direction = "right";
                worldX += speed;
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

        if (images != null) {
            g2.drawImage(images[spriteNum], screenX, screenY, tileSize+16, tileSize+16, null);
        }
        return null;
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
    }
}
