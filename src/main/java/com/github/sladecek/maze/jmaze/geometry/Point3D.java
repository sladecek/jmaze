package com.github.sladecek.maze.jmaze.geometry;
//REV1

/**
 * Point in 3D.
 */
@SuppressWarnings("SameParameterValue")
public final class Point3D {

    public Point3D(double x, double y, double z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Point3D midpoint(Point3D p1, Point3D p2) {
        return new Point3D((p1.x + p2.x) / 2, (p1.y + p2.y) / 2,
                (p1.z + p2.z) / 2);
    }

    public static double computeDistance(Point3D p1, Point3D p2) {
        return Math.sqrt(
                Math.pow(p1.getX() - p2.getX(), 2) +
                        Math.pow(p1.getY() - p2.getY(), 2) +
                        Math.pow(p1.getZ() - p2.getZ(), 2)
        );
    }

    public double getX() {
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + ", z=" + z + "]";
    }
    private double x;
    private double y;
    private double z;

}
