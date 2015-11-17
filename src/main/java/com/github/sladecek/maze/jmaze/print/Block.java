package com.github.sladecek.maze.jmaze.print;

import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.geometry.Point;

/**
 * Piece of 3D maze - solid irregular polyhedron or a spherical mark.
 * @author sladecek
 *
 */
public final class Block {

	private Block() {	
	}
	
	public static Block newPolyhedron(final ArrayList<Point> polyhedron, final String comment, final Color color) {
		Block result = new Block();
		result.polyhedron = polyhedron;
		result.comment = comment;
		result.color = color;
		result.isMark = false;
		return result;
	}
	
	public static Block newMark(final Point center, double radius, final String comment, final Color color) {
		Block result = new Block();
		result.polyhedron = new ArrayList<Point>();
		result.polyhedron.add(center);
		result.comment = comment;
		result.color = color;
		result.isMark = true;
		result.radius = radius;
		return result;
	}

	public ArrayList<Point> getPolyhedron() {
		return polyhedron;
	}
	public String getComment() {
		return comment;
	}
	public Color getColor() {
		return color;
	}
	public boolean isMark() {
		return isMark;
	}

	public double getRadius() {
		return radius;
	}

	private ArrayList<Point> polyhedron;
	private String comment;
	private Color color;
	private double radius;


	private boolean isMark;
	
}
