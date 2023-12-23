package model.entity;

import java.awt.*;

public interface EntityState {
    void handleInput();  // Si possono aggiungere altri metodi in base alle azioni da gestire
    void update();

    void draw(Graphics2D graphics2D);

}
