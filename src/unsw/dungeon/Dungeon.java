/**
 *
 */
package unsw.dungeon;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import unsw.dungeon.entities.Entity;
import unsw.dungeon.entities.Player;
import unsw.dungeon.goals.check.Observer;
import unsw.dungeon.goals.generate.Goal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Anthony Dang, Mozamel Anwary
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private List<Observer> observers;
    private Goal parentGoal;
    private int maxTreasure;
    private int treasurePickedUp;
    private int maxSwitches;
    private int switchesActivated;
    private int maxEnemies;
    private int enemiesKilled;

    private boolean isClosing = false;

    public SimpleStringProperty uiTreasureCount;
    public SimpleStringProperty uiSwitchCount;
    public SimpleStringProperty uiEnemyCount;
    public SimpleStringProperty uiGoalsText;
    public SimpleStringProperty uiLevelsText;

    /**
     * Class constructor for Dungeon
     * @param width, set the width of the dungeon
     * @param height,set the height of the dungeon
     */
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.observers = new ArrayList<>();
        this.parentGoal = null;
        this.maxTreasure = 0;
        this.treasurePickedUp = 0;
        this.maxSwitches = 0;
        this.switchesActivated = 0;
        this.maxEnemies = 0;
        this.enemiesKilled = 0;
        
        this.uiTreasureCount = new SimpleStringProperty();
        this.uiSwitchCount = new SimpleStringProperty();
        this.uiEnemyCount = new SimpleStringProperty();
        this.uiGoalsText = new SimpleStringProperty();
        this.uiLevelsText = new SimpleStringProperty();
        uiLevelsText.setValue(DungeonApplication.levelIndex + 1 + "");
    }

    /**
     * Add an observser to observer List
     * @param o
     */
    public void addObserver(Observer o) {
        if(!this.observers.contains(o)) {
            this.observers.add(o);
        }
    }

    /**
     * Remove an observer from observer List
     * @param o
     */
    public void removeObserver(Observer o) {
        if(!this.observers.contains(o)) {
            this.observers.remove(o);
        }
    }

    /**
     * Notify all observers from observer lists and update them
     * @param entity
     * @param isAdd, if add or minus
     */
    public void notifyObservers(Entity entity, boolean isAdd) {
        for(Observer o: this.observers) {
            o.update(entity, isAdd);
        }

        this.checkGoalsActions();
    }

    /**
     * Set the parent Goal
     * @param goal
     */
    public void setParentGoal(Goal goal) {
        this.parentGoal = goal;
    }

    /**
     * Get the parentGoal
     * @return
     */
    public Goal getParentGoal() {
        return this.parentGoal;
    }

    /**
     * Check if goals are completed, if yes load next level
     */
    public void checkGoalsActions() {
        uiGoalsText.setValue("Goals:\n" + parentGoal.toString());

        if(parentGoal != null && parentGoal.getCompleted(this)) {
            this.nextLevel();
            return;
        }
    }

    /**
     * Restart the current level
     */
    public void restartLevel() {
        if(!this.isClosing) {
            this.isClosing = true;
            Platform.runLater(() -> {
                try {
                    DungeonApplication.restartLevel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        return;
    }

    /**
     * Load the next level
     */
    public void nextLevel() {
        if(!this.isClosing) {
            this.isClosing = true;
            Platform.runLater(() -> {
                try {
                    DungeonApplication.loadNextLevel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        return;
    }

    /**
     * Get dungeon width
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get dungeon height
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the player from dungeon
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set the player
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Add an entity to the entities List and notify observers
     * @param entity
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
        notifyObservers(entity, true);
    }

    /**
     * Remove an enetiy from entities List and notify observers
     * @param entity
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
        notifyObservers(entity, false);
    }

    /**
     * Get entities List
     * @return
     */
    public List<Entity> getEntities() {
        return entities;
    }
    
    /**
     * Return a List of all entities at position (x,y)
     * @param x
     * @param y
     * @return
     */
    public List<Entity> getEntitiesAtPos(int x, int y) {
        List<Entity> entitiesAtPos = new ArrayList<>();

        for(Entity entity: this.getEntities()) {
            if(entity.getX() == x && entity.getY() == y)
                entitiesAtPos.add(entity);
        }

        return entitiesAtPos;
    }

    /**
     * Get max number of treasures in the dungeon
     * @return
     */
	public int getMaxTreasure() {
		return maxTreasure;
	}

	/**
	 * Add to maxTreasure
	 */
	public void addMaxTreasure() {
		this.maxTreasure++;
	}

	/**
	 * Get number of treasures picked up
	 * @return
	 */
	public int getTreasurePickedUp() {
		return treasurePickedUp;
	}

	/**
	 * Add to number of treasures picked up and update UIText
	 */
	public void pickedUpTreasure() {
		this.treasurePickedUp++;
		uiTreasureCount.setValue(this.getTreasurePickedUp() + "/" + this.getMaxTreasure());
	}
	
	/**
	 * Get max nuber of switches
	 * @return
	 */
	public int getMaxSwitches() {
		return maxSwitches;
	}

	/**
	 * Add to max number of switches
	 */
	public void addMaxSwitches() {
		this.maxSwitches++;
	}

	/**
	 * Get number of switches activated
	 * @return
	 */
	public int getSwitchesActivated() {
		return switchesActivated;
	}

	/**
	 * Toggle switch count, either increment or decrement depending on
	 * isActivated or not isActivated
	 * Update UIText
	 * @param isActivated
	 */
	public void toggleSwitchCount(boolean isActivated) {
		if (isActivated)
			switchesActivated++;
		else
			switchesActivated--;
		
		uiSwitchCount.setValue(this.getSwitchesActivated() + "/" + this.getMaxSwitches());
	}

	/**
	 * Get the max number of enemies
	 * @return
	 */
	public int getMaxEnemies() {
		return maxEnemies;
	}

	/**
	 * Add to max nuber of enemies
	 */
	public void addMaxEnemies() {
		this.maxEnemies++;
	}

	/**
	 * Get number of enemies killed
	 * @return
	 */
	public int getEnemiesKilled() {
		return enemiesKilled;
	}

	/**
	 * Add to number of enemies killed
	 */
	public void addEnemiesKilled() {
		this.enemiesKilled++;
		uiEnemyCount.setValue(this.getEnemiesKilled() + "/" + this.getMaxEnemies());
	}

	/**
	 * Get uiSwitchCount
	 * @return
	 */
    public SimpleStringProperty uiSwitchCount() {
        return this.uiSwitchCount;
    }

    /**
     * Get uiTreasureCount
     * @return
     */
    public SimpleStringProperty uiTreasureCount() {
        return this.uiTreasureCount;
    }

    /**
     * Get uiEnemyCount
     * @return
     */
    public SimpleStringProperty uiEnemyCount() {
		return this.uiEnemyCount;
	}

    /**
     * Get uiGoalsText
     * @return
     */
    public SimpleStringProperty uiGoalsText() {
        return this.uiGoalsText;
    }
}
