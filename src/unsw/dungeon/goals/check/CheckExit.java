package unsw.dungeon.goals.check;

import unsw.dungeon.entities.Entity;
import unsw.dungeon.entities.Exit;

/**
 * 
 * Observer pattern that checks if player can exit
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 *
 */

public class CheckExit implements Observer {

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
        if (entity == null || entity.getDungeon().getPlayer() == null) {
            this.isComplete = false;
            return;
        }

        if(entity instanceof Exit && !isAdd) {
            this.isComplete = true;
            System.out.println("On Exit");
        } else {
            this.isComplete = false;
        }
    }
}
