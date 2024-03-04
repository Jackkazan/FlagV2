package model.entities.characters.enemies;

import model.entities.Entity;
import model.entities.EntityState;
import model.entities.characters.npc.Npc;
import model.entities.items.Item;
import model.entities.Prototype;
import model.entities.characters.Characters;
import model.gameState.GameStateManager;
import model.entities.states.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static model.gameState.PlayState.nearEntityList;
import static view.GamePanel.tileSize;

public class Enemy extends Characters implements Prototype {
    private int aggroRange;
    private int maxHealthBarWidth; //lunghezza massima della barra della vita
    private boolean isDespawned;
    private int despawnTimer;
    private int despawnCooldown;
    private boolean canRespawn;

    public Enemy() {
        super();
        this.currentState = new IdleState();
        this.lastHitTime = System.currentTimeMillis();
        isAttacking = false;
        isHitted = false;
        isAttackAnimationCompleted = true;
        isDead = false;
        isHitAnimationCompleted = true;
        isDeadAnimationCompleted = true;
        isDespawned = false;
        despawnTimer = 0;
        despawnCooldown = 500;
        hitCooldown = 500;
        damage = 1;
    }

    public boolean isNearPlayer() {
        // puoi definire la logica per verificare se il giocatore è nelle vicinanze in base alle coordinate e alla dimensione dell'oggetto
        if (this.collisionArea != null && gsm.getPlayer().getCollisionArea().intersects(this.collisionArea)) {
            System.out.println(this.name + " mi sta hittando");
            return true;
        } else return false;
    }


