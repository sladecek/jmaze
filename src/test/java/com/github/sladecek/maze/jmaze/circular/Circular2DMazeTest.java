package com.github.sladecek.maze.jmaze.circular;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import org.junit.Before;
import org.junit.Test;


import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D;
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
	public void testMarkShapes() {
		ArrayList<MarkShape> floors = new ArrayList<MarkShape>();
		ShapeContainer  sc = maze.getShapes();
		for (IMazeShape2D s: sc.getShapes()) {
			if (s instanceof MarkShape) {
				floors.add((MarkShape)s);
			}
		}
		assertEquals(29, floors.size());


		assertEquals(0, floors.get(0).getY());
	}
	

}
