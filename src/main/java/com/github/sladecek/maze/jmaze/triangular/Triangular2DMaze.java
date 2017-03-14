package com.github.sladecek.maze.jmaze.triangular;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D;

import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import java.util.logging.Logger;

public class Triangular2DMaze extends Maze implements IMazeStructure {


    private int size;


    public Triangular2DMaze(int size) {
        super();
        this.size = size;
        buildMaze();
    }

private final int rsx = 12;
    private final int rsy = 20;


    private void buildMaze() {
        final int height = size;
        final int width = 2 * size;
        final boolean isPolar = false;
        setContext(new ShapeContext(isPolar, rsy*height, rsx*width/*, 12, 20*/));


        // outer walls

        addShape(WallShape.newOuterWall(new Point2D(rsx*size,0), new Point2D(0,rsy*size)));
        addShape(WallShape.newOuterWall(new Point2D(0, rsy*size), new Point2D( 2 * size*rsx,  rsy*size)));
        addShape(WallShape.newOuterWall(new Point2D(2 * size*rsx,rsy*size),  new Point2D(rsx*size,0)));
int prevFirst = -1;
        int lastRoom = -1;
        int myFirst = -1;

        // for all rows
        for (int y = 0; y < size; y++) {
            int prevRoom = -1;
            final int roomsInRow = 2 * y + 1;

            // make a row of rooms  and vertical walls among them
            for (int j = 0; j < roomsInRow; j++) {
                int r = addRoom();
                if (j == 0) {
                    prevFirst = myFirst;
                    myFirst = r;
                }
                lastRoom = r;
                LOGGER.info("addRoom " + r + " y=" + y + " j=" + j + " prevRoom=" + prevRoom + " myFirst=" + myFirst + " prevFirst=" + prevFirst + " lastRoom=" + lastRoom);
                int x1 = size + j - y - 1;
                int x2 = size + j - y;
                int y1 = y;
                int y2 = y;
                if (j % 2 == 0) {
                    y1++;
                } else {
                    y2++;
                }

                final MarkShape floor = new MarkShape(r, new Point2D(rsx*x2, rsy*y+rsy/2));
//                linkRoomToFloor(r, floor);
                addShape(floor);

                if (prevRoom > 0) {
                    addWallAndShape(prevRoom, r, x1, x2, y1, y2);
                }
                prevRoom = r;
            }

            // connect rooms to upper row by horizontal walls
            if (prevFirst >= 0) {
                int i = 0;
                for (int j = 1; j < roomsInRow; j += 2) {
                    int x = size + j - y - 1;
                    addWallAndShape(prevFirst + i, myFirst + j, x, x + 2, y, y);
                    i += 2;
                }
            }

        }

        setStartRoom(0);
        setTargetRoom(lastRoom);
    }

    private void addWallAndShape(int prevRoom, int room, int x1, int x2,
                                 int y1, int y2) {
        int id = addWall(prevRoom, room);
        addShape(WallShape.newInnerWall(id, new Point2D(rsx*x1, rsy*y1), new Point2D(rsx*x2, rsy*y2)));
        LOGGER.info("addWallAndShape room1=" + prevRoom + " room2=" + room + " y1=" + y1 + " y2=" + y2 + " x1=" + x1 + " x2=" + x2);

    }

    public int getSize() {
        return size;
    }

    private static final Logger LOGGER = Logger.getLogger("maze.jmaze");
}
