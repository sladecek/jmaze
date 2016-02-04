package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;

public final class TestAppRectangular extends TestApp2DBase {

	public static void main(String[] args) {
		new TestAppRectangular().printTestMaze("maze-rect", () -> {
			final int defaultSize = 27;
			
			return new Rectangular2DMaze(defaultSize, defaultSize);
	   			
		});
    }
}
