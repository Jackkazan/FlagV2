package model.entities.enemies;

import model.gameState.GameStateManager;
import model.tile.MapManager;

import java.util.ArrayList;
import java.util.List;

public class EnemyCreator {
    public static List<Enemy> createEnemies(GameStateManager gsm, MapManager mapManager) {

        List<Enemy> enemyList = new ArrayList<>();

        String greenSlime1Down_0 ="/enemy/GreenSlime/GreenSlime1Down_0.png";
        String greenSlime1Down_1="/enemy/GreenSlime/GreenSlime1Down_1.png";
        String greenSlime1Down_2="/enemy/GreenSlime/GreenSlime1Down_2.png";
        String greenSlime1Down_3="/enemy/GreenSlime/GreenSlime1Down_3.png";
        String greenSlime1Up_0 = "/enemy/GreenSlime/GreenSlime1Up_0.png";
        String greenSlime1Up_1= "/enemy/GreenSlime/GreenSlime1Up_1.png";
        String greenSlime1Up_2= "/enemy/GreenSlime/GreenSlime1Up_2.png";
        String greenSlime1Up_3= "/enemy/GreenSlime/GreenSlime1Up_3.png";
        String greenSlime1Left_0 = "/enemy/GreenSlime/GreenSlime1Left_0.png";
        String greenSlime1Left_1= "/enemy/GreenSlime/GreenSlime1Left_1.png";
        String greenSlime1Left_2= "/enemy/GreenSlime/GreenSlime1Left_2.png";
        String greenSlime1Left_3= "/enemy/GreenSlime/GreenSlime1Left_3.png";
        String greenSlime1Right_0= "/enemy/GreenSlime/GreenSlime1Right_0.png";
        String greenSlime1Right_1= "/enemy/GreenSlime/GreenSlime1Right_1.png";
        String greenSlime1Right_2= "/enemy/GreenSlime/GreenSlime1Right_2.png";
        String greenSlime1Right_3= "/enemy/GreenSlime/GreenSlime1Right_3.png";

        String greenSlime1Idle_0 = "/enemy/GreenSlime/GreenSlime1Idle_0.png";
        String greenSlime1Idle_1 = "/enemy/GreenSlime/GreenSlime1Idle_1.png";
        String greenSlime1Idle_2 = "/enemy/GreenSlime/GreenSlime1Idle_2.png";
        String greenSlime1Idle_3 = "/enemy/GreenSlime/GreenSlime1Idle_3.png";

        Enemy slime1 = new Enemy.EnemyBuilder(25,46)
                .setName("Slime1")
                .setSpeed(2)
                .setMaxLife(6)
                .setCurrentLife(6)
                .setDamage(3)
                .setSpeedChangeSprite(10)
                .setSpriteNumLess1(3)
                .setScale(4)
                .setCollisionArea(32,32)
                .setImageDimension(48,48)
                .setDefaultDirection("left")
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .setCollisionMap(mapManager.getTileManagerZonaIniziale().getCollisionMap())
                .set16EntityImage(greenSlime1Up_0,greenSlime1Up_1,greenSlime1Up_2,greenSlime1Up_3,
                        greenSlime1Down_0,greenSlime1Down_1,greenSlime1Down_2,greenSlime1Down_3,
                        greenSlime1Left_0,greenSlime1Left_1,greenSlime1Left_2,greenSlime1Left_3,
                        greenSlime1Right_0,greenSlime1Right_1,greenSlime1Right_2,greenSlime1Right_3)
                .set4IdleImage(greenSlime1Idle_0,greenSlime1Idle_1,greenSlime1Idle_2,greenSlime1Idle_3)
                .setAggroRange(10)
                .build();

        enemyList.add(slime1);

        return enemyList;
    }
}
