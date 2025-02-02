//Authors: Elizabeth Bauch and Danella Bunavi
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class TestsPart1 {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11; //our legend size is also 11
	//number of rows and columns are the same
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 22;

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
	
	@Test
	public void testRoomsAndLegendFile() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Conference Room", legend.get('C'));
		assertEquals("Warehouse", legend.get('H'));
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Michael�s Office", legend.get('M'));
		assertEquals("Dwight�s Desk", legend.get('D'));
	}

	@Test
	public void testRowsAndColumns() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}


	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet

	//Test RIGHT direction
	@Test
	public void TestRightDoorDirection() {
		//kitchen has 3 doorways that open RIGHT
		BoardCell room = board.getCellAt(1, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(2, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(3, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		//Jim's desk
		room = board.getCellAt(2, 8);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());	

	}

	//Test left direction
	@Test
	public void TestLeftDoorDirection() {
		BoardCell room = board.getCellAt(1, 2);
		//Warehouse
		room = board.getCellAt(19, 17);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
	}

	//Test up direction
	@Test
	public void TestUpDoorDirection() {
		BoardCell room = board.getCellAt(1, 2);
		//Break Room
		room = board.getCellAt(16, 10);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
	}


	//Test down direction
	@Test
	public void TestDownDoorDirection() {
		BoardCell room = board.getCellAt(1, 2);
		//Vance Refrigeration
		room = board.getCellAt(3, 19);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
	}


	//Test cells that are not doorways know that they are not doorways
	@Test
	public void TestNotDoorways() {
		BoardCell room = board.getCellAt(1, 2);
		// Test that room pieces that aren't doors know it
		//Cell right next to Break Room door in break room
		room = board.getCellAt(9, 16);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		//walkway cell between Break Room and Michael's office
		BoardCell cell = board.getCellAt(6, 21);
		assertFalse(cell.isDoorway());	
	}




	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(17, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		//Test cell in kitchen
		assertEquals('K', board.getCellAt(1, 2).getInitial());
		//Test cell at Jim's Desk
		assertEquals('F', board.getCellAt(7, 0).getInitial());
		//Test cell at Dwight's Desk
		assertEquals('W', board.getCellAt(14, 5).getInitial());
		//Test bottom left cell in Michael's Office
		assertEquals('V', board.getCellAt(0, 21).getInitial());
		//Test top right corner in Vance Refrigeration room
		assertEquals('M', board.getCellAt(21, 0).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(14, 17).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(9,11).getInitial());
	}


}