    @Override
    public void draw(Graphics2D graphics2D) {
        currentState.draw(graphics2D, this);
        if (!this.isDespawned) {
            // Disegna la barra della vita
            int healthBarWidth = (int) (((double) this.currentLife / this.maxLife) * this.maxHealthBarWidth); //calcola la larghezza della barra della vita in base alla percentuale di vita attuale rispetto alla vita massima
            int screenX = this.x - gsm.getPlayer().getX() + gsm.getPlayer().getScreenX() - this.maxHealthBarWidth / 2 + this.idle1.getWidth() / 2;
            int screenY = this.y - gsm.getPlayer().getY() + gsm.getPlayer().getScreenY();
            graphics2D.setColor(Color.RED);  // Colore della barra
            graphics2D.fillRect(screenX, screenY - tileSize, healthBarWidth, 7);  // Disegna la barra
            graphics2D.setColor(Color.BLACK);  // Colore del bordo della barra
            graphics2D.drawRect(screenX, screenY - tileSize, maxHealthBarWidth, 7);  // Disegna il bordo della barra
            //System.out.println("Bar Coordinates - X: " + screenX + ", Y: " + screenY);
        }
    }
    @Override
    public void update() {
        //System.out.println("Despawn Timer: " + despawnTimer);
        //System.out.println("Is Despawned: " + isDespawned);
        //updateAttackArea()
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
                        if (distance <= this.aggroRange)
                            this.setState(State.MOVEMENT);
                        else
                            this.setState(State.IDLE);

                    }
                }
            }
        }
        currentState.update(this);
        //System.out.println("Direzione "+ this.name+": "+ this.direction);
    }

    private void reset() {
        this.currentLife = this.maxLife;
        this.isAttacking = false;
        this.isHitted = false;
    }

    public void checkDeath() {
        if (this.currentLife <= 0 && !this.isDead && !this.isDespawned) {
            this.isDead = true;
            this.isDeadAnimationCompleted = false;
            this.spriteNum = 0;
            this.despawnTimer = this.despawnCooldown;
        }
    }

    public void hitPlayer() {
        if (this.attackArea.intersects(gsm.getPlayer().getCollisionArea())) {
            gsm.getPlayer().setEnemyHitDirection(this.direction);
            gsm.getPlayer().setEnemyHitDamage(this.damage);
            if (!gsm.getPlayer().isHitted()) {
                gsm.getPlayer().setSpriteNum(0);
                gsm.getPlayer().setHitAnimationCompleted(false);
            }
            gsm.getPlayer().setHitted(true);

            System.out.println(this.name + " ha colpito il player");
        }
    }

    public void decrementDespawnTimer() {
        this.despawnTimer--;
    }

    public void setDespawnTimer(int despawnTimer) {
        this.despawnTimer = despawnTimer;
    }

    public void moveTowardsPlayer(int playerX, int playerY) {
        int distanceThreshold = tileSize; // Adjust this value as needed

        int distanceX = Math.abs(playerX - this.x);
        int distanceY = Math.abs(playerY - this.y);

        if (distanceX < distanceThreshold && distanceY < distanceThreshold) {
            if (this.isPlayerNearby()) {
                this.isAttacking = true;
                this.isAttackAnimationCompleted = false;
                this.spriteNum = 0;
            }
            return;
        }
        int nextX;
        int nextY;
        if (playerX < this.x) {
            nextX = this.x;
            if (!collidesWithObjects(nextX - this.speed, this.y) && !collidesWithEnemies(nextX - this.speed, this.y)) {
                this.setDirection("left");
                this.setX(nextX - this.speed);
            }
        } else if (playerX > this.x) {
            nextX = this.x;
            if (!collidesWithObjects(nextX + this.speed, this.y) && !collidesWithEnemies(nextX + this.speed, this.y)) {
                this.setDirection("right");
                this.setX(nextX + this.speed);
            }
        }

        if (playerY < this.y) {
            nextY = this.y;
            if (!collidesWithObjects(this.x, nextY - this.speed) && !collidesWithEnemies(this.x, nextY - this.speed)) {
                this.setDirection("up");
                this.setY(nextY - this.speed);
            }
        } else if (playerY > this.y) {
            nextY = this.y;
            if (!collidesWithObjects(this.x, nextY + this.speed) && !collidesWithEnemies(this.x, nextY + this.speed)) {
                this.setDirection("down");
                this.setY(nextY + this.speed);
            }
        }
        this.updateAttackArea();
    }

    public boolean collidesWithEnemies(int nextX, int nextY) {

        // Verifica la collisione con le entità della lista npcList
        for (Entity enemy : nearEntityList.stream().filter(entity -> entity instanceof Enemy).toList()) {
            if (enemy.equals(this))
                return false;
            if (checkCollisionRectangle(nextX, nextY, enemy.getCollisionArea())) {
                //System.out.println("Sei stato hittato da "+ enemy.getName());
                return true; // Collisione rilevata
            }
        }
        return false; // Nessuna collisione rilevata
    }

    @Override
    public Prototype clone() {
        try {
            return (Enemy) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setRespawn(int x, int y) {
        this.respawnX = x * tileSize;
        this.respawnY = y * tileSize;
    }


    public static class EnemyBuilder extends Entity.EntityBuilder<Enemy, EnemyBuilder> {

        private int[] pathX;  // Array delle coordinate x del percorso
        private int[] pathY;  // Array delle coordinate y del percorso
        private int pathIndex;

        public EnemyBuilder(int x, int y) {
            super();
            this.entity.x = x * tileSize;
            this.entity.y = y * tileSize;
        }

        public Enemy.EnemyBuilder setMaxLife(int maxLife) {
            this.entity.maxLife = maxLife;
            return this;
        }

        public Enemy.EnemyBuilder setDamage(int damage) {
            this.entity.damage = damage;
            return this;
        }

        public Enemy.EnemyBuilder setCurrentLife(int currentLife) {
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

        public EnemyBuilder setCollisionMap(ArrayList<Rectangle2D.Double> collisionMap) {
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

        public Enemy.EnemyBuilder setIsInteractable(boolean isInteractable) {
            this.entity.isInteractable = isInteractable;
            return this;
        }

        public Enemy.EnemyBuilder setCanRespawn(boolean b) {
            this.entity.canRespawn = b;
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
        public Enemy.EnemyBuilder set24EntityImage(String path_up1, String path_up2, String path_up3, String path_up4, String path_up5, String path_up6,
                                                   String path_down1, String path_down2, String path_down3, String path_down4, String path_down5, String path_down6,
                                                   String path_left1, String path_left2, String path_left3, String path_left4, String path_left5, String path_left6,
                                                   String path_right1, String path_right2, String path_right3, String path_right4, String path_right5, String path_right6) {
            try {
                this.entity.up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up1)));
                this.entity.up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up2)));
                this.entity.up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up3)));
                this.entity.up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up4)));
                this.entity.up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up5)));
                this.entity.up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up6)));

                this.entity.down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down1)));
                this.entity.down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down2)));
                this.entity.down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down3)));
                this.entity.down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down4)));
                this.entity.down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down5)));
                this.entity.down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down6)));


                this.entity.left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left1)));
                this.entity.left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left2)));
                this.entity.left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left3)));
                this.entity.left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left4)));
                this.entity.left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left5)));
                this.entity.left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left6)));


                this.entity.right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right1)));
                this.entity.right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right2)));
                this.entity.right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right3)));
                this.entity.right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right4)));
                this.entity.right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right5)));
                this.entity.right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right6)));


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
                                             String path_attackdown1, String path_attackdown2, String path_attackdown3, String path_attackdown4,
                                             String path_attackleft1, String path_attackleft2, String path_attackleft3, String path_attackleft4,
                                             String path_attackright1, String path_attackright2, String path_attackright3, String path_attackright4) {
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
        public EnemyBuilder set16HitImage(String path_hitup1, String path_hitup2, String path_hitup3, String path_hitup4,
                                             String path_hitdown1, String path_hitdown2, String path_hitdown3, String path_hitdown4,
                                             String path_hitleft1, String path_hitleft2, String path_hitleft3, String path_hitleft4,
                                             String path_hitright1, String path_hitright2, String path_hitright3, String path_hitright4) {
            try {
                this.entity.hit1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitup1)));
                this.entity.hit2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitup2)));
                this.entity.hit3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitup3)));
                this.entity.hit4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitup4)));
                this.entity.hit5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitdown1)));
                this.entity.hit6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitdown2)));
                this.entity.hit7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitdown3)));
                this.entity.hit8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitdown4)));
                this.entity.hit9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitleft1)));
                this.entity.hit10 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitleft2)));
                this.entity.hit11 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitleft3)));
                this.entity.hit12 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitleft4)));
                this.entity.hit13 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitright1)));
                this.entity.hit14 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitright2)));
                this.entity.hit15 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitright3)));
                this.entity.hit16 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_hitright4)));

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
            this.entity.respawnX = respawnX * tileSize;
            this.entity.respawnY = respawnY * tileSize;
            return this;
        }
        public Enemy.EnemyBuilder setMaxHealthBarWidth(int maxHealthBarWidth) {
            this.entity.maxHealthBarWidth = maxHealthBarWidth;
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


    public void takeDamage() {

        currentLife -= gsm.getPlayer().getDamage();
        //System.out.println("La vita del nemico e' : " + currentLife);

    }

    public void respawn(int respawnX, int respawnY) {
        //System.out.println("Respawning at X: " + respawnX + ", Y: " + respawnY);
        this.isDespawned = false;
        this.x = respawnX * tileSize;
        this.y = respawnY * tileSize;

    }
    public void respawn() {
        this.isDespawned = false;
        this.x = respawnX;
        this.y = respawnY;
        this.updateAttackArea();
    }
    public void setDespawned(boolean despawned) {
        isDespawned = despawned;
    }

    public void setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
    }

    public void setHitAnimationCompleted(boolean b) {
        this.isHitAnimationCompleted = b;
    }

    public void setDeadAnimationCompleted(boolean b) {
        this.isDeadAnimationCompleted = b;
    }

    public void setDead(boolean b) {
        this.isDead = b;
    }

    public int getAggroRange() {
        return aggroRange;
    }

    public int getDamage() {
        return damage;
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

    public boolean getCanRespawn(){
        return this.canRespawn;
    }

}


