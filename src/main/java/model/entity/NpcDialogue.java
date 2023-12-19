package model.entity;

import model.gameState.GameStateManager;
import model.quests.Interactable;
import model.items.KeyItems;

public class NpcDialogue implements Interactable {
    GameStateManager gsm;
    public NpcDialogue(GameStateManager gsm){
        this.gsm = gsm;
    }

    @Override
    public void performAction(KeyItems keyItems) {

    }

    @Override
    public void performAction(Entity entity) {
        //cambia direzione dell'entità
        entity.setDirection(calculateDirection(entity));
        gsm.setDialogueState(entity);

    }

    private String calculateDirection(Entity entity) {
        double npcCenterX = entity.getX() + (entity.getImageWidth() * entity.getScale()) / 2.0;
        double npcCenterY = entity.getY() + (entity.getImageHeight() * entity.getScale()) / 2.0;

        double playerCenterX = gsm.getPlayer().getX() + (gsm.getPlayer().getImageWidth() * gsm.getPlayer().getScale()) / 2.0;
        double playerCenterY = gsm.getPlayer().getY() + (gsm.getPlayer().getImageHeight() * gsm.getPlayer().getScale()) / 2.0;

        double deltaX = playerCenterX - npcCenterX;
        double deltaY = playerCenterY - npcCenterY;

        String calculatedDirection;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            calculatedDirection = (deltaX > 0) ? "right" : "left";
        } else {
            calculatedDirection = (deltaY > 0) ? "down" : "up";
        }
        return calculatedDirection;
    }

}
