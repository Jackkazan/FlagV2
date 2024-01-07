package model.entities.Interaction;

import model.entities.Entity;
import model.entities.items.Item;
import model.entities.characters.npc.Npc;

public interface Interactable {
    //void performAction(Item item);
    void performAction(Entity entity);
    //void performAction(Npc npc);
}
