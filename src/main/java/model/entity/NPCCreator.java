package model.entity;

import controller.KeyHandler;
import model.gameState.GameStateManager;
import model.tile.MapManager;
import view.GamePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static view.GamePanel.tileSize;

public class NPCCreator extends Entity{
    private int actionLockCounter = 0;
    public NPCCreator(String entity) {
        super(entity);
    }


    public static List<Entity> createNPCs(GameStateManager gsm, MapManager mapManager) {
        int x_vecchietta = 22*tileSize;
        int y_vecchietta = 46*tileSize;

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

                                                                    //x          //y
        Entity vecchietta = new Entity.EntityBuilder(x_vecchietta, y_vecchietta,"NPC")
                .setName("Vecchietta")
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setCollisionArea(24,30)
                .setImageDimension(32,32)
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
        Entity contadino1 = new Entity.EntityBuilder(86*tileSize, 37*tileSize,"NPC")
                .setName("Contadino1")
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setCollisionArea(24,30)
                .setImageDimension(32,32)
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
        Entity contadino2 = new Entity.EntityBuilder(61*tileSize, 33*tileSize,"NPC")
                .setName("Contadino2")
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setCollisionArea(24,30)
                .setImageDimension(32,32)
                .setInteractionAction(new NpcDialogue(gsm))
                .setDefaultDirection("down")
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .set8EntityImage(contadino2_up1, contadino2_up2,
                        contadino2_down1, contadino2_down2,
                        contadino2_left1, contadino2_left2,
                        contadino2_right1, contadino2_right2)
                .build();


        String cameriera1Down_0 = "/npc/Cameriera/CamerieraVillaggioTavernaDown_0.png";
        String cameriera1Down_1 = "/npc/Cameriera/CamerieraVillaggioTavernaDown_1.png";

        Entity cameriera1 = new Entity.EntityBuilder(15*tileSize, 3*tileSize, "NPC")
                .setName("Cameriera1")
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setCollisionArea(16,37)
                .setTotalSprite(2)
                .setImageDimension(16,32)
                .setInteractionAction(new NpcDialogue(gsm))
                .setDefaultDirection("down")
                .setContainedMap(mapManager.getTileManagerPianoTerraTavernaVillaggio())
                .set8EntityImage(cameriera1Down_0, cameriera1Down_1,
                        cameriera1Down_0, cameriera1Down_1,
                        cameriera1Down_0, cameriera1Down_1,
                        cameriera1Down_0, cameriera1Down_1)
                .build();

        npcList.add(vecchietta);
        npcList.add(contadino1);
        npcList.add(contadino2);
        npcList.add(cameriera1);


        return npcList;
    }


}
