package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;

import java.util.EnumMap;


/**
 * Point in the planar projection of the maze. It is created with two unmapped coordinates [x,y] and
 * then moved to the appropriate altitude or extruded into a vertical edge reaching two altitudes.
 * After extrusion the TelescopicPoint obtains the coordinates of the point with the higher altitude
 * and a new MPoint is created at the low altitude. After extrusion the point keeps its previous identity
 * so that it remains a member of the original edges.
 */
public class TelescopicPoint extends MPoint {
    public TelescopicPoint(double planarX, double planarY) {
        super(new Point3D(0, 0, 0));
        this.isExtruded = false;
        this.planarX = planarX;
        this.planarY = planarY;
    }

    @Override
    public String toString() {
        return "{" + planarX + ", " + planarY + "," + getCoord().getZ() + "} ";
    }

    public void setAltitudesUsingMapper(IMaze3DMapper mapper, int lowAltitude, int highAltitude) {
        if (areAltitudesDefined()) {
            // The point has been extruded already and the coordinates match.
            if ((lowAltitude == this.lowAltitude) && (highAltitude == this.highAltitude)) {
                return;
            }

            if (lowAltitude == highAltitude && this.highAltitude == highAltitude) {
                return;
            }

            // The point has been  extruded
            if (this.lowAltitude != this.highAltitude) {
                return;
                //TODO throw new IllegalStateException("Cannot setAltitudesUsingMapper a point " + this + " twice.");
            }
            // else reset point altitudes

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

    public boolean areAltitudesDefined() {
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

    public Altitude getMinAltitude() {
        return minAltitude;
    }

    public Altitude getMaxAltitude() {
        return maxAltitude;
    }

    public void setOwnAltitude(IMaze3DMapper mapper) {
        setCoord(mapPoint(mapper, maxAltitude));
    }

    public Point3D mapPoint(IMaze3DMapper mapper, Altitude altitude) {
        return mapPoint(mapper, altitude.getValue());
    }

    public Point3D mapPoint(IMaze3DMapper mapper, int altitude) {
        return mapper.map(new Point3D(planarX, planarY, altitude));
    }

    public void stretchAltitude(Altitude a) {
        if (a.getValue() < minAltitude.getValue()) {
            minAltitude = a;
        }
        if (a.getValue() > maxAltitude.getValue()) {
            maxAltitude = a;
        }
    }

    public void addSection(Altitude aa, MPoint p, MEdge rod) {
        assert !points.containsKey(aa);
        points.put(aa, new Section(rod, p));
    }

    public MPoint getPointAt(Altitude aa) {
        return points.get(aa).lowPoint;
    }

    public MEdge getRodAt(Altitude aa)
    {
        return points.get(aa).rod;
    }

    public boolean hasSectionAt(Altitude altitude) {
        return points.containsKey(altitude);
    }

    private class Section {
        public Section(MEdge rod, MPoint lowPoint) {
            this.rod = rod;
            this.lowPoint = lowPoint;
        }

        MEdge rod; // upward rod, null at upper point
        MPoint lowPoint;
    }
    private double planarX;
    private double planarY;
    private int lowAltitude;  // TODO smazat
    private int highAltitude; // TODO smazat
    private MEdge rod;
    private boolean isExtruded;

    private Altitude minAltitude = Altitude.MAX;
    private Altitude maxAltitude = Altitude.MIN;

    ;
    private EnumMap<Altitude, Section> points = new EnumMap<Altitude, Section>(Altitude.class);

}
