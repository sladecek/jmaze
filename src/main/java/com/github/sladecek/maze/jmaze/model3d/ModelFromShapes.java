package com.github.sladecek.maze.jmaze.model3d;

import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import org.springframework.ui.Model;

/**
 * Creates model from shapes
 */
public class ModelFromShapes {

    public ModelFromShapes(ShapeContainer shapes, Maze3DSizes sizes, IPrintStyle style) {
        this.shapes = shapes;
        this.sizes = sizes;
        this.style = style;
    }

    static public Model3d make(ShapeContainer shapes, Maze3DSizes sizes, IPrintStyle style) {
        ModelFromShapes mfs = new ModelFromShapes(shapes, sizes, style);
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


    private void makePillars() {
    }

    private void collectEdgesForPillars() {
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
    private Model3d m;
}
