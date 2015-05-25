package com.github.sladecek.maze.jmaze;

import java.io.IOException;

import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;

/**
 * Shapes for maze drawing.
 */
public interface IMazeShape {
	public enum ShapeType { innerWall, outerWall, hole, nonHole, solution, auxiliaryWall, startRoom, targetRoom };
	
	ShapeType getShapeType();
	
	void printToSvg(SvgMazePrinter printer) throws IOException;

	void produce3DShapes(I3DShapeConsumer is);
	
	

}
