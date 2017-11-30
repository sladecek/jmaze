package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.print.Color;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Tests <code>MazeProperties</code> class.
 */
public class MazePropertiesTest {

    @Before
    public void setup() {
        p = new MazeProperties();
        p.put("int", 1);
        p.put("double", 2.2);
        p.put("string", "st");
        p.put("color", new Color("010101"));
        p.put("bool", true);
    }

    @Test
    public void hasProperty() throws Exception {
        assertTrue(p.hasProperty("int"));
        assertFalse(p.hasProperty("nint"));
    }

    @Test
    public void testClone() throws Exception {
        MazeProperties c = p.deepCopy();
        assertEquals(1, c.getInt("int"));
    }

    @Test
    public void getInt() throws Exception {
        assertEquals(1, p.getInt("int"));
    }


    @Test
    public void getDouble() throws Exception {
        assertEquals(2.2, p.getDouble("double"), 0.000001);
    }

    @Test
    public void getColor() throws Exception {
        assertEquals(new Color("010101"), p.getColor("color"));
    }

    @Test
    public void getString() throws Exception {
        assertEquals("st", p.getString("string"));
    }

    @Test
    public void getBoolean() throws Exception {
        assertEquals(true, p.getBoolean("bool"));
    }

    @Test
    public void isNotEmpty() throws Exception {
        assertFalse(p.isEmpty());
    }

    @Test
    public void isEmpty() throws Exception {
        p = new MazeProperties();
        assertTrue(p.isEmpty());
    }

    @Test
    public void updateInt() throws Exception {
        MazeProperties p2 = new MazeProperties();
        p2.put("int", "7");
        p.updateFromStrings(p2);
        assertEquals(7, p.getInt("int"));
    }

    @Test
    public void updateDouble() throws Exception {
        MazeProperties p2 = new MazeProperties();
        p2.put("double", "7.7");
        p.updateFromStrings(p2);
        assertEquals(7.7, p.getDouble("double"), 0.000001);
    }

    @Test
    public void updateBool() throws Exception {
        MazeProperties p2 = new MazeProperties();
        p2.put("bool", "false");
        p.updateFromStrings(p2);
        assertEquals(false, p.getBoolean("bool"));
    }

    @Test
    public void updateString() throws Exception {
        MazeProperties p2 = new MazeProperties();
        p2.put("string", "7");
        p.updateFromStrings(p2);
        assertEquals("7", p.getString("string"));
    }

    @Test
    public void updateColor() throws Exception {
        MazeProperties p2 = new MazeProperties();
        p2.put("color", "019988");
        p.updateFromStrings(p2);
        assertEquals(new Color("019988"), p.getColor("color"));
    }

    private MazeProperties p;
}