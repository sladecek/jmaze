package com.github.sladecek.maze.jmaze.spheric;

/**
 * 
 * Define geometry of egg-like shape.
 *
 */
public class Egg {


	private double ellipseMajor_mm;
	private double ellipseMinor_mm;
	private double eggCoef;
	
	public Egg(double ellipseMajor_mm, double ellipseMinor_mm, double eggCoef) {
		super();
		this.ellipseMajor_mm = ellipseMajor_mm;
		this.ellipseMinor_mm = ellipseMinor_mm;
		this.eggCoef = eggCoef;
	}
	public double getEllipseMajor_mm() {
		return ellipseMajor_mm;
	}
	public double getEllipseMinor_mm() {
		return ellipseMinor_mm;
	}
	public double getEggCoef() {
		return eggCoef;
	}
}
