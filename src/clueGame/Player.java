package clueGame;

import java.awt.Color;
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

	// constructor
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		myCards = new HashSet<Card>();
		seenCards = new HashSet<Card>();
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

		ArrayList<Card> possibilities = new ArrayList<Card>();

		for(Card c : myCards){
			if(suggestion.person.equals(c.getCardName()))
				possibilities.add(c);
			if(suggestion.weapon.equals(c.getCardName()))
				possibilities.add(c);
			if(suggestion.room.equals(c.getCardName()))
				possibilities.add(c);
		}

		if(possibilities.size() > 0){
			Random rand = new Random();
			int randCard = rand.nextInt(possibilities.size());
			return possibilities.get(randCard);
		}

		return null;
	}

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