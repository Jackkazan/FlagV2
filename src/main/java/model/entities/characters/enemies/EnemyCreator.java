package model.entities.characters.enemies;

import model.entities.Prototype;
import model.gameState.GameStateManager;
import model.tile.MapManager;

import java.util.ArrayList;
import java.util.List;

public class EnemyCreator {

    static EnemyPrototype prototypeManager;
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

        Enemy slime1 = new Enemy.EnemyBuilder(15,76)
                .setName("Slime1")
                .setSpeed(2)
                .setMaxLife(6)
                .setCurrentLife(6)
                .setDamage(1)
                .setSpeedChangeSprite(10)
                .setSpriteNumLess1(3)
                .setScale(4)
                .setTotalSprite(16)
                .setCollisionArea(32,32)
                .setImageDimension(48,48)
                .setDefaultDirection("left")
                .setCanRespawn(true)
                .setContainedMap(mapManager.getTileManagerDungeonSud())
                .setCollisionMap(mapManager.getTileManagerDungeonSud().getCollisionMap())
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
                .setAggroRange(6) //10
                .setRespawnCoordinates(15,76)
                .setMaxHealthBarWidth(65)  // Imposta la lunghezza massima della barra della vita
                .build();

        prototypeManager = new EnemyPrototype(slime1);
        Prototype slime2 = prototypeManager.createEnemy("Slime2", 8,66, 32,32);
        Prototype slime3 = prototypeManager.createEnemy("Slime3", 21,64, 32,32);
        Prototype slime4 = prototypeManager.createEnemy("Slime4", 27,72, 32,32);
        Prototype slime5 = prototypeManager.createEnemy("Slime5", 34,64, 32,32);
        Prototype slime6 = prototypeManager.createEnemy("Slime6", 35,81, 32,32);

        String greenGoblin1Down_0="/enemy/GreenGoblin/GreenGoblinMovement_00.png";
        String greenGoblin1Down_1="/enemy/GreenGoblin/GreenGoblinMovement_01.png";
        String greenGoblin1Down_2="/enemy/GreenGoblin/GreenGoblinMovement_02.png";
        String greenGoblin1Down_3="/enemy/GreenGoblin/GreenGoblinMovement_03.png";
        String greenGoblin1Down_4="/enemy/GreenGoblin/GreenGoblinMovement_04.png";
        String greenGoblin1Down_5="/enemy/GreenGoblin/GreenGoblinMovement_05.png";

        String greenGoblin1Up_0 = "/enemy/GreenGoblin/GreenGoblinMovement_06.png";
        String greenGoblin1Up_1=  "/enemy/GreenGoblin/GreenGoblinMovement_07.png";
        String greenGoblin1Up_2=  "/enemy/GreenGoblin/GreenGoblinMovement_08.png";
        String greenGoblin1Up_3=  "/enemy/GreenGoblin/GreenGoblinMovement_09.png";
        String greenGoblin1Up_4=  "/enemy/GreenGoblin/GreenGoblinMovement_10.png";
        String greenGoblin1Up_5=  "/enemy/GreenGoblin/GreenGoblinMovement_11.png";

        String greenGoblin1Left_0="/enemy/GreenGoblin/GreenGoblinMovement_12.png";
        String greenGoblin1Left_1="/enemy/GreenGoblin/GreenGoblinMovement_13.png";
        String greenGoblin1Left_2="/enemy/GreenGoblin/GreenGoblinMovement_14.png";
        String greenGoblin1Left_3="/enemy/GreenGoblin/GreenGoblinMovement_15.png";
        String greenGoblin1Left_4="/enemy/GreenGoblin/GreenGoblinMovement_16.png";
        String greenGoblin1Left_5="/enemy/GreenGoblin/GreenGoblinMovement_17.png";

        String greenGoblin1Right_0="/enemy/GreenGoblin/GreenGoblinMovement_18.png";
        String greenGoblin1Right_1="/enemy/GreenGoblin/GreenGoblinMovement_19.png";
        String greenGoblin1Right_2="/enemy/GreenGoblin/GreenGoblinMovement_20.png";
        String greenGoblin1Right_3="/enemy/GreenGoblin/GreenGoblinMovement_21.png";
        String greenGoblin1Right_4="/enemy/GreenGoblin/GreenGoblinMovement_22.png";
        String greenGoblin1Right_5="/enemy/GreenGoblin/GreenGoblinMovement_23.png";

        String greenGoblin1Idle_0 ="/enemy/GreenGoblin/GreenGoblinMovement_12.png";
        String greenGoblin1Idle_1 ="/enemy/GreenGoblin/GreenGoblinMovement_15.png";
        String greenGoblin1Idle_2 ="/enemy/GreenGoblin/GreenGoblinMovement_12.png";
        String greenGoblin1Idle_3 ="/enemy/GreenGoblin/GreenGoblinMovement_15.png";

