package com.github.sladecek.maze.jmaze;

import java.io.IOException;

/**
 * Shapes for maze drawing.
 */
public interface IMazeShape {
	public enum ShapeType { innerWall, outerWall, solution, auxiliaryWall, startRoom, targetRoom };
	
	ShapeType getShapeType();
	
	void printToSvg(SvgMazePrinter printer) throws IOException;

}
