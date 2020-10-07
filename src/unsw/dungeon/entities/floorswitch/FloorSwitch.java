package unsw.dungeon.entities.floorswitch;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Entity;

/**
 * Floor switch entity to interact with boulders
 */
public class FloorSwitch extends Entity {

    private SwitchState state;

    /**
     * Instantiate floor switch in dungeon at position x, y
     * @param dungeon The main dungeon
     * @param x Position x of switch
     * @param y Position y of switch
     */
    public FloorSwitch(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.state = new SwitchStateOff();
    }

    /**
     * Set state of switch
     * @param switchState New switch state
     */
    public void setState(SwitchState switchState) {
        this.state = switchState;
        getDungeon().notifyObservers(this, !this.getState().getIsSwitched());
    }

    /**
     * Get state of switch
     * @return boolean
     */
    public SwitchState getState() {
    	return this.state;
    }

    /**
     * Return collision property
     * @return boolean
     */
    @Override
    public boolean getCollide() {
        return false;
    }
}
