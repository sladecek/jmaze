package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.LeftRight;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MFace;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.FloorFace;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.TelescopicPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates vertical surfaces by extruding floor faces to proper altitude.
 */
class VerticalFaceMaker {
    public VerticalFaceMaker(Model3d m, IMaze3DMapper mapper) {
        this.m = m;
        this.mapper = mapper;
        newEdges = new ArrayList<>();
        newPoints = new ArrayList<>();
    }

    /**
     * Set maximal/minimal altitudes of a TelescopicPoint.
     */
    public void stretchTelescopicPoints() {
        for (MEdge e : m.getEdges()) {
            stretchOneEdge(e);
        }
    }

    /**
     * Make vertical edges including newly created points.
     */
    public void makeVerticalEdges() {
        for (MPoint p : m.getPoints()) {
            assert p instanceof TelescopicPoint;
            TelescopicPoint tp = (TelescopicPoint) p;
            makeVerticalEdgesOnePoint(tp);
        }
    }

    public void setTelescopicPointAltitude() {
        for (MPoint p : m.getPoints()) {
            assert p instanceof TelescopicPoint;
            TelescopicPoint tp = (TelescopicPoint) p;
            setTelescopicPointAltitudeOnePoint(tp);
        }
    }

    private void setTelescopicPointAltitudeOnePoint(TelescopicPoint tp) {
        // Max altitude defines the z coordinate of the TelescopicPoint itself.
        tp.setOwnAltitude(mapper);
    }


    private void makeVerticalEdgesOnePoint(TelescopicPoint tp) {
        MPoint upperPoint = tp;
        Altitude upperAlt = tp.getMaxAltitude();
        tp.addSection(upperAlt, tp, null);

        // loop over all altitudes of the point
        while (upperAlt.hasPrev() && upperAlt.prev().getValue() >= tp.getMinAltitude().getValue()) {
            Altitude aa = upperAlt.prev();
            MPoint p = new MPoint(tp.mapPoint(mapper, aa));
            MEdge rod = new MEdge(upperPoint, p);
            newPoints.add(p);
            newEdges.add(rod);
            tp.addSection(aa, p, rod);

            upperAlt = aa;
            upperPoint = p;
        }
    }

    public void makeVerticalFaces() {
        for (MEdge e : m.getEdges()) {
            makeVerticalFacesOneEdge(e);
        }
    }


    public void addNewObjectsBackToModel() {
        m.addEdges(newEdges);
        m.addPoints(newPoints);
    }


    /**
     * Set maximal/minimal altitudes of TelescopicPoint. Public for unit tests.
     */
    public void stretchOneEdge(MEdge e) {
        // edge endpoints
        TelescopicPoint fp1 = (TelescopicPoint) e.getP1();
        TelescopicPoint fp2 = (TelescopicPoint) e.getP2();

        // each edge separates two faces
        FloorFace leftFace = (FloorFace) e.getRightFace();
        FloorFace rightFace = (FloorFace) e.getLeftFace();


        Altitude a1 = leftFace.getAltitude();
        Altitude a2 = rightFace.getAltitude();

        fp1.stretchAltitude(a1);
        fp1.stretchAltitude(a2);
        fp2.stretchAltitude(a1);
        fp2.stretchAltitude(a2);

    }

    /**
     * Make vertical shapes induced by one edge. This method is public due to internal usage
     * by unit tests.
     */
    void makeVerticalFacesOneEdge(MEdge e) {
        // edge endpoints
        TelescopicPoint p1 = (TelescopicPoint) e.getP1();
        TelescopicPoint p2 = (TelescopicPoint) e.getP2();

        // each edge separates two faces
        FloorFace rightFace = (FloorFace) e.getRightFace();
        FloorFace leftFace = (FloorFace) e.getLeftFace();

        // compare altitude of the faces along the edge
        int a1 = rightFace.getAltitude().getValue();
        int a2 = leftFace.getAltitude().getValue();

        Altitude upperAltitude = rightFace.getAltitude();
        Altitude lowerAltitude = leftFace.getAltitude();
        MFace lowFace = leftFace;
        LeftRight lowFaceSide = LeftRight.left;
        if (a1 < a2) {
            upperAltitude = leftFace.getAltitude();
            lowerAltitude = rightFace.getAltitude();
            lowFace = rightFace;
            lowFaceSide = LeftRight.right;
        }

        // if the faces are on different altitudes, it is necessary to
        // connect them with vertical faces and connecting edges
        if (a1 != a2) {
            fixUnevenAltitudes(e, p1, p2, upperAltitude, lowerAltitude, lowFace, lowFaceSide);
        }

        // replace endpoints of the upper edge so that they are both at the proper level
        e.setP1(p1.getPointAt(upperAltitude));
        e.setP2(p2.getPointAt(upperAltitude));
    }

    private void fixUnevenAltitudes(MEdge e,
                                    TelescopicPoint p1, TelescopicPoint p2,
                                    Altitude upperAltitude, Altitude lowerAltitude,
                                    MFace lowFace, LeftRight lowFaceSide) {
        MEdge upperEdge = e;


        assert (Altitude.FRAME.getValue() < Altitude.GROUND.getValue())
                && (Altitude.GROUND.getValue() < Altitude.FLOOR.getValue())
                && (Altitude.FLOOR.getValue() < Altitude.CEILING.getValue())
                : "Altitudes must be ordered.";

        // Loop over all altitudes between two horizontal faces.
        // Fill the whole side with vertical faces - one per altitude.
        Altitude alt = upperAltitude;
        while (alt.hasPrev() && alt.prev().getValue() >= lowerAltitude.getValue()) {
            MEdge newEdge = makeVerticalLevel(upperEdge, p1, p2, lowFaceSide, alt);
            alt = alt.prev();
            upperEdge = newEdge;
        }

        // connect the new objects to the existing low altitude face
        lowFace.replaceEdge(e, upperEdge);
        upperEdge.setSideFace(lowFaceSide, lowFace);
    }

    private MEdge makeVerticalLevel(MEdge upperEdge,
                                    TelescopicPoint p1, TelescopicPoint p2,
                                    LeftRight upperEdgeSide, Altitude upperAltitude) {
        Altitude lowerAltitude = upperAltitude.prev();
        LeftRight lowerEdgeSide = upperEdgeSide.mirror();

        MFace newFace = new MFace();

        MEdge lowerEdge = new MEdge(p1.getPointAt(lowerAltitude), p2.getPointAt(lowerAltitude));
        newEdges.add(lowerEdge);

        upperEdge.setSideFace(upperEdgeSide, newFace);
        newFace.addEdge(upperEdge);

        lowerEdge.setSideFace(lowerEdgeSide, newFace);
        newFace.addEdge(lowerEdge);

        final MEdge rod1 = p1.getRodAt(lowerAltitude);
        rod1.setSideFace(lowerEdgeSide, newFace);
        newFace.addEdge(rod1);

        final MEdge rod2 = p2.getRodAt(lowerAltitude);
        rod2.setSideFace(upperEdgeSide, newFace);
        newFace.addEdge(rod2);

        // vertical face is invisible when both its altitudes are invisible
        newFace.setVisible(
                mapper.isAltitudeVisible(upperAltitude) || mapper.isAltitudeVisible(lowerAltitude)
        );

        m.addFace(newFace);
        return lowerEdge;
    }


    private final IMaze3DMapper mapper;

    private final Model3d m;

    private final List<MEdge> newEdges;
    private final List<MPoint> newPoints;
}
