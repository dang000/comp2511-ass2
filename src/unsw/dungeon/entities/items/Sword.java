package unsw.dungeon.entities.items;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Entity;

/**
 * Sword collectible item for attacking enemies
 */
public class Sword extends Entity implements ItemCollectible {

    private int attackCount;
    private int maxAttackCount = 5;

    /**
     * Constructor for sword
     * @param dungeon Dungeon to add sword to
     * @param x x position of sword
     * @param y y position of sword
     */
    public Sword(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.attackCount = maxAttackCount;
    }

    /**
     * Iterate attacks left
     */
    public void attack() {
        this.attackCount--;
    }

    /**
     * Get attack count remaining
     * @return int
     */
    public int attackCount() {
        return this.attackCount;
    }

    /**
     * Get max attack count of sword
     * @return int
     */
    public int getMaxAttackCount() {
        return this.maxAttackCount;
    }

    @Override
    public boolean collisionAction(int x, int y, Entity collideEntity) {
        if(!collideEntity.isPlayer()) return false;
        itemPickup();
        return false;
    }

    @Override
    public void itemPickup() {
        if(this.getDungeon().getPlayer().pickUpSword(this))
            destroy();
    }
}
