package com.github.sladecek.maze.jmaze.makers.moebius;

import com.github.sladecek.maze.jmaze.geometry.Point2DDbl;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.ConfigurableAltitudes;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.ILocalCoordinateSystem;

/**
 * Maps planar 'shape' coordinates into 3D points.
 */
public final class Moebius3dMapper extends ConfigurableAltitudes implements IMaze3DMapper {

    /**
     * Creates <code>{@link Moebius3dMapper}</code>.
     *
     * @param sizeAcross    number of cells across the maze.
     * @param sizeAlong     number of cells along the maze loop.
     * @param cellSize      cell size in mm.
     * @param wallThickness wall size in mm.
     */
    public Moebius3dMapper(final int sizeAcross, final int sizeAlong,
                           final double cellSize, final double wallThickness) {
        super();

        this.sizeAcross = sizeAcross;
        this.sizeAlong = sizeAlong;

        if (sizeAlong % 2 != 0) {
            throw new IllegalArgumentException("Moebius maze must have even number of cells.");
        }
        
        if (sizeAlong <= 0 || sizeAcross <= 0 || cellSize <= 0 || wallThickness <= 0 ) {
            throw new IllegalArgumentException("Maze sizes must be positive.");
        }
        
        if (wallThickness > cellSize/2) {
            throw new IllegalArgumentException("Maze walls are too thick.");
        }

        this.length = cellSize * sizeAlong + wallThickness * sizeAlong;
        this.cellStep = cellSize + wallThickness;
        this.geometry = new MoebiusStripGeometry(length);
    }

    @Override
    public Point3D map(Point2DDbl image, Altitude altitude) {
        final Point3D imagePoint = computeImagePoint(image, altitude);
        return geometry.transform(imagePoint);
    }

    public Point3D computeImagePoint(Point2DDbl image, Altitude altitude) {
        double y = (image.getY() - sizeAcross / 2) * cellStep;
        double x = image.getX() * cellStep;
        return new Point3D(x, y, mapAltitude(altitude));
    }

    @Override
    public ILocalCoordinateSystem createLocalCoordinateSystem(Point2DInt center) {
        return new Moebius3dMapper.MoebiusLocalCoordinateSystem(center, sizeAlong);
    }

    @Override
    public boolean isAltitudeVisible(Altitude alt) {
        return (alt != Altitude.FRAME) && (alt != Altitude.GROUND);
    }


    class MoebiusLocalCoordinateSystem implements ILocalCoordinateSystem {
        MoebiusLocalCoordinateSystem(Point2DInt center, int maxX) {
            this.center = center;
            this.maxX = maxX;
        }

        @Override
        public Point2DDbl transformToLocal(Point2DDbl image) {
            // There is a sew at x = 0. Any positive coordinate x can be also represented as negative x-maxX.
            // Select the nearest one.
            double deltaX = image.getX() - center.getX();
            if (deltaX > maxX / 2) {
                return new Point2DDbl(image.getX() - maxX, image.getY());
            }
            if (deltaX < -maxX / 2) {
                return new Point2DDbl(image.getX() + maxX, image.getY());
            }
            return new Point2DDbl(image);
        }

        private final Point2DInt center;
        private final double maxX;
    }


    public double getLength() {
        return length;
    }

    public double getCellStep() {
        return cellStep;
    }

    public MoebiusStripGeometry getGeometry() {
        return geometry;
    }

    private final int sizeAcross;
    private final int sizeAlong;
    private final double length;
    private final double cellStep;
    private MoebiusStripGeometry geometry;

}
