package com.github.sladecek.maze.jmaze.circular;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates circular maze.
 */
public class Circular2DMaze extends Maze implements IMazeStructure {


    public Circular2DMaze(int layerCount, int layerSize) {
        super();
        this.layerCount = layerCount;
        this.layerSize = layerSize;
        this.zeroLayerRadius = layerSize * 2 / 3;
        this.roomCountInZeroLayer = 4;
        this.minimalRoomLength = layerSize / 2;
        buildMaze();
    }

    private void buildMaze() {
        computeRoomCounts();
        firstRoomInLayer = new Vector<Integer>();
        firstRoomInLayer.setSize(layerCount);

        createContext();

        generateRooms();
        generateConcentricWalls();
        generateRadialWalls();
        setStartAndTargetRooms();
        generateOuterWalls();
    }

    private void computeRoomCounts() {
        roomCounts = new Vector<Integer>();
        roomCountRatio = new Vector<Integer>();
        if (layerCount > 0) {
            roomCounts.add(1);
            roomCountRatio.add(1);
            if (layerCount > 1) {
                roomCounts.add(roomCountInZeroLayer);
                roomCountRatio.add(roomCountInZeroLayer);

                // all layers except the central layer
                for (int i = 2; i < layerCount; i++) {
                    int cnt = roomCounts.get(i-1);
                    double nextRoomIfDoubled =  Math.PI * computeRadius(i-1) / cnt;
                    if (nextRoomIfDoubled < minimalRoomLength) {
                        roomCounts.add(cnt);
                        roomCountRatio.add(1);
                    } else {
                        roomCounts.add(2 * cnt);
                        roomCountRatio.add(2);
                    }
                }
            }
        }
        assert roomCounts.size() == layerCount;
        assert roomCountRatio.size() == layerCount;

    }

    private int computeRadius(int i) {
        return zeroLayerRadius + i * layerSize;
    }

    private void createContext() {
        final int rmax = computeRadius(layerCount);
        final int height = 2 * rmax;
        final int width = 2 * rmax;
        final boolean isPolar = true;
        setContext(new ShapeContext(isPolar, height, width, 2, 2));
    }

    private void setStartAndTargetRooms() {
        setStartRoom(0);
        setTargetRoom(getRoomCount()-1);
    }

    private void generateRooms() {
        for (int r = 0; r < layerCount; r++) {
            generateRowOfRooms(r);
        }
    }

    private void generateRowOfRooms(int layer) {
        final int cntMax = roomCntInOuterLayer();
        final int cntThis = roomCounts.get(layer);
        final int roomRatio = cntMax / cntThis;
        for (int phi = 0; phi < cntThis; phi++) {
            int room = addRoom();
            if (phi == 0) {
                firstRoomInLayer.set(layer, room);
            }

            int y = 0;
            if (layer > 0) {
                y = (computeRadius(layer) + computeRadius(layer - 1)) / 2;
            }

            Point2D center = new Point2D(mapPhiD(phi * roomRatio+roomRatio*0.5), y);

            final FloorShape floor = new FloorShape(center, false, 0, 0);
            linkRoomToFloor(room, floor);
                addShape(floor);
        }
    }

    private Integer roomCntInOuterLayer() {
        return roomCounts.get(roomCounts.size()-1);
    }


    private int mapPhiD(double phi) {
        return (int) Math.floor((phi * Point2D.ANGLE_2PI) / roomCntInOuterLayer());
    }

    private void generateConcentricWalls() {

        // draw concentric wall at radius r
        for (int layer = 0; layer < layerCount-1; layer++) {
            LOGGER.log(Level.INFO, "generateConcentricWalls r=" + layer);

            // the next layer may have less rooms than this one
            final int roomCntInner = roomCounts.get(layer);
            final int roomCntOuter = roomCounts.get(layer+1);
            final int gRoomInner = firstRoomInLayer.get(layer);
            final int gRoomOuter = firstRoomInLayer.get(layer+1);

            final int roomCntRatio = roomCountRatio.get(layer+1);
            for (int roomInner = 0; roomInner < roomCntInner; roomInner++) {
                for (int j = 0; j < roomCntRatio; j++) {
                    int roomOuter = roomInner * roomCntRatio + j;
                    int id = addWall(gRoomInner + roomInner, gRoomOuter + roomOuter);
                    final int r = computeRadius(layer);
                    addWallShape(roomCntOuter, r, r, roomOuter, roomOuter + 1, id);
                }
            }
        }
    }

    private void addWallShape(int roomCntThisLayer, int r1, int r2, int phi1, int phi2, int id) {
        final int outerCnt = roomCntInOuterLayer();
        final int roomMapRatio = outerCnt / roomCntThisLayer;
        final int rphi1 = (phi1 * roomMapRatio) % outerCnt;
        final int rphi2 = (phi2 * roomMapRatio) % outerCnt;
        WallShape ws = new WallShape(ShapeType.innerWall, r1 , mapPhiD(rphi1), r2 , mapPhiD(rphi2));
        addShape(ws);
        linkShapeToId(ws, id);
    }


    private void generateRadialWalls() {
        for (int layer = 1; layer < layerCount; layer++) {
            LOGGER.log(Level.INFO, "generateRadialWalls i=" + layer);

            final int cnt = roomCounts.get(layer);
            if (cnt <= 1) {
                continue;
            }

            final int gr = firstRoomInLayer.get(layer);
            for (int j = 0; j < cnt; j++) {
                int id = addWall(gr + j, gr + (j + 1) % cnt);
                // strange wall naming convention - wall 0 is between room 0 and
                // 1
                int phi = (j + 1) % cnt;
                addWallShape(cnt, computeRadius(layer-1), computeRadius(layer), phi, phi, id);
            }
        }
    }

    private void generateOuterWalls() {
        final ShapeType ow = ShapeType.outerWall;
        int r = computeRadius(layerCount-1);
        addShape(new WallShape(ow, r, 0, r, 0));
    }


    private int layerCount;
    private int zeroLayerRadius;
    private int roomCountInZeroLayer;
    private int minimalRoomLength;
    private int layerSize;
    private Vector<Integer> roomCounts;
    private Vector<Integer> roomCountRatio;
    private Vector<Integer> firstRoomInLayer;

    private static final Logger LOGGER = Logger.getLogger("maze.jmaze");
}
