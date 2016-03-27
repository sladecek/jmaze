package com.github.sladecek.maze.jmaze.spheric;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.print3d.BlockMakerBase;
import com.github.sladecek.maze.jmaze.print3d.IBlockMaker;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.printstyle.Color;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * Egg maze - create 3D blocks from 2D shapes.
 */
public final class EggBlockMaker extends BlockMakerBase implements IBlockMaker {

    public EggBlockMaker(EggMaze maze, ShapeContainer shapes, Maze3DSizes sizes, IPrintStyle colors,
            final EggGeometry egg, final int equatorCellCnt) {
        super(shapes, sizes, colors, egg.computeBaseRoomSizeInmm(equatorCellCnt));
        this.egg = egg;
        this.maze = maze;
    }

    @Override
    public void makeBlocks() {
        maze3dMapper = new Egg3dMapper(egg, maze);
        prepareShapes(maze);
        printFloors();
        printWalls();
        printMarks();
    }

    private void printMarks() {
        for (MarkShape ms : marks) {
            Color c = new Color(0, 120, 120, 120);
            if (ms.getShapeType() == ShapeType.startRoom) {
                c = new Color(120, 0, 0, 120);
            } else if (ms.getShapeType() == ShapeType.targetRoom) {
                c = new Color(0, 120, 0, 120);
            }
            printMark(ms.getY(), ms.getX(), c);
        }
    }

    protected void prepareShapes(final Maze maze) {
        walls = new ArrayList<WallShape>();
        floor = new ArrayList<FloorShape>();
        marks = new ArrayList<MarkShape>();
        for (IMazeShape shape : shapes.getShapes()) {
            // TODO neda se to udelat elegantneji ?
            if (shape.getShapeType() == ShapeType.nonHole) {
                floor.add((FloorShape) shape);
            } else if (shape.getShapeType() == ShapeType.innerWall) {
                walls.add((WallShape) shape);
            } else {
                ShapeType sp = shape.getShapeType();
                if (sp == ShapeType.startRoom || sp == ShapeType.targetRoom || sp == ShapeType.solution) {
                    marks.add((MarkShape) shape);
                }
            }
        }
    }

    private void printFloors() {
        for (FloorShape hs : floor) {
            LOGGER.log(Level.INFO, hs.toString());
            LOGGER.log(Level.INFO, "room");
            printFloorWithoutHole(hs.getY(), hs.getX());
            LOGGER.log(Level.INFO, "hole");
            LOGGER.log(Level.INFO, "end");
        }
    }

    private void printWalls() {
        final double wt = sizes.getInnerWallToCellRatio() / 2;
        for (WallShape wall : walls) {
            printWallElements(wt, wall);
        }
    }

    private static final Logger LOGGER = Logger.getLogger("maze.jmaze");
    private EggGeometry egg;
    private EggMaze maze;
    private ArrayList<WallShape> walls;
    private ArrayList<FloorShape> floor;
    private ArrayList<MarkShape> marks;

}
