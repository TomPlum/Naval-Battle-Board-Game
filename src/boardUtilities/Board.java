package boardUtilities;
import java.util.InputMismatchException;
import java.util.Scanner;
/**------------------------------------------------------
 * @author Tom Plumpton (1500936)
 * @version 2.0.0
 * @description Handles all the console functionality,
 * 				ship logic and error handling.
 *-----------------------------------------------------*/
public class Board {
	/**Private Fields*/
	private Scanner userInput;
	private int rows, cols;
	private int battleships, destroyers, submarines, minelayers, mines;
	private int battleships2, destroyers2, submarines2, minelayers2, mines2;
	private String player1, player2;
	private ShipFactory shipFactory;
	private Cell[][] cells, cellsSnapshot;
	private Controller ctrl;
	
	/**
	 * Invokes all the relevant methods in order to run the game
	 */
	public void startGame() {
		//Game Initialisation
		userInput = new Scanner(System.in);
		shipFactory = new ShipFactory();
		ctrl = new Controller();
		welcomeMessage();
		sleep(200);
		configurePlayers();
		sleep(200);
		configureBoard();
		cells = new Cell[rows][cols]; //Instantiate the 2D-Array AFTER rows/cols have been decided.
		cellsSnapshot = new Cell[rows][cols]; //Instantiate so System.arraycopy can work.
		printBoard(rows, cols);
		initPlayer1();
		sleep(200);
		initPlayer2();
		sleep(200);
		startPlayerTurns();
	}
	
	/**
	 * Prints a blank game board with the specified rows and columns
	 * @param rows Number of Rows
	 * @param cols Number of Columns
	 */
	public void printBoard(int rows, int cols) {
		System.out.println("This is the battle area, you must place your ships\non your side of the border.\n");
		
		//Players Names
		for (int i = 0; i < cols; i++) {
			if (i == cols / 4) {
				System.out.print(player1);
			} else if (i == (cols / 4) * 3) {
				System.out.print(" " + player2);
			} else {
				System.out.print("    ");
			}
		}
		System.out.println(); //Line Break Between Names & Board
		
		//Initial Space
		System.out.print("   ");
		sleep(20);
		
		//Top Line of Numbers
		for (int j = 0; j < cols; j++) {
			if (j <= 9) {
				if (j == cols / 2 ) {
					System.out.print("|  ");
				}
				System.out.print(j + "  ");
				sleep(20);
			} else {
				if (j == cols / 2) {
					System.out.print("|  ");
				}
				System.out.print(j + " ");
				sleep(20);
			}
		}
		
		//Line Break for first Row
		System.out.println("");
		sleep(20);
		
		//Nested Loop - Generates Rows
		for (int i = 0; i < rows; i++) {
			//Left Side Numbers
			if (i <= 9) {
				System.out.print(" " + i + " ");
				sleep(20);
			} else {
				System.out.print(i + " ");
				sleep(20);
			}
			
			//Hyphenated Cells
			for(int j = 0; j < cols; j++) {
				if (j == cols / 2) {
					System.out.print("|  ");
				}
				//If j < cols - 1, print "- " as normal, else print without a space for end numbers
				if (j < cols - 1) {
					System.out.print("-  ");
				} else {
					System.out.print("-");
				}
				
				sleep(20);
			}
			
			//Right Side Numbers
			System.out.print(" " + i);
			sleep(20);
			System.out.println();
		}
		
		//Final-Initial Space
		System.out.print("   ");
		sleep(20);
		
		//Bottom Line of Numbers
		for (int j = 0; j < cols; j++) {
			if (j <= 9) {
				if (j == cols / 2 ) {
					System.out.print("|  ");
				}
				System.out.print(j + "  ");
				sleep(20);
			} else {
				if (j == cols / 2) {
					System.out.print("|  ");
				}
				System.out.print(j + " ");
				sleep(20);
			}
		}
		System.out.println(); //Final Line Break
	}
	
	/**
	 * Prints a board containing the ships belonging to the passed player
	 * @param player Player whose ships to be printed
	 */
	private void buildBoard(int player, IShip currentShip) {
		//Initial Space
		if (rows > 9) {
			System.out.print("   ");
		} else {
			System.out.print("  "); 
		}
		sleep(20);
		
		//Top Line of Numbers
		for (int j = 0; j < cols; j++) {
			if (j <= 9) {
				System.out.print(j + "  ");
				sleep(20);
			} else {
				System.out.print(j + " ");
				sleep(20);
			}
		}
		
		//Line Break for first Row
		System.out.println("");
		sleep(20);
		
		//Nested Loop - Generates Rows
		int xCount = 0;
		int yCount = 0;
		for (int i = 0; i < rows; i++) {
			//Left Side Numbers
			if (i <= 9) {
				System.out.print(" " + i + " ");
				sleep(20);
			} else {
				System.out.print(i + " ");
				sleep(20);
			}
			
			//Hyphenated Cells OR Ships
			for(int j = 0; j < cols; j++) {
				if (cells[xCount][yCount] != null && cells[xCount][yCount].getShip().getPlayer() == player && currentShip != null && xCount == currentShip.getX() && yCount == currentShip.getY()) {
					System.err.print(getShipTypeFromCell(xCount, yCount) + "  "); //Print in Red
					sleep(20);
				} else if (cells[xCount][yCount] != null && cells[xCount][yCount].getShip().getPlayer() == player) { 
					System.out.print(getShipTypeFromCell(xCount, yCount) + "  "); //Print in Black
					sleep(20);
				} else if (cells[xCount][yCount] != null && cells[xCount][yCount].getShip().getPlayer() == player && currentShip == null) {
					//This statement handles a null passed as the ship
					System.out.print(getShipTypeFromCell(xCount, yCount) + "  "); //Print in Black
					sleep(20);
				} else {
					System.out.print("-  "); //There's no ship here, so print a hyphen
					sleep(20);
				}
				if (yCount < cols) {
					yCount++;
				}
			}
			System.out.println();
			if (xCount < rows) {
				xCount++;
			}
			yCount = 0;
		}		
	}
	
	/**
	 * Prompts the user for the number of rows and sets it if correct 
	 */
	private void configureBoard() {
		animateMessage("Please enter the number of rows for the board: ");
		boolean correctRows = false;
		while(!correctRows) {
			try {
				int inputRows = userInput.nextInt();
				if (inputRows >= 1 && inputRows < 27) {
					setRows(inputRows);
					correctRows = true;
				} else {
					animateErrorMessage("## ERROR ## - ROWS MUST BE >= 0 & < 27\n");
					animateMessage("Please enter the number of rows for the board: ");
				}
			} catch (InputMismatchException e) {
				animateErrorMessage("## ERROR ## - ILLEGAL CHARACTER ENTERED\n");
				animateMessage("Please enter the number of rows for the board: ");
			}
		}
		
		animateMessage("Please enter the number of columns for the board: ");
		boolean correctCols = false;
		while(!correctCols) {
			try {
				int inputCols = userInput.nextInt();
				if (inputCols >= 0 && inputCols < 27 && inputCols % 2 == 0) {
					//Checks if cols is greater than 0, less than 27 and even
					setCols(inputCols);
					correctCols = true;
				} else {
					animateErrorMessage("## ERROR ## - COLUMNS MUST BE >= 0 & < 27 & EVEN\n");
					animateMessage("Please enter the number of columns for the board: ");
				}
			} catch (InputMismatchException e) {
				animateErrorMessage("## ERROR ## - ILLEGAL CHARACTER ENTERED\n");
				animateMessage("Please enter the number of columns for the board: ");
			}
		}
		System.out.println();
	}
	
