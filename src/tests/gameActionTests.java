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
		ComputerPlayer player = new ComputerPlayer("Jim Halpert", 2,9, Color.blue);
		
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
		ComputerPlayer player = new ComputerPlayer("Jim Halpert", 9,1, Color.blue);
		
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
			Solution answerKey = board.getAnswerKey(); 
			String ansP = answerKey.getPerson(); 
			String ansW = answerKey.getWeapon(); 
			String ansR = answerKey.getRoom(); 

			Solution accusation = new Solution();

			// Solution that is correct 
			accusation.setAnswerKeyPerson(ansP);
			accusation.setAnswerKeyWeapon(ansW); 
			accusation.setAnswerKeyRoom(ansR); 

			assertTrue(board.checkAccusation(accusation));

			// Solution with wrong person 
			accusation.setAnswerKeyPerson("wrong");

			assertFalse(board.checkAccusation(accusation));

			// Solution with wrong weapon 
			accusation.setAnswerKeyPerson(ansP);
			accusation.setAnswerKeyWeapon("wrong");

			assertFalse(board.checkAccusation(accusation)); 

			// Solution with wrong room 
			accusation.setAnswerKeyWeapon(ansW);
			accusation.setAnswerKeyRoom("wrong");

			assertFalse(board.checkAccusation(accusation)); 

		}

	@Test
	public void testCreateSuggestion()
	{	
		Card card = new Card("Poison Dart", CardType.WEAPON);
		Card card2 = new Card ("Knife", CardType.WEAPON);
		Card card3 = new Card ("Taser", CardType.WEAPON);
		Card card4 = new Card ("Boomerang", CardType.WEAPON);
		Card card5 = new Card ("Samurai Sword", CardType.WEAPON);
		Card card6 = new Card ("Pepper Spray", CardType.WEAPON);
		Card card7 = new Card ("Crossbow", CardType.WEAPON);
		Card card8 = new Card ("Nunchucks", CardType.WEAPON);

		Card card9 = new Card ("Michael Scott", CardType.PERSON);
		Card card10 = new Card ("Dwight Schrute", CardType.PERSON);
		Card card11 = new Card ("Jim Halpert", CardType.PERSON);
		Card card12 = new Card ("Pam Halpert", CardType.PERSON);
		Card card13 = new Card ("Kevin Malone", CardType.PERSON);
		Card card14 = new Card ("Andy Bernard", CardType.PERSON);
		Card card15 = new Card ("Oscar Martinez", CardType.PERSON);
		Card card16 = new Card ("Angela Martin", CardType.PERSON);

		System.out.println("Rooms size before: " + board.getRooms().size());
		ComputerPlayer computerPlayer = new ComputerPlayer ("Michael Scott","black", 19, 18);
		computerPlayer.createSuggestion(board.getCellAt(19, 18), board.possiblePeople, board.possibleWeapons, board.getRooms(), computerPlayer);
		Solution sol = computerPlayer.getCreatedSoln();
		String solutionRoom = sol.getRoom();
		System.out.println("Sol: " + solutionRoom);
		// room matches current location
		assertEquals("Warehouse", solutionRoom);

		// Making sure null was returned 
		ArrayList<Card> weapons0 = new ArrayList<Card>();
		ArrayList<Card> people0 = new ArrayList<Card>();
		weapons0.add(card);
		weapons0.add(card2);
		weapons0.add(card3);
		weapons0.add(card4);
		weapons0.add(card5);
		weapons0.add(card6);
		people0.add(card7);
		people0.add(card8);
		people0.add(card9);
		people0.add(card10);
		people0.add(card11);
		people0.add(card12);
		computerPlayer.clearSeenCards();
		for ( Card seen : people0)
		{
			computerPlayer.addSeen(seen);
		}
		for ( Card seen : weapons0)
		{
			computerPlayer.addSeen(seen);
		}
		computerPlayer.createSuggestion(board.getCellAt(19, 18), people0, weapons0, board.getRooms(), computerPlayer);
		sol = computerPlayer.getCreatedSoln();
		String solution = sol.getPerson();
		assertNull(solution);

		// If only one person is not seen, it's selected; 
		computerPlayer.clearSeenCards();
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		ArrayList<Card> weapons = new ArrayList<Card>();
		ArrayList<Card> people = new ArrayList<Card>();
		computerPlayer.addSeen(card);
		computerPlayer.addSeen(card2);
		computerPlayer.addSeen(card3);
		computerPlayer.addSeen(card4);
		computerPlayer.addSeen(card5);
		computerPlayer.addSeen(card6);
		computerPlayer.addSeen(card7);
		computerPlayer.addSeen(card8);
		computerPlayer.addSeen(card9);
		computerPlayer.addSeen(card10);
		computerPlayer.addSeen(card11);
		//computerPlayer.addSeen(card12);
		weapons.add(card);
		weapons.add(card2);
		weapons.add(card3);
		weapons.add(card4);
		weapons.add(card5);
		weapons.add(card6);
		people.add(card7);
		people.add(card8);
		people.add(card9);
		people.add(card10);
		people.add(card11);
		people.add(card12);
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		computerPlayer.createSuggestion(board.getCellAt(14, 15), people, weapons, board.getRooms(), computerPlayer);
		sol = computerPlayer.getCreatedSoln();
		solution = sol.getPerson();
		System.out.println(card12.getCardName() + " " + solution);

		assertEquals(card12.getCardName(), solution);

		//If only one weapon not seen, it's selected
		computerPlayer.clearSeenCards();
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		ArrayList<Card> weapons2 = new ArrayList<Card>();
		ArrayList<Card> people2 = new ArrayList<Card>();
		computerPlayer.addSeen(card2);
		computerPlayer.addSeen(card3);
		computerPlayer.addSeen(card4);
		computerPlayer.addSeen(card5);
		computerPlayer.addSeen(card6);
		computerPlayer.addSeen(card7);
		computerPlayer.addSeen(card8);
		computerPlayer.addSeen(card9);
		computerPlayer.addSeen(card10);
		computerPlayer.addSeen(card11);
		computerPlayer.addSeen(card12);
		weapons2.add(card);
		weapons2.add(card2);
		weapons2.add(card3);
		weapons2.add(card4);
		weapons2.add(card5);
		weapons2.add(card6);
		people2.add(card7);
		people2.add(card8);
		people2.add(card9);
		people2.add(card10);
		people2.add(card11);
		people2.add(card12);
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		computerPlayer.createSuggestion(board.getCellAt(14, 15), people2, weapons2, board.getRooms(), computerPlayer);
		sol = computerPlayer.getCreatedSoln();
		solution = sol.getWeapon();
		System.out.println(card.getCardName() + " " + solution);

		assertEquals(card.getCardName(), solution);

		// mutiple choices, people
		computerPlayer.clearSeenCards();
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		ArrayList<Card> weapons3 = new ArrayList<Card>();
		ArrayList<Card> people3 = new ArrayList<Card>();
		computerPlayer.addSeen(card2);
		computerPlayer.addSeen(card3);
		computerPlayer.addSeen(card4);
		computerPlayer.addSeen(card5);
		computerPlayer.addSeen(card6);
		computerPlayer.addSeen(card7);
		computerPlayer.addSeen(card8);
		computerPlayer.addSeen(card9);

		weapons3.add(card);
		weapons3.add(card2);
		weapons3.add(card3);
		weapons3.add(card4);
		weapons3.add(card5);
		weapons3.add(card6);
		people3.add(card7);
		people3.add(card8);
		people3.add(card9);
		people3.add(card10);
		people3.add(card11);
		people3.add(card12);
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		computerPlayer.createSuggestion(board.getCellAt(14, 15), people3, weapons3, board.getRooms(), computerPlayer);
		sol = computerPlayer.getCreatedSoln();
		solution = sol.getPerson();
		System.out.println(solution);

		assertNotNull(solution);

		//multiple choices, weapon
		computerPlayer.clearSeenCards();
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		ArrayList<Card> weapons4 = new ArrayList<Card>();
		ArrayList<Card> people4 = new ArrayList<Card>();

		computerPlayer.addSeen(card5);
		computerPlayer.addSeen(card6);
		computerPlayer.addSeen(card7);
		computerPlayer.addSeen(card8);
		computerPlayer.addSeen(card9);
		computerPlayer.addSeen(card10);
		computerPlayer.addSeen(card11);
		computerPlayer.addSeen(card12);
		weapons4.add(card);
		weapons4.add(card2);
		weapons4.add(card3);
		weapons4.add(card4);
		weapons4.add(card5);
		weapons4.add(card6);
		people4.add(card7);
		people4.add(card8);
		people4.add(card9);
		people4.add(card10);
		people4.add(card11);
		people4.add(card12);
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		computerPlayer.createSuggestion(board.getCellAt(14, 15), people4, weapons4, board.getRooms(), computerPlayer);
		sol = computerPlayer.getCreatedSoln();
		solution = sol.getWeapon();
		System.out.println(solution);

		assertNotNull(solution);


	}
}
