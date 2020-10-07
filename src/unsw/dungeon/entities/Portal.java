package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;

/**
 * Portal entity for teleporting entities to other positions
 */
public class Portal extends Entity {

    private int id;

    /**
     * Constructor for portal
     * @param dungeon Dungeon to add portal into
     * @param x x position of portal
     * @param y y position of portal
     * @param id ID of portal corresponding to other portal
     */
    public Portal(Dungeon dungeon, int x, int y, int id) {
        super(dungeon, x, y);
        this.id = id;
    }

    /**
     * Get ID of this portal
     * @return int
     */
    public int getID() {
        return this.id;
    }

    /**
     * Find the relative portal that this portal is connected to
     * @return Relative portal entity
     */
    public Portal getRelativePortal() {
        for(Entity entity: this.getDungeon().getEntities()) {
            if(entity instanceof Portal
                    && this.id == ((Portal) entity).getID()
                    && !this.equals(entity)) {
                return (Portal) entity;
            }
        }

    	return null;
    }

    @Override
    public boolean collisionAction(int x, int y, Entity collideEntity) {
        Portal relativePortal = getRelativePortal();

        for(Entity entity: this.getDungeon().getEntitiesAtPos(relativePortal.getX() + x, relativePortal.getY() + y)) {
            if(entity.getCollide()) return true;
        }

        if(relativePortal != null) {
            collideEntity.x().set(relativePortal.getX());
            collideEntity.y().set(relativePortal.getY());
        }

        return false;
    }
}
