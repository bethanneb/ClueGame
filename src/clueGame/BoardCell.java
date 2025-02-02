/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	
	//NEW
	private Point pixel;
	private Color color; 
	public final int SCALE = 22;
	private final int WIDTH = 20;
	private final int HEIGHT = 20;

	// default constructor
	public BoardCell() {
		this.row = 0;
		this.column = 0;
		this.initial = 'W';
		this.doorDirection = DoorDirection.NONE;
		pixel = new Point( this.row, this.column);
		this.color = Color.BLACK;
	} 

	// constructor with parameters
	public BoardCell(int row, int column, char initial, DoorDirection doorDirection) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDirection = doorDirection;
		pixel = new Point (this.row * SCALE + 15, this.column * SCALE + 100);
		this.color = Color.BLACK;
	}


	public boolean isWalkway() {
		if(initial == 'W') {
			return true;
		}
		return false;
	}

	public boolean isRoom() {
		if(initial == 'W' || initial == 'X') {
			return false;
		}
		return true;
	}

	public boolean isDoorway() {
		if(doorDirection != DoorDirection.NONE) {
			return true;
		}
		return false;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return initial;
	}
	
	//NEW
	public int getRow() 
	{
		return row;
	}

	public void setRow(int row) 
	{
		this.row = row;
	}

	public int getCol() 
	{
		return column;
	}

	public void setCol(int col) 
	{
		column = col;
	}

	public void setInitial(char initial) 
	{
		this.initial = initial;
	}
	
	//C21A
	public void draw (Graphics g ) {
		//walkways are grey
		if (this.initial == 'W') {
			this.color = Color.GRAY;
			g.setColor(this.color);
			g.fillRect(this.pixel.y , this.pixel.x, WIDTH, HEIGHT);
		}
		if (isRoom()) {
			//display where doors are
			if (isDoorway()){
				this.color = Color.WHITE;
				if (doorDirection == DoorDirection.UP){
					g.setColor(this.color);
					g.fillRect(this.pixel.y, this.pixel.x, WIDTH, HEIGHT);
					g.setColor(Color.BLUE);
					g.fillRect(this.pixel.y, this.pixel.x, WIDTH, 4);
				}
				else if (doorDirection == DoorDirection.DOWN){
					g.setColor(this.color);
					g.fillRect(this.pixel.y, this.pixel.x, WIDTH, HEIGHT);
					g.setColor(Color.BLUE);
					g.fillRect(this.pixel.y, this.pixel.x + 15, WIDTH, 4);
				}
				else if (doorDirection == DoorDirection.LEFT){
					g.setColor(this.color);
					g.fillRect(this.pixel.y, this.pixel.x, WIDTH, HEIGHT);
					g.setColor(Color.BLUE);
					g.fillRect(this.pixel.y, this.pixel.x, 4,HEIGHT);
				}
				else if (doorDirection == DoorDirection.RIGHT){
					g.setColor(this.color);
					g.fillRect(this.pixel.y, this.pixel.x, WIDTH, HEIGHT);
					g.setColor(Color.BLUE);
					g.fillRect(this.pixel.y + 15, this.pixel.x, 4,HEIGHT);
				}

			}
			else{
				this.color = Color.WHITE;
				g.setColor(this.color);
				g.fillRect(this.pixel.y, this.pixel.x, WIDTH, HEIGHT);
			}


		}
		//closet is green
		if (this.initial == 'X') {
			this.color = Color.GREEN;
			g.setColor(this.color);
			g.fillRect(this.pixel.y, this.pixel.x, WIDTH, HEIGHT);
		}


	}
	
	//targets are different color
	public void drawTargets( Graphics g) {
		this.color = Color.CYAN;
		g.setColor(this.color);
		g.fillRect(this.pixel.y, this.pixel.x, WIDTH, HEIGHT);
	}
	

	public boolean containsClick(int mouseX, int mouseY) {
		Rectangle rect = new Rectangle(pixel.x, pixel.y, WIDTH, HEIGHT);
		if (rect.contains(new Point(mouseX, mouseY))){
			return true;
		}
		else{
			return false;
		}
	}

}