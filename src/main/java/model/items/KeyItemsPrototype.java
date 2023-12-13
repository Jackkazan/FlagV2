package model.items;

import java.awt.*;

// Classe che utilizza il pattern Prototype
class KeyItemsPrototype {
    private Prototype prototype;

    public KeyItemsPrototype(Prototype prototype) {
        this.prototype = prototype;
    }

    // Metodo per ottenere un nuovo oggetto prototipato
    public Prototype createKeyItems() {
        return prototype.clone();
    }

    public Prototype createKeyItems(String name, int x, int y, int imageWidth, int imageHeight) {
        // Clonare l'oggetto prototipo
        KeyItems newKeyItem = (KeyItems) prototype.clone();

        // Modificare alcuni valori del nuovo oggetto, se necessario
        newKeyItem.setName(name);
        newKeyItem.setPosition(x, y);
        newKeyItem.setCollisionArea(new Rectangle(x,y,imageWidth,imageHeight));

        // Restituire il nuovo oggetto
        return newKeyItem;
    }
}
