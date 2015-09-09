package com.github.sladecek.maze.jmaze.moebius;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.MazeColors;
import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;
import com.github.sladecek.maze.jmaze.print.WinterColors;

/***
 * Command line application generating a Moebius maze into OpenScad file.
 * 
 * @author sladecek
 *
 */
public final class TestAppScad {
	public static void main(final String[] args) {   	
		final int widthCells = 8;
		final int lengthCells = 180;
		MoebiusMaze maze = new MoebiusMaze(widthCells, lengthCells);
    	IMazeGenerator g = new DepthFirstMazeGenerator();
    	MazeRealization r = g.generateMaze(maze);
    	
    	SvgMazePrinter smp = new SvgMazePrinter();
    	smp.printMaze(maze, r,  "maze.svg");
    	
    	Maze3DSizes sizes = new Maze3DSizes();
    	sizes.setCellSize_mm(2);
    	
    	MazeColors colors = new WinterColors();
    	
    	MoebiusOpenScadPrinter osp = new MoebiusOpenScadPrinter(sizes, colors);
    	osp.printMaze(maze, r, "maze-moebius.scad");
    }
}
