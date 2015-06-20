package com.github.sladecek.maze.jmaze.spheric;

import java.util.Vector;

import com.github.sladecek.maze.jmaze.geometry.OrientationVector2D;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;

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
		final double tx = 1 / (1-this.eggCoef*x);
		final double aa = this.ellipseMajor_mm * this.ellipseMajor_mm;
		final double bb = this.ellipseMinor_mm * this.ellipseMinor_mm;
		final double yy = (1-x*x/aa)*bb/tx;
		if (yy == Double.NaN || yy < 0) {
			return 0;
		}
		return Math.sqrt(yy);
	}

	/**
	 * Divide egg meridian into steps of required size (approximate). Keep the rest at pole.
	 *  
	 * @param step_mm Required step size. 
	 * @param hemisphere Which hemisphere to divide.
	 * @return Vector of x positions starting with the equator (x==0).
	 */
	public Vector<Double> divideMeridian(double step_mm, SouthNorth hemisphere) {

		
		final double d = hemisphere  == SouthNorth.north ? 1 : -1;
		final double probe_mm = step_mm / 10;
		Vector<Double> result = new Vector<Double>();
		double x_mm = 0;
		result.add(x_mm);
		
		while(x_mm < this.ellipseMajor_mm - 2*probe_mm)
		{
			double angle_rad = Math.atan2(computeY(d*(x_mm+probe_mm)) - computeY(d*x_mm), probe_mm);
			double dx_mm = step_mm * Math.cos(angle_rad);
			x_mm += dx_mm;
			if (x_mm >= this.ellipseMajor_mm) {
				break;
			}
			result.add(x_mm*d);
		}		
		return result;
	}
	
	public OrientationVector2D computeNormalVector(double x) {
		double y = computeY(x);
		double cc = x / Math.pow(this.ellipseMajor_mm,2);
		double ss = y / Math.pow(this.ellipseMinor_mm,2);
		double norm = Math.sqrt(cc*cc+ss*ss);
		return new OrientationVector2D(cc/norm, ss/norm);
	}
	
	public double computeBaseRoomSize_mm(int equatorCellCnt) {
		final double equatorCircumference_mm = ellipseMinor_mm * 2 * Math.PI;
		return equatorCircumference_mm / equatorCellCnt;
	}
}
