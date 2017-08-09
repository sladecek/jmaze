package com.github.sladecek.maze.jmaze.triangular;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.maze.BaseMaze;

import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.*;

import java.util.logging.Logger;

public class TriangularMaze extends BaseMaze {

/*
    public TriangularMaze(int size) {
    }
*/

    @Override
    public MazeProperties getDefaultProperties() {
        MazeProperties defaultProperties = super.getDefaultProperties();
        defaultProperties.put("name", "triangular");
        defaultProperties.put("size", 20);
        addDefault2DProperties(defaultProperties);
        addDefault3DProperties(defaultProperties);
        return defaultProperties;
    }

    @Override
    public void buildMazeGraphAndShapes() {
        final boolean isPolar = false;
        size = properties.getInt("size", 2,10000);


        final int height = size;
        final int width = 2 * size;

        allShapes = new Shapes(isPolar, rsy * height, rsx * width);

        int prevFirst = -1;
        int lastRoom = -1;
        int myFirst = -1;

        // for all rows
        for (int y = 0; y <= size; y++) {
            final int roomsInRow = 2 * y + 1;
            if (y < size) {
                int prevRoom = -1;

                // make a row of rooms  and vertical walls among them
                for (int j = 0; j < roomsInRow + 1; j++) {
                    int x1 = size + j - y - 1;
                    int x2 = size + j - y;
                    int y1 = y;
                    int y2 = y;
                    boolean newRoomIsRight = true;
                    if (j % 2 == 0) {
                        y1++;
                    } else {
                        y2++;
                        newRoomIsRight = false;
                    }

                    int r = -1;
                    if (j < roomsInRow) {
                        r = getGraph().addRoom();
                        if (j == 0) {
                            prevFirst = myFirst;
                            myFirst = r;
                        }
                        lastRoom = r;
                        LOGGER.info("addRoom " + r + " y=" + y + " j=" + j + " prevRoom=" + prevRoom +
                                " myFirst=" + myFirst + " prevFirst=" + prevFirst + " lastRoom=" + lastRoom);


                        final Point2DInt position = new Point2DInt(rsx * x2, rsy * y + rsy / 2);
                        final MarkShape mark = new MarkShape(r, position);
                        allShapes.add(mark);
                        final FloorShape floor = new FloorShape(r, position);
                        allShapes.add(floor);
                    }

                    if (newRoomIsRight) {
                        addWallAndWallShape(r, prevRoom, x1, x2, y1, y2);
                    } else {
                        addWallAndWallShape(prevRoom, r, x1, x2, y1, y2);
                    }
                    prevRoom = r;
                }
            } else {
                prevFirst = myFirst;
            }
            // connect rooms to upper row by horizontal walls
            if (prevFirst >= 0) {
                int i = 0;
                for (int j = 1; j < roomsInRow; j += 2) {
                    int x = size + j - y - 1;
                    int r = -1;
                    if (y < size) {
                        r = myFirst + j;
                    }
                    addWallAndWallShape(r, prevFirst + i, x, x + 2, y, y);
                    i += 2;
                }
            }

        }

        getGraph().setStartRoom(0);
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

    private void addWallAndWallShape(int roomLeft, int roomRight, int x1, int x2,
                                     int y1, int y2) {

        final Point2DInt p1 = new Point2DInt(rsx * x1, rsy * y1);
        final Point2DInt p2 = new Point2DInt(rsx * x2, rsy * y2);
        LOGGER.info("addWallAndWallShape roomRight=" + roomRight + " roomLeft=" +
                roomLeft + " y1=" + y1 + " y2=" + y2 + " x1=" + x1 + " x2=" + x2);

        if (roomRight >= 0 && roomLeft >= 0) {
            int id = getGraph().addWall(roomRight, roomLeft);
            allShapes.add(WallShape.newInnerWall(id, p1, p2, roomRight, roomLeft));
        } else {
            allShapes.add(WallShape.newOuterWall(p1, p2, roomRight, roomLeft));
        }
    }

    public int getSize() {
        return size;
    }

    private static final Logger LOGGER = Logger.getLogger("maze");
    private final int rsx = 12;
    private final int rsy = 20;
    private int size;

}
