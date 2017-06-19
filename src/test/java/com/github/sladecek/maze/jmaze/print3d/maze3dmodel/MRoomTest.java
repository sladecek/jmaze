package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.shapes.WallShape;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for MRoom class.
 */
public class MRoomTest {
    @Test
    public void finishEdges() throws Exception {
        final int floorId = 27;

        // Pillar centers
        Point2D pp1 = new Point2D(1,1);
        Point2D pp2 = new Point2D(2,2);
        Point2D pp3 = new Point2D(3,3);

        // Walls connecting the pillars
        WallShape ws12 = WallShape.newInnerWall(12, pp1, pp2);
        WallShape ws23 = WallShape.newInnerWall(23, pp2, pp3);
        WallShape ws31 = WallShape.newInnerWall(31, pp3, pp1);

        // Corresponding floor faces
        MWall mw12 = newMWallWithSomeEdges(100);
        MWall mw23 = newMWallWithSomeEdges(200);
        MWall mw31 = newMWallWithSomeEdges(300);

        // Each wall has two ends
        WallEnd we12 = new WallEnd(mw12, ws12, true);
        WallEnd we21 = new WallEnd(mw12, ws12, false);

        WallEnd we23 = new WallEnd(mw23, ws23, true);
        WallEnd we32 = new WallEnd(mw23, ws23, false);

        WallEnd we31 = new WallEnd(mw31, ws31, true);
        WallEnd we13 = new WallEnd(mw31, ws31, false);

        // The room is one corner in each pillar (normally created by PillarMaker).
        RoomCorner rc1 = new RoomCorner(floorId);
        RoomCorner rc2 = new RoomCorner(floorId);
        RoomCorner rc3 = new RoomCorner(floorId);

        rc1.setWallEnd1(we13);
        rc1.setWallEnd2(we12);

        rc2.setWallEnd1(we21);
        rc2.setWallEnd2(we23);

        rc3.setWallEnd1(we32);
        rc3.setWallEnd2(we31);

        assertEquals(floorId, rc1.getFloorId());

        // Create the romm
        MRoom r = new MRoom(-1);

        // Out of order - the computation must find correct order.
        r.addCorner(rc1);
        r.addCorner(rc3);
        r.addCorner(rc2);

        // Computation to be tested.
        r.finishEdges();

        // Check resulting edges and their connection to the room face.
        assertEquals(3, r.getEdges().size());

        assertEquals(mw12.getE2(), r.getEdges().get(0));
        assertEquals(mw23.getE2(), r.getEdges().get(1));
        assertEquals(mw31.getE2(), r.getEdges().get(2));

        assertEquals(r, r.getEdges().get(0).getRightFace());
        assertEquals(r, r.getEdges().get(1).getRightFace());
        assertEquals(r, r.getEdges().get(2).getRightFace());

    }

    private MWall newMWallWithSomeEdges(int id) {
        MWall w = new MWall();


        MPoint p41 = new MPoint(new Point3D(id+4.1, 0, 0));
        MPoint p12 = new MPoint(new Point3D(id+1.2, 0, 0));
        MPoint p23 = new MPoint(new Point3D(id+2.3, 0, 0));
        MPoint p34 = new MPoint(new Point3D(id+3.4, 0, 0));

        MEdge e1 = new MEdge(p41, p12);
        MEdge e3 = new MEdge(p23, p34);

        w.addEndEdge(e1, true);
        w.addEndEdge(e3, false);
        w.finishEdges();
        return w;
    }

}