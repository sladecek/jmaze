package com.github.sladecek.maze.jmaze.moebius;


import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.Shapes;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * 2D rectangular maze on Moebius strip.
 */
public final class MoebiusMaze extends Maze {

      /*
    TODO validace




        int moebWidth = maze.getMoebWidth();
        if (moebWidth % 2 == 1) {
            errors.rejectValue("moebWidth", "odd", "must not be odd");
        }
    */
    public MoebiusMaze() {
    }

    @Override
    public MazeProperties getDefaultProperties() {
        MazeProperties defaultProperties = super.getDefaultProperties();
        defaultProperties.put("name", "moebius");
        defaultProperties.put("sizeAlong", 40);
        defaultProperties.put("sizeAcross", 4);
        addComputedProperties(defaultProperties);
        addDefault3DProperties(defaultProperties);
        return defaultProperties;
    }

    @Override
    public void buildMazeGraphAndShapes() {
        //      final boolean isPolar = false;
        sizeAcross = properties.getInt("sizeAcross", 2, 1000);
        sizeAlong = properties.getInt("sizeAlong", 2, 10000);

        if (sizeAlong % 2 != 0) {
            throw new IllegalArgumentException(
                    "Moebius maze must have even sizeAlong");
        }
        if (sizeAcross % 2 != 0) {
            throw new IllegalArgumentException(
                    "Moebius maze must have even sizeAcross");
        }

        allShapes = new Shapes(false, sizeAcross, sizeAlong, 0);
        eastWestWallCount = sizeAlong * sizeAcross;
        southNorthWallCount = sizeAlong * (sizeAcross - 1);

        int expectedRoomCount = sizeAlong * sizeAcross;
        @SuppressWarnings("UnnecessaryLocalVariable")
        int firstHorizontalWall = expectedRoomCount;
        int firstFloorWall = firstHorizontalWall + expectedRoomCount - sizeAlong;
        int expectedWallCount = firstFloorWall + expectedRoomCount / 2;
        int expectedShapeCount = 3 * expectedRoomCount + sizeAlong;

        getGraph().setStartRoom(0);
        getGraph().setTargetRoom(sizeAlong / 4 + (sizeAcross - 1) * sizeAlong);

        for (int y = 0; y < sizeAcross; y++) {
            for (int x = 0; x < sizeAlong; x++) {
                int wall = mapXYToRoomId(y, x);
                int i = getGraph().addRoom();
                assert i == wall : "Inconsistent room numbering";

                FloorShape r3 = new FloorShape(i, new Point2DInt(x, y));
                r3.setRoomId(i);

                int wallIdOfThisFloor = wall;
                if (y >= sizeAcross) {
                    wallIdOfThisFloor = mapXYToRoomId(getTheOtherSideOfHoleX(x), getTheOtherSideOfHoleY(y));
                }
                r3.setWallId(firstFloorWall + wallIdOfThisFloor);

                getAllShapes().add(r3);
            }
        }

        // inner walls - east/west
        for (int y = 0; y < sizeAcross; y++) {
            for (int x = 0; x < sizeAlong; x++) {
                int roomEast = mapXYToRoomId(y, x);
                int roomWest = mapXYToRoomId(y, (x + 1) % sizeAlong);
                int id = getGraph().addWall(roomEast, roomWest);
                setWallProbabilityWeight(id);
                getAllShapes().add(WallShape.newInnerWall(id,
                        new Point2DInt(x, y), new Point2DInt(x, y + 1),
                        roomEast, roomWest)
                );
                assert id == mapXYToRoomId(y, x) : "Inconsistent wall numbering - east/west";
            }
        }


        // inner walls - south/north
        for (int y = 0; y < sizeAcross - 1; y++) {
            for (int x = 0; x < sizeAlong; x++) {
                int roomNorth = mapXYToRoomId(y, x);
                int roomSouth = mapXYToRoomId(y + 1, x);
                int id = getGraph().addWall(roomNorth, roomSouth);
                setWallProbabilityWeight(id);
                getAllShapes().add(WallShape.newInnerWall(id, new Point2DInt((x - 1 + sizeAlong) % sizeAlong, y + 1), new Point2DInt(x, y + 1),
                        roomSouth, roomNorth
                        )
                );
                assert id == firstHorizontalWall + mapXYToRoomId(y, x) : "Inconsistent wall numbering - south/north";
            }
        }

        // outer walls - south/north
        for (int x = 0; x < sizeAlong; x++) {
            int roomNorth = mapXYToRoomId(0, x);
            int xx = (x - 1 + sizeAlong) % sizeAlong;
            getAllShapes().add(WallShape.newOuterWall(new Point2DInt(xx, 0), new Point2DInt(x, 0), roomNorth, -1));
            int roomSouth = mapXYToRoomId(sizeAcross - 1, x);
            getAllShapes().add(WallShape.newOuterWall(new Point2DInt(xx, sizeAcross), new Point2DInt(x, sizeAcross), -2, roomSouth));
        }

        // floors
        for (int y = 0; y < sizeAcross / 2; y++) {
            for (int x = 0; x < sizeAlong; x++) {
                int room1 = mapXYToRoomId(y, x);
                int hy = getTheOtherSideOfHoleY(y);
                int hx = getTheOtherSideOfHoleX(x);
                int room2 = mapXYToRoomId(hy, hx);
                int id = getGraph().addWall(room1, room2);
                setWallProbabilityWeight(id);
                assert id == firstFloorWall + mapXYToRoomId(y, x) : "Inconsistent wall numbering - floor";
            }
        }

        assert getGraph().getRoomCount() == expectedRoomCount : "Unexpected room count";
        assert getGraph().getWallCount() == expectedWallCount : "Unexpected wall count";

        assert getAllShapes().length() == expectedShapeCount : "Incorrect shape count";

    }

    @Override
    public IMaze3DMapper create3DMapper() {
        Moebius3dMapper mapper = new Moebius3dMapper(sizeAcross, sizeAlong,
                properties.getDouble("cellSize"),
                properties.getDouble("wallSize")
        );
        mapper.configureAltitudes(properties);

        return mapper;
    }

    @Override
    public boolean canBePrintedIn2D() {
        return false;
    }

    private int mapXYToRoomId(int hy, int hx) {
        return hx + hy * sizeAlong;
    }

    private int getTheOtherSideOfHoleY(int y) {
        return sizeAcross - 1 - y;
    }

    private int getTheOtherSideOfHoleX(int x) {
        return (x + sizeAlong / 2) % sizeAlong;
    }

    private void setWallProbabilityWeight(int wall) {
        int value;
        if (wall < eastWestWallCount) {
            // horizontal - along
            value = 30;
        } else if (wall < eastWestWallCount + southNorthWallCount) {
            // vertical - across
            value = 3;
        } else {
            // hole
            value = 99;
        }
        getGraph().setWallProbability(wall, value);
    }

    public boolean isAlongWall(int wallId) {
        return wallId < eastWestWallCount;
    }

    public boolean isAcrossWall(int wallId) {
        return (wallId >= eastWestWallCount) && (wallId < eastWestWallCount + southNorthWallCount);
    }

    public boolean isHole(int wallId) {
        return wallId >= eastWestWallCount + southNorthWallCount;
    }

    private int sizeAcross;
    private int sizeAlong;

    private int eastWestWallCount;
    private int southNorthWallCount;

}
