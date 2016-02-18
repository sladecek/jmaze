package com.github.sladecek.maze.jmaze.shapes;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.ArgumentCaptor;

public class WallShapeTest {


	@Test
	public void testGetShapeType() {
		WallShape w = new WallShape(ShapeType.hole, 1, 2, 3, 4);
		assertEquals(ShapeType.hole, w.getShapeType());
	}

	@Test
	public void whenRectangularCoordinatesTestPrint2DShouldPrintLine() {
		I2DDocument mockedDocument = mock(I2DDocument.class);
		
		boolean isPolarCoordinates = false;
		int pictureHeight = 100;
		int pictureWidth = 200;
		int resolution = 7;
		int markOffsetXPercent = 33;
		int markOffsetYPercent = 66;
		ShapeContext sc = new ShapeContext(isPolarCoordinates, pictureHeight, pictureWidth, resolution,
				markOffsetXPercent, markOffsetYPercent);

		
		when(mockedDocument.getContext()).thenReturn(sc);
		WallShape w = new WallShape(ShapeType.hole, 1, 2, 3, 4);
		w.print2D(mockedDocument);
		
		ArgumentCaptor<Point2D> p1 = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<Point2D> p2 = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<String> style = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Boolean> c = ArgumentCaptor.forClass(Boolean.class);
		verify(mockedDocument).printLine(p1.capture(), p2.capture(), style.capture(), c.capture());
		

		assertEquals(2, p1.getValue().getX());
		assertEquals(1, p1.getValue().getY());
		
		assertEquals(4, p2.getValue().getX());
		assertEquals(3, p2.getValue().getY());
		
		assertEquals("", style.getValue());
		assertEquals(false, c.getValue());
		
	}

	@Test
	public void testGetX1() {
		WallShape w = new WallShape(ShapeType.hole, 1, 2, 3, 4);
		assertEquals(2, w.getX1());
	}

	@Test
	public void testGetX2() {
		WallShape w = new WallShape(ShapeType.hole, 1, 2, 3, 4);
		assertEquals(4, w.getX2());
	}

	@Test
	public void testGetY1() {
		WallShape w = new WallShape(ShapeType.hole, 1, 2, 3, 4);
		assertEquals(1, w.getY1());
	}

	@Test
	public void testGetY2() {
		WallShape w = new WallShape(ShapeType.hole, 1, 2, 3, 4);
		assertEquals(3, w.getY2());
	}

	@Test
	public void testToString() {
		WallShape w = new WallShape(ShapeType.hole, 1, 2, 3, 4);
		assertEquals("WallShape [ shapeType=hole, x1=2, x2=4, y1=1, y2=3]", w.toString());
	}

}
