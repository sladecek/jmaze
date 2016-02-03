package com.github.sladecek.maze.jmaze.hexagonal;

import java.util.Random;

import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;
import com.github.sladecek.maze.jmaze.topology.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.topology.IMazeGenerator;
import com.github.sladecek.maze.jmaze.topology.MazeRealization;

public class TestAppHexagonal extends TestApp2DBase {
	
	public static void main(String[] args) {
		new TestAppHexagonal().printTestMaze("maze-hexagonal", () -> {
			final int defaultSize = 30	;			
			Hexagonal2DMaze maze = new Hexagonal2DMaze(defaultSize);
			final Random randomGenerator = new Random();
			randomGenerator.setSeed(0);
			IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);

			MazeRealization realization = g.generateMaze(maze);	    		    	
	    	return  maze.makeShapes(realization);	    			
		});
    }


	

}
