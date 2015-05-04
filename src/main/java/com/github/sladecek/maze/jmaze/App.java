package com.github.sladecek.maze.jmaze;


public class App 
{
    public static void main( String[] args )
    {   	
    	MoebiusMaze maze = new MoebiusMaze(10, 100);
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
