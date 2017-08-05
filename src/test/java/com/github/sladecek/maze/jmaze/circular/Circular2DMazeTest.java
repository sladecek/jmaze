package com.github.sladecek.maze.jmaze.circular;

import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Circular2DMazeTest {


    @Before
    public void setUp() throws Exception {
        int layerCount = 4;
        maze = new Circular2DMaze(layerCount);
        maze.makeMazeAllSteps(false);
    }

    @Test
    public void testGetRoomCount() {
        assertEquals(29, maze.getGraph().getRoomCount());
    }

    @Test
    public void testGetWallCount() {
        assertEquals(56, maze.getGraph().getWallCount());
    }

    @Test
    public void testGetWalls() {
        List<Integer> list = new ArrayList<Integer>();
        maze.getGraph().getWalls(0).iterator().forEachRemaining(list::add);
        assertEquals(4, list.size());
    }

    @Test
    public void testGetStartRoom() {
        assertEquals(0, maze.getGraph().getStartRoom());
    }

    @Test
    public void testGetTargetRoom() {
        assertEquals(28, maze.getGraph().getTargetRoom());
    }

    @Test
    public void testMarkShapes() {
        ArrayList<MarkShape> floors = new ArrayList<MarkShape>();
        ShapeContainer sc = maze.getFlatModel();
        for (IMazeShape2D s : sc.getShapes()) {
            if (s instanceof MarkShape) {
                floors.add((MarkShape) s);
            }
        }
        assertEquals(29, floors.size());


        assertEquals(0, floors.get(0).getY());
    }
    private Circular2DMaze maze;


}
