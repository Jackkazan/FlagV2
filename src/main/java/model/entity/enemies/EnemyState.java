package model.entity.enemies;

import java.awt.*;

public interface EnemyState {
    void handleInput();  // Si possono aggiungere altri metodi in base alle azioni da gestire
    void update();

    void draw(Graphics2D graphics2D);
}
