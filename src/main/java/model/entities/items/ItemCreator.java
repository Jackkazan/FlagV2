package model.entities.items;


import controller.KeyHandler;
import model.entities.Prototype;
import model.entities.npc.Npc;
import model.gameState.GameStateManager;
import model.entities.Interactable;
import model.quests.Quest;
import model.quests.QuestInitializer;
import model.tile.MapManager;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator {

    static List<Quest> questList = QuestInitializer.createQuestList();
    static ItemPrototype prototypeManager;

    public static List<Item> createObjects(GameStateManager gsm, MapManager mapManager, KeyHandler keyH) {
        List<Item> objectList = new ArrayList<>();

        //Inizializzazione oggetti
        Item keyCasettaIniziale = new Item.ItemBuilder(7, 4)
                .setName("keyCasettaIniziale")
                .setStaticImage("/object/key.png")
                .setImageDimension(16,16)
                .setCollisionArea(16,16)
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setRelatedQuests(questList.get(0))
                .setInteractable(true)
                .setInteractionAction(new DisappearOrChangeImageAction())
                .build();

        Item portaCasettaInizialeChiusa = new Item.ItemBuilder(4,7)
                .setName("portaCasettaInizialeChiusa")
                .setStaticImage("/object/PortaChiusaInterno.png")
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setInteractable(true)
                .setImageDimension(32,48)
                .setCollisionArea(48,48)
                .setRelatedQuests(questList.get(1))
                .setInteractionAction(new DisappearOrChangeImageAction())
                .build();

        Item zuccaMarcia1 = new Item.ItemBuilder(39, 44)
                .setName("zuccaMarcia1")
                .setStaticImage("/object/zuccaMarcia.png")
                .setImageDimension(16,16)
                .setCollisionArea(16,16)
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .setRelatedQuests(questList.get(0))
                .setInteractable(true)
                .setInteractionAction(new DisappearOrChangeImageAction())
                .build();

        prototypeManager = new ItemPrototype(zuccaMarcia1);

        Prototype zuccaMarcia2 = prototypeManager.createKeyItems("zuccaMarcia2",47,46,16,16);
        Prototype zuccaMarcia3 = prototypeManager.createKeyItems("zuccaMarcia3", 42,48,16,16);
        Prototype zuccaMarcia4 = prototypeManager.createKeyItems("zuccaMarcia4",45,52,16,16);
        Prototype zuccaMarcia5 = prototypeManager.createKeyItems("zuccaMarcia5", 50,50,16,16);



        Item spaventaPasseri1 = new Item.ItemBuilder(43, 27)
                .setName("spaventaPasseri1")
                .setStaticImage("/object/spaventaPasseri.png")
                .setImageDimension(32,48)
                .setCollisionArea(32,64)
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .setRelatedQuests(questList.get(0))
                .setInteractable(true)
                .setInteractionAction(new DisappearOrChangeImageAction())
                .build();

        prototypeManager = new ItemPrototype(spaventaPasseri1);

        Prototype spaventaPasseri2 = prototypeManager.createKeyItems("spaventaPasseri2",64,27,32,64);
        Prototype spaventaPasseri3 = prototypeManager.createKeyItems("spaventaPasseri3", 82,30,32,64);
        Prototype spaventaPasseri4 = prototypeManager.createKeyItems("spaventaPasseri4",44,40,32,64);
        Prototype spaventaPasseri5 = prototypeManager.createKeyItems("spaventaPasseri5", 64,43,32,64);
        Prototype spaventaPasseri6 = prototypeManager.createKeyItems("spaventaPasseri6", 85,42,32,64);


        //Aggiunta di tutti gli oggetti alla lista
        objectList.add(keyCasettaIniziale);
        objectList.add(portaCasettaInizialeChiusa);
        objectList.add(zuccaMarcia1);
        objectList.add((Item) zuccaMarcia2);
        objectList.add((Item) zuccaMarcia3);
        objectList.add((Item) zuccaMarcia4);
        objectList.add((Item) zuccaMarcia5);
        objectList.add(spaventaPasseri1);
        objectList.add((Item) spaventaPasseri2);
        objectList.add((Item) spaventaPasseri3);
        objectList.add((Item) spaventaPasseri4);
        objectList.add((Item) spaventaPasseri5);
        objectList.add((Item) spaventaPasseri6);

        return objectList;
    }


    //Raccolta oggetti o semplice interazione con scomparsa
    public static class DisappearOrChangeImageAction implements Interactable {
        @Override
        public void performAction(Item item){

            //Se le quest prima di interagire con questo oggetto sono state fatte
            if(item.questListIsDone()){

                // Implementa l'azione di nascondere
                System.out.println("Sto nascondendo " + item.getName());

                switch (item.getName()) {

                    //Se hai interagito con chiave e tutte le quest fin qui sono completate allora prendi la chiave
                    case "keyCasettaIniziale" -> {
                        item.setStaticImage("/object/spriteInvisibile16x16.png");
                        questList.get(1).setDone();
                        item.setInteractable(false);
                    }
                    //Se hai interagito con la collisione della porta e tutte le quest fin qui sono completate allora sblocca la porta
                    case "portaCasettaInizialeChiusa" -> {
                        item.setStaticImage("/object/portaAperta.png");
                        item.setCollisionArea(null);        //rimuove collisione della porta
                        questList.get(2).setDone();
                        item.setInteractable(false);
                    }
                    case "zuccaMarcia1", "zuccaMarcia2", "zuccaMarcia3", "zuccaMarcia4", "zuccaMarcia5" -> {
                        item.setStaticImage("/object/spriteInvisibile16x16.png");
                        item.setCollisionArea(null);        //rimuove collisione
                        questList.get(2).setDone();     // la quest al momento è sbagliata
                        item.setInteractable(false);
                    }

                    case "spaventaPasseri1", "spaventaPasseri2", "spaventaPasseri3", "spaventaPasseri4", "spaventaPasseri5", "spaventaPasseri6" -> {
                        item.setStaticImage("/object/spaventaPasseriDritto.png");
                    }

                    default -> {}
                }


            }
            // Pannello comunicativo che ti dice che non puoi ancora passare/prendere
            else{

                //Se hai interagito con la collisione della porta ma tutte le quest precedenti non sono state completate
                if(item.getName().equals("portaCasettaInizialeChiusa")){
                    System.out.println("La porta è bloccata, guardati attorno, magari puoi trovare qualcosa per aprirla");
                }
            }
        }

        @Override
        public void performAction(Npc npc) {}
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

