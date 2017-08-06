package com.github.sladecek.maze.jmaze.spheric;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Irrengarten;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;

import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * Rooms and walls of a maze on an egg-like shape.
 */
public final class EggMaze extends Irrengarten implements IMazeStructure {

    public EggMaze(EggGeometry egg, int equatorCellCnt) {
        this.egg = egg;
        this.equatorCellCnt = equatorCellCnt;
        this.baseRoomSizeInmm = egg.computeBaseRoomSizeInmm(equatorCellCnt);
        north = new EggMazeHemisphere(egg.getEllipseMajorInmm());
        south = new EggMazeHemisphere(-egg.getEllipseMajorInmm());

        if (this.equatorCellCnt < MINIMAL_ROOM_COUNT_ON_EGG_MAZE_EQUATOR) {
            throw new IllegalArgumentException(
                    "Irrengarten must have at least " + MINIMAL_ROOM_COUNT_ON_EGG_MAZE_EQUATOR + "cells.");
        }

        if (!isPowerOfTwo(this.equatorCellCnt)) {
            throw new IllegalArgumentException("Cell number must be power of 2.");
        }

        // Build topological structure of the maze and maze shapes at the same
        // time. All walls will be generated.
        buildMaze();

    }

    /*
    public Hexagonal2DMaze() {

    }

    public RectangularMaze(int width, int height) {
        this();
        MazeProperties p = getDefaultProperties();
        p.put("width", width);
        p.put("height", height);
        setProperties(p);
    }

    public RectangularMaze() {
        super();
        defaultProperties.put("name", "rect");
        defaultProperties.put("width", 20);
        defaultProperties.put("height", 20);
        properties = defaultProperties.clone();

    }

    @Override
    public void buildMaze() {
        final boolean isPolar = false;
        final int height = properties.getInt("height");
        final int width = properties.getInt("width");*/



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
        setContext(new ShapeContext(isPolar, height, width));

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
            Point2DInt center = new Point2DInt(ix*res+res/2, (iy * roomMapRatio)*res+res/2);
            final MarkShape mark = new MarkShape(r, center);
            addShape(mark);
            final FloorShape floor = new FloorShape(r, center);
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
                    final int r1 = gRoomThis + roomThis;
                    final int r2 = gRoomNext + roomNext;
                    int id = addWall(r1, r2);
                    if (sn == SouthNorth.south) {
                        addWallShape(roomCntThis, roomThis, roomThis + 1, x, x, id, r1, r2);
                    } else {
                        addWallShape(roomCntThis, roomThis, roomThis + 1, x, x, id, r2, r1);
                    }
                }
            }
        }

    }

    private void generateParallelWallsOnEquator() {
        final int gRoomNorth = north.getGreenwichRoom(0);
        final int gRoomSouth = south.getGreenwichRoom(0);
        LOG.log(Level.INFO, "generateParallelWallsOnEquator");
        for (int i = 0; i < equatorCellCnt; i++) {
            final int rightRoom = gRoomNorth + i;
            final int leftRoom = gRoomSouth + i;
            int id = addWall(leftRoom, rightRoom);
            addWallShape(equatorCellCnt, i, i + 1, 0, 0, id, rightRoom, leftRoom);
        }
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
                final int rightRoom = gr + j;
                final int leftRooom = gr + (j + 1) % cnt;
                int id = addWall(rightRoom, leftRooom);
                // strange wall naming convention - wall 0 is between room 0 and
                // 1
                int jj = (j + 1) % cnt;
                if (sn == SouthNorth.north) {
                    addWallShape(cnt, jj, jj, i, i + 1, id, rightRoom, leftRooom);
                } else {
                    addWallShape(cnt, jj, jj, -i - 1, -i, id, rightRoom, leftRooom);
                }
            }
        }
    }


    public int getEquatorCellCnt() {
        return equatorCellCnt;
    }

    private void addWallShape(int roomCntThisLayer, int yr1, int yr2, int x1, int x2, int id, int rightRoom, int leftRoom) {
        final int roomMapRatio = equatorCellCnt / roomCntThisLayer;
        final int y1 = (yr1 * roomMapRatio) % equatorCellCnt;
        final int y2 = (yr2 * roomMapRatio) % equatorCellCnt;
        final Point2DInt p1 = new Point2DInt(x1*res, y1*res);
        final Point2DInt p2 = new Point2DInt(x2*res, y2*res);
        LOG.log(Level.INFO, "addWallShape p1="+p1+" p2="+p2+" right="+rightRoom+" left="+leftRoom);
        addShape(WallShape.newInnerWall(id, p1, p2, rightRoom, leftRoom));
    }

    private boolean isPowerOfTwo(int n) {
        for (; ; ) {
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

    private static final Logger LOG = Logger.getLogger("maze");
    private static final int MINIMAL_ROOM_COUNT_ON_EGG_MAZE_EQUATOR = 4;
    private EggGeometry egg;
    private int equatorCellCnt;
    private EggMazeHemisphere north;
    private EggMazeHemisphere south;
    private double baseRoomSizeInmm;


    public static int res = 10;
}
