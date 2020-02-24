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
		//return list for specific cell
		return adjMtx[BoardCell];
	}
	
	//start cell shouldn't be an int maybe? I made it a BoarcCell
	public void calcTargets(BoardCell startCell, int pathLength) {
		//how many steps you have
		int numSteps = pathLength;
		//initialize placement
		BoardCell currentCell = startCell;
		//for all of our adjacent cells
		for(BoardCell adjacentCell: adjMtx[currentCell]) {
			//if on visited list already continue
			for(BoardCell alreadyVisited: visited) {
				if(alreadyVisited == adjacentCell) {
					continue;
				}
				else {
					visited.add(currentCell);
					if(numSteps == 1) {
						targets.add(adjacentCell);
					}
					else {
						numSteps--;
						calcTargets(adjacentCell, numSteps);
					}
				}
			}
			visited.remove(adjacentCell);
			
		}
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}

	
}
