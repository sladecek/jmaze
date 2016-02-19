package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;

public final class MarkShape implements IMazeShape {

	public int getX() {
		return center.getX();
	}

	public int getY() {
		return center.getY();
	}

	private ShapeType shapeType;

	public MarkShape(ShapeType type, int y, int x) {
		this.shapeType = type;
		this.center = new Point2D(x, y);

	}

	public ShapeType getShapeType() {
		return this.shapeType;
	}

	@Override
	public String toString() {
		return "MarkShape [shapeType=" + shapeType + ", x=" + getX() + ", y=" + getY() + "]";
	}

	public void setOffsetXPercent(int offsetXPercent) {
		this.offsetXPercent = offsetXPercent;
	}

	public void setOffsetYPercent(int offsetYPercent) {
		this.offsetYPercent = offsetYPercent;
	}

	@Override
	public void print2D(I2DDocument doc, IPrintStyle printStyle) {

		switch (shapeType) {
		case startRoom:
			doc.printMark(center, "red", 25, offsetXPercent, offsetYPercent);
			break;
		case targetRoom:
			doc.printMark(center, "green", 25, offsetXPercent, offsetYPercent);
			break;
		case solution:
			doc.printMark(center, "gray", 15, offsetXPercent, offsetYPercent);
			break;
		default:
			break;
		}
	}

	private Point2D center;

	private int offsetXPercent = 50;
	private int offsetYPercent = 50;
}
