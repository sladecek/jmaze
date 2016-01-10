package com.github.sladecek.maze.jmaze.voronoi;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Voronoi;

import com.github.sladecek.maze.jmaze.generator.GenericMazeSpace;
import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.GenericShapeMaker;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.IShapeMaker;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

public class Voronoi2DMaze extends GenericMazeSpace implements IMazeSpace, IShapeMaker {


	public Voronoi2DMaze(int width, int height, int roomCount) {
		super();
		this.width = width;
		this.height = height;
		this.roomCount = roomCount;
		buildMaze();
	}

	private int width;
	private int height;
	private int roomCount;

    private double[] roomCenterY;
	private double[] roomCenterX;

	private GenericShapeMaker shapeMaker;

	private Random randomGenerator = new Random();

	private static int flr(double r) {
		return (int)Math.floor(r);
	}
	
	@Override
	public ShapeContainer makeShapes(MazeRealization realization) {
		ShapeContainer result = shapeMaker.makeShapes(realization, getStartRoom(), getTargetRoom(), 0, 50); 
		result.setPictureHeight(width);
		result.setPictureWidth(2*width);
		
		// outer walls
		final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
		result.add(new WallShape(ow, 0, 0, 0, width));
		result.add(new WallShape(ow, 0, 0, height, 0));
		result.add(new WallShape(ow, 0, width, height, width));
		result.add(new WallShape(ow, height, 0, height, width));

		
		return result;
	}

	
	private void buildMaze() {
		shapeMaker = new GenericShapeMaker();
			
	    roomCenterY = new double[roomCount];
		roomCenterX = new double[roomCount];
		for (int i = 0; i < roomCount; i++) {
			roomCenterY[i] = randomGenerator.nextDouble()*height;
			roomCenterX[i] = randomGenerator.nextDouble()*width;
		}


		for (int i = 0; i < roomCount; i++) {			
			int x = flr(roomCenterX[i]);
			int y = flr(roomCenterY[i]);
			int r = addRoom();
			String floorId = "r" + Integer.toString(r);
			final FloorShape floor = new FloorShape(y, x, false, floorId);
			shapeMaker.linkRoomToFloor(r, floor);
			shapeMaker.addShape(floor);
		}
		
		Voronoi v = new Voronoi(0.00001f);
		List<GraphEdge> allEdges = v.generateVoronoi(roomCenterY, roomCenterX, 0, height-1, 0, width-1);
		
		for (GraphEdge ge: allEdges) {
			int id = addWall(ge.site1, ge.site2);
			LOGGER.info("voronoi edge "+ge);
			WallShape ws = new WallShape(ShapeType.innerWall, flr(ge.x1), flr(ge.y1), flr(ge.x2), flr(ge.y2));
			shapeMaker.addShape(ws);
			shapeMaker.linkShapeToId(ws, id);
			
		}
		
		// TODO
		setStartRoom(0);
		setTargetRoom(roomCount-1);
	}

	
	private static final Logger LOGGER =  Logger.getLogger("maze.jmaze");
}