        String greenGoblin1AttackDown_0="/enemy/GreenGoblin/GreenGoblinAttack_00.png";
        String greenGoblin1AttackDown_1="/enemy/GreenGoblin/GreenGoblinAttack_01.png";
        String greenGoblin1AttackDown_2="/enemy/GreenGoblin/GreenGoblinAttack_02.png";
        String greenGoblin1AttackDown_3="/enemy/GreenGoblin/GreenGoblinAttack_03.png";
        String greenGoblin1AttackUp_0=  "/enemy/GreenGoblin/GreenGoblinAttack_04.png";
        String greenGoblin1AttackUp_1=  "/enemy/GreenGoblin/GreenGoblinAttack_05.png";
        String greenGoblin1AttackUp_2=  "/enemy/GreenGoblin/GreenGoblinAttack_06.png";
        String greenGoblin1AttackUp_3=  "/enemy/GreenGoblin/GreenGoblinAttack_07.png";
        String greenGoblin1AttackLeft_0= "/enemy/GreenGoblin/GreenGoblinAttack_08.png";
        String greenGoblin1AttackLeft_1= "/enemy/GreenGoblin/GreenGoblinAttack_09.png";
        String greenGoblin1AttackLeft_2= "/enemy/GreenGoblin/GreenGoblinAttack_10.png";
        String greenGoblin1AttackLeft_3= "/enemy/GreenGoblin/GreenGoblinAttack_11.png";
        String greenGoblin1AttackRight_0="/enemy/GreenGoblin/GreenGoblinAttack_12.png";
        String greenGoblin1AttackRight_1="/enemy/GreenGoblin/GreenGoblinAttack_13.png";
        String greenGoblin1AttackRight_2="/enemy/GreenGoblin/GreenGoblinAttack_14.png";
        String greenGoblin1AttackRight_3="/enemy/GreenGoblin/GreenGoblinAttack_15.png";

        String greenGoblin1Hit_0 ="/enemy/GreenGoblin/GreenGoblinHit_00.png";
        String greenGoblin1Hit_1 ="/enemy/GreenGoblin/GreenGoblinHit_01.png";
        String greenGoblin1Hit_2 ="/enemy/GreenGoblin/GreenGoblinHit_02.png";
        String greenGoblin1Hit_3 ="/enemy/GreenGoblin/GreenGoblinHit_03.png";
        String greenGoblin1Hit_4 ="/enemy/GreenGoblin/GreenGoblinHit_04.png";
        String greenGoblin1Hit_5 ="/enemy/GreenGoblin/GreenGoblinHit_05.png";
        String greenGoblin1Hit_6 ="/enemy/GreenGoblin/GreenGoblinHit_06.png";
        String greenGoblin1Hit_7 ="/enemy/GreenGoblin/GreenGoblinHit_07.png";
        String greenGoblin1Hit_8 ="/enemy/GreenGoblin/GreenGoblinHit_08.png";
        String greenGoblin1Hit_9 ="/enemy/GreenGoblin/GreenGoblinHit_09.png";
        String greenGoblin1Hit_10="/enemy/GreenGoblin/GreenGoblinHit_10.png";
        String greenGoblin1Hit_11="/enemy/GreenGoblin/GreenGoblinHit_11.png";
        String greenGoblin1Hit_12="/enemy/GreenGoblin/GreenGoblinHit_12.png";
        String greenGoblin1Hit_13="/enemy/GreenGoblin/GreenGoblinHit_13.png";
        String greenGoblin1Hit_14="/enemy/GreenGoblin/GreenGoblinHit_14.png";
        String greenGoblin1Hit_15="/enemy/GreenGoblin/GreenGoblinHit_15.png";


        String greenGoblin1Dead_0 = "/enemy/GreenGoblin/GreenGoblinDeath_0.png";
        String greenGoblin1Dead_1 = "/enemy/GreenGoblin/GreenGoblinDeath_1.png";
        String greenGoblin1Dead_2 = "/enemy/GreenGoblin/GreenGoblinDeath_2.png";
        String greenGoblin1Dead_3 = "/enemy/GreenGoblin/GreenGoblinDeath_3.png";
        String greenGoblin1Dead_4 = "/enemy/GreenGoblin/GreenGoblinDeath_4.png";
        String greenGoblin1Dead_5 = "/enemy/GreenGoblin/GreenGoblinDeath_5.png";
        String greenGoblin1Dead_6 = "/enemy/GreenGoblin/GreenGoblinDeath_6.png";
        String greenGoblin1Dead_7 = "/enemy/GreenGoblin/GreenGoblinDeath_7.png";
        String greenGoblin1Dead_8 = "/enemy/GreenGoblin/GreenGoblinDeath_8.png";

