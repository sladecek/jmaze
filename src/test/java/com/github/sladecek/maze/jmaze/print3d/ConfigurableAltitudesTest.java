package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the <code>{@link ConfigurableAltitudes}</code> class.
 */
public class ConfigurableAltitudesTest {

    @Before
    public void setup() {
        ca = new ConfigurableAltitudes();
    }

    @Test
    public void defaultMappings() throws Exception {
        final double epsilon = 0.000001;
        assertEquals(Altitude.FRAME.getValue(),ca.mapAltitude(Altitude.FRAME), epsilon);
        assertEquals(Altitude.GROUND.getValue(),ca.mapAltitude(Altitude.GROUND), epsilon);
        assertEquals(Altitude.FLOOR.getValue(),ca.mapAltitude(Altitude.FLOOR), epsilon);
        assertEquals(Altitude.CEILING.getValue(),ca.mapAltitude(Altitude.CEILING), epsilon);
    }

    @Test
    public void configureAltitudes() throws Exception {
        MazeProperties props = new MazeProperties();
        props.put("wallHeight", 10.0);
        ca.configureAltitudes(props);
        final double epsilon = 0.000001;
        assertEquals(-2,ca.mapAltitude(Altitude.FRAME), epsilon);
        assertEquals(0,ca.mapAltitude(Altitude.GROUND), epsilon);
        assertEquals(2,ca.mapAltitude(Altitude.FLOOR), epsilon);
        assertEquals(10,ca.mapAltitude(Altitude.CEILING), epsilon);
    }

    private ConfigurableAltitudes ca;
}