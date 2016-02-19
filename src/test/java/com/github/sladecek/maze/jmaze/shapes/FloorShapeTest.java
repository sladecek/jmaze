package com.github.sladecek.maze.jmaze.shapes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;

public class FloorShapeTest {

	@Test
	public void testGetShapeTypeHole() {
		final int y = 1;
		final int x = 2;
		final boolean isHole = true;
		FloorShape f = new FloorShape(y, x, isHole);
		assertEquals(ShapeType.hole, f.getShapeType());
	}

	@Test
	public void testGetShapeTypeNonHole() {
		final int y = 1;
		final int x = 2;
		final boolean isHole = false;
		FloorShape f = new FloorShape(y, x, isHole);
		assertEquals(ShapeType.nonHole, f.getShapeType());
	}

	@Test
	public void testGetY() {
		final int y = 1;
		final int x = 2;
		final boolean isHole = true;
		FloorShape f = new FloorShape(y, x, isHole);
		assertEquals(y, f.getY());
	}

	@Test
	public void testGetX() {
		final int y = 1;
		final int x = 2;
		final boolean isHole = true;
		FloorShape f = new FloorShape(y, x, isHole);
		assertEquals(x, f.getX());
	}

	@Test
	public void testIsHole() {
		final int y = 1;
		final int x = 2;
		final boolean isHole = false;
		FloorShape f = new FloorShape(y, x, isHole);
		assertEquals(isHole, f.isHole());
	}

	@Test
	public void testCreateMarkInThisRoom() {
		final int y = 1;
		final int x = 2;
		final boolean isHole = false;
		FloorShape f = new FloorShape(y, x, isHole);
		MarkShape m = f.createMarkInThisRoom(ShapeType.solution);
		assertEquals(ShapeType.solution, m.getShapeType());
		assertEquals(1, m.getY());
		assertEquals(2, m.getX());
	}

	@Test
	public void testToString() {
		final int y = 1;
		final int x = 2;
		final boolean isHole = false;
		FloorShape f = new FloorShape(y, x, isHole);
		assertEquals("FloorShape [y=1, x=2, isHole=false]", f.toString());
	}

}
