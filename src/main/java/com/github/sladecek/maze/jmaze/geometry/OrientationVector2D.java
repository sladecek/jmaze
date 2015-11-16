package com.github.sladecek.maze.jmaze.geometry;

/*
 * Two dimensional vector.
 */
public final class OrientationVector2D {
	
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

	private double x;
	private double y;
}
