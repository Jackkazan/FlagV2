package model.entities.enemies;

import model.collisions.CollisionObject;
import model.entities.EntityState;
import model.entities.npc.Npc;
import model.entities.states.*;
import model.gameState.GameStateManager;

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

    protected boolean isAttacking =false;
    protected boolean isHitted = false;
    protected boolean attackAnimationCompleted = true;
    private int aggroRange;

    private boolean isDespawned = false;
    private boolean isDead = false;

    private int despawnTimer = 0;
    private int despawnCooldown = 500;
    private int respawnX; // Posizione di respawn X
    private int respawnY; // Posizione di respawn Y

    private long lastHitTime;  // Memorizza il tempo dell'ultima hit
    private long hitCooldown = 1000;  // Cooldown in millisecondi (1 secondo)

    protected BufferedImage attackUp1, attackUp2, attackUp3, attackUp4, attackDown1, attackDown2, attackDown3, attackDown4, attackLeft1, attackLeft2, attackLeft3, attackLeft4, attackRight1, attackRight2, attackRight3, attackRight4;

    protected BufferedImage dead1, dead2, dead3, dead4, dead5, dead6, dead7, dead8, dead9;

    protected BufferedImage idle1,idle2,idle3,idle4;

    protected BufferedImage hit1,hit2,hit3,hit4;
    private boolean isHitAnimationCompleted = true;
    private boolean deadAnimationCompleted= true;

    public void setHitAnimationCompleted(boolean b) {
        this.isHitAnimationCompleted = b;
    }

    public boolean getHitAnimationCompleted() {
        return this.isHitAnimationCompleted;
    }

    public void setDeadAnimationCompleted(boolean b) {
        this.deadAnimationCompleted = b;
    }

    public boolean getDeadAnimationCompleted() {
        return  this.deadAnimationCompleted;
    }

    public void setDead(boolean b) {
        this.isDead=b;
    }

    public enum State{IDLE, MOVEMENT,HIT,ATTACK, RESPAWN, DEAD}
    protected ArrayList<CollisionObject> currentCollisionMap;

    public Enemy (){
        this.gsm = GameStateManager.gp.getGsm();
        this.keyH = GameStateManager.keyH;
        this.currentState = new IdleState();
        this.lastHitTime = System.currentTimeMillis();
    }

    public boolean isNearPlayer() {
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
        //System.out.println("Despawn Timer: " + despawnTimer);
        //System.out.println("Is Despawned: " + isDespawned);
        this.checkDeath();
        if (this.isDead) {
            this.setState(State.DEAD);
        }else {
            if (this.isDespawned) {
                this.reset();
                this.setState(State.RESPAWN);
            } else {
                if (this.isHitted) {
                    this.setState(State.HIT);
                } else {
                    if (this.isAttacking) {
                        this.setState(State.ATTACK);
                    } else {
                        double distance = Math.hypot(gsm.getPlayer().getX() - this.getX(), gsm.getPlayer().getY() - this.getY());
                        if (distance <= this.aggroRange) {
                            this.setState(State.MOVEMENT);
                        } else {
                            this.setState(State.IDLE);
                        }
                    }
                }
            }
        }
        currentState.update(this);
    }

    private void reset() {
        this.currentLife = this.maxLife;
        this.isAttacking = false;
        this.isHitted = false;
    }

    public void checkDeath(){
        if(this.currentLife <= 0 && !this.isDead && !this.isDespawned) {
            this.isDead = true;
            this.deadAnimationCompleted = false;
            this.spriteNum = 0;
            this.despawnTimer = this.despawnCooldown;
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

    public void decrementDespawnTimer(){
        this.despawnTimer--;
    }

    public void setDespawnTimer(int despawnTimer) {
        this.despawnTimer = despawnTimer;
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
            if(this.isNearPlayer()) {
                this.isAttacking = true;
                this.attackAnimationCompleted = false;
                this.spriteNum=0;
            }
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
            case DEAD -> currentState = new DeadState();
            case RESPAWN -> currentState = new RespawnState();
            default -> {}
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
        public Enemy.EnemyBuilder set4HitImage(String path_hit1, String path_hit2, String path_hit3, String path_hit4) {
            try {
                this.entity.hit1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hit1)));
                this.entity.hit2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hit2)));
                this.entity.hit3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hit3)));
                this.entity.hit4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hit4)));

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
        public EnemyBuilder set16AttackImage(String path_attackup1, String path_attackup2, String path_attackup3, String path_attackup4,
                                             String path_attackdown1, String path_attackdown2,String path_attackdown3, String path_attackdown4,
                                             String path_attackleft1, String path_attackleft2,String path_attackleft3, String path_attackleft4,
                                             String path_attackright1, String path_attackright2,String path_attackright3, String path_attackright4) {
            try {
                this.entity.attackUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackup1)));
                this.entity.attackUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackup2)));
                this.entity.attackUp3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackup3)));
                this.entity.attackUp4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackup4)));
                this.entity.attackDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackdown1)));
                this.entity.attackDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackdown2)));
                this.entity.attackDown3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackdown3)));
                this.entity.attackDown4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackdown4)));
                this.entity.attackLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackleft1)));
                this.entity.attackLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackleft2)));
                this.entity.attackLeft3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackleft3)));
                this.entity.attackLeft4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackleft4)));
                this.entity.attackRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackright1)));
                this.entity.attackRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackright2)));
                this.entity.attackRight3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackright3)));
                this.entity.attackRight4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_attackright4)));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }
        public EnemyBuilder set4DeadImage(String path_dead1, String path_dead2, String path_dead3, String path_dead4) {
            try {
                this.entity.dead1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead1)));
                this.entity.dead2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead2)));
                this.entity.dead3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead3)));
                this.entity.dead4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead4)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public EnemyBuilder set9DeadImage(String path_dead1, String path_dead2, String path_dead3, String path_dead4,
                                          String path_dead5, String path_dead6, String path_dead7, String path_dead8, String path_dead9) {
            try {
                this.entity.dead1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead1)));
                this.entity.dead2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead2)));
                this.entity.dead3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead3)));
                this.entity.dead4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead4)));
                this.entity.dead5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead5)));
                this.entity.dead6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead6)));
                this.entity.dead7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead7)));
                this.entity.dead8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead8)));
                this.entity.dead9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_dead9)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }
        public EnemyBuilder setRespawnCoordinates(int respawnX, int respawnY) {
            this.entity.respawnX = respawnX;
            this.entity.respawnY = respawnY;
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

    public void takeDamage(int damage) {
        currentLife -= damage;
        System.out.println("La vita del nemico e' : " + currentLife);
/*
        if(currentLife <= 0) {
            setState(State.DEAD);
        } else {
            setState(State.HIT);
        }
        */
    }


    public void respawn(int respawnX, int respawnY) {
        //System.out.println("Respawning at X: " + respawnX + ", Y: " + respawnY);
        this.isDespawned = false;
        this.x = respawnX * tileSize;
        this.y = respawnY * tileSize;

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
        return this.attackUp1;
    }

    public BufferedImage getAttackUp2() {
        return this.attackUp2;
    }

    public BufferedImage getAttackUp3() {
        return this.attackUp3;
    }

    public BufferedImage getAttackUp4() {
        return this.attackUp4;
    }

    public BufferedImage getAttackDown1() {
        return this.attackDown1;
    }

    public BufferedImage getAttackDown2() {
        return this.attackDown2;
    }

    public BufferedImage getAttackDown3() {
        return this.attackDown3;
    }

    public BufferedImage getAttackDown4() {
        return this.attackDown4;
    }

    public BufferedImage getAttackLeft1() {
        return attackLeft1;
    }

    public BufferedImage getAttackLeft2() {
        return this.attackLeft2;
    }

    public BufferedImage getAttackLeft3() {
        return this.attackLeft3;
    }

    public BufferedImage getAttackLeft4() {
        return this.attackLeft4;
    }

    public BufferedImage getAttackRight1() {
        return this.attackRight1;
    }

    public BufferedImage getAttackRight2() {
        return this.attackRight2;
    }

    public BufferedImage getAttackRight3() {
        return this.attackRight3;
    }

    public BufferedImage getAttackRight4() {
        return this.attackRight4;
    }

    public ArrayList<CollisionObject> getCurrentCollisionMap() {
        return this.currentCollisionMap;
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
    public void setAttackAnimationCompleted(boolean attackAnimationCompleted) {
        this.attackAnimationCompleted = attackAnimationCompleted;
    }

    public boolean isHitted() {
        return this.isHitted;
    }

    public boolean getAttackAnimationCompleted() {
        return this.attackAnimationCompleted;
    }

    public BufferedImage getIdle1() {
        return this.idle1;
    }

    public BufferedImage getIdle2() {
        return this.idle2;
    }

    public BufferedImage getIdle3() {
        return this.idle3;
    }

    public BufferedImage getIdle4() {
        return this.idle4;
    }

    public long getLastHitTime() {
        return lastHitTime;
    }

    public void setLastHitTime(long time) {
        lastHitTime = time;
    }

    public long getHitCooldown() {
        return hitCooldown;
    }

    public boolean isAttackAnimationCompleted() {
        return attackAnimationCompleted;
    }

    public boolean isDespawned() {
        return isDespawned;
    }

    public int getDespawnTimer() {
        return despawnTimer;
    }

    public int getDespawnCooldown() {
        return despawnCooldown;
    }

    public int getRespawnX() {
        return respawnX;
    }

    public int getRespawnY() {
        return respawnY;
    }

    public BufferedImage getDead1() {
        return dead1;
    }

    public BufferedImage getDead2() {
        return dead2;
    }

    public BufferedImage getDead3() {
        return dead3;
    }

    public BufferedImage getDead4() {
        return dead4;
    }

    public BufferedImage getDead5() {
        return dead5;
    }

    public BufferedImage getDead6() {
        return dead6;
    }

    public BufferedImage getDead7() {
        return dead7;
    }

    public BufferedImage getDead8() {
        return dead8;
    }

    public BufferedImage getDead9() {
        return dead9;
    }

    public void setDespawned(boolean despawned) {
        isDespawned = despawned;
    }

    public void setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
    }

    public void setHitted(boolean hitted) {
        isHitted = hitted;
    }

    public boolean isDead() {
        return isDead;
    }

    public BufferedImage getHit1() {
        return hit1;
    }

    public BufferedImage getHit2() {
        return hit2;
    }

    public BufferedImage getHit3() {
        return hit3;
    }

    public BufferedImage getHit4() {
        return hit4;
    }

    public boolean isHitAnimationCompleted() {
        return isHitAnimationCompleted;
    }

    public boolean isDeadAnimationCompleted() {
        return deadAnimationCompleted;
    }
}
