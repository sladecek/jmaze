package com.github.sladecek.maze.jmaze.moebius;


import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Irrengarten;
import com.github.sladecek.maze.jmaze.shapes.*;

/**
 * 2D rectangular maze on Moebius strip.
 */
public final class MoebiusMaze extends Irrengarten implements IMazeStructure {

    public MoebiusMaze(final int height, final int width) {
        this.width = width;
        this.height = height;

        if (width % 2 != 0) {
            throw new IllegalArgumentException(
                    "Moebius maze must have even width");
        }
        if (height % 2 != 0) {
            throw new IllegalArgumentException(
                    "Moebius maze must have even height");
        }
        eastWestWallCount = width * height;
        southNorthWallCount = width * (height - 1);

        buildMaze();
    }


    private void buildMaze() {
        int expectedRoomCount = width * height;
        int firstHorizontalWall = expectedRoomCount;
        int firstFloorWall = firstHorizontalWall + expectedRoomCount - width;
        expectedWallCount = firstFloorWall + expectedRoomCount / 2;
        expectedShapeCount = 3 * expectedRoomCount + width;

        setStartRoom(0);
        setTargetRoom(width / 4 + (height - 1) * width);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int wall = mapXYToRoomId(y, x);
                int i = addRoom();
                assert i == wall : "Inconsistent room numbering";

                FloorShape r3 = new FloorShape(i, new Point2DInt(x, y));
                r3.setRoomId(i);
                addShape(r3);
            }
        }


        // inner walls - east/west
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int roomEast = mapXYToRoomId(y, x);
                int roomWest = mapXYToRoomId(y, (x + 1) % width);
                int id = addWall(roomEast, roomWest);
                addShape(WallShape.newInnerWall(id,
                        new Point2DInt(x, y), new Point2DInt(x, y + 1),
                        roomEast, roomWest)
                );
                assert id == mapXYToRoomId(y, x) : "Inconsistent wall numbering - east/west";
            }
        }


        // inner walls - south/north
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                int roomNorth = mapXYToRoomId(y, x);
                int roomSouth = mapXYToRoomId(y + 1, x);
                int id = addWall(roomNorth, roomSouth);
                addShape(WallShape.newInnerWall(id, new Point2DInt((x - 1 + width) % width, y + 1), new Point2DInt(x, y + 1),
                        roomSouth, roomNorth
                        )
                );
                assert id == firstHorizontalWall + mapXYToRoomId(y, x) : "Inconsistent wall numbering - south/north";
            }
        }

        // outer walls - south/north
        for (int x = 0; x < width; x++) {
            int roomNorth = mapXYToRoomId(0, x);
            int xx = (x - 1 + width) % width;
            addShape(WallShape.newOuterWall(new Point2DInt(xx, 0), new Point2DInt(x, 0), roomNorth, -1));
            int roomSouth = mapXYToRoomId(height - 1, x);
            addShape(WallShape.newOuterWall(new Point2DInt(xx, height), new Point2DInt(x, height), -2, roomSouth));
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

        assert getShapes().length() == expectedShapeCount : "Incorrect shape count";

    }

    private int mapXYToRoomId(int hy, int hx) {
        return hx + hy * width;
    }

    private int getTheOtherSideOfHole(int room) {
        int y = room / width;
        int x = room % width;
        int hy = getTheOtherSideOfHoleY(y, x);
        int hx = getTheOtherSideOfHoleX(y, x);
        return hy * width + hx;
    }

    private int getTheOtherSideOfHoleY(int y, int x) {
        return height - 1 - y;
    }

    private int getTheOtherSideOfHoleX(int y, int x) {
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
            return 99; // TODO

        }
    }

    private final int height;
    private final int width;

    private int expectedWallCount;
    private int expectedShapeCount;

    private int eastWestWallCount;
    private int southNorthWallCount;

}
