package unsw.dungeon.entities.items;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Entity;

/**
 * Potion item collectible for invincibility
 */
public class Potion extends Entity implements ItemCollectible {

    private int potionTimeSecs;

    /**
     * Constructor for potion
     * @param dungeon Dungeon to add potion to
     * @param x x position of potion
     * @param y y position of potion
     * @param potionTimeSecs Potion duration (s)
     */
    public Potion(Dungeon dungeon, int x, int y, int potionTimeSecs) {
        super(dungeon, x, y);
        this.potionTimeSecs = potionTimeSecs;
    }

    /**
     * Get potion duration
     * @return int
     */
    public int getPotionTimeSecs() {
    	return this.potionTimeSecs;
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
        this.getDungeon().getPlayer().activateInvincible(potionTimeSecs);
    }
}
