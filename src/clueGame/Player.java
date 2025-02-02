/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Point pixel;
	private Set<Card> myCards;
	private Set<Card> seenCards;
	private String colorString;

	//NEW
	private int currentRow, currentColumn, previousRow, previousColumn; 
	private Color colorMain; 
	// handling the the previous and current BoardCell for pickLocation for ComputerPlayer 
	private BoardCell current;
	private BoardCell previous;
	private Solution suggestion = new Solution();
	public String guess;
	public String result;

	// constructors
	public Player()
	{
		playerName = "";
		row = 0;
		myCards = new HashSet<Card>();
		seenCards = new HashSet<Card>();
		color = Color.white;
		guess = "no guess";
		result = "no result";
	}
	
	// Player parameterized constructors
	public Player( String name, String color, int r, int c ) {
		this.playerName = name;
		this.row = r;
		this.column = c;
		this.colorMain = convertColor(color);
		this.myCards = new HashSet<Card>();
		this.seenCards = new HashSet<Card>();
		// can draw with correct scaling 
		pixel = new Point (this.row * 22 + 15, this.column * 22 + 100);
		guess = "no guess";
		result = "no result";
	}
	
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		myCards = new HashSet<Card>();
		seenCards = new HashSet<Card>();
		// can draw with correct scaling 
		pixel = new Point (this.row * 22 + 15, this.column * 22 + 100);
		guess = "no guess";
		result = "no result";
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

	public Card disproveSuggestion(Solution suggestion){
		Set<Card> myCards = new HashSet<Card>(); 
		myCards = getMyCards(); 
		ArrayList<Card> cardsFound = new ArrayList<Card>();

		for (Card found: myCards) {
			if (suggestion.getPerson().equals(found.getCardName())) {
				cardsFound.add(found); 
			}
			if (suggestion.getWeapon().equals(found.getCardName())) {
				cardsFound.add(found); 
			}
			if (suggestion.getRoom().equals(found.getCardName())) {
				cardsFound.add(found); 
			}

		}

		if (cardsFound.size() == 0) {
			return null;
		}
		else {
			Random rand = new Random(); 
			int position = rand.nextInt(cardsFound.size());
			return cardsFound.get(position); 
		}
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return current;
	}
	
	public void setLastRoom(BoardCell cell) {}
	
	// draws players using their given color and starting location
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(pixel.y, pixel.x, 20, 20);
	}
	
	public int getMyCardSize()
	{
		return myCards.size();
	}
	
	public void clearCards() { 
		myCards.clear(); 
	}
	
	public String getPlayerName()
	{
		return this.playerName;
	}
	
	public Solution getCreatedSoln() {return suggestion;}
	
	public void createSuggestion(BoardCell cell, ArrayList<Card> peopleArray, ArrayList<Card> weaponsArray, Map<Character, String> legend, Player player) {}
	
	public boolean isAccusationReady(){return false;}
	
	public void updatePosition(int r, int c) { 
		previousColumn = column; 
		column = c; 
		previousRow = row; 
		row = r; 
		pixel = new Point (this.row * 22 + 15, this.column * 22 + 100);
	}
	
	public int getPreviousColumn() {
		return previousColumn; 
	}
	
	public int getPreviousRow() {
		return previousRow; 
	}
	
	
	public void clearSeenCards ()
	{
		seenCards.clear();
	}
	
	public Set<Card> getSeenCards() {
		return seenCards; 
	}

	
	public String getColorString()
	{
		return this.colorString;
	}
	
	public void updatePlayerName(String playerName)
	{
		this.playerName = playerName;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
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
	
	//NEW FOR TESTS
	public void setName(String i) {
		this.playerName = i;
	}
	
	public Solution getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(Solution suggestion) {
		this.suggestion = suggestion;
	}
	
	public void setAccusation(Solution nextAccusation) {}
	
	public ArrayList<Card> cards;
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	public String getGuess(){
		return guess;
	}
	
	public void updateGuess(String newGuess) {
		guess = newGuess;
	}

	public String getResult(){
		return result;
	}
	
	public void updateResult(String newResult) {
		result = newResult;
	}

	public Solution getAccusation() {
		return null;
	}
}