package model.entity;

import model.collisioni.CollisionObject;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static view.GamePanel.tileSize;

//Class for npc and enemy
public class Entity {
    //Name entity es. npc_1 ecc...
    private String name;
    //Coordinate spawn npc
    private int x;
    private int y;
    //Coordinate player for collision
    private int xPlayer;
    private int yPlayer;
    private int speed;
    private BufferedImage
            up1, up2, down1, down2,
            left1, left2, right1, right2;
    private String direction = "down";
    private int spriteCounter = 0;
    private int spriteNum = 3;
    private GamePanel gamePanel;
    private final int screenX = 800;
    private final int screenY = 608;

    private Entity(){
    }

    public static class EntityBuilder{
        private Entity entity;


        public EntityBuilder(GamePanel gamePanel,int x, int y){
            this.entity = new Entity();
            this.entity.gamePanel = gamePanel;
            this.entity.x = x;
            this.entity.y = y;
        }

        public EntityBuilder setName(String name) {
            this.entity.name = name;
            return this;
        }

        public EntityBuilder setSpeed(int speed){
            this.entity.speed = speed;
            return this;
        }
        public EntityBuilder setEntityImage(String path_up1, String path_up2,
                                   String path_down1, String path_down2,
                                   String path_left1, String path_left2,
                                   String path_right1, String path_right2) {
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

        public Entity build(){
            return this.entity;
        }
    }



    public BufferedImage draw(Graphics2D graphics2D) {
        if (up1 != null ) {
            graphics2D.drawImage(up1, this.x, this.y, tileSize+16, tileSize+16, null);
        }
        return null;
    }


    //Futura """"AI""""
    public void setupAI() {

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
