package com.github.sladecek.maze.jmaze.voronoi;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.geometry.Point2D;

/***
 * 
 * Collection of points (room centers) in a rectangle.
 *
 */
public class PointsInRectangle {
	public PointsInRectangle(int width, int height, int roomCount) {
		super();
		this.width = width * 50;
		this.height = height * 50;
		this.roomCount = roomCount;
		roomCenterY = new double[roomCount];
		roomCenterX = new double[roomCount];

	}

	public static PointsInRectangle newRandom(int width, int height, int roomCount, Random randomGenerator) {
		PointsInRectangle p = new PointsInRectangle(width, height, roomCount);
		for (int i = 0; i < roomCount; i++) {
			p.roomCenterY[i] = randomGenerator.nextDouble() * height;
			p.roomCenterX[i] = randomGenerator.nextDouble() * width;
		}
		return p;
	}

	public static PointsInRectangle newCopyOf(PointsInRectangle input) {
		PointsInRectangle p = new PointsInRectangle(input.width, input.height, input.roomCount);
		p.roomCenterX = Arrays.copyOf(input.roomCenterX, input.roomCount);
		p.roomCenterY = Arrays.copyOf(input.roomCenterY, input.roomCount);
		return p;
	}
	
	
	public void sortByLongerCoordinate() {

		if (width >= height) {
			Arrays.sort(roomCenterX);
		} else {
			Arrays.sort(roomCenterY);
		}
	}

	public Point2D getIntegerPoint(int i) {
		int x = (int) Math.floor(roomCenterX[i]);
		int y = (int) Math.floor(roomCenterY[i]);
		return new Point2D(x, y);
	}

	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getRoomCount() {
		return roomCount;
	}

	public double[] getRoomCenterY() {
		return roomCenterY;
	}

	public double[] getRoomCenterX() {
		return roomCenterX;
	}


	public void setRoomCenterX(int i, double value) {
		roomCenterX[i] = value;
	}

	public void setRoomCenterY(int i, double value) {
		roomCenterY[i] = value;
	}

	private static final Logger LOGGER = Logger.getLogger("maze.jmaze");
	
	private int width;
	private int height;
	private int roomCount;

	private double[] roomCenterY;
	private double[] roomCenterX;
	

}