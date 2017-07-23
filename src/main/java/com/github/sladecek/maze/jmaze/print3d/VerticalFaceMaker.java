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
        for (MPoint p: m.getPoints()) {
            assert p instanceof TelescopicPoint;
            TelescopicPoint tp = (TelescopicPoint)p;
            makeVerticalEdgesOnePoint(tp);
        }
        
        
    }

    private void makeVerticalEdgesOnePoint(TelescopicPoint tp) {

        // Max altitude defines the z coordinate of the TelescopicPoint itself.
        tp.setOwnAltitude(mapper);

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
        }
    }

    public void makeVerticalEdgesAndFacesShapes() {
        for (MEdge e : m.getEdges()) {
            makeVerticalEdgesAndFacesOneEdge(e);
        }
    }


    public void addNewObjectsBackToModel() {
        m.addEdges(newEdges);
        m.addPoints(newPoints);
    }



    /**
     * Set maximal/minimal altitudes of TelescopicPoint. This method exists for internal usage
     * by unit testx.
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
     * Make vertical shapes induced by one edge. This method exists for internal usage
     * by unit testx.
     *
     * @param e
     */
    void makeVerticalEdgesAndFacesOneEdge(MEdge e) {
        // edge endpoints
        TelescopicPoint fp1 = (TelescopicPoint) e.getP1();
        TelescopicPoint fp2 = (TelescopicPoint) e.getP2();

        // each edge separates two faces
        FloorFace leftFace = (FloorFace) e.getRightFace();
        FloorFace rightFace = (FloorFace) e.getLeftFace();

        // compare altitude of the faces along the edge
        int a1 = leftFace.getAltitude().getValue();
        int a2 = rightFace.getAltitude().getValue();


        // if the altitude is the same in both faces, we are done

        // if the faces are on different altitudes, it is necessary to
        // connect them with vertical faces and connecting edges
        if (a1 != a2) {
            Altitude upperAltitude = leftFace.getAltitude();
            Altitude lowerAltitude = rightFace.getAltitude();
            MFace lowFace = rightFace;

            if (a1 < a2) {
                upperAltitude = rightFace.getAltitude();
                lowerAltitude = leftFace.getAltitude();
                lowFace = leftFace;
            }


            MEdge upperEdge = e;


            // loop over all altitudes between two faces
            while (upperAltitude.hasPrev() && upperAltitude.prev().getValue() >= lowerAltitude.getValue()) {
                Altitude aa = upperAltitude.prev();
                MEdge newEdge = new MEdge(fp1.getPointAt(aa), fp2.getPointAt(aa));
                newEdges.add(newEdge);

                MFace newFace = new MFace();
                newFace.addEdge(upperEdge);
                newFace.addEdge(fp1.getRodAt(aa));
                newFace.addEdge(newEdge);
                newFace.addEdge(fp2.getRodAt(aa));
                m.addFace(newFace);

                upperAltitude = aa;
                upperEdge = newEdge;
            }

            lowFace.replaceEdge(e, upperEdge);

        }
    }


    private final IMaze3DMapper mapper;

    private Model3d m;

    private List<MEdge> newEdges;
    private List<MPoint> newPoints;
}
