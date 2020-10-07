package unsw.dungeon.test;

import unsw.dungeon.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import unsw.dungeon.entities.Boulder;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.items.Sword;


public class SwordTest {

	static Dungeon dungeon;
	static Player player;
	static Sword sword;
	static Boulder boulder;
	
	@BeforeAll
	static void setup() {
		dungeon = new Dungeon(25, 25);
		player = new Player(dungeon, 10, 5);
		sword = new Sword(dungeon, 9, 15);
		boulder = new Boulder(dungeon, 10, 15);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(sword);
		dungeon.addEntity(boulder);
	}
	
	@AfterAll
	static void tearDown() {
		
	}
	
	 @Test
	public void testX() {
		assertEquals(sword.getX(), 9);
	}
	 
	 @Test
	public void testY() {
		 assertEquals(sword.getY(), 15);
	 }
	 
	 @Test
	 public void testGetCollide() {
		 assertEquals(sword.getCollide(), true);
	 }
	 
	 @Test
	 public void testGetDungeon() {
		 assertEquals(sword.getDungeon(), dungeon);
	 }
	 
	 @Test
	 public void testIsPlayer() {
		 assertEquals(sword.isPlayer(), false);
	 }
	 
	 @Test
	 public void testGetMaxAttackCount() {
		 assertEquals(sword.getMaxAttackCount(), 5);
	 }
	 
	 @Test
	 public void testCollisionActionWithNonPlayer() {
		assertEquals(sword.collisionAction(9, 15, boulder), false);
		assertEquals(sword.getX(), 9);
		assertEquals(sword.getY(), 15);
	 }
	 
	@Test
	public void testCollisionActionWithPlayer() {
		assertEquals(sword.collisionAction(9, 15, player), false);
		assertEquals(sword.getX(), 26);
		assertEquals(sword.getY(), 26);
		assertEquals(player.hasSword(), true);
	}
	
	 @Test
	 public void testAttack() {
		 assertEquals(sword.attackCount(), 5);
		 player.attack();
		 player.attack();
		 player.attack();
		 player.attack();
		 player.attack();
		 assertEquals(sword.attackCount(), 0);
		 assertEquals(player.hasSword(), false);
	 }
	
}
