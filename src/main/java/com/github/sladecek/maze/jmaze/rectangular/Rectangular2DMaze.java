package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * 2D rectangular maze. Both rooms and walls are numbered first by rows, then by
 * columns. East/west walls are numbered before south/north ones.
 */
public final class Rectangular2DMaze extends Maze implements
        IMazeStructure {

    public Rectangular2DMaze(int height, int width) {
        this.width = width;
        this.height = height;
        buildMaze();
    }

    /**
     * Get all geometric shapes needed to print this maze.
     */
    public void buildMaze() {
        final boolean isPolar = false;

        // width and height in pixels
        final int h = rsp * height;
        final int w = rsp * width;
        setContext(new ShapeContext(isPolar, h, w));


        // rooms
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int roomId = addRoom();
                assert roomId == y * width + x : "Inconsistent room numbering";
                final int x1 = x * rsp + rsp / 2;
                final int y1 = y * rsp + rsp / 2;
                final Point2D position = new Point2D(x1, y1);
                final MarkShape mark = new MarkShape(roomId, position);
                addShape(mark);
                final FloorShape floor = new FloorShape(roomId, position);
                addShape(floor);
            }
        }

        // walls - east/west
        for (int y = 0; y < height; y++) {
            for (int x = -1; x < width; x++) {
                int roomIdWest = y * width + x;
                int roomIdEast = roomIdWest + 1;
                final Point2D p1 = new Point2D(rsp * (x + 1), rsp * y);
                final Point2D p2 = new Point2D(rsp * (x + 1), rsp * (y + 1));

                if (x >= width - 1) {
                    // east outer wall
                    addShape(WallShape.newOuterWall(p1, p2, roomIdWest, -1));

                } else if (x < 0) {
                    // west outer wall
                    addShape(WallShape.newOuterWall(p1, p2, -1, roomIdEast));
                } else {

                    // inner wall
                    int id = addWall(roomIdWest, roomIdEast);
                    addShape(WallShape.newInnerWall(id, p1, p2, roomIdWest, roomIdEast));

                }
            }
        }

        // walls - south/north
        for (int y = -1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int roomIdNorth = y * width + x;
                int roomIdSouth = roomIdNorth + width;
                final Point2D p1 = new Point2D(rsp * x, rsp * (y + 1));
                final Point2D p2 = new Point2D(rsp * (x + 1), rsp * (y + 1));

                if (y < 0) {
                    addShape(WallShape.newOuterWall(p1, p2, roomIdSouth, -1));
                } else if (y >= height - 1) {
                    // south outer wall
                    addShape(WallShape.newOuterWall(p1, p2, -1, roomIdNorth));
                } else {
                    // inner wall
                    int id = addWall(roomIdNorth, roomIdSouth);
                    addShape(WallShape.newInnerWall(id, p1, p2, roomIdSouth, roomIdNorth));
                }
            }
        }
        setStartRoom(0);
        setTargetRoom(width * height - 1);
    }

    /**
     * Room size in pixels.
     */
    private final int rsp = 20;

    private int height;
    private int width;
}