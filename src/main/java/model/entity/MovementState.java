package model.entity;

import model.entity.enemies.Enemy;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MovementState implements EntityState {
    private Enemy enemy;
    private BufferedImage[] images;

    public MovementState(Enemy enemy) {
        this.enemy = enemy;
        // Inizializza le immagini per lo stato di movimento
        // ... (usa le immagini appropriate per il movimento)
    }

    @Override
    public void handleInput() {
        // Gestisci input specifici per lo stato di movimento
    }

    @Override
    public void update() {
        // Aggiorna lo stato di movimento
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        // Esegui il disegno specifico per lo stato di movimento

    }

}
