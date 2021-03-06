package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MFace;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.FloorFace;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.TelescopicPoint;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for VerticalFaceMaker.
 */
@SuppressWarnings({"FieldCanBeLocal", "SameParameterValue"})
public class VerticalFaceMakerTest {

    @Before
    public void setUp() throws Exception {
        pCenter = new TelescopicPoint(1, 1);
        pWest = new TelescopicPoint(0, 1);
        pEast = new TelescopicPoint(2, 1);
        pSouth = new TelescopicPoint(1, 0);
        pNorth = new TelescopicPoint(1, 2);
    }

    private void prepareModel(Altitude anw, Altitude ane, Altitude ase, Altitude asw) {
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
        eNE = new MEdge(pNorth, pEast);

        model.addEdge(eCW);
        model.addEdge(eCE);
        model.addEdge(eNE);
        model.addEdge(eCS);
        model.addEdge(eCN);
        model.addEdge(eWS);
        model.addEdge(eWN);
        model.addEdge(eSE);

        fNW = new FloorFace();
        fNW.addEdge(eCW);
        fNW.addEdge(eCN);
        fNW.addEdge(eWN);
        fNW.setAltitude(anw);

        fSW = new FloorFace();
        fSW.addEdge(eCW);
        fSW.addEdge(eCS);
        fSW.addEdge(eWS);
        fSW.setAltitude(asw);

        fSE = new FloorFace();
        fSE.addEdge(eCE);
        fSE.addEdge(eCS);
        fSE.addEdge(eSE);
        fSE.setAltitude(ase);

        fNE = new FloorFace();
        fNE.addEdge(eCE);
        fNE.addEdge(eCN);
        fNE.addEdge(eNE);
        fNE.setAltitude(ane);

        model.addFace(fNW);
        model.addFace(fSW);
        model.addFace(fSE);
        model.addFace(fNE);

        eCN.setLeftFace(fNW);
        eCN.setRightFace(fNE);

        eCW.setLeftFace(fSW);
        eCW.setRightFace(fNW);

        eCS.setLeftFace(fSE);
        eCS.setRightFace(fSW);

        eCE.setLeftFace(fSE);
        eCE.setRightFace(fNE);
    }


    @Test
    public void checkUntouchedModel() {
        prepareModel(Altitude.FLOOR, Altitude.FLOOR, Altitude.FLOOR, Altitude.FLOOR);
        assertEquals(5, model.getPoints().size());
        assertEquals(8, model.getEdges().size());
        assertEquals(4, model.getFaces().size());

    }

    @Test
    public void sameLevel() throws Exception {
        prepareModel(Altitude.FLOOR, Altitude.FLOOR, Altitude.FLOOR, Altitude.FLOOR);
        maker = new VerticalFaceMaker(model, mapper);
        // process the central edges only, to insulate tested algorithm. Otherwise the frame
        // edges would be extruded too.
        MEdge[] myEdges = {eCE, eCN, eCS, eCW};
        for (MEdge e : myEdges) maker.stretchOneEdge(e);
        maker.setTelescopicPointAltitude();
        maker.makeVerticalEdges();
        for (MEdge e : myEdges) maker.makeVerticalFacesOneEdge(e);
        maker.addNewObjectsBackToModel();

        // no change in element count
        assertEquals(5, model.getPoints().size());
        assertEquals(8, model.getEdges().size());
        assertEquals(4, model.getFaces().size());

    }

