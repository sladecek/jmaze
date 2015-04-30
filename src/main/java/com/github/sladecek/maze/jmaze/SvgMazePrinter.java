package com.github.sladecek.maze.jmaze;

import java.io.IOException;

public class SvgMazePrinter implements IMazePrinter {

	private void createElements() {

	}

	public void printMaze(IPrintableMaze maze, String fileName) {

		try (XmlWriter xml = new XmlWriter(fileName)) {
			xml.startElement("svg");
			xml.addAttribute("xmlns", "http://www.w3.org/2000/svg");
			xml.addAttribute("width", 100);
			xml.addAttribute("height", 100);

			createElements();
			xml.closeElement();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}
}
