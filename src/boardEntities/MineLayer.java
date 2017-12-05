package boardEntities;
import boardUtilities.IShip;
/**------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 1.0.0
 * @description Concrete MineLayer Object - Sets Ship Type
 *-----------------------------------------------------*/
public class MineLayer extends Ship implements IShip {
	/**
	 * Instantiates a new MineLayer Object and sets the player and type
	 * @param player The player whom the ship belongs too
	 */
	public MineLayer(int player) {
		type = ShipType.MINELAYER;
		this.player = player;
	}
}