	/**
	 * Prompts the users for the player names and sets if correct
	 */
	private void configurePlayers() {
		animateMessage("[Player 1] Enter Name: ");
		boolean correctNameFormat = false;
		while(!correctNameFormat) {
			String player1 = userInput.nextLine();
			if (player1.length() < 10 && player1.length() > 0) {
				setPlayer1(player1);
				correctNameFormat = true;
			} else if (player1.length() == 0) {
				animateErrorMessage("## ERROR ## - NAME CANNOT BE BLANK\n");
				animateMessage("[Player 1] Enter Name: ");
			} else {
				animateErrorMessage("## ERROR ## - NAME MUST BE < 10 CHARACTERS\n");
				animateMessage("[Player 1] Enter Name: ");
			}
		}
		
		animateMessage("[Player 2] Enter Name: ");
		boolean correctNameFormat2 = false;
		while(!correctNameFormat2) {
			String player2 = userInput.nextLine();
			if (player2.length() < 10 && player2.length() > 0) {
				setPlayer2(player2);
				correctNameFormat2 = true;
			} else if (player2.length() == 0) {
				animateErrorMessage("## ERROR ## - NAME CANNOT BE BLANK\n");
				animateMessage("[Player 2] Enter Name: ");
			} else {
				animateErrorMessage("## ERROR ## - NAME MUST BE < 10 CHARACTERS\n");
				animateMessage("[Player 2] Enter Name: ");
			}
		}
	}
	
	/**
	 * Prints player 1's board with their ships
	 */
	private void printPlayer1Board(IShip currentShip) {
		animateMessage(player1 + ", here are your ships");
		loading();
		System.out.println();
		buildBoard(1, currentShip);
	}
	
	/**
	 * Prints player 2's board with their ships
	 */
	private void printPlayer2Board(IShip currentShip) {
		animateMessage(player2 + ", here are your ships");
		loading();
		System.out.println();
		buildBoard(2, currentShip);
	}
	
	/**
	 * Prompts the user for the number of Battleships they would like and 
	 * configures them.
	 * @param player Player whom is to be configuring the ships
	 */
	private void configureNumberOfBattleships(int player) {
		if (player == 1) {
			animateMessage(player1 + ", how many Battleships would you like to place?\n");
		} else if (player == 2) {
			animateMessage(player2 + ", how many Battleships would you like to place?\n");
		}	
		userInput.nextLine(); //Skip Line
		int usersShipInput = Integer.parseInt(userInput.nextLine());
		boolean correctShipNo = false;
		while(!correctShipNo) {
			if (usersShipInput < (((cols * rows) / 2) - 4) && usersShipInput > 0) {
				if (player == 1) {
					battleships = usersShipInput;
				} else if (player == 2) {
					battleships2 = usersShipInput;
				}
				correctShipNo = true;
			} else if (usersShipInput <= 0){
				animateErrorMessage("## ERROR ## - NOT ENOUGH BATTLESHIPS\n");
				animateMessage("Please re-enter the number of Battleships you would like to place:\n");
				usersShipInput = Integer.parseInt(userInput.nextLine());
			} else if (usersShipInput > (((cols * rows) / 2) - 3)) {
				animateErrorMessage("## ERROR ## - TOO MANY BATTLESHIPS\n");
				animateMessage("Please re-enter the number of Battleships you would like to place:\n");
				usersShipInput = Integer.parseInt(userInput.nextLine());
			}
		}
		
		if (player == 1) {
			for (int i = 1; i <= battleships; i++) {
				configureBattleship(player, i);
			}
		} else if (player == 2) {
			for (int i = 1; i <= battleships2; i++) {
				configureBattleship(player, i);
			}
		}
	}
	
	/**
	 * Prompts the user for a coordinate for their Battleship. Also handles errors
	 * for incorect coordinate formats.
	 * @param player Player who is to be configuring the ship
	 * @param shipNo The current iteration of this method (Handled by previous method)
	 */
	private void configureBattleship(int player, int shipNo) {
		String suffix = calculateNumberSuffix(shipNo);					
		animateMessage("Enter the coordinates for your " + shipNo + suffix + " Battleship.");
		if (shipNo == 1) {
			animateMessage("Please separate the ordinates with a comma. E.g. 3, 6\n");
		}
		String coordinates = userInput.nextLine().replaceAll("\\s", "");
		while(!validateCoordinateFormat(coordinates)) {
			animateErrorMessage("## ERROR ## - INCORRECT COORDINATE FORMAT\n");
			if (player == 1) {
				animateMessage(player1 + ", enter the coordinates for your Battleship.\n");
			} else  {
				animateMessage(player2 + ", enter the coordinates for your Battleship.\n");
			}
			coordinates = userInput.nextLine().replaceAll("\\s", "");
		}
		IShip bs = shipFactory.createShip("Battleship", player);
		Integer x = parseX(coordinates);
		Integer y = parseY(coordinates);
		//System.out.println("configureBattleship() : (" + x + ", " + y + ")");
		addShip(bs, y, x, player, shipNo);
	}
	
	/**
	 * Prompts the user for the number of Destroyers they would like and 
	 * configures them.
	 * @param player Player whom is to be configuring the ships
	 */
	private void configureNumberOfDestroyers(int player) {
		if (player == 1) {
			animateMessage(player1 + ", how many Destroyers would you like to place?\n");
		} else if (player == 2) {
			animateMessage(player2 + ", how many Destroyers would you like to place?\n");
		}	
		int usersShipInput = Integer.parseInt(userInput.nextLine());
		boolean correctShipNo = false;
		while(!correctShipNo) {
			if (player == 1 && usersShipInput < (((cols * rows) / 2) - battleships - 3) && usersShipInput > 0) {
				destroyers = usersShipInput;
				correctShipNo = true;
			} else if (player == 2 && usersShipInput < (((cols * rows) / 2) - battleships2 - 3) && usersShipInput > 0) {
				destroyers2 = usersShipInput;
				correctShipNo = true;
			} else if (usersShipInput <= 0) {
				animateErrorMessage("## ERROR ## - NOT ENOUGH DESTROYERS\n");
				animateMessage("Please re-enter the number of Destroyers you would like to place:\n");
				usersShipInput = Integer.parseInt(userInput.nextLine());
			} else if (usersShipInput > (((cols * rows) / 2) - 3)) {
				animateErrorMessage("## ERROR ## - TOO MANY DESTROYERS\n");
				animateMessage("Please re-enter the number of Destroyers you would like to place:\n");
				usersShipInput = Integer.parseInt(userInput.nextLine());
			}
		}
		
		if (player == 1) {
			for (int i = 1; i <= destroyers; i++) {
				configureDestroyer(player, i);
			}
		} else if (player == 2) {
			for (int i = 1; i <= destroyers2; i++) {
				configureDestroyer(player, i);
			}
		}
	}
	
