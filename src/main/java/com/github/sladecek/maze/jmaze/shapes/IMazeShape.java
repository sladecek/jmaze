package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.print2d.SvgMazePrinter;

/**
 * Shapes for maze drawing.
 */
public interface IMazeShape {
	enum ShapeType { innerWall, outerWall, hole, nonHole, solution, auxiliaryWall, startRoom, targetRoom };
	
	ShapeType getShapeType();

	void printToSvg(SvgMazePrinter printer, ShapeContext context) ;

}
