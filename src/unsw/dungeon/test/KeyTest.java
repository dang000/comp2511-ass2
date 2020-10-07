package unsw.dungeon.test;

import unsw.dungeon.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import unsw.dungeon.entities.Boulder;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.items.Key;

public class KeyTest {

	static Dungeon dungeon;
	static Player player;
	static Key key;
	static Boulder boulder;
	
	@BeforeAll
	static void setup() {
		dungeon = new Dungeon(25, 25);
		player = new Player(dungeon, 10, 5);
		boulder = new Boulder(dungeon, 10, 15);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(boulder);
		key = new Key(dungeon, 9, 15, 0);
		dungeon.addEntity(key);
	}
	
	@AfterAll
	static void tearDown() {
		
	}
	
	@Test
	public void testX() {
		assertEquals(key.getX(), 9);
	}
	
	@Test
	public void testY() {
		assertEquals(key.getY(), 15);
	}
	
	@Test
	public void testGetCollide() {
		assertEquals(key.getCollide(), true);
	}
	
	@Test
	public void testGetDungeon() {
		assertEquals(key.getDungeon(), dungeon);
	}
	
	@Test
	public void testIsPlayer() {
		assertEquals(key.isPlayer(), false);
	}
	
	@Test
	public void testGetID() {
		assertEquals(key.getID(), 0);
	}
	
	@Test
	public void testCollisionActionWithNonPLayer() {
		assertEquals(key.collisionAction(9, 15, boulder), false);
		assertEquals(key.getX(), 9);
		assertEquals(key.getY(), 15);
	}
	
	@Test
	public void testCollisionActionWithPlayer() {
		assertEquals(key.collisionAction(9, 15, player), false);
		assertEquals(key.getX(), 26);
		assertEquals(key.getY(), 26);
		assertEquals(player.hasKey(), true);
	}

}
