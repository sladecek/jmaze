package com.github.sladecek.maze.jmaze.shapes;

import static org.junit.Assert.assertEquals;

import com.github.sladecek.maze.jmaze.geometry.Point2D;


/* TODO opravit
public class FloorShapeTest {

	@Test
	public void testGetShapeTypeHole() {
		final Point2D center = new Point2D(2, 1);
		final boolean isHole = true;
		FloorShape f = new FloorShape(center, isHole, 0,0);
		assertEquals(WallType.hole, f.getShapeType());
	}

	@Test
	public void testGetShapeTypeNonHole() {
		final Point2D center = new Point2D(2, 1);
		final boolean isHole = false;
		FloorShape f = new FloorShape(center, isHole, 0,0);
		assertEquals(WallType.nonHole, f.getShapeType());
	}

	@Test
	public void testGetY() {
		final Point2D center = new Point2D(2, 1);
		final boolean isHole = true;
		FloorShape f = new FloorShape(center, isHole, 0,0);
		assertEquals(1, f.getY());
	}

	@Test
	public void testGetX() {
		final Point2D center = new Point2D(2, 1);
		final boolean isHole = true;
		FloorShape f = new FloorShape(center, isHole, 0,0);
		assertEquals(2, f.getX());
	}

	@Test
	public void testIsHole() {
		final Point2D center = new Point2D(2, 1);
		final boolean isHole = false;
		FloorShape f = new FloorShape(center, isHole, 0,0);
		assertEquals(isHole, f.isHole());
	}

	@Test
	public void testCreateMarkInThisRoom() {
		final Point2D center = new Point2D(2, 1);
		final boolean isHole = false;
		FloorShape f = new FloorShape(center, isHole, 0,0);
		MarkShape m = f.createMarkInThisRoom(WallType.solution);
		assertEquals(WallType.solution, m.getShapeType());
		assertEquals(1, m.getY());
		assertEquals(2, m.getX());
	}

	@Test
	public void testToString() {
		final Point2D center = new Point2D(2, 1);
		final boolean isHole = false;
		FloorShape f = new FloorShape(center, isHole, 0,0);
		assertEquals("FloorShape [y=1, x=2, isHole=false]", f.toString());
	}

}
*/