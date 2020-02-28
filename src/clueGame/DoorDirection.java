//Authors: Elizabeth Bauch and Danella Bunavi
package clueGame;

public enum DoorDirection {
	UP, DOWN, LEFT, RIGHT, NONE;
	
	public static DoorDirection convert(char initial) { 	// it makes sense to have this function in
		switch(initial) {							// the enumerator class
			case 'U': return UP;
			case 'D': return DOWN;
			case 'L': return LEFT;
			case 'R': return RIGHT;
			default: return NONE;
		}
	}
	
}

