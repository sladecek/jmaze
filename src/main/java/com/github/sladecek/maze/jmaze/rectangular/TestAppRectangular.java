package com.github.sladecek.maze.jmaze.rectangular;

import java.util.Random;

import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;
import com.github.sladecek.maze.jmaze.topology.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.topology.IMazeGenerator;
import com.github.sladecek.maze.jmaze.topology.MazeRealization;

public final class TestAppRectangular extends TestApp2DBase {

	public static void main(String[] args) {
		new TestAppRectangular().printTestMaze("maze-rect", () -> {
			final int defaultSize = 27;
			
			Rectangular2DMaze maze = new Rectangular2DMaze(defaultSize, defaultSize);
			final Random randomGenerator = new Random();
			randomGenerator.setSeed(0);
			IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);
	    	MazeRealization realization = g.generateMaze(maze);	    		    	
	    	return  maze.makeShapes(realization);	    			
		});
    }
}
