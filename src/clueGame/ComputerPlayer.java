package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	
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
