package com.github.sladecek.maze.jmaze.circular;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

public class Circular2DMazeTest {

	
	private Circular2DMaze maze;
	
	@Before
	public void setUp() throws Exception {		
		int layerCount = 3;
		int layerSize = 10;
		maze = new Circular2DMaze(layerCount, layerSize);				
	}


	@Test
	public void testGetRoomCount() {
		assertEquals(18, maze.getRoomCount());
	}

	@Test
	public void testGetWallCount() {
		assertEquals(32, maze.getWallCount());
	}

	@Test
	public void testGetWalls() {
		List<Integer> list = new ArrayList<Integer>();
		maze.getWalls(0).iterator().forEachRemaining(list::add);
		assertEquals(3, list.size());
	}

	@Test
	public void testGetStartRoom() {
		assertEquals(0, maze.getStartRoom());
	}

	@Test
	public void testGetTargetRoom() {
		assertEquals(1, maze.getTargetRoom());
	}

	@Test
	public void testGetRoomBehindWall() {
		// rooms are numbered by columns
		// walls counterclockwise
/*		TODO
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

	@Test
	public void testFloorShapes() {
		ArrayList<FloorShape> floors = new ArrayList<FloorShape>();
		ShapeContainer  sc = maze.getShapes();
		for (IMazeShape s: sc.getShapes()) {
			if (s instanceof FloorShape) {
				floors.add((FloorShape)s);
			}
		}
		assertEquals(33, floors.size());
		
		assertEquals(0, floors.get(0).getX());
		assertEquals(0, floors.get(0).getY());
	}
	

}
