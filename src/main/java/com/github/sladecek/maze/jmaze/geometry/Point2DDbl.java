package com.github.sladecek.maze.jmaze.geometry;
//REV1
/**
 * Point in 2D - real number coordinates.
 */
public final class Point2DDbl {

    public Point2DDbl(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public Point2DDbl(Point2DDbl image) {
        this(image.getX(), image.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + " ]";
    }

    public Point2DDbl minus(Point2DDbl other) {
        return new Point2DDbl(x - other.x, y - other.y);
    }


    public double getCartesianAngle() {
        return Math.atan2(y, x);
    }

    private final double x;
    private final double y;

}
