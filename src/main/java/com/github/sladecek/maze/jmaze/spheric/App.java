package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;


public class App 
{
	public static void main(String[] args)
    {   	
		
		final int equator_cells = 64; // must be power of 2
		final double ellipseMajor_mm = 30;
		final double ellipseMinor_mm = 20;
		final double eggCoef = 0.2;
		
		EggGeometry egg = new EggGeometry(4, 3, 0.2);
//		EggGeometry egg = new EggGeometry(ellipseMajor_mm, ellipseMinor_mm, eggCoef);
		EggMaze maze = new EggMaze(egg, equator_cells);
    	IMazeGenerator g = new DepthFirstMazeGenerator();
    	MazeRealization real = g.generateMaze(maze);
    	   	
    	Maze3DSizes sizes = new Maze3DSizes();
    	sizes.setCellSize_mm(0.1);
    	sizes.setBaseThickness_mm(0.03);
    	sizes.setWallHeight_mm(0.3);
    	sizes.setInnerWallToCellRatio(0.05);
    	
    	EggOpenScadPrinter osp = new EggOpenScadPrinter(sizes, egg, equator_cells);
    	osp.printMaze(maze, real, "maze-egg.scad");
    }
}
