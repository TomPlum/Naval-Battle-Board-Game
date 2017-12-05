package boardUtilitiesTests;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;
import boardUtilities.Board;
public class TestOrdinateParsing {

	@Test
	public void testParseX() {
		Random rand = new Random();
		Integer x = rand.nextInt(26); //0-25
		Integer y = rand.nextInt(26); //0-25
		String testCoordinates = (x + ", " + y).replaceAll("\\s", "");
		Integer parsedX = new Board().parseX(testCoordinates);
		assertEquals(x, parsedX);
	}
	
	@Test
	public void testParseY() {
		Random rand = new Random();
		Integer x = rand.nextInt(26); //0-25
		Integer y = rand.nextInt(26); //0-25
		String testCoordinates = (x + ", " + y).replaceAll("\\s", "");
		Integer parsedY = new Board().parseY(testCoordinates);
		assertEquals(y, parsedY);
	}
}
