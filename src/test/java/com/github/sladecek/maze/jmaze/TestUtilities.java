package com.github.sladecek.maze.jmaze;

import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.geometry.Point3D;

/*
 * Static helper methods for testing. 
 */
class TestUtilities {

	public static ArrayList<Point3D> buildPolyhedronForTest(double x, double y, double z, double stepX) {
		ArrayList<Point3D> polyhedron = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			polyhedron.add(new Point3D(x + i * stepX, y, z));
		}
		return polyhedron;
	}

}
