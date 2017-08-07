package com.github.sladecek.maze.jmaze.moebius;


import com.github.sladecek.maze.jmaze.geometry.*;
import com.github.sladecek.maze.jmaze.print3d.ConfigurableAltitudes;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.ILocalCoordinateSystem;

/**
 * Maps planar maze coordinates into 3D points.
 */
public final class Moebius3dMapper extends ConfigurableAltitudes implements IMaze3DMapper {

    public Moebius3dMapper(final int sizeAcross, final int sizeAlong, final double cellSizeInmm, final double innerWallSize) {
        super();

        this.sizeAcross = sizeAcross;
        this.sizeAlong = sizeAlong;
        this.cellSizeInmm = cellSizeInmm;
        this.innerWallSize = innerWallSize;
        if (sizeAlong % 2 != 0) {
            throw new IllegalArgumentException("Moebius maze size along must be even");
        }
        double innerWallThicknessInmm = cellSizeInmm * innerWallSize;

        double lengthInmm = cellSizeInmm * sizeAlong + innerWallThicknessInmm
                * sizeAlong;
        cellStepInmm = cellSizeInmm + innerWallThicknessInmm;

        geometry = new MoebiusStripGeometry(lengthInmm);

    }

    @Override
    public Point3D map(Point2DDbl image, Altitude altitude) {
        double y = (image.getY() - sizeAcross / 2) * cellStepInmm;
        double x = image.getX() * cellStepInmm;
        return geometry.transform(new Point3D(x, y, altitude.getValue() * 2.001));
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
        public MoebiusLocalCoordinateSystem(Point2DInt center, int maxX) {
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

    private final int sizeAcross;
    private final int sizeAlong;
    private final double cellSizeInmm;
    private final double innerWallSize;
    private MoebiusStripGeometry geometry;
    private double cellStepInmm;


}
