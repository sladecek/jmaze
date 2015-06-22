package com.github.sladecek.maze.jmaze.shapes;

import java.io.IOException;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;

/**
 * Shapes for maze drawing.
 */
public interface IMazeShape {
	public enum ShapeType { innerWall, outerWall, hole, nonHole, solution, auxiliaryWall, startRoom, targetRoom };
	
	ShapeType getShapeType();
	
	void printToSvg(SvgMazePrinter printer) throws IOException;

	boolean isInRealization(MazeRealization real);
	

}