        Enemy goblin1 = new Enemy.EnemyBuilder(83,36)
                .setName("Goblin1")
                .setSpeed(1)
                .setMaxLife(10)
                .setCurrentLife(10)
                .setDamage(2)
                .setSpeedChangeSprite(10)
                .setSpriteNumLess1(3)
                .setScale(8)
                .setTotalSprite(16)
                .setCanRespawn(false)
                .setCollisionArea(64,64)
                .setImageDimension(48,48)
                .setDefaultDirection("down")
                .setContainedMap(mapManager.getTileManagerDungeonSud())
                .setCollisionMap(mapManager.getTileManagerDungeonSud().getCollisionMap())
                .set24EntityImage(greenGoblin1Up_0, greenGoblin1Up_1 ,greenGoblin1Up_2 ,greenGoblin1Up_3 ,greenGoblin1Up_4 ,greenGoblin1Up_5,
                        greenGoblin1Down_0 ,greenGoblin1Down_1 ,greenGoblin1Down_2 ,greenGoblin1Down_3 ,greenGoblin1Down_4 ,greenGoblin1Down_5,
                        greenGoblin1Left_0 ,greenGoblin1Left_1 ,greenGoblin1Left_2 ,greenGoblin1Left_3 ,greenGoblin1Left_4 ,greenGoblin1Left_5,
                        greenGoblin1Right_0 ,greenGoblin1Right_1 ,greenGoblin1Right_2 ,greenGoblin1Right_3 ,greenGoblin1Right_4 ,greenGoblin1Right_5)
                .set4IdleImage(greenGoblin1Idle_0,greenGoblin1Idle_1,greenGoblin1Idle_2,greenGoblin1Idle_3)
                .set16AttackImage(greenGoblin1AttackUp_0 ,greenGoblin1AttackUp_1 ,greenGoblin1AttackUp_2 ,greenGoblin1AttackUp_3,
                        greenGoblin1AttackDown_0 ,greenGoblin1AttackDown_1 ,greenGoblin1AttackDown_2 ,greenGoblin1AttackDown_3,
                        greenGoblin1AttackLeft_0 ,greenGoblin1AttackLeft_1 ,greenGoblin1AttackLeft_2 ,greenGoblin1AttackLeft_3,
                        greenGoblin1AttackRight_0 ,greenGoblin1AttackRight_1 ,greenGoblin1AttackRight_2 ,greenGoblin1AttackRight_3)
                .set9DeadImage(greenGoblin1Dead_0 ,greenGoblin1Dead_1 ,greenGoblin1Dead_2 ,
                        greenGoblin1Dead_3 , greenGoblin1Dead_4 ,greenGoblin1Dead_5 ,
                        greenGoblin1Dead_6 , greenGoblin1Dead_7 ,greenGoblin1Dead_8)
                .set16HitImage(greenGoblin1Hit_0 ,greenGoblin1Hit_1 ,greenGoblin1Hit_2 ,greenGoblin1Hit_3 ,greenGoblin1Hit_4 ,
                        greenGoblin1Hit_5 ,greenGoblin1Hit_6 ,greenGoblin1Hit_7 ,greenGoblin1Hit_8 ,greenGoblin1Hit_9,
                        greenGoblin1Hit_10 ,greenGoblin1Hit_11 ,greenGoblin1Hit_12 ,greenGoblin1Hit_13, greenGoblin1Hit_14 ,greenGoblin1Hit_15)
                .setAggroRange(8) //10
                .setRespawnCoordinates(83,36)
                .setMaxHealthBarWidth(65)  // Imposta la lunghezza massima della barra della vita
                .build();

        prototypeManager = new EnemyPrototype(goblin1);

        Prototype goblin2 = prototypeManager.createEnemy("Goblin2", 75,50, 64,64);
        Prototype goblin3 = prototypeManager.createEnemy("Goblin3", 88,56, 64,64);
        Prototype goblin4 = prototypeManager.createEnemy("Goblin4", 88,71, 64,64);



        enemyList.add(slime1);
        enemyList.add((Enemy) slime2);
        enemyList.add((Enemy) slime3);
        enemyList.add((Enemy) slime4);
        enemyList.add((Enemy) slime5);
        enemyList.add((Enemy) slime6);
        enemyList.add(goblin1);

        enemyList.add((Enemy) goblin2);
        enemyList.add((Enemy) goblin3);
        enemyList.add((Enemy) goblin4);


        return enemyList;
    }
}
