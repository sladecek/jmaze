package com.github.sladecek.maze.jmaze.spheric;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * 
 * Rooms and walls of a maze on an egg-like shape.
 *
 */
public final class EggMaze extends Maze implements IMazeStructure {

    public EggMaze(EggGeometry egg, int equatorCellCnt) {
        this.egg = egg;
        this.equatorCellCnt = equatorCellCnt;
        this.baseRoomSizeInmm = egg.computeBaseRoomSizeInmm(equatorCellCnt);
        north = new EggMazeHemisphere(egg.getEllipseMajorInmm());
        south = new EggMazeHemisphere(-egg.getEllipseMajorInmm());

        if (this.equatorCellCnt < MINIMAL_ROOM_COUNT_ON_EGG_MAZE_EQUATOR) {
            throw new IllegalArgumentException(
                    "Maze must have at least " + MINIMAL_ROOM_COUNT_ON_EGG_MAZE_EQUATOR + "cells.");
        }

        if (!isPowerOfTwo(this.equatorCellCnt)) {
            throw new IllegalArgumentException("Cell number must be power of 2.");
        }

        // Build topological structure of the maze and maze shapes at the same
        // time.
        // all walls will be generated
        buildMaze();

    }

    public EggMazeHemisphere getHemisphere(SouthNorth sn) {
        return sn == SouthNorth.north ? north : south;
    }

    public Vector<Integer> computeRoomCounts(Vector<Double> layerXPosition, int layerRoomCnt, double baseRoomSizeInmm) {

        final int layerCnt = layerXPosition.size();
        Vector<Integer> result = new Vector<Integer>();
        // equator
        result.add(layerRoomCnt);
        int roomCnt = layerRoomCnt;

        // all layers except the polar layer
        for (int i = 1; i < layerCnt - 1 - 1; i++) {
            final double x = layerXPosition.elementAt(i);
            final double y = egg.computeY(x);

            double currentRoomSizeInmm = 2 * Math.PI * y / roomCnt;

            if (currentRoomSizeInmm < baseRoomSizeInmm / 2) {
                // egg is becoming too narrow, join two cells together
                roomCnt /= 2;
                if (roomCnt < 4) {
                    break;
                }
            }
            result.add(roomCnt);
        }

        // polar layer has the same number of rooms as the last normal layer
        result.add(roomCnt);

        return result;
    }

    private void buildMaze() {

        final boolean isPolar = false;
        int width = 0;
        int height = 0;
        setContext(new ShapeContext(isPolar, height, width, 1, 1, 50, 50));

        // generate both hemispheres
        for (SouthNorth sn : SouthNorth.values()) {
            divideSpace(sn);
            LOG.log(Level.INFO, "generate hemisphere " + sn + " cnt=" + getHemisphere(sn).getCircleCnt());
            generateRooms(sn);

            generateParallelWalls(sn);
            generateMeridianWalls(sn);
        }
        generateParallelWallsOnEquator();
        assignStartAndTragetRooms();

    }

    private void assignStartAndTragetRooms() {
        setStartRoom(north.getGreenwichRoom(north.getCircleCnt() - 1));
        setTargetRoom(south.getGreenwichRoom(south.getCircleCnt() - 1));

    }

    private void divideSpace(SouthNorth sn) {
        final EggMazeHemisphere h = getHemisphere(sn);
        Vector<Double> xPos = egg.divideMeridian(baseRoomSizeInmm, sn);

        Vector<Integer> cellCnt = computeRoomCounts(xPos, equatorCellCnt, baseRoomSizeInmm);
        h.setLayerXPosition(new Vector<Double>(xPos.subList(0, cellCnt.size())));
        h.setLayerRoomCnt(cellCnt);

    }

    private void generateRooms(SouthNorth sn) {

        final EggMazeHemisphere h = getHemisphere(sn);

        final int cnt = h.getCircleCnt();
        for (int ix = 0; ix < cnt; ix++) {
            int x = ix;
            if (sn == SouthNorth.south) {
                x = -1 - ix;
            }
            final int cntThis = h.getRoomCntBeforeCircle(ix);
            final int cntNext = h.getRoomCntAfterCircle(ix);
            LOG.log(Level.INFO,
                    "generate row of rooms ix=" + ix + " x=" + x + " ctnThis=" + cntThis + " cntNext=" + cntNext);
            generateRowOfRooms(h, x, cntThis, cntNext, h.isPolarLayer(ix));
        }
    }

