package com.github.sladecek.maze.jmaze.print3d.output;
//REV1
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.maze.MazeGenerationException;
import com.github.sladecek.maze.jmaze.print.IMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MBlock;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Save maze model in open scad file format.
 */

public class OpenScad3DPrinter implements IMazePrinter {

    public OpenScad3DPrinter(Model3d model) {
        this.model = model;
    }

    @Override
    public void print(OutputStream stream) throws IOException, MazeGenerationException {
        try (OpenScadComposer scad = new OpenScadComposer(stream)) {
            scad.beginUnion();
            for (MBlock b : model.getBlocks()) {
                printBlock(scad, b);
            }
            scad.closeUnion();
        }
    }

    @Override
    public Point2DInt get2dCanvasSize() {
        assert false : "3d mazes have no canvas";
        return new Point2DInt(0, 0);
    }

    private void printBlock(OpenScadComposer scad, MBlock b) throws IOException {
        ArrayList<Point3D> g = b.getGroundPoints();
        ArrayList<Point3D> c = b.getCeilingPoints();
        int sz = g.size();
        if (sz != c.size()) {
            throw new IllegalArgumentException("Invalid block point count.");
        }

        if (sz < 3) {
            return;
        }
        ArrayList<Point3D> allPoints = new ArrayList<>();
        allPoints.addAll(g);
        allPoints.addAll(c);

        ArrayList<ArrayList<Integer>> faces = new ArrayList<>();

        // ground points
        faces.addAll(printBaseBlocks(sz, 0));
        // ceiling points
        faces.addAll(printBaseBlocks(sz, sz));

        // side faces
        for (int p1 = 0; p1 < sz; p1++) {
            int p2 = (p1 + 1) % sz;
            faces.add(printTriangle(p1, p2, p1 + sz));
            faces.add(printTriangle(p2, p1 + sz, p2 + sz));
        }

        scad.printPolyhedron(allPoints, faces);
    }

    private ArrayList<Integer> printTriangle(int p1, int p2, int p3) {
        ArrayList<Integer> face = new ArrayList<>();
        face.add(p1);
        face.add(p2);
        face.add(p3);
        return face;
    }

    private ArrayList<ArrayList<Integer>> printBaseBlocks(int sz, int offs) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int p2 = 2; p2 < sz; p2++) {
            ArrayList<Integer> face = new ArrayList<>();
            face.add(offs);
            face.add(offs + p2 - 1);
            face.add(offs + p2);
            result.add(face);
        }
        return result;
    }

    private final Model3d model;
}