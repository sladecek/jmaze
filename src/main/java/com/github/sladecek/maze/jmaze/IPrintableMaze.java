package com.github.sladecek.maze.jmaze;

/***
 * Interface for mazes. Maze is a drawing consisting of outer walls, inner walls and 
 * solution path.
 */
public interface IPrintableMaze {
	
	enum ShapeType { innerWall, outerWall, solution };
	
	Iterable<IMazeShape> GetShapes(ShapeType whichType);

}
