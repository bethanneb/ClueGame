/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Board extends JPanel {
	public static final int MAX_BOARD_SIZE = 50;
	public static final int DECK_SIZE = 25;
	public static final int NUM_WEAPONS = 8;
	public static final int NUM_PEOPLE = 8;
	public static final int NUM_ROOMS = 9;
	private int numRows, numColumns;
	private static BoardCell[][] board;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private static Set<BoardCell> targets, visited = new HashSet<BoardCell>();
	private String boardConfigFile, roomConfigFile;
	private BoardCell cell;
	private Map<Character, String> legend;
	private ArrayList<Character> legendKeys;
	private Set<Card> deck;
	private ArrayList<String> weapons;
	private ArrayList<Player> playersList;
	private String playersConfigFile;
	private String cardsConfigFile;
	private Card[] cards;
	public Solution solution;
	private Player[] players;
	public Suggestion suggest;

	private Solution answerKey = new Solution();
	// Set that would hold the computer player
	private Set<ComputerPlayer> computerPlayers = new HashSet<ComputerPlayer>();
	// Set that would hold the human player
	private Set<HumanPlayer> humanPlayer; 
	private Set<String> rooms;
	private Set<Card> key; 
	private Set<Card> roomPile;
	private Set<Card> peoplePile = new HashSet<Card>();
	private Set<Card> weaponsPile = new HashSet<Card>();
	private String peopleConfigFile;
	private String weaponsConfigFile; 
	ArrayList<Card> possibleCards = new ArrayList<Card>();
	public static ArrayList<Card> possiblePeople = new ArrayList<Card>();  
	public static ArrayList<Card> possibleWeapons = new ArrayList<Card>(); 
	public static ArrayList<Card> possibleRooms = new ArrayList<Card>();
	ArrayList<Player> player = new ArrayList<Player>();
	ArrayList<Point> roomNames = new ArrayList<Point>();
	private JPanel panel;
	static JFrame suggestAccuseFrame; 
	JFrame myFrame; 
	boolean gameFinished = false; 

	// NOTE: Game logic variables 
	public boolean doneWithHuman = true;
	public boolean targetSelected = true;
	public boolean hasNotAccused = true;
	private boolean doneWithComputer = false;
	private Player currentPlayerInGame;
	public int currentPlayerInGameCount = -1;
	private BoardCell selectedBox;
	private ArrayList<Player> gamePlayers = new ArrayList<Player>();
	private int dieRollValue = -1;
	private boolean compReadyMakeAccusation = false;
	private boolean compSuggestionDisproved = true;
	private String currentGuess = "no guess yet";
	private String currentResults = "no new clue";
	public boolean inWindow = false; 
	public boolean isFirstTurn = true; 

	//private MouseEvent event; 

	private int clickRow;
	private int clickCol;

	//public Suggestion suggest; //CREATE CLASS LATER
	// Functions:
	//NOTE: Singleton pattern 
	//used for tests
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	} 


	private Board() {
		super();
		visited = new HashSet<BoardCell>(); //should we set these up here? might be inefficient.
		targets = new HashSet<BoardCell>();
		boardConfigFile = "OurClueLayout.csv";
		roomConfigFile = "OurClueLegend.txt";
		Player emptyPlayer = new Player();
		currentPlayerInGame = emptyPlayer;


	}

	//constructor with board and room files passed in for testing with other files
	public Board(String boardConfigFile, String roomConfigFile) { 
		super();
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	//tests given to us call this function, to pass in their files for testing
	public void setConfigFiles(String boardConfigFile, String roomConfigFile) {
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
	}

	//to use when passing in file names in testing file
	public void setCardFiles(String players, String cards) {
		//Stores file path to a variable in the board
		this.playersConfigFile = players;
		this.cardsConfigFile = cards;

	}

	//NEW
	public BoardCell[][] getBoard()
	{
		return board;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	//returns the targets (where the player can go)
	public static Set<BoardCell> getTargets() {
		return targets;
	}

	//scans in board file; find the number of rows by scanning them in while there is still another row to scan; 
	//uses commas as delimiters in order to find the number of columns (comma separated values)
	private int determineNumRowsAndColumns() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(boardConfigFile);
		FileReader fin = new FileReader(boardConfigFile);
		Scanner scan = new Scanner(fin);
		Scanner scanner = new Scanner(reader);
		int columns = 0, maxCount = 0, rows = 0; //compressed
		boolean firstGo = true; //initialize boolean to true
		//determine numRows
		while(scanner.hasNext()) {
			scanner.nextLine(); 
			rows++;
		}
		scanner.close();
		//assigns number of rows
		numRows = rows;
		//determine numColumns
		while(scan.hasNext()) {
			columns = 0;
			String nextCol = scan.nextLine();
			Scanner scanIn = new Scanner(nextCol);
			scanIn.useDelimiter(","); //csv file (comma separated values)
			while(scanIn.hasNext()) {
				scanIn.next();
				columns++;
			}
			scanIn.close();
			if(firstGo) {
				maxCount = columns;
				firstGo = false;
			}
			else {
				if(columns != maxCount) {
					throw new BadConfigFormatException("Number of rows or columns are not consistent");
				}
			}
		}
		scan.close();
		numColumns = columns;
		return numColumns;
	}

	//returns the cell's row and column 
	public BoardCell getCellAt(int row, int column){
		return board[row][column];
	}

	//makes sure that both the board and legend files are there
	//prints the name of the file if it isn't found
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadConfigFiles();
			loadCards();
			dealCards();

		} catch (FileNotFoundException e) {
			System.out.println("File not found. " + e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}

	//reads in the room using BufferedReader (did this because of some special characters issues we were having from our file)
	//several exceptions to indicate certain, more common errors
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		legend = new HashMap<>();
		rooms = new HashSet<String>();
		BufferedReader reader = null;
		try {
			//read entire file and put into standard characters (we had some issues with special characters so we used BufferedReader)
			reader = new BufferedReader(new FileReader(roomConfigFile, StandardCharsets.UTF_8));
			String line = reader.readLine();
			while (line != null) {
				String[] splits = line.split(",");
				// no character for room
				if(splits[1] == null || "".equals(splits[1])) {
					throw new BadConfigFormatException("Bad legend file; no character to represent the room");
				}
				//not a valid card or walkway or closet
				if(!("Card".equalsIgnoreCase(splits[2].trim()) || "Other".equalsIgnoreCase(splits[2].trim()))) {
					throw new BadConfigFormatException("It is not a card or other");
				}
				String keyScan = splits[0].trim();
				char key = keyScan.charAt(0);
				//if it is being converted into special characters for some reason
				if (keyScan.length()>1) {
					//get only the initial we want
					key = keyScan.charAt(keyScan.length()-1);
				}
				String split = splits[1].trim();
				legend.put(key, split);
				rooms.add(split);
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

	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		determineNumRowsAndColumns();
		board = new BoardCell[numRows][numColumns];
		FileReader fin = new FileReader(boardConfigFile);
		Scanner scan = new Scanner(fin);
		int row = 0;
		int column = 0;
		while(scan.hasNext()) {
			column = 0;
			String nextLine = scan.next();	// This is a single line of comma-separated values
			nextLine = nextLine.replace(',', ' '); // Commas replaced by spaces, to generate a readable list
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
		//move to constructor???
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		calcAdjacencies();
	}

	//checks that the neighbors of a cell are adjacent to where the player is at, and if it's an allowed adjacency, it's added to the set
	public void calcAdjacencies(){
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				HashSet<BoardCell> neighbors = new HashSet<BoardCell>();

				// cell above
				if (isInBoard(i + 1, j) && allowedAdj(i, j, i + 1, j, DoorDirection.DOWN, DoorDirection.UP)) {
					neighbors.add(board[i + 1][j]);
				}

				// cell below
				if (isInBoard(i - 1, j) && allowedAdj(i, j, i - 1, j, DoorDirection.UP, DoorDirection.DOWN)) {
					neighbors.add(board[i - 1][j]);
				}

				// cell to the right
				if (isInBoard(i, j + 1) && allowedAdj(i, j, i, j + 1, DoorDirection.RIGHT, DoorDirection.LEFT)) {
					neighbors.add(board[i][j + 1]);
				}

				// cell to the left
				if (isInBoard(i, j - 1) && allowedAdj(i, j, i, j - 1, DoorDirection.LEFT, DoorDirection.RIGHT)) {
					neighbors.add(board[i][j - 1]);
				}
				adjMatrix.put(board[i][j], neighbors);
			}
		}
	}


	//checks that the cell is within the borders of the board
	private boolean isInBoard(int row, int column) {
		if(row >= 0 && row < numRows && column >= 0 && column < numColumns) {
			return true;
		}
		return false;
	}

	//checks what is necessary to make sure an adjacency is allowed
	private boolean allowedAdj(int i, int j, int nextI, int nextJ, DoorDirection directionOut, DoorDirection directionIn) {

		// Can go to walkway if not in room
		if (board[nextI][nextJ].isWalkway() && !board[i][j].isRoom()) {
			return true;
		}

		// Can get out of door if at the correct direction
		else if (board[i][j].isDoorway() && board[i][j].getDoorDirection() == directionOut) {
			return true;
		}

		// Can get into door if at the correct location
		else if (!board[i][j].isDoorway() && board[nextI][nextJ].getDoorDirection() == directionIn) {
			return true;
		}

		// If you are in the room part of the room (not the doorway), you stay in the room
		else if (board[i][j].isRoom() && !board[i][j].isDoorway()) {
			return false;
		}

		// if none of these it's not adjacent
		return false;

	}

	public void calcTargets(int row, int column, int pathLength){
		visited.clear(); //clear the visited set
		targets.clear(); //clear the targets set
		visited.add(board[row][column]);
		targets = findAllTargets(board[row][column], pathLength);
	}

	//this recursive function adds all of the cells that are targets for where the player currently is, based on whether a player can't love there based on the rules:
	//if they have already visited a cell they can't revisit, or if a cell is a doorway within their number of steps they can go there
	private Set<BoardCell> findAllTargets(BoardCell currentCell, int remainingSteps) {
		visited.add(currentCell);
		//new hash set of cells that have not been visited
		HashSet<BoardCell> adj = new HashSet<BoardCell>(adjMatrix.get(currentCell));
		for (BoardCell i:visited){
			adj.remove(i);
		}
		for (BoardCell i:adj){
			if (i.isDoorway() || remainingSteps == 1){
				targets.add(i);
			}
			else {
				findAllTargets(i, remainingSteps-1);
			}
			visited.remove(i);
		}
		return targets;
	}

	//returns the matrix of allowed adjacency cells
	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMatrix.get(board[i][j]);
	}


	//load players in (I think)
	//I think it could be to load in players and cards because they are both .txt files
	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {
		playersList = new ArrayList<Player>();
		humanPlayer = new HashSet<HumanPlayer>();
		BufferedReader reader = null;
		try {
			//read entire file and put into standard characters (we had some issues with special characters so we used BufferedReader)
			reader = new BufferedReader(new FileReader(playersConfigFile, StandardCharsets.UTF_8));
			String line = reader.readLine();
			while (line != null) {
				String[] splits = line.split(",");

				// makes sure we have every element for player
				if(splits[0].trim() == null) {
					throw new BadConfigFormatException("No name found");
				}
				if(splits[1].trim() == null) {
					throw new BadConfigFormatException("No color found");
				}
				if(splits[2].trim() == null) {
					throw new BadConfigFormatException("No row found");
				}
				if(splits[3].trim() == null) {
					throw new BadConfigFormatException("No column found");
				}

				// define all elements to put into constructor
				String name = splits[0].trim();
				String colorString = splits[1].trim();
				int row = Integer.parseInt(splits[2].trim());
				int column = Integer.parseInt(splits[3].trim());

				//convert string with color name into color
				Color color = convertColor(colorString);

				// Michael is human player
				if(name.equals("Michael Scott")){
					HumanPlayer player1 = new HumanPlayer(name, row, column, color);
					playersList.add(player1);
					humanPlayer.add(player1);
				}
				else {
					ComputerPlayer player2 = new ComputerPlayer(name, row, column, color);
					playersList.add(player2);
					computerPlayers.add(player2);
				}

				Card people = new Card (splits[0], CardType.PERSON);
				peoplePile.add(people);

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

	private void loadCards() throws FileNotFoundException {
		deck = new HashSet<>();
		cards = new Card[DECK_SIZE];
		FileReader fin = new FileReader(cardsConfigFile);
		Scanner in = new Scanner(fin);
		String temp;
		Card currentCard;

		//people cards
		for(int i = 0; i < NUM_PEOPLE; i++){
			temp = in.nextLine();
			currentCard = new Card(CardType.PERSON, temp);
			possiblePeople.add(currentCard);
			cards[i] = new Card(CardType.PERSON, temp);
		}
		//weapon cards
		for(int i = 0; i < NUM_WEAPONS; i++){
			temp = in.nextLine();
			Card weapon = new Card (temp, CardType.WEAPON);
			weaponsPile.add(weapon);
			possibleWeapons.add(weapon);
			currentCard = new Card(CardType.WEAPON, temp);
			cards[i+NUM_PEOPLE] = new Card(CardType.WEAPON, temp);
		}
		//room cards
		for(int i = 0; i < NUM_ROOMS; i++){
			temp = in.nextLine();
			currentCard = new Card(CardType.ROOM, temp);
			cards[i+(NUM_PEOPLE+NUM_WEAPONS)] = new Card(CardType.ROOM, temp);
		}
	}

	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined
		}
		return color;
	}

	//deal cards
	private void dealCards() {		
		Card[] backup = new Card[cards.length];
		for(int i = 0; i < DECK_SIZE; i++){
			backup[i] = cards[i];
		}

		//randomly pick solution
		Random rand = new Random();
		int solutionPlayer = rand.nextInt(NUM_PEOPLE);
		int solutionWeapon = rand.nextInt(NUM_WEAPONS) + NUM_PEOPLE;
		int solutionRoom = rand.nextInt(NUM_ROOMS) + (NUM_PEOPLE+NUM_WEAPONS);

		solution = new Solution(cards[solutionPlayer].getCardName(), cards[solutionWeapon].getCardName(), cards[solutionRoom].getCardName());
		Card remove = getCard(solution.getPerson(), CardType.PERSON);
		//cant give solution cards out
		cards[solutionPlayer] = null;
		cards[solutionWeapon] = null;
		cards[solutionRoom] = null;

		int cardsRemaining = DECK_SIZE;
		int cardIndex = 0;

		//gives cards that are not in the solution to the players
		while(cardsRemaining != 0) {
			for(int i=0; i<playersList.size(); i++) {
				if(cardIndex == DECK_SIZE) {
					break;
				}
				else if(cards[cardIndex] == null) {
					i--;
				}else {
					playersList.get(i).giveCard(cards[cardIndex]);
					deck.add(cards[cardIndex]);
					cards[cardIndex] = null;
				}
				cardsRemaining --;
				cardIndex++;
			}
		}


		for(int i = 0; i < cards.length ; i++){
			cards[i] = backup[i];
		}
	}



	public Card getCard(String name, CardType type) {
		for (Card card : deck) {
			if (card.getCardName().contentEquals(name) && card.getCardType().equals(type)) {
				return card;
			}

		}
		return null;
	}

	public Set<Card> getDeck(){
		return deck;
	}

	public Card[] getCards() {
		return cards;
	}

	public ArrayList<Player> getPlayerList(){
		return playersList;
	}
	//makes the solution for the game (I think...?)
	public void selectAnswer() {

	}

	//handles solution for tests, same logic is used later
	public Card handleSolution(Solution suggestion, String accusingPlayer, BoardCell clicked){
		int startIndex = 0;
		for(int i = 0; i < 6; i++){
			if(players[i].getName().equals(accusingPlayer)){
				startIndex = i;
				break;
			}
		}

		Card answer = null;
		for(int i = (startIndex+1)%(players.length); i != startIndex; i = (i+1)%(players.length)){
			answer = players[i].disproveSuggestion(suggestion);
			if (answer != null) return answer;
		}
		return answer;
	}


	//NEW
	public boolean checkAccusation(Solution accusation) {

		String p, w, r;   
		p = accusation.getPerson(); 
		w = accusation.getWeapon(); 
		r = accusation.getRoom(); 

		// check person 
		if (!solution.getPerson().equals(p)) {
			return false; 
		}
		// check weapon 
		if (!solution.getWeapon().equals(w)) {
			return false; 
		}
		// check room 
		if (!solution.getRoom().equals(r)) {
			return false; 
		}

		//if we have not returned false, then we have won
		gameFinished = true; 
		if(gameFinished) {
			JOptionPane.showMessageDialog(null, "Game Over!", "Message", JOptionPane.INFORMATION_MESSAGE);
		}

		// If no differences exist then returns true 
		return true;
	}


	public Solution getSolution() {
		return solution;
	}

	public void clearTargets() {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
	}


	//NEW FOR TESTS

	public Solution getAnswerKey() {
		return answerKey; 
	}

	public Set<String> getRooms() {
		return rooms;
	}

	public Set<ComputerPlayer> getComputerPlayers() {
		return computerPlayers;
	}

	public void setPlayers(ArrayList<Player> p) {
		this.playersList = new ArrayList<Player>();
		this.playersList = p;
	}

	//handles suggestion for tests, use same logic later
	public Card handleSuggestion(ArrayList<Player> players, Solution suggestion, Player accuser) {

		Card disproved = new Card();

		for (Player p: getPlayerList()) {
			// will never disprove yourself
			if(p == accuser) {
				continue;
			}

			disproved = p.disproveSuggestion(suggestion);
			if (disproved != null) {
				return disproved;
			} 

		}
		return null;
	}

	public Player whoIsTheCurrentPLayer() { 
		return currentPlayerInGame; // NOTE: Empty player was made to return when game first starts  
	}


	//C21A
	public void paintComponent (Graphics g) {

		super.paintComponent(g);

		//draws board
		for ( int i = 21; i >= 0; i--){
			for ( int j = 21; j >= 0; j--){
				getCellAt(i, j).draw(g);
			}
		}

		if(currentPlayerInGame instanceof HumanPlayer) {
			for (BoardCell cell: targets) {
				cell.drawTargets(g);
			}
		}

		//labels rooms
		g.setColor(Color.BLACK);
		g.drawString("Kitchen", 110, 60);
		g.drawString("Jim's Desk", 232, 50);
		g.drawString("Dwight's", 372, 50);
		g.drawString("Desk", 372, 65);
		g.drawString("Vance", 520, 50);
		g.drawString("Refrigeration", 500, 65);
		g.drawString("Front", 103, 195);
		g.drawString("Desk", 103, 280);
		g.drawString("Conference", 510, 240);
		g.drawString("Room", 525, 255);
		g.drawString("Michael's Office", 101, 450);
		g.drawString("Break Room", 305, 440);
		g.drawString("Warehouse", 500, 440);

		//draws players
		for(Player p: playersList) {
			p.draw(g);
		}


	}

	public Set<HumanPlayer> getHumanPlayer() {
		return humanPlayer;
	}

	public int currentDieRollValue() { 
		return dieRollValue; 
	}

	public void nextPlayerButtonMethod() {
		if (targetSelected) {
			//cycles through players starting with the human player
			if (currentPlayerInGameCount == -1 || currentPlayerInGameCount == 7) {
				currentPlayerInGameCount = 0;
				//allows them to accuse at the beginning of their turn
				hasNotAccused = true;
			}
			else {
				currentPlayerInGameCount ++; 
			}
			// NOTE: updating the current player 
			Player emptyPlayer = new Player();
			if (currentPlayerInGameCount == -1) {
				currentPlayerInGame = emptyPlayer;
			}
			else {
				currentPlayerInGame = playersList.get(currentPlayerInGameCount);
			}

			dieRollValue = rollDie();

			//play game
			GamePlay();

		}
		else {
			JOptionPane.showMessageDialog(null, "Take your turn", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public int rollDie() { 
		Random rand = new Random(); 
		int dieRoll = rand.nextInt(6) + 1; 
		return dieRoll;  
	}

	public void updateHumanPosition(Player player) { 
		//pick target
		addMouseListener(new TargetListener());

		//done with turn
		doneWithHuman = true;

		//make sure everything is repainted
		repaint();
	}


	public void updateComputerPosition(int col, int row, int pathlength, Player player) { 
		//if the computer was just in a room, they will not go back in
		if(!getCellAt(row,col).isWalkway()) {
			player.setLastRoom(getCellAt(row,col));
		}

		//calculate targets and then use previous function to pick a new location
		calcTargets(row, col, pathlength); 
		BoardCell newLoc = player.pickLocation(getTargets());

		//update location
		player.updatePosition(newLoc.getRow(), newLoc.getCol());

		//if they are in a room they make a guess and guess gets handled properly
		if(newLoc.isDoorway()) {
			player.createSuggestion(newLoc, possiblePeople, possibleWeapons, legend, player);
			player.updateGuess(player.getCreatedSoln().getPerson() + ", " + player.getCreatedSoln().getRoom() + ", " + player.getCreatedSoln().getWeapon());
			handleSuggestion(player);
		}

		//show it on board
		repaint(); 

		//computer turn over
		doneWithComputer = true;
	}	


	public void GamePlay() {
		int row = currentPlayerInGame.getRow(); 
		int col = currentPlayerInGame.getColumn();
		
		//if human player turn
		if (currentPlayerInGame instanceof HumanPlayer){
			//must take turn
			doneWithHuman = false;
			targetSelected = false; 

			//find targets and then show them
			calcTargets(row, col, currentDieRollValue());
			repaint();

			//update position
			updateHumanPosition(currentPlayerInGame); 

			repaint();
		}

		//if computer player turn
		else {
			//must take turn
			doneWithComputer = false;
			
			//resets guess so it will not keep displaying previous guess
			currentPlayerInGame.updateGuess("no guess");
			currentPlayerInGame.updateResult("no result");
			
			//if computer is ready to accuse, they accuse
			if(currentPlayerInGame.isAccusationReady()) {
				if (checkAccusation(currentPlayerInGame.getAccusation()) == false ) { 
					incorrectAccusation(currentPlayerInGame.getAccusation());  
				}
				else { 
					correctAccusation(currentPlayerInGame.getAccusation());
				}
			}
			
			//update position
			updateComputerPosition(col, row, currentDieRollValue(), currentPlayerInGame);
			repaint();

		}

	}

	public void handleSuggestion(Player player) {
		ArrayList<Card> foundCards = new ArrayList<Card>(); 

		for(Player tempPlayer: getPlayerList()) {
			if (tempPlayer == player) {
				continue;  
			}
			else { 
				// if a card is found by another player, the card is added to the ArrayList of cards
				Card temp = tempPlayer.disproveSuggestion(player.getCreatedSoln()); 
				if(temp != null) {
					foundCards.add(temp);
				}
			}
		}

		//selecting a random number for selecting a found Card
		Random rand = new Random(); 

		//if did not disprove
		if (foundCards.size() == 0) { 
			player.setAccusation(player.getCreatedSoln());
			player.updateResult("no new clue");
			
		}
		//show a disproved card if one was disproved
		else { 
			int location = rand.nextInt(foundCards.size()); 
			player.giveCard(foundCards.get(location));
			player.updateResult(foundCards.get(location).getCardName());
		}

	}

	public void closeMyFrame() { 
		myFrame.setVisible(false);
		myFrame.dispose();
	}

	private class TargetListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {

			if (targetSelected == false && inWindow == false){
				//puts the cell that was clicked in whichbox
				BoardCell whichBox = null;
				for (int i = 0; i < 22; i++){
					for (int j = 0; j < 22; j++){
						if (getCellAt(i,j).containsClick(e.getY(), e.getX())){
							whichBox = getCellAt(i, j);
							break;
						}

					}
				}
				//checks to see if the box is a target
				if (whichBox != null){
					//if it is, update location and end turn
					if (targets.contains(whichBox)) {
						//can only make accusation at the beginning of your turn
						hasNotAccused = false;
						selectedBox = whichBox;
						clickRow = selectedBox.getRow();
						clickCol = selectedBox.getCol();
						currentPlayerInGame.updatePosition(clickRow, clickCol);
						targetSelected = true;

						//suggestion box when in room
						if (whichBox.isDoorway()) {
							doneWithHuman = false;
							inWindow = true; 

							myFrame = new JFrame("Make a Guess");
							myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

							try {
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							} catch (Exception exc) {
								exc.printStackTrace();
							}


							char i = whichBox.getInitial(); 
							String currentRoom = ""; 
							for (String temp : rooms) { 
								if(i == temp.charAt(0)) { 
									currentRoom = temp;
									break;
								}
							}
							JPanel myPanel = new JPanel();
							suggest = new Suggestion(currentRoom); 

							myPanel = suggest; 


							myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
							myPanel.setOpaque(true);

							JTextArea text = new JTextArea(20, 5);
							text.setEditable(false);
							text.setFont(Font.getFont(Font.SANS_SERIF));
							JPanel input = new JPanel(); 
							input.setLayout(new FlowLayout()); 
							myPanel.add(input);

							myFrame.getContentPane().add(BorderLayout.CENTER, myPanel); 
							myFrame.pack();
							myFrame.setLocationByPlatform(true);
							myFrame.setVisible(true);
							myFrame.setResizable(false);
							
							inWindow = false;
						}

					}
					//otherwise error message
					//this message pops up multiple times sometimes, not sure why
					else {
						JOptionPane.showMessageDialog(null, "That is not a target", "Message", JOptionPane.INFORMATION_MESSAGE);
					}
					//display changes!
					repaint();
				}
			}
		}
	}

	public void incorrectAccusation(Solution soln) { 
		String message = "Accusation made. Incorrect guess. " + soln.getPerson() + ", " + soln.getWeapon() + ", " + soln.getRoom() + " was not the answer. "; 
		JOptionPane.showMessageDialog(null, message);
	}

	public void correctAccusation(Solution soln) { 
		String message = "Accusation made. You win! " + soln.getPerson() + ", " + soln.getWeapon() + ", " + soln.getRoom() + " was the correct answer!"; 
		JOptionPane.showMessageDialog(null, message);
	}

	//C24A
	public String whatIsTheCurrentGuess() { 
		return currentGuess; 
	}

	public Suggestion passCurrentSuggestionState() { 
		return suggest; 
	}

	public String whatIsTheCurrentResult() { 
		return currentResults; 
	}

}