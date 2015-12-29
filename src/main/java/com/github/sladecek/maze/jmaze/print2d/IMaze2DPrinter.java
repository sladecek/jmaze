package com.github.sladecek.maze.jmaze.print2d;

import java.io.OutputStream;

import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

/**
 * 
 * Print maze to a stream.
 *
 */
public interface IMaze2DPrinter {
	void printShapes(ShapeContainer maze, MazeOutputFormat format, OutputStream output, boolean showSolution);
}
