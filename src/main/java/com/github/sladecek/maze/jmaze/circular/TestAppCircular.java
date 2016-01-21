package com.github.sladecek.maze.jmaze.circular;

import java.util.Random;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;

public class TestAppCircular extends TestApp2DBase {
	
	public static void main(String[] args) {
		new TestAppCircular().printTestMaze("maze-circular", () -> {
			final int defaultSize = 5	;			
			Circular2DMaze maze = new Circular2DMaze(defaultSize);
			final Random randomGenerator = new Random();
			randomGenerator.setSeed(0);
			IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);

			MazeRealization realization = g.generateMaze(maze);	    		    	
	    	return  maze.makeShapes(realization);	    			
		});
    }


	

}
