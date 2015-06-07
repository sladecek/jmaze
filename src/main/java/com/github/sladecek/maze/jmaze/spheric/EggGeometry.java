package com.github.sladecek.maze.jmaze.spheric;

import java.util.Vector;

/**
 * 
 * Define geometry of egg-like shape.
 *
 */
public class EggGeometry {


	private double ellipseMajor_mm;
	private double ellipseMinor_mm;
	private double eggCoef;
	
	public EggGeometry(double ellipseMajor_mm, double ellipseMinor_mm, double eggCoef) {
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
	public double computeY(double x) {
		// TODO Auto-generated method stub
		return 0;
	}
	public double getBaseRoomSize_mm() {
		// TODO Auto-generated method stub
		return 0;
	}
	public double findNextX(double x, double d) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getCircumferenceAt_mm(double x) {
		// TODO Auto-generated method stub
		return 0;
	}
	public Vector<Double> divideMeridianEquidistantly(double baseRoomSize_mm,
			int dix) {
		// TODO Auto-generated method stub
		return null;
	}
}
