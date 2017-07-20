package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

/**
 * Floor altitudes in 3D model
 */
public enum Altitude {
    FRAME(-1),
    GROUND(0),
    FLOOR(1),
    CEILING(2);

    public int getValue() {
        return value;
    }

    private int value;

    Altitude(int value) {
        this.value = value;
    }
}
