package com.github.sladecek.maze.jmaze;

import java.io.IOException;

public class SvgMazePrinter implements IMazePrinter {
	
	final int cellSize = 10;
	final int margin = cellSize/2;

	public void printLine(int y1, int x1, int y2, int x2, String style) throws IOException  {
		
		xml.startElement("line");
		xml.addAttribute("x1",toUnits(x1));
		xml.addAttribute("y1",toUnits(y1));
		xml.addAttribute("x2",toUnits(x2));
		xml.addAttribute("y2",toUnits(y2));
		xml.addAttribute("style", style);
		xml.closeElement();
		
	}

	private int toUnits(int xy) {
		return margin + xy*cellSize;
	}

	XmlWriter xml;

	public void printMaze(IPrintableMaze maze, String fileName) {

		final int canvasWidth = 2*margin+maze.getPictureWidth()*cellSize;
		final int canvasHeight = 2*margin+maze.getPictureHeight()*cellSize;
		
		
		try (XmlWriter x = new XmlWriter(fileName)) {
			xml = x;
			xml.startElement("svg");
			xml.addAttribute("xmlns", "http://www.w3.org/2000/svg");
			xml.addAttribute("width", canvasWidth );
			xml.addAttribute("height", canvasHeight);
			
/*			// background
			xml.startElement("rect");
			xml.addAttribute("width", canvasWidth );
			xml.addAttribute("height", canvasHeight);
			xml.addAttribute("style", "fill:rgb(255,255,0)");
	*/		
			for(IMazeShape shape: maze.getShapes()){
				shape.printToSvg(this);
			}
			
		//	xml.closeElement(); // rect
			xml.closeElement(); // svg
			xml = null;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}
}
