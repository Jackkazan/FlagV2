package model.entity.enemies;

import java.awt.*;

public interface EnemyState {

    void update(Enemy enemy);

    void draw(Graphics2D graphics2D, Enemy enemy);
}
