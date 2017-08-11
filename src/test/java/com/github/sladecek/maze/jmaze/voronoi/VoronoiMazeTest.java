package com.github.sladecek.maze.jmaze.voronoi;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import org.junit.Before;
import org.junit.Test;


import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.Shapes;

public class VoronoiMazeTest {

	private VoronoiMaze maze;

	@Before
	public void setUp() throws Exception {
		
		int width = 10;
		int height = 30;
		int roomCount = 3;
        Random r = mock(Random.class);
        when(r.nextDouble()).thenReturn(0.5, 0.1, 0.5, 0.5, 0.5, 0.9);
		maze = new VoronoiMaze(width, height, roomCount, 1);
		maze.forceRandomGenerator(r);
		maze.buildMazeGraphAndShapes();
	}


	@Test
	public void testGetRoomCount() {
		assertEquals(3, maze.getGraph().getRoomCount());
	}

	@Test
	public void testGetWallCount() {
		assertEquals(2, maze.getGraph().getWallCount());
	}

	@Test
	public void testGetWalls() {
		List<Integer> list = new ArrayList<>();
		maze.getGraph().getWalls(0).iterator().forEachRemaining(list::add);
		assertEquals(1, list.size());
	}

	@Test
	public void testGetStartRoom() {
		assertEquals(0, maze.getGraph().getStartRoom());
	}

	@Test
	public void testGetTargetRoom() {
		assertEquals(2, maze.getGraph().getTargetRoom());
	}


	@Test
	public void testMarkShapes() {
		ArrayList<MarkShape> floors = new ArrayList<>();
		Shapes sc = maze.getAllShapes();
		for (IMazeShape s: sc.getShapes()) {
			if (s instanceof MarkShape) {
				floors.add((MarkShape)s);
			}
		}
		assertEquals(3, floors.size());
		

	}

}
