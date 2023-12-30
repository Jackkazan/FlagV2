package model.entities.states;

import model.entities.Entity;

import model.entities.enemies.Enemy;


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
        // Implementa la logica per la transizione a DEAD state, se necessario
        //System.out.println("Il nemico deve morì");
        enemy.die();
    }

    private void drawEnemy(Graphics2D graphics2D, Enemy enemy) {
        // Implementa la logica di disegno per lo stato DEAD
        BufferedImage deadImage = enemy.getDeadImage();

        int screenX = enemy.getX() - enemy.getGsm().getPlayer().getX() + enemy.getGsm().getPlayer().getScreenX();
        int screenY = enemy.getY() - enemy.getGsm().getPlayer().getY() + enemy.getGsm().getPlayer().getScreenY();

        if (deadImage != null && enemy.getGsm().getMapManager().getCurrentMap() == enemy.getTileManager()) {
            graphics2D.drawImage(deadImage, screenX - (deadImage.getWidth() / 2), screenY - (deadImage.getHeight() / 2), (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
        }
    }
}
