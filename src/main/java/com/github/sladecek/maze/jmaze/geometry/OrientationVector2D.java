package com.github.sladecek.maze.jmaze.geometry;

public final class OrientationVector2D {

	private double x;
	private double y;
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "OrientationVector2D [x=" + x + ", y=" + y + "]";
	}
	
	public OrientationVector2D(final double x, final double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public OrientationVector2D getOrthogonal() {
		return new OrientationVector2D(y, -x);
	}
	
}
