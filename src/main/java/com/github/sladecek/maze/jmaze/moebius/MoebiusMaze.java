package com.github.sladecek.maze.jmaze.moebius;


import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.maze.BaseMaze;

import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.*;

/**
 * 2D rectangular maze on Moebius strip.
 */
public final class MoebiusMaze extends BaseMaze {


    public MoebiusMaze(int width, int height) {
        this();
        MazeProperties p = getDefaultProperties();
        p.put("width", width);
        p.put("height", height);
        setProperties(p);
    }

    public MoebiusMaze() {
        super();
        defaultProperties.put("name", "moebius");
        defaultProperties.put("width", 20);
        defaultProperties.put("height", 20);
        properties = defaultProperties.clone();

    }

    @Override
    public void buildMazeGraphAndShapes() {
  //      final boolean isPolar = false;
        height = properties.getInt("height");
        width = properties.getInt("width");

        if (width % 2 != 0) {
            throw new IllegalArgumentException(
                    "Moebius maze must have even width");
        }
        if (height % 2 != 0) {
            throw new IllegalArgumentException(
                    "Moebius maze must have even height");
        }

        flatModel = new ShapeContainer(false, height, width);
        eastWestWallCount = width * height;
        southNorthWallCount = width * (height - 1);

        int expectedRoomCount = width * height;
        int firstHorizontalWall = expectedRoomCount;
        int firstFloorWall = firstHorizontalWall + expectedRoomCount - width;
        expectedWallCount = firstFloorWall + expectedRoomCount / 2;
        expectedShapeCount = 3 * expectedRoomCount + width;

        getGraph().setStartRoom(0);
        getGraph().setTargetRoom(width / 4 + (height - 1) * width);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int wall = mapXYToRoomId(y, x);
                int i = getGraph().addRoom();
                assert i == wall : "Inconsistent room numbering";

                FloorShape r3 = new FloorShape(i, new Point2DInt(x, y));
                r3.setRoomId(i);
                getFlatModel().add(r3);
            }
        }


        // inner walls - east/west
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int roomEast = mapXYToRoomId(y, x);
                int roomWest = mapXYToRoomId(y, (x + 1) % width);
                int id = getGraph().addWall(roomEast, roomWest);
                setWallProbabilityWeight(id);
                getFlatModel().add(WallShape.newInnerWall(id,
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
                int id = getGraph().addWall(roomNorth, roomSouth);
                setWallProbabilityWeight(id);
                getFlatModel().add(WallShape.newInnerWall(id, new Point2DInt((x - 1 + width) % width, y + 1), new Point2DInt(x, y + 1),
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
            getFlatModel().add(WallShape.newOuterWall(new Point2DInt(xx, 0), new Point2DInt(x, 0), roomNorth, -1));
            int roomSouth = mapXYToRoomId(height - 1, x);
            getFlatModel().add(WallShape.newOuterWall(new Point2DInt(xx, height), new Point2DInt(x, height), -2, roomSouth));
        }

        // floors
        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width; x++) {
                int room1 = mapXYToRoomId(y, x);
                int hy = getTheOtherSideOfHoleY(y, x);
                int hx = getTheOtherSideOfHoleX(y, x);
                int room2 = mapXYToRoomId(hy, hx);
                int id = getGraph().addWall(room1, room2);
                setWallProbabilityWeight(id);
                assert id == firstFloorWall + mapXYToRoomId(y, x) : "Inconsistent wall numbering - floor";
            }
        }

        assert getGraph().getRoomCount() == expectedRoomCount : "Unexpected room count";
        assert getGraph().getWallCount() == expectedWallCount : "Unexpected wall count";

        assert getFlatModel().length() == expectedShapeCount : "Incorrect shape count";

    }

    @Override
    public IMaze3DMapper create3DMapper() {
        Maze3DSizes sizes = new Maze3DSizes();
        sizes.setCellSizeInmm(2);  // TODO
        sizes.setWallHeightInmm(30);

        IPrintStyle colors = new DefaultPrintStyle();

        Moebius3dMapper mapper = new Moebius3dMapper(sizes, height, width);
        mapper.configureAltitudes(properties);



        return mapper;
    }

    @Override
    public boolean canBePrintedIn2D() {
        return false;
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

    private void setWallProbabilityWeight(int wall) {
        int value = 1;
        if (wall < eastWestWallCount) {
            // horizontal
            value = 30;
        } else if (wall < eastWestWallCount + southNorthWallCount) {
            // vertical
            value =  3;
        } else {
            // hole
            value =  99; // TODO

        }
        getGraph().setWallProbability(wall, value);
    }

    private int height;
    private int width;

    private int expectedWallCount;
    private int expectedShapeCount;

    private int eastWestWallCount;
    private int southNorthWallCount;

}
