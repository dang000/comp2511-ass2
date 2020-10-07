package unsw.dungeon.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import unsw.dungeon.*;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.Wall;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlayerMoveTest {

    private Dungeon dungeon;
    private Player player;

    @BeforeEach
    void init() {
        dungeon = new Dungeon(25, 25);
        player = new Player(dungeon, 10, 10);
        dungeon.setPlayer(player);
    }

    @Test
    public void MoveRight() {
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 10);
        player.moveRight();
        assertEquals(player.getX(), 11);
        assertEquals(player.getY(), 10);
    }

    @Test
    public void MoveLeft() {
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 10);
        player.moveLeft();
        assertEquals(player.getX(), 9);
        assertEquals(player.getY(), 10);
    }

    @Test
    public void MoveUp() {
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 10);
        player.moveUp();
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 9);
    }

    @Test
    public void MoveDown() {
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 10);
        player.moveDown();
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 11);
    }

    @Test
    public void MoveIntoWall() {
        Wall wall = new Wall(dungeon, 11, 10);
        dungeon.addEntity(wall);

        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 10);
        player.moveRight();
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 10);
    }
}
