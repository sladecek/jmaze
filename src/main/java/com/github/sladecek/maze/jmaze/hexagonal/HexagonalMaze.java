package com.github.sladecek.maze.jmaze.hexagonal;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.maze.BaseMaze;

import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;

import com.github.sladecek.maze.jmaze.shapes.Shapes;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import java.util.ArrayList;
import java.util.logging.Logger;

public class HexagonalMaze extends BaseMaze {

    private int size;
/*
    public HexagonalMaze(int size) {
        MazeProperties p = getDefaultProperties();
        p.put("size", size);
        setProperties(p);
    }
*/

public HexagonalMaze() {

}

    @Override
    public MazeProperties getDefaultProperties() {
        MazeProperties defaultProperties = super.getDefaultProperties();
        defaultProperties.put("name", "hexa");
        defaultProperties.put("size", 6);
        addDefault2DProperties(defaultProperties);
        return defaultProperties;
    }


    // Radius of the hexagon.
    static final int hP = 20;

    /// Half-height of the hexagon
    static final int hH = (int) Math.floor(hP * Math.sqrt(3f) / 2f);

    // parameters of six walls of a hexagon
    // walls numbered counterclockwise, starting at upper room
    static final int[] walllXOffs = {hP / 2, -hP / 2, -hP, -hP / 2, hP / 2, hP};
    static final int[] wallYOffs = {-hH, -hH, 0, hH, hH, 0};
    static final int[] neigbourRoomX = {0, -1, -1, 0, 1, 1};
    static final int[] neigbourRoomYOdd = {-1, 0, 1, 1, 1, 0};
    static final int[] neigbourRoomYEven = {-1, -1, 0, 1, 0, -1};

    @Override
    public void buildMazeGraphAndShapes() {
        final boolean isPolar = false;
        size = properties.getInt("size", 2, 10000);

        makeContext();

        final int roomsPerRow = 2 * size - 1;

        ArrayList<Integer> mapXY2room = new ArrayList<>();

        // make rooms
        for (int x = 0; x < roomsPerRow; x++) {
            boolean isOdd = x % 2 == 1;
            for (int y = 0; y < size; y++) {

                // make room (topology)
                int r = getGraph().addRoom();
                mapXY2room.add(r);

                Point2DInt center = computeRoomCenter(x, isOdd, y);

                LOGGER.info("addRoom " + r + " y=" + y + " x=" + x + " center=" + center);

                makeFloor(r, center);

                // make walls
                for (int w = 0; w < 6; w++) {
                    int w2 = (w + 1) % 6;

                    // wall endpoints
                    int x1 = center.getX() + walllXOffs[w];
                    int y1 = center.getY() + wallYOffs[w];
                    int x2 = center.getX() + walllXOffs[w2];
                    int y2 = center.getY() + wallYOffs[w2];

                    // the other room
                    int ox = x + neigbourRoomX[w];
                    int oy = y;
                    if (isOdd) {
                        oy += neigbourRoomYOdd[w];
                    } else {
                        oy += neigbourRoomYEven[w];
                    }

                    // if the other room does not exist then this is a border
                    // wall
                    if (!areRoomCoordinatesValid(roomsPerRow, ox, oy)) {
                        addOuterWall(x1, y1, x2, y2);
                    } else if (w < 3) {
                        // Link only three rooms out of six. The other three
                        // walls will be linked in the from the other room
                        // (which does not exist yet).
                        int r2 = mapXY2room.get(ox * size + oy);
                        addInnerWall(r2, r, x1, y1, x2, y2, ox, oy);
                    }

                }

            }
        }
        getGraph().setStartRoom(0);
        final int lastRoom = size * roomsPerRow - 1;
        getGraph().setTargetRoom(lastRoom);
    }

    @Override
    public IMaze3DMapper create3DMapper() {
        return null;
    }

    @Override
    public boolean canBePrintedIn2D() {
        return true;
    }

    private void makeFloor(int r, Point2DInt center) {
        final MarkShape floor = new MarkShape(r, center);

        getAllShapes().add(floor);
    }

    private void makeContext() {
        final int height = hH * (2 * size + 1);
        final int width = hP * (3 * size - 1);
        final boolean isPolar = false;
        allShapes = new Shapes(isPolar, height, width);
    }

    private Point2DInt computeRoomCenter(int x, boolean isOdd, int y) {
        // compute centre of the room
        int xc = (hP * (2 + x * 3)) / 2;
        int yc = hH * (2 * y + 1);
        if (isOdd) {
            yc += hH;
        }
        Point2DInt center = new Point2DInt(xc, yc);
        return center;
    }

    private void addInnerWall(int r2, int r, int x1, int y1, int x2, int y2, int ox, int oy) {

        int id = getGraph().addWall(r, r2);

        LOGGER.info(
                "addWallAndShape room1=" + r + " room2=" + r2 + " y1=" + y1 + " y2=" + y2 + " x1=" + x1 + " x2=" + x2);
        getAllShapes().add(WallShape.newInnerWall(id, new Point2DInt(x1, y1), new Point2DInt(x2, y2)));
    }

    private void addOuterWall(int x1, int y1, int x2, int y2) {
        getAllShapes().add(WallShape.newOuterWall(new Point2DInt(x1, y1), new Point2DInt(x2, y2)));
    }

    private boolean areRoomCoordinatesValid(final int roomsPerRow, int ox, int oy) {
        return ox >= 0 && ox < roomsPerRow && oy >= 0 && oy < size;
    }

    public int getSize() {
        return size;
    }

    private static final Logger LOGGER = Logger.getLogger("maze");
}
