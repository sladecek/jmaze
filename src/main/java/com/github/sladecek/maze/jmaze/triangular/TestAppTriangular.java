package com.github.sladecek.maze.jmaze.triangular;

import java.util.Random;

import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;
import com.github.sladecek.maze.jmaze.topology.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.topology.IMazeGenerator;
import com.github.sladecek.maze.jmaze.topology.MazeRealization;

public class TestAppTriangular extends TestApp2DBase {

	
	public static void main(String[] args) {
		new TestAppTriangular().printTestMaze("maze-triangular", () -> {
			final int defaultSize = 60	;			
			Triangular2DMaze maze = new Triangular2DMaze(defaultSize);
			final Random randomGenerator = new Random();
			randomGenerator.setSeed(0);
			IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);

	    	MazeRealization realization = g.generateMaze(maze);	    		    	
	    	return  maze.makeShapes(realization);	    			
		});
    }


	

}
