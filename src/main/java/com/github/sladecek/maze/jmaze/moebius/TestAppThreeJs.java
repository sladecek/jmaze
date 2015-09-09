package com.github.sladecek.maze.jmaze.moebius;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;

/***
 * Command line application generating a Moebius maze in ThreeJs format
 * 
 * @author sladecek
 *
 */
public final class TestAppThreeJs {
	public static void main(final String[] args) {   	
		final int widthCells = 4;
		final int lengthCells = 60;
		MoebiusMaze maze = new MoebiusMaze(widthCells, lengthCells);
    	IMazeGenerator g = new DepthFirstMazeGenerator();
    	MazeRealization r = g.generateMaze(maze);
    	
    	
    	Maze3DSizes sizes = new Maze3DSizes();
    	sizes.setCellSize_mm(2);
    	
    	MoebiusThreeJsMazePrinter smp = new MoebiusThreeJsMazePrinter(sizes);
    	smp.printMaze(maze, r,  "maze.js");
	}
}
