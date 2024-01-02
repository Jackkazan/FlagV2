package model.entities.items;

import model.entities.Prototype;

import java.awt.*;

// Classe che utilizza il pattern Prototype
class ItemPrototype {
    private final Prototype prototype;

    public ItemPrototype(Prototype prototype) {
        this.prototype = prototype;
    }

    // Metodo per ottenere un nuovo oggetto prototipato
    public Prototype createKeyItems() {
        return prototype.clone();
    }

    public Prototype createKeyItems(String name, int x, int y, int collisionWidth, int collisionHeight) {
        // Clonare l'oggetto prototipo
        Item newKeyItem = (Item) prototype.clone();

        // Modificare alcuni valori del nuovo oggetto, se necessario
        newKeyItem.setName(name);
        newKeyItem.setPosition(x, y);
        newKeyItem.setCollisionArea(x,y,collisionWidth,collisionHeight);

        // Restituire il nuovo oggetto
        return newKeyItem;
    }
}
