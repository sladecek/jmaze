package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;

public final class WallShape implements IMazeShape {

	public WallShape(ShapeType type, int y1, int x1, int y2, int x2) {
		this.shapeType = type;
		this.p1 = new Point2D(x1, y1);
		this.p2 = new Point2D(x2, y2);
	}

	public ShapeType getShapeType() {
		return this.shapeType;
	}

	@Override
	public void print2D(I2DDocument doc) {
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
		if (doc.getContext().isPolarCoordinates() && p1.getY() == p2.getY()) {
			if (p1.getX() == 0 && p2.getX() == 0) {
				doc.printCircle(new Point2D(0, 0), "none", 0, 0, p1.getY(), false, style);
			} else {
				doc.printArcSegment(p1, p2, style);
			}
		} else {
			doc.printLine(p1, p2, style, center);
		}
	}

	public int getX1() {
		return p1.getX();
	}

	public int getX2() {
		return p2.getX();
	}

	public int getY1() {
		return p1.getY();
	}

	public int getY2() {
		return p2.getY();
	}

	@Override
	public String toString() {
		return "WallShape [ shapeType=" + shapeType + ", x1=" + getX1() + ", x2=" + getX2() + ", y1=" + getY1()
				+ ", y2=" + getY2() + "]";
	}

	private Point2D p1;
	private Point2D p2;

	private ShapeType shapeType;

}
