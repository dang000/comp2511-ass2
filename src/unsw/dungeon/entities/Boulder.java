package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.floorswitch.FloorSwitch;
import unsw.dungeon.entities.items.ItemCollectible;

/**
 * Boulder item which interacts with floor switches
 */
public class Boulder extends Entity {

    private FloorSwitch currFloorSwitch;

    /**
     * Constructor for boulder
     * @param dungeon Dungeon to add boulder to
     * @param x x position for boulder
     * @param y y position for boulder
     */
    public Boulder(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);

        //Check if on switch on spawn
        doFloorSwitch();
    }

    @Override
    public boolean getCollide() {
        return true;
    }

    @Override
    public boolean collisionAction(int x, int y, Entity entity) {
        if(entity.isPlayer())
            return attemptPushBoulder(x, y);
        return true;
    }

    /**
     * Check to push boulder into new position
     * @param x Future x position of boulder
     * @param y Future y position of boulder
     * @return boolean
     */
    public boolean attemptPushBoulder(int x, int y) {
        if(!isCollisionBoulder(x, y)) {
            this.x().set(this.getX() + x);
            this.y().set(this.getY() + y);

            doFloorSwitch();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Trigger floor switch on boulder landing
     */
    public void doFloorSwitch() {
        if(this.currFloorSwitch != null) {
            //Toggle state for current switch
            this.currFloorSwitch.getState().toggleSwitch(this.currFloorSwitch);
            this.currFloorSwitch = null;
        }

        for(Entity entity: this.getDungeon().getEntitiesAtPos(this.getX(), this.getY())) {
            if (entity instanceof FloorSwitch) {
                //Toggle state for new switch to go to
                this.currFloorSwitch = (FloorSwitch) entity;
                this.currFloorSwitch.getState().toggleSwitch(this.currFloorSwitch);
                return;
            }
        }
    }

    /**
     * Check if boulder is colliding into items
     * @param addX x translation of boulder
     * @param addY y translation of boulder
     * @return boolean
     */
    public boolean isCollisionBoulder(int addX, int addY) {
        int x = getX() + addX;
        int y = getY() + addY;

        for(Entity entity : this.getDungeon().getEntitiesAtPos(x, y)) {
            if(entity.getCollide()) {
                if ((entity instanceof ItemCollectible))
                    return true;
                else
                    return entity.collisionAction(x, y, this);
            }
        }

        return false;
    }
}
