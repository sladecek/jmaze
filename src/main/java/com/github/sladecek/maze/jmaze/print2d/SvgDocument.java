package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SvgDocument implements I2DDocument {

    public SvgDocument(ShapeContext context) {
        this.context = context;
        constructCoordinateSystem();
        createSvgRoot();
    }

    @Override
    public void printLine(Point2D p1, Point2D p2, String style, boolean center) {
        int offsX = center ? context.getResolutionX() / 2 : 0;
        int offsY = center ? context.getResolutionY() / 2 : 0;

        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        Element line = doc.createElementNS(svgNS, "line");
        Element svgRoot = doc.getDocumentElement();
        svgRoot.appendChild(line);

        Point2D mp1 = mapper.mapPoint(p1);
        Point2D mp2 = mapper.mapPoint(p2);

        line.setAttributeNS(null, "x1", String.valueOf(mp1.getX() + offsX));
        line.setAttributeNS(null, "y1", String.valueOf(mp1.getY() + offsY));
        line.setAttributeNS(null, "x2", String.valueOf(mp2.getX() + offsX));
        line.setAttributeNS(null, "y2", String.valueOf(mp2.getY() + offsY));
        line.setAttributeNS(null, "style", style);


    }

    @Override
    public void printArcSegment(Point2D p1, Point2D p2, String style) {

        if (p1.getY() != p2.getY()) {
            throw new IllegalArgumentException("arc segment must be defined on the same diameter");
        }
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        Element path = doc.createElementNS(svgNS, "path");
        Element svgRoot = doc.getDocumentElement();
        svgRoot.appendChild(path);

        Point2D mp1 = mapper.mapPoint(p1);
        Point2D mp2 = mapper.mapPoint(p2);
        int r = mapper.mapLength(p1.getY());

        String pth = "M" + mp1.getX() + " " + mp1.getY() + " A" + r + " " + r + " 0 0 1 " + mp2.getX() + " "
                + mp2.getY();

        path.setAttributeNS(null, "d", pth);
        path.setAttributeNS(null, "style", style);
        path.setAttributeNS(null, "fill", "none");

    }


    @Override
    public void printMark(Point2D center, String fill, int sizePercent, int offsXPercent, int offsYPercent) {

        final int perimeter = /*context.getResolutionX() */ sizePercent /* 100*/;
        printCircle(center, fill, offsXPercent, offsYPercent, perimeter, true, new String());

    }

    @Override
    public void printCircle(Point2D center, String fill, int offsXPercent, int offsYPercent, final int perimeter,
                            boolean isPerimeterAbsolute, String style) {
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

        Element circle = doc.createElementNS(svgNS, "circle");

        Element svgRoot = doc.getDocumentElement();
        svgRoot.appendChild(circle);

        Point2D mc = mapper.mapPoint(center);

        int offsX = mapper.mapLength(offsXPercent) / 100;
        int offsY = mapper.mapLength(offsYPercent) / 100;

        circle.setAttributeNS(null, "cx", String.valueOf(mc.getX() + offsX));
        circle.setAttributeNS(null, "cy", String.valueOf(mc.getY() + offsY));

        int perimeterAbsolute = isPerimeterAbsolute ? perimeter : mapper.mapLength(perimeter);
        circle.setAttributeNS(null, "r", String.valueOf(perimeterAbsolute));
        circle.setAttributeNS(null, "fill", fill);
        if (!style.isEmpty()) {
            circle.setAttributeNS(null, "style", style);
        }
    }

    @Override
    public ShapeContext getContext() {
        return context;
    }

    public Document getDocument() {
        return doc;
    }

    @Override
    public int getCanvasWidth() {
        return canvasWidth;
    }

    @Override
    public int getCanvasHeight() {
        return canvasHeight;
    }

    public IMaze2DMapper getMapper() {
        return mapper;
    }

    private void createSvgRoot() {
        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        doc = impl.createDocument(svgNS, "svg", null);

        Element svgRoot = doc.getDocumentElement();
        String viewBox = "0 0 " + Integer.toString(canvasWidth) + " " + Integer.toString(canvasHeight);
        svgRoot.setAttributeNS(null, "viewBox", viewBox);

    }

    private void constructCoordinateSystem() {
        final int resolutionX = context.getResolutionX();
        final int resolutionY = context.getResolutionY();
        final int margin = resolutionX / 2;

        canvasWidth = 2 * margin + context.getPictureWidth() * resolutionX;
        canvasHeight = 2 * margin + context.getPictureHeight() * resolutionY;

        if (context.isPolarCoordinates()) {
            assert resolutionX == resolutionY;
            Point2D zeroPoint = new Point2D(
                    margin + context.getPictureWidth() * resolutionX / 2,
                    margin + context.getPictureHeight() * resolutionY / 2);
            mapper = new Polar2DMapper(zeroPoint, resolutionX);
        } else {
            Point2D zeroPoint = new Point2D(margin, margin);

            mapper = new Cartesian2DMapper(zeroPoint, resolutionX, resolutionY);
        }
    }

    ShapeContext context;
    private Document doc;

    private int canvasWidth;
    private int canvasHeight;

    private IMaze2DMapper mapper;

}
