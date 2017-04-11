package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.model3d.MEdge;
import com.github.sladecek.maze.jmaze.model3d.MPoint;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;

import static org.neo4j.helpers.collection.MapUtil.map;


/**
 * Planar point in the floor plane. May be mapped to one or two 3D points.
 */
public class FloorPoint extends MPoint {
    public FloorPoint(Point3D p) {
        super(p);
    }


    public MPoint atAltitude(IMaze3DMapper mapper, double altitude) {
        return new MPoint(mapper.map(new Point3D(getCoord().getX(), getCoord().getY(), altitude)));
    }

    public void setAltitude(int a1) {
    }

    public MEdge makeRod(int a1, int a2) {
        return null;
    }

    public FloorPoint cloneAtAltitude(int a2) {
        return null;
    }

    private MEdge rod;

    public MEdge getRod() {
        return rod;
    }

    public void setRod(MEdge rod) {
        this.rod = rod;
    }
}
