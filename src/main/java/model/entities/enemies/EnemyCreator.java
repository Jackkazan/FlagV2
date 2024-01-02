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

        String greenSlime1AttackDown_0="/enemy/GreenSlime/GreenSlime1AttackDown_0.png";
        String greenSlime1AttackDown_1="/enemy/GreenSlime/GreenSlime1AttackDown_1.png";
        String greenSlime1AttackDown_2="/enemy/GreenSlime/GreenSlime1AttackDown_2.png";
        String greenSlime1AttackDown_3="/enemy/GreenSlime/GreenSlime1AttackDown_3.png";
        String greenSlime1AttackUp_0= "/enemy/GreenSlime/GreenSlime1AttackUp_0.png";
        String greenSlime1AttackUp_1= "/enemy/GreenSlime/GreenSlime1AttackUp_1.png";
        String greenSlime1AttackUp_2= "/enemy/GreenSlime/GreenSlime1AttackUp_2.png";
        String greenSlime1AttackUp_3= "/enemy/GreenSlime/GreenSlime1AttackUp_3.png";
        String greenSlime1AttackLeft_0= "/enemy/GreenSlime/GreenSlime1AttackLeft_0.png";
        String greenSlime1AttackLeft_1= "/enemy/GreenSlime/GreenSlime1AttackLeft_1.png";
        String greenSlime1AttackLeft_2= "/enemy/GreenSlime/GreenSlime1AttackLeft_2.png";
        String greenSlime1AttackLeft_3= "/enemy/GreenSlime/GreenSlime1AttackLeft_3.png";
        String greenSlime1AttackRight_0= "/enemy/GreenSlime/GreenSlime1AttackRight_0.png";
        String greenSlime1AttackRight_1= "/enemy/GreenSlime/GreenSlime1AttackRight_1.png";
        String greenSlime1AttackRight_2= "/enemy/GreenSlime/GreenSlime1AttackRight_2.png";
        String greenSlime1AttackRight_3= "/enemy/GreenSlime/GreenSlime1AttackRight_3.png";

        String greenSlime1Hit_0="/enemy/GreenSlime/GreenSlime1Hit_0.png";
        String greenSlime1Hit_1="/enemy/GreenSlime/GreenSlime1Hit_1.png";
        String greenSlime1Hit_2="/enemy/GreenSlime/GreenSlime1Hit_2.png";
        String greenSlime1Hit_3="/enemy/GreenSlime/GreenSlime1Hit_3.png";

        String greenSlime1Dead_0 = "/enemy/GreenSlime/GreenSlime1Dead_0.png";
        String greenSlime1Dead_1 = "/enemy/GreenSlime/GreenSlime1Dead_1.png";
        String greenSlime1Dead_2 = "/enemy/GreenSlime/GreenSlime1Dead_2.png";
        String greenSlime1Dead_3 = "/enemy/GreenSlime/GreenSlime1Dead_3.png";
        String greenSlime1Dead_4 = "/enemy/GreenSlime/GreenSlime1Dead_4.png";
        String greenSlime1Dead_5 = "/enemy/GreenSlime/GreenSlime1Dead_5.png";
        String greenSlime1Dead_6 = "/enemy/GreenSlime/GreenSlime1Dead_6.png";
        String greenSlime1Dead_7 = "/enemy/GreenSlime/GreenSlime1Dead_7.png";
        String greenSlime1Dead_8 = "/enemy/GreenSlime/GreenSlime1Dead_8.png";

        Enemy slime1 = new Enemy.EnemyBuilder(25,46)
                .setName("Slime1")
                .setSpeed(2)
                .setMaxLife(6)
                .setCurrentLife(6)
                .setDamage(3)
                .setSpeedChangeSprite(10)
                .setSpriteNumLess1(3)
                .setScale(4)
                .setTotalSprite(16)
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
                .set16AttackImage(greenSlime1AttackUp_0, greenSlime1AttackUp_1, greenSlime1AttackUp_2, greenSlime1AttackUp_3,
                        greenSlime1AttackDown_0, greenSlime1AttackDown_1, greenSlime1AttackDown_2, greenSlime1AttackDown_3,
                        greenSlime1AttackLeft_0, greenSlime1AttackLeft_1, greenSlime1AttackLeft_2, greenSlime1AttackLeft_3,
                        greenSlime1AttackRight_0, greenSlime1AttackRight_1, greenSlime1AttackRight_2, greenSlime1AttackRight_3)
                .set9DeadImage(greenSlime1Dead_0, greenSlime1Dead_1, greenSlime1Dead_2,
                        greenSlime1Dead_3, greenSlime1Dead_4, greenSlime1Dead_5,
                        greenSlime1Dead_6, greenSlime1Dead_7, greenSlime1Dead_8)
                .set4HitImage(greenSlime1Hit_0,greenSlime1Hit_1,greenSlime1Hit_2,greenSlime1Hit_3)
                .setAggroRange(10) //10
                .setRespawnCoordinates(25,46)
                .build();

        enemyList.add(slime1);

        return enemyList;
    }
}
