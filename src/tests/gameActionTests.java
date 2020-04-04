package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
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
		ComputerPlayer player = new ComputerPlayer("Jim Halpert", 9,4, Color.blue);
		
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(9, 4, 1);
		
		boolean loc_9_3 = false;
		boolean loc_9_5 = false;
		boolean loc_8_4 = false;
		boolean loc_10_4 = false;
		
		// Run the test a large number of times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(9, 3)) {
				loc_9_3 = true;
			}
			else if (selected == board.getCellAt(9, 5)) {
				loc_9_5 = true;
			}
			else if (selected == board.getCellAt(8, 4)) {
				loc_8_4 = true;
			}
			else if (selected == board.getCellAt(10, 4)) {
				loc_10_4 = true;
			}
			else {
				//Not sure why it it accessing an invalid target, because I chose the cell I did so we only had to check 2 targets
				//Look at Clue Player pdf and see if I'm missing something
				fail("Invalid target selected");
			}
		}
		// Ensure each target was selected at least once
		assertTrue(loc_9_3);
		assertTrue(loc_9_5);
		assertTrue(loc_8_4);
		assertTrue(loc_10_4);
	}
	
	//If a player is in a room, all spots are equally likely through random pick
		@Test
		public void testTargetRandomSelectionInRoom() {
			//Computer player is in walkway 
			ComputerPlayer player = new ComputerPlayer("Jim Halpert", 1,7, Color.blue);
//			Player player = new ComputerPlayer();
//			player.setColumn(1);
//			player.setRow(7);
			board.clearTargets();
			// Pick a location with four equally likely targets
			board.calcTargets(1, 7, 1);
			
			boolean loc_0_7 = false;
			boolean loc_1_6 = false;
			boolean loc_2_7 = false;
			boolean loc_1_8 = false;
			
			// Run the test a large number of times
			
			
			for (int i=0; i<100; i++) {
				BoardCell selected = player.pickLocation(board.getTargets());
				if (selected == board.getCellAt(0, 7)) {
					loc_0_7 = true;
				}
				else if (selected == board.getCellAt(1, 6)) {
					loc_1_6 = true;
				}
				else if (selected == board.getCellAt(2, 7)) {
					loc_2_7 = true;
				}
				else if (selected == board.getCellAt(1, 8)) {
					loc_1_8 = true;
				}
				else {
					//Not sure why it it accessing an invalid target, because I chose the cell I did so we only had to check 2 targets
					//Look at Clue Player pdf and see if I'm missing something
					fail("Invalid target selected");
				}
			}
			// Ensure each target was selected at least once
			assertTrue(loc_0_7);
			assertTrue(loc_1_6);
			assertTrue(loc_2_7);
			assertTrue(loc_1_8);
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
		computerPlayer.createSuggestion(board.getCellAt(19, 18), board.possiblePeople, board.possibleWeapons, board.getLegend(), computerPlayer);
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
		weapons0.add(card7);
		weapons0.add(card8);

		people0.add(card9);
		people0.add(card10);
		people0.add(card11);
		people0.add(card12);
		people0.add(card13);
		people0.add(card14);
		people0.add(card15);
		people0.add(card16);
		computerPlayer.clearSeenCards();
		for ( Card seen : people0)
		{
			computerPlayer.addSeen(seen);
		}
		for ( Card seen : weapons0)
		{
			computerPlayer.addSeen(seen);
		}
		computerPlayer.createSuggestion(board.getCellAt(19, 18), people0, weapons0, board.getLegend(), computerPlayer);
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
		computerPlayer.addSeen(card12);
		computerPlayer.addSeen(card13);
		computerPlayer.addSeen(card14);
		computerPlayer.addSeen(card15);
		
		weapons.add(card);
		weapons.add(card2);
		weapons.add(card3);
		weapons.add(card4);
		weapons.add(card5);
		weapons.add(card6);
		weapons.add(card7);
		weapons.add(card8);
		
		people.add(card9);
		people.add(card10);
		people.add(card11);
		people.add(card12);
		people.add(card13);
		people.add(card14);
		people.add(card15);
		people.add(card16);
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		computerPlayer.createSuggestion(board.getCellAt(19, 18), people, weapons, board.getLegend(), computerPlayer);
		sol = computerPlayer.getCreatedSoln();
		solution = sol.getPerson();
		System.out.println(card16.getCardName() + " " + solution);

		assertEquals(card16.getCardName(), solution);

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
		computerPlayer.addSeen(card13);
		computerPlayer.addSeen(card14);
		computerPlayer.addSeen(card15);
		computerPlayer.addSeen(card16);
		
		weapons2.add(card);
		weapons2.add(card2);
		weapons2.add(card3);
		weapons2.add(card4);
		weapons2.add(card5);
		weapons2.add(card6);
		weapons2.add(card7);
		weapons2.add(card8);

		people2.add(card9);
		people2.add(card10);
		people2.add(card11);
		people2.add(card12);
		people2.add(card13);
		people2.add(card14);
		people2.add(card15);
		people2.add(card16);
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		computerPlayer.createSuggestion(board.getCellAt(19, 18), people2, weapons2, board.getLegend(), computerPlayer);
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
		computerPlayer.addSeen(card10);
		computerPlayer.addSeen(card11);
		computerPlayer.addSeen(card12);
		computerPlayer.addSeen(card13);

		weapons3.add(card);
		weapons3.add(card2);
		weapons3.add(card3);
		weapons3.add(card4);
		weapons3.add(card5);
		weapons3.add(card6);
		weapons3.add(card7);
		weapons3.add(card8);
		people3.add(card9);
		people3.add(card10);
		people3.add(card11);
		people3.add(card12);
		people3.add(card13);
		people3.add(card14);
		people3.add(card15);
		people3.add(card16);
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		computerPlayer.createSuggestion(board.getCellAt(19, 18), people3, weapons3, board.getLegend(), computerPlayer);
		sol = computerPlayer.getCreatedSoln();
		solution = sol.getPerson();
		//System.out.println(solution);

		assertNotNull(solution);

		//multiple choices, weapon
		computerPlayer.clearSeenCards();
		//System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
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
		computerPlayer.addSeen(card13);
		computerPlayer.addSeen(card14);
		computerPlayer.addSeen(card15);
		computerPlayer.addSeen(card16);
		
		weapons4.add(card);
		weapons4.add(card2);
		weapons4.add(card3);
		weapons4.add(card4);
		weapons4.add(card5);
		weapons4.add(card6);
		weapons4.add(card7);
		weapons4.add(card8);
		
		people4.add(card9);
		people4.add(card10);
		people4.add(card11);
		people4.add(card12);
		people4.add(card13);
		people4.add(card14);
		people4.add(card15);
		people4.add(card16);
		System.out.println("Seen cards: " + computerPlayer.getSeenCards().size());
		computerPlayer.createSuggestion(board.getCellAt(19, 18), people4, weapons4, board.getLegend(), computerPlayer);
		sol = computerPlayer.getCreatedSoln();
		solution = sol.getWeapon();
		System.out.println(solution);

		assertNotNull(solution);
	}
	
	@Test 
	public void testDisproveSuggestion() { 

		//BoardCell testCell = new BoardCell(5, 3, 't', DoorDirection.RIGHT); 
		Set<ComputerPlayer> computerPlayers = new HashSet<ComputerPlayer>();  
		computerPlayers = board.getComputerPlayers(); 

		ComputerPlayer computerPlayer = new ComputerPlayer(); 
		ComputerPlayer testPlayer = new ComputerPlayer();

		int counter = 0; 
		for (ComputerPlayer temp: computerPlayers) {
			if (counter == 0) {
				computerPlayer = temp; 
			}
			else if (counter == 1) {
				testPlayer = temp; 
			}
			else {
				break;
			} 
			counter ++; 
		}


		//changed board.getRooms() to bord.getLegend()
		computerPlayer.createSuggestion(board.getCellAt(4, 0), board.possiblePeople, board.possibleWeapons, board.getLegend(), computerPlayer); 

		testPlayer.clearCards();

		Solution s1 = computerPlayer.getCreatedSoln(); 
		String p1 = s1.getPerson(); 
		Card cp1 = new Card(p1, CardType.PERSON); 
		String w1 = s1.getWeapon(); 
		Card cw1 = new Card(w1, CardType.WEAPON);
		String r1 = s1.getRoom(); 
		Card cr1 = new Card(r1, CardType.ROOM);

		testPlayer.addCard(cp1);
		testPlayer.addCard(cw1);
		testPlayer.addCard(cr1);

		String p2 = "wrong"; 
		String w2 = w1; 
		String r2 = "wrong"; 

		Solution s2 = new Solution(p2, w2, r2); 


		Card returnedCard = testPlayer.disproveSuggestion(s2); 
		Card test = new Card(w1, CardType.WEAPON);

		String a = test.getCardName(); 
		String b = returnedCard.getCardName(); 
		
		//System.out.println("A: " + a);
		//System.out.println("B: " + b);


		// One matching card
		assertEquals(a,b); 

		p2 = p1; 
		r2 = r1; 

		Solution s3 = new Solution(p2, w2, r2); 

		returnedCard = testPlayer.disproveSuggestion(s3);
		Card testP = new Card(p2, CardType.PERSON); 
		Card testW = new Card(w2, CardType.WEAPON); 
		Card testR = new Card(r2, CardType.ROOM); 

		b = returnedCard.getCardName(); 
		String c = testP.getCardName(); 
		String d = testW.getCardName(); 
		String e = testR.getCardName(); 
		//System.out.println(b + " " + c + " " + d + " " + e);

		// More than one matching card 
		//assertTrue(b.equals(c) || b.equals(d) || b.equals(e));
		boolean matchingCard = false;
		if(b==c || b==d || b== e) {
			matchingCard = true;
		}
		assertTrue(matchingCard);


		p2 = "wrong"; 
		w2 = "wrong";  
		r2 = "wrong"; 

		Solution s4 = new Solution(p2, w2, r2); 

		assertNull(testPlayer.disproveSuggestion(s4));


	}
}