    private void generateRowOfRooms(EggMazeHemisphere hemisphere, int ix, int cntThis, int cntNext,
            boolean isPolarLayer) {
        final int thisNextRatio = cntThis / cntNext;
        final int roomMapRatio = equatorCellCnt / cntThis;
        for (int iy = 0; iy < cntThis; iy++) {
            int r = 0;
            if (iy == 0) {
                r = addRoom();
                hemisphere.addGreenwichRoom(r);
            } else if (!isPolarLayer && iy % thisNextRatio == 0) {
                // polar layers have only one room
                r = addRoom();
            }
            Point2D center = new Point2D(ix, iy * roomMapRatio);
            final FloorShape floor = new FloorShape(center, false);
            linkRoomToFloor(r, floor);
            addShape(floor);

        }

    }

    private void generateParallelWalls(SouthNorth sn) {
        EggMazeHemisphere h = getHemisphere(sn);
        for (int i = 1; i < h.getCircleCnt(); i++) {
            LOG.log(Level.INFO, "generateParallesWalls(" + sn + ") i=" + i);

            // the next layer may have less rooms than this one
            final int roomCntThis = h.getRoomCntBeforeCircle(i);
            final int roomCntNext = h.getRoomCntAfterCircle(i);
            final int gRoomThis = h.getGreenwichRoom(i - 1);
            final int gRoomNext = h.getGreenwichRoom(i);

            int x = sn == SouthNorth.south ? -i : i;

            final int roomCntRatio = roomCntThis / roomCntNext;
            for (int roomNext = 0; roomNext < roomCntNext; roomNext++) {
                for (int j = 0; j < roomCntRatio; j++) {
                    int roomThis = roomNext * roomCntRatio + j;
                    int id = addWall(gRoomThis + roomThis, gRoomNext + roomNext);
                    addWallShape(roomCntThis, roomThis, roomThis + 1, x, x, id);
                }
            }
        }

    }

    private void generateParallelWallsOnEquator() {
        final int gRoomNorth = north.getGreenwichRoom(0);
        final int gRoomSouth = south.getGreenwichRoom(0);
        LOG.log(Level.INFO, "generateParallelWallsOnEquator");
        for (int i = 0; i < equatorCellCnt; i++) {
            int id = addWall(gRoomNorth + i, gRoomSouth + i);
            addWallShape(equatorCellCnt, i, i + 1, 0, 0, id);
        }

    }

    public int getEquatorCellCnt() {
        return equatorCellCnt;
    }

    private void addWallShape(int roomCntThisLayer, int yr1, int yr2, int x1, int x2, int id) {
        final int roomMapRatio = equatorCellCnt / roomCntThisLayer;
        final int y1 = (yr1 * roomMapRatio) % equatorCellCnt;
        final int y2 = (yr2 * roomMapRatio) % equatorCellCnt;
        WallShape ws = new WallShape(ShapeType.innerWall, y1, x1, y2, x2);
        addShape(ws);
        linkShapeToId(ws, id);
    }

    private void generateMeridianWalls(SouthNorth sn) {
        EggMazeHemisphere h = getHemisphere(sn);
        for (int i = 0; i < h.getCircleCnt(); i++) {
            LOG.log(Level.INFO, "generateMeridianWalls(" + sn + ") i=" + i);

            final int cnt = h.getRoomCntAfterCircle(i);
            if (cnt <= 1) {
                continue;
            }

            final int gr = h.getGreenwichRoom(i);
            for (int j = 0; j < cnt; j++) {
                int id = addWall(gr + j, gr + (j + 1) % cnt);
                // strange wall naming convention - wall 0 is between room 0 and
                // 1
                int jj = (j + 1) % cnt;
                if (sn == SouthNorth.north) {
                    addWallShape(cnt, jj, jj, i, i + 1, id);
                } else {
                    addWallShape(cnt, jj, jj, -i - 1, -i, id);
                }
            }

        }

    }

    private boolean isPowerOfTwo(int n) {
        for (;;) {
            if (n == 1 || n == 0) {
                return true;
            }
            if (n % 2 == 1) {
                return false;
            }
            n /= 2;
        }
    }

    public double getBaseRoomSizeInmm() {
        return this.baseRoomSizeInmm;
    }

    private static final Logger LOG = Logger.getLogger("maze.jmaze");

    private EggGeometry egg;
    private int equatorCellCnt;

    private EggMazeHemisphere north;
    private EggMazeHemisphere south;

    private double baseRoomSizeInmm;

    private static final int MINIMAL_ROOM_COUNT_ON_EGG_MAZE_EQUATOR = 4;

}
