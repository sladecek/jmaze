package com.github.sladecek.maze.jmaze.rectangular;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Vector;

import com.github.sladecek.maze.jmaze.maze.IMaze;
import org.junit.Before;
import org.junit.Test;

public class Rectangular2DMazeTest {

	private RectangularMaze maze;
	
	@Before
	public void setUp() throws Exception {
		maze = new RectangularMaze(5, 3);
		maze.makeMazeAllSteps(false);



	}


	@Test
	public void testGetRoomCount() {
		assertEquals(15, maze.getGraph().getRoomCount());
	}


	/* TODO
	@Test
	public void testGetShapes() {
		int cnt = 0;
		for (IMazeShape2D ms: maze.getGraph().getShapes()) {
			cnt++;
			assertEquals("class com.github.sladecek.maze.jmaze.shapes.WallShape", ms.getClass().toString());
		}
		assertEquals(4+12+10, cnt);
	}
	*/

	@Test
	public void testGetStartRoom() {
		assertEquals(0, maze.getGraph().getStartRoom());
	}

	@Test
	public void testGetTargetRoom() {
		assertEquals(14, maze.getGraph().getTargetRoom());
	}
	
	@Test
	public void testGetWalls_center() {
		int[] w = {4,5,13,18};
		testGetWalls(6, w);
	}

	@Test
	public void testGetWalls_corner0() {
		int[] w = {0,12};
		testGetWalls(0, w);
	}

	@Test
	public void testGetWalls_corner4() {
		int[] w = {3,16};
		testGetWalls(4, w);
	}

	@Test
	public void testGetWalls_corner10() {
		int[] w = {8,17};
		testGetWalls(10, w);
	}
	
	@Test
	public void testGetWalls_corner14() {
		int[] w = {11,21};
		testGetWalls(14, w);
	}

	@Test
	public void testGetWalls_corner5() {
		int[] w = {4,12,17};
		testGetWalls(5, w);
	}

	@Test
	public void testGetWalls_corner12() {
		int[] w = {9,10,19};
		testGetWalls(12, w);
	}

	
	private void testGetWalls(int room, int[] walls) {
		Vector<Integer> v = (Vector<Integer>) maze.getGraph().getWalls(room);
		Collections.sort(v);
		assertEquals(walls.length, v.size());				
		int cnt = Math.min(walls.length, v.size());
		for (int i = 0; i < cnt; i++) {
			assertEquals(walls[i], (int)v.get(i));
		}
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetOtherRoom_invalid() {
		assertEquals(1, maze.getGraph().getRoomBehindWall(0, 2));
	}

	@Test
	public void testGetOtherRoom_0() {
		assertEquals(1, maze.getGraph().getRoomBehindWall(0, 0));
		assertEquals(0, maze.getGraph().getRoomBehindWall(1, 0));

	}

	@Test
	public void testGetOtherRoom_12() {
		assertEquals(5, maze.getGraph().getRoomBehindWall(0, 12));
		assertEquals(0, maze.getGraph().getRoomBehindWall(5, 12));
	}

	@Test
	public void testGetOtherRoom_11() {
		assertEquals(13, maze.getGraph().getRoomBehindWall(14, 11));
		assertEquals(14, maze.getGraph().getRoomBehindWall(13, 11));
	}

	@Test
	public void testGetOtherRoom_21() {
		assertEquals(9, maze.getGraph().getRoomBehindWall(14, 21));
		assertEquals(14, maze.getGraph().getRoomBehindWall(9, 21));
	}
	
}
