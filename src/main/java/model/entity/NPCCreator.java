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

        String Vecchietta_up1 = "/npc/VecchiettaUp_0.png";
        String Vecchietta_up2= "/npc/VecchiettaUp_1.png";

        String Vecchietta_down1 = "/npc/VecchiettaDown_0.png";
        String Vecchietta_down2 ="/npc/VecchiettaDown_1.png";

        String Vecchietta_left1= "/npc/VecchiettaLeft_0.png";
        String Vecchietta_left2= "/npc/VecchiettaLeft_1.png";

        String Vecchietta_right1= "/npc/VecchiettaRight_0.png";
        String Vecchietta_right2= "/npc/VecchiettaRight_1.png";

        Entity vecchietta = new Entity.EntityBuilder(gamePanel, gsm, 22*tileSize, 46*tileSize, gsm.getKeyH())
                .setName("Vecchietta")
                .setSpeed(2)
                .setSpeedChangeSprite(100)
                .setSpriteNumLess1(1)
                .setScale(5)
                .setCollisionArea(24,30)
                .setTotalSprite(8)
                .setImageDimension(32,32)
                .setIsInteractble(true)
                .setInteractionAction(new NpcDialogue(gsm))
                .setDefaultDirection("down")
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .set8EntityImage(Vecchietta_up1, Vecchietta_up2,
                        Vecchietta_down1, Vecchietta_down2,
                        Vecchietta_left1, Vecchietta_left2,
                        Vecchietta_right1, Vecchietta_right2)
                .build();


        npcList.add(vecchietta);

        return npcList;
    }
}
