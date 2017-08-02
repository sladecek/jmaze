package com.github.sladecek.maze.jmaze.moebius;


import com.github.sladecek.maze.jmaze.geometry.*;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.ILocalCoordinateSystem;

public final class Moebius3dMapper implements IMaze3DMapper {

    public Moebius3dMapper(final Maze3DSizes sizes, final int height, final int width) {
        super();

        this.height = height;
        this.width = width;
        if (width % 2 != 0) {
            throw new IllegalArgumentException(
                    "GridMapper - maze width must be even");
        }
        double innerWallThicknessInmm = sizes.getCellSizeInmm() * sizes.getInnerWallToPixelRatio();

        double lengthInmm = sizes.getCellSizeInmm() * width + innerWallThicknessInmm
                * width;
        cellStepInmm = sizes.getCellSizeInmm() + innerWallThicknessInmm;

        geometry = new MoebiusStripGeometry(lengthInmm);

    }

    @Override
    public Point3D map(Point2DDbl image, Altitude altitude) {
        double y = (image.getY() - height / 2) * cellStepInmm;
        double x = image.getX() * cellStepInmm;
        return geometry.transform(new Point3D(x, y, altitude.getValue()));
    }

    @Override
    public ILocalCoordinateSystem createLocalCoordinateSystem(Point2DInt center) {
        return new Moebius3dMapper.MoebiusLocalCoordinateSystem(center, width);
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
    private final int height;
    private final int width;
    private MoebiusStripGeometry geometry;
    private double cellStepInmm;

}
