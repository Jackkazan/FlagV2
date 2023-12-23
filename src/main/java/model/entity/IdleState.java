package model.entity;

import model.entity.enemies.Enemy;

import java.awt.*;

public class IdleState implements EntityState {
    private Enemy enemy;

    public IdleState(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {

    }
}
