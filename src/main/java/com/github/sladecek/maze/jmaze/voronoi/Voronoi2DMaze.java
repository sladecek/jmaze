package com.github.sladecek.maze.jmaze.voronoi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Voronoi;

public class Voronoi2DMaze extends Maze implements IMazeStructure {

	public Voronoi2DMaze(int width, int height, int roomCount, Random randomGenerator) {
		super();
		this.width = width;
		this.height = height;
		this.roomCount = roomCount;
		this.randomGenerator = randomGenerator;
		buildMaze();
	}

	private static int flr(double r) {
		return (int) Math.floor(r);
	}

	private void buildMaze() {
		final boolean isPolar = false;
		setContext(new ShapeContext(isPolar, height, width, 10, 10, 0, 50));

		createOuterWalls();

		roomCenterY = new double[roomCount];
		roomCenterX = new double[roomCount];
		for (int i = 0; i < roomCount; i++) {
			roomCenterY[i] = randomGenerator.nextDouble() * height;
			roomCenterX[i] = randomGenerator.nextDouble() * width;
		}

		if (width  >= height) {
			Arrays.sort(roomCenterX);
		} else {
			Arrays.sort(roomCenterY);
		}
		
		for (int i = 0; i < roomCount; i++) {
			LOGGER.info("room center [" + i + "]=" + roomCenterX[i] + "," + roomCenterY[i]);
			int x = flr(roomCenterX[i]);
			int y = flr(roomCenterY[i]);
			int r = addRoom();
			final FloorShape floor = new FloorShape(new Point2D(x, y), false);
			linkRoomToFloor(r, floor);
			addShape(floor);
		}

		// TODO smazat
		HashSet<Long> set = new HashSet();
		Voronoi v = new Voronoi(0.00001f);
		List<GraphEdge> allEdges = v.generateVoronoi(roomCenterX, roomCenterY, 0, width - 1, 0, height - 1);

		for (GraphEdge ge : allEdges) {
			/*
			 * TODO smazat - older simplevoronoi
			 */
			Long k = ge.site1 * 1000000l + ge.site2;
			if (ge.site1 < ge.site2) {
				k = ge.site2 * 1000000l + ge.site1;
			}
			if (set.contains(k)) {
				continue;
			}

			set.add(k);

			// Voronoi algorithm generates nonsensical edges with very small
			// length that would
			// otherwise be outside the rectangle. There are also estheticall
			// reasons to filter
			// short edges out.
			double dx = (ge.x1 - ge.x2);
			double dy = (ge.y1 - ge.y2);
			double lsq = dx * dx + dy * dy;
			final double minimalLength = 0.01;
			boolean isShort = lsq < minimalLength * minimalLength;
			LOGGER.info("voronoi edge room1=" + ge.site1 + " room2=" + ge.site2 + " x1=" + ge.x1 + " y1=" + ge.y1
					+ " x2=" + ge.x2 + " y2=" + ge.y2 + " isShort=" + isShort);

			if (isShort) {
				// continue;
			}

			int id = addWall(ge.site1, ge.site2);
			WallShape ws = new WallShape(ShapeType.innerWall, flr(ge.y1), flr(ge.x1), flr(ge.y2), flr(ge.x2));
			addShape(ws);
			linkShapeToId(ws, id);
		}

		// TODO
		setStartRoom(0);
		setTargetRoom(roomCount - 1);
	}

	private void createOuterWalls() {
		final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
		addShape(new WallShape(ow, 0, 0, 0, width));
		addShape(new WallShape(ow, 0, 0, height, 0));
		addShape(new WallShape(ow, 0, width, height, width));
		addShape(new WallShape(ow, height, 0, height, width));
	}

	public void setRandomSeed(final long seed) {
		randomGenerator.setSeed(seed);
	}

	private static final Logger LOGGER = Logger.getLogger("maze.jmaze");
	private int width;
	private int height;
	private int roomCount;

	private double[] roomCenterY;
	private double[] roomCenterX;

	private Random randomGenerator;
}
