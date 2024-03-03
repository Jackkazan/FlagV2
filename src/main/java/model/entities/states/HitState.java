package model.entities.states;

import model.entities.Entity;
import model.entities.EntityState;
import model.entities.characters.enemies.Enemy;
import model.entities.characters.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

import static view.GamePanel.tileSize;

public class HitState implements EntityState {


    @Override
    public void update(Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Player" -> updatePlayer((Player) entity);
            case "Enemy" -> updateEnemy((Enemy) entity);
            default -> {}
        }
    }

    @Override
    public void draw(Graphics2D graphics2D, Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Player" -> drawPlayer(graphics2D, (Player) entity);
            case "Enemy" -> drawEnemy(graphics2D, (Enemy) entity);
            default -> {}
        }
    }
    private void updateEnemy(Enemy enemy) {
        long currentTime = System.currentTimeMillis();
        enemy.getCollisionArea().setLocation(enemy.getX(), enemy.getY());
        enemy.updateAttackArea();
        // Verifica se è passato il periodo di cooldown
        if (currentTime - enemy.getLastHitTime() >= enemy.getHitCooldown()){
            //System.out.println("Sto colpendo il nemico");

            //da cambiare, dipende dal danno del nemico
            enemy.takeDamage();  // 1 danno per hit
            enemy.setLastHitTime(currentTime);  // Aggiorna il tempo dell'ultima hit
        }
        enemy.incrementSpriteCounter();
        //velocità di cambio sprite 5-10

        //da sistemare per tutte le direzioni e per le collisioni con la mappa
        int nextX = enemy.getX();
        int nextY = enemy.getY();
        int pushback = enemy.getSpeed()*2;
        switch (enemy.getDirection()) {
            case "up", "up&attack":
                if (!enemy.collidesWithObjects(nextX, nextY + pushback)) {
                    enemy.setX(nextX);
                    enemy.setY(nextY + pushback);
                }
                break;
            case "down", "down&attack":
                if (!enemy.collidesWithObjects(nextX, nextY - pushback)) {
                    enemy.setX(nextX);
                    enemy.setY(nextY - pushback);
                }
                break;
            case "left", "left&attack":
                if (!enemy.collidesWithObjects(nextX + pushback, nextY)) {
                    enemy.setX(nextX + pushback);
                    enemy.setY(nextY);
                }
                break;
            case "right", "right&attack":
                if (!enemy.collidesWithObjects(nextX - pushback, nextY)) {
                    enemy.setX(nextX - pushback);
                    enemy.setY(nextY);
                }
                break;
        }
        enemy.getCollisionArea().setLocation(enemy.getX(), enemy.getY());
        enemy.updateAttackArea();

        //System.out.println("Sprite num: "+ enemy.getSpriteNum());
        if (enemy.getSpriteCounter() > 4) {
            enemy.setSpriteNum((enemy.getSpriteNum() + 1) % 4);
            enemy.setSpriteCounter(0);
        }

        if(enemy.getSpriteNum()==3) {
            enemy.setHitAnimationCompleted(true);
            enemy.setAttacking(false);
            enemy.setAttackAnimationCompleted(true);
            //logica dell'hit

        }
        if(enemy.getSpriteNum()==0 && enemy.getHitAnimationCompleted()) {
            enemy.setHitted(false);
        }

    }
    private void drawEnemy(Graphics2D graphics2D, Enemy enemy) {
        BufferedImage[] images = new BufferedImage[]{};
        switch (enemy.getScale()) {
            case 4:
                images = switch (enemy.getDirection()){
                    case "up","up&attack","down","down&attack","left","left&attack","right", "right&attack" -> new BufferedImage[]{enemy.getHit1(), enemy.getHit2(), enemy.getHit3(), enemy.getHit4()};
                    default -> null;
                };
                break;
            case 8:
                images = switch (enemy.getDirection()) {
                    case "up", "up&attack" -> new BufferedImage[]{enemy.getHit13(), enemy.getHit14(), enemy.getHit15(), enemy.getHit16()};
                    case "down", "down&attack" -> new BufferedImage[]{enemy.getHit9(), enemy.getHit10(), enemy.getHit11(), enemy.getHit12()};
                    case "left", "left&attack" -> new BufferedImage[]{enemy.getHit1(), enemy.getHit2(), enemy.getHit3(), enemy.getHit4()};
                    case "right", "right&attack" -> new BufferedImage[]{enemy.getHit5(), enemy.getHit6(), enemy.getHit7(), enemy.getHit8()};
                    default -> null;
                };
        }



        if (images != null ) {
            switch (enemy.getScale()){
                case 4:
                    graphics2D.drawImage(images[enemy.getSpriteNum()], enemy.getX()-(enemy.getIdle1().getWidth()/2), enemy.getY()-(enemy.getIdle1().getHeight()/2), (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
                    break;
                case 8:
                    graphics2D.drawImage(images[enemy.getSpriteNum()], enemy.getX()-(enemy.getImageHeight() / 2) * enemy.getScale()/3 -8, enemy.getY()-(enemy.getImageHeight() / 2) * enemy.getScale()/3 -tileSize, (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
                    break;
            }
        }

    }

    private void updatePlayer(Player player) {
        player.setAttacking(false);
        player.setAttackAnimationCompleted(true);
        //System.out.println(player.getSpriteNum());
        //System.out.println(player.getHitAnimationCompleted());
        long currentTime = System.currentTimeMillis();

        // Verifica se è passato il periodo di cooldown
        if (currentTime - player.getLastHitTime() >= player.getHitCooldown()){
            //System.out.println("Sto colpendo il nemico");

            //da cambiare, dipende dal danno del nemico
            player.takeDamage();  // 1 danno per hit
            player.setLastHitTime(currentTime);  // Aggiorna il tempo dell'ultima hit
        }
        player.incrementSpriteCounter();
        //velocità di cambio sprite 5-10
        int nextX = player.getX();
        int nextY = player.getY();
        int pushback = player.getSpeed();
        //System.out.println(player.getEnemyHitDirection());
        player.updateCollisionArea();
        player.updateAttackArea();
        switch (player.getEnemyHitDirection()){
            case "up","up&attack":
                if(!player.collidesWithObjects(nextX,nextY-pushback)){
                    player.setX(nextX);
                    player.setY(nextY-pushback);
                }
                break;

            case "down","down&attack" :
                if(!player.collidesWithObjects(nextX,nextY+pushback)) {
                    player.setX(nextX);
                    player.setY(nextY + pushback);
                }
                break;

            case "left","left&attack" :
                if(!player.collidesWithObjects(nextX-pushback,nextY)) {
                    player.setX(nextX - pushback);
                    player.setY(nextY);
                }
                break;

            case "right","right&attack" :
                if(!player.collidesWithObjects(nextX+pushback,nextY)) {
                    player.setX(nextX +pushback);
                    player.setY(nextY);
                }
                break;
            case "null":
                player.setX(nextX);
                player.setY(nextY);
                break;
        }

        player.updateCollisionArea();
        player.updateAttackArea();
        //System.out.println("Sprite num: "+ player.getSpriteNum());

        if (player.getSpriteCounter() > 5) {
            player.setSpriteNum((player.getSpriteNum() + 1) % 4);
            player.setSpriteCounter(0);
        }

        if(player.getSpriteNum()==3) {
            player.setHitAnimationCompleted(true);
            //logica dell'hit
        }

        if(player.getSpriteNum()==0 && player.getHitAnimationCompleted()) {
            player.setHitted(false);
        }

    }
    private void drawPlayer(Graphics2D graphics2D, Player player) {
        BufferedImage[] images = switch (player.getDirection()) {
            case "up" -> new BufferedImage[]{player.getHit1(), player.getHit2(), player.getHit3(), player.getHit4()};
            case "down" -> new BufferedImage[]{player.getHit5(), player.getHit6(), player.getHit7(), player.getHit8()};
            case "left" -> new BufferedImage[]{player.getHit9(), player.getHit10(), player.getHit11(), player.getHit12()};
            case "right" -> new BufferedImage[]{player.getHit13(), player.getHit14(), player.getHit15(), player.getHit16()};
            default -> null;
        };

        if (images != null) {
            int spriteIndex = player.getSpriteNum() % images.length;

            graphics2D.drawImage(images[spriteIndex], player.getScreenX()-16, player.getScreenY()-32, (player.getImageWidth()/2) *player.getScale(), (player.getImageHeight()/2)*player.getScale(), null);
        }
    }

}