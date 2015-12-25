package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;

public final class MarkShape implements IMazeShape {
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private ShapeType shapeType;

	public MarkShape(ShapeType type, int y, int x, String id) {
		this.shapeType = type;
		this.y = y;
		this.x = x;
		this.id = id;
	}

	public ShapeType getShapeType() {
		return this.shapeType;
	}


	@Override
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "MarkShape [shapeType=" + shapeType + ", x=" + x + ", y=" + y
				+ "]";
	}

	public void printToSvg(SvgMazePrinter svg)   {
		switch (shapeType) {
		case startRoom:
			svg.printMark(y, x, "red", 25);		
			break;
		case targetRoom:
			svg.printMark(y, x, "green", 25);
			break;
		case solution:
			svg.printMark(y, x, "gray", 15);
			break;
		default:
			break;			
		}
	}

	private int x;
	private int y;
	private String id;

}
