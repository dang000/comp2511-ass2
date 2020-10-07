package unsw.dungeon.goals.generate;

import unsw.dungeon.Dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Composite goal for OR condition
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 *
 */

public class GoalCompositeOr implements Goal {

    private List<Goal> subgoals;

    /**
     * Class constructor for GoalCompositeOr
     * Creates a subgoals ArrayList
     */
    public GoalCompositeOr() {
        this.subgoals = new ArrayList<>();
    }

    /**
     * Get list of subgoals
     * @return
     */
    public List<Goal> getSubgoals() {
        return this.subgoals;
    }

    /**
     * Add a goal to the subgoals List
     * @param subgoal
     */
    public void addSubgoal(Goal subgoal) {
        this.subgoals.add(subgoal);
    }

    /**
     * Remove a goal from the subgoals List
     * @param subgoal
     */
    public void removeSubgoal(Goal subgoal) {
        this.subgoals.remove(subgoal);
    }

    @Override
    public boolean getCompleted(Dungeon dungeon) {
       for(Goal subgoal: this.getSubgoals()) {
           if(subgoal.getCompleted(dungeon))
               return true;
       }

       return false;
    }

    @Override
    public String toString() {
        String childGoals = new String("");

        for(int i = 0; i < this.getSubgoals().size(); i++) {
            childGoals += this.getSubgoals().get(i).toString() + "\t";
            if(i != this.getSubgoals().size() - 1)
                childGoals += " OR\n";
        }

        return childGoals + "\n";
    }
}
