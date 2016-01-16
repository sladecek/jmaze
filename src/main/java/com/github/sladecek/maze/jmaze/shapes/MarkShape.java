package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.print2d.SvgMazePrinter;

public final class MarkShape implements IMazeShape {
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private ShapeType shapeType;

	public MarkShape(ShapeType type, int y, int x) {
		this.shapeType = type;
		this.y = y;
		this.x = x;

	}

	public ShapeType getShapeType() {
		return this.shapeType;
	}

	@Override
	public String toString() {
		return "MarkShape [shapeType=" + shapeType + ", x=" + x + ", y=" + y
				+ "]";
	}
	
	

	public void setOffsetXPercent(int offsetXPercent) {
		this.offsetXPercent = offsetXPercent;
	}

	public void setOffsetYPercent(int offsetYPercent) {
		this.offsetYPercent = offsetYPercent;
	}

	@Override
	public void printToSvg(SvgMazePrinter svg, boolean polarCoordinates)   {
		
		switch (shapeType) {
		case startRoom:
			svg.printMark(y, x, "red", 25, offsetXPercent, offsetYPercent);		
			break;
		case targetRoom:
			svg.printMark(y, x, "green", 25, offsetXPercent, offsetYPercent);
			break;
		case solution:
			svg.printMark(y, x, "gray", 15, offsetXPercent, offsetYPercent);
			break;
		default:
			break;			
		}
	}

	private int x;
	private int y;
	

	private int offsetXPercent = 50;
	private int offsetYPercent = 50;
}
