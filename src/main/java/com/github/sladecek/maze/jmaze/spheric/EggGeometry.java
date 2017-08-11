package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.geometry.OrientationVector2D;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;

import java.util.ArrayList;

/**
 * Define geometry of egg-like shape.
 */
public final class EggGeometry {

    public EggGeometry(double ellipseMajorInmm, double ellipseMinorInmm, double eggCoef) {
        super();
        this.ellipseMajorInmm = ellipseMajorInmm;
        this.ellipseMinorInmm = ellipseMinorInmm;
        this.eggCoef = eggCoef;
    }

    public double getEllipseMajorInmm() {
        return ellipseMajorInmm;
    }

    public double getEllipseMinorInmm() {
        return ellipseMinorInmm;
    }

    public double getEggCoef() {
        return eggCoef;
    }

    public double computeY(double x) {
        final double tx = 1 / (1 - this.eggCoef * x / this.ellipseMajorInmm);
        final double aa = this.ellipseMajorInmm * this.ellipseMajorInmm;
        final double bb = this.ellipseMinorInmm * this.ellipseMinorInmm;
        final double yy = (1 - x * x / aa) * bb / tx;
        if (yy == Double.NaN || yy < 0) {
            return 0;
        }
        return Math.sqrt(yy);
    }

    /**
     * Divide egg meridian into steps of required size (approximate). Keep the
     * rest at pole.
     *
     * @param stepInmm   Required step size.
     * @param hemisphere Which hemisphere to divide.
     * @return ArrayList of x positions starting with the equator (x==0).
     */
    public ArrayList<Double> divideMeridian(double stepInmm, SouthNorth hemisphere) {
        final double sign = signOfHemispherePole(hemisphere);
        final double probeInmm = stepInmm / 10;
        ArrayList<Double> result = new ArrayList<>();
        double xInmm = 0;
        result.add(xInmm);

        while (xInmm < this.ellipseMajorInmm - 2 * probeInmm) {
            double angleInRad = Math.atan2(computeY(sign * (xInmm + probeInmm)) - computeY(sign * xInmm), probeInmm);
            double dxInmm = stepInmm * Math.cos(angleInRad);
            if (dxInmm <= 0 || dxInmm < xInmm / 1000000) {
                throw new InternalError("Infinite loop in egg division");
            }
            xInmm += dxInmm;
            if (xInmm >= this.ellipseMajorInmm) {
                break;
            }
            result.add(xInmm * sign);
        }

        if (result.size() < 2) {
            // maze must have at least one layer of rooms, place it
            // somewhere
            final double twoThirds = 2.0 / 3.0;
            result.add(this.ellipseMajorInmm * twoThirds * sign);
        }

        return result;
    }


    public OrientationVector2D computeNormalArrayList(double x) {
        double y = computeY(x);
        double cc = x / Math.pow(this.ellipseMajorInmm, 2);
        double ss = y / Math.pow(this.ellipseMinorInmm, 2);
        double norm = Math.sqrt(cc * cc + ss * ss);
        return new OrientationVector2D(cc / norm, ss / norm);
    }

    public double computeBaseRoomSizeInmm(int equatorCellCnt) {
        final double equatorCircumferenceInmm = ellipseMinorInmm * 2 * Math.PI;
        return equatorCircumferenceInmm / equatorCellCnt;
    }

    private int signOfHemispherePole(SouthNorth hemisphere) {
        if (hemisphere == SouthNorth.north) {
            return 1;
        } else {
            return -1;
        }
    }

    private double ellipseMajorInmm;
    private double ellipseMinorInmm;
    private double eggCoef;
}
