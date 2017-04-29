package com.github.sladecek.maze.jmaze.geometry;

import com.github.sladecek.maze.jmaze.shapes.MarkShape;

/**
 * 2D point either in rectangular or polar coordinates. In case of polar coordinates, x is used for
 * angle, y for diameter.
 */
public final class Point2D {
    public Point2D(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public Point2D minus(Point2D other) {
        return new Point2D(x - other.x, y - other.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getCartesianLength() {
        return Math.sqrt(x * x + y * y);
    }

    public double getCartesianAngle() {
        return Math.atan2(y, x);
    }

    public int getAngle() {
        return x;
    }

    public double getAngleRad() {
        return 2 * Math.PI * getAngle() / (double) ANGLE_2PI;
    }

    public int getDiameter() {
        return y;
    }

    @Override
    public String toString() {
        return "Point2D(x=" + x + ", " + "y=" + y + ")";
    }
    public static final int ANGLE_2PI = 0x1000000;
    private int x;

    private int y;

}
