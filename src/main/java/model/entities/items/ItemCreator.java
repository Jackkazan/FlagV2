package model.entities.items;
import controller.KeyHandler;
import model.entities.Interaction.InteractionAction;
import model.entities.Prototype;
import model.gameState.GameStateManager;
import model.tile.MapManager;
import java.util.ArrayList;
import java.util.List;

import static view.GamePanel.tileSize;

public class ItemCreator {

    static ItemPrototype prototypeManager;
    public static List<Item> objectList = new ArrayList<>();

    public static List<Item> createObjects(GameStateManager gsm, MapManager mapManager, KeyHandler keyH) {


        //Inizializzazione oggetti
        Item keyCasettaIniziale = new Item.ItemBuilder(7, 4)
                .setName("Chiave")
                .setStaticImage("/object/key.png")
                .setInteractImage("/object/spriteInvisibile16x16.png")
                .setImageDimension(16, 16)
                .setCollisionArea(16, 16)
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                // .setRelatedQuests(questList.get(0))
                .setInteractable(true)
                .setInteractionAction(new InteractionAction.DisappearAction())
                .build();


        Item portaCasettaInizialeChiusa = new Item.ItemBuilder(4, 8)
                .setName("Porta")
                .setStaticImage("/object/PortaChiusaInterno.png")
                .setInteractImage("/object/portaAperta.png")
                .setContainedMap(mapManager.getTileManagerCasettaIniziale())
                .setOffsetY(-tileSize)
                .setInteractable(true)
                .setImageDimension(32, 48)
                .setCollisionArea(4 * tileSize, 9 * tileSize, 48, 16)
                // .setRelatedQuests(questList.get(1))
                .setInteractionAction(new InteractionAction.DisappearAction())
                .build();
        //System.out.println(QuestManager.getQuestMap().get(portaCasettaInizialeChiusa).getQuestName());
        Item zuccaMarcia1 = new Item.ItemBuilder(39, 44)
                .setName("ZuccaMarcia1")
                .setStaticImage("/object/zuccaMarcia.png")
                .setInteractImage("/object/spriteInvisibile16x16.png")
                .setImageDimension(16, 16)
                .setCollisionArea(16, 16)
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                //.setRelatedQuests(questList.get(0))
                .setInteractable(false)
                .setInteractionAction(new InteractionAction.DisappearAction())
                .build();
        prototypeManager = new ItemPrototype(zuccaMarcia1);
        Prototype zuccaMarcia2 = prototypeManager.createItem("ZuccaMarcia2", 47, 46, 16, 16);
        Prototype zuccaMarcia3 = prototypeManager.createItem("ZuccaMarcia3", 42, 48, 16, 16);
        Prototype zuccaMarcia4 = prototypeManager.createItem("ZuccaMarcia4", 45, 52, 16, 16);
        Prototype zuccaMarcia5 = prototypeManager.createItem("ZuccaMarcia5", 50, 50, 16, 16);

        Item spaventaPasseri1 = new Item.ItemBuilder(43, 28)
                .setName("spaventaPasseri1")
                .setStaticImage("/object/spaventaPasseri.png")
                .setInteractImage("/object/spaventaPasseriDritto.png")
                .setImageDimension(32, 48)
                .setCollisionArea(32, 32)
                .setOffsetY(-48)
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                // .setRelatedQuests(questList.get(0))
                .setInteractable(false)
                .setInteractionAction(new InteractionAction.ChangeImageAction())
                .build();

        prototypeManager = new ItemPrototype(spaventaPasseri1);
        Prototype spaventaPasseri2 = prototypeManager.createItem("spaventaPasseri2", 64, 28, 32, 32);
        Prototype spaventaPasseri3 = prototypeManager.createItem("spaventaPasseri3", 82, 31, 32, 32);
        Prototype spaventaPasseri4 = prototypeManager.createItem("spaventaPasseri4", 44, 41, 32, 32);
        Prototype spaventaPasseri5 = prototypeManager.createItem("spaventaPasseri5", 64, 44, 32, 32);
        Prototype spaventaPasseri6 = prototypeManager.createItem("spaventaPasseri6", 85, 43, 32, 32);

        Item wall1 = new Item.ItemBuilder(60, 64)
                .setName("Wall1")
                .setCollisionArea(256,64)
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                        .build();
        Item wall2 = new Item.ItemBuilder(48, 86)
                .setName("Wall2")
                .setCollisionArea(92, 16)
                .setContainedMap(mapManager.getTileManagerVillaggioSud())
                .build();

        Item wall3 = new Item.ItemBuilder(41, 79)
                .setName("Wall3")
                .setCollisionArea(tileSize*3, tileSize*5)
                .setContainedMap(mapManager.getTileManagerDungeonSud())
                .build();

        Item chest1 = new Item.ItemBuilder(87,80)
                .setName("Chest1")
                .setStaticImage("/object/goldenChest.png")
                .setInteractImage("/object/spriteInvisibile16x16.png")
                .setImageDimension(32, 64)
                .setCollisionArea(48, 80)
                .setContainedMap(mapManager.getTileManagerDungeonSud())
                // .setRelatedQuests(questList.get(0))
                .setInteractable(true)
                .setInteractionAction(new InteractionAction.ChangeImageAction())
                .build();

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
        objectList.add(wall1);
        objectList.add(wall2);
        objectList.add(wall3);
        objectList.add(chest1);
        return objectList;
    }


    //Raccolta oggetti o semplice interazione con scomparsa

}
   /* public static class AdvanceQuest implements Interactable {
        @Override
        public void performAction(Item item){

            //}
        }
        public void performAction(Npc npc){}

        @Override
        public void performAction(Entity entity) {
            Objective objective = QuestManager.getObjectiveMap().get(entity);
            if(!objective.isCompleted()){
            objective.complete();
            entity.setInteractionAction(new DisappearOrChangeImageAction());
            entity.loadProgress();

        }
    }
    }
    public static class CompleteQuest implements Interactable {
        @Override
        public void performAction(Item item){

        }
        public void performAction(Npc npc){}

        @Override
        public void performAction(Entity entity) {
            Quest quest = QuestManager.getQuestMap().get(entity);
            if(!quest.isCompleted()){
                if(quest.getObjectives().stream().allMatch(Objective::isCompleted)){
                    quest.complete();
                    entity.setInteractionAction(new DisappearOrChangeImageAction());
                    entity.interact();
                }
                else{
                    System.out.println("OK");;
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



