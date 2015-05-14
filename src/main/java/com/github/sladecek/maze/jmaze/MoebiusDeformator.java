package com.github.sladecek.maze.jmaze;

public class MoebiusDeformator {
	
	double length_mm;
	double width_mm;
	
	Point transform(Point p) {
		double r = length_mm / (Math.PI); 
		double theta = 4 * Math.PI * p.x / length_mm;
		r += p.z;
		Point result = new Point(r*Math.cos(theta), p.y, r*Math.sin(theta));
		return result;
	}

	public MoebiusDeformator(double length_mm, double width_mm) {
		super();
		this.length_mm = length_mm;
		this.width_mm = width_mm;
	}
}