	/**
	 * Prompts the user for a coordinate for their Destroyer. Also handles errors
	 * for incorect coordinate formats.
	 * @param player Player who is to be configuring the ship
	 * @param shipNo The current iteration of this method (Handled by previous method)
	 */
	private void configureDestroyer(int player, int shipNo) {
		String suffix = calculateNumberSuffix(shipNo);					
		animateMessage("Enter the coordinates for your " + shipNo + suffix + " Destroyer.\n");
		String coordinates = userInput.nextLine().replaceAll("\\s", "");
		while(!validateCoordinateFormat(coordinates)) {
			animateErrorMessage("## ERROR ## - INCORRECT COORDINATE FORMAT\n");
			animateMessage("Please enter the coordinates for your Destroyer\n");
			coordinates = userInput.nextLine().replaceAll("\\s", "");
		}
		IShip d = shipFactory.createShip("Destroyer", player);
		Integer x = parseX(coordinates);
		Integer y = parseY(coordinates);
		addShip(d, y, x, player, shipNo);
	}
	
	/**
	 * Prompts the user for the number of Submarines they would like and 
	 * configures them.
	 * @param player Player whom is to be configuring the ships
	 */
	private void configureNumberOfSubmarines(int player) {
		if (player == 1) {
			animateMessage(player1 + ", how many Submarines would you like to place?\n");
		} else if (player == 2) {
			animateMessage(player2 + ", how many Submarines would you like to place?\n");
		}	
		int usersShipInput = Integer.parseInt(userInput.nextLine());
		boolean correctShipNo = false;
		while(!correctShipNo) {
			if (player == 1 && usersShipInput < (((cols * rows) / 2) - battleships - destroyers - 2) && usersShipInput > 0) {
				submarines = usersShipInput;
				correctShipNo = true;
			} else if (player == 2 && usersShipInput < (((cols * rows) / 2) - battleships2 - destroyers2 - 2) && usersShipInput > 0) {
				submarines2 = usersShipInput;
				correctShipNo = true;
			} else if (usersShipInput <= 0){
				animateErrorMessage("## ERROR ## - NOT ENOUGH SUBMARINES\n");
				animateMessage("Please re-enter the number of Submarines you would like to place:\n");
				usersShipInput = Integer.parseInt(userInput.nextLine());
			} else if (usersShipInput > (((cols * rows) / 2) - 3)) {
				animateErrorMessage("## ERROR ## - TOO MANY SUBMARINES\n");
				animateMessage("Please re-enter the number of Submarines you would like to place:\n");
				usersShipInput = Integer.parseInt(userInput.nextLine());
			}
		}
		
		if (player == 1) {
			for (int i = 1; i <= submarines; i++) {
				configureSubmarine(player, i);
			}
		} else if (player == 2) {
			for (int i = 1; i <= submarines2; i++) {
				configureSubmarine(player, i);
			}
		}
	}
	
	/**
	 * Prompts the user for a coordinate for their Submarine. Also handles errors
	 * for incorect coordinate formats.
	 * @param player Player who is to be configuring the ship
	 * @param shipNo The current iteration of this method (Handled by previous method)
	 */
	private void configureSubmarine(int player, int shipNo) {
		String suffix = calculateNumberSuffix(shipNo);					
		animateMessage("Enter the coordinates for your " + shipNo + suffix + " Submarine.\n");
		String coordinates = userInput.nextLine().replaceAll("\\s", "");
		while(!validateCoordinateFormat(coordinates)) {
			animateErrorMessage("## ERROR ## - INCORRECT COORDINATE FORMAT\n");
			animateMessage("Please enter the coordinates for your Submarine.\n");
			coordinates = userInput.nextLine().replaceAll("\\s", "");
		}
		IShip s = shipFactory.createShip("Submarine", player);
		Integer x = parseX(coordinates);
		Integer y = parseY(coordinates);
		addShip(s, y, x, player, shipNo);
	}
	
	/**
	 * Prompts the user for the number of MineLayers they would like and 
	 * configures them.
	 * @param player Player whom is to be configuring the ships
	 */
	private void configureNumberOfMineLayers(int player) {
		if (player == 1) {
			animateMessage(player1 + ", how many MineLayers would you like to place?\n");
		} else if (player == 2) {
			animateMessage(player2 + ", how many MineLayers would you like to place?\n");
		}	
		int usersShipInput = Integer.parseInt(userInput.nextLine());
		boolean correctShipNo = false;
		while(!correctShipNo) {
			if (player == 1 && usersShipInput < (((cols * rows) / 2) - battleships - destroyers - submarines - 1) && usersShipInput > 0) {
				minelayers = usersShipInput;
				correctShipNo = true;
			} else if (player == 2 && usersShipInput < (((cols * rows) / 2) - battleships2 - destroyers2 - submarines2 - 1) && usersShipInput > 0) {
				minelayers2 = usersShipInput;
				correctShipNo = true;
			} else if (usersShipInput <= 0) {
				animateErrorMessage("## ERROR ## - NOT ENOUGH MINELAYERS\n");
				animateMessage("Please re-enter the number of MineLayers you would like to place:\n");
				usersShipInput = Integer.parseInt(userInput.nextLine());
			} else if (usersShipInput > (((cols * rows) / 2) - 3)) {
				animateErrorMessage("## ERROR ## - TOO MANY MINELAYERS\n");
				animateMessage("Please re-enter the number of MineLayers you would like to place:\n");
				usersShipInput = Integer.parseInt(userInput.nextLine());
			}
		}
		
		if (player == 1) {
			for (int i = 1; i <= minelayers; i++) {
				configureMineLayer(player, i);
			}
		} else if (player == 2) {
			for (int i = 1; i <= minelayers2; i++) {
				configureMineLayer(player, i);
			}
		}
	}
	
	/**
	 * Prompts the user for a coordinate for their MineLayer. Also handles errors
	 * for incorect coordinate formats.
	 * @param player Player who is to be configuring the ship
	 * @param shipNo The current iteration of this method (Handled by previous method)
	 */
	private void configureMineLayer(int player, int shipNo) {
		String suffix = calculateNumberSuffix(shipNo);					
		animateMessage("Enter the coordinates for your " + shipNo + suffix + " MineLayer.\n");
		String coordinates = userInput.nextLine().replaceAll("\\s", "");
		while(!validateCoordinateFormat(coordinates)) {
			animateErrorMessage("## ERROR ## - INCORRECT COORDINATE FORMAT\n");
			animateMessage("Please enter the coordinates for your MineLayer.\n");
			coordinates = userInput.nextLine().replaceAll("\\s", "");
		}
		IShip ml = shipFactory.createShip("MineLayer", player);
		Integer x = parseX(coordinates);
		Integer y = parseY(coordinates);
		addShip(ml, y, x, player, shipNo);
	}
	
