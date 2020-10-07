package unsw.dungeon.test;

import unsw.dungeon.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import unsw.dungeon.entities.Boulder;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.Portal;

public class PortalTest {

	static Dungeon dungeon;
	static Player player;
	static Portal portal1;
	static Portal portal2;
	static Boulder boulder;
	
	@BeforeAll
	static void setup() {
		dungeon = new Dungeon(25, 25);
		player = new Player(dungeon, 10, 5);
		portal1 = new Portal(dungeon, 9, 15, 0);
		portal2 = new Portal(dungeon, 20, 5, 0);
		boulder = new Boulder(dungeon, 10, 15);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(portal1);
		dungeon.addEntity(portal2);
		dungeon.addEntity(boulder);
	}
	
	@AfterAll
	static void tearDown() {
		
	}
	
	@Test
	public void testX() {
		assertEquals(portal1.getX(), 9);
		assertEquals(portal2.getX(), 20);
	}
	
	@Test
	public void testY() {
		assertEquals(portal1.getY(), 15);
		assertEquals(portal2.getY(), 5);
	}
	
	@Test
	public void testGetCollide() {
		assertEquals(portal1.getCollide(), true);
		assertEquals(portal2.getCollide(), true);
	}
	
	@Test
	public void testGetDungeon() {
		assertEquals(portal1.getDungeon(), dungeon);
		assertEquals(portal2.getDungeon(), dungeon);
	}
	
	@Test
	public void testIsPlayer() {
		assertEquals(portal1.isPlayer(), false);
		assertEquals(portal2.isPlayer(), false);
	}
	
	@Test
	public void testGetID() {
		assertEquals(portal1.getID(), 0);
		assertEquals(portal2.getID(), 0);
	}
	
	@Test
	public void testCollisionActionWithNonPlayer() {
		assertEquals(portal1.collisionAction(9, 15, boulder), false);
		assertEquals(boulder.getX(), 20);
		assertEquals(boulder.getY(), 5);
		assertEquals(portal2.collisionAction(20, 5, boulder), false);
		assertEquals(boulder.getX(), 9);
		assertEquals(boulder.getY(), 15);
	}

}
