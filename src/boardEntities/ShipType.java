package boardEntities;
/**------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 1.0.0
 * @description Defines Enum Types and lengths for the ships 
 *-----------------------------------------------------*/
public enum ShipType {	
	BATTLESHIP(6), DESTROYER(4), SUBMARINE(3), MINELAYER(2), MINE(1);
	
	private final int length;
	
	/**
	 * Instantiates a new ShipType 
	 * @param length Length of Ship
	 */
	private ShipType(int length) {
		this.length = length;
	}
	
	/**
	 * Returns the length of the ship
	 * @return Length of the ship
	 */
	public int getLength() {
		return length;
	}
}
