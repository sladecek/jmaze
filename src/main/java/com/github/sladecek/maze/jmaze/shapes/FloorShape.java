package com.github.sladecek.maze.jmaze.shapes;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;

/// Represents hole in the ground of the maze or the lack of thereof.
/// The user usually prints either holes or non-holes depending on output
/// type.
public final class FloorShape implements IMazeShape {

	public FloorShape(int y, int x, boolean isHole) {
		super();
		this.center = new Point2D(x, y);

		this.isHole = isHole;
		LOG.log(Level.INFO, "FloorShape  x=" + x + " y=" + y);
	}

	@Override
	public ShapeType getShapeType() {
		if (isHole) {
			return ShapeType.hole;
		} else {
			return ShapeType.nonHole;
		}
	}

	public int getY() {
		return center.getY();
	}

	public int getX() {
		return center.getX();
	}

	public boolean isHole() {
		return isHole;
	}

	public MarkShape createMarkInThisRoom(ShapeType type) {
		return new MarkShape(type, getY(), getX());
	}

	@Override
	public void print2D(I2DDocument doc, IPrintStyle printStyle) {
	}

	@Override
	public String toString() {
		return "FloorShape [y=" + getY() + ", x=" + getX() + ", isHole=" + isHole + "]";
	}

	private static final Logger LOG = Logger.getLogger("maze.jmaze");
	private Point2D center;
	private boolean isHole;

}
