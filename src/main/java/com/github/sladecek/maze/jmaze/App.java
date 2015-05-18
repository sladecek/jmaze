package com.github.sladecek.maze.jmaze;


public class App 
{
	public static void main(String[] args)
    {   	
		final int width_cells = 6;
		final int length_cells = 180;
		MoebiusMaze maze = new MoebiusMaze(width_cells, length_cells);
    	IMazeGenerator g = new DepthFirstMazeGenerator();
    	g.generateMaze(maze);
    	
    	SvgMazePrinter smp = new SvgMazePrinter();
    	smp.printMaze(maze, "maze.svg");
    	
    	Maze3DSizes sizes = new Maze3DSizes();
    	sizes.setCellSize_mm(2);
    	
    	MoebiusOpenScadPrinter osp = new MoebiusOpenScadPrinter(sizes);
    	osp.printMaze(maze,"maze.scad");
    }
}
