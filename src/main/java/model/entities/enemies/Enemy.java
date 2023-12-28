package model.entities.enemies;

import model.collisions.CollisionObject;
import model.entities.EntityState;
import model.entities.Interactable;
import model.entities.npc.Npc;
import model.entities.states.AttackState;
import model.entities.states.HitState;
import model.entities.states.IdleState;
import model.entities.states.MovementState;
import model.gameState.GameStateManager;
import model.tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static view.GamePanel.tileSize;

public class Enemy extends Npc {
    protected int maxLife;
    protected int currentLife;
    protected int damage;

    protected boolean isAttacking;
    protected boolean isHitted;
    protected boolean attackAnimationCompleted;
    private int aggroRange;

    protected BufferedImage attackUp1, attackUp2, attackUp3, attackUp4, attackDown1, attackDown2, attackDown3, attackDown4, attackLeft1, attackLeft2, attackLeft3, attackLeft4, attackRight1, attackRight2, attackRight3, attackRight4;

    protected BufferedImage idle1,idle2,idle3,idle4;
    public enum State{IDLE, MOVEMENT,HIT,ATTACK}
    protected ArrayList<CollisionObject> currentCollisionMap;

    public Enemy (){
        this.gsm = GameStateManager.gp.getGsm();
        this.keyH = GameStateManager.keyH;
        this.currentState = new IdleState();
    }

    public boolean isHittingPlayer() {
        // puoi definire la logica per verificare se il giocatore è nelle vicinanze in base alle coordinate e alla dimensione dell'oggetto
        if(this.collisionArea!= null && gsm.getPlayer().getCollisionArea().intersects(this.collisionArea)){
            System.out.println(this.name+" mi sta hittando" );
            return true;
        }
        else return false;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        currentState.draw(graphics2D, this);
    }
    @Override
    public void update() {
        if(isAttacking){
            this.setState(State.ATTACK);
        }else {
            double distance = Math.hypot(gsm.getPlayer().getX() - this.getX(), gsm.getPlayer().getY() - this.getY());
            if (distance <= aggroRange) {
                //System.out.println("Sei nell'aggro");
                this.setState(State.MOVEMENT);
            } else {
                //System.out.println("Non sei nell'aggro");
                this.setState(State.IDLE);
            }
        }
        currentState.update(this);
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
        double objectX = collisionObject.getX() * gsm.getGamePanel().getScale();
        double objectY = collisionObject.getY() * gsm.getGamePanel().getScale();
        double objectWidth = collisionObject.getWidth() * gsm.getGamePanel().getScale();
        double objectHeight = collisionObject.getHeight() * gsm.getGamePanel().getScale();

        return x < objectX + objectWidth &&
                x + tileSize > objectX &&
                y < objectY + objectHeight &&
                y + tileSize > objectY;
    }

    public void moveTowardsPlayer(int playerX, int playerY) {
        int distanceThreshold = tileSize; // Adjust this value as needed

        int distanceX = Math.abs(playerX - this.x);
        int distanceY = Math.abs(playerY - this.y);

        if (distanceX < distanceThreshold && distanceY < distanceThreshold) {
            if(this.isHittingPlayer())
                this.setState(State.ATTACK);
            return;
        }
        int nextX;
        int nextY;
        if (playerX < this.x) {
            nextX = this.x;
            if(!collidesWithObjects(nextX - this.speed,this.y)) {
                this.setDirection("left");
                this.setX(nextX - this.speed);
            }
        } else if (playerX > this.x) {
            nextX = this.x;
            if(!collidesWithObjects(nextX + this.speed,this.y)) {
                this.setDirection("right");
                this.setX(nextX + this.speed);
            }
        }

        if (playerY < this.y) {
            nextY = this.y ;
            if(!collidesWithObjects(this.x,nextY- this.speed)) {
                this.setDirection("up");
                this.setY(nextY- this.speed);
            }
        } else if (playerY > this.y) {
            nextY = this.y ;
            if(!collidesWithObjects(this.x,nextY+ this.speed)) {
                this.setDirection("down");
                this.setY(nextY+ this.speed);
            }
        }
    }

    public void setState(State enemyState) {
        switch (enemyState) {
            case IDLE -> currentState = new IdleState();
            case MOVEMENT -> currentState = new MovementState();
            case ATTACK -> currentState = new AttackState();
            case HIT -> currentState = new HitState();
            default -> {
            }
        }
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


        public EnemyBuilder setCollisionMap(ArrayList<CollisionObject> collisionMap) {
            this.entity.currentCollisionMap = collisionMap;
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

        public Enemy.EnemyBuilder setIsInteractable(boolean isInteractable){
            this.entity.isInteractable = isInteractable;
            return this;
        }

        public Enemy.EnemyBuilder set4IdleImage(String path_idle1, String path_idle2, String path_idle3, String path_idle4) {
            try {
                this.entity.idle1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_idle1)));
                this.entity.idle2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_idle2)));
                this.entity.idle3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_idle3)));
                this.entity.idle4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_idle4)));

            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public EntityState getCurrentState() {
        return currentState;
    }

    public void incrementSpriteCounter() {
        this.spriteCounter = spriteCounter +1;
    }

    public BufferedImage getAttackUp1() {
        return attackUp1;
    }

    public BufferedImage getAttackUp2() {
        return attackUp2;
    }

    public BufferedImage getAttackUp3() {
        return attackUp3;
    }

    public BufferedImage getAttackUp4() {
        return attackUp4;
    }

    public BufferedImage getAttackDown1() {
        return attackDown1;
    }

    public BufferedImage getAttackDown2() {
        return attackDown2;
    }

    public BufferedImage getAttackDown3() {
        return attackDown3;
    }

    public BufferedImage getAttackDown4() {
        return attackDown4;
    }

    public BufferedImage getAttackLeft1() {
        return attackLeft1;
    }

    public BufferedImage getAttackLeft2() {
        return attackLeft2;
    }

    public BufferedImage getAttackLeft3() {
        return attackLeft3;
    }

    public BufferedImage getAttackLeft4() {
        return attackLeft4;
    }

    public BufferedImage getAttackRight1() {
        return attackRight1;
    }

    public BufferedImage getAttackRight2() {
        return attackRight2;
    }

    public BufferedImage getAttackRight3() {
        return attackRight3;
    }

    public BufferedImage getAttackRight4() {
        return attackRight4;
    }

    public ArrayList<CollisionObject> getCurrentCollisionMap() {
        return currentCollisionMap;
    }
    public boolean isAttacking() {
        return isAttacking;
    }
    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
    public void setAttackAnimationCompleted(boolean attackAnimationCompleted) {
        this.attackAnimationCompleted = attackAnimationCompleted;
    }

    public boolean isHitted() {
        return isHitted;
    }

    public boolean isAttackAnimationCompleted() {
        return attackAnimationCompleted;
    }

    public BufferedImage getIdle1() {
        return idle1;
    }

    public BufferedImage getIdle2() {
        return idle2;
    }

    public BufferedImage getIdle3() {
        return idle3;
    }

    public BufferedImage getIdle4() {
        return idle4;
    }
}
