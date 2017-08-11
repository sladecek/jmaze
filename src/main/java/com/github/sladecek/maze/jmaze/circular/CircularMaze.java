package com.github.sladecek.maze.jmaze.circular;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.maze.BaseMaze;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.Shapes;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates circular maze.
 */
public class CircularMaze extends BaseMaze {

    /**
     * Creates new instance of circular maze..
     */
    public CircularMaze() {
    }

    @Override
    public MazeProperties getDefaultProperties() {
        MazeProperties defaultProperties = super.getDefaultProperties();
        defaultProperties.put("name", "circular");
        defaultProperties.put("layerCount", 8);
        addComputedProperties(defaultProperties);
        addDefault2DProperties(defaultProperties);
        return defaultProperties;
    }

    public void buildMazeGraphAndShapes() {
        layerCount = properties.getInt("layerCount", 1, 1000);
        computeRoomCounts();
        firstRoomInLayer = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            firstRoomInLayer.add(-1);
        }

        createModel();

        generateRooms();
        generateConcentricWalls();
        generateRadialWalls();
        generateOuterWalls();
        setStartAndTargetRooms();
    }

    @Override
    public IMaze3DMapper create3DMapper() {
        // Cannot be printed in 3D yet.
        return null;
    }

    @Override
    public boolean canBePrintedIn2D() {
        return true;
    }

    private void computeRoomCounts() {
        roomCounts = new ArrayList<>();
        roomCountRatio = new ArrayList<>();
        if (layerCount > 0) {
            roomCounts.add(1);
            roomCountRatio.add(1);
            if (layerCount > 1) {
                int roomCountInZeroLayer = 4;
                roomCounts.add(roomCountInZeroLayer);
                roomCountRatio.add(roomCountInZeroLayer);

                // all layers except the central layer
                for (int i = 2; i < layerCount; i++) {
                    int cnt = roomCounts.get(i - 1);
                    double nextRoomIfDoubled = Math.PI * computeRadius(i - 1) / cnt;
                    int minimalRoomLength = 15;
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
        int zeroLayerRadius = 20;
        int layerSize = 30;
        return zeroLayerRadius + i * layerSize;
    }

    private void createModel() {
        final int rMax = computeRadius(layerCount);
        final int height = 2 * rMax;
        final int width = 2 * rMax;
        final boolean isPolar = true;
        allShapes = new Shapes(isPolar, height, width);
    }

    private void setStartAndTargetRooms() {
        getGraph().setStartRoom(0);
        getGraph().setTargetRoom(getGraph().getRoomCount() - 1);
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
            int room = getGraph().addRoom();
            if (phi == 0) {
                firstRoomInLayer.set(layer, room);
            }

            int y = 0;
            if (layer > 0) {
                y = (computeRadius(layer) + computeRadius(layer - 1)) / 2;
            }

            Point2DInt center = new Point2DInt(mapPhiD(phi * roomRatio + roomRatio * 0.5), y);
            final MarkShape floor = new MarkShape(room, center);
            getAllShapes().add(floor);
        }
    }

    private Integer roomCntInOuterLayer() {
        return roomCounts.get(roomCounts.size() - 1);
    }

    private int mapPhiD(double phi) {
        return (int) Math.floor((phi * Point2DInt.ANGLE_2PI) / roomCntInOuterLayer());
    }

    private void generateConcentricWalls() {
        // draw concentric wall at radius r
        for (int layer = 0; layer < layerCount - 1; layer++) {
            LOGGER.log(Level.INFO, "generateConcentricWalls r=" + layer);

            // the next layer may have less rooms than this one
            final int roomCntInner = roomCounts.get(layer);
            final int roomCntOuter = roomCounts.get(layer + 1);
            final int gRoomInner = firstRoomInLayer.get(layer);
            final int gRoomOuter = firstRoomInLayer.get(layer + 1);

            final int roomCntRatio = roomCountRatio.get(layer + 1);
            for (int roomInner = 0; roomInner < roomCntInner; roomInner++) {
                for (int j = 0; j < roomCntRatio; j++) {
                    int roomOuter = roomInner * roomCntRatio + j;
                    int id = getGraph().addWall(gRoomInner + roomInner, gRoomOuter + roomOuter);
                    final int r = computeRadius(layer);
                    addWallShape(roomCntOuter, r, r, roomOuter, roomOuter + 1, id);
                }
            }
        }
    }

    private void addWallShape(int roomCntThisLayer, int r1, int r2, int phi1, int phi2, int id) {
        final int outerCnt = roomCntInOuterLayer();
        final int roomMapRatio = outerCnt / roomCntThisLayer;
        final int rPhi1 = (phi1 * roomMapRatio) % outerCnt;
        final int rPhi2 = (phi2 * roomMapRatio) % outerCnt;
        getAllShapes().add(WallShape.newInnerWall(id, new Point2DInt(mapPhiD(rPhi1), r1)
                , new Point2DInt(mapPhiD(rPhi2), r2)));
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
                int id = getGraph().addWall(gr + j, gr + (j + 1) % cnt);
                // strange wall naming convention - wall 0 is between room 0 and
                // 1
                int phi = (j + 1) % cnt;
                addWallShape(cnt, computeRadius(layer - 1), computeRadius(layer), phi, phi, id);
            }
        }
    }

    private void generateOuterWalls() {
        int r = computeRadius(layerCount - 1);
        getAllShapes().add(WallShape.newOuterWall(new Point2DInt(0, r), new Point2DInt(0, r)));
    }

    private static final Logger LOGGER = Logger.getLogger("maze");
    private int layerCount;
    private ArrayList<Integer> roomCounts;
    private ArrayList<Integer> roomCountRatio;
    private ArrayList<Integer> firstRoomInLayer;
}
