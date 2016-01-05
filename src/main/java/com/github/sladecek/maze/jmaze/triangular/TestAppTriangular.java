package com.github.sladecek.maze.jmaze.triangular;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.rectangular.TestAppRectangular;

public class TestAppTriangular {

	
	public static void main(String[] args) {
		new TestAppRectangular().printTestMaze("maze-triangular", () -> {
			final int defaultSize = 60	;			
			Triangular2DMaze maze = new Triangular2DMaze(defaultSize);
	    	IMazeGenerator g = new DepthFirstMazeGenerator();
	    	MazeRealization realization = g.generateMaze(maze);	    		    	
	    	return  maze.makeShapes(realization);	    			
		});
    }


	

}
