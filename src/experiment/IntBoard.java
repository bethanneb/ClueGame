/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	//to keep track of starting cell, and the adjacency matrix
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	//to keep track of the visited cells, when trying to find targets
	private Set<BoardCell> visited;
	//to keep track of the targets, or cells that player can move to
	private Set<BoardCell> targets;
	//used to calculate the adjacency matrix
	private BoardCell[][] grid;
	
	//for 4x4 grid
	public static final int ROWS = 4;
	public static final int COLUMNS = 4;
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		return adjMtx.get(cell);
	}

	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public IntBoard() {
		super();
		//allocating memory
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>(); 
		grid = new BoardCell[ROWS][COLUMNS];
		//nested for loops to create grid
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		//
		calcAdjacencies();
	}

	public void calcAdjacencies(){
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				Set<BoardCell> adjacencies = new HashSet<BoardCell>();
				if (i > 0) {
					adjacencies.add(grid[i - 1][j]);
				}
				if (i < ROWS - 1) {
					adjacencies.add(grid[i + 1][j]);
				}
				if (j > 0) {
					adjacencies.add(grid[i][j - 1]);
				}
				if (j < COLUMNS - 1) {
					adjacencies.add(grid[i][j + 1]);
				}
				adjMtx.put(grid[i][j], adjacencies);
			}
		}
	}

	public void calcTargets(BoardCell startCell, int pathLength){
		//have to clear visited and targets sets so they are empty for the next starting cell
		visited.clear();
		targets.clear();
		//add start location to visited, because can't backtrack
		visited.add(startCell); 
		targets = findAllTargets(startCell, pathLength);
	}

	public Set<BoardCell> findAllTargets(BoardCell currentCell, int remainingSteps){
		visited.add(currentCell);
		Set<BoardCell> adjacency = new HashSet<BoardCell>(adjMtx.get(currentCell));
		//if any of the cells in the adjacency set have already been visited, remove it from the adjacency set
		for (BoardCell i:visited){
			adjacency.remove(i);
		}
		//if there's only one step left, then add the adjacency cell to targets set
		for (BoardCell i:adjacency){
			if(remainingSteps == 1){
				targets.add(i);
			}
			//else, recurse
			else {
				targets.addAll(findAllTargets(i, remainingSteps-1));
			}
			visited.remove(i);
		}
		return targets;
	}

}