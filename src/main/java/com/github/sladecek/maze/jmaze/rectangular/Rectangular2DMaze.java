package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
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
                int id = addRoom();
                assert id == y * width + x : "Inconsistent room numbering";
                final MarkShape floor = new MarkShape(id, new Point2D(x * rsp + rsp / 2, y * rsp + rsp / 2));
                addShape(floor);
            }
        }

        // walls - east/west
        for (int y = 0; y < height; y++) {
            for (int x = -1; x < width; x++) {
                int roomWest = y * width + x;
                int roomEast = roomWest + 1;
                final Point2D p1 = new Point2D(rsp * (x + 1), rsp * y);
                final Point2D p2 = new Point2D(rsp * (x + 1), rsp * (y + 1));

                if (x >= width - 1) {
                    // east outer wall
                    addShape(WallShape.newOuterWall(p1, p2, roomWest, -1));

                } else if (x < 0) {
                    // west outer wall
                    addShape(WallShape.newOuterWall(p1, p2, -1, roomEast));
                } else {

                    // inner wall
                    int id = addWall(roomWest, roomEast);
                    addShape(WallShape.newInnerWall(id, p1, p2, roomWest, roomEast));

                }
            }
        }

        // walls - south/north
        for (int y = -1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int roomNorth = y * width + x;
                int roomSouth = roomNorth + width;
                final Point2D p1 = new Point2D(rsp * x, rsp * (y + 1));
                final Point2D p2 = new Point2D(rsp * (x + 1), rsp * (y + 1));

                if (y < 0) {
                    addShape(WallShape.newOuterWall(p1, p2, roomSouth, -1));
                } else if (y >= height - 1) {
                    // south outer wall
                    addShape(WallShape.newOuterWall(p1, p2, -1, roomNorth));
                } else {
                    // inner wall
                    int id = addWall(roomNorth, roomSouth);
                    addShape(WallShape.newInnerWall(id, p1, p2, roomSouth, roomNorth));
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