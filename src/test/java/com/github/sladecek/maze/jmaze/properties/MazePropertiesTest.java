package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.printstyle.Color;
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
        MazeProperties c = p.clone();
        assertEquals(1, c.getInt("int", 0, 10));
    }

    @Test
    public void getInt() throws Exception {
        assertEquals(1, p.getInt("int", 1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getIntMin() throws Exception {
        p.getInt("int", 2, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getIntMax() throws Exception {
        p.getInt("int", 0, 0);
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

    private MazeProperties p;
}