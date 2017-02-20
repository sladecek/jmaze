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
		int layerCount = 4;
		maze = new Circular2DMaze(layerCount);
	}

	@Test
	public void testGetRoomCount() {
		assertEquals(29, maze.getRoomCount());
	}

	@Test
	public void testGetWallCount() {
		assertEquals(56, maze.getWallCount());
	}

	@Test
	public void testGetWalls() {
		List<Integer> list = new ArrayList<Integer>();
		maze.getWalls(0).iterator().forEachRemaining(list::add);
		assertEquals(4, list.size());
	}

	@Test
	public void testGetStartRoom() {
		assertEquals(0, maze.getStartRoom());
	}

	@Test
	public void testGetTargetRoom() {
		assertEquals(28, maze.getTargetRoom());
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
		assertEquals(29, floors.size());


		assertEquals(0, floors.get(0).getY());
	}
	

}
