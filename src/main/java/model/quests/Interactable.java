package model.quests;

import model.entity.Entity;
import model.items.KeyItems;

public interface Interactable {
    void performAction(KeyItems keyItems);
    void performAction(Entity entity);
}
