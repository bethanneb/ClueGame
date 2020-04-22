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
		accusation.setPerson(person);
		accusation.setRoom(room);
		accusation.setWeapon(weapon);
	}



}