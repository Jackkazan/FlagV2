package model.entities.items;

import model.entities.Prototype;

// Classe che utilizza il pattern Prototype
class ItemPrototype {
    private final Prototype prototype;

    public ItemPrototype(Prototype prototype) {
        this.prototype = prototype;
    }

    // Metodo per ottenere un nuovo oggetto prototipato
    public Prototype createItems(String name, int x, int y, int collisionWidth, int collisionHeight) {
        // Clonare l'oggetto prototipo
        Item newItem = (Item) prototype.clone();

        // Modificare alcuni valori del nuovo oggetto, se necessario
        newItem.setName(name);
        newItem.setPosition(x, y);
        newItem.setCollisionArea(x,y,collisionWidth,collisionHeight);

        // Restituire il nuovo oggetto
        return newItem;
    }
}
