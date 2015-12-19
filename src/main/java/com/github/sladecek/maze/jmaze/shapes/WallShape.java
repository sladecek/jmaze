package com.github.sladecek.maze.jmaze.shapes;

import java.io.IOException;

import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;

public final class WallShape implements IMazeShape {
	
	public WallShape(ShapeType type, int y1, int x1, int y2, int x2) {
		this.shapeType = type;
		this.y1 = y1;
		this.y2 = y2;
		this.x1 = x1;
		this.x2 = x2;
		this.wallId = -1;
	}

	/* TODO smazat
	public int getWallId() {
		return wallId;
	}
*/
	/* TODO smazat
	public void setWallId(int value) {
		this.wallId = value;
		LOG.log(Level.INFO, "WallShape id=" + wallId + " x1=" + x1 + " y1=" + y1 + " x2=" + x2 + " y2=" + y2);
	}
	*/

	public ShapeType getShapeType() {
		return this.shapeType;
	}

	public void printToSvg(SvgMazePrinter svg) throws IOException  {		
		String style = "";
		boolean center = false;
		switch (shapeType) {		
		case outerWall:
			style = "stroke:rgb(0,0,0);stroke-width:2";
			break;
		case innerWall:
			style = "stroke:rgb(0,0,0);stroke-width:1";		
			break;
		case solution:
			style = "stroke:rgb(255,0,0);stroke-width:2";
			center = true;
			break;
		case auxiliaryWall:
			style = "stroke:rgb(240,240,240);stroke-width:1";
			break;
		default:
			break;
			
		}
		svg.printLine(y1, x1, y2, x2, style, center);		
	}

	
	public int getX1() {
		return x1;
	}

	public int getX2() {
		return x2;
	}

	public int getY1() {
		return y1;
	}

	public int getY2() {
		return y2;
	}
/*TODO 

	@Override
	public boolean isOpen(MazeRealization real) {
		if (shapeType != ShapeType.innerWall) {		
			return false;
		}
		if (this.wallId < 0) {
			return true;
		}
		return !real.isWallClosed(this.wallId);
	}
	*/
	@Override
	public String getId() {
		return "w" + Integer.toString(wallId);
	}

	@Override
	public String toString() {
		return "WallShape [id=" + wallId + " shapeType=" + shapeType + ", x1=" + x1 + ", x2="
				+ x2 + ", y1=" + y1 + ", y2=" + y2 + "]";
	}

// TODO 	private static final Logger LOG =  Logger.getLogger("maze.jmaze");
	private int x1;
	private int x2;
	private int y1;
	private int y2;

	private ShapeType shapeType;

	/* TODO smazat */
	private int wallId;
	
	
}
