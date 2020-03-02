//Authors: Elizabeth Bauch and Danella Bunavi

package tests;

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class TestsPart3 {

	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("OurClueLayout.csv", "OurClueLegend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	//From my understanding, these tests use (column, row) format
	// Ensure that player does not move around within room
	// These cells are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms() {
		// Test top right corner
		Set<BoardCell> testList = board.getAdjList(0, 21);
		assertEquals(0, testList.size());
		// Test a cell that has a walkway underneath
		testList = board.getAdjList(12, 0);
		assertEquals(0, testList.size());
		// Test a cell that has a walkway above
		testList = board.getAdjList(16, 17);
		assertEquals(0, testList.size());
		// Test a cell that is in the middle of the room
		testList = board.getAdjList(2, 13);
		assertEquals(0, testList.size());
		// Test a cell that's beside a door
		testList = board.getAdjList(2, 7);
		assertEquals(0, testList.size());
		// Test a cell in a corner of the room
		testList = board.getAdjList(4, 0);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway.
	// These tests are WHITE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(3,2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(3,3)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(13, 18);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 17)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(4, 13);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 13)));
		//TEST DOORWAY UP
		testList = board.getAdjList(16, 10);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 10)));
		//TEST DOORWAY RIGHT, WHERE THERE'S A WALKWAY BELOW
		testList = board.getAdjList(2, 8);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(2, 9)));

	}

	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(3, 3);
		assertTrue(testList.contains(board.getCellAt(3, 2)));
		assertTrue(testList.contains(board.getCellAt(3, 4)));
		assertTrue(testList.contains(board.getCellAt(4, 3)));
		assertTrue(testList.contains(board.getCellAt(2, 3)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(4, 19);
		assertTrue(testList.contains(board.getCellAt(4, 18)));
		assertTrue(testList.contains(board.getCellAt(4, 20)));
		assertTrue(testList.contains(board.getCellAt(5, 19)));
		assertTrue(testList.contains(board.getCellAt(3, 19)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(18, 16);
		assertTrue(testList.contains(board.getCellAt(17, 16)));
		assertTrue(testList.contains(board.getCellAt(19, 16)));
		assertTrue(testList.contains(board.getCellAt(18, 17)));
		assertTrue(testList.contains(board.getCellAt(18, 15)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(7, 21);
		assertTrue(testList.contains(board.getCellAt(6, 21)));
		assertTrue(testList.contains(board.getCellAt(8, 21)));
		assertTrue(testList.contains(board.getCellAt(7, 20)));
		assertEquals(3, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, two walkway pieces
		Set<BoardCell> testList = board.getAdjList(0, 15);
		assertTrue(testList.contains(board.getCellAt(1, 15)));
		assertTrue(testList.contains(board.getCellAt(0, 16)));
		assertEquals(2, testList.size());
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(15, 0);
		assertTrue(testList.contains(board.getCellAt(14, 0)));
		assertTrue(testList.contains(board.getCellAt(16, 0)));
		assertTrue(testList.contains(board.getCellAt(15, 1)));
		assertEquals(3, testList.size());
		// Test surrounded by 4 walkways
		testList = board.getAdjList(10,15);
		assertTrue(testList.contains(board.getCellAt(10, 16)));
		assertTrue(testList.contains(board.getCellAt(10, 14)));
		assertTrue(testList.contains(board.getCellAt(9, 15)));
		assertTrue(testList.contains(board.getCellAt(11, 15)));
		assertEquals(4, testList.size());
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(21, 4);
		assertTrue(testList.contains(board.getCellAt(21, 5)));
		assertTrue(testList.contains(board.getCellAt(20, 4)));
		assertEquals(2, testList.size());
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(15, 21);
		assertTrue(testList.contains(board.getCellAt(14, 21)));
		assertTrue(testList.contains(board.getCellAt(15, 20)));
		assertEquals(2, testList.size());
		// Test on walkway next to door that is not in the needed
		// direction to enter
		testList = board.getAdjList(8, 2);
		assertTrue(testList.contains(board.getCellAt(7, 2)));
		assertTrue(testList.contains(board.getCellAt(9, 2)));
		assertTrue(testList.contains(board.getCellAt(8, 3)));
		assertEquals(3, testList.size());
	}

	// Tests of just walkways, 1 step, includes bottom and right edge of board
	// These are YELLOW on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(21, 8, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 8)));
		assertTrue(targets.contains(board.getCellAt(21, 7)));	

		board.calcTargets(5, 21, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 21)));
		assertTrue(targets.contains(board.getCellAt(6, 21)));	
		assertTrue(targets.contains(board.getCellAt(5, 20)));			
	}

	// Tests of just walkways, 2 steps
	// These are YELLOW on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(21, 8, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 8)));
		assertTrue(targets.contains(board.getCellAt(21, 6)));
		assertTrue(targets.contains(board.getCellAt(20, 7)));

		board.calcTargets(5, 21, 2);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 20)));
		assertTrue(targets.contains(board.getCellAt(5, 19)));	
		assertTrue(targets.contains(board.getCellAt(6, 20)));	
		assertTrue(targets.contains(board.getCellAt(7, 21)));
	}

	// Tests of just walkways, 4 steps
	// These are YELLOW on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(21, 8, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 7)));
		assertTrue(targets.contains(board.getCellAt(19, 6)));
		assertTrue(targets.contains(board.getCellAt(20, 5)));
		assertTrue(targets.contains(board.getCellAt(21, 4)));
		assertTrue(targets.contains(board.getCellAt(21, 6)));
		assertTrue(targets.contains(board.getCellAt(20, 7)));
		assertTrue(targets.contains(board.getCellAt(19, 8)));

		// Includes a path that doesn't have enough length
		board.calcTargets(5, 21, 4);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 19)));
		assertTrue(targets.contains(board.getCellAt(3, 20)));
		assertTrue(targets.contains(board.getCellAt(4, 20)));
		assertTrue(targets.contains(board.getCellAt(4, 18)));	
		assertTrue(targets.contains(board.getCellAt(5, 17)));
		assertTrue(targets.contains(board.getCellAt(5, 19)));	
		assertTrue(targets.contains(board.getCellAt(6, 18)));
		assertTrue(targets.contains(board.getCellAt(6, 20)));
		assertTrue(targets.contains(board.getCellAt(7, 19)));
		assertTrue(targets.contains(board.getCellAt(7, 21)));
		assertTrue(targets.contains(board.getCellAt(8, 21)));
	}	

	// Tests of just walkways plus one door, 6 steps
	// These are YELLOW on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(21, 8, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 4)));
		assertTrue(targets.contains(board.getCellAt(21, 6)));
		assertTrue(targets.contains(board.getCellAt(19, 8)));
		assertTrue(targets.contains(board.getCellAt(18, 7)));	
		assertTrue(targets.contains(board.getCellAt(20, 7)));	
		assertTrue(targets.contains(board.getCellAt(17, 6)));	
		assertTrue(targets.contains(board.getCellAt(19, 6)));	
		assertTrue(targets.contains(board.getCellAt(18, 5)));	
		assertTrue(targets.contains(board.getCellAt(20, 5)));
		assertTrue(targets.contains(board.getCellAt(19, 4)));

	}	

	// Test getting into a room
	// These are YELLOW on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(20, 14, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		// directly left
		assertTrue(targets.contains(board.getCellAt(20, 12)));
		// directly up
		assertTrue(targets.contains(board.getCellAt(18, 14)));
		//assertTrue(targets.contains(board.getCellAt(21, 14)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(21, 15)));
		assertTrue(targets.contains(board.getCellAt(21, 13)));
		assertTrue(targets.contains(board.getCellAt(19, 13)));
		assertTrue(targets.contains(board.getCellAt(19, 15)));
		assertTrue(targets.contains(board.getCellAt(20, 16)));
	}

	// Test getting into room, doesn't require all steps
	// These are YELLOW on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(1, 3, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(10, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(4, 3)));
		// up, then right, then down
		assertTrue(targets.contains(board.getCellAt(1, 4)));
		// up then right
		assertTrue(targets.contains(board.getCellAt(0, 5)));
		//right 2, then down 1
		assertTrue(targets.contains(board.getCellAt(2, 5)));
		// right 1, down 2
		assertTrue(targets.contains(board.getCellAt(3, 4)));
		//right 1, down 1, left 1
		assertTrue(targets.contains(board.getCellAt(2, 3)));
		//right 1, up 1, left 1
		assertTrue(targets.contains(board.getCellAt(0, 3)));
		// into the room
		assertTrue(targets.contains(board.getCellAt(3, 2)));
		assertTrue(targets.contains(board.getCellAt(2, 2)));		 
		assertTrue(targets.contains(board.getCellAt(1, 2)));		


	}

	// Test getting out of a room
	// These are YELLOW on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(3, 20, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 20)));
		// Take two steps
		board.calcTargets(3, 20, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 19)));
		assertTrue(targets.contains(board.getCellAt(5, 20)));
		assertTrue(targets.contains(board.getCellAt(4, 21)));
	}

}