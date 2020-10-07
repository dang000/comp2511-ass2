package unsw.dungeon.entities.items;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Entity;

/**
 * Treasure item collectible to reach goals
 */
public class Treasure extends Entity implements ItemCollectible {

    /**
     * Constructor for treasure
     * @param dungeon Dungeon to add treasure to
     * @param x x position of treasure
     * @param y y position of treasure
     */
    public Treasure(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
    }

    @Override
    public boolean collisionAction(int x, int y, Entity collideEntity) {
        if(!collideEntity.isPlayer()) return false;
        itemPickup();
        return false;
    }

    @Override
    public void itemPickup() {
        destroy();
        this.getDungeon().pickedUpTreasure();
    }
}
