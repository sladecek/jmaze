package com.github.sladecek.maze.jmaze.print3d.output;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MFace;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print.IMazePrinter;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Saves 3D maze model into STL files
 * <p>
 * https://en.wikipedia.org/wiki/STL_(file_format)
 */
public class StlMazePrinter implements IMazePrinter {

    public StlMazePrinter(Model3d model) {
        this.model = model;
    }

    private Model3d model;

    @Override
    public void print(OutputStream f) {
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.print("solid ");
            pw.println(model.getName());
            for (MFace face : model.getFaces()) {
                if (face.isVisible()) {
                    printFace(pw, face);
                }
            }
            pw.print("endsolid ");
            pw.println(model.getName());
        }
    }

    private void printFace(PrintWriter pw, MFace face) {
        ArrayList<MPoint> points = face.visitPointsCounterclockwise();
        final int sz = points.size();
        MPoint pt1 = points.get(0);

        for (int p2 = 2; p2 < sz; p2++) {
            MPoint pt2 = points.get(p2 - 1);
            MPoint pt3 = points.get(p2);
            printTriangle(pw, pt1, pt2, pt3);
        }
    }

    private void printTriangle(PrintWriter pw, MPoint pt1, MPoint pt2, MPoint pt3) {
        pw.println("facet normal 0 0 0");
        pw.println("outer loop");
        printVertex(pw, pt1);
        printVertex(pw, pt2);
        printVertex(pw, pt3);
        pw.println("endloop");
        pw.println("endfacet");

    }

    private void printVertex(PrintWriter pw, MPoint pt1) {
        Point3D p = pt1.getCoordinate();
        pw.printf("vertex %.6f %.6f %.6f\n", p.getX(), p.getY(), p.getZ());
    }
}
