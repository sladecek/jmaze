package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;

/**
 * Helper class for mapers. Enables to define numerical values of feature altitudes such as wall heights.
 */
public class ConfigurableAltitudes {

    public double mapAltitude(Altitude a) {
        return altitudes[a.ordinal()];
    }

    public void setAltitude(Altitude a, double value) {
        altitudes[a.ordinal()] = value;
    }

    public void configureAltitudes(Maze3DSizes cfg) {
        double wh  = cfg.getWallHeightInmm();
        setAltitude(Altitude.FRAME,-wh / 5);
        setAltitude(Altitude.GROUND,0);
        setAltitude(Altitude.FLOOR, wh /5);
        setAltitude(Altitude.CEILING,wh);
    }

    private double altitudes[] = new double[Altitude.values().length];
    {
        // default setting - each altitude is equal to value
        for (Altitude a: Altitude.values()) {
            altitudes[a.ordinal()] = a.getValue();
        }
    }
}
