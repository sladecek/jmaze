package com.github.sladecek.maze.jmaze.moebius;

import com.github.sladecek.maze.jmaze.geometry.Point3D;

/**
 * 
 * Geometric properties of Moebius strip.
 *
 */
public final class MoebiusStripGeometry {
	
	public MoebiusStripGeometry(double circumferenceInmm) {
		this.circumferenceInmm = circumferenceInmm;		
		this.radiusInmm = this.circumferenceInmm / (4 * Math.PI); 
	}

	/** Transform point in planar maze into point on Moebius list.
	 */
	Point3D transform(Point3D p) {
		final double theta = computeTheta(p.getX());
		final double phi = theta / 2;
		
		// rotate along peripheral axis
		final double y2 = p.getY() * Math.cos(phi) + p.getZ() * Math.sin(phi);
		final double z2 = -p.getY() * Math.sin(phi) + p.getZ() * Math.cos(phi);
		
		// rotate along global axis 
		final double rInmm = radiusInmm + z2;
		final Point3D result = new Point3D(rInmm * Math.cos(theta), rInmm * Math.sin(theta), y2);
		
		return result;
	}

	public double getCircumferenceInmm() {
		return circumferenceInmm;
	}

	public double getRadiusInmm() {
		return radiusInmm;
	}

	private double computeTheta(double distanceAlongEdgeInmm) {
		return 4 * Math.PI * distanceAlongEdgeInmm / circumferenceInmm;
	}
	
	private final double circumferenceInmm;
	private final double radiusInmm;
	
}
