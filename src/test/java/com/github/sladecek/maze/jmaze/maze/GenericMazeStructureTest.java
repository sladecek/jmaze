package com.github.sladecek.maze.jmaze.maze;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GenericMazeStructureTest {

    public GenericMazeStructure s;
    
    @Before
    public void setUp() throws Exception {
        s = new GenericMazeStructure();
        s.addRoom();
        s.addRoom();
        s.addRoom();
        s.addWall(0, 1);
        s.addWall(1, 2);
    }

    @Test
    public void testGetRoomCount() {
        assertEquals(3, s.getRoomCount());
    }

    @Test
    public void testGetWallCount() {
        assertEquals(2, s.getWallCount());
    }

    @Test
    public void testGetWalls() {
        assertEquals(0, (int)s.getWalls(1).iterator().next());
        assertEquals(1, (int)s.getWalls(1).iterator().next());
        assertEquals(false, s.getWalls(1).iterator().next());
    }

    @Test
    public void testAddRoom() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddWall() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetRoomBehindWall() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetWallProbabilityWeight() {
        fail("Not yet implemented");
    }

}
