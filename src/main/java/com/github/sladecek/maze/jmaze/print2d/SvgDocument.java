package com.github.sladecek.maze.jmaze.print2d;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;

public class SvgDocument implements I2DDocument {

    public SvgDocument(ShapeContext context) {
        this.context = context;
        final int cellSize = context.getResolution();
        final int margin = cellSize / 2;

        canvasWidth = 2 * margin + context.getPictureWidth() * cellSize;
        canvasHeight = 2 * margin + context.getPictureHeight() * cellSize;

        if (context.isPolarCoordinates()) {
            Point2D zeroPoint = new Point2D(margin + canvasWidth / 2, margin
                    + canvasHeight / 2);
            mapper = new Polar2DMapper(zeroPoint, cellSize);
        } else {
            Point2D zeroPoint = new Point2D(margin, margin);
            mapper = new Cartesian2DMapper(zeroPoint, cellSize);

        }

        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        doc = impl.createDocument(svgNS, "svg", null);

        // Set the width and height attributes on the root 'svg' element.
        Element svgRoot = doc.getDocumentElement();
        svgRoot.setAttributeNS(null, "width", Integer.toString(canvasWidth));
        svgRoot.setAttributeNS(null, "height", Integer.toString(canvasHeight));

    }

    @Override
    public void printLine(Point2D p1, Point2D p2, String style, boolean center) {
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

    @Override
    public void printArcSegment(Point2D p1, Point2D p2, String style) {

        assert p1.getY() == p2.getY() : "arc segment must be defined on the same diameter";
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        Element path = doc.createElementNS(svgNS, "path");
        Element svgRoot = doc.getDocumentElement();
        svgRoot.appendChild(path);

        Point2D mp1 = mapper.mapPoint(p1);
        Point2D mp2 = mapper.mapPoint(p2);
        int r = mapper.mapLength(p1.getY());

        String pth = "M" + mp1.getX() + " " + mp1.getY() + " A" + r + " " + r
                + " 0 0 1 " + mp2.getX() + " " + mp2.getY();

        path.setAttributeNS(null, "d", pth);
        path.setAttributeNS(null, "style", style);
        path.setAttributeNS(null, "fill", "none");

    }

    @Override
    public void printMark(Point2D center, String fill, int sizePercent,
            int offsXPercent, int offsYPercent) {

        final int perimeter = context.getResolution() * sizePercent / 100;
        printCircle(center, fill, offsXPercent, offsYPercent, perimeter, true,
                new String());

    }

    @Override
    public void printCircle(Point2D center, String fill, int offsXPercent,
            int offsYPercent, final int perimeter, boolean isPerimeterAbsolute,
            String style) {
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

        Element circle = doc.createElementNS(svgNS, "circle");

        Element svgRoot = doc.getDocumentElement();
        svgRoot.appendChild(circle);

        Point2D mc = mapper.mapPoint(center);

        final int cellSize = context.getResolution();
        final int margin = cellSize / 2;

        int offsX = mapper.mapLength(offsXPercent) / 100;
        int offsY = mapper.mapLength(offsYPercent) / 100;

        circle.setAttributeNS(null, "cx", String.valueOf(mc.getX() + offsX));
        circle.setAttributeNS(null, "cy", String.valueOf(mc.getY() + offsY));

        int perimeterAbsolute = isPerimeterAbsolute ? perimeter : mapper
                .mapLength(perimeter);
        circle.setAttributeNS(null, "r", String.valueOf(perimeterAbsolute));
        circle.setAttributeNS(null, "fill", fill);
        if (!style.isEmpty()) {
            circle.setAttributeNS(null, "style", style);
        }
    }

    @Override
    public int getCanvasWidth() {
        return canvasWidth;
    }

    @Override
    public int getCanvasHeight() {
        return canvasHeight;
    }

    @Override
    public ShapeContext getContext() {
        return context;
    }

    public Document getDocument() {
        return doc;
    }

    ShapeContext context;
    private Document doc;

    private int canvasWidth;
    private int canvasHeight;

    private IMaze2DMapper mapper;

}
