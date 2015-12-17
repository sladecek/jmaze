package com.github.sladecek.maze.jmaze.moebius;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.MazeColors;
import com.github.sladecek.maze.jmaze.print.OpenScadBlockPrinter;
import com.github.sladecek.maze.jmaze.print.ThreeJsBlockPrinter;
import com.github.sladecek.maze.jmaze.print.WinterColors;

/***
 * Command line application generating a Moebius maze into OpenScad file.
 * 
 * @author sladecek
 *
 */
public final class TestAppMoebius {
	public static void main(final String[] args) {
		final int widthCells = 8;
		final int lengthCells = 120;
		MoebiusMaze maze = new MoebiusMaze(widthCells, lengthCells);
		IMazeGenerator g = new DepthFirstMazeGenerator();
		MazeRealization r = g.generateMaze(maze);
		/*
		 * TODO SvgMazePrinter smp = new SvgMazePrinter();
		 * smp.printMaze(maze,"maze.svg");
		 */
		Maze3DSizes sizes = new Maze3DSizes();
		sizes.setCellSizeInmm(2);

		MazeColors colors = new WinterColors();

		double approxRoomSizeInmm = 3;
		MoebiusBlockMaker maker = new MoebiusBlockMaker(maze, r, sizes, colors,
				approxRoomSizeInmm);

		final String fileName = "maze-moebius";

		final boolean printInJs = true;
		final boolean printInScad = true;

		if (printInJs) {
			ThreeJsBlockPrinter printerJs = new ThreeJsBlockPrinter(maker);
			printerJs.printMaze(fileName + ".js");
		}
		if (printInScad) {
			OpenScadBlockPrinter printerScad = new OpenScadBlockPrinter(maker);
			printerScad.printMaze(fileName + ".scad");
		}

	}
}