    @Test
    public void twoLevels() throws Exception {

        prepareModel(Altitude.CEILING, Altitude.FLOOR, Altitude.FLOOR, Altitude.FLOOR);

        maker = new VerticalFaceMaker(model, mapper);
        // process the central edges only to insulate tested algorithm. Otherwise the frame
        // edges would be extruded to.
        MEdge[] myEdges = {eCE, eCN, eCS, eCW};
        for (MEdge e : myEdges) maker.stretchOneEdge(e);

        assertEquals(Altitude.CEILING, pCenter.getMaxAltitude());
        assertEquals(Altitude.CEILING, pNorth.getMaxAltitude());
        assertEquals(Altitude.CEILING, pWest.getMaxAltitude());
        assertEquals(Altitude.FLOOR, pSouth.getMaxAltitude());
        assertEquals(Altitude.FLOOR, pEast.getMaxAltitude());

        assertEquals(Altitude.FLOOR, pCenter.getMinAltitude());
        assertEquals(Altitude.FLOOR, pNorth.getMinAltitude());
        assertEquals(Altitude.FLOOR, pSouth.getMinAltitude());
        assertEquals(Altitude.FLOOR, pEast.getMinAltitude());
        assertEquals(Altitude.FLOOR, pWest.getMinAltitude());

        maker.setTelescopicPointAltitude();
        maker.makeVerticalEdges();

        final double epsilon = 1e-8;
        assertEquals(2, pCenter.getCoordinate().getZ(), epsilon);
        assertEquals(2, pNorth.getCoordinate().getZ(), epsilon);
        assertEquals(2, pWest.getCoordinate().getZ(), epsilon);
        assertEquals(1, pEast.getCoordinate().getZ(), epsilon);
        assertEquals(1, pSouth.getCoordinate().getZ(), epsilon);

        assertTrue(pCenter.hasSectionAt(Altitude.CEILING));
        assertTrue(pCenter.hasSectionAt(Altitude.FLOOR));
        assertFalse(pCenter.hasSectionAt(Altitude.GROUND));
        assertFalse(pCenter.hasSectionAt(Altitude.FRAME));

        assertTrue(pNorth.hasSectionAt(Altitude.CEILING));
        assertTrue(pNorth.hasSectionAt(Altitude.FLOOR));
        assertFalse(pNorth.hasSectionAt(Altitude.GROUND));
        assertFalse(pNorth.hasSectionAt(Altitude.FRAME));

        assertFalse(pSouth.hasSectionAt(Altitude.CEILING));
        assertTrue(pSouth.hasSectionAt(Altitude.FLOOR));
        assertFalse(pSouth.hasSectionAt(Altitude.GROUND));
        assertFalse(pSouth.hasSectionAt(Altitude.FRAME));

        assertNotNull(pCenter.getPointAt(Altitude.CEILING));
        assertNotNull(pCenter.getPointAt(Altitude.FLOOR));
        assertNull(pCenter.getRodAt(Altitude.CEILING));
        assertNotNull(pCenter.getRodAt(Altitude.FLOOR));

        assertEquals(pCenter, pCenter.getPointAt(Altitude.CEILING));
        assertEquals(pCenter, pCenter.getRodAt(Altitude.FLOOR).getP1());
        assertEquals(pCenter.getPointAt(Altitude.FLOOR), pCenter.getRodAt(Altitude.FLOOR).getP2());

        assertNotNull(pWest.getPointAt(Altitude.CEILING));
        assertNotNull(pWest.getPointAt(Altitude.FLOOR));
        assertNull(pWest.getRodAt(Altitude.CEILING));
        assertNotNull(pWest.getRodAt(Altitude.FLOOR));

        assertEquals(pWest, pWest.getPointAt(Altitude.CEILING));
        assertEquals(pWest, pWest.getRodAt(Altitude.FLOOR).getP1());
        assertEquals(pWest.getPointAt(Altitude.FLOOR), pWest.getRodAt(Altitude.FLOOR).getP2());


        for (MEdge e : myEdges) maker.makeVerticalFacesOneEdge(e);
        maker.addNewObjectsBackToModel();

        assertEquals(5 + 3, model.getPoints().size());
        assertEquals(8 + 3 + 2, model.getEdges().size());
        assertEquals(4 + 2, model.getFaces().size());

        assertEquals(2, pCenter.getCoordinate().getZ(), epsilon);
        assertEquals(2, pNorth.getCoordinate().getZ(), epsilon);
        assertEquals(2, pWest.getCoordinate().getZ(), epsilon);
        assertEquals(1, pEast.getCoordinate().getZ(), epsilon);
        assertEquals(1, pSouth.getCoordinate().getZ(), epsilon);

        assertEquals(1, eCE.getP1().getCoordinate().getZ(), epsilon);
        assertEquals(1, eCE.getP2().getCoordinate().getZ(), epsilon);
        assertEquals(1, eCS.getP1().getCoordinate().getZ(), epsilon);
        assertEquals(1, eCS.getP2().getCoordinate().getZ(), epsilon);

        assertEquals(2, eCN.getP1().getCoordinate().getZ(), epsilon);
        assertEquals(2, eCN.getP2().getCoordinate().getZ(), epsilon);
        assertEquals(2, eCW.getP1().getCoordinate().getZ(), epsilon);
        assertEquals(2, eCW.getP2().getCoordinate().getZ(), epsilon);

        MFace newFace1 = (MFace)(model.getFaces().toArray()[4]);
        assertEquals(eCN, newFace1.getEdges().get(0));
        assertEquals(newFace1, newFace1.getEdges().get(0).getRightFace());
        assertEquals(newFace1, newFace1.getEdges().get(1).getLeftFace());
        assertEquals(newFace1, newFace1.getEdges().get(2).getLeftFace());
        assertEquals(newFace1, newFace1.getEdges().get(3).getRightFace());

        ArrayList<MPoint> newFace1Points = newFace1.visitPointsCounterclockwise();
        assertEquals(4, newFace1Points.size());
        // TODO points must be visited CCW


        MFace newFace2 = (MFace)(model.getFaces().toArray()[5]);
        assertEquals(eCW, newFace2.getEdges().get(0));
        assertEquals(newFace2, newFace2.getEdges().get(0).getLeftFace());
        assertEquals(newFace2, newFace2.getEdges().get(1).getRightFace());
        assertEquals(newFace2, newFace2.getEdges().get(2).getRightFace());
        assertEquals(newFace2, newFace2.getEdges().get(3).getLeftFace());


        ArrayList<MPoint> newFacePoints2 = newFace1.visitPointsCounterclockwise();
        assertEquals(4, newFacePoints2.size());
        // TODO points must be visited CCW

    }


