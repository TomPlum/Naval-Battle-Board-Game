package boardEntities;
import boardUtilities.IShip;
/**------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 1.0.0
 * @description Concrete Battleship Object - Sets Ship Type 
 *-----------------------------------------------------*/
public class Battleship extends Ship implements IShip {
	/**
	 * Instantiates a Battleship Object and sets the player and type
	 * @param player Player whom the ships belongs too
	 */
	public Battleship(int player) {
		type = ShipType.BATTLESHIP;
		this.player = player;
	}
}
