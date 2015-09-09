package com.github.sladecek.maze.jmaze.print;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/***
 * Write 3D maze shapes as ThreeJS javascript file format.
 *  
 * @author sladecek
 *
 */
public final class ThreeJsWriter implements java.lang.AutoCloseable {
	
	private OutputStream stream;
	private OutputStreamWriter out;

	public ThreeJsWriter(final OutputStream stream) throws IOException {
		this.stream = stream;
		out = new OutputStreamWriter(stream, "UTF8");
	}
	
	
	public void close() throws IOException {		
		
		out.close();
		stream.close();
	}
	

}
