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
	//static so that it only has to be loaded one time
	private static Board board;
	//static so we only have one copy of the ArrayList
	private static ArrayList<Player> players;

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
		players = board.getPlayerList();
	}

	@Test
	public void testLoadingPeople() {
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

	/*
	 * Ensures correct deck size, correct number of each type of card, and that names on the cards are being loaded correctly
	 */
	@Test
	public void testLoadingDeck() {
		//Ensures deck contains the correct total number of cards (21)
		assertEquals(21, board.getCards().length);

		int people = 0, rooms = 0, weapons = 0;
		for(Card c : board.getCards()){
			switch(c.getType()){
			case PERSON:
				people++;
				break;
			case ROOM:
				rooms++;
				break;
			case WEAPON:
				weapons++;
				break;
			}
		}
		//Ensures the deck contains the correct number of each type of card
		assertEquals(6, people);
		assertEquals(9, rooms);
		assertEquals(6, weapons);

		//ensures the deck contains one certain room, weapon, and person
		boolean room = false, weapon = false, person = false;
		for(Card c : board.getCards()){
			if(c.getCardName().equals("Angela Martin")){
				person = true;
			}
			if(c.getCardName().equals("Poison Dart")){
				weapon = true;
			}
			if(c.getCardName().equals("Conference Room")){
				room = true;
			}
		}
		//if this is true, then the names on those three cards were loaded correctly
		assertTrue(person && weapon && room);
	}
	
	@Test
	public void testDealingCards() {
		//Gets player list so we can do tests with their cards
		ArrayList<Player> playerList = board.getPlayerList();
		boolean dealtTwice = false;
		int avgCardsPerPlayer = board.getDeck().size() / board.getPlayerList().size();
		
		Set<Card> testCardsDealt = new HashSet<>();
		for (Player player: playerList) {
			//this test assures each player has roughly the same amount of cards
			assert(player.getMyCards().size() <= avgCardsPerPlayer +1 &&
					player.getMyCards().size() >= avgCardsPerPlayer -1);
			//gets each player's set of cards
			for(Card card: player.getMyCards()) {
				//tests if a card already exists, then adds to test set
				if (testCardsDealt.contains(card))
					dealtTwice = true;
				testCardsDealt.add(card);
				
			}
		}
		//if the test set is equal to the original deck of cards, then all the cards were dealt
		assert(testCardsDealt.equals(board.getDeck()));
		//No card should be dealt twice
		assertFalse(dealtTwice);
	}

































}
