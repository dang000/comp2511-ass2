package unsw.dungeon.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import unsw.dungeon.*;
import unsw.dungeon.entities.Boulder;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.Wall;
import unsw.dungeon.entities.items.Treasure;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BoulderTest {

    private Dungeon dungeon;
    private Player player;
    private Boulder boulder1;
    private Boulder boulder2;
    private Boulder boulder3;
    private Treasure treasure;
    private Wall wall;

    @BeforeEach
    void init() {
        dungeon = new Dungeon(25, 25);
        player = new Player(dungeon, 10, 10);
        boulder1 = new Boulder(dungeon, 10, 11);
        boulder2 = new Boulder(dungeon, 11, 10);
        boulder3 = new Boulder(dungeon, 10, 9);
        wall  = new Wall(dungeon, 12, 10);
        treasure = new Treasure(dungeon, 10, 8);

        //
        //    T
        //    B3
        //    P  B2  W
        //    B1
        //

        dungeon.setPlayer(player);
        dungeon.addEntity(boulder1);
        dungeon.addEntity(boulder2);
        dungeon.addEntity(boulder3);
        dungeon.addEntity(treasure);
        dungeon.addEntity(wall);
    }

    @Test
    public void PushBoulder() {
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 10);
        assertEquals(boulder1.getX(), 10);
        assertEquals(boulder1.getY(), 11);
        player.moveDown();
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 11);
        assertEquals(boulder1.getX(), 10);
        assertEquals(boulder1.getY(), 12);
    }

    @Test
    public void PushBoulderAgainstItem() {
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 10);
        assertEquals(boulder3.getX(), 10);
        assertEquals(boulder3.getY(), 9);
        assertEquals(treasure.getX(), 10);
        assertEquals(treasure.getY(), 8);
        player.moveUp();
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 10);
        assertEquals(boulder3.getX(), 10);
        assertEquals(boulder3.getY(), 9);
        assertEquals(treasure.getX(), 10);
        assertEquals(treasure.getY(), 8);

    }

    @Test
    public void PushBoulderAgainstWall() {
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 10);
        assertEquals(boulder2.getX(), 11);
        assertEquals(boulder2.getY(), 10);
        assertEquals(wall.getX(), 12);
        assertEquals(wall.getY(), 10);
        player.moveRight();
        assertEquals(player.getX(), 10);
        assertEquals(player.getY(), 10);
        assertEquals(boulder2.getX(), 11);
        assertEquals(boulder2.getY(), 10);
        assertEquals(wall.getX(), 12);
        assertEquals(wall.getY(), 10);
    }
}
