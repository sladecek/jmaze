package com.github.sladecek.maze.jmaze.moebius;

import java.security.InvalidParameterException;

import com.github.sladecek.maze.jmaze.geometry.*;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.ILocalCoordinateSystem;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.TrivialCoordinateSystem;

public final class Moebius3dMapper implements IMaze3DMapper {

    public Moebius3dMapper(final Maze3DSizes sizes, final int height, final int width) {
        super();
        this.sizes = sizes;
        this.height = height;
        if (width % 2 != 0) {
            throw new InvalidParameterException(
                    "GridMapper - maze width must be even");
        }
        innerWallThicknessInmm = sizes.getCellSizeInmm() * sizes.getInnerWallToPixelRatio();
        outerWallThicknessInmm = sizes.getCellSizeInmm() * sizes.getOuterWallToPixelRatio();
        heightInmm = sizes.getCellSizeInmm() * height + innerWallThicknessInmm
                * (height - 1) + 2 * outerWallThicknessInmm;

        lengthInmm = sizes.getCellSizeInmm() * width + innerWallThicknessInmm
                * width;
        cellStepInmm = sizes.getCellSizeInmm() + innerWallThicknessInmm;

        geometry = new MoebiusStripGeometry(lengthInmm);

    }

    public double getLengthInmm() {
        return lengthInmm;
    }

    public double getHeightInmm() {
        return heightInmm;
    }

    // TODO @Override
    public Point3D mapPoint(int cellY, int cellX, double offsetY, double offsetX,
                            double offsetZ) {
        double y = (offsetY + cellY - height / 2) * cellStepInmm;
        double x = (offsetX + cellX) * cellStepInmm;
        return geometry.transform(new Point3D(x, y, offsetZ));
    }

    // TODO @Override
    public Point3D mapCorner(int cellX, EastWest ew, UpDown ud,
                             SouthNorth snWall, SouthNorth snEdge) {
        double x = (((ew == EastWest.east) ? 0 : 1) + cellX) * cellStepInmm;
        double y = heightInmm / 2;
        if (snWall == snEdge) {
            y -= outerWallThicknessInmm;
        }
        if (snWall == SouthNorth.north) {
            y *= -1;
        }

        double z = ud == UpDown.down ? 0 : sizes.getWallHeightInmm();

        return geometry.transform(new Point3D(x, y, z));
    }

    // TODO @Override
    public int getStepY(int y, int x) {
        return 1;
    }

    @Override
    public Point3D map(Point2DDbl image, Altitude altitude) {
        return null;
    }

    @Override
    public double inverselyMapLengthAt(Point2DDbl center, double v) {
        return 0;
    }

    @Override
    public ILocalCoordinateSystem createLocalCoordinateSystem(Point2DInt center) {
        return new TrivialCoordinateSystem();
    }


    private MoebiusStripGeometry geometry;
    private Maze3DSizes sizes;
    private int height;
    private double lengthInmm;
    private double heightInmm;
    private double innerWallThicknessInmm;
    private double outerWallThicknessInmm;
    private double cellStepInmm;

}
