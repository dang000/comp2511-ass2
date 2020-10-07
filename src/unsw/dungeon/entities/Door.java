package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;

/**
 * Door entity that can be interacted using key
 */
public class Door extends Entity {

    private int id;
    private OpenDoorDummy openDoorDummy;
    private Player player;

    /**
     * Constructor for door
     * @param dungeon Dungeon to add door to
     * @param x x position of door
     * @param y y position of door
     * @param id Relaitive id of door corresponding to key
     * @param openDoorDummy Fake door item to replace door on open
     */
    public Door(Dungeon dungeon, int x, int y, int id, OpenDoorDummy openDoorDummy) {
        super(dungeon, x, y);
        this.id = id;
        this.openDoorDummy = openDoorDummy;
        this.player = dungeon.getPlayer();
    }

    /**
     * Get ID of door
     * @return int
     */
    public int getID() {
    	return this.id;
    }

    /**
     * Check if door is same as player key on collision
     * @param collideEntity Entity that collides with door
     * @return boolean
     */
    public boolean isSameKey(Entity collideEntity) {
        if(collideEntity.isPlayer() && player.hasKey() && player.getKey().getID() == this.getID()) {
            setOpenDoor();
            player.setKey(null);
            return false;
        }

        return true;
    }

    /**
     * Door destroys self and creates dummy door inplace
     */
    public void setOpenDoor() {
        destroy();
    }

    @Override
    public boolean collisionAction(int x, int y, Entity collideEntity) {
        return isSameKey(collideEntity);
    }
}
