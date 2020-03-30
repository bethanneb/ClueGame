package clueGame;

import java.awt.Color;

//I think it is suppose to implement Solution (but I can't remember exactly what dotted arrows mean from the UML)
public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	// constructor
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
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
	
	//created instance of Card class to return a card type object for now 
	public Card disproveSuggestion(Solution suggestion) {
		Card card = new Card();
		return card;
	}
}
