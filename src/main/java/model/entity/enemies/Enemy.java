package model.entity.enemies;

import controller.KeyHandler;
import model.entity.*;
import model.entity.states.IdleState;
import model.entity.states.MovementState;
import model.gameState.GameStateManager;
import model.tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.GamePanel.tileSize;

public class Enemy extends Entity{
    private int speed;
    private int speedChangeSprite;
    private int spriteNum;
    private BufferedImage
            up1, up2, up3, up4,
            down1, down2, down3, down4,
            left1, left2, left3, left4,
            right1, right2, right3, right4;
    private String direction;
    private int spriteCounter = 0;

    private int totalSprite;
    private int maxLife;
    private int currentLife;
    private int damage;
    private int aggroRange;


    private EnemyState currentState;

    public enum State{IDLE, MOVEMENT}

    public Enemy (){
        this.gsm = GameStateManager.gp.getGsm();
        this.keyH = GameStateManager.keyH;
        this.currentState = new IdleState();
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

        public Enemy.EnemyBuilder setAggroRange(int aggroRange) {
            this.enemy.aggroRange = aggroRange * tileSize;
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
    @Override
    public void draw(Graphics2D graphics2D) {
        currentState.draw(graphics2D, this);
    }
    @Override
    public void update() {
        double distance = Math.hypot(gsm.getPlayer().getX() - this.getX(), gsm.getPlayer().getY() - this.getY());
        if (distance <= aggroRange) {
            System.out.println("Sei nell'aggro");
            setState(State.MOVEMENT);
        } else {
            System.out.println("Non sei nell'aggro");
            setState(State.IDLE);
        }
        currentState.update(this);
    }

    public void moveTowardsPlayer(int playerX, int playerY) {
        int distanceThreshold = tileSize; // Adjust this value as needed

        int distanceX = Math.abs(playerX - this.x);
        int distanceY = Math.abs(playerY - this.y);

        if (distanceX < distanceThreshold && distanceY < distanceThreshold) {
            // The enemy is already close to the player, no need to move
            return;
        }

        if (playerX < this.x) {
            this.setDirection("left");
            this.setX(this.x - this.speed);
        } else if (playerX > this.x) {
            this.setDirection("right");
            this.setX(this.x + this.speed);
        }

        if (playerY < this.y) {
            this.setDirection("up");
            this.setY(this.y - this.speed);
        } else if (playerY > this.y) {
            this.setDirection("down");
            this.setY(this.y + this.speed);
        }
    }

    public void setState(State enemyState) {
        switch (enemyState) {
            case IDLE:
                currentState = new IdleState();
                break;
            case MOVEMENT:
                currentState = new MovementState();
                break;
            // Aggiungi altri stati se necessario
        }
    }

    public int getSpeed() {
        return speed;
    }

    public int getSpeedChangeSprite() {
        return speedChangeSprite;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public BufferedImage getUp1() {
        return up1;
    }

    public BufferedImage getUp2() {
        return up2;
    }

    public BufferedImage getUp3() {
        return up3;
    }

    public BufferedImage getUp4() {
        return up4;
    }

    public BufferedImage getDown1() {
        return down1;
    }

    public BufferedImage getDown2() {
        return down2;
    }

    public BufferedImage getDown3() {
        return down3;
    }

    public BufferedImage getDown4() {
        return down4;
    }

    public BufferedImage getLeft1() {
        return left1;
    }

    public BufferedImage getLeft2() {
        return left2;
    }

    public BufferedImage getLeft3() {
        return left3;
    }

    public BufferedImage getLeft4() {
        return left4;
    }

    public BufferedImage getRight1() {
        return right1;
    }

    public BufferedImage getRight2() {
        return right2;
    }

    public BufferedImage getRight3() {
        return right3;
    }

    public BufferedImage getRight4() {
        return right4;
    }

    public String getDirection() {
        return direction;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public int getTotalSprite() {
        return totalSprite;
    }



    public int getMaxLife() {
        return maxLife;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public int getAggroRange() {
        return aggroRange;
    }

    public int getDamage() {
        return damage;
    }

    public EnemyState getCurrentState() {
        return currentState;
    }

    public void incrementSpriteCounter() {
        this.spriteCounter = spriteCounter +1;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
