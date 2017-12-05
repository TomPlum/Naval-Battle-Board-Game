package boardUtilities;
import boardEntities.*;
/**------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 1.0.0
 * @description Generates Ship Objects of type IShip
 *-----------------------------------------------------*/
public class ShipFactory {
	/**
	 * Returns an IShip Object
	 * @param shipType The type of Ship Object to create
	 * @param player The player whom the ship belongs too
	 * @return IShip Object
	 */
	public IShip createShip(String shipType, int player) {
		if (shipType.equalsIgnoreCase("Battleship")) {
			return new Battleship(player);
		} else if (shipType.equalsIgnoreCase("Destroyer")) {
			return new Destroyer(player);
		} else if (shipType.equalsIgnoreCase("Submarine")) {
			return new Submarine(player);
		} else if (shipType.equalsIgnoreCase("MineLayer")) {
			return new MineLayer(player);
		} else if (shipType.equalsIgnoreCase("Mine")){
			return new Mine(player);
		} else {
			return null;
		}
	}
}
