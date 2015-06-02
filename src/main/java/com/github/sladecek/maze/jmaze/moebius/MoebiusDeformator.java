package com.github.sladecek.maze.jmaze.moebius;

import com.github.sladecek.maze.jmaze.geometry.Point;

/** Transform point in planar maze into point on Moebius list.
 */
public class MoebiusDeformator {
	

	public MoebiusDeformator(double length_mm) {
		this.length_mm = length_mm;		
	}

	Point transform(Point p) {
		
		double r = length_mm / (4*Math.PI); 
		double theta = 4 * Math.PI * p.getX() / length_mm;
		double phi = theta / 2;
		
		double y2 = p.getY()*Math.cos(phi) + p.getZ()*Math.sin(phi);
		double z2 = -p.getY()*Math.sin(phi) + p.getZ()*Math.cos(phi);
		
		r += z2;
		Point result = new Point(r*Math.cos(theta), r*Math.sin(theta), y2);
		
		return result;
	}
	
	private double length_mm;
	
}
