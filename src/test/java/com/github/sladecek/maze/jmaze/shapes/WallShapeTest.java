package com.github.sladecek.maze.jmaze.shapes;

import static org.junit.Assert.assertEquals;
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

public class WallShapeTest {


	@Test
	public void testGetShapeType() {
		WallShape w = new WallShape(ShapeType.hole, 1, 2, 3, 4);
		assertEquals(ShapeType.hole, w.getShapeType());
	}

	@Test
	public void whenCoordinatesAreRectangularTestPrint2DShouldPrintLine() {
		I2DDocument mockedDocument = mock(I2DDocument.class);
		
		boolean isPolarCoordinates = false;
		ShapeType shapeType = ShapeType.hole;		
		callPrint2D(mockedDocument, isPolarCoordinates, shapeType, 3, 4);
		
		ArgumentCaptor<Point2D> p1 = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<Point2D> p2 = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<String> style = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Boolean> c = ArgumentCaptor.forClass(Boolean.class);
		verify(mockedDocument).printLine(p1.capture(), p2.capture(), style.capture(), c.capture());
		

		assertEquals(0, p1.getValue().getX());
		assertEquals(1, p1.getValue().getY());
		
		assertEquals(4, p2.getValue().getX());
		assertEquals(3, p2.getValue().getY());
		
		assertEquals("", style.getValue());
		assertEquals(false, c.getValue());		
	}

	@Test
	public void whenCoordinatesArePolarTestPrint2DShouldPrintRadialLine() {
		I2DDocument mockedDocument = mock(I2DDocument.class);
		
		boolean isPolarCoordinates = true;
		ShapeType shapeType = ShapeType.hole;		
		callPrint2D(mockedDocument, isPolarCoordinates, shapeType, 5, 0);
		
		ArgumentCaptor<Point2D> p1 = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<Point2D> p2 = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<String> style = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Boolean> c = ArgumentCaptor.forClass(Boolean.class);
		verify(mockedDocument).printLine(p1.capture(), p2.capture(), style.capture(), c.capture());
		

		assertEquals(0, p1.getValue().getX());
		assertEquals(1, p1.getValue().getY());
		
		assertEquals(0, p2.getValue().getX());
		assertEquals(5, p2.getValue().getY());
		
		assertEquals("", style.getValue());
		assertEquals(false, c.getValue());		
	}

	@Test
	public void whenCoordinatesArePolarTestPrint2DShouldPrintArc() {
		I2DDocument mockedDocument = mock(I2DDocument.class);
		
		boolean isPolarCoordinates = true;
		ShapeType shapeType = ShapeType.hole;		
		callPrint2D(mockedDocument, isPolarCoordinates, shapeType, 1, 5);
		
		ArgumentCaptor<Point2D> p1 = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<Point2D> p2 = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<String> style = ArgumentCaptor.forClass(String.class);
		verify(mockedDocument).printArcSegment(p1.capture(), p2.capture(), style.capture());
		

		assertEquals(0, p1.getValue().getX());
		assertEquals(1, p1.getValue().getY());
		
		assertEquals(5, p2.getValue().getX());
		assertEquals(1, p2.getValue().getY());
		
		assertEquals("", style.getValue());		
	}

	@Test
	public void whenCoordinatesArePolarTestPrint2DShouldPrintCircle() {
		I2DDocument mockedDocument = mock(I2DDocument.class);
		
		boolean isPolarCoordinates = true;
		ShapeType shapeType = ShapeType.hole;		
		callPrint2D(mockedDocument, isPolarCoordinates, shapeType, 1, 0);
		
		ArgumentCaptor<Point2D> center = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<String> fill = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> style = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Integer> offsXPercent = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> offsYPercent = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> perimeter = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Boolean> isPerimeterAbsolute = ArgumentCaptor.forClass(Boolean.class);
		
		verify(mockedDocument).printCircle(center.capture(), fill.capture(), 
				offsXPercent.capture(), offsYPercent.capture(), perimeter.capture(), 
				isPerimeterAbsolute.capture(), style.capture());
		

		assertEquals(0, center.getValue().getX());
		assertEquals(0, center.getValue().getY());
		assertEquals("none", fill.getValue());
		assertEquals("", style.getValue());
		assertEquals(0, (int)offsXPercent.getValue());
		assertEquals(0, (int)offsYPercent.getValue());
		assertEquals(1, (int)perimeter.getValue());
		assertEquals(false, isPerimeterAbsolute.getValue());		
	}
	
