package com.github.sladecek.maze.jmaze.shapes;

import java.io.IOException;

import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;

/**
 * Shapes for maze drawing.
 */
public interface IMazeShape {
	enum ShapeType { innerWall, outerWall, hole, nonHole, solution, auxiliaryWall, startRoom, targetRoom };
	
	ShapeType getShapeType();
	
	String getId();
	
	void printToSvg(SvgMazePrinter printer) throws IOException;

}
