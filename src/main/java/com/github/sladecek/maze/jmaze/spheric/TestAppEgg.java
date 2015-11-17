package com.github.sladecek.maze.jmaze.spheric;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.MazeColors;
import com.github.sladecek.maze.jmaze.print.OpenScadBlockPrinter;
import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;
import com.github.sladecek.maze.jmaze.print.ThreeJsBlockPrinter;
import com.github.sladecek.maze.jmaze.print.WinterColors;

final class TestAppEgg {
	private TestAppEgg() {		
	}
	
	private static final Logger LOG = Logger.getLogger("LOG");

	public static void main(final String[] args) {
		LogManager.getLogManager().reset();
		LOG.setLevel(Level.INFO);
		try {
			FileHandler fh = new FileHandler("maze.log");
			LOG.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());

			final int equatorCells = 32; // must be power of 2
			EggGeometry egg = new EggGeometry(5, 5, 0);

			EggMaze maze = new EggMaze(egg, equatorCells);
			IMazeGenerator g = new DepthFirstMazeGenerator();
			MazeRealization real = g.generateMaze(maze);

			Maze3DSizes sizes = new Maze3DSizes();
			sizes.setCellSizeInmm(0.1);
			sizes.setBaseThicknessInmm(0.03);
			sizes.setWallHeightInmm(0.3);
			sizes.setInnerWallToCellRatio(0.05);

			MazeColors colors = new WinterColors();

			EggBlockMaker maker = new EggBlockMaker(maze, real, sizes, colors,
					egg, equatorCells);

			final boolean printInJs = true;
			final boolean printInScad = true;
			final String fileName = "maze-egg";
			if (printInJs) {
				ThreeJsBlockPrinter printerJs = new ThreeJsBlockPrinter(maker);
				printerJs.printMaze(fileName + ".js");
			}
			if (printInScad) {
				OpenScadBlockPrinter printerScad = new OpenScadBlockPrinter(
						maker);
				printerScad.printMaze(fileName + ".scad");
			}
/*
			SvgMazePrinter sp = new SvgMazePrinter();
			sp.printMaze(maze, "maze-egg.svg");
*/
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
