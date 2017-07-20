package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MFace;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.FloorFace;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.ProjectedPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates vertical surfaces by extruding floor faces to proper altitude.
 */
public class VerticalFaceMaker {
    public VerticalFaceMaker(Model3d m) {
        this.m = m;
        newEdges = new ArrayList<>();
    }

    public void makeVerticalShapes() {
        for (MEdge e : m.getEdges()) {
            processEdge(e);
        }
    }

    public void addNewEdgesBackToModel() {
        m.addEdges(newEdges);
    }

    /**
     * Make vertical shapes induced by one edge. This method exists for internal usage
     * by unit testx.
     * @param e
     */
    void processEdge(MEdge e) {
        // edge endpoints
        ProjectedPoint fp1 = (ProjectedPoint) e.getP1();
        ProjectedPoint fp2 = (ProjectedPoint) e.getP2();

        // each edge separates two faces
        FloorFace leftFace = (FloorFace) e.getRightFace();
        FloorFace rightFace = (FloorFace) e.getLeftFace();

        // compare altitude of the faces along the edge
        int a1 = leftFace.getAltitude().getValue();
        int a2 = rightFace.getAltitude().getValue();

        // if the altitude is the same in both faces, we are done

        // if the faces are on different altitudes, it is necessary to
        // add vertical face on the low altitude
        if (a1 != a2) {
            MPoint fp1low = fp1.getLowAltitudePoint();
            MPoint fp2low = fp2.getLowAltitudePoint();

            // new horizontal edge
            MEdge lowEdge = new MEdge(fp1low, fp2low);
            MFace lowFace = leftFace;
            if (a1 > a2) {
                lowFace = rightFace;
            }
            lowFace.replaceEdge(e, lowEdge);

            // add new edge to the model
            newEdges.add(lowEdge);

            // add the new points to the model
            m.addPoint(fp1low);
            m.addPoint(fp2low);

            // create new vertical face
            MFace vf = new MFace();
            vf.addEdge(e);
            vf.addEdge(fp1.getRod());
            vf.addEdge(fp2.getRod());
            vf.addEdge(lowEdge);
            m.addFace(vf);

        }
    }

    private Model3d m;

    private List<MEdge> newEdges;
}