	/**
	 * Prompts the user for the number of Mines they would like and 
	 * configures them.
	 * @param player Player whom is to be configuring the ships
	 */
	private void configureNumberOfMines(int player) {
		if (player == 1) {
			animateMessage(player1 + ", how many Mines would you like to place?\n");
		} else if (player == 2) {
			animateMessage(player2 + ", how many Mines would you like to place?\n");
		}	
		int usersShipInput = Integer.parseInt(userInput.nextLine());
		boolean correctShipNo = false;
		while(!correctShipNo) {
			if (player == 1 && usersShipInput < (((cols * rows) / 2) - battleships - destroyers - submarines - minelayers) && usersShipInput > 0) {
				mines = usersShipInput;
				correctShipNo = true;
			} else if (player == 2 && usersShipInput < (((cols * rows) / 2) - battleships2 - destroyers2 - submarines2 - minelayers2) && usersShipInput > 0) {
				mines2 = usersShipInput;
				correctShipNo = true;
			} else if (usersShipInput <= 0) {
				animateErrorMessage("## ERROR ## - NOT ENOUGH MINES\n");
				animateMessage("Please re-enter the number of Mines you would like to place:\n");
				usersShipInput = Integer.parseInt(userInput.nextLine());
			} else if (usersShipInput > (((cols * rows) / 2) - battleships - destroyers - submarines - minelayers)) {
				animateErrorMessage("## ERROR ## - TOO MANY MINES\n");
				animateMessage("Please re-enter the number of Mines you would like to place:\n");
				usersShipInput = Integer.parseInt(userInput.nextLine());
			}
		}
		
		if (player == 1) {
			for (int i = 1; i <= mines; i++) {
				configureMine(player, i);
			}
		} else if (player == 2) {
			for (int i = 1; i <= mines2; i++) {
				configureMine(player, i);
			}
		}
	}
	
	/**
	 * Prompts the user for a coordinate for their Mine. Also handles errors
	 * for incorect coordinate formats.
	 * @param player Player who is to be configuring the ship
	 * @param shipNo The current iteration of this method (Handled by previous method)
	 */
	private void configureMine(int player, int shipNo) {
		String suffix = calculateNumberSuffix(shipNo);					
		animateMessage("Enter the coordinates for your " + shipNo + suffix + " Mine.\n");
		String coordinates = userInput.nextLine().replaceAll("\\s", "");
		while(!validateCoordinateFormat(coordinates)) {
			animateErrorMessage("## ERROR ## - INCORRECT COORDINATE FORMAT\n");
			animateMessage("Please enter the coordinates for your Mine.\n");
			coordinates = userInput.nextLine().replaceAll("\\s", "");
		}
		IShip mine = shipFactory.createShip("Mine", player);
		Integer x = parseX(coordinates);
		Integer y = parseY(coordinates);
		addShip(mine, y, x, player, shipNo);
	}
	
	/**
	 * Invokes all the ship configuration methods for player 1
	 */
	private void initPlayer1() {
		configureNumberOfBattleships(1);
		configureNumberOfDestroyers(1);
		configureNumberOfSubmarines(1);
		configureNumberOfMineLayers(1);
		configureNumberOfMines(1);
		animateMessage("Placing " + player1 + "'s ships");
		loading();
		System.out.println();
	}
	
	/**
	 * Invokes all the ship configuration methods for player 2
	 */
	private void initPlayer2() {
		configureNumberOfBattleships(2);
		configureNumberOfDestroyers(2);
		configureNumberOfSubmarines(2);
		configureNumberOfMineLayers(2);
		configureNumberOfMines(2);
		animateMessage("Placing " + player2 + "'s ships");
		loading();
		System.out.println();
	}
	
