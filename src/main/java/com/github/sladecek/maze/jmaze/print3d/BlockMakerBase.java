package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;
import com.github.sladecek.maze.jmaze.printstyle.Color;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base class for 3D Maze printers. Responsible for shared operations such as
 * rectangular room drawing. Converts 2D shapes to 3D blocks.
 */

/*
public abstract class BlockMakerBase {

    public BlockMakerBase(ShapeContainer shapes, Maze3DSizes sizes,
                          IPrintStyle style, double approxRoomSizeInmm) {
        this.shapes = shapes;
        this.sizes = sizes;
        this.style = style;
        this.approxRoomSizeInmm = approxRoomSizeInmm;
        blocks = new ArrayList<Block>();
    }

    public final Iterable<Block> getBlocks() {
        return blocks;
    }

    public final void print8PointPolyhedron(final ArrayList<Point3D> polyhedron,
                                      final String comment, final Color color) {
        LOG.log(Level.INFO, "print8PointPolyhedron " + comment);
        Block b = Block.newPolyhedron(polyhedron, comment, color);
        blocks.add(b);
    }

    public final void printWallElements(final double wallThickness,
                                        WallShape wall) {

        LOG.log(Level.INFO, wall.toString());
        int y1 = wall.getY1();
        int y2 = wall.getY2();
        int x1 = wall.getX1();
        int x2 = wall.getX2();

        // There is an overlap in the corner between walls. Overlaps are not
        // nice, they make
        // ramparts. Therefore the wall must be rendered from three parts - the
        // corners must be rendered separately.
        printInnerWallElement(y1, y1, x1, x1, wallThickness, wallThickness,
                "inner wall corner " + wall.toString(),
                style.getInnerWallColor());
        printInnerWallElement(y2, y2, x2, x2, wallThickness, wallThickness,
                "inner wall corner " + wall.toString(),
                style.getInnerWallColor());
        if (x1 == x2) {
            printInnerWallElement(y1, y2, x1, x2, -wallThickness,
                    wallThickness, "inner wall along y " + wall.toString(),
                    style.getCornerColor());
        } else {
            printInnerWallElement(y1, y2, x1, x2, wallThickness,
                    -wallThickness, "inner wall along x " + wall.toString(),
                    style.getCornerColor());
        }
    }

    public void printInnerWallElement(int y1, int y2, int x1, int x2,
                                      double wy, double wx, String comment, Color color) {

        final double z = sizes.getWallHeightInmm();
        ArrayList<Point3D> p = new ArrayList<Point3D>();
        p.add(maze3dMapper.mapPoint(y1, x1, -wy, -wx, 0));
        p.add(maze3dMapper.mapPoint(y1, x1, -wy, -wx, z));
        p.add(maze3dMapper.mapPoint(y2, x1, wy, -wx, 0));
        p.add(maze3dMapper.mapPoint(y2, x1, wy, -wx, z));
        p.add(maze3dMapper.mapPoint(y1, x2, -wy, wx, 0));
        p.add(maze3dMapper.mapPoint(y1, x2, -wy, wx, z));
        p.add(maze3dMapper.mapPoint(y2, x2, wy, wx, 0));
        p.add(maze3dMapper.mapPoint(y2, x2, wy, wx, z));
        print8PointPolyhedron(p, comment, color);

    }

    public final void printFloorWithoutHole(int cellY, int cellX) {

        ArrayList<Point3D> po = new ArrayList<Point3D>();
        for (SouthNorth sn : SouthNorth.values()) {
            for (UpDown ud : UpDown.values()) {
                Point3D p = getFloorPoint(cellY, cellX, ud, sn, EastWest.east);
                po.add(p);
            }
        }
        for (SouthNorth sn : SouthNorth.values()) {
            for (UpDown ud : UpDown.values()) {
                Point3D p = getFloorPoint(cellY, cellX, ud, sn, EastWest.west);
                po.add(p);
            }
        }

        print8PointPolyhedron(po, "whole floor" + cellX + " " + cellY,
                style.getBaseColor());
    }


    public final void printFloorWithHoleOneRoom(int cellY, int cellX) {
        ArrayList<Point3D> pe = makeFloorSegmentEast(cellY, cellX);
        ArrayList<Point3D> pw = makeFloorSegmentWest(cellY, cellX);
        ArrayList<Point3D> pn = makeFloorSegmentNorth(pe, pw);
        ArrayList<Point3D> ps = makeFloorSegmentSouth(pe, pw);

        print8PointPolyhedron(pe, "base e " + cellX + " " + cellY,
                style.getBaseColor());

        print8PointPolyhedron(pn, "base n " + cellX + " " + cellY,
                style.getBaseColor());

        print8PointPolyhedron(pw, "base w " + cellX + " " + cellY,
                style.getBaseColor());

        print8PointPolyhedron(ps, "base s " + cellX + " " + cellY,
                style.getBaseColor());
    }

    public ArrayList<Point3D> makeFloorSegmentWest(int cellY, int cellX) {
        ArrayList<Point3D> pw = new ArrayList<Point3D>();
        for (SouthNorth sn : SouthNorth.values()) {
            for (UpDown ud : UpDown.values()) {
                Point3D p = getHolePoint(cellY, cellX, EastWest.west, sn, ud);
                pw.add(p);
            }
        }
        for (SouthNorth sn : SouthNorth.values()) {
            for (UpDown ud : UpDown.values()) {
                Point3D p = getFloorPoint(cellY, cellX, ud, sn, EastWest.west);
                pw.add(p);
            }
        }
        return pw;
    }

    public ArrayList<Point3D> makeFloorSegmentEast(int cellY, int cellX) {
        ArrayList<Point3D> pe = new ArrayList<Point3D>();
        for (SouthNorth sn : SouthNorth.values()) {
            for (UpDown ud : UpDown.values()) {
                Point3D p = getFloorPoint(cellY, cellX, ud, sn, EastWest.east);
                pe.add(p);
            }
        }
        for (SouthNorth sn : SouthNorth.values()) {
            for (UpDown ud : UpDown.values()) {
                Point3D p = getHolePoint(cellY, cellX, EastWest.east, sn, ud);
                pe.add(p);
            }
        }
        return pe;
    }

    public ArrayList<Point3D> makeFloorSegmentSouth(ArrayList<Point3D> pe,
                                                    ArrayList<Point3D> pw) {
        ArrayList<Point3D> ps = new ArrayList<Point3D>();
        ps.add(pw.get(6));
        ps.add(pw.get(7));
        ps.add(pw.get(2));
        ps.add(pw.get(3));
        ps.add(pe.get(2));
        ps.add(pe.get(3));
        ps.add(pe.get(6));
        ps.add(pe.get(7));
        return ps;
    }

    public ArrayList<Point3D> makeFloorSegmentNorth(ArrayList<Point3D> pe,
                                                    ArrayList<Point3D> pw) {
        ArrayList<Point3D> pn = new ArrayList<Point3D>();
        pn.add(pw.get(0));
        pn.add(pw.get(1));
        pn.add(pw.get(4));
        pn.add(pw.get(5));
        pn.add(pe.get(4));
        pn.add(pe.get(5));
        pn.add(pe.get(0));
        pn.add(pe.get(1));
        return pn;
    }

    public final void fillHoleInTheFloorOneRoom(FloorShape hs) {
        ArrayList<Point3D> p = new ArrayList<Point3D>();
        for (EastWest ew : EastWest.values()) {
            for (SouthNorth sn : SouthNorth.values()) {
                for (UpDown ud : UpDown.values()) {
                    p.add(getHolePoint(hs.getY(), hs.getX(), ew, sn, ud));
                }
            }
        }
        print8PointPolyhedron(p, "hole", style.getHoleColor());

    }

    public final Point3D getHolePoint(int y, int x, EastWest ew,
                                      SouthNorth sn, UpDown ud) {

        final double wt = sizes.getInnerWallToCellRatio() / 2
                * approxRoomSizeInmm;
        final int dY = (sn == SouthNorth.south) ? 0 : maze3dMapper.getStepY(y,
                x);
        final int dX = (ew == EastWest.east) ? 0 : 1;

        return mapPointWithZ(y + dY, x + dX, ud, 0, (ew == EastWest.east ? wt
                : -wt));

    }

    public Point3D mapPointWithZ(int cellY, int cellX, UpDown ud,
                                 double offsetY, double offsetX) {
        double z = ud == UpDown.down ? 0 : sizes.getBaseThicknessInmm();
        return maze3dMapper.mapPoint(cellY, cellX, offsetY, offsetX, z);
    }

    public final Point3D getFloorPoint(int cellY, int cellX, UpDown ud, SouthNorth sn,
                                       EastWest ew) {
        final int dY = (sn == SouthNorth.south) ? 0 : maze3dMapper.getStepY(
                cellY, cellX);
        final int dX = (ew == EastWest.east) ? 0 : 1;
        return mapPointWithZ(cellY + dY, cellX + dX, ud, 0, 0);
    }

    public void printMark(int y, int x, Color color) {

        double z = 3 * sizes.getBaseThicknessInmm();
        double avgX = 0;
        double avgY = 0;
        double avgZ = 0;
        for (EastWest ew : EastWest.values()) {
            for (SouthNorth sn : SouthNorth.values()) {
                final int dY = (sn == SouthNorth.south) ? 0 : maze3dMapper
                        .getStepY(y, x);
                final int dX = (ew == EastWest.east) ? 0 : 1;

                Point3D p0 = maze3dMapper.mapPoint(y + dY, x + dX, 0, 0, z);
                avgX += p0.getX();
                avgY += p0.getY();
                avgZ += p0.getZ();
            }
        }

        final Point3D center = new Point3D(avgX / 4, avgY / 4, avgZ / 4);
        final double radius = 1 * sizes.getBaseThicknessInmm();

        Block b = Block.newMark(center, radius, "", color);
        blocks.add(b);

    }

    public IMaze3DMapper getMaze3dMapper() {
        return maze3dMapper;
    }

    public void setMaze3dMapper(IMaze3DMapper maze3dMapper) {
        this.maze3dMapper = maze3dMapper;
    }

    private static final Logger LOG = Logger.getLogger("maze.jmaze");

    protected ShapeContainer shapes;

    protected Maze3DSizes sizes;
    protected IPrintStyle style;

    protected IMaze3DMapper maze3dMapper;

    private double approxRoomSizeInmm;

    protected ArrayList<Block> blocks;

}

*/