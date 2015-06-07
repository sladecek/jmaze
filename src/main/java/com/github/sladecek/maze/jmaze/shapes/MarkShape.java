package com.github.sladecek.maze.jmaze.shapes;

import java.io.IOException;

import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;

public class MarkShape implements IMazeShape {
	
	private ShapeType shapeType;

	public MarkShape(ShapeType type, int y, int x) {
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
