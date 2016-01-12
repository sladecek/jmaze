package com.github.sladecek.maze.jmaze.triangular;

import java.util.Random;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;

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
