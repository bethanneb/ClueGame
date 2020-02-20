package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import experiment.IntBoard;

public class IntBoardTests {

	@Before 
	public void beforeAll() {
		board = new IntBoard(); //constructor should call calcAjacencies() so you can test them
		
	}
	
	@Test
	public void testCalcAjacencyTopLeft() {
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testCalcAjacencyBottomRight() {
		BoardCell cell = board.getCell(21,21);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(21, 20)));
		assertTrue(testList.contains(board.getCell(20, 21)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testCalcAjacencyRightEdge() {
		BoardCell cell = board.getCell(12,21);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(11, 21)));
		assertTrue(testList.contains(board.getCell(13, 21)));
		assertTrue(testList.contains(board.getCell(12, 20)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testCalcAjacencyLeftEdge() {
		BoardCell cell = board.getCell(12,21);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(11, 21)));
		assertTrue(testList.contains(board.getCell(13, 21)));
		assertTrue(testList.contains(board.getCell(12, 20)));
		assertEquals(3, testList.size());
	}

}
