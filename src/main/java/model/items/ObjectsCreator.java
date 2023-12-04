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
                .setCollisionArea(0,0,16,16)
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setRelatedQuests(questList.get(0))
                .setInteractible(true)
                .build();


        KeyItems portaCasettaInizialeChiusa = new KeyItems.KeyItemsBuilder(gamePanel,4*tileSize,7*tileSize, keyH)
                .setName("portaCasettaInizialeChiusa")
                .setStaticImage("/object/PortaChiusaInterno.png")
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setInteractible(true)
                .setCollisionArea(0,0,48,48)
                .setRelatedQuests(questList.get(1))
                .setScale(2,3)
                .build();



        //aggiunta di tutti gli oggetti alla lista
        objectList.add(keyCasettaIniziale);
        objectList.add(portaCasettaInizialeChiusa);

        keyCasettaIniziale.setInteractionAction(new DisappearAction());
        portaCasettaInizialeChiusa.setInteractionAction(new DisappearAction());

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

                //se hai interagito con chiave e tutte le quest fin qui sono completate allora prendi la chiave
                if (keyItems.getName().equals("keyCasettaIniziale")) {
                    keyItems.setStaticImage("/object/spriteInvisibile16x16.png");
                    questList.get(1).setDone();
                    keyItems.setInteractable(false);
                }
                //se hai interagito con la collisione della porta e tutte le quest fin qui sono completate allora sblocca la porta
                if(keyItems.getName().equals("portaCasettaInizialeChiusa")){
                    keyItems.setStaticImage("/object/portaAperta.png");
                    keyItems.setCollisionArea(new Rectangle(0, 0, 0, 0));
                    questList.get(2).setDone();
                    keyItems.setInteractable(false);

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

