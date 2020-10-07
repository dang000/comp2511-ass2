package unsw.dungeon.goals.check;

import unsw.dungeon.entities.Entity;

/**
 * 
 * Observer pattern interface
 * 
 * @author Anthony Dang
 * @author Mozamel Anwary
 *
 */

public interface Observer {

	/**
	 * Update the observer
	 * @param entity
	 * @param isAdd
	 */
    void update(Entity entity, boolean isAdd);
}
