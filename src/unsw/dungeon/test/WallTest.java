package unsw.dungeon.test;

import unsw.dungeon.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import unsw.dungeon.entities.Wall;

class WallTest {
	
	static Dungeon dungeon;
	static Wall wall;
	
	@BeforeAll
	static void setup() {
		dungeon = new Dungeon(10, 15);
		wall = new Wall(dungeon, 1, 2);
	}
	
	@AfterAll
	static void tearDown() {
		
	}
	
	@Test
	public void testX() {
		assertEquals(wall.getX(), 1);
	}
	
	@Test
	public void testY() {
		assertEquals(wall.getY(), 2);
	}
	
	@Test
	public void testGetCollide() {
		assertEquals(wall.getCollide(), true);
	}
	
	@Test
	public void testGetDungeon() {
		assertEquals(wall.getDungeon(), dungeon);
	}

}
