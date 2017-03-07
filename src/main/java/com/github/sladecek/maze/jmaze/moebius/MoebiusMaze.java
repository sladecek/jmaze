package com.github.sladecek.maze.jmaze.moebius;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.print3dn.Room3DShape;
import com.github.sladecek.maze.jmaze.print3dn.WallDirection;
import com.github.sladecek.maze.jmaze.shapes.*;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D;

/**
 * 2D rectangular maze on Moebius strip. Rooms and walls (including holes) are
 * numbered first by rows (x, width), then by columns (y, height). East/west walls are numbered before
 * south/north ones. Holes are numbered after south/north walls.
 */
public final class MoebiusMaze extends Maze implements IMazeStructure {

    public MoebiusMaze(final int height, final int width) {
        this.width = width;
        this.height = height;

        if (width % 2 != 0) {
            throw new InvalidParameterException(
                    "Moebius maze must have even width");
        }
        if (height % 2 != 0) {
            throw new InvalidParameterException(
                    "Moebius maze must have even height");
        }
        eastWestWallCount = width * height;
        southNorthWallCount = width * (height - 1);

        buildMaze();
    }

    public void buildMaze() {

        ShapeContainer result = new ShapeContainer(context);

        setStartRoom(0);
        setTargetRoom(width / 4 + (height - 1) * width);

        int wh= width*height;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int wall = mapXYToRoomId(y, x);
                int i = addRoom();
                assert i == wall : "Inconsistent room numbering";


                Room3DShape r3 = new Room3DShape(i, new Point2D(x,y));
                addShape(r3);

                r3.setWallId(WallDirection.WEST, i);
                r3.setWallId(WallDirection.EAST, width*y+(x+1%width));
                if (y == 0) {
                    r3.setWallType(WallDirection.NORTH, WallType.outerWall);
                } else {
                    r3.setWallId(WallDirection.NORTH, i+wh-width);
                }
                if (y == height-1) {
                    r3.setWallType(WallDirection.SOUTH, WallType.outerWall);
                } else {
                    r3.setWallId(WallDirection.SOUTH, i+wh);
                }
                r3.setWallId(WallDirection.FLOOR, getTheOtherSideOfHole(i));
            }
        }


        // inner walls - east/west
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                int roomEast = mapXYToRoomId(y, x);
                int roomWest = mapXYToRoomId(y, (x + 1) % width);
                int id = addWall(roomEast, roomWest);
                assert id == mapXYToRoomId(y, x) : "Inconsistent wall numbering - east/west";
            }
        }

        // inner walls - south/north
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                int roomNorth = mapXYToRoomId(y, x);
                int roomSouth = mapXYToRoomId(y + 1, x);
                int id = addWall(roomNorth, roomSouth);
                assert id == wh+mapXYToRoomId(y, x) : "Inconsistent wall numbering - south/north";
            }
        }
        

        // holes are walls too
        // the user will use either holes or non-holes
        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width; x++) {
                int room1 = mapXYToRoomId(y, x);
                int hy = getTheOtherSideOfHoleY(y, x);
                int hx = getTheOtherSideOfHoleX(y, x);
                int room2 = mapXYToRoomId(hy, hx);
                int id = addWall(room1, room2);
                assert id == 2*wh-width+mapXYToRoomId(y, x) : "Inconsistent wall numbering - floor";
            }
        }

    }

    private int mapXYToRoomId(int hy, int hx) {
        return hx + hy * width;
    }

    protected int getTheOtherSideOfHole(int room) {
        int y = room / width;
        int x = room % width;
        int hy = getTheOtherSideOfHoleY(y, x);
        int hx = getTheOtherSideOfHoleX(y, x);
        return hy * width + hx;
    }

    protected int getTheOtherSideOfHoleY(int y, int x) {
        return height - 1 - y;
    }

    protected int getTheOtherSideOfHoleX(int y, int x) {
        return (x + width / 2) % width;
    }

    @Override
    public int getWallProbabilityWeight(int wall) {
        if (wall < eastWestWallCount) {
            // horizontal
            return 30;
        } else if (wall < eastWestWallCount + southNorthWallCount) {
            // vertical
            return 3;
        } else {
            // hole
            return 1;

        }
    }

    private int height;
    private int width;
    private ShapeContext context;

    private int eastWestWallCount;
    private int southNorthWallCount;

}
