package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.Color;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class MarkShapeTest {

    @Test
    public void testGetShapeType() {
        int y = 1;
        int x = 2;
        int id = 0;
        MarkShape m = new MarkShape(id, new Point2DInt(x, y));
        m.setMarkType(MarkType.startRoom);
        assertEquals(MarkType.startRoom, m.getMarkType());
    }

    @Test
    public void testGetX() {
        final MarkType type = MarkType.startRoom;
        int y = 1;
        int x = 2;
        int id = 0;
        MarkShape m = new MarkShape(id, new Point2DInt(x, y));
        assertEquals(x, m.getX());
    }

    @Test
    public void testGetY() {
        final MarkType type = MarkType.startRoom;
        int y = 1;
        int x = 2;
        int id = 0;
        MarkShape m = new MarkShape(id, new Point2DInt(x, y));
        assertEquals(y, m.getY());
    }


    @Test
    public void testPrint2DStartRoom() {
        final MarkType type = MarkType.startRoom;
        String expectedFill = "rgb(1,2,3)";
        int expectedSize = 5;
        testPrint2DForOneMarkType(type, expectedFill, expectedSize);
    }


    @Test
    public void testPrint2DTargetRoom() {
        final MarkType type = MarkType.targetRoom;
        String expectedFill = "rgb(4,4,4)";
        int expectedSize = 5;
        testPrint2DForOneMarkType(type, expectedFill, expectedSize);
    }


    @Test
    public void testPrint2DSolutionRoom() {
        final MarkType type = MarkType.solution;
        String expectedFill = "rgb(5,5,5)";
        int expectedSize = 7;
        testPrint2DForOneMarkType(type, expectedFill, expectedSize);
    }

    private void testPrint2DForOneMarkType(final MarkType type, String expectedFill, int expectedSize) {
        I2DDocument mockedDocument = mock(I2DDocument.class);

        boolean isPolarCoordinates = false;
        int pictureHeight = 100;
        int pictureWidth = 200;
        int resolutionX = 7;
        int resolutionY = 7;

        ShapeContext sc = new ShapeContext(isPolarCoordinates, pictureHeight, pictureWidth);

        when(mockedDocument.getContext()).thenReturn(sc);
        int y = 1;
        int x = 2;
        int id = 0;
        MarkShape m = new MarkShape(id, new Point2DInt(x, y));
        m.setMarkType(type);

        IPrintStyle mockedPrintStyle = mock(IPrintStyle.class);
        when(mockedPrintStyle.getStartMarkColor()).thenReturn(new Color("010203"));
        when(mockedPrintStyle.getTargetMarkColor()).thenReturn(new Color("040404"));
        when(mockedPrintStyle.getSolutionMarkColor()).thenReturn(new Color("050505"));
        when(mockedPrintStyle.getStartMarkWidth()).thenReturn(5);
        when(mockedPrintStyle.getTargetMarkWidth()).thenReturn(5);
        when(mockedPrintStyle.getSolutionMarkWidth()).thenReturn(7);
        when(mockedPrintStyle.isPrintSolution()).thenReturn(true);

        m.print2D(mockedDocument, mockedPrintStyle);

        ArgumentCaptor<Point2DInt> center = ArgumentCaptor.forClass(Point2DInt.class);
        ArgumentCaptor<String> fill = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);


        verify(mockedDocument).printMark(center.capture(), fill.capture(),
                size.capture());


        assertEquals(x, center.getValue().getX());
        assertEquals(y, center.getValue().getY());
        assertEquals(expectedFill, fill.getValue());
        assertEquals(expectedSize, (int) size.getValue());

    }

}
