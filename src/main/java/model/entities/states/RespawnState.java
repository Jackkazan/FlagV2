package model.entities.states;

import model.entities.Entity;
import model.entities.EntityState;
import model.entities.characters.enemies.Enemy;

import java.awt.*;

public class RespawnState implements EntityState {
    @Override
    public void update(Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            //case "Player" -> updatePlayer((Player) entity);
            case "Enemy" -> updateEnemy((Enemy) entity);
            default -> {}
        }
    }

    @Override
    public void draw(Graphics2D graphics2D, Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            //case "Player" -> drawPlayer(graphics2D, (Player) entity);
            case "Enemy" -> drawEnemy(graphics2D, (Enemy) entity);
            default -> {}

        }
    }
    private void updateEnemy(Enemy enemy) {
        // Logica di despawn
        if(enemy.getCanRespawn()) {
            enemy.decrementDespawnTimer(); // Decrementa il timer di despawn

            if (enemy.getDespawnTimer() <= 0) {
                enemy.setCollisionArea(new Rectangle(enemy.getRespawnX(), enemy.getRespawnY(), 32, 32));

                enemy.respawn(enemy.getRespawnX(), enemy.getRespawnY()); // Respawn dell'entità
                enemy.setDespawnTimer(enemy.getDespawnCooldown()); // Reimposta il timer di despawn
                enemy.setDespawned(false);
            }
        }
    }
    private void drawEnemy(Graphics2D graphics2D, Enemy enemy) {}
}
