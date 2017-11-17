package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2DDbl;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;

import java.util.EnumMap;

/**
 * Point in the planar projection of the maze. It is created with two unmapped coordinates [x,y] and
 * then moved to the appropriate altitude or extruded into a set of vertical edges..
 * After extrusion  the TelescopicPoint obtains the coordinates of the point with the higher altitude
 * and new MPoints are created at low altitudes. After extrusion the point keeps its previous identity
 * so that it remains a member of the original edges.
 */
public class TelescopicPoint extends MPoint {

    public TelescopicPoint(double planarX, double planarY) {
        super(new Point3D(0, 0, 0));
        if (Double.isInfinite(planarX) || Double.isInfinite(planarY)) {
            throw new IllegalArgumentException("Coordinates must be finite.");
        }
        this.planar = new Point2DDbl(planarX, planarY);
    }

    @Override
    public String toString() {
        return "{" + planar.getX() + ", " + planar.getY() + "," + getCoordinate().getZ() + "} ";
    }

    public double getPlanarX() {
        return planar.getX();
    }

    public double getPlanarY() {
        return planar.getY();
    }


    public Altitude getMinAltitude() {
        return minAltitude;
    }

    public Altitude getMaxAltitude() {
        return maxAltitude;
    }

    public void setOwnAltitude(IMaze3DMapper mapper) {
        setCoordinate(mapPoint(mapper, maxAltitude));
    }

    public Point3D mapPoint(IMaze3DMapper mapper, Altitude altitude) {
        return mapper.map(planar, altitude);
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

    public MEdge getRodAt(Altitude aa) {
        return points.get(aa).rod;
    }

    public boolean hasSectionAt(Altitude altitude) {
        return points.containsKey(altitude);
    }

    private class Section {
        Section(MEdge rod, MPoint lowPoint) {
            this.rod = rod;
            this.lowPoint = lowPoint;
        }

        final MEdge rod; // upward rod, null at upper point
        final MPoint lowPoint;
    }

    private final EnumMap<Altitude, Section> points = new EnumMap<>(Altitude.class);
    private Point2DDbl planar;
    private Altitude minAltitude = Altitude.MAX;
    private Altitude maxAltitude = Altitude.MIN;

}
