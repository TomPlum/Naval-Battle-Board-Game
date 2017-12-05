package boardEntitiesTests;

import static org.junit.Assert.*;

import org.junit.Test;

import boardUtilities.IShip;
import boardUtilities.ShipFactory;
/**-----------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 1.0.0
 * @description This class tests the enum length of each ship
 *-----------------------------------------------------------*/
public class TestGetShipLengths {
	private ShipFactory factory = new ShipFactory();
	
	@Test
	public void testBattleshipLength() {
		IShip bs = factory.createShip("BATTLESHIP", 1);
		int actualLength = bs.getType().getLength();
		int expectedLength = 6;
		assertEquals(expectedLength, actualLength);
	}
	
	@Test
	public void testDestroyerLength() {
		IShip d = factory.createShip("DESTROYER", 1);
		int actualLength = d.getType().getLength();
		int expectedLength = 4;
		assertEquals(expectedLength, actualLength);
	}
	
	@Test
	public void testSubmarineLength() {
		IShip s = factory.createShip("SUBMARINE", 1);
		int actualLength = s.getType().getLength();
		int expectedLength = 3;
		assertEquals(expectedLength, actualLength);
	}
	
	@Test
	public void testMineLayer() {
		IShip ml = factory.createShip("MINELAYER", 1);
		int actualLength = ml.getType().getLength();
		int expectedLength = 2;
		assertEquals(expectedLength, actualLength);
	}
	
	@Test
	public void testMine() {
		IShip m = factory.createShip("MINE", 1);
		int actualLength = m.getType().getLength();
		int expectedLength = 1;
		assertEquals(expectedLength, actualLength);
	}
}