    @Test
    public void allLevels() throws Exception {

        prepareModel(Altitude.CEILING, Altitude.GROUND, Altitude.FLOOR, Altitude.FRAME);

        maker = new VerticalFaceMaker(model, mapper);
        // process the central edges only to insulate tested algorithm. Otherwise the frame
        // edges would be extruded to.
        // process the central edges only, to insulate tested algorithm. Otherwise the frame
        // edges would be extruded too.
        MEdge[] myEdges = {eCE, eCN, eCS, eCW};
        for (MEdge e : myEdges) maker.stretchOneEdge(e);

        assertEquals(Altitude.CEILING, pCenter.getMaxAltitude());
        assertEquals(Altitude.CEILING, pNorth.getMaxAltitude());
        assertEquals(Altitude.FLOOR, pEast.getMaxAltitude());
        assertEquals(Altitude.FLOOR, pSouth.getMaxAltitude());
        assertEquals(Altitude.CEILING, pWest.getMaxAltitude());

        assertEquals(Altitude.FRAME, pCenter.getMinAltitude());
        assertEquals(Altitude.GROUND, pNorth.getMinAltitude());
        assertEquals(Altitude.GROUND, pEast.getMinAltitude());
        assertEquals(Altitude.FRAME, pSouth.getMinAltitude());
        assertEquals(Altitude.FRAME, pWest.getMinAltitude());

        maker.setTelescopicPointAltitude();
        maker.makeVerticalEdges();

        final double epsilon = 1e-8;
        assertEquals(2, pCenter.getCoordinate().getZ(), epsilon);
        assertEquals(2, pNorth.getCoordinate().getZ(), epsilon);
        assertEquals(1, pEast.getCoordinate().getZ(), epsilon);
        assertEquals(1, pSouth.getCoordinate().getZ(), epsilon);
        assertEquals(2, pWest.getCoordinate().getZ(), epsilon);

        assertTrue(pCenter.hasSectionAt(Altitude.CEILING));
        assertTrue(pCenter.hasSectionAt(Altitude.FLOOR));
        assertTrue(pCenter.hasSectionAt(Altitude.GROUND));
        assertTrue(pCenter.hasSectionAt(Altitude.FRAME));

        assertTrue(pNorth.hasSectionAt(Altitude.CEILING));
        assertTrue(pNorth.hasSectionAt(Altitude.FLOOR));
        assertTrue(pNorth.hasSectionAt(Altitude.GROUND));
        assertFalse(pNorth.hasSectionAt(Altitude.FRAME));

        assertFalse(pEast.hasSectionAt(Altitude.CEILING));
        assertTrue(pEast.hasSectionAt(Altitude.FLOOR));
        assertTrue(pEast.hasSectionAt(Altitude.GROUND));
        assertFalse(pEast.hasSectionAt(Altitude.FRAME));

        assertFalse(pSouth.hasSectionAt(Altitude.CEILING));
        assertTrue(pSouth.hasSectionAt(Altitude.FLOOR));
        assertTrue(pSouth.hasSectionAt(Altitude.GROUND));
        assertTrue(pSouth.hasSectionAt(Altitude.FRAME));

        assertTrue(pWest.hasSectionAt(Altitude.CEILING));
        assertTrue(pWest.hasSectionAt(Altitude.FLOOR));
        assertTrue(pWest.hasSectionAt(Altitude.GROUND));
        assertTrue(pWest.hasSectionAt(Altitude.FRAME));

        assertNotNull(pCenter.getPointAt(Altitude.CEILING));
        assertNotNull(pCenter.getPointAt(Altitude.FLOOR));
        assertNotNull(pCenter.getPointAt(Altitude.GROUND));
        assertNotNull(pCenter.getPointAt(Altitude.FRAME));
        assertNull(pCenter.getRodAt(Altitude.CEILING));
        assertNotNull(pCenter.getRodAt(Altitude.FLOOR));
        assertNotNull(pCenter.getRodAt(Altitude.GROUND));
        assertNotNull(pCenter.getRodAt(Altitude.FRAME));

        assertEquals(pCenter, pCenter.getPointAt(Altitude.CEILING));
        assertEquals(pCenter, pCenter.getRodAt(Altitude.FLOOR).getP1());
        assertEquals(pCenter.getPointAt(Altitude.FLOOR), pCenter.getRodAt(Altitude.FLOOR).getP2());
        assertEquals(pCenter.getPointAt(Altitude.GROUND), pCenter.getRodAt(Altitude.GROUND).getP2());
        assertEquals(pCenter, pCenter.getRodAt(Altitude.FLOOR).getP1());
        assertEquals(pCenter.getPointAt(Altitude.FLOOR), pCenter.getRodAt(Altitude.FLOOR).getP2());


        for (MEdge e : myEdges) maker.makeVerticalFacesOneEdge(e);
        maker.addNewObjectsBackToModel();

        assertEquals(5 + 3 + 3 + 2 + 1 + 2, model.getPoints().size());
        assertEquals(8 + (3 + 3 + 2 + 1 + 2) + (3 + 2 + 1 + 2), model.getEdges().size());
        assertEquals(4 + (3+2+1+2), model.getFaces().size());

        assertEquals(2, pCenter.getCoordinate().getZ(), epsilon);
        assertEquals(2, pNorth.getCoordinate().getZ(), epsilon);
        assertEquals(2, pWest.getCoordinate().getZ(), epsilon);
        assertEquals(1, pEast.getCoordinate().getZ(), epsilon);
        assertEquals(1, pSouth.getCoordinate().getZ(), epsilon);

        assertEquals(1, eCE.getP1().getCoordinate().getZ(), epsilon);
        assertEquals(1, eCE.getP2().getCoordinate().getZ(), epsilon);
        assertEquals(1, eCS.getP1().getCoordinate().getZ(), epsilon);
        assertEquals(1, eCS.getP2().getCoordinate().getZ(), epsilon);

        assertEquals(2, eCN.getP1().getCoordinate().getZ(), epsilon);
        assertEquals(2, eCN.getP2().getCoordinate().getZ(), epsilon);
        assertEquals(2, eCW.getP1().getCoordinate().getZ(), epsilon);
        assertEquals(2, eCW.getP2().getCoordinate().getZ(), epsilon);


    }




    private final IMaze3DMapper mapper = new PlanarMapper();
    private Model3d model;

    private TelescopicPoint pCenter;
    private TelescopicPoint pWest;
    private TelescopicPoint pEast;
    private TelescopicPoint pSouth;
    private TelescopicPoint pNorth;

    private MEdge eCW;
    private MEdge eCE;
    private MEdge eNE;
    private MEdge eCS;
    private MEdge eCN;

    private MEdge eWS;
    private MEdge eWN;
    private MEdge eSE;


    private FloorFace fNW;
    private FloorFace fSW;
    private FloorFace fSE;
    private FloorFace fNE;

    private VerticalFaceMaker maker;

}