package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;


/**
 * Point in the planar projection of the maze. It is created with two unmapped coordinates [x,y] and
 * then moved to the appropriate altitude or extruded into a vertical edge reaching two altitudes.
 * After extrusion the ProjectedPoint obtains the coordinates of the point with the higher altitude
 * and a new MPoint is created at the low altitude. After extrusion the point keeps the identity
 * so that it remains a member of the original edges.
 */
public class ProjectedPoint extends MPoint {
    public ProjectedPoint(double planarX, double planarY) {
        super(new Point3D(0, 0, 0));
        this.isExtruded = false;
        this.planarX = planarX;
        this.planarY = planarY;
    }

    public void setAltitudes(IMaze3DMapper mapper, int lowAltitude, int highAltitude) {
        if (isExtruded()) {
            // when the point has been extruded already, only validate the altitudes
            if ((lowAltitude == this.lowAltitude) && (highAltitude == this.highAltitude)) {
                return;
            }
            throw new IllegalStateException("Cannot setAltitudes a point " + this + " twice.");

        }
        if (lowAltitude > highAltitude) {
            throw new IllegalArgumentException(" lowAltitude must not be larger than highAltitude");
        }
        isExtruded = true;
        this.lowAltitude = lowAltitude;
        this.highAltitude = highAltitude;

        setCoord(mapPoint(mapper, highAltitude));

        if (lowAltitude != highAltitude) {
            // extrude
            rod = new MEdge(this, new MPoint(mapPoint(mapper, lowAltitude)));
        }
    }


    public MPoint getOppositeRodPoint() {
        if (rod == null) {
            return this;
        }
        return rod.getP2();
    }

    public MPoint getLowAltitudePoint() {
        if (rod == null) {
            return this;
        }
        return rod.getP2();
    }

    public MPoint getHighAltitudePoint() {
        if (rod == null) {
            return this;
        }
        return rod.getP1();
    }

    public MEdge getRod() {
        return rod;
    }

    public boolean isExtruded() {
        return isExtruded;
    }

    public int getLowAltitude() {
        return lowAltitude;
    }

    public double getPlanarX() {
        return planarX;
    }

    public double getPlanarY() {
        return planarY;
    }

    public int getHighAltitude() {
        return highAltitude;
    }


    public Point3D mapPoint(IMaze3DMapper mapper, int altitude) {
        return mapper.map(new Point3D(planarX, planarY, altitude));
    }

    private double planarX;
    private double planarY;
    private int lowAltitude;
    private int highAltitude;
    private MEdge rod;
    private boolean isExtruded;

}
