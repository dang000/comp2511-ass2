package unsw.dungeon.goals.generate;

import unsw.dungeon.Dungeon;
import unsw.dungeon.goals.check.CheckTreasure;

/**
 * 
 * Goal Leaf that holds the treasure goal
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 */

public class GoalLeafTreasure implements Goal {

    CheckTreasure checkTreasure;

    /**
     * Class constructor for GoalLeafTreasure
     * @param dungeon, used to add an observer to this goal leaf
     */
    public GoalLeafTreasure(Dungeon dungeon) {
        this.checkTreasure = new CheckTreasure();
        dungeon.addObserver(this.checkTreasure);
    }

    @Override
    public boolean getCompleted(Dungeon dungeon) {
        return this.checkTreasure.getGoalStatus();
    }

    @Override
    public String toString() {
        if(this.getCompleted(null)) {
            return "\t[X] Collect all treasure";
        } else {
            return "\t[ ] Collect all treasure";
        }
    }
}
