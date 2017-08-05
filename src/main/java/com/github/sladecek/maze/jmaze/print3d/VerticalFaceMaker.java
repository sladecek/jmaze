package com.github.sladecek.maze.jmaze.print3d;

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
public class VerticalFaceMaker {
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
     *
     * @param e
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
     * by unit testx.
     *
     * @param e
     */
    void makeVerticalFacesOneEdge(MEdge e) {


        // edge endpoints
        TelescopicPoint fp1 = (TelescopicPoint) e.getP1();
        TelescopicPoint fp2 = (TelescopicPoint) e.getP2();

        // each edge separates two faces
        FloorFace rightFace = (FloorFace) e.getRightFace();
        FloorFace leftFace = (FloorFace) e.getLeftFace();

        // compare altitude of the faces along the edge
        int a1 = rightFace.getAltitude().getValue();
        int a2 = leftFace.getAltitude().getValue();

        Altitude upperAltitude = rightFace.getAltitude();
        Altitude lowerAltitude = leftFace.getAltitude();
        MFace lowFace = leftFace;

        if (a1 < a2) {
            upperAltitude = leftFace.getAltitude();
            lowerAltitude = rightFace.getAltitude();
            lowFace = rightFace;
        }

        // if the faces are on different altitudes, it is necessary to
        // connect them with vertical faces and connecting edges
        if (a1 != a2) {
            fixUnevenAltitudes(e, fp1, fp2, upperAltitude, lowerAltitude, lowFace);
        }

        // replace endpoints of the upper edge so that they are both at the proper level
        e.setP1(fp1.getPointAt(upperAltitude));
        e.setP2(fp2.getPointAt(upperAltitude));
    }

    private void fixUnevenAltitudes(MEdge e, TelescopicPoint fp1, TelescopicPoint fp2,
                                    Altitude upperAltitude, Altitude lowerAltitude, MFace lowFace) {
        MEdge upperEdge = e;

        // loop over all altitudes between two faces
        Altitude alt = upperAltitude;
        while (alt.hasPrev() && alt.prev().getValue() >= lowerAltitude.getValue()) {
            Altitude oneBelow = alt.prev();
            MEdge newEdge = new MEdge(fp1.getPointAt(oneBelow), fp2.getPointAt(oneBelow));
            newEdges.add(newEdge);

            MFace newFace = new MFace();
            newFace.addEdge(upperEdge);
            newFace.addEdge(fp1.getRodAt(oneBelow));
            newFace.addEdge(newEdge);
            newFace.addEdge(fp2.getRodAt(oneBelow));
            // vertical face is invisible when both its altitudes are invisible
            newFace.setVisible(
                    mapper.isAltitudeVisible(alt) || mapper.isAltitudeVisible(oneBelow)
            );
            m.addFace(newFace);


            alt = oneBelow;
            upperEdge = newEdge;
        }

        lowFace.replaceEdge(e, upperEdge);
    }


        private final IMaze3DMapper mapper;

    private Model3d m;

    private List<MEdge> newEdges;
    private List<MPoint> newPoints;
}