	public void startPlayerTurns() {
		boolean endOfGame = false;
		while(!endOfGame) {
			if (ctrl.getCurrentPlayer() == 1) {
				int p1total = battleships + destroyers + submarines + minelayers; //Excl mines as they don't move
				animateMessage(player1 + "'s turn.");
				takeSnapshot(); //Store current ship config in snapshot, since we're moving them about
				for (int i = 0; i < p1total; i++) {
					IShip shipToMove = getPlayerShipViaIndex(i, 1);
					printPlayer1Board(shipToMove); 
					animateMessage("Please enter the new coordinates for your " + String.valueOf(shipToMove.getType()).toLowerCase() + "\n");
					String coordinates = userInput.nextLine().replaceAll("\\s", "");
					while(!validateCoordinateFormat(coordinates)) {
						animateErrorMessage("## ERROR ## - INCORRECT COORDINATE FORMAT\n");
						animateMessage("Please enter the new coordinates for your " + String.valueOf(shipToMove.getType()).toLowerCase() + "\n");
						coordinates = userInput.nextLine().replaceAll("\\s", "");
					}
					moveShip(shipToMove, shipToMove.getX(), shipToMove.getY(), parseX(coordinates), parseY(coordinates));
					shipToMove = null;
					if (gameOver()) {
						endOfGame = true;
					}
				}
				printPlayer1Board(null); //Print the board one last time before turn ends
				animateMessage("Turn Over");
				loading();
				clearScreen(); //So other player cannot see ships
				ctrl.incrememntTurnNo();
				ctrl.switchCurrentPlayer(); //Switch Players
			} else if (ctrl.getCurrentPlayer() == 2) {
				int p2total = battleships2 + destroyers2 + submarines2 + minelayers2; //Excl mines as they don't move
				animateMessage(player2 + "'s turn.");
				takeSnapshot(); //Store current ship config in snapshot, since we're moving them about
				for (int j = 0; j < p2total; j++) {
					IShip shipToMove2 = getPlayerShipViaIndex(j, 2);
					printPlayer2Board(shipToMove2);
					animateMessage("Please enter the new coordinates for your " + String.valueOf(shipToMove2.getType()).toLowerCase() + "\n");
					String coordinates = userInput.nextLine().replaceAll("\\s", "");
					while(!validateCoordinateFormat(coordinates)) {
						animateErrorMessage("## ERROR ## - INCORRECT COORDINATE FORMAT\n");
						animateMessage("Please enter the new coordinates for your " + String.valueOf(shipToMove2.getType()).toLowerCase() + "\n");
						coordinates = userInput.nextLine().replaceAll("\\s", "");
					}
					moveShip(shipToMove2, shipToMove2.getX(), shipToMove2.getY(), parseX(coordinates), parseY(coordinates));
					shipToMove2 = null;
					if (gameOver()) {
						endOfGame = true;
					}
				}
				printPlayer2Board(null); //Print the board one last time before turn ends
				animateMessage("Turn Over.");
				loading();
				clearScreen(); //So other player cannot see ships
				ctrl.incrememntTurnNo();
				ctrl.switchCurrentPlayer(); //Switch Players
			}
			
			//If both players have moved their ships
			if (ctrl.getTurnNo() % 2 == 0 && ctrl.getTurnNo() != 0) {
				int xCount = 0;
				int yCount = 0;
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						try {
							if (cells[xCount][yCount].getShip() != null) {
								checkNeighbouringCells(cells[xCount][yCount].getShip()); //Check for conflict
							}
						} catch (NullPointerException npe) {
							//Do nothing
						}
						
						xCount++; //Increment X
					}
					xCount = 0; //Reset X
					yCount++; //Increment Y
				}
			}
		}
	}
	
	public void setDestroyers2(int destroyers2) {
		this.destroyers2 = destroyers2;
	}

	public String getPlayer1() {
		return player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public int getDestroyers2() {
		return destroyers2;
	}

	/**
	 * Emulates an old CLI Console and prints a message with delay
	 * @param message Message to print to the console
	 */
	private void animateMessage(String message) {
		System.out.println();
		for(int i = 0; i < message.length(); i++) {
			System.out.print(message.substring(i, i + 1));
			sleep(20);
		}
	}
	
	/**
	 * See 'animateMessage()' - Prints in red
	 * @param message
	 */
	private void animateErrorMessage(String message) {
		System.out.println();
		for(int i = 0; i < message.length(); i++) {
			System.err.print(message.substring(i, i + 1));
			sleep(20);
		}
	}
	
	private void clearScreen() {
		for (int i = 0; i < 100; i++) {
			System.out.println("\n");
		}
	}
	
	/**
	 * Adds a new IShip into the 2D-Array of Cell Objects
	 * @param ship Ship To Add
	 * @param shipX 1st Dimension Array Index
	 * @param shipY 2nd Dimension Index Inside Array
	 * @param player Player whom the ship belongs too
	 * @param shipNo Index of iteration of configure ship method
	 */
	private void addShip(IShip ship, int shipX, int shipY, int player, int shipNo) {
		boolean correctCoordinate = false;
		while(!correctCoordinate) {
			if (cells[shipX][shipY] == null) { //If null, there's no ship, check half.
				if (player == 1 && shipY < (cols / 2) && shipY >= 0 && shipX < rows && shipX >= 0) {
					cells[shipX][shipY] = new Cell(ship);
					ship.setX(shipX); //Update Object X
					ship.setY(shipY); //Update Object Y
					ship.setPlayer(player); //Update Object Player
					if (player == 1) {
						ship.setPlayerName(player1); //Update Object Player Name
					}
					correctCoordinate = true;
				} else if (player == 2 && shipY >= (cols / 2) && shipY < cols && shipX < rows && shipX >= 0) {
					cells[shipX][shipY] = new Cell(ship);
					ship.setX(shipX); //Update Object X
					ship.setY(shipY); //Update Object Y
					ship.setPlayer(player); //Update Object Player
					ship.setPlayerName(player2); //Update Object Player Name
					correctCoordinate = true;
				} else {
					animateErrorMessage("## ERROR ## - ENSURE SHIP IS IN YOUR HALF\n");
					reconfigureShip(ship, player, shipNo);
				}
			} else { //If not null, there's a ship in it.
				animateErrorMessage("## ERROR ## - CELL TAKEN\n");
				reconfigureShip(ship, player, shipNo);
			}
		}
	}
	
	private void moveShip(IShip shipToMove, int oldX, int oldY, int newX, int newY) {
		//System.out.println("Old Coordinates: (" + oldY + ", " + oldX + ")\nNew Coordinates: (" + newX + ", " + newY + ")");
		cells[oldX][oldY] = null; //Remove the ship
		boolean correctlyMoved = false;
		while(!correctlyMoved) {
			if (cells[newY][newX] == null) {
				//If new cell doesn't have a ship, add it
				cells[newY][newX] = new Cell(shipToMove);
				shipToMove.setX(newY); //Update Coords In Object
				shipToMove.setY(newX);
				correctlyMoved = true;
			} else if (oldX == newX && oldY == newY && cells[newY][newX] == null) {
				animateMessage("You've chosen to keep this ship stationary for this turn.\n");
				cells[newY][newX] = new Cell(shipToMove); //Re-Add Back To Original Cell
				shipToMove.setX(newY); //Update Coords In Object
				shipToMove.setY(newX);
				correctlyMoved = true;
			} else {
				animateErrorMessage("## ERROR ## - CANNOT MOVE : CELL TAKEN\n");
				animateMessage("Please enter the new coordinates for your " + shipToMove.getType());
				String coordinates = userInput.nextLine().replaceAll("\\s", "");
				while(!validateCoordinateFormat(coordinates)) {
					animateErrorMessage("## ERROR ## - INCORRECT COORDINATE FORMAT\n");
					animateMessage("Please enter the new coordinates:\n");
					coordinates = userInput.nextLine().replaceAll("\\s", "");
				}
				newX = parseX(coordinates); //Update local var ordinates
				newY = parseY(coordinates);
			}
		}
	}
	
	private void compareShipLengths(IShip centralShip, IShip outerShip, int xOffset, int yOffset) {
		int x = centralShip.getY(); //Object Ordinates Are Inverted.
		int y = centralShip.getX();
		int centralShipPlayer = centralShip.getPlayer();
		int outerShipPlayer = outerShip.getPlayer();
		
		if (centralShip.getType().getLength() > outerShip.getType().getLength() && centralShipPlayer != outerShipPlayer) {
			//If the shipToCheck's length is greater than it's neighbour, destroy it
			IShip destroyedShip = cells[y + yOffset][x + xOffset].getShip();
			String destroyedShipPlayerName = destroyedShip.getPlayerName();	//Get players name before its set to null	
			String destroyedShipType = String.valueOf(destroyedShip.getType()).toLowerCase(); //and its type
			cells[y + yOffset][x + xOffset] = null; //Destroy Neighbouring Ship	
			decrementShipCount(destroyedShip, destroyedShip.getPlayer());
			takeSnapshot(); //Update Snapshot
			animateMessage(destroyedShipPlayerName + ", your " + destroyedShipType + " has been destroyed!.\n");
		} else if (outerShip.getType().getLength() > centralShip.getType().getLength() && centralShipPlayer != outerShipPlayer) {	
			//If the neighbouring ship's length is greater than the shipToChecks length, destroy it
			String destroyedShipPlayerName = centralShip.getPlayerName();
			String destroyedShipType = String.valueOf(centralShip.getType()).toLowerCase();
			cells[x][y] = null; //Destroy Central Ship
			takeSnapshot(); //Update Snapshot
			decrementShipCount(cells[x][y].getShip(), centralShipPlayer); //-1 from shipcount
			animateMessage(destroyedShipPlayerName + ", your " + destroyedShipType + " has been destroyed!.\n");
		} else if (outerShip.getType().getLength() == centralShip.getType().getLength() && centralShipPlayer != outerShipPlayer) {
			//If the neighbouring ship and the shipToCheck have the same length
			IShip neighbourShip = cells[x + xOffset][y + yOffset].getShip();
			String neighbourShipPlayerName = neighbourShip.getPlayerName();
			String checkedShipPlayerName = centralShip.getPlayerName();
			String neighbourShipType = String.valueOf(neighbourShip.getType()).toLowerCase();
			String checkedShipType = String.valueOf(centralShip.getType()).toLowerCase();
			cells[y + yOffset][x + xOffset] = null; //Destroy Neighbour Ship
			cells[x][y] = null; //Destroy Checked Ship
			decrementShipCount(neighbourShip, neighbourShip.getPlayer()); 
			decrementShipCount(cells[x][y].getShip(), centralShipPlayer);
			takeSnapshot(); //Update Snapshot
			animateMessage(neighbourShipPlayerName + ", your " + neighbourShipType + " has been destroyed!\n");
			animateMessage(checkedShipPlayerName + ", your " + checkedShipType + " has been destroyed!\n");
		}
	}
	
	private void checkNeighbouringCells(IShip shipToCheck) {
		int x = shipToCheck.getY(); //Object Ordinates Are Inverted.
		int y = shipToCheck.getX();
		
		try {		
			//Left Column (Excluding Corners)
			if (x == 0 && y != 0 && y < rows - 1) {
				//Top Middle - (x, y-1)
				if (cells[y - 1][x] != null) {
					IShip outerShip = cells[y - 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, -1);
				}
				
				//Top Right - (x+1, y-1)
				if (cells[y - 1][x + 1] != null) {
					IShip outerShip = cells[y - 1][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, -1);
				}
				
				//Middle Right - (x+1, y)
				if (cells[y][x + 1] != null) {
					IShip outerShip = cells[y][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, 0);
				}
				
				//Bottom Middle - (x, y+1)
				if (cells[y + 1][x] != null) {
					IShip outerShip = cells[y + 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, 1);
				}
				
				//Bottom Right - (x+1,y+1)
				if (cells[y + 1][x + 1] != null) {
					IShip outerShip = cells[y + 1][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, 1);
				}
			//Right Column (Excluding Corners)
			} else if (x == cols - 1 && x != cols - 1 && y < rows - 1) {
				//Top Left - (x-1, y-1)
				if (cells[y - 1][x - 1] != null) {
					IShip outerShip = cells[y - 1][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, -1);
				}
				
				//Top Middle - (x, y-1)
				if (cells[y - 1][x] != null) {
					IShip outerShip = cells[y - 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, -1);
				}
				
				//Middle Left - (x-1, y)
				if (cells[y][x - 1] != null) {
					IShip outerShip = cells[y][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, 0);
				}
				
				//Bottom Left - (x-1, y+1)
				if (cells[y + 1][x - 1] != null) {
					IShip outerShip = cells[y + 1][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, 1);
				}
				
				//Bottom Middle - (x, y+1)
				if (cells[y + 1][x] != null) {
					IShip outerShip = cells[y + 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, 1);
				}
			//Top Row (Excluding Corners)
			} else if (y == 0 && x != 0 && x < cols - 1) {
				//Bottom Left - (x-1, y+1)
				if (cells[y + 1][x - 1] != null) {
					IShip outerShip = cells[y + 1][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, 1);
				}
				
				//Bottom Middle - (x, y+1)
				if (cells[y + 1][x] != null) {
					IShip outerShip = cells[y + 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, 1);
				}
				
				//Bottom Right - (x+1,y+1)
				if (cells[y + 1][x + 1] != null) {
					IShip outerShip = cells[y + 1][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, 1);
				}
				
				//Middle Left - (x-1, y)
				if (cells[y][x - 1] != null) {
					IShip outerShip = cells[y][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, 0);
				}
				
				//Middle Right - (x+1, y)
				if (cells[y][x + 1] != null) {
					IShip outerShip = cells[y][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, 0);
				}
			//Bottom Row (Excluding Corners)
			} else if (y == rows - 1 && x != 0 && x < cols - 1) {
				//Top Left - (x-1, y-1)
				if (cells[y - 1][x - 1] != null) {
					IShip outerShip = cells[y - 1][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, -1);
				}
				
				//Top Middle - (x, y-1)
				if (cells[y - 1][x] != null) {
					IShip outerShip = cells[y - 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, -1);
				}
				
				//Top Right - (x+1, y-1)
				if (cells[y - 1][x + 1] != null) {
					IShip outerShip = cells[y - 1][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, -1);
				}
				
				//Middle Left - (x-1, y)
				if (cells[y][x - 1] != null) {
					IShip outerShip = cells[y][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, 0);
				}
				
				//Middle Right - (x+1, y)
				if (cells[y][x + 1] != null) {
					IShip outerShip = cells[y][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, 0);
				}
			//Top Left Corner
			} else if (x == 0 && y == 0) {
				//Middle Right - (x+1, y)
				if (cells[y][x + 1] != null) {
					IShip outerShip = cells[y][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, 0);
				}
				
				//Bottom Middle - (x, y+1)
				if (cells[y + 1][x] != null) {
					IShip outerShip = cells[y + 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, 1);
				}
				
				//Bottom Right - (x+1,y+1)
				if (cells[y + 1][x + 1] != null) {
					IShip outerShip = cells[y + 1][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, 1);
				}
			//Top Right Corner
			} else if (x == cols - 1 && y == 0) {
				//Middle Left - (x-1, y)
				if (cells[y][x - 1] != null) {
					IShip outerShip = cells[y][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, 0);
				}
				
				//Bottom Left - (x-1, y+1)
				if (cells[y + 1][x - 1] != null) {
					IShip outerShip = cells[y + 1][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, 1);
				}
				
				//Bottom Middle - (x, y+1)
				if (cells[y + 1][x] != null) {
					IShip outerShip = cells[y + 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, 1);
				}
			//Bottom Left Corner
			} else if (x == 0 && y == rows - 1) {
				//Top Middle - (x, y-1)
				if (cells[y - 1][x] != null) {
					IShip outerShip = cells[y - 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, -1);
				}
				
				//Top Right - (x+1, y-1)
				if (cells[y - 1][x + 1] != null) {
					IShip outerShip = cells[y - 1][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, -1);
				}
				
				//Middle Right - (x+1, y)
				if (cells[y][x + 1] != null) {
					IShip outerShip = cells[y][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, 0);
				}
			//Bottom Right Corner
			} else if (x == cols - 1 && y == rows - 1) {
				//Top Left - (x-1, y-1)
				if (cells[y - 1][x - 1] != null) {
					IShip outerShip = cells[y - 1][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, -1);
				}
				
				//Top Middle - (x, y-1)
				if (cells[y - 1][x] != null) {
					IShip outerShip = cells[y - 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, -1);
				}
				
				//Middle Left - (x-1, y)
				if (cells[y][x - 1] != null) {
					IShip outerShip = cells[y][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, 0);
				}
			//Not On Edge OR Corner (Check All Around)
			} else {
				//Top Left - (x-1, y-1)
				if (cells[y - 1][x - 1] != null) {
					IShip outerShip = cells[y - 1][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, -1);
				}
				
				//Top Middle - (x, y-1)
				if (cells[y - 1][x] != null) {
					IShip outerShip = cells[y - 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, -1);
				}
				
				//Top Right - (x+1, y-1)
				if (cells[y - 1][x + 1] != null) {
					IShip outerShip = cells[y - 1][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, -1);
				}
				
				//Middle Left - (x-1, y)
				if (cells[y][x - 1] != null) {
					IShip outerShip = cells[y][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, 0);
				}
				
				//Middle Right - (x+1, y)
				if (cells[y][x + 1] != null) {
					IShip outerShip = cells[y][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, 0);
				}
				
				//Bottom Left - (x-1, y+1)
				if (cells[y + 1][x - 1] != null) {
					IShip outerShip = cells[y + 1][x - 1].getShip();
					compareShipLengths(shipToCheck, outerShip, -1, 1);
				}
				
				//Bottom Middle - (x, y+1)
				if (cells[y + 1][x] != null) {
					IShip outerShip = cells[y + 1][x].getShip();
					compareShipLengths(shipToCheck, outerShip, 0, 1);
				}
				
				//Bottom Right - (x+1,y+1)
				if (cells[y + 1][x + 1] != null) {
					IShip outerShip = cells[y + 1][x + 1].getShip();
					compareShipLengths(shipToCheck, outerShip, 1, 1);
				}
			}			
		} catch (ArrayIndexOutOfBoundsException e) {
			//Do Nothing
		}
	}
	
	/**
	 * Re-Invokes the correct configureShip methods depending on which ship
	 * threw and error
	 * @param ship Ship that threw the error
	 * @param player Player whom said ship belongs too
	 * @param shipNo Iteration of configure ship method
	 */
	private void reconfigureShip(IShip ship, int player, int shipNo) {
		if (String.valueOf(ship.getType()).equalsIgnoreCase("DESTROYER")) {
			if(player == 1) {
				configureDestroyer(1, shipNo);
			} else if (player == 2) {
				configureDestroyer(2, shipNo);
			}
		} else if (String.valueOf(ship.getType()).equalsIgnoreCase("BATTLESHIP")) {
			if(player == 1) {
				configureBattleship(1, shipNo);
			} else if (player == 2) {
				configureBattleship(2, shipNo);
			}
		} else if (String.valueOf(ship.getType()).equalsIgnoreCase("SUBMARINE")) {
			if(player == 1) {
				configureSubmarine(1, shipNo);
			} else if (player == 2) {
				configureSubmarine(2, shipNo);
			}
		} else if (String.valueOf(ship.getType()).equalsIgnoreCase("MINELAYER")) {
			if(player == 1) {
				configureMineLayer(1, shipNo);
			} else if (player == 2) {
				configureMineLayer(2, shipNo);
			}
		} else if (String.valueOf(ship.getType()).equalsIgnoreCase("MINE")) {
			if(player == 1) {
				configureMine(1, shipNo);
			} else if (player == 2) {
				configureMine(2, shipNo);
			}
		}
	}
	
	private void printAllShipsSnapshot() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (cellsSnapshot[x][y] != null) {
					System.out.println("Ship: " + cellsSnapshot[x][y].getShip().getType() + "@ CellsSnapshot[" + y + "][" + x + "]");
				} else {
					System.out.println("No Ship @ CellsSnapshot[" + y + "][" + x + "]");
				}
				y++;
			}
			x++;
			y = 0;
		}
	}
	
	private void printAllShips() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (cells[x][y] != null) {
					System.out.println("Ship: " + cells[x][y].getShip().getType() + "@ Cells[" + y + "][" + x + "]");
				} else {
					System.out.println("No Ship @ Cells[" + y + "][" + x + "]");
				}
				y++;
			}
			x++;
			y = 0;
		}
	}
	
	private IShip getPlayerShipViaIndex(int index, int player) {
		int x = 0;
		int y = 0;
		int count = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				//If it has a ship, belongs to the passed player, and is not a mine.
				if (cellsSnapshot[y][x] != null && cellsSnapshot[y][x].getShip().getPlayer() == player && !cellsSnapshot[y][x].getShip().getType().toString().equalsIgnoreCase("MINE")) {
					if (index == count) {
						return cellsSnapshot[y][x].getShip();
					}
					count++; //Everytime the loop finds a ship belonging to the correct player, increment count
				}
				y++; //Increment y
			}
			y = 0; //Reset y
			x++; //Increment x
		}
		return null;
	}
	
	private void takeSnapshot() {
		int xCount = 0;
		int yCount = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (cells[xCount][yCount] != null) {
					IShip copyingFrom = cells[xCount][yCount].getShip(); //Ship to copy from, inside cells
					IShip snapshotShip = shipFactory.createShip(copyingFrom.getType().toString(), copyingFrom.getPlayer()); //new ship to add to snapshot
					snapshotShip.setX(copyingFrom.getX()); //update all its fields
					snapshotShip.setY(copyingFrom.getY());
					snapshotShip.setPlayer(copyingFrom.getPlayer());
					snapshotShip.setPlayerName(copyingFrom.getPlayerName());
					cellsSnapshot[xCount][yCount] = new Cell(snapshotShip); //add copied ship to snapshot
				}
				xCount++; //Increment X
			}
			xCount = 0; //Reset X
			yCount++; //Increment Y
		}
	}
	
	private void decrementShipCount(IShip ship, int player) {
		if (ship.getType().toString().equalsIgnoreCase("BATTLESHIP")) {
			if (player == 1) {
				battleships--;
			} else {
				battleships2--;
			}
		} else if (ship.getType().toString().equalsIgnoreCase("DESTROYER")) {
			if (player == 1) {
				destroyers--;
			} else {
				destroyers2--;
			}
		} else if (ship.getType().toString().equalsIgnoreCase("SUBMARINE")) {
			if (player == 1) {
				submarines--;
			} else {
				submarines2--;
			}
		} else if (ship.getType().toString().equalsIgnoreCase("MINELAYER")) {
			if (player == 1) {
				minelayers--;
			} else {
				minelayers2--;
			}
		}
	}
	
	/**
	 * Takes a String, Breaks it down into the x-ordinate and returns it as 
	 * an Integer.
	 * @param userInput String containing a validate set of coordinates
	 * @return 1 or 2 digit x-ordinate
	 */
	public Integer parseX(String userInput) {
		if (userInput.length() == 3) {
			return Integer.valueOf(userInput.substring(0, 1)); //If String is 3 characters, x is just index 0
		} else if (userInput.length() == 4) {
			String firstChar = userInput.substring(0, 1);
			String secondChar = userInput.substring(1, 2);
			if (Character.isDigit(firstChar.charAt(0)) && Character.isDigit(secondChar.charAt(0))) {
				return Integer.valueOf(userInput.substring(0, 2)); //If first 2 chars are digits, return indices 0 & 1
			} else {
				return Integer.valueOf(userInput.substring(0, 1)); //If not, return just the first, it means y is double digits
			}
		} else if (userInput.length() == 5) {
			return Integer.valueOf(userInput.substring(0, 2)); //If x and y are double digits, return indices 0 & 1
		}
		return null;
	}
	
	/**
	 * Takes a String, Breaks it down into the y-ordinate and returns it as 
	 * an Integer.
	 * @param userInput String containing a validate set of coordinates
	 * @return 1 or 2 digit y-ordinate
	 */
	public Integer parseY(String userInput) {
		if (userInput.length() == 3) {
			return Integer.valueOf(userInput.substring(2, 3)); //If String is 3 characters, x is just index 2
		} else if (userInput.length() == 4) {
			String thirdChar = userInput.substring(2, 3);
			String fourthChar = userInput.substring(3, 4);
			if (Character.isDigit(thirdChar.charAt(0)) && Character.isDigit(fourthChar.charAt(0))) {
				return Integer.valueOf(userInput.substring(2, 4)); //If last 2 chars are digits, return indices 2 & 3
			} else {
				return Integer.valueOf(userInput.substring(3, 4)); //If not, return just the third, it means x is double digits
			} 
		} else if (userInput.length() == 5) {
			return Integer.valueOf(userInput.substring(3, 5)); //If x and y are double digits, return indices 3 & 4
		}
		return null;
	}
	
	/**
	 * Takes an integer, calculates which ordinal suffix it needs.
	 * @param number A Positive Integer
	 * @return Correct Ordinal Suffix
	 */
	private String calculateNumberSuffix(int number) {
		if (number > 3 && number < 21) {
			return "th";
		} else if (number == 1) {
			return "st";
		} else if (number == 2) {
			return "nd";
		} else if (number == 3) {
			return "rd";
		} else {
			switch (number % 10) {
			case 1:
				return "st";
			case 2:
				return "nd";
			case 3: 
				return "rd";
			default:
				return "th";
			}
		}
	}
	
	/**
	 * Takes a String and verifies that it the correct format. Given the 
	 * row and column restrictions, a maximum of 2 digits can be used per
	 * ordinate. The possible formats are (x,y), (xx,y), (x,yy), (xx,yy).
	 * @param coordinates String to be vaidated
	 * @return true if correct format, otherwise false
	 */
	private boolean validateCoordinateFormat(String coordinates) {
		Character first, second, third, fourth, fifth;
		Character comma = new String(",").charAt(0);
		if (coordinates.length() < 3) {
			return false;
		}
		if (coordinates.length() > 2) {
			first = coordinates.substring(0, 1).charAt(0);
			second = coordinates.substring(1, 2).charAt(0);
			third = coordinates.substring(2, 3).charAt(0);
		} else {
			first = null;
			second = null;
			third = null;
		}
		if (coordinates.length() > 3) {
			fourth = coordinates.substring(3, 4).charAt(0);
		} else {
			fourth = null;
		}
		if (coordinates.length() > 5 && coordinates.length() < 7) {
			fifth = coordinates.substring(4, 5).charAt(0);
		} else {
			fifth = null;
		}
		if (Character.isDigit(first) && second == comma  && Character.isDigit(third)) {
			return true; //If Format is (x,y)
		} else if (Character.isDigit(first) && second == comma && Character.isDigit(third) && Character.isDigit(fourth)) {
			return true; //If Format is(x,yy)
		} else if (Character.isDigit(first) && Character.isDigit(second) && second == comma && Character.isDigit(fourth)) {
			return true; //If Format is (xx,y)
		} else if (Character.isDigit(first) && Character.isDigit(second) && second == comma && Character.isDigit(fourth) && Character.isDigit(fifth)) {
			return true; //If Format is (xx,yy)
		} else {
			return false; //If Format is incorrect
		}
	}
	
	/**
	 * Animates a welcome message in the console
	 */
	private void welcomeMessage() {
		String message = "--------------------------------*- Welcome To Battleships -*--------------------------------";
		for(int i = 0; i < message.length(); i++) {
			if (i == 30 || i == 61) {
				System.out.println();
				sleep(20);
			} else {
				System.out.print(message.substring(i, i + 1));
				sleep(20);
			}
		}
		System.out.println();
	}
	
	private boolean gameOver() {
		int playerOneShips = 0;
		int playerTwoShips = 0;
		int x = 0;
		int y = 0;
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				if (cells[x][y] != null && cells[x][y].getShip().getPlayer() == 1) {
					playerOneShips++;
				} else if (cells[x][y] != null && cells[x][y].getShip().getPlayer() == 2) {
					playerTwoShips++;
				}
				y++;
			}
			y = 0;
			x++;
		}
		if (playerOneShips > 0 && playerTwoShips == 0) {
			animateMessage(player1 + "Wins!");
			return true;
		} else if (playerTwoShips > 0 && playerOneShips == 0){
			animateMessage(player2 + "Wins!");
			return true;
		} else if (playerOneShips > 0 && playerTwoShips > 0) {
			return false;
		} else {
			animateMessage("TOTAL ANNIHILATION! IT'S A DRAW.");
			return true;
		}
	}
	
	/**
	 * Gets the ShipType from a specified Cell and returns a single digit String
	 * to represent the Ship on the Board.
	 * @param x X-Ordinate
	 * @param y Y-Ordinate
	 * @return Single Digit String representing the Ship
	 */
	private String getShipTypeFromCell(int x, int y) {
		if (cells[x][y] != null) {
			if (String.valueOf(cells[x][y].getShip().getType()) == "BATTLESHIP") {
				return "B";
			} else if (String.valueOf(cells[x][y].getShip().getType()) == "DESTROYER") {
				return "D";
			} else if (String.valueOf(cells[x][y].getShip().getType()) == "SUBMARINE") {
				return "S";
			} else if (String.valueOf(cells[x][y].getShip().getType()) == "MINELAYER") {
				return "M";
			} else if (String.valueOf(cells[x][y].getShip().getType()) == "MINE") {
				return "*";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Prints an animated elipsis to the console
	 */
	private void loading() {
		String elipsis = "...";
		for (int i = 0; i < elipsis.length(); i++) {
			System.out.print(elipsis.substring(i, i + 1));
			sleep(500);
		}
		System.out.println();
	}
	
	/**
	 * Pauses the execution of the current thread by the specified duration
	 * @param duration Duration to pause for in milliseconds
	 */
	private void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the number of rows to the specified int
	 * @param rows Number of Board Rows
	 */
	public void setRows(int rows) {
		boolean correctFormat = false;
		while(!correctFormat) {
			try {
				if (rows >= 4 && rows <= 26) {
					this.rows = rows;
					correctFormat = true;
				} 
			} catch (NumberFormatException e) {
				System.out.println("Rows must be greater than 3 and less than 27.");
			}
		}
	}
	
	/**
	 * Sets the number of columns to the specified int
	 * @param cols Number of Board Columns
	 */
	public void setCols(int cols) {
		if (cols <= 26) {
			this.cols = cols;
		} else {
			System.out.println("\nMaximum Number of Columns is 26");
		}
	}
	
	/**
	 * Returns the number of board rows
	 * @return Number of Rows
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * Returns the number of board columns
	 * @return Number of Columns
	 */
	public int getCols() {
		return cols;
	}
	
	/**
	 * Sets Player 1's Name to the specified String
	 * @param player1 Player 1's Name
	 */
	public void setPlayer1(String player1) {
		this.player1 = player1;
	}
	
	/**
	 * Sets Player 2's Name to the specified String
	 * @param player2 Player 2's Name
	 */
	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
}
