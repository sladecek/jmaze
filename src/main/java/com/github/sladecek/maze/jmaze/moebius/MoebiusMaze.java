package com.github.sladecek.maze.jmaze.moebius;


import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.maze.BaseMaze;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.Shapes;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * 2D rectangular maze on Moebius strip.
 */
public final class MoebiusMaze extends BaseMaze {


    public MoebiusMaze() {

    }

    @Override
    public MazeProperties getDefaultProperties() {
        MazeProperties defaultProperties = super.getDefaultProperties();
        defaultProperties.put("name", "moebius");
        defaultProperties.put("sizeAlong", 40);
        defaultProperties.put("sizeAcross", 4);
        addDefault3DProperties(defaultProperties);
        return defaultProperties;
    }

    @Override
    public void buildMazeGraphAndShapes() {
        //      final boolean isPolar = false;
        sizeAcross = properties.getInt("sizeAcross");
        sizeAlong = properties.getInt("sizeAlong");

        if (sizeAlong % 2 != 0) {
            throw new IllegalArgumentException(
                    "Moebius maze must have even sizeAlong");
        }
        if (sizeAcross % 2 != 0) {
            throw new IllegalArgumentException(
                    "Moebius maze must have even sizeAcross");
        }

        allShapes = new Shapes(false, sizeAcross, sizeAlong);
        eastWestWallCount = sizeAlong * sizeAcross;
        southNorthWallCount = sizeAlong * (sizeAcross - 1);

        int expectedRoomCount = sizeAlong * sizeAcross;
        int firstHorizontalWall = expectedRoomCount;
        int firstFloorWall = firstHorizontalWall + expectedRoomCount - sizeAlong;
        expectedWallCount = firstFloorWall + expectedRoomCount / 2;
        expectedShapeCount = 3 * expectedRoomCount + sizeAlong;

        getGraph().setStartRoom(0);
        getGraph().setTargetRoom(sizeAlong / 4 + (sizeAcross - 1) * sizeAlong);

        for (int y = 0; y < sizeAcross; y++) {
            for (int x = 0; x < sizeAlong; x++) {
                int wall = mapXYToRoomId(y, x);
                int i = getGraph().addRoom();
                assert i == wall : "Inconsistent room numbering";

                FloorShape r3 = new FloorShape(i, new Point2DInt(x, y));
                r3.setRoomId(i);
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

        assert getAllShapes().length() == expectedShapeCount : "Incorrect shape count";

    }

    @Override
    public IMaze3DMapper create3DMapper() {
        IPrintStyle colors = new DefaultPrintStyle();

        Moebius3dMapper mapper = new Moebius3dMapper(sizeAcross, sizeAlong,
                properties.getDouble("cellSize"),
                properties.getDouble("innerWallSize")
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

    private int getTheOtherSideOfHole(int room) {
        int y = room / sizeAlong;
        int x = room % sizeAlong;
        int hy = getTheOtherSideOfHoleY(y, x);
        int hx = getTheOtherSideOfHoleX(y, x);
        return hy * sizeAlong + hx;
    }

    private int getTheOtherSideOfHoleY(int y, int x) {
        return sizeAcross - 1 - y;
    }

    private int getTheOtherSideOfHoleX(int y, int x) {
        return (x + sizeAlong / 2) % sizeAlong;
    }

    private void setWallProbabilityWeight(int wall) {
        int value = 1;
        if (wall < eastWestWallCount) {
            // horizontal
            value = 30;
        } else if (wall < eastWestWallCount + southNorthWallCount) {
            // vertical
            value = 3;
        } else {
            // hole
            value = 99; // TODO

        }
        getGraph().setWallProbability(wall, value);
    }

    private int sizeAcross;
    private int sizeAlong;

    private int expectedWallCount;
    private int expectedShapeCount;

    private int eastWestWallCount;
    private int southNorthWallCount;

}
