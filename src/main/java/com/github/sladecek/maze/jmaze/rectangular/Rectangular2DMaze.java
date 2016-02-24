package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
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
        setContext(new ShapeContext(isPolar, height, width, 10, 50, 50));

        // outer walls
        final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
        addShape(new WallShape(ow, 0, 0, 0, width));
        addShape(new WallShape(ow, 0, 0, height, 0));
        addShape(new WallShape(ow, 0, width, height, width));
        addShape(new WallShape(ow, height, 0, height, width));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int id = addRoom();
                assert id == y * width + x : "Inconsistent room numbering";
                                
                final FloorShape floor = new FloorShape(new Point2D(x,y), false);
                linkRoomToFloor(id, floor);
                addShape(floor);
            }
        }

        final IMazeShape.ShapeType iw = IMazeShape.ShapeType.innerWall;

        // inner walls - east/west
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width - 1; x++) {
                int roomEast = y * width + x;
                int roomWest = roomEast + 1;
                int id = addWall(roomEast, roomWest);
                
                WallShape w = new WallShape(iw, y, x + 1, y + 1, x + 1);
                addShape(w);
                linkShapeToId(w, id);
            }
        }

        // inner walls - south/north
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                int roomNorth = y * width + x;
                int roomSouth = roomNorth + width;
                int id = addWall(roomNorth, roomSouth);
                WallShape w = new WallShape(iw, y + 1, x, y + 1, x + 1);
                addShape(w);
                linkShapeToId(w, id);
            }
        }

        setStartRoom(0);
        setTargetRoom(width*height-1);
        
    }

    private int height;
    private int width;

}
