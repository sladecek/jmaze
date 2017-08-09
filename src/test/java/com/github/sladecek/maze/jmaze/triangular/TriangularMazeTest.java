package com.github.sladecek.maze.jmaze.triangular;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IPrintableMazeShape2D;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import org.junit.Before;
import org.junit.Test;


import com.github.sladecek.maze.jmaze.shapes.Shapes;

public class TriangularMazeTest {
	
	private TriangularMaze maze;

	@Before
	public void setUp() throws Exception {

		final int size = 3;

		maze = new TriangularMaze();
		MazeProperties p = maze.getDefaultProperties();
		p.put("size", size);
		maze.setProperties(p);

		maze.makeMazeAllSteps(false);
	}


	@Test
	public void testGetSize() {
		assertEquals(3, maze.getSize());
	}

	@Test
	public void testGetRoomCount() {
		assertEquals(9, maze.getGraph().getRoomCount());
	}

	@Test
	public void testGetWallCount() {
		assertEquals(9, maze.getGraph().getWallCount());
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
		assertEquals(8, maze.getGraph().getTargetRoom());
	}

	@Test
	public void testGetRoomBehindWall() {
		// rooms are numbered by columns
		// walls counterclockwise

		/* TODO
		assertEquals(1, maze.getGraph().getRoomBehindWall(0, 0));
		assertEquals(2, maze.getGraph().getRoomBehindWall(0, 1));
		
		assertEquals(0, maze.getGraph().getRoomBehindWall(1, 0));
		assertEquals(2, maze.getGraph().getRoomBehindWall(1, 2));
		
		assertEquals(0, maze.getGraph().getRoomBehindWall(2, 1));
		assertEquals(1, maze.getGraph().getRoomBehindWall(2, 2));
		assertEquals(3, maze.getGraph().getRoomBehindWall(2, 3));
		assertEquals(5, maze.getGraph().getRoomBehindWall(2, 7));
		assertEquals(4, maze.getGraph().getRoomBehindWall(2, 5));

		assertEquals(2, maze.getGraph().getRoomBehindWall(4, 5));
		assertEquals(5, maze.getGraph().getRoomBehindWall(4, 6));
		
		assertEquals(4, maze.getGraph().getRoomBehindWall(5, 6));
		assertEquals(2, maze.getGraph().getRoomBehindWall(5, 7));
		assertEquals(3, maze.getGraph().getRoomBehindWall(5, 8));
		*/
	}

	// TODO wall shapes
	@Test
	public void testFloorShapes() {
		ArrayList<MarkShape> floors = new ArrayList<>();
		Shapes sc = maze.getAllShapes();
		for (IMazeShape s: sc.getShapes()) {
			if (s instanceof MarkShape) {
				floors.add((MarkShape)s);
			}
		}
		assertEquals(9, floors.size());
		
/* TODO		assertEquals(10, floors.get(0).getX());
		assertEquals(8, floors.get(0).getY());

		assertEquals(40, floors.get(5).getX());
		assertEquals(24, floors.get(5).getY());
		*/
	}

}
