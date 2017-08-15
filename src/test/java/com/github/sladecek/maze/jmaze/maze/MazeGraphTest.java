package com.github.sladecek.maze.jmaze.maze;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class MazeGraphTest {

    private MazeGraph s;
    
    @Before
    public void setUp() throws Exception {
        s = new MazeGraph();
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
        Iterator<Integer> it = s.getWalls(1).iterator();
        assertEquals(0, (int)it.next());
        assertEquals(1, (int)it.next());
        assertEquals(false, it.hasNext());
    }

    @Test
    public void testAddRoom() {
        int rn = s.addRoom();
        assertEquals(3, rn);
    }

    @Test
    public void testAddWall() {
        int rw = s.addWall(0, 2);
        assertEquals(2, rw);    }

    @Test
    public void testGetRoomBehindWall() {
        int r2 = s.getRoomBehindWall(0, 0);
        assertEquals(1, r2);
        int r1 = s.getRoomBehindWall(1, 0);
        assertEquals(0, r1);
    }

    @Test
    public void testGetWallProbabilityWeight() {
        assertEquals(1, s.getWallProbabilityWeight(0));
        assertEquals(1, s.getWallProbabilityWeight(1));
    }

}
