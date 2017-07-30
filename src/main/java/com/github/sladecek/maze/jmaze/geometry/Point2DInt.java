package com.github.sladecek.maze.jmaze.geometry;

/**
 * 2D point either in rectangular or polar coordinates. In case of polar coordinates, x is used for
 * angle, y for diameter. Coordinates are integer.
 */
public final class Point2DInt implements  Comparable<Point2DInt> {
    public Point2DInt(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public Point2DInt minus(Point2DInt other) {
        return new Point2DInt(x - other.x, y - other.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
        return "Point2DInt(x=" + x + ", " + "y=" + y + ")";
    }

    @Override
    public int compareTo(Point2DInt p) {
        int cx = Integer.compare(this.getY(), p.getY());
        if (cx == 0) {
            return Integer.compare(this.getX(), p.getX());
        }
        return cx;
    }

    public Point2DDbl toDouble() {
        return new Point2DDbl(x,y);
    }

    public static final int ANGLE_2PI = 0x1000000;
    private int x;

    private int y;

}
