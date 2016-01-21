package com.github.sladecek.maze.jmaze.print3d;

import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.colors.Color;
import com.github.sladecek.maze.jmaze.geometry.Point3D;

/**
 * Piece of 3D maze - solid irregular polyhedron or a spherical mark.
 * @author sladecek
 *
 */
public final class Block {

	private Block() {	
	}
	
	public static Block newPolyhedron(final ArrayList<Point3D> polyhedron, final String comment, final Color color) {
		Block result = new Block();
		result.polyhedron = polyhedron;
		result.comment = comment;
		result.color = color;
		result.isMark = false;
		return result;
	}
	
	public static Block newMark(final Point3D center, double radius, final String comment, final Color color) {
		Block result = new Block();
		result.polyhedron = new ArrayList<Point3D>();
		result.polyhedron.add(center);
		result.comment = comment;
		result.color = color;
		result.isMark = true;
		result.radius = radius;
		return result;
	}

	public ArrayList<Point3D> getPolyhedron() {
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

	private ArrayList<Point3D> polyhedron;
	private String comment;
	private Color color;
	private double radius;


	private boolean isMark;
	
}
