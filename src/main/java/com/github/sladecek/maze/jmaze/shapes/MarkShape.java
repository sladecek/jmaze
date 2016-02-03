package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print2d.SvgMazePrinter;

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
		return "MarkShape [shapeType=" + shapeType + ", x=" + getX() + ", y=" + getY()
				+ "]";
	}
	
	

	public void setOffsetXPercent(int offsetXPercent) {
		this.offsetXPercent = offsetXPercent;
	}

	public void setOffsetYPercent(int offsetYPercent) {
		this.offsetYPercent = offsetYPercent;
	}

	@Override
	public void printToSvg(SvgMazePrinter svg, ShapeContext context)   {
		
		switch (shapeType) {
		case startRoom:
			svg.printMark(center, "red", 25, offsetXPercent, offsetYPercent, context);		
			break;
		case targetRoom:
			svg.printMark(center, "green", 25, offsetXPercent, offsetYPercent, context);
			break;
		case solution:
			svg.printMark(center, "gray", 15, offsetXPercent, offsetYPercent, context);
			break;
		default:
			break;			
		}
	}

	private Point2D center;
	

	private int offsetXPercent = 50;
	private int offsetYPercent = 50;
}
