package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.model3d.Model3d;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Creates model from shapes
 */
public class ModelFromShapes {

    public ModelFromShapes(ShapeContainer shapes, IMaze3DMapper mapper, Maze3DSizes sizes, IPrintStyle style) {
        this.shapes = shapes;
        this.mapper = mapper;
        this.sizes = sizes;
        this.style = style;
    }

    static public Model3d make(ShapeContainer shapes, IMaze3DMapper mapper, Maze3DSizes sizes, IPrintStyle style) {
        ModelFromShapes mfs = new ModelFromShapes(shapes, mapper, sizes, style);
        return mfs.make();
    }

    private Model3d make() {
        m = new Model3d();
        collectEdgesForPillars();
        makePillars();
        makeFloorPlan();
        assignAltitude();
        extrudeBlocks();
        return m;
    }

    private void collectEdgesForPillars() {
        edgesForPillars = new HashMap<>();
        shapes.getShapes().forEach((shape)-> {
            if (shape instanceof  WallShape) {
                WallShape edge = (WallShape)shape;
                addEdgeToPilar(edge, edge.getP1());
                addEdgeToPilar(edge, edge.getP2());
            }
        });
    }

    private void addEdgeToPilar(WallShape edge, Point2D p) {
        Set<WallShape> s = edgesForPillars.get(p);
        if (s == null) {
            s = new HashSet<WallShape>();
            edgesForPillars.put(p, s);
        }
        s.add(edge);
    }

    private void makePillars() {
        for(Point2D center: edgesForPillars.keySet()) {
            double wallWidthInMm = mapper.inverselyMapLengthAt(center,
                    sizes.getInnerWallToCellRatio()*sizes.getCellSizeInmm());
            PillarMaker pm = new PillarMaker(center, edgesForPillars.get(center), wallWidthInMm);
            pm.makePillars();
        }
    }

    private void makeFloorPlan() {
    }

    private void assignAltitude() {
    }

    private void extrudeBlocks() {
    }

    private ShapeContainer shapes;
    private Maze3DSizes sizes;
    private IPrintStyle style;
    private IMaze3DMapper mapper;
    private Model3d m;
    private HashMap<Point2D, Set<WallShape>> edgesForPillars;
}
