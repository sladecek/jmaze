package com.github.sladecek.maze.jmaze;

import java.io.IOException;

public class LineShape implements IMazeShape {
	
	private ShapeType shapeType;

	LineShape(ShapeType type, int y1, int x1, int y2, int x2) {
		this.shapeType = type;
		this.y1 = y1;
		this.y2 = y2;
		this.x1 = x1;
		this.x2 = x2;
	}

	public ShapeType getShapeType() {
		return this.shapeType;
	}

	public void printToSvg(SvgMazePrinter svg) throws IOException  {
		String style = "";
		boolean center = false;
		switch (shapeType)
		{
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
			
		}
		svg.printLine(y1, x1, y2, x2, style, center);		
	}

	private int x1;
	private int x2;
	private int y1;
	private int y2;

	@Override
	public void produce3DShapes(I3DShapeConsumer is) {
		if (shapeType == ShapeType.innerWall) {
			is.consumeWall(this);
		}
		
	}

	public int getX1() {
		return x1;
	}

	public int getX2() {
		return x2;
	}

	public int getY1() {
		return y1;
	}

	public int getY2() {
		return y2;
	}

	@Override
	public String toString() {
		return "LineShape [shapeType=" + shapeType + ", x1=" + x1 + ", x2="
				+ x2 + ", y1=" + y1 + ", y2=" + y2 + "]";
	}
}
