package com.github.sladecek.maze.jmaze.circular;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;
import com.github.sladecek.maze.jmaze.triangular.Triangular2DMaze;

public class TestAppCircular extends TestApp2DBase {
	
	public static void main(String[] args) {
		new TestAppCircular().printTestMaze("maze-circular", () -> {
			final int defaultSize = 60	;			
			Triangular2DMaze maze = new Triangular2DMaze(defaultSize);
	    	IMazeGenerator g = new DepthFirstMazeGenerator();
	    	MazeRealization realization = g.generateMaze(maze);	    		    	
	    	return  maze.makeShapes(realization);	    			
		});
    }


	

}
