package com.github.sladecek.maze.jmaze.moebius;

import java.io.FileOutputStream;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print3d.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.ThreeJs3DPrinter;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

/***
 * Command line application generating a Moebius maze into OpenScad file.
 */
public final class TestAppMoebius {

	private static final Logger LOG = Logger.getLogger("maze.jmaze");

	public static void main(final String[] args) {

		LogManager.getLogManager().reset();
		LOG.setLevel(Level.INFO);
		try {
			FileHandler fh = new FileHandler("maze.log");
			LOG.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());

			final int widthCells = 8;
			final int lengthCells = 120;
			MoebiusMaze maze = new MoebiusMaze(widthCells, lengthCells);
			final Random randomGenerator = new Random();
			randomGenerator.setSeed(0);
			IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);
			MazeRealization r = g.generateMaze(maze);

			Maze3DSizes sizes = new Maze3DSizes();
			sizes.setCellSizeInmm(2);

			IPrintStyle colors = new DefaultPrintStyle();

			ShapeContainer shapes = maze.applyRealization(r);

			double approxRoomSizeInmm = 3;
			MoebiusBlockMaker maker = new MoebiusBlockMaker(shapes, sizes, colors, approxRoomSizeInmm);

			final boolean printInJs = true;
			final boolean printInScad = true;
			final boolean showSolution = true;

			if (printInJs) {
				FileOutputStream f;
				f = new FileOutputStream("maze-moebius.js");
				new ThreeJs3DPrinter().printBlocks(maker, showSolution, f);
			}
			if (printInScad) {
				FileOutputStream f = new FileOutputStream("maze-moebius.scad");
				new OpenScad3DPrinter().printBlocks(maker, showSolution, f);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
