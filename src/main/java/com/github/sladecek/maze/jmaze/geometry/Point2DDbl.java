package com.github.sladecek.maze.jmaze.geometry;

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
    }public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + " ]";
    }

    public static double computeDistance(Point2DDbl p1, Point2DDbl p2) {
        return Math.sqrt(
                Math.pow(p1.getX()-p2.getX(), 2)+
                Math.pow(p1.getY()-p2.getY(), 2)
        );
    }

    public Point2DDbl minus(Point2DDbl other) {
        return new Point2DDbl(x - other.x, y - other.y);
    }


    public double getCartesianLength() {
        return Math.sqrt(x * x + y * y);
    }

    public double getCartesianAngle() {
        return Math.atan2(y, x);
    }

    private double x;
    private double y;

}
