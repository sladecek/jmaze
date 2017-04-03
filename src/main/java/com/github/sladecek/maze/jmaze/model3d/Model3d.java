package com.github.sladecek.maze.jmaze.model3d;

import com.github.sladecek.maze.jmaze.maze3d.MWall;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Complete 3d maze model.
 */
public class Model3d implements IModel3d {
    public static Model3d newFromShapes() {
        return null; // TODO
    }

    @Override
    public Collection<MPoint> getPoints() {
        return null;
    }

    @Override
    public Collection<MEdge> getEdges() {
        return null;
    }

    @Override
    public Collection<MFace> getFaces() {
        return null;
    }

    @Override
    public Collection<MBlock> getBlocks() {
        return null;
    }

    public void addFace(MFace wall) {
    }

    private ArrayList<MPoint> points = new ArrayList<>();
    private ArrayList<MEdge> edges = new ArrayList<>();
    private ArrayList<MFace> faces = new ArrayList<>();
    private ArrayList<MBlock> blocks = new ArrayList<>();

}
