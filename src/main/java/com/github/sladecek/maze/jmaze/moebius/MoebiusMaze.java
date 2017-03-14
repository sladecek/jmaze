package com.github.sladecek.maze.jmaze.moebius;

import java.security.InvalidParameterException;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
//import com.github.sladecek.maze.jmaze.geometry.Direction;
import com.github.sladecek.maze.jmaze.shapes.*;

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

        expectedRoomCount = width * height;
        firstHorizontalWall = expectedRoomCount;
        firstFloorWall = firstHorizontalWall + expectedRoomCount - width;
        expectedWallCount = firstFloorWall + expectedRoomCount / 2;

        setStartRoom(0);
        setTargetRoom(width / 4 + (height - 1) * width);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int wall = mapXYToRoomId(y, x);
                int i = addRoom();
                assert i == wall : "Inconsistent room numbering";

                FloorShape r3 = new FloorShape(i, new Point2D(x, y));
                addShape(r3);
/*
                if (x > 0) {
                    r3.setWallId(Direction.WEST, i);
                } else {
                    // In Moebius maze, there is a seam before the first column.
                    r3.setWallId(Direction.WEST, expectedRoomCount - width - i);
                }

                if (x < width - 1) {
                    int rightWall = width * y + (x + 1 % width);
                    r3.setWallId(Direction.EAST, rightWall);
                } else {
                    int rightWall = width * (height-1-y) + (x + 1) % width;
                    r3.setWallId(Direction.EAST, rightWall);
                }

                if (y == 0) {
                    r3.setWallType(Direction.NORTH, WallType.outerWall);
                } else {
                    r3.setWallId(Direction.NORTH, firstHorizontalWall + i - width);
                }
                if (y == height - 1) {
                    r3.setWallType(Direction.SOUTH, WallType.outerWall);
                } else {
                    r3.setWallId(Direction.SOUTH, i + firstHorizontalWall);
                }

                int floorWallBelongsToRoom = i;
                if (y >= height / 2) {
                    floorWallBelongsToRoom = getTheOtherSideOfHole(i);
                }
                r3.setWallId(Direction.FLOOR, floorWallBelongsToRoom + firstFloorWall);
*/
            }
        }


        // inner walls - east/west
        for (int y = 0; y < height; y++) {
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
                assert id == firstHorizontalWall + mapXYToRoomId(y, x) : "Inconsistent wall numbering - south/north";
            }
        }


        // floors
        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width; x++) {
                int room1 = mapXYToRoomId(y, x);
                int hy = getTheOtherSideOfHoleY(y, x);
                int hx = getTheOtherSideOfHoleX(y, x);
                int room2 = mapXYToRoomId(hy, hx);
                int id = addWall(room1, room2);
                assert id == firstFloorWall + mapXYToRoomId(y, x) : "Inconsistent wall numbering - floor";
            }
        }

        assert getRoomCount() == expectedRoomCount : "Unexpected room count";
        assert getWallCount() == expectedWallCount : "Unexpected wall count";

        assert getShapes().length() == expectedRoomCount : "Incorrect room count";

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

    private int expectedRoomCount;
    private int firstHorizontalWall;
    private int firstFloorWall;
    private int expectedWallCount;

    private ShapeContext context;

    private int eastWestWallCount;
    private int southNorthWallCount;

}
