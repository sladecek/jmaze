package com.github.sladecek.maze.jmaze.print2d;

import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;

/*
 * Print 2D maze to SVG or PDF. 
 */
public final class SvgMazePrinter implements IMaze2DPrinter {
	IMaze2DMapper mapper;

	public IMaze2DMapper getMapper() {
		return mapper;
	}

	public SvgMazePrinter() {
		super();
	}

	public void printLine(Point2D p1, Point2D p2, String style,
			boolean center, ShapeContext context) {
		int offs = center ? context.getResolution() / 2 : 0;

		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		Element line = doc.createElementNS(svgNS, "line");
		Element svgRoot = doc.getDocumentElement();
		svgRoot.appendChild(line);

		Point2D mp1 = mapper.mapPoint(p1);
		Point2D mp2 = mapper.mapPoint(p2);
		
		line.setAttributeNS(null, "x1", String.valueOf(mp1.getX() + offs));
		line.setAttributeNS(null, "y1", String.valueOf(mp1.getY() + offs));
		line.setAttributeNS(null, "x2", String.valueOf(mp2.getX() + offs));
		line.setAttributeNS(null, "y2", String.valueOf(mp2.getY() + offs));
		line.setAttributeNS(null, "style", style);

	}

	public void printArcSegment(Point2D p1, Point2D p2, String style, ShapeContext context) {

		assert p1.getY() == p2.getY(): "arc segment must be defined on the same diameter";
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		Element path = doc.createElementNS(svgNS, "path");
		Element svgRoot = doc.getDocumentElement();
		svgRoot.appendChild(path);

		Point2D mp1 = mapper.mapPoint(p1);
		Point2D mp2 = mapper.mapPoint(p2);
		int r = mapper.mapLength(p1.getY());
		
		
		String pth = "M" + mp1.getX() + " " + mp1.getY() + " A"+r+" "+r+" 0 0 1 "+mp2.getX() + " " + mp2.getY();
		
		
		path.setAttributeNS(null, "d", pth);
		path.setAttributeNS(null, "style", style);
		path.setAttributeNS(null, "fill", "none");

	}
	
	public void printMark(Point2D center, String fill, int sizePercent,
			int offsXPercent, int offsYPercent, ShapeContext context) {
		// <circle cx="50" cy="50" r="40" stroke="black" stroke-width="3"
		// fill="red" />

		final int perimeter = context.getResolution() * sizePercent / 100;		
		printCircle(center, fill, offsXPercent, offsYPercent, perimeter, true, new String(), context);

	}

	public void printCircle(Point2D center, String fill, int offsXPercent,
			int offsYPercent, final int perimeter, boolean isPerimeterAbsolute, String style, ShapeContext context) {
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

		Element circle = doc.createElementNS(svgNS, "circle");

		Element svgRoot = doc.getDocumentElement();
		svgRoot.appendChild(circle);

		Point2D mc = mapper.mapPoint(center);
		
		final int cellSize = context.getResolution();
	    final int margin = cellSize / 2;

		int offsX = mapper.mapLength((offsXPercent * cellSize) / 100);
		int offsY = mapper.mapLength((offsYPercent * cellSize) / 100);

		circle.setAttributeNS(null, "cx", String.valueOf(mc.getX() + offsX));
		circle.setAttributeNS(null, "cy", String.valueOf(mc.getY() + offsY));
		
		int perimeterAbsolute = isPerimeterAbsolute ? perimeter : mapper.mapLength(perimeter);
		circle.setAttributeNS(null, "r",
				String.valueOf(perimeterAbsolute));
		circle.setAttributeNS(null, "fill", fill);
		if (!style.isEmpty()) {
			circle.setAttributeNS(null, "style", style);
		}
	}

	public int getCanvasWidth() {
		return canvasWidth;
	}

	public int getCanvasHeight() {
		return canvasHeight;
	}

	private int canvasWidth;
	private int canvasHeight;

	@Override
	public void printShapes(ShapeContainer shapes, MazeOutputFormat format,
			OutputStream output, boolean showSolution) {
		try {
			Document doc = createSvgDom(shapes, showSolution);

			switch (format) {
			case svg:
				Source source = new DOMSource(doc);
				Result result = new StreamResult(output);
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory.newTransformer();
				transformer.transform(source, result);
				break;
			case pdf:
				Transcoder transcoder = new PDFTranscoder();
				TranscoderInput transcoderInput = new TranscoderInput(doc);
				TranscoderOutput transcoderOutput = new TranscoderOutput(output);
				transcoder.transcode(transcoderInput, transcoderOutput);
				break;
			default:
				throw new IllegalArgumentException();
			}

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (TranscoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Document doc;

	private Document createSvgDom(ShapeContainer shapes, boolean showSolution) {
	    
	    
		ShapeContext context = shapes.getContext();
		
        final int cellSize = context.getResolution();
        final int margin = cellSize / 2;

		
		if (context.isPolarCoordinates()) {
			canvasWidth = 2 * margin + context.getPictureWidth() * cellSize;
			canvasHeight = 2 * margin + context.getPictureHeight() * cellSize;
			Point2D zeroPoint = new Point2D(margin + canvasWidth/2, margin + canvasHeight/2);
			mapper = new Polar2DMapper(zeroPoint, cellSize);
		} else {
			canvasWidth = 2 * margin + context.getPictureWidth() * cellSize;
			canvasHeight = 2 * margin + context.getPictureHeight() * cellSize;
			Point2D zeroPoint = new Point2D(margin, margin);
			mapper = new Cartesian2DMapper(zeroPoint, cellSize);
			
		}
		
		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		doc = impl.createDocument(svgNS, "svg", null);

		for (IMazeShape shape : shapes.getShapes()) {
			if (showSolution
					|| shape.getShapeType() != IMazeShape.ShapeType.solution) {
				
				shape.printToSvg(this,context);
			}
		}

		// Set the width and height attributes on the root 'svg' element.
		Element svgRoot = doc.getDocumentElement();
		svgRoot.setAttributeNS(null, "width", Integer.toString(canvasWidth));
		svgRoot.setAttributeNS(null, "height", Integer.toString(canvasHeight));

		return doc;
	}

}
