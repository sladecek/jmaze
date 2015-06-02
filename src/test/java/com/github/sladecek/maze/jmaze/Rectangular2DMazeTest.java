package com.github.sladecek.maze.jmaze;

import static org.junit.Assert.assertEquals;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.sladecek.maze.jmaze.rectangular.Rectangular2DMaze;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;

public class Rectangular2DMazeTest {

	private Rectangular2DMaze maze;
	
	@Before
	public void setUp() throws Exception {
		maze = new Rectangular2DMaze(3, 5);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetRoomCount() {
		assertEquals(15, maze.getRoomCount());
	}

	@Test
	public void testGetRoomDistance_theSameRoom() {
		assertEquals(0.0, maze.getRoomDistance(0, 0), 1e-6);
		assertEquals(0.0, maze.getRoomDistance(14, 14), 1e-6);
	}

	@Test
	public void testGetRoomDistance_neighbour() {
		assertEquals(1.0, maze.getRoomDistance(6, 1), 1e-6);
		assertEquals(1.0, maze.getRoomDistance(1, 6), 1e-6);
		assertEquals(1.0, maze.getRoomDistance(6, 11), 1e-6);
		assertEquals(1.0, maze.getRoomDistance(6, 5), 1e-6);
		assertEquals(1.0, maze.getRoomDistance(6, 7), 1e-6);		
	}

	@Test
	public void testGetRoomDistance_corner() {
		assertEquals(2.0, maze.getRoomDistance(6, 0), 1e-6);
		assertEquals(2.0, maze.getRoomDistance(0, 6), 1e-6);
		assertEquals(2.0, maze.getRoomDistance(6, 2), 1e-6);
		assertEquals(2.0, maze.getRoomDistance(6, 10), 1e-6);
		assertEquals(2.0, maze.getRoomDistance(6, 12), 1e-6);		
	}

	@Test
	public void testGetShapes() {
		int cnt = 0;
		for (IMazeShape ms: maze.getShapes()) {
			cnt++;
			assertEquals("class com.github.sladecek.maze.jmaze.LineShape", ms.getClass().toString());
		}
		assertEquals(4+12+10, cnt);
	}

	@Test
	public void testGetStartRoom() {
		assertEquals(0, maze.getStartRoom());
	}

	@Test
	public void testGetTargetRoom() {
		assertEquals(14, maze.getTargetRoom());
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
		Vector<Integer> v = (Vector<Integer>) maze.getWalls(room);
		Collections.sort(v);
		assertEquals(walls.length, v.size());				
		int cnt = Math.min(walls.length, v.size());
		for (int i = 0; i < cnt; i++) {
			assertEquals(walls[i], (int)v.get(i));
		}
	}

	@Test
	public void testIsWallClosed_initial() {
		assertEquals(true, maze.isWallClosed(0));
		assertEquals(true, maze.isWallClosed(14));
	}

	@Test
	public void testSetWallClosed() {
		maze.setWallClosed(0, false);
		assertEquals(false, maze.isWallClosed(0));
	}

	@Test
	public void testGetPictureHeight() {
		assertEquals(3, maze.getPictureHeight());
	}

	@Test
	public void testGetPictureWidth() {
		assertEquals(5, maze.getPictureWidth());
	}

	@Test(expected=InvalidParameterException.class)
	public void testGetOtherRoom_invalid() {
		assertEquals(1, maze.getOtherRoom(0, 2));
	}

	@Test
	public void testGetOtherRoom_0() {
		assertEquals(1, maze.getOtherRoom(0, 0));
		assertEquals(0, maze.getOtherRoom(1, 0));

	}

	@Test
	public void testGetOtherRoom_12() {
		assertEquals(5, maze.getOtherRoom(0, 12));
		assertEquals(0, maze.getOtherRoom(5, 12));
	}

	@Test
	public void testGetOtherRoom_11() {
		assertEquals(13, maze.getOtherRoom(14, 11));
		assertEquals(14, maze.getOtherRoom(13, 11));
	}

	@Test
	public void testGetOtherRoom_21() {
		assertEquals(9, maze.getOtherRoom(14, 21));
		assertEquals(14, maze.getOtherRoom(9, 21));
	}
	
}
