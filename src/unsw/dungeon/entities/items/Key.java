package unsw.dungeon.entities.items;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Entity;
import unsw.dungeon.entities.Player;

/**
 * Key collectible item which interacts with doors
 */
public class Key extends Entity implements ItemCollectible {

    private int id;
    private Player player;

    /**
     * Class constructor for key
     * @param dungeon Dungeon to add key to
     * @param x x co-ordinate of key
     * @param y y co-ordinate of key
     * @param id Key id relative to door
     */
    public Key(Dungeon dungeon, int x, int y, int id) {
        super(dungeon, x, y);
        this.id = id;
        this.player = dungeon.getPlayer();
    }

    /**
     * Get ID of key
     * @return int
     */
    public int getID() {
        return this.id;
    }

    @Override
    public boolean collisionAction(int x, int y, Entity collideEntity) {
        if(!collideEntity.isPlayer()) return false;
        itemPickup();
        return false;
    }

    @Override
    public void itemPickup() {
        if(this.player.hasKey()) return;

        this.player.setKey(this);
        destroy();
    }
}
