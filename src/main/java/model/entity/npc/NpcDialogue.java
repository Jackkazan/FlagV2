package model.entity.npc;

import model.gameState.GameStateManager;
import model.entity.Interactable;
import model.entity.items.Item;

public class NpcDialogue implements Interactable {
    GameStateManager gsm;
    public NpcDialogue(GameStateManager gsm){
        this.gsm = gsm;
    }

    @Override
    public void performAction(Item item) {

    }

    @Override
    public void performAction(Npc npc) {
        //cambia direzione dell'entità
        npc.setDirection(calculateDirection(npc));
        gsm.setDialogueState(npc);

    }

    private String calculateDirection(Npc npc) {
        double npcCenterX = npc.getX() + (npc.getImageWidth() * npc.getScale()) / 2.0;
        double npcCenterY = npc.getY() + (npc.getImageHeight() * npc.getScale()) / 2.0;

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
