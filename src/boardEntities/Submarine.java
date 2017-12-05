package boardEntities;
import boardUtilities.IShip;
/**------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 1.0.0
 * @description Concrete Submarine Object - Sets Ship Type
 *-----------------------------------------------------*/
public class Submarine extends Ship implements IShip {
	/**
	 * Instantiates a new Submarine Object and sets the type and player
	 * @param player Player whom the ship belongs too
	 */
	public Submarine(int player) {
		type = ShipType.SUBMARINE;
		this.player = player;
	}
}
