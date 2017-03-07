package com.github.sladecek.maze.jmaze.hexagonal;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

public class Hexagonal2DMazeTest {
	
	private Hexagonal2DMaze maze;
	
	@Before
	public void setUp() throws Exception {
		maze = new Hexagonal2DMaze(2);		
	}


	@Test
	public void testGetSize() {
		assertEquals(2, maze.getSize());
	}

	@Test
	public void testGetRoomCount() {
		assertEquals(6, maze.getRoomCount());
	}

	@Test
	public void testGetWallCount() {
		assertEquals(9, maze.getWallCount());
	}

	@Test
	public void testGetWalls() {
		List<Integer> list = new ArrayList<Integer>();
		maze.getWalls(0).iterator().forEachRemaining(list::add);
		assertEquals(2, list.size());
	}

	@Test
	public void testGetStartRoom() {
		assertEquals(0, maze.getStartRoom());
	}

	@Test
	public void testGetTargetRoom() {
		assertEquals(5, maze.getTargetRoom());
	}

	@Test
	public void testGetRoomBehindWall() {
		// rooms are numbered by columns
		// walls counterclockwise
		
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
		assertEquals(6, floors.size());
		
		assertEquals(20, floors.get(0).getX());
		assertEquals(17, floors.get(0).getY());

		assertEquals(80, floors.get(5).getX());
		assertEquals(51, floors.get(5).getY());
	}
	

}
