package boardEntitiesTests;

import static org.junit.Assert.*;

import org.junit.Test;

import boardUtilities.Board;

public class TestSetters {
	
	private Board board = new Board();
	
	@Test
	public void testSetRows() {
		board.setRows(10);
		assertEquals(board.getRows(), 10);
	}
	
	@Test
	public void testSetCols() {
		board.setCols(10);
		assertEquals(board.getCols(), 10);
	}
	
	@Test 
	public void testSetPlayer1() {
		board.setPlayer1("Tom");
		assertEquals(board.getPlayer1(), "Tom");
	}
	
	@Test
	public void testSetPlayer2() {
		board.setPlayer2("Vinesh");
		assertEquals(board.getPlayer2(), "Vinesh");
	}
	
	@Test 
	public void testSetDestroyers2() {
		board.setDestroyers2(1);
		assertEquals(board.getDestroyers2(), 1);
	}
}
