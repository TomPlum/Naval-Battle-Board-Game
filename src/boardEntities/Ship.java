package boardEntities;
/**------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 1.0.0
 * @description Super-Class to all Ships. Contains all 
 * 				common methods shared between all ships.
 *-----------------------------------------------------*/
public abstract class Ship {
	/**Shared Protected Fields*/
	protected ShipType type;
	protected int player, x, y;
	protected String playerName;
	
	/**
	 * Returns the ships enum type
	 * @return Ship Enum Type
	 */
	public ShipType getType() {
		return type;
	}
	
	/**
	 * Returns the player number
	 * @return 1 or 2 (For Player 1 or 2)
	 */
	public int getPlayer() {
		return player;
	}
	
	public void setPlayer(int player) {
		this.player = player;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
