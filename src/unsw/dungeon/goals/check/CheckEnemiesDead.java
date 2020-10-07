package unsw.dungeon.goals.check;

import unsw.dungeon.entities.Enemy;
import unsw.dungeon.entities.Entity;

/**
 * 
 * Observer pattern that checks number of enemies dead
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 *
 */

public class CheckEnemiesDead implements Observer {

    private int enemiesDeadCount;
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
        if(entity instanceof Enemy) {
            if (isAdd) {
                enemiesDeadCount++;
            } else {
                enemiesDeadCount--;
            }

            if(enemiesDeadCount == 0) {
                this.isComplete = true;
                System.out.println("All enemies dead");
            }
        }
    }
}
