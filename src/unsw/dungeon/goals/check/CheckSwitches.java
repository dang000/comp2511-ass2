package unsw.dungeon.goals.check;

import unsw.dungeon.entities.Entity;
import unsw.dungeon.entities.floorswitch.FloorSwitch;

/**
 * 
 * Observer pattern that checks number of switches activated
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 *
 */

public class CheckSwitches implements Observer {

    private int floorSwitchesOff = 0;
    private boolean isComplete = false;

    /**
     * Get completion status of goal
     * @return
     */
    public boolean getGoalStatus() {
        return this.isComplete;
    }

    @Override
    public void update(Entity entity, boolean isAdd) {
        if(entity instanceof FloorSwitch) {
            if (isAdd) {
                floorSwitchesOff++;
            } else {
                floorSwitchesOff--;
            }

            if(floorSwitchesOff == 0) {
                this.isComplete = true;
                System.out.println("All floor switches ON");
            } else {
                this.isComplete = false;
            }
        }
    }
}
