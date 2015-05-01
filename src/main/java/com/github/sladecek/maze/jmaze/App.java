package com.github.sladecek.maze.jmaze;

/**
 * Print rectangular maze in svg - the simplest demo.
 */
public class App 
{
    public static void main( String[] args )
    {
    	Rectangular2DMaze maze = new Rectangular2DMaze(100, 150);
    	IMazeGenerator g = new DepthFirstMazeGenerator();
    	g.generateMaze(maze);
    	SvgMazePrinter smp = new SvgMazePrinter();
    	smp.printMaze(maze, "maze.svg");
    }
}
