package tests;
import static org.junit.Assert.*;

import java.awt.Color;
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
		ArrayList<Player> players = board.getPlayerList();

		// 1st character and also human player
		assertEquals("Michael Scott", players.get(0).getName()); //name
		assertEquals(Color.BLACK, players.get(0).getColor()); //color
		assertEquals(3, players.get(0).getRow()); //row
		assertEquals(0, players.get(0).getColumn()); //column
		assertTrue(players.get(0).isHuman()); //is the human player
		
		// 3rd character and also computer player
		assertEquals("Jim Halpert", players.get(2).getName()); //name
		assertEquals(Color.BLUE, players.get(2).getColor()); //color
		assertEquals(0, players.get(2).getRow()); //row
		assertEquals(13, players.get(2).getColumn()); //column
		assertTrue(players.get(2).isComputer()); //is the computer player
		
		// last character
		assertEquals("Angela Martin", players.get(7).getName()); //name
		assertEquals(Color.MAGENTA, players.get(7).getColor()); //color
		assertEquals(11, players.get(7).getRow()); //row 
		assertEquals(0, players.get(7).getColumn()); //column
	}
}
