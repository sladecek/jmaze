package com.github.sladecek.maze.jmaze.moebius;

import static org.junit.Assert.assertEquals;

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


	@Test(expected=IllegalArgumentException.class)
	public final void testGetOtherRoom_invalid() {
		assertEquals(1, maze.getRoomBehindWall(0, 2));
	}

	@Test
	public final void testGetOtherRoom_0() {
		assertEquals(1, maze.getRoomBehindWall(0, 0));
		assertEquals(0, maze.getRoomBehindWall(1, 0));
	}

	@Test
	public final void testGetOtherRoom_5() {
		assertEquals(5, maze.getRoomBehindWall(0, 5));
		assertEquals(0, maze.getRoomBehindWall(5, 5));
	}

	@Test
	public final void testGetOtherRoom_24() {
		assertEquals(6, maze.getRoomBehindWall(0, 24));
		assertEquals(0, maze.getRoomBehindWall(6, 24));
	}

	@Test
	public void testGetOtherRoom_22() {
		assertEquals(23, maze.getRoomBehindWall(22, 22));
		assertEquals(22, maze.getRoomBehindWall(23, 22));
	}

	@Test
	public final void testGetOtherRoom_23() {
		assertEquals(23, maze.getRoomBehindWall(18, 23));
		assertEquals(18, maze.getRoomBehindWall(23, 23));
	}
	
	@Test
	public final void testGetOtherRoom_41() {
		assertEquals(17, maze.getRoomBehindWall(23, 41));
		assertEquals(23, maze.getRoomBehindWall(17, 41));
	}

	@Test
	public final void testGetOtherRoom_42() {
		assertEquals(21, maze.getRoomBehindWall(0, 42));
		assertEquals(0, maze.getRoomBehindWall(21, 42));
	}

	@Test
	public final void testGetOtherRoom_51() {
		assertEquals(12, maze.getRoomBehindWall(9, 51));
		assertEquals(9, maze.getRoomBehindWall(12, 51));
	}
	
	
}
