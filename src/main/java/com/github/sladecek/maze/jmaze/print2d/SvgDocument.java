package com.github.sladecek.maze.jmaze.print2d;
//REV1
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * SVG document created by printing a maze in 2D. Uses batik SVG library.
 */
public class SvgDocument implements I2DDocument {

    public SvgDocument(ShapeContext context) {
        this.context = context;
        constructCoordinateSystem();
        createSvgRoot();
    }

    @Override
    public void printLine(Point2DInt p1, Point2DInt p2, String style) {
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        Element line = doc.createElementNS(svgNS, "line");
        Element svgRoot = doc.getDocumentElement();
        svgRoot.appendChild(line);

        Point2DInt mp1 = mapper.mapPoint(p1);
        Point2DInt mp2 = mapper.mapPoint(p2);

        line.setAttributeNS(null, "x1", String.valueOf(mp1.getX()));
        line.setAttributeNS(null, "y1", String.valueOf(mp1.getY()));
        line.setAttributeNS(null, "x2", String.valueOf(mp2.getX()));
        line.setAttributeNS(null, "y2", String.valueOf(mp2.getY()));
        line.setAttributeNS(null, "style", style);
    }

    @Override
    public void printArcSegment(Point2DInt p1, Point2DInt p2, String style) {

        if (p1.getY() != p2.getY()) {
            throw new IllegalArgumentException("arc segment must be defined on the same diameter");
        }
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        Element path = doc.createElementNS(svgNS, "path");
        Element svgRoot = doc.getDocumentElement();
        svgRoot.appendChild(path);

        Point2DInt mp1 = mapper.mapPoint(p1);
        Point2DInt mp2 = mapper.mapPoint(p2);
        int r = mapper.mapLength(p1.getY());

        String pth = "M" + mp1.getX() + " " + mp1.getY() + " A" + r + " " + r + " 0 0 1 " + mp2.getX() + " "
                + mp2.getY();

        path.setAttributeNS(null, "d", pth);
        path.setAttributeNS(null, "style", style);
        path.setAttributeNS(null, "fill", "none");

    }


    @Override
    public void printMark(Point2DInt center, String fill, int sizePercent) {
        printCircle(center, fill, sizePercent, true, "");
    }

    @Override
    public void printCircle(Point2DInt center, String fill, int perimeter,
                            boolean isPerimeterAbsolute, String style) {
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

        Element circle = doc.createElementNS(svgNS, "circle");

        Element svgRoot = doc.getDocumentElement();
        svgRoot.appendChild(circle);

        Point2DInt mc = mapper.mapPoint(center);


        circle.setAttributeNS(null, "cx", String.valueOf(mc.getX()));
        circle.setAttributeNS(null, "cy", String.valueOf(mc.getY()));

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
        int margin = context.getMargin();
        canvasWidth = 2 * margin + context.getPictureWidth();
        canvasHeight = 2 * margin + context.getPictureHeight();

        if (context.isPolarCoordinates()) {
            Point2DInt zeroPoint = new Point2DInt(
                    margin + context.getPictureWidth() / 2,
                    margin + context.getPictureHeight() / 2);
            mapper = new Polar2DMapper(zeroPoint, 1);
        } else {
            Point2DInt zeroPoint = new Point2DInt(margin, margin);

            mapper = new Cartesian2DMapper(zeroPoint, 1, 1);
        }
    }

    private final ShapeContext context;
    private Document doc;
    private int canvasWidth;
    private int canvasHeight;
    private IMaze2DMapper mapper;
}
