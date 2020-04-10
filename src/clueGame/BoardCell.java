/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	
	//NEW
	private Point pixel;
	private Color color; 
	// 22 rows, 23 cols
	public final int MARGIN = 50;
	public final int SCALE = 32;
	private final int WIDTH = 30;
	private final int HEIGHT = 30;

	// default constructor
	public BoardCell() {
		this.row = 0;
		this.column = 0;
		this.initial = 'P';
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
		pixel = new Point (this.row * SCALE + MARGIN, this.column * SCALE + MARGIN);
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
		return this.row;
	}

	public void setRow(int row) 
	{
		this.row = row;
	}

	public int getCol() 
	{
		return this.column;
	}

	public void setCol(int col) 
	{
		this.column = col;
	}

	public void setInitial(char initial) 
	{
		this.initial = initial;
	}
	
	//C21A
	public void draw ( Graphics g ) {
		// TODO implement
		// set color 
		if (this.initial == 'P') 
		{
			this.color = Color.GRAY;
			g.setColor(this.color);
			g.fillRect(this.pixel.x , this.pixel.y, WIDTH, HEIGHT);
		}
		if (this.isRoom()) 
		{
			if ( isDoorway())
			{
				if (doorDirection == DoorDirection.UP)
				{
					this.color = Color.WHITE;
					g.setColor(this.color);
					g.fillRect(this.pixel.x, this.pixel.y, WIDTH, HEIGHT);
					g.setColor(Color.YELLOW);
					g.fillRect(this.pixel.x, this.pixel.y, WIDTH, 5);
				}
				else if (doorDirection == DoorDirection.DOWN)
				{
					this.color = Color.WHITE;
					g.setColor(this.color);
					g.fillRect(this.pixel.x, this.pixel.y, WIDTH, HEIGHT);
					g.setColor(Color.YELLOW);
					g.fillRect(this.pixel.x, this.pixel.y + 25, WIDTH, 5);
				}
				else if (doorDirection == DoorDirection.LEFT)
				{
					this.color = Color.WHITE;
					g.setColor(this.color);
					g.fillRect(this.pixel.x, this.pixel.y, WIDTH, HEIGHT);
					g.setColor(Color.YELLOW);
					g.fillRect(this.pixel.x, this.pixel.y, 5,HEIGHT);
				}
				else if (doorDirection == DoorDirection.RIGHT)
				{
					this.color = Color.WHITE;
					g.setColor(this.color);
					g.fillRect(this.pixel.x, this.pixel.y, WIDTH, HEIGHT);
					g.setColor(Color.YELLOW);
					g.fillRect(this.pixel.x + 25, this.pixel.y, 5,HEIGHT);
				}

			}
			else
			{
				this.color = Color.WHITE;
				g.setColor(this.color);
				g.fillRect(this.pixel.x, this.pixel.y, WIDTH, HEIGHT);
			}


		}
		if (this.initial == 'K') 
		{
			this.color = Color.GREEN;
			g.setColor(this.color);
			g.fillRect(this.pixel.x, this.pixel.y, WIDTH, HEIGHT);
		}


	}
	
	public void drawTargets( Graphics g) {
		this.color = Color.CYAN;
		g.setColor(this.color);
		g.fillRect(this.pixel.x, this.pixel.y, WIDTH, HEIGHT);
	}
	
	public void reDrawTargets ( Graphics g) {
		if ( isDoorway())
		{		
			this.color = Color.WHITE;
			g.setColor(this.color);
			g.fillRect(this.pixel.x, this.pixel.y, WIDTH, HEIGHT);
		}
		if (this.initial == 'P') 
		{
			this.color = Color.GRAY;
			g.setColor(this.color);
			g.fillRect(this.pixel.x , this.pixel.y, WIDTH, HEIGHT);
		}
	}

}