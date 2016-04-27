package com.github.sladecek.maze.jmaze.voronoi;

import java.util.Random;

import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;

public class TestAppVoronoi extends TestApp2DBase {

	
	public static void main(String[] args) {
		new TestAppVoronoi().printTestMaze("maze-voronoi", () -> {
			final int width = 200;
			final int height = 200;
			final int roomCount = 100;
			final Random randomGenerator = new Random();
			randomGenerator.setSeed(0);
			return  new Voronoi2DMaze(width, height, roomCount, randomGenerator);
		});
    }

	

}
