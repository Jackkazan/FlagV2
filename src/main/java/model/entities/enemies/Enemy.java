package model.entities.enemies;

import model.entities.Interactable;
import model.entities.npc.Npc;
import model.entities.states.IdleState;
import model.entities.states.MovementState;
import model.gameState.GameStateManager;
import model.tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static view.GamePanel.tileSize;

public class Enemy extends Npc {
    protected int maxLife;
    protected int currentLife;
    protected int damage;
    private int aggroRange;
    private EnemyState currentState;

    public enum State{IDLE, MOVEMENT}

    public Enemy (){
        this.gsm = GameStateManager.gp.getGsm();
        this.keyH = GameStateManager.keyH;
        this.currentState = new IdleState();
    }


    public static class EnemyBuilder extends EntityBuilder<Enemy,EnemyBuilder>{

        private int[] pathX;  // Array delle coordinate x del percorso
        private int[] pathY;  // Array delle coordinate y del percorso
        private int pathIndex;

        public EnemyBuilder( int x, int y) {
            super();
            this.entity.x = x * tileSize;
            this.entity.y = y * tileSize;
        }

        public Enemy.EnemyBuilder setMaxLife(int maxLife){
            this.entity.maxLife= maxLife;
            return this;
        }
        public Enemy.EnemyBuilder setDamage(int damage){
            this.entity.damage = damage;
            return this;
        }
        public Enemy.EnemyBuilder setCurrentLife(int currentLife){
            this.entity.currentLife = currentLife;
            return this;
        }

        public Enemy.EnemyBuilder setAggroRange(int aggroRange) {
            this.entity.aggroRange = aggroRange * tileSize;
            return this;
        }

        public Enemy.EnemyBuilder setTotalSprite(int totalSprite) {
            this.entity.totalSprite = totalSprite;
            return this;
        }

        public Enemy.EnemyBuilder setSpeed(int speed) {
            this.entity.speed = speed;
            return this;
        }

        public Enemy.EnemyBuilder setSpeedChangeSprite(int speedChangeSprite) {
            this.entity.speedChangeSprite = speedChangeSprite;
            return this;
        }


        public Enemy.EnemyBuilder setSpriteNumLess1(int numSpriteEachDirection) {
            this.entity.spriteNum = numSpriteEachDirection;
            return this;
        }

        public Enemy.EnemyBuilder setDefaultDirection(String direction) {
            this.entity.direction = direction;
            return this;
        }

        public Enemy.EnemyBuilder setIsInteractible(boolean isInteractible){
            this.entity.isInteractable = isInteractible;
            return this;
        }

        public Enemy.EnemyBuilder set16EntityImage(String path_up1, String path_up2, String path_up3, String path_up4,
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

        public Enemy.EnemyBuilder set8EntityImage(String path_up1, String path_up2, String path_down1, String path_down2,
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
        protected Enemy createEntity() {
            return new Enemy();
        }
        public Enemy build() {
            return (Enemy) this.entity;
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


}
