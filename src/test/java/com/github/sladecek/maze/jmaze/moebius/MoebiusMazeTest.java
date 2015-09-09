package com.github.sladecek.maze.jmaze.moebius;

import static org.junit.Assert.assertEquals;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MoebiusMazeTest {

	private MoebiusMaze maze;
	
	@Before
	public final void setUp() throws Exception {
		maze = new MoebiusMaze(4, 6);		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetRoomCount() {
		assertEquals(24, maze.getRoomCount());
	}


	@Test
	public final void testGetStartRoom() {
		assertEquals(0, maze.getStartRoom());
	}

	@Test
	public final void testGetTargetRoom() {
		assertEquals(19, maze.getTargetRoom());
	}
	
	@Test
	public final void testGetWalls_center() {
		int[] w = {6,7,25,31,49};
		testGetWalls(7, w);
	}

	@Test
	public final void testGetWalls_corner0() {
		int[] w = {0,5,24,42};
		testGetWalls(0, w);
	}

	@Test
	public final void testGetWalls_corner5() {
		int[] w = {4,5,29,47};
		testGetWalls(5, w);
	}

	@Test
	public final void testGetWalls_corner18() {
		int[] w = {18,23,36,45};
		testGetWalls(18, w);
	}
	
	@Test
	public final void testGetWalls_corner23() {
		int[] w = {22,23,41,44};
		testGetWalls(23, w);
	}

	@Test
	public final void testGetWalls_corner20() {
		int[] w = {19,20,38,47};
		testGetWalls(20, w);
	}
	
	private void testGetWalls(final int room, final int[] walls) {
		Vector<Integer> v = (Vector<Integer>) maze.getWalls(room);
		Collections.sort(v);
		assertEquals(walls.length, v.size());				
		int cnt = Math.min(walls.length, v.size());
		for (int i = 0; i < cnt; i++) {
			assertEquals(walls[i], (int)v.get(i));
		}
	}


	@Test
	public final void testGetPictureHeight() {
		assertEquals(4, maze.getPictureHeight());
	}

	@Test
	public final void testGetPictureWidth() {
		assertEquals(6, maze.getPictureWidth());
	}

	@Test(expected=InvalidParameterException.class)
	public final void testGetOtherRoom_invalid() {
		assertEquals(1, maze.getOtherRoom(0, 2));
	}

	@Test
	public final void testGetOtherRoom_0() {
		assertEquals(1, maze.getOtherRoom(0, 0));
		assertEquals(0, maze.getOtherRoom(1, 0));
	}

	@Test
	public final void testGetOtherRoom_5() {
		assertEquals(5, maze.getOtherRoom(0, 5));
		assertEquals(0, maze.getOtherRoom(5, 5));
	}

	@Test
	public final void testGetOtherRoom_24() {
		assertEquals(6, maze.getOtherRoom(0, 24));
		assertEquals(0, maze.getOtherRoom(6, 24));
	}

	@Test
	public void testGetOtherRoom_22() {
		assertEquals(23, maze.getOtherRoom(22, 22));
		assertEquals(22, maze.getOtherRoom(23, 22));
	}

	@Test
	public final void testGetOtherRoom_23() {
		assertEquals(23, maze.getOtherRoom(18, 23));
		assertEquals(18, maze.getOtherRoom(23, 23));
	}
	
	@Test
	public final void testGetOtherRoom_41() {
		assertEquals(17, maze.getOtherRoom(23, 41));
		assertEquals(23, maze.getOtherRoom(17, 41));
	}

	@Test
	public final void testGetOtherRoom_42() {
		assertEquals(21, maze.getOtherRoom(0, 42));
		assertEquals(0, maze.getOtherRoom(21, 42));
	}

	@Test
	public final void testGetOtherRoom_51() {
		assertEquals(12, maze.getOtherRoom(9, 51));
		assertEquals(9, maze.getOtherRoom(12, 51));
	}
	
	
}
