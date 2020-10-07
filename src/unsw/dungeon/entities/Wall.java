package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;

/**
 * Static wall entity
 */
public class Wall extends Entity {

    /**
     * Constructor for wall entity
     * @param dungeon Dungeon to add wall into
     * @param x x position of wall
     * @param y y positon of wall
     */
    public Wall(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
    }

    @Override
    public boolean getCollide() {
        return true;
    }
}
