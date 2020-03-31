/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	
	public BoardCell() {
		
	}
	
	public BoardCell(int row, int column, char initial, DoorDirection doorDirection) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDirection = doorDirection;
	}
	
	public boolean isWalkway() {
		if(initial == 'W') {
			return true;
		}
		return false;
	}
	
	public boolean isRoom() {
		if(initial == 'W' || initial == 'X') {
			return false;
		}
		return true;
	}
	
	public boolean isDoorway() {
		if(doorDirection != DoorDirection.NONE) {
			return true;
		}
		return false;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return initial;
	}

}