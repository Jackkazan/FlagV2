package model.entities.characters.enemies;

import model.entities.Prototype;

import java.awt.*;

class EnemyPrototype {

    private final Prototype prototype;

    public EnemyPrototype(Prototype prototype){this.prototype = prototype;}

    public Prototype createEnemy(String name, int x, int y, int collisionWidth, int collisionHeight){

        Enemy newEnemy = (Enemy) prototype.clone();

        newEnemy.setName(name);
        newEnemy.setPosition(x,y);
        newEnemy.setCollisionArea(new Rectangle(x,y,collisionWidth,collisionHeight));
        newEnemy.setRespawn(x,y);

        return newEnemy;
    }
}
