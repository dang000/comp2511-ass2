package unsw.dungeon.goals.generate;

import unsw.dungeon.Dungeon;

/**
 * 
 * Composite pattern interface for goals
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 *
 */
public interface Goal {

	/**
	 * Checks if all goals are completed
	 * @param dungeon
	 * @return boolean, true if completed, otherwise false
	 */
    boolean getCompleted(Dungeon dungeon);
}
