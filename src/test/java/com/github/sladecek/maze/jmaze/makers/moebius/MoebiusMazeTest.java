package com.github.sladecek.maze.jmaze.makers.moebius;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import org.junit.Before;
import org.junit.Test;

public class MoebiusMazeTest {
    private MoebiusMaze maze;


    @Before
    public final void setUp() throws Exception {
        maze = new MoebiusMaze();

        MazeProperties p = new MoebiusMazeDescription().getDefaultProperties();
        p.put("sizeAlong", 6);
        p.put("sizeAcross", 4);
        maze.setProperties(p);

        maze.makeMazeAllSteps(false);
    }


    @Test
    public final void testGetRoomCount() {
    assertEquals(24, maze.getGraph().getRoomCount());
    }


    @Test
    public final void testGetStartRoom() {
        assertEquals(0, maze.getGraph().getStartRoom());
    }

    @Test
    public final void testGetTargetRoom() {
        assertEquals(19, maze.getGraph().getTargetRoom());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testGetOtherRoom_invalid() {
        assertEquals(1, maze.getGraph().getRoomBehindWall(0, 2));
    }

    @Test
    public final void testGetOtherRoom_0() {
        assertEquals(1, maze.getGraph().getRoomBehindWall(0, 0));
        assertEquals(0, maze.getGraph().getRoomBehindWall(1, 0));
    }

    @Test
    public final void testGetOtherRoom_5() {
        assertEquals(5, maze.getGraph().getRoomBehindWall(0, 5));
        assertEquals(0, maze.getGraph().getRoomBehindWall(5, 5));
    }

    @Test
    public final void testGetOtherRoom_24() {
        assertEquals(6, maze.getGraph().getRoomBehindWall(0, 24));
        assertEquals(0, maze.getGraph().getRoomBehindWall(6, 24));
    }

    @Test
    public void testGetOtherRoom_22() {
        assertEquals(23, maze.getGraph().getRoomBehindWall(22, 22));
        assertEquals(22, maze.getGraph().getRoomBehindWall(23, 22));
    }

    @Test
    public final void testGetOtherRoom_23() {
        assertEquals(23, maze.getGraph().getRoomBehindWall(18, 23));
        assertEquals(18, maze.getGraph().getRoomBehindWall(23, 23));
    }

    @Test
    public final void testGetOtherRoom_41() {
        assertEquals(17, maze.getGraph().getRoomBehindWall(23, 41));
        assertEquals(23, maze.getGraph().getRoomBehindWall(17, 41));
    }

    @Test
    public final void testGetOtherRoom_42() {
        assertEquals(21, maze.getGraph().getRoomBehindWall(0, 42));
        assertEquals(0, maze.getGraph().getRoomBehindWall(21, 42));
    }

    @Test
    public final void testGetOtherRoom_51() {
        assertEquals(12, maze.getGraph().getRoomBehindWall(9, 51));
        assertEquals(9, maze.getGraph().getRoomBehindWall(12, 51));
    }

    @Test
    public final void testCreate3dMapper() {
        IMaze3DMapper m = maze.create3DMapper();
        assertNotNull(m);
        assertTrue(m instanceof Moebius3dMapper );
    }

}
