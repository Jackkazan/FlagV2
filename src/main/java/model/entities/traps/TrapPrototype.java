package model.entities.traps;

import model.entities.Prototype;
import model.entities.items.Item;

import java.awt.*;

public class TrapPrototype {

    private final Prototype prototype;

    public TrapPrototype(Prototype prototype) {
        this.prototype = prototype;
    }

    public Prototype createTrap(String name, int x, int y) {
        // Clonare l'oggetto prototipo
        Trap newTrap = (Trap) prototype.clone();

        // Modificare alcuni valori del nuovo oggetto, se necessario
        newTrap.setName(name);
        newTrap.setPosition(x, y);
        newTrap.setAttackArea(x,y,16,16);

        // Restituire il nuovo oggetto
        return newTrap;
    }
}
