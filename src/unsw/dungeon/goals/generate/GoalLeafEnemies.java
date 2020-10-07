package unsw.dungeon.goals.generate;

import unsw.dungeon.Dungeon;
import unsw.dungeon.goals.check.CheckEnemiesDead;

/**
 * 
 * Goal Leaf that holds the enemy goal
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 */

public class GoalLeafEnemies implements Goal {

    private CheckEnemiesDead checkEnemiesDead;

    /**
     * Class constructor for GoalLeafEnemies
     * @param dungeon, used to add an observer to this goal leaf
     */
    public GoalLeafEnemies(Dungeon dungeon) {
        this.checkEnemiesDead = new CheckEnemiesDead();
        dungeon.addObserver(this.checkEnemiesDead);
    }

    @Override
    public boolean getCompleted(Dungeon dungeon) {
        return this.checkEnemiesDead.getGoalStatus();
    }

    @Override
    public String toString() {
        if(this.getCompleted(null)) {
            return "\t[X] Kill all enemies";
        } else {
            return "\t[ ] Kill all enemies";
        }
    }
}
