package com.github.sladecek.maze.jmaze.print3d;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.printstyle.Color;

public class BlockTest {

	@Test
	public void testNewPolyhedron() {
		ArrayList<Point3D> polyhedron = new ArrayList<Point3D>();
		polyhedron.add(new Point3D(0, 1, 2));
		String comment = "com";
		Color color = new Color("010203");
		Block b = Block.newPolyhedron(polyhedron, comment, color);
		assertEquals(false, b.isMark());
	}

	@Test
	public void testNewMark() {
		String comment = "com";
		Color color = new Color("010203");
		Point3D center = new Point3D(3, 4, 5);
		double radius = 3.14;
		Block b = Block.newMark(center, radius, comment, color);
		assertEquals(true, b.isMark());
	}

	@Test
	public void testGetPolyhedron() {
		ArrayList<Point3D> polyhedron = new ArrayList<Point3D>();
		polyhedron.add(new Point3D(0, 1, 2));
		String comment = "com";
		Color color = new Color("010203");
		Block b = Block.newPolyhedron(polyhedron, comment, color);
		assertEquals(polyhedron, b.getPolyhedron());

	}

	@Test
	public void testGetComment() {
		ArrayList<Point3D> polyhedron = new ArrayList<Point3D>();
		polyhedron.add(new Point3D(0, 1, 2));
		String comment = "com";
		Color color = new Color("010203");
		Block b = Block.newPolyhedron(polyhedron, comment, color);
		assertEquals(comment, b.getComment());
	}

	@Test
	public void testGetColor() {
		ArrayList<Point3D> polyhedron = new ArrayList<Point3D>();
		polyhedron.add(new Point3D(0, 1, 2));
		String comment = "com";
		Color color = new Color("010203");
		Block b = Block.newPolyhedron(polyhedron, comment, color);
		assertEquals(1, color.getR());
		assertEquals(2, color.getG());
		assertEquals(3, color.getB());
	}


}
