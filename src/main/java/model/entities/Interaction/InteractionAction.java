package model.entities.Interaction;

import model.entities.Entity;
import model.gameState.GameStateManager;
import model.quests.Objective;
import model.quests.QuestManager;
public class InteractionAction {
    public static boolean questAction(Entity entity) {
        QuestManager questManager = QuestManager.getInstance();
        Objective objective = questManager.getObjectiveMap().get(entity);
        if (objective == null) {
            objective = questManager.getTrickObjectiveMap().get(entity);
        }
        return objective == null || (!objective.isCompleted() && questManager.handleObjective(entity, objective));
    }
     /*@Override
        public void performAction(Item item) {
           /* item.changeImage();
            item.setInteractable(false);
            item.setCollisionArea(null);*/


    //Se le quest prima di interagire con questo oggetto sono state fatte
           /* if(item.questListIsDone()){

                // Implementa l'azione di nascondere
                System.out.println("Sto nascondendo " + item.getName());

                switch (item.getName()) {

                    //Se hai interagito con chiave e tutte le quest fin qui sono completate allora prendi la chiave
                    case "Chiave" -> {
                        item.changeImage();
                        //questList.get(1).setDone();
                        item.setInteractable(false);
                    }
                    //Se hai interagito con la collisione della porta e tutte le quest fin qui sono completate allora sblocca la porta
                    case "Porta" -> {
                        item.changeImage();
                        item.setCollisionArea(null);        //rimuove collisione della porta
                        //questList.get(2).setDone();
                        item.setInteractable(false);
                        System.out.println("pipu");
                    }
                    case "zuccaMarcia1", "zuccaMarcia2", "zuccaMarcia3", "zuccaMarcia4", "zuccaMarcia5" -> {
                        item.changeImage();
                        item.setCollisionArea(null);        //rimuove collisione
                        //questList.get(2).setDone();     // la quest al momento è sbagliata
                        item.setInteractable(false);
                    }

                    case "spaventaPasseri1", "spaventaPasseri2", "spaventaPasseri3", "spaventaPasseri4", "spaventaPasseri5", "spaventaPasseri6" -> {
                        item.changeImage();
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
            }*/

    /* @Override
     public void performAction(Npc npc) {
     }*/

    public static class DisappearAction implements Interactable {
        @Override
        public void performAction(Entity entity) {
            if (questAction(entity)) {
                entity.changeImage();
                entity.setInteractable(false);
                entity.setCollisionArea(null);
            }

        }
    }
    public static class ChangeImageAction implements Interactable {
        @Override
        public void performAction(Entity entity) {
            if (questAction(entity)) {
                entity.changeImage();
                entity.setInteractable(false);
            }

        }
    }
}
