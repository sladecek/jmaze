package com.github.sladecek.maze.jmaze.voronoi;

import java.util.Random;

import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;
import com.github.sladecek.maze.jmaze.topology.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.topology.IMazeGenerator;
import com.github.sladecek.maze.jmaze.topology.MazeRealization;

public class TestAppVoronoi extends TestApp2DBase {

	
	public static void main(String[] args) {
		new TestAppVoronoi().printTestMaze("maze-voronoi", () -> {
			final int width = 200;
			final int height = 200;
			final int roomCount = 200*200/50;
			final Random randomGenerator = new Random();
			randomGenerator.setSeed(0);
			Voronoi2DMaze maze = new Voronoi2DMaze(width, height, roomCount, randomGenerator);
			IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);
	    	MazeRealization realization = g.generateMaze(maze);	    		    	
	    	return  maze.makeShapes(realization);	    			
		});
    }

	

}
