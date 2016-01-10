package com.github.sladecek.maze.jmaze.voronoi;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;

public class TestAppVoronoi extends TestApp2DBase {

	
	public static void main(String[] args) {
		new TestAppVoronoi().printTestMaze("maze-voronoi", () -> {
			final int width = 60;
			final int height = 80;
			final int roomCount = 50;
			Voronoi2DMaze maze = new Voronoi2DMaze(width, height, roomCount);
	    	IMazeGenerator g = new DepthFirstMazeGenerator();
	    	MazeRealization realization = g.generateMaze(maze);	    		    	
	    	return  maze.makeShapes(realization);	    			
		});
    }


	

}
