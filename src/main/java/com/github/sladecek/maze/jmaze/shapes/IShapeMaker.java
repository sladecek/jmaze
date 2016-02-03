package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.topology.MazeRealization;

/***
 * Makes 2D printable maze shapes.
 */
public interface IShapeMaker {		
	ShapeContainer makeShapes(MazeRealization realization);
}
