package model.items;


import controller.KeyHandler;
import model.tile.MapManager;
import view.GamePanel;

import java.util.ArrayList;
import java.util.List;
import static view.GamePanel.tileSize;

public class ObjectsCreator {

    public static List<KeyItems> createObjects(GamePanel gamePanel, MapManager mapManager, KeyHandler keyH) {
        List<KeyItems> objectList = new ArrayList<>();

        //inizializzazione oggetti
        KeyItems keyCasettaIniziale = new KeyItems.KeyItemsBuilder(gamePanel, 7*tileSize, 5*tileSize, keyH)
                .setName("keyCasettaIniziale")
                .setStaticImage("/object/key.png")
                .setCollisionArea(16,16)
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setInteractible(true)
                .build();

        KeyItems collisioneInvisibileCasettaIniziale = new KeyItems.KeyItemsBuilder(gamePanel,4*tileSize,9*tileSize, keyH)
                .setName("collisioneInvisibileCasettaIniziale")
                .setStaticImage("/object/muroInvisibile16x16.png")
                .setCollisionArea(48,16)
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setInteractible(true)
                .build();


        //aggiunta di tutti gli oggetti alla lista
        objectList.add(keyCasettaIniziale);
        objectList.add(collisioneInvisibileCasettaIniziale);


        return objectList;
    }

}
