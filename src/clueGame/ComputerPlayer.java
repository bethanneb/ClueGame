package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}

	//created instance of BoardCell so this function could return it to avoid errors for now
	public BoardCell pickLocation(Set <BoardCell> targets) {
		BoardCell location = new BoardCell();
		return location;
	}
	
	public void makeAccusation() {
		
	}
	
	//I also commented this out because we don't know what this is suppose to do yet
//	public createSuggestion(TBD) {
//		
//	}
	

}
