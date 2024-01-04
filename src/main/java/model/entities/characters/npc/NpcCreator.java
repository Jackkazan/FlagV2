package model.entities.characters.npc;

import model.gameState.GameStateManager;
import model.tile.MapManager;

import java.util.ArrayList;
import java.util.List;

public class NpcCreator {

    private int spriteCounter = 0;
    private int spriteNum = 3;
    private int speed;


    public static List<Npc> createNPCs(GameStateManager gsm, MapManager mapManager) {

        List<Npc> npcList = new ArrayList<>();
        // Inizializza le entità e aggiungile alla lista

        String vecchietta_up1 = "/npc/Vecchietta/VecchiettaLookUp_0.png";
        String vecchietta_up2= "/npc/Vecchietta/VecchiettaLookUp_1.png";
        String vecchietta_down1 = "/npc/Vecchietta/VecchiettaLookDown_0.png";
        String vecchietta_down2 ="/npc/Vecchietta/VecchiettaLookDown_1.png";
        String vecchietta_left1= "/npc/Vecchietta/VecchiettaLookLeft_0.png";
        String vecchietta_left2= "/npc/Vecchietta/VecchiettaLookLeft_1.png";
        String vecchietta_right1= "/npc/Vecchietta/VecchiettaLookRight_0.png";
        String vecchietta_right2= "/npc/Vecchietta/VecchiettaLookRight_1.png";
        Npc vecchietta = new Npc.NpcBuilder( 22, 47 )
                .setName("Vecchietta")
                .setSpeed(2)
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setScale(5)
                .setCollisionArea(64,32)
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
        Npc contadino1 = new Npc.NpcBuilder(86, 38)
                .setName("Contadino1")
                .setSpeed(2)
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setScale(5)
                .setCollisionArea(64,32)
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
        Npc contadino2 = new Npc.NpcBuilder(61, 34)
                .setName("Contadino2")
                .setSpeed(2)
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setScale(5)
                .setCollisionArea(64,32)
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

        String cameriera1Down_0 = "/npc/Cameriera/CamerieraVillaggioTavernaDown_0.png";
        String cameriera1Down_1 = "/npc/Cameriera/CamerieraVillaggioTavernaDown_1.png";
        Npc cameriera1 = new Npc.NpcBuilder( 15, 4)
                .setName("Cameriera1")
                .setSpeed(2)
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setScale(5)
                .setCollisionArea(64,60)
                .setTotalSprite(2)
                .setImageDimension(16,32)
                .setIsInteractible(true)
                .setInteractionAction(new NpcDialogue(gsm))
                .setDefaultDirection("down")
                .setContainedMap(mapManager.getTileManagerPianoTerraTavernaVillaggio())
                .set8EntityImage(cameriera1Down_0, cameriera1Down_1,
                        cameriera1Down_0, cameriera1Down_1,
                        cameriera1Down_0, cameriera1Down_1,
                        cameriera1Down_0, cameriera1Down_1)
                .build();

        String mercante1Right_0 = "/npc/Mercante1/VenditoreOggettiVillaggioRight_0.png";
        String mercante1Right_1 = "/npc/Mercante1/VenditoreOggettiVillaggioRight_1.png";
        Npc mercante1 = new Npc.NpcBuilder( 2, 5)
                .setName("Mercante1")
                .setSpeed(2)
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setScale(5)
                .setCollisionArea(90,64)
                .setTotalSprite(2)
                .setImageDimension(16,32)
                .setIsInteractible(true)
                .setInteractionAction(new NpcDialogue(gsm))
                .setDefaultDirection("right")
                .setContainedMap(mapManager.getTileManagerNegozioItemsVillaggioSud())
                .set8EntityImage(mercante1Right_0, mercante1Right_1,
                        mercante1Right_0, mercante1Right_1,
                        mercante1Right_0, mercante1Right_1,
                        mercante1Right_0, mercante1Right_1)
                .build();


        String fabbro1Down_0 = "/npc/Fabbro1/FabbroVillaggioDown_0.png";
        Npc fabbro1 = new Npc.NpcBuilder(66,31)
                .setName("Fabbro1")
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(0)
                .setScale(5)
                .setCollisionArea(64,32)
                .setTotalSprite(1)
                .setImageDimension(32,32)
                .setIsInteractible(true)
                .setInteractionAction(new NpcDialogue(gsm))
                .setDefaultDirection("down")
                .setContainedMap(mapManager.getTileManagerVillaggioSud())
                .set8EntityImage(fabbro1Down_0, fabbro1Down_0,
                        fabbro1Down_0, fabbro1Down_0,
                        fabbro1Down_0, fabbro1Down_0,
                        fabbro1Down_0, fabbro1Down_0)
                .build();


        npcList.add(vecchietta);
        npcList.add(contadino1);
        npcList.add(contadino2);
        npcList.add(cameriera1);
        npcList.add(mercante1);
        npcList.add(fabbro1);

       return npcList;
    }
}
