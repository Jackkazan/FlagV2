package model.entity;

import model.gameState.GameStateManager;

public class NpcDialogue implements InteractionActionEntity {
    GameStateManager gsm;
    public NpcDialogue(GameStateManager gsm){
        this.gsm = gsm;
    }
    @Override
    public void performAction(Entity entity) {
        gsm.setDialogueState(entity);

    }
}
