package boardUtilities;

public class Controller {
	private int currentPlayer;
	private int turnNo;
	
	public Controller() {
		this.currentPlayer = 1;
		this.turnNo = 0;
	}
	
	public void switchCurrentPlayer() {
		if (currentPlayer == 1) {
			this.currentPlayer = 2;
		} else if (currentPlayer == 2) {
			this.currentPlayer = 1;
		}
	}
	
	public void incrememntTurnNo() {
		this.turnNo++;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public int getTurnNo() {
		return turnNo;
	}
}
