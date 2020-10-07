package unsw.dungeon.goals.generate;

import unsw.dungeon.Dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Composite goal for AND condition
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 *
 */

public class GoalCompositeAnd implements Goal {

    private List<Goal> subgoals;

    /**
     * Class constructor for GoalCompositeAnd
     * Creates a subgoals ArrayList
     */
    public GoalCompositeAnd() {
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
        for(int i = 0; i < this.getSubgoals().size(); i++) {
            if(!this.subgoals.get(i).getCompleted(dungeon))
                return false;
        }

        return true;
    }

    @Override
    public String toString() {
        String childGoals = new String("");

        for(int i = 0; i < this.getSubgoals().size(); i++) {
            childGoals += this.getSubgoals().get(i).toString() + "\t";
            if(i != this.getSubgoals().size() - 1)
                childGoals += " AND\n";
        }

        return childGoals + "\n";
    }
}
