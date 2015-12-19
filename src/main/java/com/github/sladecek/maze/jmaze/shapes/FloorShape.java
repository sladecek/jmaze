package com.github.sladecek.maze.jmaze.shapes;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;

/// Represents hole in the ground of the maze or the lack of thereof.
/// The user usually prints either holes or non-holes depending on output
/// type.
public final class FloorShape implements IMazeShape {


	public FloorShape(int y, int x, boolean isHole, String id) {
		super();
		this.y = y;
		this.x = x;
		this.isHole = isHole;
		this.id = id;
		LOG.log(Level.INFO, "FloorShape id=" + id + " x=" + x + " y=" + y);
	}

	@Override
	public ShapeType getShapeType() {
		if (isHole) {
			return  ShapeType.hole;
		} else {
			return ShapeType.nonHole;
		}
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
	
	@Override
	public String getId() {
		return id;
	}


	public boolean isHole() {
		return isHole;
	}


	@Override
	public void printToSvg(SvgMazePrinter svg) throws IOException {
		if (isHole) {
			String style = "stroke:rgb(222,222,222);stroke-width:1";
			svg.printLine(y, x, y + 1, x + 1, style, false);
			svg.printLine(y + 1, x, y, x + 1, style, false);
		}
	}
	
	@Override
	public String toString() {
		return "FloorShape [id=" + id + " y=" + y + ", x=" + x + ", isHole=" + isHole + "]";
	}

	private static final Logger LOG =  Logger.getLogger("maze.jmaze");

	private int y;
	private int x;
	private boolean isHole;
	private String id;
	
	
	public MarkShape CreateMarkInThisRoom(ShapeType type, String id) {
		return new MarkShape(type, y, x, id);
	}
	


}
