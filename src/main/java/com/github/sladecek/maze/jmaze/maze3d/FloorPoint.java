package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.model3d.MEdge;
import com.github.sladecek.maze.jmaze.model3d.MPoint;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;


/**
 * Point in the floor plane. It is created with two unmapped coordinates [x,y] and
 * then moved to the appropriate altitude or extruded into a vertical edge reaching two altitudes.
 * After extrusion the FloorPoint obtains the coordinates of the point with the higher altitude
 * and a new MPoint is created at the low altitude. After extrusion the point keeps the identity
 * so that it remains a member of the original edges.
 */
public class FloorPoint extends MPoint {
    public FloorPoint(double planarX, double planarY) {
        super(new Point3D(0, 0, 0));
        this.planarX = planarX;
        this.planarY = planarY;
    }

    public void setAltitudes(IMaze3DMapper mapper, int lowAltitude, int highAltitude) {
        if (isExtruded()) {
            // when the point is extruded, only validate the altitudes
            if ((lowAltitude == this.lowAltitude) && (highAltitude == this.highAltitude)) {
                return;
            }
            throw new IllegalStateException("Cannot setAltitudes a point " + this + " twice.");

        }
        if (lowAltitude == highAltitude) {
            throw new IllegalArgumentException(" lowAltitude must not bet larger than highAltitude");
        }
        isExtruded = true;
        this.lowAltitude = lowAltitude;
        this.highAltitude = highAltitude;

        setCoord(mapper.map(mapPoint(highAltitude)));

        if (lowAltitude != highAltitude) {
            // extrude
            rod = new MEdge(this, new MPoint(mapPoint(highAltitude)));
        }
    }


    public MPoint getOppositeRodPoint() {
        return rod.getP2();
    }

    public MPoint getLowAltitudePoint() {
        return rod.getP2();
    }

    public MPoint getHighAltitudePoint() {
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

    public int getHighAltitude() {
        return highAltitude;
    }


    private Point3D mapPoint(int altitude) {
        return new Point3D(planarX, planarY, altitude);
    }


    private double planarX;
    private double planarY;
    private int lowAltitude;
    private int highAltitude;
    private MEdge rod;
    private boolean isExtruded = false;


}
