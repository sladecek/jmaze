package com.github.sladecek.maze.jmaze.geometry;
//REV1
/*
 * Two dimensional free vector (without origin).
 */
public final class OrientationVector2D {

    public OrientationVector2D(final double x, final double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "OrientationVector2D [x=" + x + ", y=" + y + "]";
    }

    public OrientationVector2D getOrthogonal() {
        //noinspection SuspiciousNameCombination
        return new OrientationVector2D(y, -x);
    }

    private final double x;
    private final double y;
}
