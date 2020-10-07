package unsw.dungeon.goals.generate;

import unsw.dungeon.Dungeon;
import unsw.dungeon.goals.check.CheckExit;

/**
 * 
 * Goal Leaf that holds the exit goal
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 */

public class GoalLeafExit implements Goal {

    CheckExit checkExit;

    /**
     * Class constructor for GoalLeafExit
     * @param dungeon, used to add an observer to this goal leaf
     */
    public GoalLeafExit(Dungeon dungeon) {
        this.checkExit = new CheckExit();
        dungeon.addObserver(this.checkExit);
    }

    @Override
    public boolean getCompleted(Dungeon dungeon) {
        return this.checkExit.getGoalStatus();
    }

    @Override
    public String toString() {
        if(this.getCompleted(null)) {
            return "\t[X] Complete level and reach exit";
        } else {
            return "\t[ ] Complete level and reach exit";
        }
    }
}
