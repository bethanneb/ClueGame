/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

//not sure if it us suppose to implement or extend
// children extend parent so I put extend
public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isHuman() {
		return true;
	}

	//NEW

	private Solution accusation = new Solution();

	// Default constructor
	//@param no parameters
	//@return nothing return; default constructor
	public HumanPlayer()
	{
		super();
	}

	// Parameterized constructor
	//@param name player's name
	//@param r player's location via row
	//@param c player's location via column
	//@param color player's color that is read as a string from a file
	public HumanPlayer(String name, String color, int r, int c)
	{
		super(name, color, r, c);
	}

	public void setAccusation( String room, String person, String weapon)
	{
		accusation.setAnswerKeyPerson(person);
		accusation.setAnswerKeyRoom(room);
		accusation.setAnswerKeyWeapon(weapon);
	}


	@Override
	public Card disproveSuggestion(Solution soln) {

		//String p1, p2, w1, w2, r1, r2;  

		Set<Card> myCards = new HashSet<Card>(); 
		myCards = getMyCards(); 
		ArrayList<Card> cardsFound = new ArrayList<Card>();
		//System.out.println(myCards.size());
		for (Card found: myCards) {
			if (soln.getPerson() == found.getCardName()) {
				//System.out.println("1st");
				cardsFound.add(found); 
			}
			if (soln.getWeapon() == found.getCardName()) {
				//System.out.println("2nd");
				cardsFound.add(found); 
			}
			if (soln.getRoom() == found.getCardName()) {
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
			int position = rand.nextInt(cardsFound.size());

			return cardsFound.get(position); 
		}

	}
}