package com.github.sladecek.maze.jmaze.shapes;

import java.io.IOException;

import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;

/// Represent hole in the ground of the maze or the lack of thereof.
/// The user usually prints either holes or non-holes depending on output
/// type.
public class FloorShape implements IMazeShape {

	private int y;
	private int x;
	private boolean isHole;
	
	public boolean isHole() {
		return isHole;
	}

	public FloorShape(int y, int x, boolean isHole) {
		super();
		this.y = y;
		this.x = x;
		this.isHole = isHole;
	}

	@Override
	public ShapeType getShapeType() {
		return isHole ? ShapeType.hole : ShapeType.nonHole;
	}

	@Override
	public void printToSvg(SvgMazePrinter svg) throws IOException {
		if (isHole) {
			String style = "stroke:rgb(222,222,222);stroke-width:1";
			svg.printLine(y, x, y+1, x+1, style, false);
			svg.printLine(y+1, x, y, x+1, style, false);
		}
	}

	public int getY() {
		return y;
	}


	public int getX() {
		return x;
	}

	@Override
	public String toString() {
		return "FloorShape [y=" + y + ", x=" + x + ", isHole=" + isHole + "]";
	}



}
