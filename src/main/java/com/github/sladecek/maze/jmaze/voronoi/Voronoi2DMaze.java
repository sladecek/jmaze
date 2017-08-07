package com.github.sladecek.maze.jmaze.voronoi;

import be.humphreys.simplevoronoi.GraphEdge;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.maze.BaseMaze;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Irrengarten;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;

import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import java.util.Random;
import java.util.logging.Logger;

public class Voronoi2DMaze extends BaseMaze {


    public Voronoi2DMaze(int width, int height, int roomCount, int loydCnt) {
        this();
        MazeProperties p = getDefaultProperties();
        p.put("width", width);
        p.put("height", height);
        p.put("roomCount", roomCount);
        p.put("loydCount", loydCnt);
        setProperties(p);
    }
    
    public Voronoi2DMaze() {
        super();
        defaultProperties.put("name", "voronoi");
        defaultProperties.put("width", 10);
        defaultProperties.put("height", 10);
        defaultProperties.put("roomCount", 100);
        defaultProperties.put("loydCount", 10);
        properties = defaultProperties.clone();
    }

    @Override
    public void buildMaze() {
        this.width = properties.getInt("width") * rsp;
        this.height = properties.getInt("height") * rsp;
        this.roomCount = properties.getInt("roomCount");
        this.loydCnt = properties.getInt("loydCount");
        
        final boolean isPolar = false;
        
        flatModel = new ShapeContainer(isPolar, height, width);

        createOuterWalls();

        PointsInRectangle p0 = PointsInRectangle.newRandom(width, height, roomCount, randomGenerator);

        for (int i = 0; i < p0.getRoomCount(); i++) {
            Point2DInt pt = p0.getIntegerPoint(i);
            LOGGER.info("random room center [" + i + "]=" + pt);
        }

        LoydIteration loyd = new LoydIteration(p0, loydCnt);
        PointsInRectangle p1 = loyd.getOutput();
        /*
        System.out.print("x=[");
		for (int i = 0; i < p1.getRoomCount(); i++) {
			System.out.print(p1.getRoomCenterX()[i] + ", ");
		}
		System.out.println("];");
		System.out.print("y=[");
		for (int i = 0; i < p1.getRoomCount(); i++) {
			System.out.print(p1.getRoomCenterY()[i] + ", ");
		}
		System.out.println("];");
		*/
        // sort points by longer coordinate to get decent start/target rooms
        p1.sortByLongerCoordinate();

        for (int i = 0; i < roomCount; i++) {
            Point2DInt pt = p1.getIntegerPoint(i);
            LOGGER.info("room center [" + i + "]=" + pt);
            int r = getGraph().addRoom();
            final MarkShape floor = new MarkShape(r, pt);

            getFlatModel().add(floor);
        }

        for (GraphEdge e : new VoronoiAlgorithm().computeEdges(p1)) {
            if (e.site1 < 0 || e.site2 < 0) {
                continue;
            }
            if (e.length() < 0.5) continue;
            int id = getGraph().addWall(e.site1, e.site2);
            getFlatModel().add(WallShape.newInnerWall(id,
                    new Point2DInt((int) e.x1, (int) e.y1), new Point2DInt((int) e.x2, (int) e.y2)));


            LOGGER.info("wall id=" + id + " " + e);

        }

        getGraph().setStartRoom(0);
        getGraph().setTargetRoom(roomCount - 1);
    }

    @Override
    public IMaze3DMapper create3DMapper() {
        return null;
    }

    @Override
    public boolean canBePrintedIn2D() {
        return true;
    }

    private void createOuterWalls() {

        getFlatModel().add(WallShape.newOuterWall(new Point2DInt(0, 0), new Point2DInt(width, 0)));
        getFlatModel().add(WallShape.newOuterWall(new Point2DInt(0, 0), new Point2DInt(0, height)));
        getFlatModel().add(WallShape.newOuterWall(new Point2DInt(0, height), new Point2DInt(width, height)));
        getFlatModel().add(WallShape.newOuterWall(new Point2DInt(width, 0), new Point2DInt(width, height)));

    }

    private static final Logger LOGGER = Logger.getLogger("maze");
    /**
     * Approximate room size in pixels.
     */
    private final int rsp = 20;
    private int width;
    private int height;
    private int roomCount;
    private int loydCnt;


}
