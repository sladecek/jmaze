package com.github.sladecek.maze.jmaze.rectangular;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

public final class TestAppRectangular {

	private TestAppRectangular() {
	}
	
	public static void main(String[] args) {
   	
		LogManager.getLogManager().reset();
		LOG.setLevel(Level.INFO);
		try {
			FileHandler fh = new FileHandler("maze.log");
			LOG.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
		
			final int defaultSize = 27;
			
			Rectangular2DMaze maze = new Rectangular2DMaze(defaultSize, defaultSize);
	    	IMazeGenerator g = new DepthFirstMazeGenerator();
	    	MazeRealization realization = g.generateMaze(maze);	    		    	
	    	ShapeContainer shapes = maze.makeShapes(realization);	    	
	    	SvgMazePrinter printer = new SvgMazePrinter();
	    	final boolean showSolution = true;
	    	printer.printShapes(shapes,  "maze-rect.svg", showSolution);
	    	
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
    }

	private static final Logger LOG =  Logger.getLogger("maze.jmaze");
	

}
