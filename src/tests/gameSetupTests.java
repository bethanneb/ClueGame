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
		ArrayList<Player> player = board.getPlayerList();
		
		// human character
		
		// computer character
		
		// 1st character
		assertEquals("Micheal Scott", player.get(1).playerName);
		// 3rd character
		
		// last character
	}
}
