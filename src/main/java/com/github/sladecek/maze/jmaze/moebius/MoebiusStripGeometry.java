package com.github.sladecek.maze.jmaze.moebius;

import com.github.sladecek.maze.jmaze.geometry.Point;

/**
 * 
 * Geometric properties of Moebius strip.
 *
 */
public class MoebiusStripGeometry {
	
	public MoebiusStripGeometry(double circumference_mm) {
		this.circumference_mm = circumference_mm;		
		this.radius_mm = this.circumference_mm / (4*Math.PI); 
	}

	/** Transform point in planar maze into point on Moebius list.
	 */
	Point transform(Point p) {
		final double theta = computeTheta(p.getX());
		final double phi = theta / 2;
		
		// rotate along peripheral axis
		final double y2 = p.getY()*Math.cos(phi) + p.getZ()*Math.sin(phi);
		final double z2 = -p.getY()*Math.sin(phi) + p.getZ()*Math.cos(phi);
		
		// rotate along global axis 
		final double r_mm = radius_mm + z2;
		final Point result = new Point(r_mm*Math.cos(theta), r_mm*Math.sin(theta), y2);
		
		return result;
	}

	public double getCircumference_mm() {
		return circumference_mm;
	}

	public double getRadius_mm() {
		return radius_mm;
	}

	private double computeTheta(double distanceAlongEdge_mm) {
		return 4 * Math.PI * distanceAlongEdge_mm / circumference_mm;
	}
	
	private final double circumference_mm;
	private final double radius_mm;
	
}
