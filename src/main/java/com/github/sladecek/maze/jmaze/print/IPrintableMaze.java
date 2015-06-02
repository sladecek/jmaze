package com.github.sladecek.maze.jmaze.print;

import com.github.sladecek.maze.jmaze.shapes.IMazeShape;

/***
 * Interface for mazes. Maze is a drawing consisting of outer walls, inner walls and 
 * solution path.
 */
public interface IPrintableMaze {
	
	
	Iterable<IMazeShape> getShapes();
	int getPictureHeight();
	int getPictureWidth();

}
