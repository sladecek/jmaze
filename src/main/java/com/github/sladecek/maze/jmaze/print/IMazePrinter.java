package com.github.sladecek.maze.jmaze.print;

import java.io.OutputStream;

import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

/**
 * 
 * Print maze to a stream.
 *
 */
public interface IMazePrinter {
	void printShapes(ShapeContainer maze, MazeOutputFormat format, OutputStream output, boolean showSolution);
}
