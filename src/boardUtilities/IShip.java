package boardUtilities;
import boardEntities.ShipType;
/**------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 1.0.0
 * @description Contains methods that are invoked on Ship
 * 				Objects. Complies to the OCP.
 *-----------------------------------------------------*/
public interface IShip {
	public ShipType getType();
	public int getPlayer();
	public void setPlayer(int player);
	public void setX(int x);
	public void setY(int y);
	public int getX();
	public int getY();
	public String getPlayerName();
	public void setPlayerName(String playerName);
}
