package com.github.sladecek.maze.jmaze;

public class MoebiusDeformator {
	
	double length_mm;
	double width_mm;
	
	Point transform(Point p) {
		return p;
	}

	public MoebiusDeformator(double length_mm, double width_mm) {
		super();
		this.length_mm = length_mm;
		this.width_mm = width_mm;
	}
}
