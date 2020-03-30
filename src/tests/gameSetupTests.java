package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class gameSetupTests {
	//Copied the test beforeclass method from CTEST
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("OurClueLayout.csv", "OurClueLegend.txt");	
		// set the file names to use my config files
		board.setCardFiles("Players.txt", "Cards.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public void testLoadingPeople() {
		// all players will be in an ArrayList
		// should this be in @Before?
		ArrayList<Player> player = board.getPlayerList();

		// 1st character and also human player
		assertEquals("Micheal Scott", player.get(0).playerName); //name
		assertEquals("black", player.get(0).color); //color, not sure if syntax is correct
		assertEquals(3, player.get(0).row); //row
		assertEquals(0, player.get(0).column); //column
		assertTrue(player.get(0).isHuman()); //is the human player
		
		// 3rd character and also computer player
		assertEquals("Jim Halpert", player.get(2).playerName); //name
		assertEquals("blue", player.get(2).color); //color
		assertEquals(0, player.get(2).row); //row
		assertEquals(13, player.get(2).column); //column
		assertTrue(player.get(0).isComputer()); //is the computer player
		
		// last character
		assertEquals("Angela Martin", player.get(7).playerName); //name
		assertEquals("purple", player.get(7).color); //color
		assertEquals(11, player.get(7).row); //row 
		assertEquals(0, player.get(7).column); //column
	}
}
