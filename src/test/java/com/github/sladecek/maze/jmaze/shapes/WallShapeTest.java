package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.Color;
import com.github.sladecek.maze.jmaze.printstyle.PrintStyle;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class WallShapeTest {


    @Test
    public void testGetShapeType() {
        final int id = 777;
        WallShape w = new WallShape(id, WallType.innerWall, new Point2DInt(11, 22), new Point2DInt(33, 44), 3, 4);
        assertEquals(WallType.innerWall, w.getWallType());
    }

    @Test
    public void whenCoordinatesAreRectangularTestPrint2DShouldPrintLine() {
        I2DDocument mockedDocument = mock(I2DDocument.class);

        boolean isPolarCoordinates = false;
        WallType shapeType = WallType.innerWall;
        callPrint2D(mockedDocument, isPolarCoordinates, shapeType, 3, 4);

        ArgumentCaptor<Point2DInt> p1 = ArgumentCaptor.forClass(Point2DInt.class);
        ArgumentCaptor<Point2DInt> p2 = ArgumentCaptor.forClass(Point2DInt.class);
        ArgumentCaptor<String> style = ArgumentCaptor.forClass(String.class);

        verify(mockedDocument).printLine(p1.capture(), p2.capture(), style.capture());


        assertEquals(0, p1.getValue().getX());
        assertEquals(1, p1.getValue().getY());

        assertEquals(4, p2.getValue().getX());
        assertEquals(3, p2.getValue().getY());

        assertEquals("stroke:rgb(4,4,4);stroke-width:3", style.getValue());

    }

    @Test
    public void whenCoordinatesArePolarTestPrint2DShouldPrintRadialLine() {
        I2DDocument mockedDocument = mock(I2DDocument.class);

        boolean isPolarCoordinates = true;
        WallType shapeType = WallType.innerWall;
        callPrint2D(mockedDocument, isPolarCoordinates, shapeType, 5, 0);

        ArgumentCaptor<Point2DInt> p1 = ArgumentCaptor.forClass(Point2DInt.class);
        ArgumentCaptor<Point2DInt> p2 = ArgumentCaptor.forClass(Point2DInt.class);
        ArgumentCaptor<String> style = ArgumentCaptor.forClass(String.class);

        verify(mockedDocument).printLine(p1.capture(), p2.capture(), style.capture());

        assertEquals(0, p1.getValue().getX());
        assertEquals(1, p1.getValue().getY());

        assertEquals(0, p2.getValue().getX());
        assertEquals(5, p2.getValue().getY());

        assertEquals("stroke:rgb(4,4,4);stroke-width:3", style.getValue());

    }

    @Test
    public void whenCoordinatesArePolarTestPrint2DShouldPrintArc() {
        I2DDocument mockedDocument = mock(I2DDocument.class);

        boolean isPolarCoordinates = true;
        WallType shapeType = WallType.innerWall;
        callPrint2D(mockedDocument, isPolarCoordinates, shapeType, 1, 5);

        ArgumentCaptor<Point2DInt> p1 = ArgumentCaptor.forClass(Point2DInt.class);
        ArgumentCaptor<Point2DInt> p2 = ArgumentCaptor.forClass(Point2DInt.class);
        ArgumentCaptor<String> style = ArgumentCaptor.forClass(String.class);
        verify(mockedDocument).printArcSegment(p1.capture(), p2.capture(), style.capture());


        assertEquals(0, p1.getValue().getX());
        assertEquals(1, p1.getValue().getY());

        assertEquals(5, p2.getValue().getX());
        assertEquals(1, p2.getValue().getY());

        assertEquals("stroke:rgb(4,4,4);stroke-width:3", style.getValue());
    }

    @Test
    public void whenCoordinatesArePolarTestPrint2DShouldPrintCircle() {
        I2DDocument mockedDocument = mock(I2DDocument.class);

        boolean isPolarCoordinates = true;
        WallType shapeType = WallType.innerWall;
        callPrint2D(mockedDocument, isPolarCoordinates, shapeType, 1, 0);

        ArgumentCaptor<Point2DInt> center = ArgumentCaptor.forClass(Point2DInt.class);
        ArgumentCaptor<String> fill = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> style = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> perimeter = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Boolean> isPerimeterAbsolute = ArgumentCaptor.forClass(Boolean.class);

        verify(mockedDocument).printCircle(center.capture(), fill.capture(),
                perimeter.capture(),
                isPerimeterAbsolute.capture(), style.capture());


        assertEquals(0, center.getValue().getX());
        assertEquals(0, center.getValue().getY());
        assertEquals("none", fill.getValue());
        assertEquals("stroke:rgb(4,4,4);stroke-width:3", style.getValue());
        assertEquals(1, (int) perimeter.getValue());
        assertEquals(false, isPerimeterAbsolute.getValue());
    }

    @Test
    public void testPrint2DShouldPrintInnerWallInCorrectStyle() {
        String expectedStyle = "stroke:rgb(4,4,4);stroke-width:3";
        boolean expectedCenter = false;
        WallType shapeType = WallType.innerWall;
        test2DPrintStyle(expectedStyle, expectedCenter, shapeType);
    }

    @Test
    public void testPrint2DShouldPrintOuterWallInCorrectStyle() {
        String expectedStyle = "stroke:rgb(5,5,5);stroke-width:5";
        boolean expectedCenter = false;
        WallType shapeType = WallType.outerWall;
        test2DPrintStyle(expectedStyle, expectedCenter, shapeType);
    }

    @Test
    public void testPrint2DShouldPrintSolutionWallInCorrectStyle() {
        String expectedStyle = "stroke:rgb(5,5,5);stroke-width:5";
        boolean expectedCenter = true;
        WallType shapeType = WallType.outerWall;
        test2DPrintStyle(expectedStyle, expectedCenter, shapeType);
    }

    private void test2DPrintStyle(String expectedStyle, boolean expectedCenter,
                                  WallType shapeType) {
        I2DDocument mockedDocument = mock(I2DDocument.class);
        boolean isPolarCoordinates = false;
        callPrint2D(mockedDocument, isPolarCoordinates, shapeType, 3, 0);

        ArgumentCaptor<Point2DInt> p1 = ArgumentCaptor.forClass(Point2DInt.class);
        ArgumentCaptor<Point2DInt> p2 = ArgumentCaptor.forClass(Point2DInt.class);
        ArgumentCaptor<String> style = ArgumentCaptor.forClass(String.class);

        verify(mockedDocument).printLine(p1.capture(), p2.capture(), style.capture());

        assertEquals(expectedStyle, style.getValue());

    }


    private void callPrint2D(I2DDocument mockedDocument, boolean isPolarCoordinates, WallType shapeType, int y2, int x2) {
        int pictureHeight = 100;
        int pictureWidth = 200;
        int resolutionX = 7;
        int resolutionY = 7;
        final int margin = 10;
        ShapeContext sc = new ShapeContext(isPolarCoordinates, pictureHeight, pictureWidth, margin);

        int y1 = 1;
        int x1 = 0;

        when(mockedDocument.getContext()).thenReturn(sc);

        WallShape w = new WallShape(777, shapeType, new Point2DInt(x1, y1), new Point2DInt(x2, y2), 99,99);

        PrintStyle ps = new PrintStyle();
        MazeProperties properties = new MazeProperties();

        properties.put("printSolution", true);
        properties.put("printAllWalls", false);

        properties.put("startMarkColor", new Color("ff0000"));
        properties.put("targetMarkColor",  new Color("00ff00"));
        properties.put("solutionMarkColor", new Color("777777"));

        properties.put("startMarkWidth", 4);
        properties.put("targetMarkWidth",4);
        properties.put("solutionMarkWidth",2);


        properties.put("innerWallColor", new Color("040404"));
        properties.put("outerWallColor", new Color("050505"));
        properties.put("debugWallColor", new Color("070707"));
        properties.put("innerWallWidth", 3);
        properties.put("outerWallWidth", 5);
        ps.configureFromProperties(properties);

        w.print2D(mockedDocument, ps);
    }

    @Test
    public void testGetX1() {
        WallShape w = new WallShape(0, WallType.innerWall, new Point2DInt(11, 22), new Point2DInt(33, 44), 3, 4);
        assertEquals(11, w.getX1());
    }

    @Test
    public void testGetX2() {
        WallShape w = new WallShape(0, WallType.innerWall, new Point2DInt(11, 22), new Point2DInt(33, 44), 3, 4);
        assertEquals(33, w.getX2());
    }

    @Test
    public void testGetY1() {
        WallShape w = new WallShape(0, WallType.innerWall, new Point2DInt(11, 22), new Point2DInt(33, 44), 3, 4);
        assertEquals(22, w.getY1());
    }

    @Test
    public void testGetY2() {
        WallShape w = new WallShape(0, WallType.innerWall, new Point2DInt(11, 22), new Point2DInt(33, 44), 3, 4);
        assertEquals(44, w.getY2());
    }


}
