package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;

public final class TestAppRectangular extends TestApp2DBase {

	public static void main(String[] args) {
		new TestAppRectangular().printTestMaze("maze-rect", () -> {
			final int defaultSize = 27;
			
			Rectangular2DMaze maze = new Rectangular2DMaze(defaultSize, defaultSize);
	    	IMazeGenerator g = new DepthFirstMazeGenerator();
	    	MazeRealization realization = g.generateMaze(maze);	    		    	
	    	return  maze.makeShapes(realization);	    			
		});
    }
}
