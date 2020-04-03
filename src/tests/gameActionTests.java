package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class gameActionTests {
	//static so that it only has to be loaded one time
	private static Board board;
	//static so we only have one copy of the ArrayList
	private static ArrayList<Player> playersList;

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
		playersList = board.getPlayerList();
	}


	//I'm a little confused by this test 
	//@Test
	public void testComputerPlayerLocation() {
		// One room is exactly 4 away
		board.calcTargets(7, 15, 4);
		// save current location into BoardCell previous; may need or may not
		//BoardCell current =  board.getCellAt(16, 16);
		//Player computer = new Player();
		//computer.savingTestLocation(current);

		// will be passing in targets into the pickLocation function that is called by ComputerPlayer which extends player
		Set<BoardCell> targets= board.getTargets();
		// testing the calculated targets 
		//assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCellAt(11, 15)));
		assertTrue(targets.contains(board.getCellAt(6, 18)));
		// Last target tested is doorway 
		assertTrue(targets.contains(board.getCellAt(10, 16)));

		// need to call ComputerPlayer with the pickLocation function and return the selected position
		ComputerPlayer computer = new ComputerPlayer();
		BoardCell selected = computer.pickLocation(targets);
		assertEquals(board.getCellAt(10, 16), selected);
	}

	@Test
	public void testTargetRandomSelection() {
		ComputerPlayer player = new ComputerPlayer();
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(21, 14, 2);
		boolean loc_19_14 = false;
		boolean loc_20_15 = false;
		boolean loc_14_10 = false;
		// Run the test a large number of times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(19, 14)) {
				loc_19_14 = true;
			}
			else if (selected == board.getCellAt(20, 15)) {
				loc_20_15 = true;
			}
			else if (selected == board.getCellAt(14, 10)) {
				loc_14_10 = true;
			}
			else {
				//Not sure why it it accessing an invalid target
				fail("Invalid target selected");
			}
		}
		// Ensure each target was selected at least once
		assertTrue(loc_19_14);
		assertTrue(loc_20_15);
		assertTrue(loc_14_10);
	}

}
