package model.entities.characters.npc;

import model.entities.Entity;
import model.entities.EntityState;
import model.entities.characters.Characters;
import model.entities.states.IdleState;
import model.entities.states.MovementState;
import model.gameState.GameStateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.GamePanel.tileSize;

//Class for npc
public class Npc extends Characters {

    public Npc (){
        super();
    }

    public static class NpcBuilder extends Entity.EntityBuilder<Npc,NpcBuilder> {

        public NpcBuilder( int x, int y) {
            super();
            this.entity.x = x * tileSize;
            this.entity.y = y * tileSize;
        }

        public NpcBuilder setTotalSprite(int totalSprite) {
            this.entity.totalSprite = totalSprite;
            return this;
        }

        public NpcBuilder setSpeed(int speed) {
            this.entity.speed = speed;
            return this;
        }

        public NpcBuilder setSpeedChangeSprite(int speedChangeSprite) {
            this.entity.speedChangeSprite = speedChangeSprite;
            return this;
        }

        public NpcBuilder setSpriteNumLess1(int numSpriteEachDirection) {
            this.entity.spriteNum = numSpriteEachDirection;
            return this;
        }

        public NpcBuilder setDefaultDirection(String direction) {
            this.entity.direction = direction;
            return this;
        }

        public NpcBuilder setIsInteractible(boolean isInteractible){
            this.entity.isInteractable = isInteractible;
            return this;
        }

        public NpcBuilder set16EntityImage(String path_up1, String path_up2, String path_up3, String path_up4,
                                           String path_down1, String path_down2, String path_down3, String path_down4,
                                           String path_left1, String path_left2, String path_left3, String path_left4,
                                           String path_right1, String path_right2, String path_right3, String path_right4) {
            try {
                this.entity.up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up1)));
                this.entity.up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up2)));
                this.entity.up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up3)));
                this.entity.up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up4)));

                this.entity.down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down1)));
                this.entity.down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down2)));
                this.entity.down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down3)));
                this.entity.down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down4)));

                this.entity.left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left1)));
                this.entity.left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left2)));
                this.entity.left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left3)));
                this.entity.left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left4)));

                this.entity.right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right1)));
                this.entity.right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right2)));
                this.entity.right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right3)));
                this.entity.right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right4)));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public NpcBuilder set8EntityImage(String path_up1, String path_up2, String path_down1, String path_down2,
                                          String path_left1, String path_left2, String path_right1, String path_right2) {
            try {
                this.entity.up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up1)));
                this.entity.up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up2)));

                this.entity.down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down1)));
                this.entity.down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down2)));

                this.entity.left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left1)));
                this.entity.left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left2)));

                this.entity.right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right1)));
                this.entity.right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right2)));


            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }
        @Override
        public Npc build() {
            return this.entity;
        }
        @Override
        protected Npc createEntity(){
            return new Npc();
        }

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        BufferedImage[] images = switch (direction) {
            case "up" -> new BufferedImage[]{up1, up2, up3, up4};
            case "down" -> new BufferedImage[]{down1, down2, down3, down4};
            case "left" -> new BufferedImage[]{left1, left2, left3, left4};
            case "right" -> new BufferedImage[]{right1, right2, right3, right4};
            default -> null;
        };

        int screenX = this.x - gsm.getPlayer().getX() + gsm.getPlayer().getScreenX();
        int screenY = this.y - gsm.getPlayer().getY() + gsm.getPlayer().getScreenY();

        if (images != null && gsm.getMapManager().getCurrentMap() == this.tileManager) {
            graphics2D.drawImage(images[spriteNum], screenX, screenY-tileSize, (imageWidth/2)*scale , (imageHeight/2)*scale,null);
        }
    }


    @Override
    public void update() {
        collisionArea.setLocation(x, y);
        if (totalSprite == 16) {
            //alternatore di sprite
            spriteCounter++;
            //più è alto, più è lento
            if (spriteCounter > speedChangeSprite) {
                spriteNum = (spriteNum + 1) % 4;
                spriteCounter = 0;
            }
        } else {
            spriteCounter++;
            //più è alto, più è lento
            if (spriteCounter > speedChangeSprite) {
                spriteNum = (spriteNum + 1) % 2;
                spriteCounter = 0;

            }
        }
        setPath();
        interact();
    }
    public void setPath(){
        // Definisci il percorso predefinito
        int[] pathX = new int[]{this.x, this.x + tileSize, this.x, this.x - tileSize};  // Esempio: movimento orizzontale a destra, poi su, poi a sinistra
        int[] pathY = new int[]{this.y, this.y + tileSize, this.y, this.y - tileSize};       // Modifica il percorso secondo le tue esigenze
        int pathIndex = 0;  // Inizia dal primo punto del percorso

        // Muovi l'entità lungo il percorso predefinito
        this.x = pathX[pathIndex];
        this.y = pathY[pathIndex];
        pathIndex++;
        if(pathIndex == 3) pathIndex = 0;
        // Se hai raggiunto la fine del percorso, ricomincia da capo
    }
}