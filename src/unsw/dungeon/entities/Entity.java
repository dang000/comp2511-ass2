package unsw.dungeon.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.dungeon.Dungeon;

/**
 * An entity in the dungeon.
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private Dungeon dungeon;
    private IntegerProperty x, y;
    private boolean canCollide;

    /**
     * Constructor to instantiate new entity into dungeon
     * @param dungeon Dungeon to add entity into
     * @param x x position of entity
     * @param y y position of entity
     */
    public Entity(Dungeon dungeon, int x, int y) {
        this.dungeon = dungeon;
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.canCollide = true;
    }

    /**
     * Check if this entity is player
     * @return boolean
     */
    public boolean isPlayer() {
        return false;
    }

    /**
     * Get x position for mapping to image
     * @return x position
     */
    public IntegerProperty x() {
        return x;
    }

    /**
     * Get y position for mapping to image
     * @return y position
     */
    public IntegerProperty y() {
        return y;
    }

    /**
     * Get y position of entity
     * @return y position
     */
    public int getY() {
        return y().get();
    }

    /**
     * Get x position of entity
     * @return x position
     */
    public int getX() {
        return x().get();
    }

    /**
     * Retrieve dungeon the entity is contained in
     * @return Dungeon
     */
    public Dungeon getDungeon() {
        return this.dungeon;
    }

    /**
     * Check if entity is a collidable with other entities
     * @return
     */
    public boolean getCollide() {
        return this.canCollide;
    }

    /**
     * Destroy this entity
     */
    public void destroy() {
        dungeon.removeEntity(this);
        this.x().set(dungeon.getWidth() + 1);
        this.y().set(dungeon.getHeight() + 1);
    }

    /**
     * Process action of collision against other entities
     * @param x x position of entity collision
     * @param y y position of entity collision
     * @param collideEntity The entity the entity has collided with
     * @return A boolean to check whether the collision requires action on other entity
     */
    //Default collide with no action
    public boolean collisionAction(int x, int y, Entity collideEntity) {
        return this.canCollide;
    }
}
