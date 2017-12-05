package boardEntities;

import boardUtilities.IShip;
/**------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 1.0.0
 * @description Concrete Mine Object - Sets Ship Type
 *-----------------------------------------------------*/
public class Mine extends Ship implements IShip {
	/**
	 * Instantiates a Mine Object and sets the player and type
	 * @param player The player whom the ship belongs too
	 */
	public Mine(int player) {
		type = ShipType.MINE;
		this.player = player;
	}
}
