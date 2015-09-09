package com.github.sladecek.maze.jmaze.print;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Locale;

import com.github.sladecek.maze.jmaze.geometry.Point;

public final class OpenScadWriter implements java.lang.AutoCloseable {

	private Writer out;

	private OutputStream stream;

	public OpenScadWriter(final String fileName) throws IOException {
		stream = new FileOutputStream(fileName);
		out = new OutputStreamWriter(stream, "UTF8");
	}
	
	public OpenScadWriter(final OutputStream stream) throws IOException {
		this.stream = stream;
		out = new OutputStreamWriter(stream, "UTF8");
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


	public void beginDifference() throws IOException {
		out.write("difference() {\n");				
	}
	
	public void closeDifference() throws IOException {
		out.write("}\n");
		
	}

	public void printTranslate(final Point p0) throws IOException {
		out.write("translate(");
		printPoint(p0);
		out.write(")");
	}

	public void printText(final String text, final Color color, final double size) throws IOException {
		out.write("color(" + formatColor(color) + ") {\n");
		out.write("text(\"" + text + "\", size=" + size + ");\n");
		out.write("}\n");
	}
	
	private void printPoint(final Point p0) throws IOException {
		out.write(" [ " + p0.getX() + "," + p0.getY() + "," + p0.getZ() + "] ");
	}

	
	/**
	 * Print polyhedron consisting of 8 points. The points must be ordered by x (most important), then y, then z.
	 * @param polyhedron
	 * @throws IOException
	 */
	public void printPolyhedron(final ArrayList<Point> polyhedron, final String comment, final Color color) 
			throws IOException {
		final int POLYHEDRON_VERTEX_CNT = 8;
		if (polyhedron.size() != POLYHEDRON_VERTEX_CNT) {
			throw new InvalidParameterException("Polyhedrons must have " + POLYHEDRON_VERTEX_CNT + " points");
		}
		out.write("/* " + comment + "*/\n");
		out.write("color(" + formatColor(color) + ") polyhedron (points =[");
		for (int i = 0; i < POLYHEDRON_VERTEX_CNT; i++) {
			printPoint(polyhedron.get(i));
			if (i < POLYHEDRON_VERTEX_CNT - 1) {
				out.write(", ");
			}			
		}
		out.write("], \n");
		out.write("faces = [ [0,2,3,1], [0,1,5,4], [5,7,6,4], [6,7,3,2], [1,3,7,5], [6,2,0,4]] \n");
		out.write("); \n");
		
	}


	public static String formatColor(final Color color) {
		final double r = 255.0;
		return String.format(Locale.US, "[%.2f, %.2f, %.2f]", color.getR()/r, color.getG()/r, color.getB()/r);		
	}
	
}
