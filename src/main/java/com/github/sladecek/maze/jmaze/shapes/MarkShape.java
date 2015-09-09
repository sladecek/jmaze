package com.github.sladecek.maze.jmaze.shapes;

import java.io.IOException;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;

public class MarkShape implements IMazeShape {
	
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
	private String id;

	@Override
	public String toString() {
		return "MarkShape [shapeType=" + shapeType + ", x=" + x + ", y=" + y
				+ "]";
	}

	@Override
	public boolean isOpen(MazeRealization real) {
		return true;
	}

	@Override
	public String getId() {
		return id;
	}


}
