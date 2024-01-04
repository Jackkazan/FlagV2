package model.entities.states;

import model.entities.Entity;

import model.entities.EntityState;
import model.entities.characters.enemies.Enemy;


import java.awt.*;
import java.awt.image.BufferedImage;
public class DeadState implements EntityState {
    @Override
    public void update(Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Enemy" -> updateEnemy((Enemy) entity);
            default -> {}
        }
    }

    @Override
    public void draw(Graphics2D graphics2D, Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Enemy" -> drawEnemy(graphics2D, (Enemy) entity);
            default -> {}
        }
    }

    private void updateEnemy(Enemy enemy) {
        if(enemy.getSpriteNum()==8) {
            enemy.setDeadAnimationCompleted(true);
            //logica dell'hit

        }
        if(enemy.getSpriteNum()==0 && enemy.getDeadAnimationCompleted()) {
            enemy.setDead(false);
            enemy.setDespawned(true);
        }
        // alternatore di sprite
        enemy.incrementSpriteCounter();
        // più è alto, più è lento
        if (enemy.getSpriteCounter() > 7) {
            enemy.setSpriteNum((enemy.getSpriteNum() + 1) % 9);
            enemy.setSpriteCounter(0);
        }

        // Implementa la logica per la transizione a DEAD state, se necessario
        //System.out.println("Il nemico deve morì");
    }

    private void drawEnemy(Graphics2D graphics2D, Enemy enemy) {
        BufferedImage[] images = switch (enemy.getDirection()){
            case "up","up&attack","down","down&attack","left","left&attack","right", "right&attack" -> new BufferedImage[]{enemy.getDead1(), enemy.getDead2(), enemy.getDead3(), enemy.getDead4(), enemy.getDead5(), enemy.getDead6(), enemy.getDead7(),enemy.getDead8(),enemy.getDead9()};
            default -> null;
        };

        int screenX = enemy.getX() - enemy.getGsm().getPlayer().getX() + enemy.getGsm().getPlayer().getScreenX();
        int screenY = enemy.getY() - enemy.getGsm().getPlayer().getY() + enemy.getGsm().getPlayer().getScreenY();

        if (images != null && enemy.getGsm().getMapManager().getCurrentMap() == enemy.getTileManager()) {
            if(enemy.getDeadAnimationCompleted())
                graphics2D.drawImage(images[images.length-1], screenX-(enemy.getIdle1().getWidth()/2), screenY-(enemy.getIdle1().getHeight()/2), (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
            else
                graphics2D.drawImage(images[enemy.getSpriteNum()], screenX-(enemy.getIdle1().getWidth()/2), screenY-(enemy.getIdle1().getHeight()/2), (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
        }
    }
}
