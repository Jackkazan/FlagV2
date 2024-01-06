package model.entities;

import controller.KeyHandler;

import java.awt.*;

public interface EntityState {
    KeyHandler keyH = KeyHandler.getInstance();
    void update(Entity entity);

    void draw(Graphics2D graphics2D, Entity entity);
}
