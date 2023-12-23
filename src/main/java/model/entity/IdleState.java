package model.entity;

import model.entity.enemies.Enemy;
import model.entity.enemies.EnemyState;

import java.awt.*;

public class IdleState implements EnemyState {
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
