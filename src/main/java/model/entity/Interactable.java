package model.entity;

import model.entity.npc.Npc;
import model.entity.items.Item;

public interface Interactable {
    void performAction(Item item);
    void performAction(Npc npc);
}
