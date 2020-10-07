package unsw.dungeon.entities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.items.Key;
import unsw.dungeon.entities.items.Sword;

import java.util.Timer;

/**
 * Player entity which interacts with other entities and
 * is controlled by the user
 */
public class Player extends Entity {

    public SimpleStringProperty uiAttackCount;
    public SimpleStringProperty uiPotionActive;
    public Sword sword;
    private Key key;
    private boolean isInvincible;
    private Timer timer;
    private Timeline potionTimeline;

    /**
     * Constructor to instantiate player into level
     * @param dungeon Dungeon to add player into
     * @param x x position of player spawn
     * @param y y position of player spawn
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.sword = null;
        this.key = null;
        this.isInvincible = false;
        this.timer = new Timer();

        uiAttackCount = new SimpleStringProperty();
        uiPotionActive = new SimpleStringProperty();
    }

    /**
     * Get attack count for UI attacks remaining
     * @return sword attack count
     */
    public SimpleStringProperty uiSwordAttackCount() {
        return uiAttackCount;
    }

    /**
     * Get postion timer status UI
     * @return potion timer status
     */
    public SimpleStringProperty uiPotionActive() {
        return uiPotionActive;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public boolean getCollide() {
        return false;
    }

    @Override
    public void destroy() {
        super.destroy();
        this.getDungeon().setPlayer(null);
        this.getDungeon().restartLevel();
    }

    /**
     * Get current key player is holding
     * @return key entity
     */
    public Key getKey() {
    	return this.key;
    }

    /**
     * Set key player is holding
     * @param k New picked up key
     */
    public void setKey(Key k) {
    	this.key = k;
    }

    /**
     * Check if player currently has a key
     * @return boolean
     */
    public boolean hasKey() {
    	return this.key != null;
    }

    /**
     * Process attack on player side and updates UI
     * @return whether attack can occur or has failed
     */
    //Returns true if attack successful
    public boolean attack() {
        uiAttackCount.setValue("-/-");
    	if (!this.hasSword()) {
    		return false;
    	}
    	
    	this.sword.attack();
    	
    	if (this.sword.attackCount() <= 0) {
    		this.removeSword();
    		return true;
    	}

        uiAttackCount.setValue(this.sword.attackCount() + "/" + this.sword.getMaxAttackCount());
        return true;
    }

    /**
     * Attenmpt to pick up sword from ground
     * New sword will replace a used sword
     * @param s Sword to pick up
     * @return Whether the sword was successfully added to inventory
     */
    //Returns true if sword can be picked up
    public boolean pickUpSword(Sword s) {
        if(this.sword == null || this.sword.attackCount() < this.sword.getMaxAttackCount()) {
            this.sword = s;
            uiAttackCount.setValue(this.sword.getMaxAttackCount() + "/" + this.sword.getMaxAttackCount());
            return true;
        }

        return false;
    }

    /**
     * Delete current sword from player
     */
    public void removeSword() {
    	this.sword = null;
    }

    /**
     * Activate invincibility to kill enemies for a given time
     * Also update UI
     * @param time Time of invincibility (in s)
     */
    public void activateInvincible(int time) {
        this.isInvincible = true;
        if(!uiPotionActive.getValue().equals("--"))
            time += Integer.parseInt(uiPotionActive.getValue());

        uiPotionActive.setValue(time + "");

        if(potionTimeline != null) {
            potionTimeline.stop();
        }

        potionTimeline = new Timeline(new KeyFrame(Duration.seconds(1),
            e -> uiPotionActive.setValue(Integer.parseInt(uiPotionActive.getValue()) - 1 + "")));

        potionTimeline.setCycleCount(time);
        potionTimeline.play();
        potionTimeline.setOnFinished(e -> {
            this.isInvincible = false;
            uiPotionActive.setValue("--");
        });
    }

    /**
     * Check if player is currently invincible
     * @return boolean
     */
    public boolean isInvincible() {
        return this.isInvincible;
    }

    /**
     * Check if player currently holds a sword
     * @return boolean
     */
    public boolean hasSword() {
        return this.sword != null;
    }

    /**
     * Attempt move player up
     */
    public void moveUp() {
        getDungeon().notifyObservers(null, false);
        if (getY() > 0 && !isCollision(0, -1)) {
            y().set(getY() - 1);
        }
    }

    /**
     * Attempt move player down
     */
    public void moveDown() {
        getDungeon().notifyObservers(null, false);
        if (getY() < this.getDungeon().getHeight() - 1 && !isCollision(0, 1)) {
            y().set(getY() + 1);
        }
    }

    /**
     * Attemp move left
     */
    public void moveLeft() {
        getDungeon().notifyObservers(null, false);
        if (getX() > 0 && !isCollision(-1, 0)) {
            x().set(getX() - 1);
        }
    }

    /**
     * Attemp move right
     */
    public void moveRight() {
        getDungeon().notifyObservers(null, false);
        if (getX() < this.getDungeon().getWidth() - 1 && !isCollision(1, 0)) {
            x().set(getX() + 1);
        }
    }

    /**
     * Collision controller for player with other entities
     * @param addX New x position to translate to
     * @param addY New y position to translate to
     * @return Whether the item is a collidable or not
     */
    public boolean isCollision(int addX, int addY) {
        int x = getX() + addX;
        int y = getY() + addY;

        for(Entity entity : this.getDungeon().getEntitiesAtPos(x, y)) {
            if(entity.getCollide()) {
                return entity.collisionAction(addX, addY, this);
            }
        }

        return false;
    }
}
