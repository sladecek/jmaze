package com.github.sladecek.maze.jmaze.print2d;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;

import com.github.sladecek.maze.jmaze.shapes.ShapeContext;


public class SvgDocumentTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();

	@Test
	public void testSvgDocumentWithRectangularContextShouldCreateCorrectCoordinateSystem() {

		final boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);

		assertEquals(120, d.getCanvasHeight());
		assertEquals(220, d.getCanvasWidth());

		IMaze2DMapper map = d.getMapper();

		Point2DInt zp = map.mapPoint(new Point2DInt(0, 0));
		assertEquals(10, zp.getX());
		assertEquals(10, zp.getY());

		Point2DInt px = map.mapPoint(new Point2DInt(10, 0));
		assertEquals(20, px.getX());
		assertEquals(10, px.getY());

		Point2DInt py = map.mapPoint(new Point2DInt(0, 10));
		assertEquals(10, py.getX());
		assertEquals(20, py.getY());
	}

	@Test
	public void testSvgDocumentWithPolarContextShouldCreateCorrectCoordinateSystem() {

		final boolean isPolarCoordinates = true;
		SvgDocument d = createTestDocument(isPolarCoordinates);

		assertEquals(120, d.getCanvasHeight());
		assertEquals(220, d.getCanvasWidth());

		IMaze2DMapper map = d.getMapper();

		Point2DInt zp = map.mapPoint(new Point2DInt(0, 0));
		assertEquals(110, zp.getX());
		assertEquals(60, zp.getY());

		Point2DInt px = map.mapPoint(new Point2DInt(Point2DInt.ANGLE_2PI / 4, 10));
		assertEquals(110, px.getX());
		assertEquals(70, px.getY());
	}

	@Test
	public void testEmptySvgDocumentShouldCreateCorrectSvg() throws TransformerException {

		final boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);

		Document document = d.getDocument();
		final String expected = header + "/>";
		assertEquals(expected, svgToString(document));

	}

	@Test
	public void testPrintLine() throws TransformerFactoryConfigurationError, TransformerException {
		final boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);
		d.printLine(new Point2DInt(20, 30), new Point2DInt(40, 50), "style");
		
		Document document = d.getDocument();
		
		final String expected = header + "><line y2=\"60\" style=\"style\" x1=\"30\" x2=\"50\" y1=\"40\"/></svg>";
		assertEquals(expected, svgToString(document));
		
	}
	@Test
	public void testPrintArcSegment() throws TransformerFactoryConfigurationError, TransformerException {
		final boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);
		d.printArcSegment(new Point2DInt(20, 30), new Point2DInt(40, 30), "style");
		
		Document document = d.getDocument();
		
		final String expected = header + "><path fill=\"none\" d=\"M30 40 A30 30 0 0 1 50 40\" style=\"style\"/></svg>";
		assertEquals(expected, svgToString(document));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testPrintArcSegmentShouldThrowException() throws TransformerFactoryConfigurationError, TransformerException {
		final boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);
		d.printArcSegment(new Point2DInt(20, 30), new Point2DInt(40, 31), "style");
	}

	@Test
	public void testPrintMark() throws TransformerFactoryConfigurationError, TransformerException {
		final boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);
		d.printMark(new Point2DInt(20, 30), "fill", 9);
		
		Document document = d.getDocument();
		
		final String expected = header + "><circle fill=\"fill\" r=\"9\" cx=\"30\" cy=\"40\"/></svg>";
		assertEquals(expected, svgToString(document));
	}

	@Test
	public void testPrintCircle() throws TransformerFactoryConfigurationError, TransformerException {
		final boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);
		d.printCircle(new Point2DInt(20, 30), "fill", 9,true, "style");
		
		Document document = d.getDocument();
		
		final String expected = header + "><circle fill=\"fill\" r=\"9\" style=\"style\" cx=\"30\" cy=\"40\"/></svg>";
		assertEquals(expected, svgToString(document));
	}
	

	private final String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<svg contentScriptType=\"text/ecmascript\" "
			+ "xmlns:xlink=\"http://www.w3.org/1999/xlink\" zoomAndPan=\"magnify\" "
			+ "contentStyleType=\"text/css\" viewBox=\"0 0 220 120\" preserveAspectRatio=\"xMidYMid meet\" "
			+ "xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\"";
	

	private SvgDocument createTestDocument(boolean isPolarCoordinates) {
		final int pictureHeight = 100;
		final int pictureWidth = 200;
		final int margin = 10;
		ShapeContext sc = new ShapeContext(isPolarCoordinates, pictureHeight, pictureWidth, margin);

		return new SvgDocument(sc);
	}

	private String svgToString(Document document)
			throws TransformerFactoryConfigurationError, TransformerException {
		OutputStream out = new ByteArrayOutputStream();
		Source source = new DOMSource(document);
		Result result = new StreamResult(out);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.transform(source, result);
		String xmlReq = out.toString();
		return xmlReq.replace("\r", "").replace("\n", "");
	}

}
