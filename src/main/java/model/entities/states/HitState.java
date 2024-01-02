package model.entities.states;

import model.entities.Entity;
import model.entities.EntityState;
import model.entities.enemies.Enemy;
import model.entities.player.Player;

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
        if(enemy.getSpriteNum()==3) {
            enemy.setHitAnimationCompleted(true);
            //logica dell'hit

        }
        if(enemy.getSpriteNum()==0 && enemy.getHitAnimationCompleted()) {
            enemy.setHitted(false);
        }
        long currentTime = System.currentTimeMillis();

        // Verifica se è passato il periodo di cooldown
        if (currentTime - enemy.getLastHitTime() >= enemy.getHitCooldown()){
            //System.out.println("Sto colpendo il nemico");
            enemy.takeDamage(1);  // 1 danno per hit
            enemy.setLastHitTime(currentTime);  // Aggiorna il tempo dell'ultima hit
        }
        enemy.incrementSpriteCounter();
        //velocità di cambio sprite 5-10
        if (enemy.getSpriteCounter() > 5) {
            //da sistemare per tutte le direzioni e per le collisioni con la mappa
            int nextX = enemy.getX();
            int nextY = enemy.getY();
            int pushback = enemy.getSpeed()*4;
            switch (enemy.getDirection()) {
                case "up","up&attack":
                    if(!enemy.collidesWithObjects(nextX,nextY+pushback)) {
                        enemy.setX(nextX);
                        enemy.setY(nextY + pushback);
                    }
                    break;
                case "down","down&attack" :
                    if(!enemy.collidesWithObjects(nextX,nextY-pushback)){
                        enemy.setX(nextX);
                        enemy.setY(nextY-pushback);
                    }
                    break;
                case "left","left&attack" :
                    if(!enemy.collidesWithObjects(nextX+pushback,nextY)) {
                        enemy.setX(nextX +pushback);
                        enemy.setY(nextY);
                    }
                    break;
                case "right","right&attack" :
                    if(!enemy.collidesWithObjects(nextX-pushback,nextY)) {
                        enemy.setX(nextX - pushback);
                        enemy.setY(nextY);
                    }
                    break;
            }
            enemy.setSpriteNum((enemy.getSpriteNum() + 1) % 4);
            enemy.setSpriteCounter(0);
        }
    }
    private void drawEnemy(Graphics2D graphics2D, Enemy enemy) {
        //PER ORA METTO L'IDLE PERCHE' SE NO L'IMMAGINE RIMANE INVISIBILE DURANTE L'HIT
        BufferedImage[] images = switch (enemy.getDirection()){
            case "up","up&attack","down","down&attack","left","left&attack","right", "right&attack" -> new BufferedImage[]{enemy.getHit1(), enemy.getHit2(), enemy.getHit3(), enemy.getHit4()};
            default -> null;
        };

        int screenX = enemy.getX() - enemy.getGsm().getPlayer().getX() + enemy.getGsm().getPlayer().getScreenX();
        int screenY = enemy.getY() - enemy.getGsm().getPlayer().getY() + enemy.getGsm().getPlayer().getScreenY();

        if (images != null && enemy.getGsm().getMapManager().getCurrentMap() == enemy.getTileManager()) {
            graphics2D.drawImage(images[enemy.getSpriteNum()], screenX-(enemy.getIdle1().getWidth()/2), screenY-(enemy.getIdle1().getHeight()/2), (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
        }

    }

    private void updatePlayer(Player player) {
    }
    private void drawPlayer(Graphics2D graphics2D, Player player) {
    }
}
