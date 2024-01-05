package model.entities.items;


import controller.KeyHandler;
import model.entities.Prototype;
import model.entities.characters.npc.Npc;
import model.gameState.GameStateManager;
import model.entities.Interactable;
import model.quests.Quest;
import model.quests.QuestInitializer;
import model.tile.MapManager;

import java.util.ArrayList;
import java.util.List;

import static view.GamePanel.tileSize;

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


        Item portaCasettaInizialeChiusa = new Item.ItemBuilder(4,8)
                .setName("portaCasettaInizialeChiusa")
                .setStaticImage("/object/PortaChiusaInterno.png")
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setOffsetY(-tileSize)
                .setInteractable(true)
                .setImageDimension(32,48)
                .setCollisionArea(4*tileSize,9*tileSize,48,16)
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
        Prototype zuccaMarcia2 = prototypeManager.createItem("zuccaMarcia2",47,46,16,16);
        Prototype zuccaMarcia3 = prototypeManager.createItem("zuccaMarcia3", 42,48,16,16);
        Prototype zuccaMarcia4 = prototypeManager.createItem("zuccaMarcia4",45,52,16,16);
        Prototype zuccaMarcia5 = prototypeManager.createItem("zuccaMarcia5", 50,50,16,16);


        Item spaventaPasseri1 = new Item.ItemBuilder(43, 28)
                .setName("spaventaPasseri1")
                .setStaticImage("/object/spaventaPasseri.png")
                .setImageDimension(32,48)
                .setCollisionArea(32,32)
                .setOffsetY(-48)
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .setRelatedQuests(questList.get(0))
                .setInteractable(true)
                .setInteractionAction(new DisappearOrChangeImageAction())
                .build();
        prototypeManager = new ItemPrototype(spaventaPasseri1);
        Prototype spaventaPasseri2 = prototypeManager.createItem("spaventaPasseri2",64,28,32,32);
        Prototype spaventaPasseri3 = prototypeManager.createItem("spaventaPasseri3", 82,31,32,32);
        Prototype spaventaPasseri4 = prototypeManager.createItem("spaventaPasseri4",44,41,32,32);
        Prototype spaventaPasseri5 = prototypeManager.createItem("spaventaPasseri5", 64,44,32,32);
        Prototype spaventaPasseri6 = prototypeManager.createItem("spaventaPasseri6", 85,43,32,32);


        //Aggiunta di tutti gli item alla lista
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

