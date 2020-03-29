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


	//created instance of Card class to return a card type object for now 
	public Card disproveSuggestion(Solution suggestion) {
		Card card = new Card();
		return card;
	}
}
