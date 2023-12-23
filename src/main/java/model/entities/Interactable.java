package model.entities;

import model.entities.items.Item;
import model.entities.npc.Npc;

public interface Interactable {
    void performAction(Item item);
    void performAction(Npc npc);
}
