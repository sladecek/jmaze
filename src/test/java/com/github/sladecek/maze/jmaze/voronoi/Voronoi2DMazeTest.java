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


import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

public class Voronoi2DMazeTest {

	private Voronoi2DMaze maze;

	@Before
	public void setUp() throws Exception {
		
		int width = 10;
		int height = 30;
		int roomCount = 3;
        Random r = mock(Random.class);
        when(r.nextDouble()).thenReturn(0.5, 0.1, 0.5, 0.5, 0.5, 0.9);
		maze = new Voronoi2DMaze(width, height, roomCount, 1);
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
		List<Integer> list = new ArrayList<Integer>();
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
	public void testGetRoomBehindWall() {
		// rooms are numbered by columns
		// walls counterclockwise
		/* TODO
		assertEquals(1, maze.getRoomBehindWall(0, 0));
		assertEquals(2, maze.getRoomBehindWall(0, 1));
		
		assertEquals(0, maze.getRoomBehindWall(1, 0));
		assertEquals(2, maze.getRoomBehindWall(1, 2));
		
		assertEquals(0, maze.getRoomBehindWall(2, 1));
		assertEquals(1, maze.getRoomBehindWall(2, 2));
		assertEquals(3, maze.getRoomBehindWall(2, 3));
		assertEquals(5, maze.getRoomBehindWall(2, 7));
		assertEquals(4, maze.getRoomBehindWall(2, 5));

		assertEquals(2, maze.getRoomBehindWall(4, 5));
		assertEquals(5, maze.getRoomBehindWall(4, 6));
		
		assertEquals(4, maze.getRoomBehindWall(5, 6));
		assertEquals(2, maze.getRoomBehindWall(5, 7));
		assertEquals(3, maze.getRoomBehindWall(5, 8));
		*/
	}

	// TODO wall shapes
	@Test
	public void testMarkShapes() {
		ArrayList<MarkShape> floors = new ArrayList<MarkShape>();
		ShapeContainer  sc = maze.getFlatModel();
		for (IMazeShape2D s: sc.getShapes()) {
			if (s instanceof MarkShape) {
				floors.add((MarkShape)s);
			}
		}
		assertEquals(3, floors.size());
		
		/* TODO
		assertEquals(10, floors.get(0).getX());
		assertEquals(8, floors.get(0).getY());

		assertEquals(40, floors.get(5).getX());
		assertEquals(24, floors.get(5).getY());
		*/
	}

/*
 * TODO
	@Test
	public void testGetShapes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContext() {
		fail("Not yet implemented");
	}

*/
}
