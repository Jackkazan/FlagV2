package model.entities.states;

import model.entities.Entity;
import model.entities.enemies.Enemy;

import java.awt.*;

public interface EntityState {
    void update(Entity entity);

    void draw(Graphics2D graphics2D, Entity entity);
}
