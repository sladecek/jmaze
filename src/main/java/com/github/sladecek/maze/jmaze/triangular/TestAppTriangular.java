package com.github.sladecek.maze.jmaze.triangular;

import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;

public class TestAppTriangular extends TestApp2DBase {

	
	public static void main(String[] args) {
		new TestAppTriangular().printTestMaze("maze-triangular", () -> {
			final int defaultSize = 6	;			
			return  new Triangular2DMaze(defaultSize);
		});
    }


	

}
