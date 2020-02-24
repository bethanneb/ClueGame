package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell [][] grid;
	
	public IntBoard() {
		calcAdjacencies();
	}
	
	public void calcAdjacencies () {
		
	}
	
	public Set<BoardCell> getAdjList() {
		
		return visited;
	}
	
	public void calcTargets(int startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}

}
