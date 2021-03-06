package com.github.sladecek.maze.jmaze.print3d.output;
//REV1
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print.Color;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Locale;

/*
 * Compose content of Open Scad file.
 */
final class OpenScadComposer implements java.lang.AutoCloseable {

    public OpenScadComposer(final OutputStream stream) throws IOException {
        this.stream = stream;
        out = new OutputStreamWriter(stream, "UTF8");
    }

    public static String formatColor(final Color color) {
        final double r = 255.0;
        return String.format(Locale.US, "[%.2f, %.2f, %.2f]",
                color.getR() / r, color.getG() / r, color.getB() / r);
    }

    public void close() throws IOException {
        out.close();
        stream.close();
    }

    public void beginUnion() throws IOException {
        out.write("union() {\n");
    }

    public void closeUnion() throws IOException {
        out.write("}\n");
    }

    private void printPoint(final Point3D p0) throws IOException {
        out.write(" [ " + p0.getX() + "," + p0.getY() + "," + p0.getZ() + "] ");
    }

    /**
     * Print polyhedron consisting of 8 points. The points must be ordered by x
     * (most important), then y, then z.
     */
    public void printPolyhedron(
            final ArrayList<Point3D> points,
            final ArrayList<ArrayList<Integer>> faces)
            throws IOException {


        out.write("polyhedron (points =[");
        boolean comma = false;
        for (Point3D p : points) {
            if (comma) {
                out.write(", \n");
            }
            printPoint(p);
            comma = true;
        }
        out.write("\n], \n");
        out.write("faces = [ ");
        comma = false;
        for (ArrayList<Integer> f : faces) {
            if (comma) {
                out.write(", \n");
            }
            printFace(f);
            comma = true;
        }

        out.write("\n ] \n");
        out.write("); \n");

    }

    private void printFace(ArrayList<Integer> f) throws IOException {
        out.write("[ ");
        boolean comma = false;
        for (Integer i : f) {
            if (comma) {
                out.write(", ");
            }
            out.write(String.format("%d", i));
            comma = true;
        }
        out.write("] ");
    }

    private final OutputStream stream;
    private Writer out;
}
