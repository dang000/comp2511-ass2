package unsw.dungeon.goals.generate;

import unsw.dungeon.Dungeon;
import unsw.dungeon.goals.check.CheckSwitches;

/**
 * 
 * Goal Leaf that holds the switch goal
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 */

public class GoalLeafSwitches implements Goal {

    CheckSwitches checkSwitches;

    /**
     * Class constructor for GoalLeafSwitch
     * @param dungeon, used to add an observer to this goal leaf
     */
    public GoalLeafSwitches(Dungeon dungeon) {
        this.checkSwitches = new CheckSwitches();
        dungeon.addObserver(this.checkSwitches);
    }

    @Override
    public boolean getCompleted(Dungeon dungeon) {
        return this.checkSwitches.getGoalStatus();
    }

    @Override
    public String toString() {
        if(this.getCompleted(null)) {
            return "\t[X] Activate all switches";
        } else {
            return "\t[ ] Activate all switches";
        }
    }
}
