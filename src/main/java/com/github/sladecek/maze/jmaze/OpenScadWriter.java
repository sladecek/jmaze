package com.github.sladecek.maze.jmaze;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

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


	public void closeUnion() throws IOException {
		out.write("}\n");
		
	}


	public void printPolyhedron(Point p0, Point p1,
			Point p2, Point p3, Point p4,
			Point p5, Point p6, Point p7) throws IOException {
		
		out.write("polyhedron (points =[");
		printPoint(p0);
		out.write(", ");
		printPoint(p1);
		out.write(", ");
		printPoint(p2);
		out.write(", ");
		printPoint(p3);
		out.write(", ");
		printPoint(p4);
		out.write(", ");
		printPoint(p5);
		out.write(", ");
		printPoint(p6);
		out.write(", ");
		printPoint(p7);
		out.write("], \n");
		out.write("faces = [ [0,1,2,3], [1,5,6,2], [5,4,6,7], [4,0,3,7], [0, 4, 5, 1], [2,3,7,6]] \n");
		out.write("); \n");
	}


	private void printPoint(Point p0) throws IOException {
		out.write(" [ "+ p0.x+ ","+ p0.y+ ","+ p0.z+ "] ");
		
	}


	public void beginUnion() throws IOException {
		out.write("union() {\n");
				
		
	}
	
}
