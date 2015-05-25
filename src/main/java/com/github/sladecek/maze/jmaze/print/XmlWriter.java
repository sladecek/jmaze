package com.github.sladecek.maze.jmaze.print;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Stack;

public class XmlWriter implements java.lang.AutoCloseable {

	private static final String indentString = "  ";

	private boolean isStartElementFinished = true;

	private Stack<String> nestedElements = new Stack<String>();

	private Writer out;

	private FileOutputStream stream;

	public XmlWriter(String fileName) throws IOException {
		stream = new FileOutputStream(fileName);
		out = new OutputStreamWriter(stream, "UTF8");
	}

	public void addAttribute(String name, int value) throws IOException {
		addAttribute(name, String.valueOf(value));
	}

	public void addAttribute(String name, String value) throws IOException {
		out.write(" " + name + "=\"" + value + "\"" + " ");
	}
	
	public void close() throws IOException {		
		out.close();
		stream.close();
	}
	
	public void closeElement() throws IOException {
		if (!isStartElementFinished) {
			// quick form <element/>
			isStartElementFinished = true;
			out.write("/>\n");
			nestedElements.pop();
		} else {
			// full form </element>
			out.write("</" + nestedElements.pop() + ">\n");
		}
	}
	
	private void printIndentation() throws IOException {
		int indent = nestedElements.size();
		for (int i = 0; i < indent; i++) {
			out.write(indentString);
		}
	}

	public void startElement(String name) throws IOException {
		if (!isStartElementFinished) {
			out.write(">\n");
		}

		printIndentation();
		out.write("<" + name);

		isStartElementFinished = false;
		nestedElements.push(name);
	}
}
