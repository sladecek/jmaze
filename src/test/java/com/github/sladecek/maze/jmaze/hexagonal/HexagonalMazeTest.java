package com.github.sladecek.maze.jmaze.hexagonal;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.Shapes;
import org.junit.Before;
import org.junit.Test;


import com.github.sladecek.maze.jmaze.shapes.IPrintableMazeShape2D;

public class HexagonalMazeTest {
	
	private HexagonalMaze maze;
	
	@Before
	public void setUp() throws Exception {
		maze = new HexagonalMaze();

		MazeProperties p = maze.getDefaultProperties();
		p.put("size", 2);
		maze.setProperties(p);

		maze.makeMazeAllSteps(false);
	}


	@Test
	public void testGetSize() {
		assertEquals(2, maze.getSize());
	}

	@Test
	public void testGetRoomCount() {
		assertEquals(6, maze.getGraph().getRoomCount());
	}

	@Test
	public void testGetWallCount() {
		assertEquals(9, maze.getGraph().getWallCount());
	}

	@Test
	public void testGetWalls() {
		List<Integer> list = new ArrayList<Integer>();
		maze.getGraph().getWalls(0).iterator().forEachRemaining(list::add);
		assertEquals(2, list.size());
	}

	@Test
	public void testGetStartRoom() {
		assertEquals(0, maze.getGraph().getStartRoom());
	}

	@Test
	public void testGetTargetRoom() {
		assertEquals(5, maze.getGraph().getTargetRoom());
	}

	@Test
	public void testGetRoomBehindWall() {
		// rooms are numbered by columns
		// walls counterclockwise
		
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
	}

	// TODO wall shapes
	@Test
	public void testMarkShapes() {
		ArrayList<MarkShape> floors = new ArrayList<MarkShape>();
		Shapes sc = maze.getAllShapes();
		for (IPrintableMazeShape2D s: sc.getShapes()) {
			if (s instanceof MarkShape) {
				floors.add((MarkShape)s);

			}
		}
		assertEquals(6, floors.size());
		
		assertEquals(20, floors.get(0).getX());
		assertEquals(17, floors.get(0).getY());

		assertEquals(80, floors.get(5).getX());
		assertEquals(51, floors.get(5).getY());
	}
	


}
