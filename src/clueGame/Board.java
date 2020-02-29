//Authors: Elizabeth Bauch and Danella Bunavi
package clueGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//import experiment.BoardCell;

public class Board {
	public static final int MAX_BOARD_SIZE = 50;
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private BoardCell cell;
	private Map<Character, String> legend;
	private Set<BoardCell> visited;
	
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	//private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public Board() { //default constructor
		super();
		this.boardConfigFile = "OurClueLayout.csv";
		this.roomConfigFile = "OurClueLegend.txt";
		}
	
	
	public Board(String boardConfigFile, String roomConfigFile) { //constructor with board and room files passed in for testing with other files
		super();
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public void setConfigFiles(String boardConfigFile, String roomConfigFile) {
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	private int getBoardConfigRows() throws FileNotFoundException  {
		FileReader fin = new FileReader(boardConfigFile);
		Scanner scan = new Scanner(fin);
		int count = 0;
		while(scan.hasNext()) {
			scan.next();
			count++;
		}
		scan.close();
		numRows = count;
		return numRows;
	}
	
	private int getBoardConfigColumns() throws FileNotFoundException, BadConfigFormatException {
		FileReader fin = new FileReader(boardConfigFile);
		Scanner scan = new Scanner(fin);
		int count = 0;
		int maxCount = 0;
		boolean firstGo = true;
		while(scan.hasNext()) {
			count = 0;
			String nextCol = scan.next();
			Scanner scanIn = new Scanner(nextCol);
			scanIn.useDelimiter(",");
			while(scanIn.hasNext()) {
				scanIn.next();
				count++;
			}
			scanIn.close();
			if(firstGo) {
				maxCount = count;
				firstGo = false;
			}
			else {
				if(count != maxCount) {
					throw new BadConfigFormatException("Number of rows or columns is not consistent");
				}
			}
		}
		scan.close();
		numColumns = count;
		return numColumns;
	}
	
	public BoardCell getCellAt(int row, int column){
		return board[row][column];
	}

	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (FileNotFoundException e) {
			System.out.println("File not found. " + e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		legend = new HashMap<>();
		BufferedReader reader = null;
		try {
			//read entire file and put into standard characters
			reader = new BufferedReader(new FileReader(roomConfigFile, StandardCharsets.UTF_8));
			String line = reader.readLine();
			while (line != null) {
				String[] splits = line.split(",");
				// no character for room
				if(splits[1] == null || "".equals(splits[1])) {
					throw new BadConfigFormatException("Bad legend file; lacks a room name for initial");
				}
				//not a valid card or walkway or closet
				if(!("Card".equalsIgnoreCase(splits[2].trim()) || "Other".equalsIgnoreCase(splits[2].trim()))) {
					throw new BadConfigFormatException("It is not card or other");
				}
				String keyScan = splits[0].trim();
				char key = keyScan.charAt(0);
				//if it is being converted into special characters for some reason
				if (keyScan.length()>1) {
					//get only the initial we want
					key = keyScan.charAt(keyScan.length()-1);
				}
				legend.put(key, splits[1].trim());
				// read next line
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			//want to make sure that file will be close no matter what, even if error is thrown
		} finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		numRows = getBoardConfigRows();
		numColumns = getBoardConfigColumns();
		board = new BoardCell[numRows][numColumns];
		FileReader fin = new FileReader(boardConfigFile);
		Scanner scan = new Scanner(fin);
		int row = 0;
		int column = 0;
		while(scan.hasNext()) {
			column = 0;
			String nextLine = scan.next();	// This is a single line of comma-separated values
			nextLine = nextLine.replace(',', ' ');		// Commas replaced by spaces, to generate a readable list
			Scanner scanIn = new Scanner(nextLine);
			while(scanIn.hasNext()) {
				String nextEntry = scanIn.next();
				if(!legend.containsKey(nextEntry.charAt(0))) {
					scanIn.close();
					throw new BadConfigFormatException("Bad room type");
				}
				if(nextEntry.length() > 1) {	// if this is true, then the cell must be a door
					DoorDirection d = DoorDirection.convert(nextEntry.charAt(1));
					this.board[row][column] = new BoardCell(row, column, nextEntry.charAt(0), d);
				}
				else {
					this.board[row][column] = new BoardCell(row, column, nextEntry.charAt(0), DoorDirection.NONE);
				}
				column++;
			}
			scanIn.close();
			row++;
		}
		scan.close();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		calcAdjacencies();
	}

	public void calcAdjacencies(){
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				HashSet<BoardCell> neighbors = new HashSet<BoardCell>();
				if (board[i][j].isRoom() && !board[i][j].isDoorway()){
					adjMatrix.put(board[i][j], neighbors);
					continue;
				}
				else if (board[i][j].isDoorway()) {
					DoorDirection initial = board[i][j].getDoorDirection();
					switch(initial) {							// the enumerator class
					case UP: if(i > 0) {
						neighbors.add(board[i - 1][j]);
						adjMatrix.put(board[i][j], neighbors);
					}
					break;
					case DOWN: if(i < numRows - 1) {
						neighbors.add(board[i + 1][j]);
						adjMatrix.put(board[i][j], neighbors);
					}
					break;
					case LEFT: if(j > 0) {
						neighbors.add(board[i][j - 1]);
						adjMatrix.put(board[i][j], neighbors);
					}
					break;
					case RIGHT: if(j < numColumns - 1) {
						neighbors.add(board[i][j + 1]);
						adjMatrix.put(board[i][j], neighbors);
					}
					break;
					default: System.out.println("Unknown Door Direction");;
					}
				}
				else {
					if(i > 0) {
						if (!board[i - 1][j].isRoom() || (board[i - 1][j].isDoorway() && board[i - 1][j].getDoorDirection() == DoorDirection.DOWN))
							neighbors.add(board[i - 1][j]);
					}
					if(i < numRows - 1) {
						if (!board[i + 1][j].isRoom() || (board[i + 1][j].isDoorway() && board[i + 1][j].getDoorDirection() == DoorDirection.UP))
							neighbors.add(board[i + 1][j]);
					}
					if(j > 0) {
						if(!board[i][j - 1].isRoom() || (board[i][j - 1].isDoorway() && board[i][j - 1].getDoorDirection() == DoorDirection.RIGHT))
							neighbors.add(board[i][j - 1]);
					}
					if(j < numColumns - 1) {
						if (!board[i][j + 1].isRoom() || (board[i][j + 1].isDoorway() && board[i][j + 1].getDoorDirection() == DoorDirection.LEFT))
							neighbors.add(board[i][j + 1]);
					}
					adjMatrix.put(board[i][j], neighbors);
				}
			}
		}
	}
	
	public void calcTargets(int row, int column, int pathLength){
		visited = new HashSet<BoardCell>(); //should we set these up here? might be ineff.
		targets = new HashSet<BoardCell>();
		visited.clear(); //clear the visited set
		targets.clear(); //clear the targets set
		visited.add(board[row][column]);
		targets = findAllTargets(board[row][column], pathLength);
	}
	
	private Set<BoardCell> findAllTargets(BoardCell currentCell, int remainingSteps) {
		visited.add(currentCell);
		HashSet<BoardCell> adj = new HashSet<BoardCell>(adjMatrix.get(currentCell));	//new linked list of cells that have not been visited
		for (BoardCell i:visited){
			adj.remove(i);
		}
		for (BoardCell i:adj){
			if(remainingSteps == 1){
				targets.add(i);
			}
			else if (i.isDoorway()){
				targets.add(i);
			}
			else {
				targets.addAll(findAllTargets(i, remainingSteps-1));
			}
			visited.remove(i);
		}
		return targets;
	}
	
	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMatrix.get(board[i][j]);
	}
	

}
