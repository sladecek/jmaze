package com.github.sladecek.maze.jmaze.voronoi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
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
		maze = new Voronoi2DMaze(width, height, roomCount, r, false);
	}


	@Test
	public void testGetRoomCount() {
		assertEquals(3, maze.getRoomCount());
	}

	@Test
	public void testGetWallCount() {
		assertEquals(2, maze.getWallCount());
	}

	@Test
	public void testGetWalls() {
		List<Integer> list = new ArrayList<Integer>();
		maze.getWalls(0).iterator().forEachRemaining(list::add);
		assertEquals(1, list.size());
	}

	@Test
	public void testGetStartRoom() {
		assertEquals(0, maze.getStartRoom());
	}

	@Test
	public void testGetTargetRoom() {
		assertEquals(2, maze.getTargetRoom());
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
	public void testFloorShapes() {
		ArrayList<FloorShape> floors = new ArrayList<FloorShape>();
		ShapeContainer  sc = maze.getShapes();
		for (IMazeShape s: sc.getShapes()) {
			if (s instanceof FloorShape) {
				floors.add((FloorShape)s);
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
