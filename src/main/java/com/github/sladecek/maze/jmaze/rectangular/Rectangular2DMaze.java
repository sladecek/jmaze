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


        addShape(WallShape.newOuterWall(new Point2D(0, 0), new Point2D(w, 0)));
        addShape(WallShape.newOuterWall(new Point2D(0, 0), new Point2D(0, h)));
        addShape(WallShape.newOuterWall(new Point2D(0, h), new Point2D(w, h)));
        addShape(WallShape.newOuterWall(new Point2D(w, 0), new Point2D(w, h)));


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int id = addRoom();
                assert id == y * width + x : "Inconsistent room numbering";
                                
                final MarkShape floor = new MarkShape(id, new Point2D(x*rsp+rsp/2,y*rsp+rsp/2));

                addShape(floor);
            }
        }



        // inner walls - east/west
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width - 1; x++) {
                int roomEast = y * width + x;
                int roomWest = roomEast + 1;
                int id = addWall(roomEast, roomWest);

                addShape(WallShape.newInnerWall(id,
                        new Point2D(rsp*(x + 1), rsp*y), new Point2D(rsp*(x + 1), rsp*(y + 1))));

            }
        }

        // inner walls - south/north
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                int roomNorth = y * width + x;
                int roomSouth = roomNorth + width;
                int id = addWall(roomNorth, roomSouth);
                addShape(WallShape.newInnerWall(id,
                        new Point2D(rsp*x, rsp*(y+1)), new Point2D(rsp*(x + 1), rsp*(y + 1))));
            }
        }

        setStartRoom(0);
        setTargetRoom(width*height-1);
        
    }

    private int height;
    private int width;

}
