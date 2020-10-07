package unsw.dungeon.goals.check;

import unsw.dungeon.entities.Entity;
import unsw.dungeon.entities.items.Treasure;

/**
 * 
 * Observer pattern that checks number of treasures collected
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 *
 */

public class CheckTreasure implements Observer {

    private int treasureCount;
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
        if(entity instanceof Treasure) {
            if (isAdd) {
                treasureCount++;
            } else {
                treasureCount--;
            }

            if(treasureCount == 0) {
                this.isComplete = true;
                System.out.println("All treasures pickedup");
            }
        }
    }
}
