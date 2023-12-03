package model.items;


import controller.KeyHandler;
import model.quests.Quest;
import model.quests.QuestInitializer;
import model.tile.MapManager;
import view.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static view.GamePanel.tileSize;

public class ObjectsCreator {

    static List<Quest> questList = QuestInitializer.createQuestList();


    public static List<KeyItems> createObjects(GamePanel gamePanel, MapManager mapManager, KeyHandler keyH) {
        List<KeyItems> objectList = new ArrayList<>();

        //inizializzazione oggetti
        KeyItems keyCasettaIniziale = new KeyItems.KeyItemsBuilder(gamePanel, 7*tileSize, 5*tileSize, keyH)
                .setName("keyCasettaIniziale")
                .setStaticImage("/object/key.png")
                .setCollisionArea(16,16)
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setRelatedQuests(questList.get(0))
                .setInteractible(true)
                .build();

        KeyItems collisioneInvisibileCasettaIniziale = new KeyItems.KeyItemsBuilder(gamePanel,4*tileSize,9*tileSize, keyH)
                .setName("collisioneInvisibileCasettaIniziale")
                .setStaticImage("/object/spriteInvisibile16x16.png")
                .setCollisionArea(48,16)
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setInteractible(true)
                .setRelatedQuests(questList.get(1))
                .build();

        KeyItems portaCasettaIniziale = new KeyItems.KeyItemsBuilder(gamePanel,4*tileSize,7*tileSize, keyH)
                .setName("portaCasettaIniziale")
                .setStaticImage("/object/PortaChiusaInterno.png")
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setInteractible(true)
                .setRelatedQuests(questList.get(1))
                .setScale(2,3)
                .build();


        //aggiunta di tutti gli oggetti alla lista
        objectList.add(keyCasettaIniziale);
        objectList.add(collisioneInvisibileCasettaIniziale);
        objectList.add(portaCasettaIniziale);


        keyCasettaIniziale.setInteractionAction(new DisappearAction());
        collisioneInvisibileCasettaIniziale.setInteractionAction(new DisappearAction());
        portaCasettaIniziale.setInteractionAction(new ChangeAction());


        return objectList;
    }

    //raccolta oggetti o semplice interazione con scomparsa
    public static class DisappearAction implements InteractionAction {
        @Override
        public void performAction(KeyItems keyItems){
            //se le quest prima di interagire con questo oggetto sono state fatte
            if(keyItems.questListIsDone()){
                // Implementa l'azione di nascondere
                System.out.println("Sto nascondendo " + keyItems.getName());
                keyItems.setInteractable(false);
                keyItems.setStaticImage("/object/spriteInvisibile16x16.png");
                keyItems.setCollisionArea(new Rectangle(0,0,0,0));

                //se si ha interagito con la chiave allora la si è presa
                if(keyItems.getName().equals("keyCasettaIniziale")){
                    questList.get(1).setDone();
                }

                if(keyItems.getName().equals("collisioneInvisibileCasettaIniziale")) {
                    questList.get(2).setDone();
                }
            }
            // pannello comunicativo che ti dice che non puoi ancora passare/prendere
            else System.out.println("Devi fare qualcosa prima");
        }
    }

    public static class ChangeAction implements InteractionAction {
        @Override
        public void performAction(KeyItems keyItems){
            //se le quest prima di interagire con questo oggetto sono state fatte
            if(keyItems.questListIsDone()) {
                // Implementa l'azione rimozione dalla lista
                System.out.println("Sto cambiando " + keyItems.getName());
                keyItems.setInteractable(false);
                if (keyItems.getName().equals("portaCasettaIniziale")) {
                    keyItems.setStaticImage("/object/portaApertaInterno.png");
                }
            }
            // pannello comunicativo che ti dice che non puoi ancora passare/prendere
            else System.out.println("Devi fare qualcosa prima");
        }
    }

    public static class TalkToNPCAction implements InteractionAction {
        @Override
        public void performAction(KeyItems keyItems) {
            // Implementa l'azione di parlare con un NPC
            System.out.println("Hai parlato con l'NPC: " + keyItems.getName());
        }
    }

}

