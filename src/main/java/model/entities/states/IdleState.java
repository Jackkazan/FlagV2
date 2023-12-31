package model.entities.states;

import model.entities.Entity;
import model.entities.EntityState;
import model.entities.enemies.Enemy;
import model.entities.npc.Npc;
import model.entities.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IdleState implements EntityState {
    @Override
    public void update(Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Npc" -> updateNpc((Npc) entity);
            case "Player" -> updatePlayer((Player) entity);
            case "Enemy" -> updateEnemy((Enemy) entity);
            default -> {}
        }
    }
    @Override
    public void draw(Graphics2D graphics2D, Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Npc" -> drawNpc(graphics2D, (Npc) entity);
            case "Player" -> drawPlayer(graphics2D, (Player) entity);
            case "Enemy" -> drawEnemy(graphics2D, (Enemy) entity);
            default -> {}
        }
    }
    public void updateEnemy(Enemy enemy) {
        enemy.getCollisionArea().setLocation(enemy.getX(), enemy.getY());
        if (enemy.getTotalSprite() == 16) {
            // alternatore di sprite
            enemy.incrementSpriteCounter();
            // più è alto, più è lento
            if (enemy.getSpriteCounter() > enemy.getSpeedChangeSprite()) {
                enemy.setSpriteNum((enemy.getSpriteNum() + 1) % 4);
                enemy.setSpriteCounter(0);
            }
        } else {
            enemy.incrementSpriteCounter();
            // più è alto, più è lento
            if (enemy.getSpriteCounter() > enemy.getSpeedChangeSprite()) {
                enemy.setSpriteNum((enemy.getSpriteNum() + 1) % 2);
                enemy.setSpriteCounter(0);
            }
        }
    }
    private void drawEnemy(Graphics2D graphics2D, Enemy enemy){
        BufferedImage[] images = switch (enemy.getDirection()){
            case "up","up&attack","down","down&attack","left","left&attack","right", "right&attack" -> new BufferedImage[]{enemy.getIdle1(), enemy.getIdle2(), enemy.getIdle3(), enemy.getIdle4()};
            default -> null;
        };

        int screenX = enemy.getX() - enemy.getGsm().getPlayer().getX() + enemy.getGsm().getPlayer().getScreenX();
        int screenY = enemy.getY() - enemy.getGsm().getPlayer().getY() + enemy.getGsm().getPlayer().getScreenY();

        if (images != null && enemy.getGsm().getMapManager().getCurrentMap() == enemy.getTileManager()) {
            graphics2D.drawImage(images[enemy.getSpriteNum()], screenX-(enemy.getIdle1().getWidth()/2), screenY-(enemy.getIdle1().getHeight()/2), (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
        }
    }
    public void updateNpc(Npc npc) {

    }
    private void drawNpc(Graphics2D graphics2D, Npc npc){
    }

    public void updatePlayer(Player player){
        /*
        player.setSpriteCounter(0);
        oppure ?
        player.setSpriteNum(0);
        */
    }
    private void drawPlayer(Graphics2D graphics2D, Player player){
        BufferedImage images = switch (player.getDirection()) {
            case "up","up&attack" -> player.getUp1();
            case "down","down&attack" -> player.getDown1();
            case "left","left&attack" -> player.getLeft1();
            case "right", "right&attack" -> player.getRight1();
            default -> null;
        };
        if (images != null) {
            graphics2D.drawImage(images, player.getScreenX()-16, player.getScreenY()-32, (player.getImageWidth()/2) *player.getScale(), (player.getImageHeight()/2)*player.getScale(), null);
        }
    }

}
