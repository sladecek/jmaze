package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MBlock;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MFace;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.FloorFace;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.TelescopicPoint;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests a part of ModelFromShapes class - making blocks.
 */
public class ModelFromShapesBlockMakingTest {

    @Test
    public void testBlockMaking() throws Exception {
        final PlanarMapper mapper = new PlanarMapper();

        MFace f = new FloorFace();
        f.addEdge(new MEdge(new TelescopicPoint(1.0, 1.0), new TelescopicPoint(-1.0, -1.0)));
        f.addEdge(new MEdge(new TelescopicPoint(-1.0, 20.0), new TelescopicPoint(1.0, 19.0)));
        f.addEdge(new MEdge(new TelescopicPoint(-1.0, -1.0), new TelescopicPoint(-1.0, 20.0)));
        f.addEdge(new MEdge(new TelescopicPoint(1.0, 19.0), new TelescopicPoint(1.0, 1.0)));

        for(MEdge e: f.getEdges()) {
            ((TelescopicPoint)e.getP1()).setOwnAltitude(mapper);
            ((TelescopicPoint)e.getP2()).setOwnAltitude(mapper);
            e.setRightFace(f);
        }

        ArrayList<MPoint> p = f.visitPointsCounterclockwise();
        assertEquals(p.size(), 4);

        final double epsilon=1e-6;
        assertEquals(1.0, ((TelescopicPoint)p.get(3)).getPlanarX(), epsilon);
        assertEquals(1.0, ((TelescopicPoint)p.get(3)).getPlanarY(), epsilon);
        assertEquals(1.0, ((TelescopicPoint)p.get(2)).getPlanarX(), epsilon);
        assertEquals(19.0, ((TelescopicPoint)p.get(2)).getPlanarY(), epsilon);
        assertEquals(-1.0, ((TelescopicPoint)p.get(1)).getPlanarX(), epsilon);
        assertEquals(20.0, ((TelescopicPoint)p.get(1)).getPlanarY(), epsilon);
        assertEquals(-1.0, ((TelescopicPoint)p.get(0)).getPlanarX(), epsilon);
        assertEquals(-1.0, ((TelescopicPoint)p.get(0)).getPlanarY(), epsilon);



        MBlock b = ModelFromShapes.createOneBlockFromFace(f, mapper);
        assertEquals(4, b.getCeilingPoints().size());
        assertEquals(4, b.getGroundPoints().size());


        assertEquals(0, Point3D.computeDistance( new Point3D(1.0, 1.0,1.0), b.getCeilingPoints().get(3)), epsilon);
        assertEquals(0, Point3D.computeDistance( new Point3D(1.0, 19.0,1.0), b.getCeilingPoints().get(2)), epsilon);
        assertEquals(0, Point3D.computeDistance( new Point3D(-1.0, 20.0,1.0), b.getCeilingPoints().get(1)), epsilon);
        assertEquals(0, Point3D.computeDistance( new Point3D(-1.0, -1.0,1.0), b.getCeilingPoints().get(0)), epsilon);

        assertEquals(0, Point3D.computeDistance( new Point3D(1.0, 1.0,0.0), b.getGroundPoints().get(3)), epsilon);
        assertEquals(0, Point3D.computeDistance( new Point3D(1.0, 19.0,0.0), b.getGroundPoints().get(2)), epsilon);
        assertEquals(0, Point3D.computeDistance( new Point3D(-1.0, 20.0,0.0), b.getGroundPoints().get(1)), epsilon);
        assertEquals(0, Point3D.computeDistance( new Point3D(-1.0, -1.0,0.0), b.getGroundPoints().get(0)), epsilon);

    }

}