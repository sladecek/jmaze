package com.github.sladecek.maze.jmaze;

import java.io.IOException;

public class MarkShape implements IMazeShape {
	
	private ShapeType shapeType;

	MarkShape(ShapeType type, int y, int x) {
		this.shapeType = type;
		this.y = y;
		this.x = x;
	}

	public ShapeType getShapeType() {
		return this.shapeType;
	}

	public void printToSvg(SvgMazePrinter svg) throws IOException  {
		switch (shapeType)
		{
		case startRoom:
			svg.printMark(y, x, "red");		
			break;
		case targetRoom:
			svg.printMark(y, x, "green");
			break;
			
		}
	}

	private int x;
	private int y;

}
