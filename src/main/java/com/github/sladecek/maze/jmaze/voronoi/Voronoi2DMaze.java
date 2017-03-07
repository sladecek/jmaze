package com.github.sladecek.maze.jmaze.voronoi;

import be.humphreys.simplevoronoi.GraphEdge;
import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import java.util.Random;
import java.util.logging.Logger;

public class Voronoi2DMaze extends Maze implements IMazeStructure {


    public Voronoi2DMaze(int width, int height, int roomCount, int loydCnt, Random randomGenerator, boolean debug) {
        super();
        this.width = width * rsp;
        this.height = height * rsp;
        this.roomCount = roomCount;
        this.loydCnt = loydCnt;
        this.randomGenerator = randomGenerator;
        this.setDebug(debug);
        buildMaze();
    }

    private void buildMaze() {
        final boolean isPolar = false;
        setContext(new ShapeContext(isPolar, height, width/*, 1, 1*/));

        createOuterWalls();

        PointsInRectangle p0 = PointsInRectangle.newRandom(width, height, roomCount, randomGenerator);

        for (int i = 0; i < p0.getRoomCount(); i++) {
            Point2D pt = p0.getIntegerPoint(i);
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
            Point2D pt = p1.getIntegerPoint(i);
            LOGGER.info("room center [" + i + "]=" + pt);
            int r = addRoom();
            final MarkShape floor = new MarkShape(r, pt);

            addShape(floor);
        }

        for (GraphEdge e : new VoronoiAlgorithm().computeEdges(p1)) {
            if (e.site1 < 0 || e.site2 < 0) {
                continue;
            }
            if (e.length() < 0.5) continue;
            int id = addWall(e.site1, e.site2);
            WallShape ws = new WallShape(ShapeType.innerWall, (int) e.y1, (int) e.x1, (int) e.y2, (int) e.x2);
            addShape(ws);
            linkShapeToId(ws, id);
            LOGGER.info("wall id=" + id + " " + e);

        }

        setStartRoom(0);
        setTargetRoom(roomCount - 1);
    }

    private void createOuterWalls() {
        final IMazeShape2D.ShapeType ow = IMazeShape2D.ShapeType.outerWall;
        addShape(new WallShape(ow, 0, 0, 0, width));
        addShape(new WallShape(ow, 0, 0, height, 0));
        addShape(new WallShape(ow, 0, width, height, width));
        addShape(new WallShape(ow, height, 0, height, width));
    }

    private static final Logger LOGGER = Logger.getLogger("maze.jmaze");
    /**
     * Approximate room size in pixels.
     */
    private final int rsp = 20;
    private int width;
    private int height;
    private int roomCount;
    private int loydCnt;

    private Random randomGenerator;
}
