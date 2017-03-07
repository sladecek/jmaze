package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * 2D rectangular maze. Both rooms and walls are numbered first by rows, then by
 * columns. East/west walls are numbered before south/north ones.
 */
public final class Rectangular2DMaze extends Maze implements
        IMazeStructure {

    /**
     * Room size in pixels.
     */
    private final int rsp = 20;

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

        // outer walls
        final IMazeShape2D.ShapeType ow = IMazeShape2D.ShapeType.outerWall;
        addShape(new WallShape(ow, 0, 0, 0, w));
        addShape(new WallShape(ow, 0, 0, h, 0));
        addShape(new WallShape(ow, 0, w, h, w));
        addShape(new WallShape(ow, h, 0, h, w));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int id = addRoom();
                assert id == y * width + x : "Inconsistent room numbering";
                                
                final MarkShape floor = new MarkShape(id, new Point2D(x*rsp+rsp/2,y*rsp+rsp/2));

                addShape(floor);
            }
        }

        final IMazeShape2D.ShapeType iw = IMazeShape2D.ShapeType.innerWall;

        // inner walls - east/west
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width - 1; x++) {
                int roomEast = y * width + x;
                int roomWest = roomEast + 1;
                int id = addWall(roomEast, roomWest);
                
                WallShape wall = new WallShape(iw, rsp*y, rsp*(x + 1), rsp*(y + 1), rsp*(x + 1));
                addShape(wall);
                linkShapeToId(wall, id);
            }
        }

        // inner walls - south/north
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                int roomNorth = y * width + x;
                int roomSouth = roomNorth + width;
                int id = addWall(roomNorth, roomSouth);
                WallShape wall = new WallShape(iw, rsp*(y + 1), rsp*x, rsp*(y + 1), rsp*(x + 1));
                addShape(wall);
                linkShapeToId(wall, id);
            }
        }

        setStartRoom(0);
        setTargetRoom(width*height-1);
        
    }

    private int height;
    private int width;

}
