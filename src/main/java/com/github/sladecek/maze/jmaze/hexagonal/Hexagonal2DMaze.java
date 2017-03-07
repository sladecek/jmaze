package com.github.sladecek.maze.jmaze.hexagonal;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import java.util.Vector;
import java.util.logging.Logger;

public class Hexagonal2DMaze extends Maze implements IMazeStructure {

    private int size;

    public Hexagonal2DMaze(int size) {
        super();
        this.size = size;
        buildMaze();
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

    private void buildMaze() {

        makeContext();

        final int roomsPerRow = 2 * size - 1;

        Vector<Integer> mapXY2room = new Vector<Integer>();

        // make rooms
        for (int x = 0; x < roomsPerRow; x++) {
            boolean isOdd = x % 2 == 1;
            for (int y = 0; y < size; y++) {

                // make room (topology)
                int r = addRoom();
                mapXY2room.add(r);

                Point2D center = computeRoomCenter(x, isOdd, y);

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
        setStartRoom(0);
        final int lastRoom = size * roomsPerRow - 1;
        setTargetRoom(lastRoom);
    }

    private void makeFloor(int r, Point2D center) {
        final MarkShape floor = new MarkShape(r, center);

        addShape(floor);
    }

    private void makeContext() {
        final int height = hH * (2 * size + 1);
        final int width = hP * (3 * size - 1);
        final boolean isPolar = false;
        setContext(new ShapeContext(isPolar, height, width));
    }

    private Point2D computeRoomCenter(int x, boolean isOdd, int y) {
        // compute centre of the room
        int xc = (hP * (2 + x * 3)) / 2;
        int yc = hH * (2 * y + 1);
        if (isOdd) {
            yc += hH;
        }
        Point2D center = new Point2D(xc, yc);
        return center;
    }

    private void addInnerWall(int r2, int r, int x1, int y1, int x2, int y2, int ox, int oy) {

        int id = addWall(r, r2);

        LOGGER.info(
                "addWallAndShape room1=" + r + " room2=" + r2 + " y1=" + y1 + " y2=" + y2 + " x1=" + x1 + " x2=" + x2);
        WallShape ws = new WallShape(ShapeType.innerWall, y1, x1, y2, x2);
        addShape(ws);
        linkShapeToId(ws, id);
    }

    private void addOuterWall(int x1, int y1, int x2, int y2) {
        addShape(new WallShape(ShapeType.outerWall, y1, x1, y2, x2));
    }

    private boolean areRoomCoordinatesValid(final int roomsPerRow, int ox, int oy) {
        return ox >= 0 && ox < roomsPerRow && oy >= 0 && oy < size;
    }

    public int getSize() {
        return size;
    }

    private static final Logger LOGGER = Logger.getLogger("maze.jmaze");
}
