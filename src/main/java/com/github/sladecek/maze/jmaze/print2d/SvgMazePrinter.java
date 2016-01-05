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

import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

public final class SvgMazePrinter implements IMaze2DPrinter {
	final int cellSize = 10;
	final int margin = cellSize / 2;

	public void printLine(int y1, int x1, int y2, int x2, String style,
			boolean center) {
		int offs = center ? cellSize / 2 : 0;

		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

		Element line = doc.createElementNS(svgNS, "line");

		Element svgRoot = doc.getDocumentElement();
		svgRoot.appendChild(line);

		line.setAttributeNS(null, "x1", String.valueOf(toUnits(x1) + offs));
		line.setAttributeNS(null, "y1", String.valueOf(toUnits(y1) + offs));
		line.setAttributeNS(null, "x2", String.valueOf(toUnits(x2) + offs));
		line.setAttributeNS(null, "y2", String.valueOf(toUnits(y2) + offs));
		line.setAttributeNS(null, "style", style);

	}

	public void printMark(int y, int x, String fill, int sizePercent,
			int offsXPercent, int offsYPercent) {
		// <circle cx="50" cy="50" r="40" stroke="black" stroke-width="3"
		// fill="red" />

		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

		Element circle = doc.createElementNS(svgNS, "circle");

		Element svgRoot = doc.getDocumentElement();
		svgRoot.appendChild(circle);

		int offsX = (offsXPercent * cellSize) / 100;
		int offsY = (offsYPercent * cellSize) / 100;

		circle.setAttributeNS(null, "cx", String.valueOf(toUnits(x) + offsX));
		circle.setAttributeNS(null, "cy", String.valueOf(toUnits(y) + offsY));
		circle.setAttributeNS(null, "r",
				String.valueOf(cellSize * sizePercent / 100));
		circle.setAttributeNS(null, "fill", fill);

	}

	private int toUnits(int xy) {
		return margin + xy * cellSize;
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
	public void printShapes(ShapeContainer maze, MazeOutputFormat format,
			OutputStream output, boolean showSolution) {
		try {
			Document doc = createSvgDom(maze, showSolution);

			switch (format) {
			case svg:
				Source source = new DOMSource(doc);
				// StringWriter stringWriter = new StringWriter();
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

	private Document createSvgDom(ShapeContainer maze, boolean showSolution) {
		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		doc = impl.createDocument(svgNS, "svg", null);

		for (IMazeShape shape : maze.getShapes()) {
			if (showSolution
					|| shape.getShapeType() != IMazeShape.ShapeType.solution) {
				shape.printToSvg(this);
			}
		}

		// Set the width and height attributes on the root 'svg' element.
		canvasWidth = 2 * margin + maze.getPictureWidth() * cellSize;
		canvasHeight = 2 * margin + maze.getPictureHeight() * cellSize;
		Element svgRoot = doc.getDocumentElement();
		svgRoot.setAttributeNS(null, "width", Integer.toString(canvasWidth));
		svgRoot.setAttributeNS(null, "height", Integer.toString(canvasHeight));

		return doc;
	}

}
