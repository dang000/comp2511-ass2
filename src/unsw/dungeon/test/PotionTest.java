package unsw.dungeon.test;

import unsw.dungeon.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import unsw.dungeon.entities.Boulder;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.items.Potion;

import java.util.Timer;
import java.util.TimerTask;

public class PotionTest {

	static Dungeon dungeon;
	static Player player;
	static Potion potion;
	static Boulder boulder;
	
	@BeforeAll
	static void setup() {
		dungeon = new Dungeon(25, 25);
		player = new Player(dungeon, 10, 5);
		potion = new Potion(dungeon, 9, 15, 5);
		boulder = new Boulder(dungeon, 10, 15);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(potion);
		dungeon.addEntity(boulder);
	}
	
	@AfterAll
	static void tearDown() {
		
	}
	
	@Test
	public void testX() {
		assertEquals(potion.getX(), 9);
	}
	
	@Test
	public void testY() {
		assertEquals(potion.getY(), 15);
	}
	
	@Test
	public void testGetCollide() {
		assertEquals(potion.getCollide(), true);
	}
	
	@Test
	public void testGetDungeon() {
		assertEquals(potion.getDungeon(), dungeon);
	}
	
	@Test
	public void testIsPlayer() {
		assertEquals(potion.isPlayer(), false);
	}
	
	@Test
	public void testGetPotionTimeSecs() {
		assertEquals(potion.getPotionTimeSecs(), 5);
	}
	
	@Test
	public void testCollisionActionWithNonPLayer() {
		assertEquals(potion.collisionAction(9, 15, boulder), false);
		assertEquals(potion.getX(), 9);
		assertEquals(potion.getY(), 15);
	}
	
	@Test
	public void testCollisionActionWithPlayer() {
		assertEquals(potion.collisionAction(9, 15, player), false);
		assertEquals(potion.getX(), 26);
		assertEquals(potion.getY(), 26);
		assertEquals(player.isInvincible(), true);
		
		Timer timer = new Timer();
		TimerTask checkInvincibileTask = new checkInvincible();
		timer.schedule(checkInvincibileTask, potion.getPotionTimeSecs() * 1000);
	}
	
	private class checkInvincible extends TimerTask {
		@Override
		public void run() {
			assertEquals(player.isInvincible(), false);
		}
	}

}
