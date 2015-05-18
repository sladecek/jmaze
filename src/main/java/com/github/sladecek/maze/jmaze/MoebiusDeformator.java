package com.github.sladecek.maze.jmaze;

public class MoebiusDeformator {
	
	double length_mm;
	double width_mm;
	
	Point transform(Point p) {
		double r = length_mm / (4*Math.PI); 
		double theta = 4 * Math.PI * p.x / length_mm;
		double phi = theta / 2;
		
		double y2 = p.y*Math.cos(phi) + p.z*Math.sin(phi);
		double z2 = -p.y*Math.sin(phi) + p.z*Math.cos(phi);
		
		r += z2;
		Point result = new Point(r*Math.cos(theta), r*Math.sin(theta), y2);
		return result;
	}

	public MoebiusDeformator(double length_mm, double width_mm) {
		super();
		this.length_mm = length_mm;
		this.width_mm = width_mm;
	}
}
