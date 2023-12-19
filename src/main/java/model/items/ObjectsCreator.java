package model.items;


import controller.KeyHandler;
import model.gameState.GameStateManager;
import model.quests.Quest;
import model.quests.QuestInitializer;
import model.sound.Playlist;
import model.tile.MapManager;

import java.util.ArrayList;
import java.util.List;

public class ObjectsCreator {

    static List<Quest> questList = QuestInitializer.createQuestList();
    static KeyItemsPrototype prototypeManager;

    public static List<KeyItems> createObjects(GameStateManager gsm, MapManager mapManager, KeyHandler keyH) {
        List<KeyItems> objectList = new ArrayList<>();

        //Inizializzazione oggetti
        KeyItems keyCasettaIniziale = new KeyItems.KeyItemsBuilder(gsm, 7, 5, keyH)
                .setName("keyCasettaIniziale")
                .setStaticImage("/object/key.png")
                .setImageDimension(16,16)
                .setCollisionArea(16,16)
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
                .setCollisionArea(48,48)
                .setRelatedQuests(questList.get(1))
                .setInteractionAction(new DisappearOrChangeImageAction())
                .build();


        KeyItems zuccaMarcia1 = new KeyItems.KeyItemsBuilder(gsm, 39, 44, keyH)
                .setName("zuccaMarcia1")
                .setStaticImage("/object/zuccaMarcia.png")
                .setImageDimension(16,16)
                .setCollisionArea(16,16)
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .setRelatedQuests(questList.get(0))
                .setInteractable(true)
                .setInteractionAction(new DisappearOrChangeImageAction())
                .build();

        prototypeManager = new KeyItemsPrototype(zuccaMarcia1);

        Prototype zuccaMarcia2 = prototypeManager.createKeyItems("zuccaMarcia2",47,46,16,16);
        Prototype zuccaMarcia3 = prototypeManager.createKeyItems("zuccaMarcia3", 42,48,16,16);
        Prototype zuccaMarcia4 = prototypeManager.createKeyItems("zuccaMarcia4",45,52,16,16);
        Prototype zuccaMarcia5 = prototypeManager.createKeyItems("zuccaMarcia5", 50,50,16,16);



        KeyItems spaventaPasseri1 = new KeyItems.KeyItemsBuilder(gsm, 43, 27, keyH)
                .setName("spaventaPasseri1")
                .setStaticImage("/object/spaventaPasseri.png")
                .setImageDimension(32,48)
                .setCollisionArea(32,64)
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .setRelatedQuests(questList.get(0))
                .setInteractable(true)
                .setInteractionAction(new DisappearOrChangeImageAction())
                .build();

        prototypeManager = new KeyItemsPrototype(spaventaPasseri1);

        Prototype spaventaPasseri2 = prototypeManager.createKeyItems("spaventaPasseri2",64,27,32,64);
        Prototype spaventaPasseri3 = prototypeManager.createKeyItems("spaventaPasseri3", 82,30,32,64);
        Prototype spaventaPasseri4 = prototypeManager.createKeyItems("spaventaPasseri4",44,40,32,64);
        Prototype spaventaPasseri5 = prototypeManager.createKeyItems("spaventaPasseri5", 64,43,32,64);
        Prototype spaventaPasseri6 = prototypeManager.createKeyItems("spaventaPasseri6", 85,42,32,64);



        //Aggiunta di tutti gli oggetti alla lista
        objectList.add(keyCasettaIniziale);
        objectList.add(portaCasettaInizialeChiusa);
        objectList.add(zuccaMarcia1);
        objectList.add((KeyItems) zuccaMarcia2);
        objectList.add((KeyItems) zuccaMarcia3);
        objectList.add((KeyItems) zuccaMarcia4);
        objectList.add((KeyItems) zuccaMarcia5);
        objectList.add(spaventaPasseri1);
        objectList.add((KeyItems) spaventaPasseri2);
        objectList.add((KeyItems) spaventaPasseri3);
        objectList.add((KeyItems) spaventaPasseri4);
        objectList.add((KeyItems) spaventaPasseri5);
        objectList.add((KeyItems) spaventaPasseri6);

        return objectList;
    }


    //Raccolta oggetti o semplice interazione con scomparsa
    public static class DisappearOrChangeImageAction implements InteractionActionItems {
        @Override
        public void performAction(KeyItems keyItems){

            //Se le quest prima di interagire con questo oggetto sono state fatte
            if(keyItems.questListIsDone()){

                // Implementa l'azione di nascondere
                System.out.println("Sto nascondendo " + keyItems.getName());

                switch (keyItems.getName()) {

                    //Se hai interagito con chiave e tutte le quest fin qui sono completate allora prendi la chiave
                    case "keyCasettaIniziale" -> {
                        keyItems.setStaticImage("/object/spriteInvisibile16x16.png");
                        questList.get(1).setDone();
                        keyItems.setInteractable(false);
                    }
                    //Se hai interagito con la collisione della porta e tutte le quest fin qui sono completate allora sblocca la porta
                    case "portaCasettaInizialeChiusa" -> {
                        keyItems.setStaticImage("/object/portaAperta.png");
                        keyItems.setCollisionArea(null);        //rimuove collisione della porta
                        questList.get(2).setDone();
                        keyItems.setInteractable(false);
                    }
                    case "zuccaMarcia1", "zuccaMarcia2", "zuccaMarcia3", "zuccaMarcia4", "zuccaMarcia5" -> {
                        keyItems.setStaticImage("/object/spriteInvisibile16x16.png");
                        keyItems.setCollisionArea(null);        //rimuove collisione
                        questList.get(2).setDone();     // la quest al momento è sbagliata
                        keyItems.setInteractable(false);
                    }
                    case "spaventaPasseri1", "spaventaPasseri2", "spaventaPasseri3", "spaventaPasseri4", "spaventaPasseri5", "spaventaPasseri6" -> {
                        keyItems.setStaticImage("/object/spaventaPasseriDritto.png");
                    }

                    default -> {}
                }


            }
            // Pannello comunicativo che ti dice che non puoi ancora passare/prendere
            else{

                //Se hai interagito con la collisione della porta ma tutte le quest precedenti non sono state completate
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

