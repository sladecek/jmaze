package com.github.sladecek.maze.jmaze.print3d.output;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.maze.MazeGenerationException;
import com.github.sladecek.maze.jmaze.print.IMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MFace;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;


public class ThreeJs3DPrinter implements IMazePrinter {

    public ThreeJs3DPrinter(Model3d model) {
        this.model = model;
    }

    public ThreeJs3DPrinter() {
        super();
    }

    @Override
    public void print(OutputStream stream) throws IOException, MazeGenerationException {
        double maxAmplitude = model
                .getPoints()
                .stream()
                .map(MPoint::amplitude)
                .max(Double::compare)
                .get();

        try (PrintWriter pw = new PrintWriter(stream)) {
            pw.print("{\n");
            pw.print("\"amplitude\": ");
            pw.print(maxAmplitude);
            pw.print(",\n");
            pw.print("\"vertices\": [");

            for (MFace face : model.getFaces()) {
                if (face.isVisible()) {
                    printFace(pw, face);
                }
            }
            pw.print("]\n ");
            pw.print("}\n ");

        }
    }

    @Override
    public Point2DInt get2dCanvasSize() {
        assert false: "3d mazes have no canvas";
        return new Point2DInt(0,0);
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
        if (skipComma) {
            skipComma = false;
        } else {
            pw.println(",");
        }
        printVertex(pw, pt1);
        pw.println(",");
        printVertex(pw, pt2);
        pw.println(",");
        printVertex(pw, pt3);
        pw.println();
    }

    private void printVertex(PrintWriter pw, MPoint pt1) {
        Point3D p = pt1.getCoordinate();
        pw.printf("%.6f, %.6f, %.6f\n", p.getX(), p.getY(), p.getZ());
    }

    private Model3d model;


    private boolean skipComma = true;
}
