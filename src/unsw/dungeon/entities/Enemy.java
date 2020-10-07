package unsw.dungeon.entities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import unsw.dungeon.Dungeon;

import java.util.*;

/**
 * Enemy entity that interacts and moves towards player
 */
public class Enemy extends Entity {

    private Player player;
    private boolean isDead = false;
    private boolean isImmune;

    /**
     * Constructor for enemy
     * @param dungeon Dungeon to add enemy to
     * @param x x position of enemy
     * @param y y position of enemy
     * @param moveInterval Move time interval of enemy (s)
     * @param isImmune Property for immune enemies (i.e. gnomes)
     */
    public Enemy(Dungeon dungeon, int x, int y, double moveInterval, boolean isImmune) {
        super(dungeon, x, y);
        this.player = dungeon.getPlayer();
        this.isImmune = isImmune;

        Timeline enemyTimeline = new Timeline(new KeyFrame(Duration.seconds(moveInterval), e -> movementController()));
        enemyTimeline.setCycleCount(Timeline.INDEFINITE);
        enemyTimeline.play();
    }

    /**
     * Main move cointroller and logic of enemy
     */
    public void movementController() {
        if(isDead) return;

        int xDiff = this.getX() - player.getX();
        int yDiff = this.getY() - player.getY();

        if(player.isInvincible() && !this.isImmune) {
            //Move Away
            diffMove(-1, xDiff, yDiff);
        } else {
            //Move to Player
            diffMove(1, xDiff, yDiff);
        }
    }

    /**
     * Move action towards or away from player depending on invincible status
     * @param dir Direction multiplier of enemy towards player
     * @param xDiff x distance between player
     * @param yDiff y distance between player
     */
    public void diffMove (int dir, int xDiff, int yDiff) {
        int xMove = 0;
        int yMove = 0;

        if(xDiff < 0) {
            xMove = 1*dir;
        } else if (xDiff > 0){
            xMove = -1*dir;
        }

        if (yDiff < 0){
            yMove = 1*dir;
        } else if (yDiff > 0) {
            yMove = -1*dir;
        }

        tryMove(xMove, yMove);
    }

    /**
     * Attempt to move if possible; not blocked by other entities
     * @param addX x position to move towards
     * @param addY y position to move towards
     */
    public void tryMove(int addX, int addY) {
        int x = this.getX() + addX;
        int y = this.getY() + addY;

        //Try diagonal, then x, then y
        if(!isEnemyCollision(x, y)) {
            move(addX, addY);
        } else if(!isEnemyCollision(x, this.getY())) {
            move(addX, 0);
        } else if(!isEnemyCollision(this.getX(), y)) {
            move(0, addY);
        }
    }

    /**
     * Position modifier once checks are complete
     * @param x New x position
     * @param y New y position
     */
    public void move(int x, int y) {
        this.x().set(this.getX() + x);
        this.y().set(this.getY() + y);
    }

    /**
     * Enemy collision controller for player and static entities
     * @param x
     * @param y
     * @return
     */
    public boolean isEnemyCollision(int x, int y) {
        List<Entity> entitiesAtPos = this.getDungeon().getEntitiesAtPos(x, y);
        ListIterator<Entity> iterator = entitiesAtPos.listIterator();

        while (iterator.hasNext()) {
            Entity currEntity = iterator.next();

            if(currEntity.isPlayer()) {
                player.destroy();
                return false;
            }

            if(currEntity.getCollide()) {
                return currEntity.collisionAction(x, y, this);
            }
        }

        return false;
    }

    @Override
    public boolean collisionAction(int x, int y, Entity collideEntity) {
        if(!collideEntity.isPlayer()) return true;

        if (player.isInvincible() && !this.isImmune) {
            destroy();
            this.isDead = true;
            this.getDungeon().addEnemiesKilled();
            return false;
        } else if (player.attack()) {
            this.isDead = true;
            destroy();
            this.getDungeon().addEnemiesKilled();
        } else {
            player.destroy();
        }

        return true;
    }
}
