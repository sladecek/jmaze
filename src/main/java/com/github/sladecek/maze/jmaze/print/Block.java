package com.github.sladecek.maze.jmaze.print;

import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.geometry.Point;

/**
 * Piece of 3D maze - solid irregular polyhedron.
 * @author sladecek
 *
 */
public class Block {

	public Block(final ArrayList<Point> polyhedron, final String comment, final Color color) {
		this.polyhedron = polyhedron;
		this.comment = comment;
		this.color = color;
	}

	
	public final ArrayList<Point> getPolyhedron() {
		return polyhedron;
	}
	public final String getComment() {
		return comment;
	}
	public final Color getColor() {
		return color;
	}


	private ArrayList<Point> polyhedron;
	private String comment;
	private Color color;
	
}
