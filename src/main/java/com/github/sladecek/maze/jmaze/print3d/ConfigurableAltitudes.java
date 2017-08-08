package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;

/**
 * Helper class. Enables to define numerical values of feature altitudes such as wall heights.
 */
public class ConfigurableAltitudes {

    public double mapAltitude(Altitude a) {
        return altitudes[a.ordinal()];
    }

    private void setAltitude(Altitude a, double value) {
        altitudes[a.ordinal()] = value;
    }

    private void configureFromWallHeight(double wallHeight) {
        final int wallToFloorRatio = 5;
        setAltitude(Altitude.FRAME,-wallHeight / wallToFloorRatio);
        setAltitude(Altitude.GROUND,0);
        setAltitude(Altitude.FLOOR, wallHeight / wallToFloorRatio);
        setAltitude(Altitude.CEILING,wallHeight);
    }

    public void configureAltitudes(MazeProperties properties) {
        double wh  = properties.getDouble("wallHeight");
        configureFromWallHeight(wh);
    }

    private final double[] altitudes = new double[Altitude.values().length];
    {
        // default setting - each altitude is equal to value
        for (Altitude a: Altitude.values()) {
            altitudes[a.ordinal()] = a.getValue();
        }
    }
}
