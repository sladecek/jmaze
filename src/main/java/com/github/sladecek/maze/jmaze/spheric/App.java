package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;


public class App 
{
	public static void main(String[] args)
    {   	
		
		final int equator_cells = 32; // must be power of 2
		final double ellipseMajor_mm = 30;
		final double ellipseMinor_mm = 20;
		final double eggCoef = 0.2;
		
		EggGeometry egg = new EggGeometry(ellipseMajor_mm, ellipseMinor_mm, eggCoef);
		EggMaze maze = new EggMaze(egg, equator_cells);
    	IMazeGenerator g = new DepthFirstMazeGenerator();
    	g.generateMaze(maze);
    	   	
    	Maze3DSizes sizes = new Maze3DSizes();
    	sizes.setCellSize_mm(2);
    	
    	EggOpenScadPrinter osp = new EggOpenScadPrinter(sizes, egg);
    	osp.printMaze(maze,"maze.scad");
    }
}
