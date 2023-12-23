package model.entity.enemies;

import controller.KeyHandler;
import model.entity.Interactable;
import model.entity.npc.Npc;
import model.gameState.GameStateManager;
import model.tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.GamePanel.tileSize;

public class Enemy{

    private String name;
    protected int x;
    private int y;
    private int speed;
    private int speedChangeSprite;

    private KeyHandler keyH;

    private int spriteNum;
    private BufferedImage
            up1, up2, up3, up4,
            down1, down2, down3, down4,
            left1, left2, left3, left4,
            right1, right2, right3, right4;
    private String direction;
    private int spriteCounter = 0;

    private int totalSprite;

    private int scale;

    private Rectangle collisionArea;
    private GameStateManager gsm;

    private TileManager tileManager;
    private boolean isInteractable;

    private Interactable interactionAction;
    private int imageWidth;
    private int imageHeight;
    private int maxLife;
    private int currentLife;
    private int damage;

    private EnemyState currentState; //Stato corrente del nemico


    public Enemy (){
        this.gsm = GameStateManager.gp.getGsm();
        this.keyH = GameStateManager.keyH;
    }


    public static class EnemyBuilder{
        private Enemy enemy;
        private int[] pathX;  // Array delle coordinate x del percorso
        private int[] pathY;  // Array delle coordinate y del percorso
        private int pathIndex;

        public EnemyBuilder( int x, int y) {
            this.enemy = new Enemy();
            this.enemy.x = x* tileSize;
            this.enemy.y = y* tileSize;
        }

        public Enemy.EnemyBuilder setMaxLife(int maxLife){
            this.enemy.maxLife= maxLife;
            return this;
        }
        public Enemy.EnemyBuilder setDamage(int damage){
            this.enemy.damage = damage;
            return this;
        }
        public Enemy.EnemyBuilder setCurrentLife(int currentLife){
            this.enemy.currentLife = currentLife;
            return this;
        }

        public Enemy.EnemyBuilder setName(String name) {
            this.enemy.name = name;
            return this;
        }

        public Enemy.EnemyBuilder setTotalSprite(int totalSprite) {
            this.enemy.totalSprite = totalSprite;
            return this;
        }

        public Enemy.EnemyBuilder setSpeed(int speed) {
            this.enemy.speed = speed;
            return this;
        }

        public Enemy.EnemyBuilder setSpeedChangeSprite(int speedChangeSprite) {
            this.enemy.speedChangeSprite = speedChangeSprite;
            return this;
        }

        public Enemy.EnemyBuilder setCollisionArea(int larghezza, int altezza) {
            this.enemy.collisionArea = new Rectangle(this.enemy.x, this.enemy.y, (larghezza/2)*this.enemy.scale, (altezza/2)*this.enemy.scale);
            return this;
        }


        public Enemy.EnemyBuilder setSpriteNumLess1(int numSpriteEachDirection) {
            this.enemy.spriteNum = numSpriteEachDirection;
            return this;
        }

        public Enemy.EnemyBuilder setDefaultDirection(String direction) {
            this.enemy.direction = direction;
            return this;
        }

        public Enemy.EnemyBuilder setIsInteractible(boolean isInteractible){
            this.enemy.isInteractable = isInteractible;
            return this;
        }
        public Enemy.EnemyBuilder setInteractionAction(Interactable action) {
            this.enemy.interactionAction = action;
            return this;
        }

        public Enemy.EnemyBuilder setImageDimension(int imageWidth, int imageHeight) {
            this.enemy.imageWidth = imageWidth;
            this.enemy.imageHeight = imageHeight;
            return this;
        }

        public Enemy.EnemyBuilder setScale(int scale) {
            this.enemy.scale = scale;
            return this;
        }

        public Enemy.EnemyBuilder setContainedMap(TileManager tileManager) {
            this.enemy.tileManager = tileManager;
            return this;
        }

        public Enemy.EnemyBuilder set16EntityImage(String path_up1, String path_up2, String path_up3, String path_up4,
                                               String path_down1, String path_down2, String path_down3, String path_down4,
                                               String path_left1, String path_left2, String path_left3, String path_left4,
                                               String path_right1, String path_right2, String path_right3, String path_right4) {
            try {
                this.enemy.up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up1)));
                this.enemy.up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up2)));
                this.enemy.up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up3)));
                this.enemy.up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up4)));

                this.enemy.down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down1)));
                this.enemy.down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down2)));
                this.enemy.down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down3)));
                this.enemy.down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down4)));


                this.enemy.left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left1)));
                this.enemy.left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left2)));
                this.enemy.left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left3)));
                this.enemy.left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left4)));


                this.enemy.right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right1)));
                this.enemy.right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right2)));
                this.enemy.right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right3)));
                this.enemy.right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right4)));


            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Enemy.EnemyBuilder set8EntityImage(String path_up1, String path_up2, String path_down1, String path_down2,
                                              String path_left1, String path_left2, String path_right1, String path_right2) {
            try {
                this.enemy.up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up1)));
                this.enemy.up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up2)));

                this.enemy.down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down1)));
                this.enemy.down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down2)));

                this.enemy.left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left1)));
                this.enemy.left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left2)));

                this.enemy.right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right1)));
                this.enemy.right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right2)));


            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }
        public Enemy build() {
            return this.enemy;
        }
    }
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
            graphics2D.drawImage(images[spriteNum], screenX, screenY, (imageWidth/2)*scale , (imageHeight/2)*scale,null);
        }
        //non so se va alla fine
       // currentState.draw(graphics2D);
    }

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
//        currentState.handleInput();
//        currentState.update();
    }

    public void setState(EnemyState state) {
        currentState = state;
    }

    public TileManager getTileManager() {
        return tileManager;
    }
}
