package com.github.sladecek.maze.jmaze.print;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;

/**
 * 
 * Print maze to file.
 *
 */
public interface IMazePrinter {
	void printMaze(IPrintableMaze maze, MazeRealization realization, String fileName);
}
