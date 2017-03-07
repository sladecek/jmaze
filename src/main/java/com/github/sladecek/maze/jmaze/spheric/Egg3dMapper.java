package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.geometry.*;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Map integer room cordinates do 3D coordinates in the egg.
 */
public final class Egg3dMapper implements IMaze3DMapper {
    public Egg3dMapper(EggGeometry egg, EggMaze maze) {
        super();
        this.egg = egg;
        this.maze = maze;
    }

    /**
     * Map integer room coordinates to 3D coordinates on the egg.
     *
     * @param cellVerical    Longitudal room number. Unit is 2*pi/#of rooms on the equator. The
     *                       rooms on the equator have consecutive numbers 0,1,2.... Following layers
     *                       may have less rooms and their numbers may skip some numbers 0,4,8....
     * @param cellHorizontal Latitudal room number. Zero index is equator.
     * @return 3D coordinate of south-western corner of the room + offset.
     */
    @Override
    public Point3D mapPoint(int cellVertical, int cellHorizontal, double offsetV,
                            double offsetH, double offsetA) {

        LOG.log(Level.INFO, "mapPoint v=" + cellVertical + " h=" + cellHorizontal
                + " ov=" + offsetV + " oh=" + offsetH);
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
        OrientationVector2D normal = egg.computeNormalVector(xx);
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

        return result;
    }

    @Override
    public Point3D mapCorner(int cellX, EastWest ew, UpDown ud,
                             SouthNorth snWall, SouthNorth snEdge) {
        throw new IllegalArgumentException("Egg has no corners");
    }

    @Override
    public int getStepY(int y, int x) {
        final int eqCnt = maze.getEquatorCellCnt();

        // South hemisphere has separate data structure.
        SouthNorth sn = SouthNorth.north;
        int indexH = x;
        if (x < 0) {
            sn = SouthNorth.south;
            indexH = -x;
        }
        EggMazeHemisphere hem = maze.getHemisphere(sn);
        final int layerCnt = hem.getCircleCnt();

        assert indexH >= 0 : "Invalid horizontal coordinate - negative";
        assert indexH <= layerCnt : "Invalid horizontal coordinate - too big";

        final boolean isPole = indexH > hem.getCircleCnt();
        final boolean isPolarCircle = indexH == hem.getCircleCnt();
        if (isPole) {
            return 1;
        } else {
            // polar circle uses room count from last normal layer to form polar cups
            int where = indexH;
            if (isPolarCircle) {
                where = indexH - 1;
            }
            int roomCntThis = hem.getWallCntOnCircle(where);
            return eqCnt / roomCntThis;
        }
    }
    private static final double SMALL_Y_MM = 0.001;
    private static final Logger LOG = Logger.getLogger("maze.jmaze");
    private EggGeometry egg;
    private EggMaze maze;


}
