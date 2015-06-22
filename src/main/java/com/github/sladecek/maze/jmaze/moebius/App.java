package com.github.sladecek.maze.jmaze.moebius;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;


public class App 
{
	public static void main(String[] args)
    {   	
		final int width_cells = 8;
		final int length_cells = 180;
		MoebiusMaze maze = new MoebiusMaze(width_cells, length_cells);
    	IMazeGenerator g = new DepthFirstMazeGenerator();
    	MazeRealization r = g.generateMaze(maze);
    	
    	SvgMazePrinter smp = new SvgMazePrinter();
    	smp.printMaze(maze, r,  "maze.svg");
    	
    	Maze3DSizes sizes = new Maze3DSizes();
    	sizes.setCellSize_mm(2);
    	
    	MoebiusOpenScadPrinter osp = new MoebiusOpenScadPrinter(sizes);
    	osp.printMaze(maze, r, "maze-moebius.scad");
    }
}
