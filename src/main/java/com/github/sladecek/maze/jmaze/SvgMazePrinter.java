package com.github.sladecek.maze.jmaze;

import java.io.IOException;

public class SvgMazePrinter implements IMazePrinter {
	
	final int cellSize = 10;
	final int margin = cellSize/2;

	public void printLine(int y1, int x1, int y2, int x2, String style, boolean center) throws IOException  {
		int offs = center ? cellSize/2 : 0;
		xml.startElement("line");
		xml.addAttribute("x1",toUnits(x1)+offs);
		xml.addAttribute("y1",toUnits(y1)+offs);
		xml.addAttribute("x2",toUnits(x2)+offs);
		xml.addAttribute("y2",toUnits(y2)+offs);
		xml.addAttribute("style", style);
		xml.closeElement();
		
	}

	public void printMark(int y, int x, String fill) throws IOException {
		//  <circle cx="50" cy="50" r="40" stroke="black" stroke-width="3" fill="red" />
		int offs = cellSize/2 ;
		xml.startElement("circle");
		xml.addAttribute("cx",toUnits(x)+offs);
		xml.addAttribute("cy",toUnits(y)+offs);
		xml.addAttribute("r",cellSize/4);
		xml.addAttribute("fill", fill);
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
