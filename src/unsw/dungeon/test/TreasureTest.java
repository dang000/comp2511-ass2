package unsw.dungeon.test;

import unsw.dungeon.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import unsw.dungeon.entities.Boulder;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.items.Treasure;

public class TreasureTest {

	static Dungeon dungeon;
	static Player player;
	static Treasure treasure;
	static Boulder boulder;
	
	@BeforeAll
	static void setup() {
		dungeon = new Dungeon(25, 25);
		player = new Player(dungeon, 10, 5);
		treasure = new Treasure(dungeon, 9, 15);
		boulder = new Boulder(dungeon, 10, 15);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(treasure);
		dungeon.addEntity(boulder);
	}
	
	@AfterAll
	static void tearDown() {
		
	}
	
	@Test
	public void testX() {
		assertEquals(treasure.getX(), 9);
	}
	
	@Test
	public void testY() {
		assertEquals(treasure.getY(), 15);
	}
	
	@Test
	public void testGetCollide() {
		assertEquals(treasure.getCollide(), true);
	}
	
	@Test
	public void testGetDungeon() {
		assertEquals(treasure.getDungeon(), dungeon);
	}
	
	@Test
	public void testIsPlayer() {
		assertEquals(treasure.isPlayer(), false);
	}
	
	@Test
	public void testCollisionActionWithNonPLayer() {
		assertEquals(treasure.collisionAction(9, 15, boulder), false);
		assertEquals(treasure.getX(), 9);
		assertEquals(treasure.getY(), 15);
	}
	
	@Test
	public void testCollisionActionWithPlayer() {
		assertEquals(treasure.collisionAction(9, 15, player), false);
		assertEquals(treasure.getX(), 26);
		assertEquals(treasure.getY(), 26);
	}

}
