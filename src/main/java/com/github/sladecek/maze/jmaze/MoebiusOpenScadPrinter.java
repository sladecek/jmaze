package com.github.sladecek.maze.jmaze;

import java.io.IOException;

public class MoebiusOpenScadPrinter implements IMazePrinter {

	public MoebiusOpenScadPrinter(Maze3DSizes sizes) {
		super();
		this.sizes = sizes;
	}

	@Override
	public void printMaze(IPrintableMaze maze, String fileName) {
		this.maze = maze;
		try {
			scad = new OpenScadWriter(fileName);
			cellHeight = maze.getPictureHeight();
			cellWidth = maze.getPictureWidth();
			this.gridMapper = new MoebiusGridMapper(sizes, cellHeight, cellWidth);
			deformator = new MoebiusDeformator(gridMapper.getLength_mm(), gridMapper.getWidth_mm());
			printBase();
			printOuterWalls();
			
			for(IMazeShape shape: maze.getShapes()){
				printShape(shape);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void printShape(IMazeShape shape) {
		// TODO Auto-generated method stub
		
	}

	private void printOuterWalls() throws IOException {
		scad.beginUnion();
		for (int cellX = 0; cellX < cellWidth; cellX++) {
			scad.printPolyhedron(
					deformator.transform(gridMapper.getOuterPoint(cellX, UpDown.down, SouthNorth.south, InnerOuter.outer)),
					deformator.transform(gridMapper.getOuterPoint(cellX, UpDown.down, SouthNorth.south, InnerOuter.inner)),
					deformator.transform(gridMapper.getOuterPoint(cellX, UpDown.up, SouthNorth.south, InnerOuter.inner)),
					deformator.transform(gridMapper.getOuterPoint(cellX, UpDown.up, SouthNorth.south, InnerOuter.outer)),
					deformator.transform(gridMapper.getOuterPoint(cellX+1, UpDown.down, SouthNorth.south, InnerOuter.outer)),
					deformator.transform(gridMapper.getOuterPoint(cellX+1, UpDown.down, SouthNorth.south, InnerOuter.inner)),
					deformator.transform(gridMapper.getOuterPoint(cellX+1, UpDown.up, SouthNorth.south, InnerOuter.inner)),
					deformator.transform(gridMapper.getOuterPoint(cellX+1, UpDown.up, SouthNorth.south, InnerOuter.outer))
					);
			scad.printPolyhedron(
					deformator.transform(gridMapper.getOuterPoint(cellX, UpDown.down, SouthNorth.north, InnerOuter.inner)),
					deformator.transform(gridMapper.getOuterPoint(cellX, UpDown.down, SouthNorth.north, InnerOuter.outer)),
					deformator.transform(gridMapper.getOuterPoint(cellX, UpDown.up, SouthNorth.north, InnerOuter.outer)),
					deformator.transform(gridMapper.getOuterPoint(cellX, UpDown.up, SouthNorth.north, InnerOuter.inner)),
					deformator.transform(gridMapper.getOuterPoint(cellX+1, UpDown.down, SouthNorth.north, InnerOuter.inner)),
					deformator.transform(gridMapper.getOuterPoint(cellX+1, UpDown.down, SouthNorth.north, InnerOuter.outer)),
					deformator.transform(gridMapper.getOuterPoint(cellX+1, UpDown.up, SouthNorth.north, InnerOuter.outer)),
					deformator.transform(gridMapper.getOuterPoint(cellX+1, UpDown.up, SouthNorth.north, InnerOuter.inner))
					);
		}
		scad.closeUnion();

	}

	private void printBase() {
		// TODO Auto-generated method stub
		
	}

	IPrintableMaze maze;
	MoebiusGridMapper gridMapper;
	MoebiusDeformator deformator;
	Maze3DSizes sizes;
	OpenScadWriter scad;
	int cellHeight;
	int cellWidth;
	
	
}
