package model.entities.enemies;

import model.gameState.GameStateManager;
import model.tile.MapManager;

import java.util.ArrayList;
import java.util.List;

public class EnemyCreator {
    public static List<Enemy> createEnemies(GameStateManager gsm, MapManager mapManager) {

        List<Enemy> enemyList = new ArrayList<>();

        String slimeDown_0 = "/testEnemy/monster/greenslime_down_1.png";
        String slimeDown_1 = "/testEnemy/monster/greenslime_down_2.png";

        Enemy slime1 = new Enemy.EnemyBuilder(25,46)
                .setName("Slime1")
                .setSpeed(2)
                .setMaxLife(6)
                .setCurrentLife(6)
                .setDamage(3)
                .setSpeedChangeSprite(10)
                .setSpriteNumLess1(1)
                .setScale(5)
                .setCollisionArea(32,32)
                .setImageDimension(16,16)
                .setDefaultDirection("left")

                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .setCollisionMap(mapManager.getTileManagerZonaIniziale().getCollisionMap())
                .set8EntityImage(slimeDown_0,slimeDown_1,
                        slimeDown_0,slimeDown_1,
                        slimeDown_0,slimeDown_1,
                        slimeDown_0,slimeDown_1)
                .setAggroRange(10)
                .build();

        enemyList.add(slime1);

        return enemyList;
    }
}
