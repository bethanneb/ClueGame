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
public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}

	@Override
	public boolean isHuman() {
		return true;
	}

	private Solution accusation = new Solution();

	public HumanPlayer(){
		super();
	}

	public HumanPlayer(String name, String color, int r, int c){
		super(name, color, r, c);
	}

	public void setAccusation( String room, String person, String weapon){
		accusation.setPerson(person);
		accusation.setRoom(room);
		accusation.setWeapon(weapon);
	}



}