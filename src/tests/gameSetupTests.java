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
	
	@Test
	public void testLoadingDeck() {
		board.loadCards(); //move to @beforeclass?

		Set<Card> deck = board.getDeck(); //move to @beforeclass?
		int numWeapons = 0, numPeople = 0, numRooms = 0;
		for (Card card : deck) {
			if(card.getType() == CardType.PERSON)
				numPeople++;
			else if(card.getType() == CardType.ROOM)
				numRooms++;
			else if(card.getType() == CardType.WEAPON)
				numWeapons++;

		}
		//checks that the deck size is correct
		assertEquals(27, deck.size());

		//checks for correct number of each type of card
		assertEquals(8, numPeople);
		assertEquals(8, numWeapons);
		assertEquals(9, numRooms);

		//makes sure the deck loaded cards correctly
		assert(deck.contains(board.getCard("Poison Dart", CardType.WEAPON)));
		assert(deck.contains(board.getCard("Angela Martin", CardType.PERSON)));
		assert(deck.contains(board.getCard("Conference Room", CardType.ROOM)));
	}
	
	@Test
	public void testDealingCards() {
		
		
		//Gets player list so we can do tests with their cards
		ArrayList<Player> playerList = board.getPlayerList();
		boolean dealtTwice = false;
		int avgCardsPerPlayer = board.getDeckOfCards().size() / board.getPlayerList().size();
		
		Set<Card> testCardsDealt = new HashSet<>();
		for (Player player: playerList) {
			//this test assures each player has roughly the same amount of cards
			assert(player.getCards().size() <= avgCardsPerPlayer +1 &&
					player.getCards().size() >= avgCardsPerPlayer -1);
			//gets each player's set of cards
			for(Card card: player.getCards()) {
				//tests if a card already exists, then adds to test set
				if (testCardsDealt.contains(card))
					dealtTwice = true;
				testCardsDealt.add(card);
				
			}
		}
		//if the test set is equal to the original deck of cards, then all the cards were dealt
		assert(testCardsDealt.equals(board.getDeckOfCards()));
		//No card should be dealt twice
		assertFalse(dealtTwice);
		
		
		
		
		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
