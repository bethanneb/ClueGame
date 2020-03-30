package clueGame;

import java.awt.Color;

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
}
