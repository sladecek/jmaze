package com.github.sladecek.maze.jmaze.print2d;
//REV1
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;

/*
 * Maps 2D cartesian coordinates used by shapes  into SVG cartesian coordinates.
 */
public class Cartesian2DMapper implements IMaze2DMapper {

    public Cartesian2DMapper(Point2DInt zeroPoint, int resolutionX, int resolutionY) {
        this.zeroPoint = zeroPoint;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
    }

    @Override
    public Point2DInt mapPoint(Point2DInt p) {
        return new Point2DInt(resolutionX * (p.getX()) + zeroPoint.getX(),
                resolutionY * (p.getY()) + zeroPoint.getY());
    }

    @Override
    public int mapLength(int l) {
        assert resolutionX == resolutionY;
        return l * resolutionX;
    }

    private final Point2DInt zeroPoint;
    private final int resolutionX;
    private final int resolutionY;
}
