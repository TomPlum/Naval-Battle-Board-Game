package boardEntitiesTests;

import static org.junit.Assert.*;
import boardUtilities.*;
import boardEntities.*;
import org.junit.Test;

public class TestShipFactory {
	private ShipFactory shipFactory = new ShipFactory();
	
	@Test
	public void testCreateBattleship() {
		IShip battleship = shipFactory.createShip("Battleship", 1);
		assertEquals(ShipType.BATTLESHIP, battleship.getType());
	}
	
	@Test
	public void testCreateDestroyer() {
		IShip destroyer = shipFactory.createShip("Destroyer", 1);
		assertEquals(ShipType.DESTROYER, destroyer.getType());
	}
	
	@Test
	public void testCreateSubmarine() {
		IShip submarine = shipFactory.createShip("Submarine", 1);
		assertEquals(ShipType.SUBMARINE, submarine.getType());
	}
	
	@Test
	public void testCreateMineLayer() {
		IShip mineLayer = shipFactory.createShip("MineLayer", 1);
		assertEquals(ShipType.MINELAYER, mineLayer.getType());
	}
}
