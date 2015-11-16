package com.github.sladecek.maze.jmaze.print;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.geometry.Point;

/***
 * Write 3D maze shapes as ThreeJS javascript file format.
 *  
 * @author sladecek
 *
 */
public final class ThreeJsComposer implements java.lang.AutoCloseable {
	
	private OutputStream stream;
	private OutputStreamWriter out;
	private boolean needComma = false;
	
	public ThreeJsComposer(final OutputStream stream) throws IOException {
		this.stream = stream;
		out = new OutputStreamWriter(stream, "UTF8");
		out.write("[\n");
		needComma = false;
	}
	
	public void close() throws IOException {		
		out.write("]\n");
		out.close();
		stream.close();
	}

	public ThreeJsComposer(final String fileName) throws IOException {
		stream = new FileOutputStream(fileName);
		out = new OutputStreamWriter(stream, "UTF8");
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
		// TODO out.write("/* " + comment + "*/\n");
		if (needComma) {
			out.write(",\n");
		}
		out.write("[");
		for (int i = 0; i < POLYHEDRON_VERTEX_CNT; i++) {
			printPoint(polyhedron.get(i));
			if (i < POLYHEDRON_VERTEX_CNT - 1) {
				out.write(", ");
			}			
		}
		out.write("] ");
		needComma = true;
		
	}

	
}
