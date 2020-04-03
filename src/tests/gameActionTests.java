package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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


	//I'm a little bit confused about this test
	//I tried picking a cell and giving it a path length, and then checking targets but it's failing and I'm not sure why
	@Test
	public void testSelectingATarget() {
		board.calcTargets(8, 18, 2);

		Set<BoardCell> targets= board.getTargets();
		// testing the calculated targets
		assertTrue(targets.contains(board.getCellAt(8, 20)));
		assertTrue(targets.contains(board.getCellAt(6, 18)));
		// Last target tested is doorway 
		assertTrue(targets.contains(board.getCellAt(7, 19)));

		// need to call ComputerPlayer with the pickLocation function and return the selected position
		ComputerPlayer computer = new ComputerPlayer();
		BoardCell selected = computer.pickLocation(targets);
		assertEquals(board.getCellAt(13, 19), selected);

	}

	@Test
	public void testTargetRandomSelection() {
		ComputerPlayer player = new ComputerPlayer();
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(21, 14, 2);
		boolean loc_19_14 = false;
		boolean loc_20_15 = false;
		// Run the test a large number of times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(19, 14)) {
				loc_19_14 = true;
			}
			else if (selected == board.getCellAt(20, 15)) {
				loc_20_15 = true;
			}
			else {
				//Not sure why it it accessing an invalid target, because I chose the cell I did so we only had to check 2 targets
				//Look at Clue Player pdf and see if I'm missing something
				fail("Invalid target selected");
			}
		}
		// Ensure each target was selected at least once
		assertTrue(loc_19_14);
		assertTrue(loc_20_15);
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
