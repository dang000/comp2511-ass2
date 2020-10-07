package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;

/**
 * Dummy entity to represent open door
 */
public class OpenDoorDummy extends Entity {

    /**
     * Constructor for dummy door
     * @param dungeon Dungeon to add dummy door to
     * @param x x position of dummy door
     * @param y y position of dummy door
     * @param id ID corresponding to real door related to dummy door
     */
    public OpenDoorDummy(Dungeon dungeon, int x, int y, int id) {
        super(dungeon, x, y);
    }

    @Override
    public boolean getCollide() {
        return false;
    }
}
