package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.print.IMazePrinter;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.shapes.HoleShape;
import com.github.sladecek.maze.jmaze.shapes.I3DShapeConsumer;
import com.github.sladecek.maze.jmaze.shapes.LineShape;

public class EggOpenScadPrinter implements IMazePrinter, I3DShapeConsumer  	
{

	public EggOpenScadPrinter(Maze3DSizes sizes) {
		this.sizes = sizes;
	}
	@Override
	public void consumeWall(LineShape ls) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void consumeHole(HoleShape hs) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void printMaze(IPrintableMaze maze, String fileName) {
		// TODO Auto-generated method stub
		
	}

	private Maze3DSizes sizes;
}
