package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;

/**
 * Exit entity for finishing level
 */
public class Exit extends Entity {

    /**
     * Constructor for exit
     * @param dungeon Dungeon to add exit to
     * @param x x position of exit
     * @param y y position of exit
     */
    public Exit(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
    }

    @Override
    public void destroy() {
        this.getDungeon().removeEntity(this);
        return;
    }

    @Override
    public boolean collisionAction(int x, int y, Entity collideEntity) {
        if(collideEntity.isPlayer()) {
            collideEntity.getDungeon().addEntity(new Exit(getDungeon(), this.getX(), this.getY()));
            destroy();
            return false;
        } else {
            return true;
        }
    }
}
