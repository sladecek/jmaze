package 	com.github.sladecek.maze.jmaze.print;

import java.io.IOException;

import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

public final class SvgMazePrinter implements IMazePrinter {
	
	final int cellSize = 10;
	final int margin = cellSize / 2;

	public void printLine(int y1, int x1, int y2, int x2, String style, boolean center) throws IOException  {
		int offs = center ? cellSize / 2 : 0;
		xml.startElement("line");
		xml.addAttribute("x1", toUnits(x1) + offs);
		xml.addAttribute("y1", toUnits(y1) + offs);
		xml.addAttribute("x2", toUnits(x2) + offs);
		xml.addAttribute("y2", toUnits(y2) + offs);
		xml.addAttribute("style", style);
		xml.closeElement();
		
	}

	
	public void printMark(int y, int x, String fill, int sizePercent) throws IOException {
		//  <circle cx="50" cy="50" r="40" stroke="black" stroke-width="3" fill="red" />
		int offs = cellSize / 2;
		xml.startElement("circle");
		xml.addAttribute("cx", toUnits(x) + offs);
		xml.addAttribute("cy", toUnits(y) + offs);
		xml.addAttribute("r", cellSize * sizePercent / 100);
		xml.addAttribute("fill", fill);
		xml.closeElement();
	}
	
	private int toUnits(int xy) {
		return margin + xy * cellSize;
	}

	private XmlWriter xml;

	public void printShapes(ShapeContainer maze, String fileName, boolean showSolution) {
		
		try (XmlWriter x = new XmlWriter(fileName)) {
			printShapes(maze,  x, showSolution);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}

	public void printShapes(ShapeContainer maze, XmlWriter x, boolean showSolution) {

		canvasWidth = 2 * margin + maze.getPictureWidth() * cellSize;
		canvasHeight = 2 * margin + maze.getPictureHeight() * cellSize;
		
		
		try  {
			xml = x;
			xml.startElement("svg");
			xml.addAttribute("xmlns", "http://www.w3.org/2000/svg");
			xml.addAttribute("width", canvasWidth);
			xml.addAttribute("height", canvasHeight);
			
	
			for (IMazeShape shape: maze.getShapes()) {
				if (showSolution || shape.getShapeType() != IMazeShape.ShapeType.solution) {
					shape.printToSvg(this);
				}
			}
						
			xml.closeElement(); // svg
			xml = null;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}

	public int getCanvasWidth() {
		return canvasWidth;
	}
	
	public int getCanvasHeight() {
		return canvasHeight;
	}
	
	private int canvasWidth;
	private int canvasHeight;

}
