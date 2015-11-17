package com.github.sladecek.maze.jmaze.print;

import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;


/**
 * 
 * Print maze to file.
 *
 */
public interface IMazePrinter {
	void printShapes(ShapeContainer maze, String fileName, boolean showSolution);
}
