package com.github.sladecek.maze.jmaze.moebius;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.github.sladecek.maze.jmaze.colors.MazeColors;
import com.github.sladecek.maze.jmaze.colors.WinterColors;
import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print3d.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.ThreeJs3DPrinter;

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

		Maze3DSizes sizes = new Maze3DSizes();
		sizes.setCellSizeInmm(2);

		MazeColors colors = new WinterColors();

		double approxRoomSizeInmm = 3;
		MoebiusBlockMaker maker = new MoebiusBlockMaker(maze, r, sizes, colors,
				approxRoomSizeInmm);

		final boolean printInJs = true;
		final boolean printInScad = true;

		try {

			if (printInJs) {
				FileOutputStream f;
				f = new FileOutputStream("maze-moebius.js");
				new ThreeJs3DPrinter().printBlocks(maker, f);
			}
			if (printInScad) {
				FileOutputStream f = new FileOutputStream("maze-moebius.scad");
				new OpenScad3DPrinter().printBlocks(maker, f);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
