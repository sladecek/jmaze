package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.maze.BaseMaze;
import com.github.sladecek.maze.jmaze.maze.GenericMazeStructure;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.PlanarMapper;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * 2D rectangular maze. Both rooms and walls are numbered first by rows, then by
 * columns. East/west walls are numbered before south/north ones.
 */
public class RectangularMaze extends BaseMaze {

    public RectangularMaze(int width, int height) {
        this();
        MazeProperties p = getDefaultProperties();
        p.put("width", width);
        p.put("height", height);
        setProperties(p);
    }

    public RectangularMaze() {
        super();
        defaultProperties.put("name", "rect");
        defaultProperties.put("width", 20);
        defaultProperties.put("height", 20);
        properties = defaultProperties.clone();

    }

    @Override
    public void buildMaze() {
        final boolean isPolar = false;
        final int height = properties.getInt("height");
        final int width = properties.getInt("width");

        // width and height in pixels
        final int h = rsp * height;
        final int w = rsp * width;

        flatModel = new ShapeContainer(isPolar, h, w);


        // rooms
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int roomId = graph.addRoom();
                assert roomId == y * width + x : "Inconsistent room numbering";
                final int x1 = x * rsp + rsp / 2;
                final int y1 = y * rsp + rsp / 2;
                final Point2DInt position = new Point2DInt(x1, y1);
                final MarkShape mark = new MarkShape(roomId, position);
                flatModel.add(mark);
                final FloorShape floor = new FloorShape(roomId, position);
                flatModel.add(floor);
            }
        }

        // walls - east/west
        for (int y = 0; y < height; y++) {
            for (int x = -1; x < width; x++) {
                int roomIdWest = y * width + x;
                int roomIdEast = roomIdWest + 1;
                final Point2DInt p1 = new Point2DInt(rsp * (x + 1), rsp * y);
                final Point2DInt p2 = new Point2DInt(rsp * (x + 1), rsp * (y + 1));

                if (x >= width - 1) {
                    // east outer wall
                    flatModel.add(WallShape.newOuterWall(p1, p2, roomIdWest, -1));

                } else if (x < 0) {
                    // west outer wall
                    flatModel.add(WallShape.newOuterWall(p1, p2, -1, roomIdEast));
                } else {

                    // inner wall
                    int id = graph.addWall(roomIdWest, roomIdEast);
                    flatModel.add(WallShape.newInnerWall(id, p1, p2, roomIdWest, roomIdEast));

                }
            }
        }

        // walls - south/north
        for (int y = -1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int roomIdNorth = y * width + x;
                int roomIdSouth = roomIdNorth + width;
                final Point2DInt p1 = new Point2DInt(rsp * x, rsp * (y + 1));
                final Point2DInt p2 = new Point2DInt(rsp * (x + 1), rsp * (y + 1));

                if (y < 0) {
                    flatModel.add(WallShape.newOuterWall(p1, p2, roomIdSouth, -1));
                } else if (y >= height - 1) {
                    // south outer wall
                    flatModel.add(WallShape.newOuterWall(p1, p2, -1, roomIdNorth));
                } else {
                    // inner wall
                    int id = graph.addWall(roomIdNorth, roomIdSouth);
                    flatModel.add(WallShape.newInnerWall(id, p1, p2, roomIdSouth, roomIdNorth));
                }
            }
        }
        graph.setStartRoom(0);
        graph.setTargetRoom(width * height - 1);
    }


    @Override
    public IMaze3DMapper create3DMapper() {
        PlanarMapper m = new PlanarMapper();
        m.configureAltitudes(properties);
        return m;
    }

    @Override
    public boolean canBePrintedIn2D() {
        return true;
    }


    /**
     * Room size in pixels.
     */
    private final int rsp = 20;

}