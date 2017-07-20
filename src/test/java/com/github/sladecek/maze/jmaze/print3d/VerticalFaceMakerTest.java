package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.print3d.VerticalFaceMaker;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.FloorFace;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.ProjectedPoint;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for VerticalFaceMaker.
 */
public class VerticalFaceMakerTest {

    @Before
    public void setUp() throws Exception {
        pCenter = new ProjectedPoint(1, 1);
        pWest = new ProjectedPoint(0, 1);
        pEast = new ProjectedPoint(2, 1);
        pSouth = new ProjectedPoint(1, 0);
        pNorth = new ProjectedPoint(1, 2);
    }

    @Test
    public void checkUntouchedModel() {
        assertEquals(5, model.getPoints().size());
        assertEquals(7, model.getEdges().size());
        assertEquals(3, model.getFaces().size());

    }

    @Test
    public void sameLevel() throws Exception {
        prepareModel(Altitude.FLOOR, Altitude.FLOOR, Altitude.FLOOR);
        maker = new VerticalFaceMaker(model);
        // process the central edges only to insulate tested algorithm. Otherwise the frame
        // edges would be extruded to.
        maker.processEdge(eCE);
        maker.processEdge(eCN);
        maker.processEdge(eCS);
        maker.processEdge(eCW);
        maker.addNewEdgesBackToModel();

        // no change in element count
        assertEquals(5, model.getPoints().size());
        assertEquals(7, model.getEdges().size());
        assertEquals(3, model.getFaces().size());

    }

    private void prepareModel(Altitude an, Altitude as, Altitude ae) {
        model = new Model3d();
        model.addPoint(pCenter);
        model.addPoint(pEast);
        model.addPoint(pWest);
        model.addPoint(pSouth);
        model.addPoint(pNorth);

        eCW = new MEdge(pCenter, pWest);
        eCE = new MEdge(pCenter, pEast);
        eCS = new MEdge(pCenter, pSouth);
        eCN = new MEdge(pCenter, pNorth);

        eWS = new MEdge(pWest, pSouth);
        eWN = new MEdge(pWest, pNorth);
        eSE = new MEdge(pSouth, pEast);

        model.addEdge(eCW);
        model.addEdge(eCE);
        model.addEdge(eCS);
        model.addEdge(eCN);
        model.addEdge(eWS);
        model.addEdge(eWN);
        model.addEdge(eSE);

        fNorth = new FloorFace();
        fNorth.addEdge(eCW);
        fNorth.addEdge(eCN);
        fNorth.addEdge(eWN);
        fNorth.setAltitude(an);

        fSouth = new FloorFace();
        fSouth.addEdge(eCW);
        fSouth.addEdge(eCS);
        fSouth.addEdge(eWS);
        fSouth.setAltitude(as);

        fEast = new FloorFace();
        fEast.addEdge(eCE);
        fEast.addEdge(eCS);
        fEast.addEdge(eSE);
        fEast.setAltitude(ae);


        model.addFace(fNorth);
        model.addFace(fSouth);
        model.addFace(fEast);

    }

    Model3d model;

    ProjectedPoint pCenter;
    ProjectedPoint pWest;
    ProjectedPoint pEast;
    ProjectedPoint pSouth;
    ProjectedPoint pNorth;

    MEdge eCW;
    MEdge eCE;
    MEdge eCS;
    MEdge eCN;

    MEdge eWS;
    MEdge eWN;
    MEdge eSE;


    FloorFace fNorth;
    FloorFace fSouth;
    FloorFace fEast;

    VerticalFaceMaker maker;

}