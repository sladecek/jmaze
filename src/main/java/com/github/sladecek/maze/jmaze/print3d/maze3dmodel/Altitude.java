package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

/**
 * Floor altitudes in 3D model
 */
public enum Altitude {
    FRAME(-1),
    GROUND(0),
    FLOOR(1),
    CEILING(2);

    Altitude(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean hasPrev() {
        return this != MIN;
    }

    public Altitude prev() {
        return values()[ordinal() - 1];
    }
    public static Altitude MIN = FRAME;
    public static Altitude MAX = CEILING;
    private int value;
}
