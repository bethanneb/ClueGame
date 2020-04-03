/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Set<Card> myCards;
	private Set<Card> seenCards;
	private String colorString;

	//NEW
	private int currentRow, currentColumn, previousRow, previousColumn; 
	private Color colorMain; 
	// handling the the previous and current BoardCell for pickLocation for ComputerPlayer 
	private BoardCell current;
	private BoardCell previous;

	// constructors
	public Player()
	{
		this.playerName = "";
		this.currentRow = 0;
		this.myCards = new HashSet<Card>();
		this.seenCards = new HashSet<Card>();
		this.colorMain = Color.white;
	}
	
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		myCards = new HashSet<Card>();
		seenCards = new HashSet<Card>();
	}

	public Player( String name, String color, int r, int c ) {
		this.playerName = name;
		this.row = r;
		this.column = c;
		this.color = convertColor(color);
		this.myCards = new HashSet<Card>();
		this.seenCards = new HashSet<Card>();

	}

	public Color convertColor (String strColor) {
		this.colorString = strColor;
		Color color;
		try 
		{
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.toLowerCase());
			color = (Color)field.get(null);
		}
		catch (Exception e)
		{
			color = null;
			System.out.println("ERROR");
		}
		return color;
	}

	public String getName() {
		return playerName;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	
	public Color getColor() {
		return color;
	}

	public boolean isHuman() {
		return false;
	}

	public boolean isComputer() {
		return false;
	}

	//NEW
	public Card disproveSuggestion(Solution soln) {

		String p1, p2, w1, w2, r1, r2;  

		Set<Card> myCards = new HashSet<Card>(); 
		myCards = getMyCards(); 
		ArrayList<Card> cardsFound = new ArrayList<Card>();
		//System.out.println(myCards.size());
		for (Card found: myCards) {
			if (soln.getPerson().equals(found.getCardName())) {
				//System.out.println("1st");
				cardsFound.add(found); 
			}
			if (soln.getWeapon().equals(found.getCardName())) {
				//System.out.println("2nd");
				cardsFound.add(found); 
			}
			if (soln.getRoom().equals(found.getCardName())) {
				//System.out.println("3rd");
				cardsFound.add(found); 
			}
			 
		}

		if (cardsFound.size() == 0) {
			return null;
		}
		
		else if (cardsFound.size() == 1) { 
			return cardsFound.get(0); 
		}
		
		else {
			Random rand = new Random(); 
			int possition = rand.nextInt(cardsFound.size());

			return cardsFound.get(possition); 
		}

	}

//	public Card disproveSuggestion(Solution suggestion){
//
//		ArrayList<Card> possibilities = new ArrayList<Card>();
//
//		for(Card c : myCards){
//			if(suggestion.person.equals(c.getCardName()))
//				possibilities.add(c);
//			if(suggestion.weapon.equals(c.getCardName()))
//				possibilities.add(c);
//			if(suggestion.room.equals(c.getCardName()))
//				possibilities.add(c);
//		}
//
//		if(possibilities.size() > 0){
//			Random rand = new Random();
//			int randCard = rand.nextInt(possibilities.size());
//			return possibilities.get(randCard);
//		}
//
//		return null;
//	}
	
	public int getMyCardSize()
	{
		return myCards.size();
	}
	
	public void addCard(Card card) {
		myCards.add(card);
	} 
	
	public void clearCards() { 
		myCards.clear(); 
	}
	
	public String getPlayerName()
	{
		return this.playerName;
	}
	
	public void updatePosition(int c, int r) { 
		previousColumn = currentColumn; 
		currentColumn = c; 
		previousRow = currentRow; 
		currentRow = r; 
	}
	
	public int getPreviousColumn() {
		return previousColumn; 
	}
	
	public int getPreviousRow() {
		return previousRow; 
	}
	
	public void addSeen(Card card) {
		seenCards.add(card); 
	}
	
	public void clearSeenCards ()
	{
		seenCards.clear();
	}
	
	public Set<Card> getSeenCards() {
		return seenCards; 
	}

	public int getCurrentRow() {
		return currentRow; 
	}
	public int getCurrentColumn() {
		return currentColumn; 
	}
	
	public String getColorString()
	{
		return this.colorString;
	}
	
	public void updatePlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	//OLD
	public Set<Card> getMyCards() {
		return myCards;
	}

	public void giveCard(Card card) {
		myCards.add(card);
		seeCard(card);
	}

	public void seeCard(Card card) {
		seenCards.add(card);
	}

}