package com.github.sladecek.maze.jmaze.generator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.maze.GenericMazeStructure;
import static org.junit.Assert.assertEquals;

public class DepthFirstMazeGeneratorTest {

    @Test
    public void testDepthFirstMazeGenerator() {
     // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first");

        // the following prints "first"
        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));
    }

    @Test
    public void testGenerateMazeToY() {
        
        /*  0 1 2 3 4 */
        /*      5     */
        /*      6     */
        
        GenericMazeStructure s = new GenericMazeStructure();
        s.addRoom();
        s.addRoom();
        s.addRoom();
        s.addRoom();
        s.addRoom();
        s.addRoom();
        s.addRoom();
        
        s.setStartRoom(0);
        s.setTargetRoom(6);
        
        s.addWall(0, 1);
        s.addWall(1, 2);
        s.addWall(2, 3);
        s.addWall(3, 4);
        s.addWall(2, 5);
        s.addWall(5, 6);
        
        Random r = mock(Random.class);
        when(r.nextInt(2)).thenReturn(1);
        DepthFirstMazeGenerator g = new DepthFirstMazeGenerator(r);
        MazeRealization e = g.generateMaze(s);
        
        assertEquals(5, e.getSolution().size());
        
        assertEquals(0, (int)e.getSolution().get(0));
        assertEquals(1, (int)e.getSolution().get(1));
        assertEquals(2, (int)e.getSolution().get(2));
        assertEquals(5, (int)e.getSolution().get(3));
        assertEquals(6, (int)e.getSolution().get(4));
        
        assertEquals(false, e.isWallClosed(0));        
        assertEquals(false, e.isWallClosed(1));
        assertEquals(false, e.isWallClosed(2));
        assertEquals(false, e.isWallClosed(3));
        assertEquals(false, e.isWallClosed(4));
        assertEquals(false, e.isWallClosed(5));
    }

    
    @Test
    public void testGenerateMazeTo2x3() {
        
        /*  0 1 2  */
        /*  3 4 5  */
        
        GenericMazeStructure s = new GenericMazeStructure();
        s.addRoom();
        s.addRoom();
        s.addRoom();
        s.addRoom();
        s.addRoom();
        s.addRoom();
        
        s.setStartRoom(0);
        s.setTargetRoom(5);
        
        assertEquals(0, s.addWall(0, 1)); 
        assertEquals(1, s.addWall(1, 2));
        assertEquals(2, s.addWall(0, 3));
        assertEquals(3, s.addWall(3, 4));
        assertEquals(4, s.addWall(1, 4));
        assertEquals(5, s.addWall(4, 5));
        assertEquals(6, s.addWall(2, 5));
        
        Random r = mock(Random.class);
        when(r.nextInt(2)).thenReturn(0);
        when(r.nextInt(2)).thenReturn(0);
        
        DepthFirstMazeGenerator g = new DepthFirstMazeGenerator(r);
        MazeRealization e = g.generateMaze(s);
        
        assertEquals(4, e.getSolution().size());
        
        assertEquals(0, (int)e.getSolution().get(0));
        assertEquals(1, (int)e.getSolution().get(1));
        assertEquals(2, (int)e.getSolution().get(2));
        assertEquals(5, (int)e.getSolution().get(3));
        
        assertEquals(false, e.isWallClosed(0));        
        assertEquals(false, e.isWallClosed(1));
        assertEquals(true, e.isWallClosed(2));
        assertEquals(false, e.isWallClosed(3));
        assertEquals(false, e.isWallClosed(4));
        assertEquals(true, e.isWallClosed(5));
        assertEquals(false, e.isWallClosed(6));

    }

    
}
