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


public class App 
{
	private final static Logger log = Logger.getLogger("LOG");
	
	public static void main(String[] args)
    {   	
		LogManager.getLogManager().reset();
		log.setLevel(Level.INFO);
		try {
			FileHandler fh = new FileHandler("maze.log");
			log.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
		

			Rectangular2DMaze maze = new Rectangular2DMaze(27, 27);
	    	IMazeGenerator g = new DepthFirstMazeGenerator();
	    	MazeRealization real = g.generateMaze(maze);
	    	log.log(Level.INFO, "realization " + real.printClosedWalls());
	    	   		    	
	    	SvgMazePrinter sp = new SvgMazePrinter();
	    	sp.printMaze(maze, real, "maze-rect.svg");
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
