package boardEntities;
import boardUtilities.IShip;
/**------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 1.0.0
 * @description Concrete Destroyer Object - Sets Ship Type
 *-----------------------------------------------------*/
public class Destroyer extends Ship implements IShip {
	/**
	 * Instantiates a Destroyer Object and sets the type and player
	 * @param player Player whom the ship belongs too
	 */
	public Destroyer(int player) {
		type = ShipType.DESTROYER;
		this.player = player;
	}
}
