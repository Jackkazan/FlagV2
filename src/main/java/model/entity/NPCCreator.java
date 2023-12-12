package model.entity;

import controller.KeyHandler;
import model.gameState.GameStateManager;
import model.tile.MapManager;
import view.GamePanel;

import java.util.ArrayList;
import java.util.List;

import static view.GamePanel.tileSize;

public class NPCCreator {

    public static List<Entity> createNPCs(GamePanel gamePanel, GameStateManager gsm, MapManager mapManager, KeyHandler keyH) {
        List<Entity> npcList = new ArrayList<>();
        // Inizializza le entità e aggiungile alla lista

        String vecchietta_up1 = "/npc/Vecchietta/VecchiettaLookUp_0.png";
        String vecchietta_up2= "/npc/Vecchietta/VecchiettaLookUp_1.png";
        String vecchietta_down1 = "/npc/Vecchietta/VecchiettaLookDown_0.png";
        String vecchietta_down2 ="/npc/Vecchietta/VecchiettaLookDown_1.png";
        String vecchietta_left1= "/npc/Vecchietta/VecchiettaLookLeft_0.png";
        String vecchietta_left2= "/npc/Vecchietta/VecchiettaLookLeft_1.png";
        String vecchietta_right1= "/npc/Vecchietta/VecchiettaLookRight_0.png";
        String vecchietta_right2= "/npc/Vecchietta/VecchiettaLookRight_1.png";
        Entity vecchietta = new Entity.EntityBuilder(gamePanel, gsm, 22*tileSize, 46*tileSize, gsm.getKeyH())
                .setName("Vecchietta")
                .setSpeed(2)
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setScale(5)
                .setCollisionArea(24,30)
                .setTotalSprite(8)
                .setImageDimension(32,32)
                .setIsInteractible(true)
                .setInteractionAction(new NpcDialogue(gsm))
                .setDefaultDirection("left")
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .set8EntityImage(vecchietta_up1, vecchietta_up2,
                        vecchietta_down1, vecchietta_down2,
                        vecchietta_left1, vecchietta_left2,
                        vecchietta_right1, vecchietta_right2)
                .build();


        String contadino1_up1 ="/npc/Contadino1/Contadino1LookUp_0.png";
        String contadino1_up2="/npc/Contadino1/Contadino1LookUp_1.png";
        String contadino1_down1 ="/npc/Contadino1/Contadino1LookDown_0.png";
        String contadino1_down2 = "/npc/Contadino1/Contadino1LookDown_1.png";
        String contadino1_left1="/npc/Contadino1/Contadino1LookLeft_0.png";
        String contadino1_left2="/npc/Contadino1/Contadino1LookLeft_1.png";
        String contadino1_right1="/npc/Contadino1/Contadino1LookRight_0.png";
        String contadino1_right2="/npc/Contadino1/Contadino1LookRight_1.png";
        Entity contadino1 = new Entity.EntityBuilder(gamePanel, gsm, 86*tileSize, 37*tileSize, gsm.getKeyH())
                .setName("Contadino1")
                .setSpeed(2)
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setScale(5)
                .setCollisionArea(24,30)
                .setTotalSprite(8)
                .setImageDimension(32,32)
                .setIsInteractible(true)
                .setInteractionAction(new NpcDialogue(gsm))
                .setDefaultDirection("right")
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .set8EntityImage(contadino1_up1, contadino1_up2,
                        contadino1_down1, contadino1_down2,
                        contadino1_left1, contadino1_left2,
                        contadino1_right1, contadino1_right2)
                .build();

        String contadino2_up1 ="/npc/Contadino2/Contadino2LookUp_0.png";
        String contadino2_up2="/npc/Contadino2/Contadino2LookUp_1.png";
        String contadino2_down1 ="/npc/Contadino2/Contadino2LookDown_0.png";
        String contadino2_down2 = "/npc/Contadino2/Contadino2LookDown_1.png";
        String contadino2_left1="/npc/Contadino2/Contadino2LookLeft_0.png";
        String contadino2_left2="/npc/Contadino2/Contadino2LookLeft_1.png";
        String contadino2_right1="/npc/Contadino2/Contadino2LookRight_0.png";
        String contadino2_right2="/npc/Contadino2/Contadino2LookRight_1.png";
        Entity contadino2 = new Entity.EntityBuilder(gamePanel, gsm, 64*tileSize, 31*tileSize, gsm.getKeyH())
                .setName("Contadino2")
                .setSpeed(2)
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setScale(5)
                .setCollisionArea(24,30)
                .setTotalSprite(8)
                .setImageDimension(32,32)
                .setIsInteractible(true)
                .setInteractionAction(new NpcDialogue(gsm))
                .setDefaultDirection("down")
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .set8EntityImage(contadino2_up1, contadino2_up2,
                        contadino2_down1, contadino2_down2,
                        contadino2_left1, contadino2_left2,
                        contadino2_right1, contadino2_right2)
                .build();


        npcList.add(vecchietta);
        npcList.add(contadino1);
        npcList.add(contadino2);

        return npcList;
    }
}
