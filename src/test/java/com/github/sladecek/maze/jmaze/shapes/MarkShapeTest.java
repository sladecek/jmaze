package com.github.sladecek.maze.jmaze.shapes;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.Color;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;

public class MarkShapeTest {

	@Test
	public void testGetShapeType() {
		final ShapeType type = ShapeType.startRoom;
		int y = 1; 
		int x = 2;
		MarkShape m = new MarkShape(type, y, x);
		assertEquals(ShapeType.startRoom, m.getShapeType());
	}

	@Test
	public void testGetX() {
		final ShapeType type = ShapeType.startRoom;
		int y = 1; 
		int x = 2;
		MarkShape m = new MarkShape(type, y, x);
		assertEquals(x, m.getX());
	}

	@Test
	public void testGetY() {
		final ShapeType type = ShapeType.startRoom;
		int y = 1; 
		int x = 2;
		MarkShape m = new MarkShape(type, y, x);
		assertEquals(y, m.getY());
	}

	@Test
	public void testToString() {
		final ShapeType type = ShapeType.startRoom;
		int y = 1; 
		int x = 2;
		MarkShape m = new MarkShape(type, y, x);
		assertEquals("MarkShape [shapeType=startRoom, x=2, y=1]", m.toString());
	}

	@Test
	public void testPrint2DStartRoom() {
		final ShapeType type = ShapeType.startRoom;
		String expectedFill = "rgb(1,2,3)";
		int expectedSize = 5;
		testPrint2DForOneMarkType(type, expectedFill, expectedSize);		
	}


	@Test
	public void testPrint2DTargetRoom() {
		final ShapeType type = ShapeType.targetRoom;
		String expectedFill = "rgb(4,4,4)";
		int expectedSize = 5;
		testPrint2DForOneMarkType(type, expectedFill, expectedSize);		
	}


	@Test
	public void testPrint2DSolutionRoom() {
		final ShapeType type = ShapeType.solution;
		String expectedFill = "rgb(5,5,5)";
		int expectedSize = 7;
		testPrint2DForOneMarkType(type, expectedFill, expectedSize);		
	}

	private void testPrint2DForOneMarkType(final ShapeType type, String expectedFill, int expectedSize) {
		I2DDocument mockedDocument = mock(I2DDocument.class);
		
		boolean isPolarCoordinates = false;
		int pictureHeight = 100;
		int pictureWidth = 200;
		int resolutionX = 7;
		int resolutionY = 7;

		ShapeContext sc = new ShapeContext(isPolarCoordinates, pictureHeight, pictureWidth/*, resolutionX, resolutionY*/);
		
		when(mockedDocument.getContext()).thenReturn(sc);		
		int y = 1; 
		int x = 2;
		MarkShape m = new MarkShape(type, y, x);

		IPrintStyle mockedPrintStyle = mock(IPrintStyle.class);
		when(mockedPrintStyle.getStartMarkColor()).thenReturn(new Color("010203"));
		when(mockedPrintStyle.getTargetMarkColor()).thenReturn(new Color("040404"));
		when(mockedPrintStyle.getSolutionMarkColor()).thenReturn(new Color("050505"));
		when(mockedPrintStyle.getStartTargetMarkWidth()).thenReturn(5);
		when(mockedPrintStyle.getSolutionMarkWidth()).thenReturn(7);
		
		m.print2D(mockedDocument, mockedPrintStyle);
				
		ArgumentCaptor<Point2D> center = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<String> fill = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);

		
		verify(mockedDocument).printMark(center.capture(), fill.capture(), 
				size.capture());
		

		assertEquals(x, center.getValue().getX());
		assertEquals(y, center.getValue().getY());
		assertEquals(expectedFill, fill.getValue());
		assertEquals(expectedSize, (int)size.getValue());

	}

}
