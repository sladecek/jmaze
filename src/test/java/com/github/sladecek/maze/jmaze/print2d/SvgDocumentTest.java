package com.github.sladecek.maze.jmaze.print2d;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;

public class SvgDocumentTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();

	@Test
	public void testSvgDocumentWithRectangularContextShouldCreateCorrectCoordinateSystem() {

		boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);

		assertEquals(3 + 3 + 100 * 7, d.getCanvasHeight());
		assertEquals(3 + 3 + 200 * 7, d.getCanvasWidth());

		IMaze2DMapper map = d.getMapper();

		Point2D zp = map.mapPoint(new Point2D(0, 0));
		assertEquals(3, zp.getX());
		assertEquals(3, zp.getY());

		Point2D px = map.mapPoint(new Point2D(10, 0));
		assertEquals(3 + 10 * 7, px.getX());
		assertEquals(3, px.getY());

		Point2D py = map.mapPoint(new Point2D(0, 10));
		assertEquals(3, py.getX());
		assertEquals(3 + 10 * 7, py.getY());
	}

	@Test
	public void testSvgDocumentWithPolarContextShouldCreateCorrectCoordinateSystem() {

		boolean isPolarCoordinates = true;
		SvgDocument d = createTestDocument(isPolarCoordinates);

		assertEquals(3 + 3 + 100 * 7, d.getCanvasHeight());
		assertEquals(3 + 3 + 200 * 7, d.getCanvasWidth());

		IMaze2DMapper map = d.getMapper();

		Point2D zp = map.mapPoint(new Point2D(0, 0));
		assertEquals(3 + 200 * 7 / 2, zp.getX());
		assertEquals(3 + 100 * 7 / 2, zp.getY());

		Point2D px = map.mapPoint(new Point2D(Point2D.ANGLE_2PI / 4, 10));
		assertEquals(3 + 200 * 7 / 2, px.getX());
		assertEquals(3 + 100 * 7 / 2 + 7 * 10, px.getY());
	}

	@Test
	public void testEmptySvgDocumentShouldCreateCorrectSvg() throws TransformerException {

		boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);

		Document document = d.getDocument();
		final String expected = header + "/>";
		assertEquals(expected, svgToString(document));

	}

	@Test
	public void testPrintLine() throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);
		d.printLine(new Point2D(20, 30), new Point2D(40, 50), "style", false);
		
		Document document = d.getDocument();
		
		final String expected = header + "><line y2=\"353\" style=\"style\" x1=\"143\" x2=\"283\" y1=\"213\"/></svg>";
		assertEquals(expected, svgToString(document));
		
	}

	@Test
	public void testPrintArcSegment() throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);
		d.printArcSegment(new Point2D(20, 30), new Point2D(40, 30), "style");
		
		Document document = d.getDocument();
		
		final String expected = header + "><path fill=\"none\" d=\"M143 213 A210 210 0 0 1 283 213\" style=\"style\"/></svg>";
		assertEquals(expected, svgToString(document));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testPrintArcSegmentShouldThrowEception() throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);
		d.printArcSegment(new Point2D(20, 30), new Point2D(40, 31), "style");
	}

	@Test
	public void testPrintMark() throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);
		d.printMark(new Point2D(20, 30), "fill", 9, 33, 66);
		
		Document document = d.getDocument();
		
		final String expected = header + "><circle fill=\"fill\" r=\"9\" cx=\"145\" cy=\"217\"/></svg>";
		assertEquals(expected, svgToString(document));
	}

	@Test
	public void testPrintCircle() throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		boolean isPolarCoordinates = false;
		SvgDocument d = createTestDocument(isPolarCoordinates);
		d.printCircle(new Point2D(20, 30), "fill", 9, 33, 66, true, "style");
		
		Document document = d.getDocument();
		
		final String expected = header + "><circle fill=\"fill\" r=\"66\" style=\"style\" cx=\"143\" cy=\"215\"/></svg>";
		assertEquals(expected, svgToString(document));
	}
	

	final String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<svg contentScriptType=\"text/ecmascript\" width=\"1406\" "
			+ "xmlns:xlink=\"http://www.w3.org/1999/xlink\" zoomAndPan=\"magnify\" "
			+ "contentStyleType=\"text/css\" height=\"706\" preserveAspectRatio=\"xMidYMid meet\" "
			+ "xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\"";

	private SvgDocument createTestDocument(boolean isPolarCoordinates) {
		int pictureHeight = 100;
		int pictureWidth = 200;
		int resolutionX = 7;
		int resolutionY = 7;
		int markOffsetXPercent = 33;
		int markOffsetYPercent = 66;
		ShapeContext sc = new ShapeContext(isPolarCoordinates, pictureHeight, pictureWidth, resolutionX, resolutionY,
				markOffsetXPercent, markOffsetYPercent);

		SvgDocument d = new SvgDocument(sc);
		return d;
	}

	private String svgToString(Document document)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
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
