package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

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


	//If a player is not in a room, and a room is possible target, it goes into that room
	@Test
	public void testGoesIntoRoom() {
		//Computer is in place where it can go into room
		ComputerPlayer player = new ComputerPlayer("Jim Halbert", 2,9, Color.blue);
		
		//calculate targets
		board.calcTargets(2, 9, 1);
		
		//test that it actually goes into room
		boolean loc_2_8 = false;
		BoardCell selected = player.pickLocation(board.getTargets());
		//if it selects the room
		if (selected == board.getCellAt(2, 8)) {
			loc_2_8 = true;
		}
		else {
			fail("Did not go into room");
		}
		
		//went into the room
		assertTrue(loc_2_8);
	}
	
	//If a player is not in a room and does not have the option of a room, all spots are equally likely through random pick
	@Test
	public void testTargetRandomSelection() {
		//Computer player is in walkway 
		ComputerPlayer player = new ComputerPlayer("Jim Halbert", 9,1, Color.blue);
		
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(9, 1, 1);
		
		boolean loc_9_0 = false;
		boolean loc_8_1 = false;
		boolean loc_10_1 = false;
		boolean loc_9_2 = false;
		
		// Run the test a large number of times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(9, 0)) {
				loc_9_0 = true;
			}
			else if (selected == board.getCellAt(8, 1)) {
				loc_8_1 = true;
			}
			else if (selected == board.getCellAt(10, 1)) {
				loc_10_1 = true;
			}
			else if (selected == board.getCellAt(9, 2)) {
				loc_9_2 = true;
			}
			else {
				//Not sure why it it accessing an invalid target, because I chose the cell I did so we only had to check 2 targets
				//Look at Clue Player pdf and see if I'm missing something
				fail("Invalid target selected");
			}
		}
		// Ensure each target was selected at least once
		assertTrue(loc_9_0);
		assertTrue(loc_8_1);
		assertTrue(loc_10_1);
		assertTrue(loc_9_2);
	}
	
	//If a player is in a room, all spots are equally likely through random pick
		@Test
		public void testTargetRandomSelectionInRoom() {
			//Computer player is in walkway 
			ComputerPlayer player = new ComputerPlayer("Jim Halbert", 1,5, Color.blue);
			
			// Pick a location with four equally likely targets
			board.calcTargets(1, 5, 1);
			
			boolean loc_0_5 = false;
			boolean loc_1_4 = false;
			boolean loc_2_5 = false;
			boolean loc_1_6 = false;
			
			// Run the test a large number of times
			for (int i=0; i<100; i++) {
				BoardCell selected = player.pickLocation(board.getTargets());
				if (selected == board.getCellAt(0, 5)) {
					loc_0_5 = true;
				}
				else if (selected == board.getCellAt(1, 4)) {
					loc_1_4 = true;
				}
				else if (selected == board.getCellAt(2, 5)) {
					loc_2_5 = true;
				}
				else if (selected == board.getCellAt(1, 6)) {
					loc_1_6 = true;
				}
				else {
					//Not sure why it it accessing an invalid target, because I chose the cell I did so we only had to check 2 targets
					//Look at Clue Player pdf and see if I'm missing something
					fail("Invalid target selected");
				}
			}
			// Ensure each target was selected at least once
			assertTrue(loc_0_5);
			assertTrue(loc_1_4);
			assertTrue(loc_2_5);
			assertTrue(loc_1_6);
		}
		
	@Test 
	public void testAccusation() {
		Solution answerKey = new Solution(); 
		String ansP = answerKey.getPerson(); 
		String ansW = answerKey.getWeapon(); 
		String ansR = answerKey.getRoom(); 

		Solution accusation = new Solution();

		// Solution that is correct 
		accusation.setAnswerKeyPerson(ansP);
		accusation.setAnswerKeyWeapon(ansW); 
		accusation.setAnswerKeyRoom(ansR); 
		

		assertTrue(board.checkAccusation(accusation, answerKey));

		// Solution with wrong person 
		accusation.setAnswerKeyPerson("wrong");

		assertFalse(board.checkAccusation(accusation, answerKey));

		// Solution with wrong weapon 
		accusation.setAnswerKeyPerson(ansP);
		accusation.setAnswerKeyWeapon("wrong");

		assertFalse(board.checkAccusation(accusation, answerKey)); 

		// Solution with wrong room 
		accusation.setAnswerKeyWeapon(ansW);
		accusation.setAnswerKeyRoom("wrong");

		assertFalse(board.checkAccusation(accusation, answerKey)); 

	}
	
}
