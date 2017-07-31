package com.github.sladecek.maze.jmaze.triangular;

import com.github.sladecek.maze.jmaze.app.TestApp2DBase;

public class TestAppTriangular extends TestApp2DBase {

	public static void main(String[] args) {
		new TestAppTriangular().printTestMaze("maze-triangular", () -> {
			final int defaultSize = 20	;
			return  new Triangular2DMaze(defaultSize);
		});
    }
}
