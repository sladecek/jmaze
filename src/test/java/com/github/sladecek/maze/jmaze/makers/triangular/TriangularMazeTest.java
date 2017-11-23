package com.github.sladecek.maze.jmaze.makers.triangular;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.*;
import org.junit.Before;
import org.junit.Test;

public class TriangularMazeTest {
	
	private TriangularMaze maze;

	@Before
	public void setUp() throws Exception {

		final int size = 3;

		maze = new TriangularMaze();
		MazeProperties p = new TriangularMazeDescription().getDefaultProperties();
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
		List<Integer> list = new ArrayList<>();
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

		assertEquals(2, maze.getGraph().getRoomBehindWall(0, 2));

		
		assertEquals(2, maze.getGraph().getRoomBehindWall(1, 0));
		assertEquals(5, maze.getGraph().getRoomBehindWall(1, 7));
		
		assertEquals(1, maze.getGraph().getRoomBehindWall(2, 0));
		assertEquals(3, maze.getGraph().getRoomBehindWall(2, 1));
		assertEquals(0, maze.getGraph().getRoomBehindWall(2, 2));

		assertEquals(2, maze.getGraph().getRoomBehindWall(3, 1));
		assertEquals(7, maze.getGraph().getRoomBehindWall(3, 8));

		assertEquals(5, maze.getGraph().getRoomBehindWall(4, 3));
		
		assertEquals(4, maze.getGraph().getRoomBehindWall(5, 3));
		assertEquals(6, maze.getGraph().getRoomBehindWall(5, 4));
		assertEquals(1, maze.getGraph().getRoomBehindWall(5, 7));

	}

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
		
        assertEquals(36, floors.get(0).getX());
		assertEquals(10, floors.get(0).getY());

        assertEquals(36, floors.get(2).getX());
        assertEquals(30, floors.get(2).getY());

        assertEquals(24, floors.get(5).getX());
		assertEquals(50, floors.get(5).getY());

	}

    @Test
    public void testWallShapes() {
        ArrayList<WallShape> walls = new ArrayList<>();
        Shapes sc = maze.getAllShapes();
        for (IMazeShape s: sc.getShapes()) {
            if (s instanceof WallShape) {
                walls.add((WallShape)s);
            }
        }
        assertEquals(18, walls.size());

        assertEquals(-1, walls.get(0).getWallId());
        assertEquals(-1, walls.get(1).getWallId());
        assertEquals(-1, walls.get(2).getWallId());
        assertEquals(0, walls.get(3).getWallId());
        assertEquals(24, walls.get(3).getX1());
        assertEquals(36, walls.get(3).getX2());
        assertEquals(20, walls.get(3).getY1());
        assertEquals(40, walls.get(3).getY2());

    }
}
