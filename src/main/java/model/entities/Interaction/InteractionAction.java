package model.entities.Interaction;

import model.entities.Entity;
import model.quests.QuestManager;
public class InteractionAction {
    public static boolean questAction(Entity entity) {
        return QuestManager.getInstance().questAction(entity);
    }
    public static class DisappearAction implements Interactable {
        @Override
        public void performAction(Entity entity) {
            // Prima di esegure l'azione controlla se l'entità è legata a una quest
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
