package com.github.sladecek.maze.jmaze;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class OpenScadWriter implements java.lang.AutoCloseable {


	private Writer out;

	private FileOutputStream stream;

	public OpenScadWriter(String fileName) throws IOException {
		stream = new FileOutputStream(fileName);
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

	
	private void printPoint(Point p0) throws IOException {
		out.write(" [ "+ p0.x+ ","+ p0.y+ ","+ p0.z+ "] ");
		
	}

	
	/**
	 * Print polyhedron consisting of 8 points. The points must be ordered by x (most important), then y, then z.
	 * @param polyhedron
	 * @throws IOException
	 */
	public void printPolyhedron(ArrayList<Point> polyhedron, String comment, String color) throws IOException {
		if (polyhedron.size() != 8) {
			throw new InvalidParameterException("Polyhedrons must have 8 points");
		}
		out.write("/* "+ comment+"*/\n");
		out.write("color("+color+") polyhedron (points =[");
		for (int i = 0; i < 8; i++) {
			printPoint(polyhedron.get(i));
			if (i < 7) {
				out.write(", ");
			}			
		}
		out.write("], \n");
		out.write("faces = [ [0,2,3,1], [0,1,5,4], [5,7,6,4], [6,7,3,2], [1,3,7,5], [6,2,0,4]] \n");
		out.write("); \n");
		
	}



	
}
