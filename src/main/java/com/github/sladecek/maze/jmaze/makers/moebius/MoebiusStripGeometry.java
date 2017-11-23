package com.github.sladecek.maze.jmaze.makers.moebius;

import com.github.sladecek.maze.jmaze.geometry.Point3D;

/**
 * 
 * Geometric properties of Moebius strip.
 *
 */
public final class MoebiusStripGeometry {
	
	public MoebiusStripGeometry(double circumference) {
		this.circumference = circumference;
		this.radius = this.circumference / (4 * Math.PI);
	}

	/**
	 * Transform point in planar maze into point on Moebius list.
	 */
	Point3D transform(Point3D p) {
		final double theta = computeTheta(p.getX());
		final double phi = theta / 2;
		
		// rotate along peripheral axis
		final double y2 = p.getY() * Math.cos(phi) + p.getZ() * Math.sin(phi);
		final double z2 = -p.getY() * Math.sin(phi) + p.getZ() * Math.cos(phi);
		
		// rotate along global axis 
		final double rInmm = radius + z2;
		return new Point3D(rInmm * Math.cos(theta), rInmm * Math.sin(theta), y2);
	}

    double computeTheta(double distanceAlongEdge) {
        return 4 * Math.PI * distanceAlongEdge / circumference;
    }

    public double getCircumference() {
		return circumference;
	}

	public double getRadius() {
		return radius;
	}
	
	private final double circumference;
	private final double radius;
}
