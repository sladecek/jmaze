package com.github.sladecek.maze.jmaze.hexagonal;

import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;

public class TestAppHexagonal extends TestApp2DBase {
	
	public static void main(String[] args) {
		new TestAppHexagonal().printTestMaze("maze-hexagonal", () -> {
			final int defaultSize = 30	;			
			return new Hexagonal2DMaze(defaultSize);
		});
    }


	

}
