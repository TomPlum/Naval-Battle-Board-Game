package boardUtilitiesTests;

import static org.junit.Assert.*;

import org.junit.Test;

import boardUtilities.Board;

public class TestConflict {

	@Test
	public void testBattleshipVsDestroyer() {
		Board board = new Board();
		
		//Instructions:
		//1. Set Player Names
		//2. Set 10 Rows & 10 Columns
		//3. Both Players Have 1 Of Each Ship
		//4. P1 Battleship @ (0,0)
		//5. P1 Destroyer @ (0,1)
		//6. P1 Submarine @ (0,2)
		//7. P1 MineLayer @ (0,3)
		//8. P1 Mine @ (0,4)
		//9. P2 Battleship @ (9,0)
		//10. P2 Destroyer @ (9,1)
		//11. P2 Submarine @ (9,2)
		//12. P2 MineLayer @ (9,3)
		//13. P2 Mine @ (9,4)
		//14. Move P1 Battleship To (4,0)
		//15. Move P1 Destroyer To (0,0)
		//16. Move P1 Submarine To (0,1)
		//17. Move P1 MineLayer To (0,2)
		//18. Move P2 Battleship To (9,5)
		//19. Move P2 Destroyer To (5,0)
		//20. Move P2 Submarine To (9,0)
		//21. Move P2 MineLayer To (9,1)
		//22. END OF BOTH TURNS.
		//23. Player 1's Battleship Should Destroyer Player 2's Destroyer
		
		//destroyer2 will = 1
		//After the conflict it should = 0 as it is destroyed
		int expected = 0;
		assertEquals(expected, board.getDestroyers2());
	}

}
