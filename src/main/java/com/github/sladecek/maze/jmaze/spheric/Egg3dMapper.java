package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.geometry.*;
import com.github.sladecek.maze.jmaze.print3d.ConfigurableAltitudes;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.ILocalCoordinateSystem;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Map integer room coordinates do 3D coordinates in the egg.
 */
final class Egg3dMapper extends ConfigurableAltitudes implements IMaze3DMapper {
    public Egg3dMapper(EggGeometry egg, EggMaze maze) {
        super();
        this.egg = egg;
        this.maze = maze;
    }

    /**
     * Map integer room coordinates to 3D coordinates on the egg.
     *
     * @return 3D coordinate of south-western corner of the room + offset.
     */
    @Override
    public Point3D map(Point2DDbl image, Altitude altitude) {

        final double x = image.getX()/EggMaze.res;
        final double y = image.getY()/EggMaze.res;
        int cellVertical = (int) Math.round(y);
        int cellHorizontal = (int) Math.round(x);

        double offsetV = y - cellVertical;
        double offsetH = x - cellHorizontal;
        double offsetA = mapAltitude(altitude);

        final int eqCnt = maze.getEquatorCellCnt();

        // South hemisphere has separate data structure.
        SouthNorth sn = SouthNorth.north;
        int indexH = cellHorizontal;
        if (cellHorizontal < 0) {
            sn = SouthNorth.south;
            indexH = -cellHorizontal;
        }
        EggMazeHemisphere hem = maze.getHemisphere(sn);
        final int layerCnt = hem.getCircleCnt();

        assert indexH >= 0 : "Invalid horizontal coordinate - negative";
        assert indexH <= layerCnt : "Invalid horizontal coordinate - too big";

        // (1) Compute coordinates in meridian plane. -------------------------------------
        double xx; // coordinate along egg main axis, from the center towards pole
        double yy; // coordinate from the egg axis towards surface

        final boolean isPolarRoom = indexH >= hem.getCircleCnt();
        if (!isPolarRoom) {
            xx = hem.getLayerXPosition(indexH);
            // y coordinate on the egg surface
            final double ySurface = egg.computeY(xx);
            yy = ySurface;
            // When layers have different number of rooms, the split rooms do not meet on
            // egg surface but slightly below on the line connecting the corners
            // of the common room.
            int roomCntThis = hem.getWallCntOnCircle(indexH);
            int roomCntNext = hem.getWallCntOnCircle(indexH + 1);
            assert roomCntThis >= roomCntNext : "Egg cannot extend.";
            int k = roomCntThis / roomCntNext;
            if (k > 1) {
                // ratio of corner distance divided by ySurface
                double lra = 2 * Math.sin(Math.PI / roomCntNext);
                double ik = (double) (cellVertical % k) / (double) k;
                yy = ySurface * Math.sqrt((ik * ik - ik) * lra * lra + 1);
            }
        } else {
            // polar layer - this defines "equatorial" side of the polar room
            // should not be used for the "polar" side
            xx = (hem.getPoleXPosition() + hem.getLastLayerXPosition()) / 2;
            yy = SMALL_Y_MM;
        }


        // (2) Add local offsets. ------------------------------------------------------
        OrientationVector2D normal = egg.computeNormalArrayList(xx);
        OrientationVector2D tangent = normal.getOrthogonal();
        double xxx = xx + offsetH * tangent.getX() + offsetA * normal.getX();
        double yyy = yy + offsetH * tangent.getY() + offsetA * normal.getY();

        LOG.log(Level.INFO, "local offsets x=" + xx + " offsetA=" + offsetA +
                " normal=" + normal + " tangent=" + tangent +
                " xxx=" + xxx + " yyy=" + yyy);

        // (3) Rotate meridian plane by longitude angle. -------------------------------
        double angle = 2 * Math.PI * cellVertical / eqCnt;
        double yyyy = yyy * Math.cos(angle) - offsetV * Math.sin(angle);
        double zzzz = -yyy * Math.sin(angle) - offsetV * Math.cos(angle);
        Point3D result = new Point3D(xxx, yyyy, zzzz);


        LOG.log(Level.INFO, "mapPoint "+image+" v=" + cellVertical + "+"+ offsetV+" h=" + cellHorizontal
                + " +" + offsetH+" =>"+result);

        return result;
    }

    class EggLocalCoordinateSystem implements ILocalCoordinateSystem {
        public EggLocalCoordinateSystem(Point2DInt center, int eqCnt) {
            this.center = center;
            this.maxY = eqCnt*EggMaze.res;
        }

        @Override
        public Point2DDbl transformToLocal(Point2DDbl image) {
            // There is a sew at y = 0. Any positive coordinate y can be also represented as negative y-maxY.
            // Select the nearest one.
            double deltaY = image.getY() - center.getY();
            if (deltaY > maxY/2) {
                return new Point2DDbl(image.getX(), image.getY()-maxY);
            }
            if (deltaY < -maxY/2 ) {
                return new Point2DDbl(image.getX(), image.getY()+maxY);
            }
            return new Point2DDbl(image);
        }

        private final Point2DInt center;
        private final double maxY;
    }

    @Override
    public ILocalCoordinateSystem createLocalCoordinateSystem(Point2DInt center) {

        final int eqCnt = maze.getEquatorCellCnt();
        return new EggLocalCoordinateSystem(center, eqCnt);
    }


    private static final double SMALL_Y_MM = 0.001;
    private static final Logger LOG = Logger.getLogger("maze");
    private final EggGeometry egg;
    private final EggMaze maze;


}
