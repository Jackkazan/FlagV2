package model.entities.states;

import model.entities.Entity;
import model.entities.enemies.Enemy;
import model.entities.player.Player;

import java.awt.*;

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
    }
    private void drawEnemy(Graphics2D graphics2D, Enemy enemy) {
    }

    private void updatePlayer(Player player) {
    }
    private void drawPlayer(Graphics2D graphics2D, Player player) {
    }
}
