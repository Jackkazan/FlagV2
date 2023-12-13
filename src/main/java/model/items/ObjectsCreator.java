package model.items;


import controller.KeyHandler;
import model.gameState.GameStateManager;
import model.quests.Quest;
import model.quests.QuestInitializer;
import model.tile.MapManager;

import java.util.ArrayList;
import java.util.List;

import static model.items.KeyItems.KeyItemsFactory.createKeyItems;
import static view.GamePanel.tileSize;

public class ObjectsCreator {

    static List<Quest> questList = QuestInitializer.createQuestList();


    public static List<KeyItems> createObjects(GameStateManager gsm, MapManager mapManager, KeyHandler keyH) {
        List<KeyItems> objectList = new ArrayList<>();

        //inizializzazione oggetti
        KeyItems keyCasettaIniziale = new KeyItems.KeyItemsBuilder(gsm, 7, 5, keyH)
                .setName("keyCasettaIniziale")
                .setStaticImage("/object/key.png")
                .setImageDimension(16,16)
                .setCollisionArea(0,0,16,16)
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setRelatedQuests(questList.get(0))
                .setInteractable(true)
                .setInteractionAction(new DisappearOrChangeImageAction())
                .build();

        KeyItems portaCasettaInizialeChiusa = new KeyItems.KeyItemsBuilder(gsm,4,7, keyH)
                .setName("portaCasettaInizialeChiusa")
                .setStaticImage("/object/PortaChiusaInterno.png")
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setInteractable(true)
                .setImageDimension(32,48)
                .setCollisionArea(0,0,48,48)
                .setRelatedQuests(questList.get(1))
                .setInteractionAction(new DisappearOrChangeImageAction())
                .build();


        KeyItems zuccaMarcia1 = new KeyItems.KeyItemsBuilder(gsm, 39, 44, keyH)
                .setName("zuccaMarcia1")
                .setStaticImage("/object/zuccaMarcia.png")
                .setImageDimension(16,16)
                .setCollisionArea(0,0,16,16)
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .setRelatedQuests(questList.get(0))
                .setInteractable(true)
                .setInteractionAction(new DisappearOrChangeImageAction())
                .build();

        KeyItems zuccaMarcia2 = KeyItems.KeyItemsFactory.createKeyItems(zuccaMarcia1, "zuccaMarcia2",47,46);
        KeyItems zuccaMarcia3 = KeyItems.KeyItemsFactory.createKeyItems(zuccaMarcia1, "zuccaMarcia3", 42,48);
        KeyItems zuccaMarcia4 = KeyItems.KeyItemsFactory.createKeyItems(zuccaMarcia1, "zuccaMarcia4",45,52);
        KeyItems zuccaMarcia5 = KeyItems.KeyItemsFactory.createKeyItems(zuccaMarcia1, "zuccaMarcia5", 50,50);


        //aggiunta di tutti gli oggetti alla lista
        objectList.add(keyCasettaIniziale);
        objectList.add(portaCasettaInizialeChiusa);
        objectList.add(zuccaMarcia1);
        objectList.add(zuccaMarcia2);
        objectList.add(zuccaMarcia3);
        objectList.add(zuccaMarcia4);
        objectList.add(zuccaMarcia5);

        return objectList;
    }


    //raccolta oggetti o semplice interazione con scomparsa
    public static class DisappearOrChangeImageAction implements InteractionActionItems {
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
                    keyItems.setCollisionArea(null);        //rimuove collisione della porta
                    questList.get(2).setDone();
                    keyItems.setInteractable(false);
                }

                if(keyItems.getName().matches("zuccaMarcia1|zuccaMarcia2|zuccaMarcia3|zuccaMarcia4|zuccaMarcia5")){
                    keyItems.setStaticImage("/object/spriteInvisibile16x16.png");
                    keyItems.setCollisionArea(null);        //rimuove collisione della porta
                    questList.get(2).setDone();
                    keyItems.setInteractable(false);
                }

            }
            // pannello comunicativo che ti dice che non puoi ancora passare/prendere
            else{

                //se hai interagito con la collisione della porta ma tutte le quest precedenti non sono state completate
                if(keyItems.getName().equals("portaCasettaInizialeChiusa")){
                    System.out.println("La porta è bloccata, guardati attorno, magari puoi trovare qualcosa per aprirla");
                }
            }
        }
    }

    /*
    public static class TalkToNPCAction implements InteractionAction {
        @Override
        public void performAction(KeyItems keyItems) {
            // Implementa l'azione di parlare con un NPC
            System.out.println("Hai parlato con l'NPC: " + keyItems.getName());
        }
    }

     */

}

