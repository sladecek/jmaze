package com.github.sladecek.maze.jmaze.spheric;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.moebius.MoebiusBlockMaker;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.MazeColors;
import com.github.sladecek.maze.jmaze.print.OpenScadBlockPrinter;
import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;
import com.github.sladecek.maze.jmaze.print.WinterColors;


class TestAppScad {
	private static final Logger lOGGER = Logger.getLogger("LOG");
	
	public static void main(final String[] args) {   	
		LogManager.getLogManager().reset();
		lOGGER.setLevel(Level.INFO);
		try {
			FileHandler fh = new FileHandler("maze.log");
			lOGGER.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
		
			final int equatorCells = 64; // must be power of 2			
			EggGeometry egg = new EggGeometry(5, 3, 0.2);


			EggMaze maze = new EggMaze(egg, equatorCells);
	    	IMazeGenerator g = new DepthFirstMazeGenerator();
	    	MazeRealization real = g.generateMaze(maze);
	    	   	
	    	Maze3DSizes sizes = new Maze3DSizes();
	    	sizes.setCellSize_mm(0.1);
	    	sizes.setBaseThickness_mm(0.03);
	    	sizes.setWallHeight_mm(0.3);
	    	sizes.setInnerWallToCellRatio(0.05);
	    	
	    	MazeColors colors = new WinterColors();
	    		    	
	    	EggBlockMaker maker = new EggBlockMaker(maze, real, sizes, colors, egg, equatorCells);
	    	OpenScadBlockPrinter printer = new OpenScadBlockPrinter(maker);
	    	printer.printMaze("maze-egg.scad");
	    	
	    	SvgMazePrinter sp = new SvgMazePrinter();
	    	sp.printMaze(maze, real, "maze-egg.svg");
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
