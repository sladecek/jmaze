package com.github.sladecek.maze.jmaze.print;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;


public interface IMazePrinter {
	void printMaze(IPrintableMaze maze, MazeRealization realization, String fileName);
}
