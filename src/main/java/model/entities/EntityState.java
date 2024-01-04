package model.entities;

import java.awt.*;

public interface EntityState {
    void update(Entity entity);

    void draw(Graphics2D graphics2D, Entity entity);
}
