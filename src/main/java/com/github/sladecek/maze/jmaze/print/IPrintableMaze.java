package com.github.sladecek.maze.jmaze.print;

import com.github.sladecek.maze.jmaze.shapes.IMazeShape;

/***
 * Collection of printable maze shapes.
 */
public interface IPrintableMaze {
	
	
	Iterable<IMazeShape> getShapes();
	int getPictureHeight();
	int getPictureWidth();

}
