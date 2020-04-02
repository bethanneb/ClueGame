package tests;

import java.util.ArrayList;

import org.junit.BeforeClass;

import clueGame.Board;
import clueGame.Player;

public class gameActionTests {
	//static so that it only has to be loaded one time
	private static Board board;
	//static so we only have one copy of the ArrayList
	private static ArrayList<Player> playersList;

	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("OurClueLayout.csv", "OurClueLegend.txt");	
		// set the file names to use my config files
		board.setCardFiles("Players.txt", "Cards.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
		playersList = board.getPlayerList();
	}
	
	public void testCompPlayerLocation() {
		
	}
	

}
