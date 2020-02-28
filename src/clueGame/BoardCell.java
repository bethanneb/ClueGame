//Authors: Elizabeth Bauch and Danella Bunavi
package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private DoorDirection doorDirection;
	
	public BoardCell () {
		
	}
	
	public BoardCell(int i, int j) {
		row = i;
		column = j;
	}
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return '0';
	}

}
