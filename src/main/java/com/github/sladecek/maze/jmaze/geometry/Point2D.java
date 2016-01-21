package com.github.sladecek.maze.jmaze.geometry;

/*
 * 2D point either in rectangular or polar coordinates. In case of polar coordinates, x is used for angle, y for diameter.
 */
public class Point2D {
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public int getAngle() {
		return x;
	}
	public int getDiameter() {
		return y;
	}

	public double getAngleRad() {
		return getAngle() / (double)ANGLE_2PI;
	}

	public Point2D(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}


	private int x;
	private int y;
	public static final int ANGLE_2PI = 0x1000000;
}
