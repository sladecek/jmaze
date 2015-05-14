package com.github.sladecek.maze.jmaze;

import java.io.IOException;

public class HoleShape implements IMazeShape {

	private int y;
	private int x;
	
	public HoleShape(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}

	@Override
	public ShapeType getShapeType() {
		return ShapeType.innerWall;
	}

	@Override
	public void printToSvg(SvgMazePrinter svg) throws IOException {
		String style = "stroke:rgb(222,222,222);stroke-width:1";
		svg.printLine(y, x, y+1, x+1, style, false);
		svg.printLine(y+1, x, y, x+1, style, false);
	}

	@Override
	public void produce3DShapes(I3DShapeConsumer is) {
		is.consumeHole(this);
		
	}

	public int getY() {
		return y;
	}


	public int getX() {
		return x;
	}



}
