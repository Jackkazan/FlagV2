package model.entity;

import model.entity.enemies.Enemy;

import java.awt.*;

public class AttackState implements EntityState{
    private Enemy enemy;

    public AttackState(Enemy enemy){
        this.enemy = enemy;
    }

    @Override
    public void handleInput() {
        // Gestisci input specifici per lo stato di attacco
    }

    @Override
    public void update() {
        // Aggiorna lo stato di attacco
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        // Esegui il disegno specifico per lo stato di attacco
    }
}