	@Test
	public void testPrint2DShouldPrintInnerWallInCorrectStyle() {		
		String expectedStyle = "stroke:rgb(4,4,4);stroke-width:3";   
		boolean expectedCenter = false;
		ShapeType shapeType = ShapeType.innerWall;		
		test2DPrintStyle(expectedStyle, expectedCenter, shapeType);		
	}

	@Test
	public void testPrint2DShouldPrintOuterWallInCorrectStyle() {		
		String expectedStyle = "stroke:rgb(5,5,5);stroke-width:5";   
		boolean expectedCenter = false;
		ShapeType shapeType = ShapeType.outerWall;		
		test2DPrintStyle(expectedStyle, expectedCenter, shapeType);		
	}

	@Test
	public void testPrint2DShouldPrintSolutionWallInCorrectStyle() {		
		String expectedStyle = "stroke:rgb(6,6,6);stroke-width:7";   
		boolean expectedCenter = true;
		ShapeType shapeType = ShapeType.solution;		
		test2DPrintStyle(expectedStyle, expectedCenter, shapeType);		
	}

	@Test
	public void testPrint2DShouldPrintAuxiliaryWallInCorrectStyle() {		
		String expectedStyle = "stroke:rgb(7,7,7);stroke-width:3";   
		boolean expectedCenter = false;
		ShapeType shapeType = ShapeType.auxiliaryWall;		
		test2DPrintStyle(expectedStyle, expectedCenter, shapeType);		
	}

	private void test2DPrintStyle(String expectedStyle, boolean expectedCenter,
			ShapeType shapeType) {
		I2DDocument mockedDocument = mock(I2DDocument.class);
		boolean isPolarCoordinates = false;
		callPrint2D(mockedDocument, isPolarCoordinates, shapeType, 3, 0);
		
		ArgumentCaptor<Point2D> p1 = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<Point2D> p2 = ArgumentCaptor.forClass(Point2D.class);
		ArgumentCaptor<String> style = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Boolean> c = ArgumentCaptor.forClass(Boolean.class);
		verify(mockedDocument).printLine(p1.capture(), p2.capture(), style.capture(), c.capture());
				
		assertEquals(expectedStyle, style.getValue());
		assertEquals(expectedCenter, c.getValue());
	}

	
	private void callPrint2D(I2DDocument mockedDocument, boolean isPolarCoordinates, ShapeType shapeType, int y2, int x2) {
		int pictureHeight = 100;
		int pictureWidth = 200;
		int resolutionX = 7;
		int resolutionY = 7;
		ShapeContext sc = new ShapeContext(isPolarCoordinates, pictureHeight, pictureWidth/*, resolutionX, resolutionY*/);
		
		int y1 = 1;
		int x1 = 0;
		
		when(mockedDocument.getContext()).thenReturn(sc);		
		WallShape w = new WallShape(shapeType, y1, x1, y2, x2);
		
		IPrintStyle mockedPrintStyle = mock(IPrintStyle.class);
		when(mockedPrintStyle.getHoleColor()).thenReturn(new Color("010203"));
		when(mockedPrintStyle.getInnerWallColor()).thenReturn(new Color("040404"));
		when(mockedPrintStyle.getOuterWallColor()).thenReturn(new Color("050505"));
		when(mockedPrintStyle.getSolutionWallColor()).thenReturn(new Color("060606"));
		when(mockedPrintStyle.getDebugWallColor()).thenReturn(new Color("070707"));
		when(mockedPrintStyle.getInnerWallWidth()).thenReturn(3);
		when(mockedPrintStyle.getOuterWallWidth()).thenReturn(5);
		when(mockedPrintStyle.getSolutionWallWidth()).thenReturn(7);
		
		w.print2D(mockedDocument, mockedPrintStyle);
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
