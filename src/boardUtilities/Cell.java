package boardUtilities;
/**------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 2.0.0
 * @description Represents a cell on the game board and 
 * 				contains an IShip
 *-----------------------------------------------------*/
public class Cell {
	private IShip ship;
	
	/**
	 * Default Constructor - Instantiates a default Cell Object
	 */
	public Cell() {}
	
	/**
	 * Parameterised Constructor - Instantiates a Cell Object containing
	 * the specified IShip Object
	 * @param ship Ship to store in the Cell
	 */
	public Cell(IShip ship) {
		this.ship = ship;
	}
	
	/**
	 * Returns the IShip Object currently inside the Cell
	 * @return IShip Object Inside Cell
	 */
	public IShip getShip() {
		return ship;
	}

	/**
	 * Sets the Cells IShip Object to the specified IShip
	 * @param ship IShip Object to store in the Cell
	 */
	public void setShip(IShip ship) {
		this.ship = ship;
	}
}
